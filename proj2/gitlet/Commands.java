package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import static gitlet.Repository.*;
import static gitlet.Blob.*;
import static gitlet.Utils.*;
import static gitlet.Commit.*;

public class Commands implements Serializable {

    /**Initialize the gitlet directory */
    public static void init() {
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            System.exit(0);
        }
        //create necessary files and dirs
        setupPersistence();

        // creat initial commit0
        Commit init = new Commit("initial commit", null);
        writeContents(HEAD, init.commitSHA1());
        init.saveCommit();

        // create stage class (add+remove)
        Stage add = new Stage();
        Stage remove = new Stage();
        writeObject(addFile, add);
        writeObject(removeFile, remove);
    }

    public static void add(String filename) {
        if (!checkFile(CWD, filename)) {
            System.out.println("File does not exist.");
            System.exit(0);
        }
        File f = join(CWD, filename);
        Blob blob = new Blob(f);
        String sha1 = blob.blobSHA1();

        Commit head = readCommit(readContentsAsString(HEAD));
        // If there is a file in current commit identical to the added file, quit
        if (head.hashmap().containsValue(sha1)) {
            System.exit(0);
        }
        Stage add = readObject(addFile, Stage.class);
        String stagedSHA1 = add.getSHA1(filename);
        if (stagedSHA1 != null) {
            //There is a file with the same name but different contents in ADDITION dir
            deleteFile(ADDITION, stagedSHA1);
            add.putBlob(filename, sha1);
            blob.saveBlob(ADDITION);
        } else {
            add.putBlob(filename, sha1);
            blob.saveBlob(ADDITION);
        }

        //save addFile
        writeObject(addFile, add);
    }

    public static void rm(String filename) {
        Stage add = readObject(addFile, Stage.class);
        if (add.checkBlob(filename)) {
            String stagedSHA1 = add.getSHA1(filename);
            add.rmBlob(filename);
            deleteFile(ADDITION, stagedSHA1);
            writeObject(addFile, add);
            System.exit(0);
        }

        Commit head = readCommit(readContentsAsString(HEAD));
        if (!head.hashmap().containsKey(filename)) {
            System.out.println("No reason to remove the file.");
            System.exit(0);
        }

        File f = join(CWD, filename);
        Blob blob = new Blob(f);
        String SHA1 = blob.blobSHA1();
        Stage remove = readObject(removeFile, Stage.class);
        remove.putBlob(filename, SHA1);
        deleteFile(CWD, filename);
        blob.saveBlob(REMOVAL);
        writeObject(removeFile, remove);
    }

    public static void commit(String message) {
        Stage add = readObject(addFile, Stage.class);
        Stage remove = readObject(removeFile, Stage.class);
        String parentID = readContentsAsString(HEAD);

        if (add.isEmpty() && remove.isEmpty()) {
            System.out.println("No changes added to the commit.");
            System.exit(0);
        }

        Commit newCommit = new Commit(message, parentID);
        Set<Map.Entry<String, String>> addset = add.set();
        Set<Map.Entry<String, String>> rmset = remove.set();

        for (Map.Entry<String, String> entry: addset) {
            String filename = entry.getKey();
            String sha1 = entry.getValue();
            newCommit.addBlob(filename, sha1);
            add.rmBlob(filename);
            File f = join(ADDITION, sha1);
            Blob blob = readObject(f, Blob.class);
            f.delete();
            blob.saveBlob(BLOBS_DIR);
        }

        for (Map.Entry<String, String> entry: rmset) {
            String filename = entry.getKey();
            String sha1 = entry.getValue();
            newCommit.rmBlob(filename);
            remove.rmBlob(filename);
            deleteFile(REMOVAL, sha1);
        }

        writeObject(addFile, add);
        writeObject(removeFile, remove);
        newCommit.saveCommit();
        writeContents(HEAD, newCommit.commitSHA1());
    }



    public static void log() {
        Commit head = readCommit(readContentsAsString(HEAD));
        Commit curPos = head;
        while (curPos.parentID() != null) {
            curPos.printCommit();
            curPos = readCommit(curPos.parentID());
        }
        curPos.printCommit();
    }

    public static void global_log() {
        List<String> commitList = plainFilenamesIn(COMMITS_DIR);
        for (String sha1: commitList) {
            Commit c = readCommit(sha1);
            c.printCommit();
        }
    }

    public static void find(String commitMessage) {
        int flag = 0;
        List<String> commitList = plainFilenamesIn(COMMITS_DIR);
        for (String sha1: commitList) {
            Commit c = readCommit(sha1);
            if (c.message().equals(commitMessage)) {
                flag = 1;
                System.out.println(sha1);
            }
        }

        if (flag == 0) {
            System.out.println("Found no commit with that message.");
        }
    }


    public static void checkout(String filename) {
        Commit head = readCommit(readContentsAsString(HEAD));
        String targetSHA1 = head.getBlobSHA1(filename);
        if (targetSHA1 == null) {
            System.out.println("File does not exist in that commit.");
            System.exit(0);
        }
        Blob blob = readBlob(targetSHA1);
        String filename1 = blob.filename;
        String contents = blob.contents;
        File f = join(CWD, filename);
        writeContents(f, contents);
    }

    public static void checkoutBranch(String branchname) {

    }

    public static void checkout(String commitID, String filename) {
        Commit C = readCommit(commitID);
        if (C == null) {
            System.out.println("No commit with that id exists.");
            System.exit(0);
        }

        String targetSHA1 = C.getBlobSHA1(filename);
        if (targetSHA1 == null) {
            System.out.println("File does not exist in that commit.");
            System.exit(0);
        }

        Blob blob = readBlob(targetSHA1);
        String filename1 = blob.filename;
        String contents = blob.contents;
        File f = join(CWD, filename);
        writeContents(f, contents);

    }

    public static void branch(String branchname) {

    }
}

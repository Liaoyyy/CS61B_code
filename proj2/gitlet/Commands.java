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
        writeContents(MASTER, init.commitSHA1());
        writeContents(HEAD, MASTER.getName());
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
        Stage add = readObject(addFile, Stage.class);

        // If there is a file with identical name in the REMOVAL dir, remove them both
        Stage remove = readObject(removeFile, Stage.class);
        if (remove.getSHA1(filename).equals(sha1)) {
            remove.rmBlob(filename);
            writeObject(removeFile, remove);
            System.exit(0);
        }

        File Branch = join(BRANCH_DIR,readContentsAsString(HEAD));
        Commit head = readCommit(readContentsAsString(Branch));
        // If there is a file in current commit identical to the added file, quit
        if (head.hashmap().containsValue(sha1)) {
            System.exit(0);
        }
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

        File Branch = join(BRANCH_DIR,readContentsAsString(HEAD));
        Commit head = readCommit(readContentsAsString(Branch));
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
        File Branch = join(BRANCH_DIR,readContentsAsString(HEAD));
        String parentID = readContentsAsString(Branch);

        if (add.isEmpty() && remove.isEmpty()) {
            System.out.println("No changes added to the commit.");
            System.exit(0);
        }

        Commit newCommit = new Commit(message, parentID);
        Set<String> addset = add.keySet();
        Set<String> rmset = remove.keySet();
        List<String> addList = new ArrayList<String>(addset);
        List<String> rmList = new ArrayList<String>(rmset);


        for (String addfile: addList) {
            String sha1 = add.getSHA1(addfile);
            newCommit.addBlob(addfile, sha1);
            add.rmBlob(addfile);
            File f = join(ADDITION, sha1);
            Blob blob = readObject(f, Blob.class);
            f.delete();
            blob.saveBlob(BLOBS_DIR);

        }


        for (String rmfile : rmList) {
            String sha1 = remove.getSHA1(rmfile);
            newCommit.rmBlob(rmfile);
            remove.rmBlob(rmfile);
            deleteFile(REMOVAL, sha1);
        }

        writeObject(addFile, add);
        writeObject(removeFile, remove);
        newCommit.saveCommit();
        writeContents(Branch, newCommit.commitSHA1());
    }



    public static void log() {
        File Branch = join(BRANCH_DIR,readContentsAsString(HEAD));
        Commit head = readCommit(readContentsAsString(Branch));
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
        File Branch = join(BRANCH_DIR,readContentsAsString(HEAD));
        Commit head = readCommit(readContentsAsString(Branch));
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
        File f = join(BRANCH_DIR, branchname);
        if (!f.exists()) {
            System.out.println("No such branch exists.");
            System.exit(0);
        }
        String branchSha1 = readContentsAsString(f);

        if (readContentsAsString(HEAD).equals(branchname)) {
            System.out.println("No need to checkout the current branch.");
            System.exit(0);
        }
        File curf= join(BRANCH_DIR, readContentsAsString(HEAD));
        String curSha1 = readContentsAsString(curf);
        Commit branchCommit = readCommit(branchSha1);
        Commit currentCommit = readCommit(curSha1);
        List<String> curList = plainFilenamesIn(CWD);
        for (String curFile: curList) {
            if (currentCommit.getBlobSHA1(curFile) == null) {
                System.out.println(curFile);
                System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                System.exit(0);
            }
        }

        // Empty the ADDITION and REMOVAL dir
        Stage add = readObject(addFile, Stage.class);
        Stage remove = readObject(removeFile, Stage.class);
        Set<String> addset = add.keySet();
        Set<String> rmset = remove.keySet();
        List<String> addList = new ArrayList<String>(addset);
        List<String> rmList = new ArrayList<String>(rmset);
        for (String addfile: addList) {
            String sha1 = add.getSHA1(addfile);
            deleteFile(ADDITION, sha1);
            add.rmBlob(addfile);
        }
        for (String rmfile: rmList) {
            String sha1 = add.getSHA1(rmfile);
            deleteFile(REMOVAL, sha1);
            remove.rmBlob(rmfile);
        }

        // Overwrite
        Collection<String> sha1s = branchCommit.hashmap().values();
        Iterator<String> shaIterator = sha1s.iterator();
        while (shaIterator.hasNext()) {
            String sha1 = shaIterator.next();
            Blob b = readBlob(sha1);
            String filename = b.filename;
            String contents = b.contents;
            File temp = join(CWD, filename);
            writeContents(temp, contents);
        }

        // Delete the file untracked in check-out branch
        for (String curFile: curList) {
            if (branchCommit.getBlobSHA1(curFile) == null) {
                deleteFile(CWD, curFile);
            }
        }

        writeContents(HEAD, branchname);

    }

    public static void checkout(String commitID, String filename) {
        Commit C = null;
        if (commitID.length() == 40) {
            C = readCommit(commitID);
        } else if (commitID.length() == 6) {
            List<String> commitList = plainFilenamesIn(COMMITS_DIR);
            for (String commitName: commitList) {
                String subName = commitName.substring(0,6);
                if (subName.equals(commitID)) {
                    C = readCommit(commitName);
                }
            }
        } else {
            System.out.println("No commit with that id exists.");
            System.exit(0);
        }

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
        File Head = join(BRANCH_DIR, readContentsAsString(HEAD));
        String curSHA1 = readContentsAsString(Head);
        File f = new File(BRANCH_DIR, branchname);
        writeContents(f, curSHA1);
    }

    public static void rmbranch(String branchname) {
        File f = join(REFERENCE_DIR, branchname);
        f.delete();
    }

    public static void status() {
        Stage add = readObject(addFile, Stage.class);
        Stage remove = readObject(removeFile, Stage.class);

        Set<String> addset = add.keySet();
        Set<String> rmset = remove.keySet();

        String currentBranch = readContentsAsString(HEAD);
        List<String> branchList = plainFilenamesIn(BRANCH_DIR);
        List<String> addList = new ArrayList<String>(addset);
        List<String> rmList = new ArrayList<String>(rmset);
        addList.sort(null);
        rmList.sort(null);



        //Branch section
        System.out.println("=== Branches ===");
        if (branchList.size() == 0) System.out.println("!!!");
        for (String branch: branchList) {
            if (branch.equals(currentBranch)) {
                System.out.println("*" + branch);
            } else {
                System.out.println(branch);
            }
        }
        System.out.println();

        //Add section
        System.out.println("=== Staged Files ===");
        for (String addFile: addList) {
            System.out.println(addFile);
        }
        System.out.println();

        //Remove section
        System.out.println("=== Removed Files ===");
        for (String removeFile: rmList) {
            System.out.println(removeFile);
        }
        System.out.println();

        //Modification but not stage section
        System.out.println("=== Modifications Not Staged For Commit ===");
        System.out.println();

        //Untracked files
        System.out.println("=== Untracked Files ===");
        System.out.println();
    }
}


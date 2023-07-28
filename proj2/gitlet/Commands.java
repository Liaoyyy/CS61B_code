package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import static gitlet.Repository.*;
import static gitlet.Utils.*;

public class Commands implements Serializable {

    /**Initialize the gitlet directory */
    public static void init() throws IOException {
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            System.exit(0);
        }
        //create relevant files
        setupPersistence();
        Commit init = new Commit("initial commit","null");
        File Master = createFile(COMMITS_DIR, "master.txt");
        File Head = createFile(COMMITS_DIR, "head.txt");
        writeObject(Master, init);
        writeObject(Head,init);
    }

    public static void add(String filename) {
        if (!checkFile(CWD, filename)) {
            System.out.println("File does not exist.");
            System.exit(0);
        }

        /**check whether current version of the file is identical to that in the current commit.
         * if so, do not add it to ADDITION dir
         * */
        String SHA1 = getSHA1(CWD, filename);
        File Head = join(COMMITS_DIR, "head.txt");
        Commit head = readObject(Head, Commit.class);
        if (head.checkBlob(filename,SHA1)) return;

        copyFiletoAdd(filename);
    }

    public static void commit(String message) throws IOException {
        File Master = join(COMMITS_DIR, "master.txt");
        Commit master = readObject(Master, Commit.class);
        File Head = join(COMMITS_DIR, "head.txt");
        Commit head = readObject(Head, Commit.class);

        List<String> addFilenames = plainFilenamesIn(ADDITION);
        List<String> rmFilenames = plainFilenamesIn(REMOVAL);
        //problem check
        if (addFilenames.size() == 0 && rmFilenames.size() == 0) {
            System.out.println("No changes added to the commit.");
            System.exit(0);
        }

        //Create a new commit
        Commit newCommit = new Commit(message, "head.txt");

        //Move the add file from dir ADDITION to BOLBS_DIR
        for (int i = 0; i < addFilenames.size(); i++) {
            String filename = addFilenames.get(i);
            File f = new File(ADDITION,filename);
            Bolb b = new Bolb(f);
            newCommit.addBlob(filename, b.SHA1());
            removeFile(ADDITION, filename);
        }

        //Remove the file
        for (int i = 0; i < rmFilenames.size(); i++) {
            String filename = rmFilenames.get(i);
            newCommit.rmBlob(filename);
            removeFile(REMOVAL, filename);
        }

        writeObject(Master, newCommit);
        writeObject(Head, newCommit);

    }

    public static void rm(String filename) {
        if (checkFile(ADDITION, filename)) {
            removeFile(ADDITION, filename);
            System.exit(0);
        }

        File Head = join(COMMITS_DIR, "head.txt");
        String SHA1 = getSHA1(CWD, filename);
        Commit head = readObject(Head, Commit.class);
        if (!head.checkBlob(filename,SHA1)) {
            System.out.println("No reason to remove the file.");
            System.exit(0);
        }
        copyFiletoRem(filename);
        restrictedDelete(filename);
    }

    public static void log() {
        File Head = join(COMMITS_DIR, "head.txt");
        Commit head = readObject(Head, Commit.class);
        Commit curPos = head;
        while (curPos != null) {
            curPos.printCommit();
            curPos = curPos.parent;
        }
    }

    public static void global_log() {
        List<String> commitList = plainFilenamesIn(COMMITS_DIR);
        for (String c: commitList) {
            File f = new File(COMMITS_DIR, c);
            Commit C = readObject(f, Commit.class);
            C.printCommit();
        }

    }

    public static void find(String commitMessage) {
        List<String> commitList = plainFilenamesIn(COMMITS_DIR);
        int flag = 0;
        for (String c: commitList) {
            File f = new File(COMMITS_DIR, c);
            Commit C = readObject(f, Commit.class);
            if (C.message().equals(commitMessage)) {
                flag = 1;
                System.out.println(getSHA1(COMMITS_DIR, C.commitname()));
            }
        }

        if (flag == 0) {
            System.out.println("Found no commit with that message.");
        }
    }

    public static void checkout3args() {

    }

    public static void checkout2args() {

    }

    public static void checkout4args() {

    }
}

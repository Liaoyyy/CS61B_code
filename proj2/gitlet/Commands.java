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
            return;
        }
        //create relevant files
        setupPersistence();
        Commit init = new Commit("initial commit",null);
        File Master = createFile(COMMITS_DIR, "master.txt");
        writeObject(Master, init);
    }

    public static void add(String filename) {
        if (!checkFile(CWD, filename)) {
            System.out.println("File does not exist.");
        }

        /**check whether current version of the file is identical to that in the current commit.
         * if so, do not add it to ADDITION dir
         * */
        String SHA1 = getSHA1(CWD, filename);
        File Master = join(COMMITS_DIR, "master.txt");
        Commit master = readObject(Master, Commit.class);
        if (master.checkBlob(SHA1)) return;

        copyFiletoAdd(filename);
    }

    public static void commit(String message) {
        File Master = join(COMMITS_DIR, "master.txt");
        Commit master = readObject(Master, Commit.class);
        List<String> addFilenames = plainFilenamesIn(ADDITION);
        List<String> rmFilenames = plainFilenamesIn(REMOVAL);
        if (addFilenames == null && rmFilenames == null) {
            System.out.println("No changes added to the commit.");
        }
        //create a new commit
        try {
            Commit newCommit = new Commit(message, master);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Move the add file from dir ADDITION to BOLBS_DIR
        for (int i = 0; i< addFilenames.size(); i++) {

        }




    }

    public static void rm(String filename) {

    }

    public static void log() {

    }
}

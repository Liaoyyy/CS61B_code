package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import static gitlet.Repository.*;
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
        File commitFile = init.saveCommit();
        writeContents(HEAD, commitFile.getName());
        printCommit(commitFile.getName());


        // create stage class (add+remove)
        Stage add = new Stage();
        Stage remove = new Stage();
        File addFile = join(ADDITION, "add");
        File removeFile = join(REMOVAL, "remove");
        writeObject(addFile, add);
        writeObject(removeFile, remove);
    }

    public static void add(String filename) {

    }

    public static void commit(String message) throws IOException {


    }

    public static void rm(String filename) {

        restrictedDelete(filename);
    }

    public static void log() {

    }

    public static void global_log() {

    }

    public static void find(String commitMessage) {

    }

    public static void checkout3args(String filename) {

    }

    public static void checkout2args(String branchname) {

    }

    public static void checkout4args(String commitID, String filename) {


    }
}

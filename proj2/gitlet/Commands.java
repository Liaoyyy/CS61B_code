package gitlet;

import java.io.File;
import java.io.IOException;
import static gitlet.Commit.*;
import static gitlet.Repository.*;
import static gitlet.Utils.*;

public class Commands {

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

    public static void add() {

    }

    public static void commit() {

    }

    public static void rm() {

    }

    public static void log() {

    }
}

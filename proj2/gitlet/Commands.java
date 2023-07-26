package gitlet;

import java.io.File;
import java.io.IOException;
import static gitlet.Commit.*;
import static gitlet.Repository.*;
import static gitlet.Utils.*;

public class Commands {
    private static final File CWD = new File(System.getProperty("user.dir"));


    /**Initialize the gitlet directory */
    public static void init() throws IOException {
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            return;
        }
        //create relevant files
        setupPersistence();


        File property = new File(gitDir, "property.txt");
        File masterProperty = new File(gitDir, "masterProperty.txt");
        property.createNewFile();
        masterProperty.createNewFile();

        Commit init = new Commit();
        Commit master = init;
        writeObject(property, init);
        writeObject(masterProperty, master);
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

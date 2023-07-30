package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    /** The objects directory */
    public static final File OBJECTS = join(GITLET_DIR, "objects");
    /** The commits directory. */
    public static final File COMMITS_DIR = join(OBJECTS, "commits");
    /** The bolbs directory. */
    public static final File BLOBS_DIR = join(OBJECTS, "blobs");
    /** The stage directory. */
    public static final File STAGING = join(GITLET_DIR, "stage");
    /** The stage of addition directory */
    public static final File ADDITION = join(STAGING, "addition");
    /** The stage of removal directory */
    public static final File REMOVAL = join(STAGING, "removal");

    /** The HEAD file records sha1 of current commit */
    public static final File HEAD = join(GITLET_DIR, "HEAD");




    /**Create a gitlet repository */
    public static void setupPersistence() {
        GITLET_DIR.mkdir();
        OBJECTS.mkdir();
        COMMITS_DIR.mkdir();
        BLOBS_DIR.mkdir();
        STAGING.mkdir();
        ADDITION.mkdir();
        REMOVAL.mkdir();

        try {
            HEAD.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

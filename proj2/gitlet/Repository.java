package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static gitlet.Utils.*;


/** Represents a gitlet repository.
 *
 *  does at a high level.
 *
 *  @author
 */
public class Repository {
    /**
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    //Dirs
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
    /** The reference directory */
    public static final File REFERENCE_DIR = join(GITLET_DIR, "refs");
    /** The branches directory */
    public static final File BRANCH_DIR =  join(REFERENCE_DIR, "branches");


    //Files
    /** The HEAD file records sha1 of current commit */
    public static final File HEAD = join(GITLET_DIR, "HEAD");
    /** The add file contains the hashmap of the blobs staged for addition */
    public static final File ADDFILE = join(ADDITION, "add");
    /** The remove file contains the hashmap of blobs staged for removal */
    public static final File REMOVEFILE = join(REMOVAL, "remove");
    /** The master branch file */
    public static final File MASTER = join(BRANCH_DIR, "master");
    /** The commit graph file */
    public static final File COMMITGRAPH = join(OBJECTS, "commitGraph");



    /**Create a gitlet repository */
    public static void setupPersistence() {
        GITLET_DIR.mkdir();
        OBJECTS.mkdir();
        COMMITS_DIR.mkdir();
        BLOBS_DIR.mkdir();
        STAGING.mkdir();
        ADDITION.mkdir();
        REMOVAL.mkdir();
        REFERENCE_DIR.mkdir();
        BRANCH_DIR.mkdir();

        try {
            HEAD.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            ADDFILE.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            REMOVEFILE.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            MASTER.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            COMMITGRAPH.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**Check whether the file is existed in the 'path' */
    public static boolean checkFile(File dir, String filename) {
        List<String> list = plainFilenamesIn(dir);
        return list.contains(filename);
    }

    /** Delete the file in path with SHA1 */
    public static void deleteFile(File path, String sha1) {
        File f = join(path, sha1);
        f.delete();
    }

}

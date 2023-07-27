package gitlet;

import java.io.File;
import java.io.IOException;

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
    /** The commits directory. */
    public static final File COMMITS_DIR = join(GITLET_DIR, "GITLET_DIR");
    /** The bolbs directory. */
    public static final File BOLBS_DIR = join(GITLET_DIR, "BOLBS_DIR");
    /** The stage area. */
    public static final File STAGING = join(GITLET_DIR, "STAGEING");
    /** The area for addition */
    public static final File ADDITION = join(STAGING, "ADDITION");
    /** The area for addition */
    public static final File REMOVAL = join(STAGING, "REMOVAL");




    /** Records the num of commits in the commits directory */
    public static final File numOfCommits = new File(COMMITS_DIR, "numOfCommits.txt");
    /** Records the num of bolbs in the staging area */
    public static final File numOfBolbs = new File(COMMITS_DIR, "numOfBolbs.txt");

    /**Create a gitlet repository */
    public static void setupPersistence() {
        GITLET_DIR.mkdir();
        COMMITS_DIR.mkdir();
        BOLBS_DIR.mkdir();
        STAGING.mkdir();
        ADDITION.mkdir();
        REMOVAL.mkdir();
        try {
            numOfCommits.createNewFile();
            numOfBolbs.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writeContents(numOfCommits, "0");
        writeContents(numOfBolbs,"0");
    }

    /**Create a file named 'filename' in the specific 'path' */
    public static File createFile(File path, String filename) {
        File newFile = new File(path, filename);
        if (newFile.exists()) {
            newFile.delete();
        }
        try {
            newFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return newFile;
    }

    /**Remove a file named 'filename' from the specific 'path'*/
    public static void removeFile(File path, String filename) {
        File f = new File(path, filename);
        f.delete();
    }

    /**Check whether the file is existed in the 'path' */
    public static boolean checkFile(File path, String filename) {
        File f =new File(path, filename);
        return f.exists();
    }

    /**Get String SHA1 of the file in 'path' */
    public static String getSHA1(File path, String filename) {
        File f = new File(path, filename);
        String contents = readContentsAsString(f);
        writeContents(f, contents);
        return sha1(contents);
    }

    /** Copy the file name 'filename' from the working space to direc 'ADDITION' */
    public static void copyFiletoAdd(String filename) {
        File f = new File(CWD, filename);
        File copy = createFile(ADDITION, filename);
        String content = readContentsAsString(f);
        writeContents(f, content);
        writeContents(copy, content);
    }

}

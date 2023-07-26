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
    public static final File BOLBS_DIR = join(GITLET_DIR, "GITLET_DIR");
    /** Records the num of commits in the commits directory */
    public static final File numOfCommits = new File(COMMITS_DIR, "numOfCommits.txt");

    /**Create a gitlet repository */
    public static void setupPersistence() {
        GITLET_DIR.mkdir();
        COMMITS_DIR.mkdir();
        BOLBS_DIR.mkdir();
        try {
            numOfCommits.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writeContents(numOfCommits, 0);
    }
}

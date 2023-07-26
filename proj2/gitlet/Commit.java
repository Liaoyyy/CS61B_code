package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.util.Date; // TODO: You'll likely use this in this class

import static gitlet.Utils.*;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable {
    /**
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private String message;
    /** The SHA-1 of the directory*/
    public String sha1;
    /** Record the parent reference of current commit*/
    public Commit parent;

    /**Save the object in the specific file */
    public void savecommit(File file) {
        writeObject(file,this);
    }

    public void Commit(File file, String message, Commit parent) {
        this.sha1 = sha1(file);
        this.message = message;
        this.parent = parent;
    }

    public String returnSha1() {

        return sha1;
    }
}

package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import static gitlet.Utils.*;
import static gitlet.Repository.*;

/** Represents a gitlet commit object.
 *
 *  does at a high level.
 *
 *  @author
 */
public class Commit implements Serializable {
    /**
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private String message;
    /** Record the parent reference(SHA1) of current commit*/
    public String parentID;
    /** Record the date of this Commit*/
    private Date date;
    /** The hashmap of the blobs list
     * Key is the filename of the blob while Value records the SHA1 of the blob file
     * */
    private HashMap<String, String> hashmap;


    public Commit(String message, String parentId) {
        this.message = message;
        this.parentID = parentId;
        this.hashmap = getParentHashmap();
        this.date = getDate();
    }

    /** get the Date information*/
    private Date getDate() {
        Date temp;
        if (this.message.equals("initial commit")) {
            temp = new Date(0);
        } else {
            temp = new Date();
        }
        return temp;
    }

    /** get parent hashmap */
    private HashMap<String,String> getParentHashmap() {
        Commit parent = getParentCommit();
        if (parent == null) {
            return new HashMap<String, String>();
        } else {
            return parent.hashmap();
        }
    }

    /** Return SHA1 of this commit */
    public String commitSHA1(){
        File f = new File(COMMITS_DIR, "temp");
        writeObject(f, this);
        String sha1 = sha1(readContents(f));
        f.delete();
        return sha1;
    }

    /** add Blob to the hashmap */
    public void addBlob(String filename, String SHA1) {
        hashmap.put(filename, SHA1);
    }

    /** rm Blob to the hashmap */
    public void rmBlob(String filename) {
        hashmap.remove(filename);
    }



    /** Get parent Commit */
    private Commit getParentCommit() {
        if (parentID == null) {
            return null;
        }
        return readCommit(parentID);
    }

    /** Save the Commit into the file with the filename of its SHA1*/
    public void saveCommit() {
        //get the sha1 of this commit
        File f = new File(COMMITS_DIR, "temp");
        writeObject(f, this);
        String SHA1 = sha1(readContents(f));
        f.delete();

        File commitFile = new File(COMMITS_DIR, SHA1);
        writeObject(commitFile, this);
    }

    /** Read the Commit from the file named sha1 */
    public static Commit readCommit(String sha1) {
        File f = join(COMMITS_DIR, sha1);
        return readObject(f, Commit.class);
    }

    /**Print the commit information */
    public void printCommit(){
        System.out.println("===");
        System.out.println("commit " + commitSHA1());
        System.out.println("Date:" + this.date.toString());
        System.out.println(this.message);
        System.out.println();
    }


    /** return Hashmap */
    public HashMap<String, String> hashmap() {return this.hashmap;}





}

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
    /** The SHA-1 of the directory*/
    public String sha1;
    /** Record the parent reference of current commit*/
    public Commit parent;
    /** Record the date of this Commit*/
    private Date date;
    /** Record the index of this Commit*/
    private String ind;
    /** Record the information of bolbs in the hashmap*/
    private HashMap<String,String> hashmap;


    public Commit( String message, Commit parent) throws IOException {
        this.message = message;
        this.parent = parent;

        //read the index of this Commit
        ind = readContentsAsString(numOfCommits);
        Integer index=Integer.parseInt(ind)+1;
        writeContents(numOfCommits,index.toString());

        String filename = "commit" + ind +".txt";
        File commit = new File(COMMITS_DIR, filename);
        commit.createNewFile();
        if (ind.equals("0")) {
            date = new Date(0);
            hashmap = null;
        } else {
            date = new Date();
            hashmap = new HashMap<>();
        }

        writeObject(commit,this);
        String content = readContentsAsString(commit);
        this.sha1 = sha1(content);
        writeObject(commit, this);
    }

    /**Add Blob to the hashmap */
    public void addBlob(String filename, String SHA1) {
        hashmap.put(SHA1,filename);
    }

    /**Check whether contains the Blob in current hashmap */
    public boolean checkBlob(String SHA1) {
        if (hashmap == null) return false;
        return hashmap.containsKey(SHA1);
    }

    /**Save the object in the specific file */
    public void saveCommit(File file) {
        writeObject(file,this);
    }

    public void printCommit(){
        System.out.println("commit" + ind);
        System.out.println("Date:" + date.toString());
    }
}

package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import static gitlet.Utils.*;
import static gitlet.Repository.*;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
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
    public Date date;
    /** Record the index of this Commit*/
    private String ind;
    /** Record the information of bolbs in the hashmap*/
    private hashing hashmap;


    public Commit( String message, Commit parent) throws IOException {
        this.message = message;
        this.parent = parent;
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
            hashmap = new hashing();
        }

        writeObject(commit,this);
        String content = readContentsAsString(commit);
        this.sha1 = sha1(content);
        writeObject(commit, this);
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

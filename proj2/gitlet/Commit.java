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
        } else {
            date = new Date();
        }

        writeObject(commit,this);
        String content = readContentsAsString(commit);
        this.sha1 = sha1(content);
        writeObject(commit, this);
    }

    /**Create a private resizeable hashing map */
    private class hashing {
        public String[] hashlist;
        private int size;
        private int num;
        /** create a hashing list*/
        public hashing() {
            hashlist = new String[4];
            size = 4;
            num = 0;
            for (int i = 0; i<size ; i++) {
                hashlist[i] = null;
            }
        }

        /**insert a specific hashcode into hashlist */
        public void add(String SHA1) {
            int index = SHA1.hashCode() % size;
            if (hashlist[index] == null) {
                hashlist[index] = SHA1;
            }
            num ++;
        }

        /** Resize the hashlist if num/size >= 1.5 */

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

package gitlet;

import java.io.File;
import java.io.Serializable;

import static gitlet.Repository.BLOBS_DIR;
import static gitlet.Utils.*;

import static gitlet.Utils.readContents;

public class Blob implements Serializable {

    /** The filename of this Blob */
    private String filename;
    /** The contents of this Blob */
    private String contents;


    /**Create a new bolb */
    public Blob(File file) {
        this.filename = file.getName();
        this.contents = readContentsAsString(file);
    }



    /** Return SHA1 of this blob */
    public String blobSHA1() {
        File f = new File(BLOBS_DIR, "temp");
        writeObject(f, this);
        String sha1 = sha1(readContents(f));
        f.delete();
        return sha1;
    }

    /** Save the Blob into file with the name of its SHA1 */
    public void saveBlob(File path) {
        //get the sha1 of this commit
        String SHA1 = blobSHA1();

        File blobFile = new File(path, SHA1);
        writeObject(blobFile, this);
    }

    /** Read Blob from file named sha1 */
    public static Blob readBlob(String sha1) {
        File f = join(BLOBS_DIR, sha1);
        return readObject(f, Blob.class);
    }

    public  String filename() {
        return this.filename;
    }

    public String contents() {
        return this.contents;
    }


}

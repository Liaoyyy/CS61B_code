package gitlet;

import java.io.File;
import java.io.Serializable;

import static gitlet.Repository.*;
import static gitlet.Utils.*;

public class Bolb implements Serializable{
    private String ind;
    private String blobname;
    private String contents;
    private String filename;



    /**Create a new bolb */
    public Bolb(File file) {
        filename = file.getName();
        //read the index of this Bolb
        ind = readContentsAsString(numOfBolbs);
        Integer index=Integer.parseInt(ind)+1;
        writeContents(numOfBolbs,index.toString());

        blobname = "Blob" + ind + ".txt";
        File bolb = createFile(BOLBS_DIR, blobname);
        contents = readContentsAsString(file);
        writeObject(bolb, this);
    }

    /** Return sha1*/
    public String SHA1() {
        return sha1(contents);
    }

    /** return the blobname*/
    public String bolbname() {return blobname;}
}

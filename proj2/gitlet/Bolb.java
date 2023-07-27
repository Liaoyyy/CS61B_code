package gitlet;

import java.io.File;
import java.io.Serializable;

import static gitlet.Repository.*;
import static gitlet.Utils.*;

public class Bolb implements Serializable{
    private String ind;
    private String blobname;
    private String contents;
    private String sha1;



    /**Create a new bolb */
    public Bolb(File file) {
        //read the index of this Bolb
        ind = readContentsAsString(numOfBolbs);
        Integer index=Integer.parseInt(ind)+1;
        writeContents(numOfCommits,index.toString());

        blobname = "Blob" + ind + ".txt";
        File bolb = createFile(BOLBS_DIR, blobname);
        contents = readContentsAsString(file);
        sha1 = sha1(contents);
        writeObject(bolb, this);
    }


}

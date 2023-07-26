package gitlet;

import java.io.File;
import static gitlet.Repository.*;

public class BolbMap {
    public BolbMap left;
    public BolbMap right;
    public String SHA;


    /**Create a new bolb */
    public BolbMap(BolbMap left, BolbMap right, String SHA) {
        File bolb = createFile(BOLBS_DIR, "bolb.txt");
        this.left = left;
        this.right = right;
        this.SHA = SHA;
    }


}

package gitlet;

import java.io.File;
import static gitlet.Repository.*;

public class Bolb {



    /**Create a new bolb */
    public Bolb(String SHA) {
        File bolb = createFile(BOLBS_DIR, "bolb.txt");
    }


}

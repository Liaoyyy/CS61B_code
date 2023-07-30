package gitlet;

import java.io.Serializable;
import java.util.HashMap;

public class Stage implements Serializable {
    /** The hashmap records the list of blobs to add/remove */
    private HashMap<String, String> hashmap;

    public Stage() {
        this.hashmap = new HashMap<>();
    }


}

package gitlet;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Stage implements Serializable {
    /** The hashmap records the list of blobs to add/remove */
    private HashMap<String, String> hashmap;

    public Stage() {
        this.hashmap = new HashMap<>();
    }

    public boolean checkBlob(String filename) {
        return hashmap.containsKey(filename);
    }

    public String getSHA1(String filename) {
        return hashmap.get(filename);
    }

    /** Put a blob information into the hashmap */
    public void putBlob(String filename, String sha1) {
        hashmap.put(filename, sha1);
    }

    /** Remove a blob information form the hashmap */
    public void rmBlob(String filename) {
        hashmap.remove(filename);
    }

    /** Return true if hashmap is Empty */
    public boolean isEmpty() {
        return hashmap.isEmpty();
    }

    /** Return the entries set of hashmap */
    public Set<Map.Entry<String, String>> set() {
        return hashmap.entrySet();

    }

    /** Return the Key set in hashmap */
    public Set<String> keySet() {
        return hashmap.keySet();
    }

}

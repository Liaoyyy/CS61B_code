package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private int size;
    private BSTNode root;
    private class BSTNode {
        public K key;
        public V value;
        public BSTNode left;
        public BSTNode right;
        public BSTNode(K k,V v) {
            key = k;
            value = v;
            left = null;
            right = null;
        }
    }

    public BSTMap() {
        size=0;
        root = new BSTNode(null,null);
    }
    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = new BSTNode(null,null);
        size = 0;
    }

    /** Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        if (size == 0) {
            return false;
        }
        BSTNode result = search(root, key);
        return result != null ;
    }

    /**search for the key from BSTNode BN */
    private BSTNode search(BSTNode BN, K key) {
        if (BN == null) {
            return null;
        }

        if (key.compareTo(BN.key) > 0) {
            return search(BN.right, key);
        } else if (key.compareTo(BN.key) == 0) {
            return BN;
        } else {
            return search(BN.left ,key);
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (size == 0) {
            return null;
        }
        BSTNode result = search(root, key);
        if (result == null) {
            return null;
        } else {
            return result.value;
        }
    }


    /** Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    /** Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (size == 0) {
            root.key = key;
            root.value = value;
            size = 1;
        } else{
            put(root, key, value);
        }


    }

    private BSTNode put(BSTNode searchStart, K key, V value) {
        if (searchStart == null) {
            BSTNode newBranch = new BSTNode(key, value);
            size += 1;
            return newBranch;
        }
        int temp = key.compareTo(searchStart.key);
        if (key.compareTo(searchStart.key) > 0) {
            searchStart.right=put(searchStart.right, key, value);
        } else if (key.compareTo(searchStart.key) < 0) {
            searchStart.left=put(searchStart.left, key, value);
        } else if (key.compareTo(searchStart.key) == 0) {
            return null;
        }
        return searchStart;

    }






    /** Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException. */
    @Override
    public Set<K> keySet() {
        return null;
    }

    /** Removes the mapping for the specified key from this map if present.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        return null;
    }


    /** Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 7. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        return null;
    }

    @Override
    public Iterator iterator( ){
        return null;
    }
}

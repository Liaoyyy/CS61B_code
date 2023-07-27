package hashmap;

import org.junit.Test;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {



    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int size;
    private double loadFactor;
    private int num;//records the num of item in the buckets
    public HashSet<K> keyset;
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        size = 16;
        num = 0;
        loadFactor = 0.75;
        buckets = createTable(size);
        keyset = new HashSet<>();
    }

    public MyHashMap(int initialSize) {
        size = initialSize;
        num = 0;
        loadFactor = 0.75;
        buckets = createTable(size);
        keyset = new HashSet<>();
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        size = initialSize;
        loadFactor = maxLoad;
        num = 0;
        buckets = createTable(size);
        keyset = new HashSet<>();
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        Node n = new Node(key,value);
        return n;
    }




    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] B = new Collection[tableSize];
        for (int i=0; i < size; i++) {
            B[i] = createBucket();
        }
        return B;
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        for (int i=0; i < size; i++) {
            buckets[i] = createBucket();
        }
        num = 0;
    }

    /** Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        if (num == 0) return false;
        int index = Math.floorMod(key.hashCode(), size);
        for (Node i: buckets[index]){
            if(key.equals(i.key)){
                return true;
            }
        }
        return false;
    }


    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (num == 0) return null;
        int index = Math.floorMod(key.hashCode(), size);
        for (Node i: buckets[index]){
            if(key.equals(i.key)){
                return i.value;
            }
        }
        return null;
    }

    /** Returns the number of key-value mappings in this map. */
    @Override
    public int size(){
        return num;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    @Override
    public void put(K key, V value) {
        int index = Math.floorMod(key.hashCode(), size);
        if (containsKey(key)) {
            for (Node i: buckets[index]) {
                if(key.equals(i.key)) {
                    i.value = value;
                }
            }
        } else {
            buckets[index].add(createNode(key, value));
            keyset.add(key);
            num += 1;
        }

        if (num / size >= loadFactor) {
            buckets = resize(size * 2);
        }
    }

    /**Resize the hashmap */
    private Collection<Node>[] resize(int new_size) {
        MyHashMap<K,V> newHashMap = new MyHashMap<>(new_size,loadFactor);
        for (K key: keyset) {
            V value = get(key);
            newHashMap.put(key, value);
        }
        size *= 2;
        return newHashMap.buckets;
    }

    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        return keyset;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public V remove(K key){
        if (num == 0) return null;
        Node result = null;
        int index = Math.floorMod(key.hashCode(), size);
        for (Node i: buckets[index]) {
            if (key.equals(i.key)) {
                result = i;
                buckets[index].remove(i);
            }
        }
        return result.value;
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    @Override
    public V remove(K key, V value){
        if (num == 0) return null;
        Node result = null;
        int index = Math.floorMod(key.hashCode(), size);
        for (Node i: buckets[index]) {
            if (key.equals(i.key)) {
                result = i;
                buckets[index].remove(i);
            }
        }
        return result.value;
    }

    @Override
    public Iterator<K> iterator() {
        return keyset.iterator();
    }

}

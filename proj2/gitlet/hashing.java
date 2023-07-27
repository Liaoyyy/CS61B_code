package gitlet;
/**Create a private resizeable hashing map */
public class hashing {
    public LinkedListDeque<String>[] hashlist;
    private int size;
    private int num;
    /** create a hashing list and each item in hashing list is a LinkedListDeque*/
    public hashing() {
        hashlist = new LinkedListDeque[127];
        size = 127;
        num = 0;
        for (int i = 0; i<size ; i++) {
            hashlist[i] = null;
        }
    }

    /**insert a specific hashcode into hashlist */
    public void add(String SHA1) {
        int index = SHA1.hashCode() % size;
        if (hashlist[index] == null) {
            LinkedListDeque<String> lklist = new LinkedListDeque<>();
            hashlist[index] = lklist;
            lklist.addLast(SHA1);
        } else {
            hashlist[index].addLast(SHA1);
        }
        num ++;
        if (num / size >=1.5) {
            resize();
        }
    }

    /** Resize the hashlist if num/size >= 1.5 */
    public void resize() {
        LinkedListDeque<String>[] newhashlist = new LinkedListDeque[size * 2];
        for (int i = 0; i < (size * 2); i++) {
            newhashlist[i] = null;
        }
        copy(newhashlist);
        size *= 2;
        hashlist = newhashlist;
    }

    /** Check whether SHA1 is existed in hashlist */
    public boolean find(String SHA1) {
        int index = SHA1.hashCode() % size;
        if (hashlist[index] == null) {
            return false;
        }
        LinkedListDeque<String> lklist = hashlist[index];
        return lklist.search(SHA1);
    }


    /**copy all items from hashlist to newhashlist*/
    private void copy(LinkedListDeque[] newhashlist) {
        for (int i = 0; i < size; i++) {
            while (hashlist[i] != null) {
                String temp = hashlist[i].get(0);
                hashlist[i].removeFirst();
                int index = temp.hashCode() % (size*2);
                if (newhashlist[index] == null) {
                    LinkedListDeque<String> lklist = new LinkedListDeque<>();
                    lklist.addFirst(temp);
                    newhashlist[index] = lklist;
                }
                if (hashlist[i].isEmpty()) {
                    hashlist[i] = null;
                }
            }
        }

    }

    public static void main(String[] args) {
        hashing test = new hashing();
        test.add("111");
        test.add("222");
        test.add("111");

    }

}
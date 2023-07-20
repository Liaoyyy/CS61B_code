package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {

    private Comparator<T> cd;
    public MaxArrayDeque(Comparator<T> c) {
        cd = c;
    }

    public T max(Comparator<T> c) {
        int length = super.size();
        T curMax = super.get(0);
        for (int i = 1; i < length; i++) {
            if (c.compare(curMax, super.get(i)) < 0) {
                curMax = super.get(i);
            }
        }
        return curMax;
    }

    public T max() {
        if (super.isEmpty()) {
            return null;
        }
        T result = max(cd);
        return result;
    }


    /*public static class ArrayComparator<T> implements Comparator<T> {
        public int compare(T item1, T item2) {
            if( (int)item1 > (int)item2 ){
                return 1;
            }
            else if((int)item1 == (int)item2){
                return 0;
            }
            return -1;
        }

    }

    public static void main(String[] args){
        ArrayComparator<Integer> cd=new ArrayComparator<>();
        MaxArrayDeque<Integer> test=new MaxArrayDeque<>(cd);
        test.addFirst(1);
        test.addFirst(0);
        test.addFirst(19);
        test.addFirst(7);

        System.out.println(test.max());

    }*/
}

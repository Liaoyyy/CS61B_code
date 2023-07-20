package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> implements Comparable<T> {
    @Override
    public int compareTo(T o) {
        return 0;
    }

    public static class ArrayComparator<T> implements Comparator<T> {
        public int compare(T item1, T item2) {
            return -1;
        }

    }
    public MaxArrayDeque(Comparator<T> c){

    }
}

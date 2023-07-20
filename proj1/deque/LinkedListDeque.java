package deque;
import java.util.Iterator;
public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private int size;
    private StuffNode sentinelFront;
    private StuffNode sentinelBack;

    private class StuffNode<T> {
        private T item;
        private StuffNode next;
        private StuffNode prev;
        StuffNode(T i, StuffNode nodeNext, StuffNode nodePrev) {
            item = i;
            next = nodeNext;
            prev = nodePrev;
        }
    }

    public LinkedListDeque() {
        sentinelFront = new StuffNode(null, null, null);
        sentinelBack = new StuffNode(null, null, sentinelFront);
        sentinelFront.next = sentinelBack;
        size = 0;
    }

    @Override
    public void addFirst(T item) {
        if (item == null) {
            return;
        }
        StuffNode temp = sentinelFront.next;
        sentinelFront.next = new StuffNode(item, temp, sentinelFront);
        temp.prev = sentinelFront.next;
        size += 1;
    }

    @Override
    public void addLast(T item) {
        if (item == null) {
            return;
        }
        StuffNode temp = sentinelBack.prev;
        sentinelBack.prev = new StuffNode(item, sentinelBack, temp);
        temp.next = sentinelBack.prev;
        size += 1;

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size - 1; i++) {
            sb.append(get(i));
            sb.append(" ");
        }
        sb.append(get(size - 1));
        System.out.print(sb.toString());
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T first;
        first = (T) sentinelFront.next.item;
        sentinelFront.next = sentinelFront.next.next;
        sentinelFront.next.prev = sentinelFront;
        size -= 1;
        return first;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T last;
        last = (T) sentinelBack.prev.item;
        sentinelBack.prev = sentinelBack.prev.prev;
        sentinelBack.prev.next = sentinelBack;
        size -= 1;
        return last;
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        T getitem;
        StuffNode temp = sentinelFront.next;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        getitem = (T) temp.item;
        return getitem;
    }

    public T getRecursive(int index) {
        if (size == 0) {
            return null;
        }
        if (index >= size || index < 0) {
            return null;
        }
        StuffNode<T> curPos = sentinelFront.next;
        T result = recursive(index, curPos);
        return result;
    }

    private T recursive(int index, StuffNode<T> CurPos) {
        if (index == 0) {
            return (T) CurPos.item;
        }
        return (T) recursive(index - 1, CurPos.next);
    }
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<T> {
        private int curPos;

        LinkedListIterator()  {
            curPos = 0;
        }
        @Override
        public boolean hasNext() {
            if (get(curPos) != null) {
                return true;
            }
            return false;
        }

        @Override
        public T next() {
            T temp;
            temp = get(curPos);
            curPos += 1;
            return temp;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof Deque)) {
            return false;
        }

        Deque<T> other = (Deque<T>)  o;
        if (this.size() != other.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (this.get(i) != other.get(i)) {
                return false;
            }
        }
        return true;
    }
}

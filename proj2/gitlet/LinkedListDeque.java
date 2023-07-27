package gitlet;

public class LinkedListDeque<T> {
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

    public void addFirst(T item) {
        if (item == null) {
            return;
        }
        StuffNode temp = sentinelFront.next;
        sentinelFront.next = new StuffNode(item, temp, sentinelFront);
        temp.prev = sentinelFront.next;
        size += 1;
    }

    public void addLast(T item) {
        if (item == null) {
            return;
        }
        StuffNode temp = sentinelBack.prev;
        sentinelBack.prev = new StuffNode(item, sentinelBack, temp);
        temp.next = sentinelBack.prev;
        size += 1;

    }

    public int size() {
        return size;
    }

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

    public boolean isEmpty() {
        return size == 0;
    }
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

    private T recursive(int index, StuffNode<T> curPos) {
        if (index == 0) {
            return (T) curPos.item;
        }
        return (T) recursive(index - 1, curPos.next);
    }


    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof LinkedListDeque)) {
            return false;
        }

        LinkedListDeque<T> other = (LinkedListDeque<T>)  o;
        if (this.size() != other.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!this.get(i).equals(other.get(i))) {
                return false;
            }
        }
        return true;
    }

    /** Search for the item<T> in the LinkedList*/
    public boolean search(T item) {
        for (int i = 0; i < size; i++) {
            if(get(i).equals(item)) {
                return true;
            }
        }
        return false;
    }
}

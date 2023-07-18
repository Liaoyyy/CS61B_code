package deque;
import java.util.Iterator;
public class LinkedListDeque<T> implements Deque<T>,Iterable<T>{
    private int size;
    private StuffNode SentinelFront;
    private StuffNode SentinelBack;

    private class StuffNode<T>{
        public T item;
        public StuffNode next;
        public StuffNode prev;
        public StuffNode(T i, StuffNode N_next,StuffNode N_prev){
            item=i;
            next=N_next;
            prev=N_prev;
        }
    }

    public LinkedListDeque(){
        SentinelFront=new StuffNode(null,null,null);
        SentinelBack=new StuffNode(null,null,SentinelFront);
        SentinelFront.next=SentinelBack;
        size=0;
    }

    @Override
    public void addFirst(T item){
        if(item==null) return;
        StuffNode temp=SentinelFront.next;
        SentinelFront.next=new StuffNode(item,temp,SentinelFront);
        temp.prev=SentinelFront.next;
        size+=1;
    }

    @Override
    public void addLast(T item){
        if(item==null) return;
        StuffNode temp=SentinelBack.prev;
        SentinelBack.prev=new StuffNode(item,SentinelBack,temp);
        temp.next=SentinelBack.prev;
        size+=1;

    }

    @Override
    public boolean isEmpty(){
        return size==0;
    }

    @Override
    public int size(){return size;}

    @Override
    public void printDeque(){
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<size-1;i++){
            sb.append(get(i));
            sb.append(" ");
        }
        sb.append(get(size-1));
        System.out.print(sb.toString());
    }

    @Override
    public T removeFirst(){
        if(isEmpty()) return null;
        T first;
        first=(T) SentinelFront.next.item;
        SentinelFront.next=SentinelFront.next.next;
        SentinelFront.next.prev=SentinelFront;
        size-=1;
        return first;
    }

    @Override
    public T removeLast(){
        if(isEmpty()) return null;
        T last;
        last=(T) SentinelBack.prev.item;
        SentinelBack.prev=SentinelBack.prev.prev;
        SentinelBack.prev.next=SentinelBack;
        size-=1;
        return last;
    }

    @Override
    public T get(int index){
        if(index>=size) return null;
        T getitem;
        StuffNode temp=SentinelFront.next;
        for(int i=0;i<index;i++){
            temp=temp.next;
        }
        getitem=(T) temp.item;
        return getitem;
    }

    /*public T getRecursive(int index){
        if(index==0) return
        return
    }*/

    public Iterator<T> iterator(){
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<T>{
        private int CurPos;

        public LinkedListIterator()  {CurPos=0;}
        @Override
        public boolean hasNext(){
            if(get(CurPos)!=null) return true;
            return false;
        }

        @Override
        public T next(){
            T temp;
            temp=get(CurPos);
            CurPos+=1;
            return temp;
        }

    }

    @Override
    public boolean equals(Object o){
        if(this==o) return true;
        if(o==null) return false;

        Deque<T> other=(Deque<T>)  o;
        if(this.size()!=other.size()) return false;
        for(int i=0;i<size;i++){
            if(this.get(i)!=other.get(i)) return false;
        }
        return true;
        }


    public static void main(String[] args){
        LinkedListDeque<Integer> test=new LinkedListDeque<>();
        test.addFirst(1);
        test.addFirst(5);
        test.addLast(3);
        test.addLast(2);
        test.removeFirst();
        test.removeFirst();
        test.removeFirst();
        test.removeLast();
        test.addFirst(1);
        test.addFirst(2);
        test.addFirst(3);
        test.addFirst(4);
        System.out.println(test.get(0));
        System.out.println(test.get(1));
    }
}
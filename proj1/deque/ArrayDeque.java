package deque;
import java.util.Iterator;
public class ArrayDeque<T> implements Deque<T>,Iterable<T>{
    private int size;

    /*pos records the postion of the first element backward*/
    private int posback;
    /*pos records the postion of the last element foreward*/
    private int posfore;
    private int totallength;
    private T[] array;

    public ArrayDeque(){
        array=(T[]) new Object[8];
        size=0;
        posback=7;
        posfore=0;
        totallength=8;
    }

    public void resizing(int capacity){
        T[] temp=(T[]) new Object[capacity];
        for(int i=0;i<posfore;i++){
            temp[i]=array[i];
        }
        int j=capacity-1;
        for(int i=totallength-1;i>posback;i--,j--){
            temp[j]=array[i];
        }
        posback=j;
        totallength=capacity;
        array=temp;
    }

    @Override
    public void addFirst(T item){
        if(item==null) return;
        if(size==totallength){
            resizing((int)(size*1.2));
        }
        array[posback]=item;
        posback-=1;
        size+=1;
    }

    @Override
    public void addLast(T item){
        if(item==null) return;
        if(size==totallength){
            resizing((int)(size*1.2));
        }
        array[posfore]=item;
        posfore+=1;
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
            sb.append(' ');
        }
        sb.append(get(size-1));
        System.out.println(sb.toString());
        System.out.println();

    }


    @Override
    public T removeFirst(){
        if(isEmpty()) return null;
        T first;
        first=array[posback];
        array[posback]=null;
        size-=1;
        posback+=1;
        if((size<totallength/4) && (totallength>=16)){
            resizing(totallength/4);
        }
        return first;
    }

    @Override
    public T removeLast(){
        if(isEmpty()) return null;
        T last;
        last=array[posfore];
        array[posfore]=null;
        size-=1;
        posfore-=1;
        if((size<totallength/4) && (totallength>=16)){
            resizing(totallength/4);
        }
        return last;
    }

    @Override
    public T get(int index){
        if((index>=0) && (index<totallength-posback-1)){
            return array[index+posback+1];
        }else if((index>=totallength-posback-1) && (index<size)){
            return array[index-(totallength-posback-1)];
        }
        return null;
    }

    public Iterator<T> iterator(){
        return new ArrayIterator();
    }

    @Override
    public boolean equals(Object o){
        if(this==o) return true;
        if(o==null) return false;
        if(this.getClass()!=o.getClass()) return false;

        ArrayDeque<T> other=(ArrayDeque<T>) o;
        if(this.size()!=other.size()) return false;
        for(int i=0;i<size;i++){
            if(this.get(i)!=other.get(i)) return false;
        }
        return true;
    }


    private class ArrayIterator implements Iterator<T>{
        private int CurPos;
        public ArrayIterator(){
            CurPos=0;
        }
        @Override
        public boolean hasNext(){
            if(get(CurPos)!=null) return true;
            return false;
        }

        @Override
        public T next(){
            T returnItem=get(CurPos);
            CurPos+=1;
            return returnItem;
        }
    }
}
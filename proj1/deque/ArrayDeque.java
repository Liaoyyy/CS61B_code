package deque;
public class ArrayDeque<T> implements Deque<T>{
    private int size;

    /*pos records the postion of the first element backward*/
    private int posback;
    /*pos records the postion of the last element foreward*/
    private int posfore;
    private int totallength;
    private T[] array;

    public void ArrayDeque(){
        array=(T[]) new Object[8];
        size=0;
        posback=7;
        posfore=0;
        totallength=8;
    }

    @Override
    public void addFirst(T item){
        array[posback]=item;
        posback-=1;
        size+=1;
    }

    @Override
    public void addLast(T item){
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
    public void printDeque(){}

    @Override
    public T removeFirst(){
        T first;
        first=array[posback];
        array[posback]=null;
        size-=1;
        posback+=1;
        return first;
    }

    @Override
    public T removeLast(){
        T last;
        last=array[posfore];
        array[posfore]=null;
        size-=1;
        posfore-=1;
        return last;
    }

    @Override
    public T get(int index){
        if((index>=0) && (index<totallength-posback)){
            return array[index+posback];
        }else if((index>totallength-posback) && (index<size)){
            return array[index-(totallength-posback+1)];
        }
        return null;
    }

    public void main(String arg[]){
        ArrayDeque<Integer> test=new ArrayDeque<>();
        test.addLast(5);
        test.addLast(3);
        test.addLast(7);
        test.addFirst(2);
        test.addFirst(0);
        test.addFirst(1);
        for(int i=0;i<6;i++){
            System.out.println(test.get(i));
        }


    }

}
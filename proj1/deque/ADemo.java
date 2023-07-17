package deque;
import static org.junit.Assert.*;
import org.junit.Test;

public class ADemo {
    @Test
    public  void test1(){
        ArrayDeque<Integer> test=new ArrayDeque<>();



        for(int i=0;i<1000;i++){
            if(i%2 ==0){
                test.addFirst(i);
            }else{
                test.addLast(i);
            }
        }

        for(int i=0;i<1000;i++){
            test.removeFirst();
            }


        //test.printDeque();
        for (Integer i  :test) {
            System.out.println(i);
        }
        //System.out.println(test.equals(test));
    }


    @Test
    public void test2(){
        ArrayDeque<Integer> test=new ArrayDeque<>();
        test.addFirst(1);
        test.addFirst(5);
        test.addLast(3);
        test.addLast(2);
        test.addLast(1);
        test.addLast(2);
        test.addFirst(3);
        test.addFirst(4);
        test.removeFirst();
        test.removeFirst();
        test.removeFirst();
        test.removeFirst();
        test.removeFirst();
        test.removeFirst();
        test.removeFirst();
        test.removeFirst();
        int k=test.removeFirst();
        test.addFirst(1);
        test.addFirst(2);
        test.addFirst(3);
        test.addFirst(4);
        test.addFirst(1);
        test.addFirst(2);
        test.addFirst(3);
        test.addFirst(4);
    }

    @Test
    public void test3(){

    }
}

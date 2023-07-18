package deque;
import static org.junit.Assert.*;
import org.junit.Test;
import java.lang.*;

public class ADemo {
    @Test
    public  void test1(){
        ArrayDeque<Integer> test=new ArrayDeque<>();



        for(int i=0;i<1000;i++){
            test.addFirst(i);
        }


        for(int i=0;i<1000;i++){
            test.removeFirst();
        }


        //test.printDeque();


        for(int i=0;i<1000;i++){
            test.addFirst(i);
        }

        //System.out.println(test.equals(test));
    }


    @Test
    public void test2(){
        ArrayDeque<Integer> test=new ArrayDeque<>();
        for(int i=0;i<100;i++){
            test.addFirst(99-i);
        }


        ArrayDeque<Integer> test2=new ArrayDeque<>();
        for(int i=0;i<100;i++){
            test2.addLast(i);
        }

        /*LinkedListDeque<Integer> test3=new LinkedListDeque<>();
        test3.addLast(1);
        test3.addLast(2);
        test3.addLast(3);
        test3.addLast(4);*/

        assertTrue("",test.equals(test2));
    }

    @Test
    public void test3(){
        ArrayDeque<Integer[]> test=new ArrayDeque<>();
        Integer[] item=new Integer[]{1,2,3};
        test.addFirst(item);
        test.addFirst(item);
        test.addFirst(item);

        ArrayDeque<Integer[]> test2=new ArrayDeque<>();
        Integer[] item2=new Integer[]{1,2,3};
        test2.addFirst(item2);
        test2.addFirst(item2);
        test2.addFirst(item2);

        System.out.print(test.equals(test2));

    }
}

package deque;
import static org.junit.Assert.*;
import org.junit.Test;

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
        test.addFirst(3);
        test.addFirst(2);
        test.addFirst(1);
        test.addLast(4);


        ArrayDeque<Integer> test2=new ArrayDeque<>();
        test2.addLast(1);
        test2.addLast(2);
        test2.addLast(3);
        test2.addLast(4);

        LinkedListDeque<Integer> test3=new LinkedListDeque<>();
        test3.addLast(1);
        test3.addLast(2);
        test3.addLast(3);
        test3.addLast(4);

        assertTrue("",test.equals(test3));
    }

    @Test
    public void test3(){

    }
}

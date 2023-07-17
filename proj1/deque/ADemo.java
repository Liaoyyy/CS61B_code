package deque;

import org.junit.Test;

public class ADemo {
    @Test
    public  void test1(){
        ArrayDeque<String> test=new ArrayDeque<>();
        test.addLast("hello");
        test.addLast("today");
        test.addLast("is");
        test.addLast("Monday");
        test.addFirst("nice");
        test.addFirst("Oh");
        test.printDeque();

        /*for(int i=0;i<1000;i++){
            if(i%2 ==0){
                test.addFirst(i);
            }else{
                test.addLast(i);
            }
        }
        for(int i=0;i<1000;i++){
            if(i%2 ==0){
                test.removeFirst();
            }else{
                test.removeLast();
            }
        }*/

        //test.printDeque();
        for (String i : test) {
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
        test.addFirst(1);
        test.addFirst(2);
        test.addFirst(3);
        test.addFirst(4);
        test.removeFirst();
        test.removeFirst();
        test.removeFirst();
        test.removeLast();
        test.addFirst(1);
        test.addFirst(2);
        test.addFirst(3);
        test.addFirst(4);
        test.addFirst(1);
        test.addFirst(2);
        test.addFirst(3);
        test.addFirst(4);
    }
}

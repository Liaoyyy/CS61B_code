package deque;

public class ADemo {
    public static void main(String[] args){
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
}

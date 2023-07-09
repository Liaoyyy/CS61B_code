package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove(){
        AListNoResizing<Integer> correct=new AListNoResizing<>();
        BuggyAList<Integer> test=new BuggyAList<>();
        correct.addLast(5);
        correct.addLast(6);
        correct.addLast(7);
        test.addLast(5);
        test.addLast(6);
        test.addLast(7);

        assertEquals(correct.size(),test.size());

        assertEquals(correct.removeLast(),test.removeLast());
        assertEquals(correct.removeLast(),test.removeLast());
        assertEquals(correct.removeLast(),test.removeLast());

    }

    @Test
    public void randomizedTest(){
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> T=new BuggyAList<>();

        int N = 500;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 3);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                T.addLast(randVal);
            } else if (operationNumber == 1) {
                if(L.size()<=0||T.size()<=0){
                    continue;
                }
                L.removeLast();
                T.removeLast();
            }else if(operationNumber==2){
                if(L.size()<=0||T.size()<=0){
                    continue;
                }
                L.getLast();
                T.getLast();
            }
            assertEquals(L.size(),T.size());
            if(L.size()>0){
                assertEquals(L.getLast(),T.getLast());
            }
        }
    }


}

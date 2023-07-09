package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static AList<Integer> Ns;
    private static AList<Double> times;
    private static AList<Integer> opCounts;

    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        // TODO: YOUR CODE HERE
        Ns=new AList<>();
        times=new AList<>();
        opCounts=new AList<>();

        //initial Ns
        int i;
        int num=1000;
        for(i=0;i<8;i++){
            Ns.addLast(num);
            num=num*2;
        }

        for(i=0;i<8;i++){
            //create a SLList
            SLList<Integer> temp=new SLList<Integer>();
            int j;
            for(j=0;j<Ns.get(i);j++){
                temp.addLast(j);
            }

            //start the timer
            Stopwatch sw=new Stopwatch();

            //perform getLast operations for M times
            int M=10000;
            opCounts.addLast(M);
            for(j=0;j<M;j++){
                int flag=temp.getLast();
            }
            double timeInSeconds=sw.elapsedTime();
            times.addLast(timeInSeconds);
        }
        printTimingTable(Ns,times,opCounts);
    }

}

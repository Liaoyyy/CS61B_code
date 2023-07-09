package timingtest;
import edu.princeton.cs.algs4.Stopwatch;
import org.antlr.v4.runtime.misc.IntegerList;

/**
 * Created by hug.
 */
public class TimeAList {
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
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        // TODO: YOUR CODE HERE
        Ns=new AList<>();
        times=new AList<>();
        opCounts=new AList<>();
        int num=1000;
        int i;
        for(i=0;i<8;i++){
            Ns.addLast(num);
            num=num*2;
        }
        for(i=0;i<8;i++){
            int j=0;
            Stopwatch sw=new Stopwatch();
            AList<Integer> temp=new AList<Integer>();
            for(j=0;j<Ns.get(i);j++){
                temp.addLast(j);
            }
            double timeInSeconds = sw.elapsedTime();
            times.addLast(timeInSeconds);
        }
        opCounts=Ns;
        printTimingTable(Ns,times,opCounts);
    }
}

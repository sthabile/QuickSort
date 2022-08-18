import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class main
{
    public static final int ARR_SIZE = 1000000;
    public static final int MAX_NUM = 100;
    public static final int RUN_NUM = 10;

    public static void main(String[] args)
    {
        ForkJoinPool pool = new ForkJoinPool();
        Random random  = new Random();
        Integer[] arrParallel = new Integer[ARR_SIZE];
        Integer[] arrSequential = new Integer[ARR_SIZE];

        for(int i = 0; i< ARR_SIZE;i++)
        {
            int num = random.nextInt(MAX_NUM);
            arrParallel[i] = num;
            arrSequential[i] = num;
        }

        ParallelSort parallelSort;
        int avgTimeParallel = 0;
        int avgTimeSequence = 0;
        int i =0;
        while(i<=RUN_NUM)
        {
            parallelSort = new ParallelSort(arrParallel,0,ARR_SIZE-1);

            long startTimeParallel = System.currentTimeMillis();
            pool.invoke(parallelSort);
            long endTimeParallel = System.currentTimeMillis();

            long startTimeSequential = System.currentTimeMillis();
            Sort.quickSort(arrSequential);
            long endTimeSequential = System.currentTimeMillis();

            avgTimeParallel += (endTimeParallel - startTimeParallel);
            avgTimeSequence += (endTimeSequential - startTimeSequential);

            i++;
        }

        System.out.println("\n=================== Average of 10 runs  ==============");
        System.out.println("ParallelSort took : " + (avgTimeParallel/RUN_NUM) + " milliseconds.");
        System.out.println("QuickSort took : " + (avgTimeSequence/RUN_NUM) + " milliseconds.");
    }
}

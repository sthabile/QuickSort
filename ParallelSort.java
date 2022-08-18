import java.util.concurrent.RecursiveAction;

public class ParallelSort extends RecursiveAction {

    private static final int THRESHOLD = 1000; 
    protected Comparable[] list;
    protected int start;
    protected int end;

    public ParallelSort(Comparable[] list, int start, int end){
        this.list = list;
        this.start = start;
        this.end = end;
    }

    /**
     * Performs the sort directly if the list is small enough,otherwise further split the list into
     * two smaller lists.
     */
    @Override
    protected void compute()
    {
        if(isWorkSmallEnough(start, end))
        {
            recursiveQS(list,start,end);
        }
        else
        {
            int mid = (start + end)/2;
            invokeAll(new ParallelSort(list, start,mid), new ParallelSort(list,mid,end));
        }
    }

    private boolean isWorkSmallEnough(int start, int end){
        return (end-start) < THRESHOLD;
    }

    /** Partition a list between given start and end points, returning the partition
     * point.   The value at <CODE>list[start]</CODE> is used as the partition
     * element.  This method is used by the Quick Sort.
     * <BR><I>Precondition:</I> The list contains data that implements
     *    the <CODE>Comparable</CODE> interface.
     * <BR><I>Postcondition:</I> The items in the list before the partition point are
     *    less than or equal to the partition element, and the items in the list after
     *    the partition point are greater than the partition element.
     * @param list The list of items to be partitioned.
     * @param start The index of the first item in the list to be considered.
     * @param end The index of the last item in the list to be considered.
     * @return The index of the partition point.
     */
    @SuppressWarnings("unchecked")
    private static int partition (Comparable[] list, int start, int end)
    { int left = start,
            right = end;
        Comparable tmp;
        while (left < right)
        { // Work from right end first
            while (list[right].compareTo(list[start]) > 0)
                right--;
            // Now work up from start
            while (left < right && list[left].compareTo(list[start]) <= 0)
                left++;
            if (left < right)
            { tmp = list[left];
                list[left] = list[right];
                list[right] = tmp;
            }
        }
        // Exchange the partition element with list[right]
        tmp = list[start];
        list[start] = list[right];
        list[right] = tmp;
        return right;
    } // partition

    /** Sort a list of items into ascending order using a recursive form of the
     * Quick Sort.
     * <BR><I>Precondition:</I> The list contains data that implements
     *    the <CODE>Comparable</CODE> interface.
     * <BR><I>Postcondition:</I> The list between <CODE>start</CODE> and
     *    <CODE>end</CODE> is in ascending order.
     * @param list The list of items to be sorted.
     * @param start The index of the first item in the list to be considered.
     * @param end The index of the last item in the list to be considered.
     */
    private static void recursiveQS (Comparable[] list, int start, int end)
    {
        if (start < end)
        {
            int partitionPoint = partition(list, start, end);
            recursiveQS(list, start, partitionPoint-1);
            recursiveQS(list, partitionPoint+1, end);
        }
    } // recursiveQS

    /** Sort a list of items into ascending order using a recursive form of the
     * Quick Sort.
     * <BR><I>Precondition:</I> The list contains data that implements
     *    the <CODE>Comparable</CODE> interface.
     * <BR><I>Postcondition:</I> The list is in ascending order.
     * <P>This sort is of order <I>n</I>log <I>n</I>.
     * @param list The list of items to be sorted.
     */
    public static <T extends Comparable<? super T>> void quickSort (T[] list)
    // Quick Sort the list - actually just calls recursiveQS
    { recursiveQS(list, 0, list.length-1);
    } // quickSort

}

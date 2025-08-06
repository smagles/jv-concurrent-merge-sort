package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 4;
    private final int[] array;
    private int start;
    private int end;

    public MergeSortAction(int[] array) {
        this(0, array.length, array);
    }

    private MergeSortAction(int start, int end, int[] array) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start <= THRESHOLD) {
            Arrays.sort(array);
        } else {
            int mid = start + (end - start) / 2;
            MergeSortAction left = new MergeSortAction(start, mid, array);
            MergeSortAction right = new MergeSortAction(mid, end, array);
            invokeAll(left, right);
            merge(start, mid, end);
        }
    }

    private void merge(int left, int mid, int right) {
        int[] temp = new int[right - left];

        int k = 0;
        int i = left;
        int j = mid;

        while (i < mid && j < right) {
            if (array[i] < array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }
        while (i < mid) {
            temp[k++] = array[i++];
        }
        while (j < right) {
            temp[k++] = array[j++];
        }
        System.arraycopy(temp, 0, array, left, right - left);
    }
}

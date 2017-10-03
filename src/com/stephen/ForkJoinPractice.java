package com.stephen;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ForkJoinPractice {
    static class SortTask<T extends Comparable<T>> extends RecursiveAction {
        T[] array;
        int start;
        int end;
        int mid;

        public SortTask(T[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
            this.mid = (this.start + this.end) / 2;
        }

        @Override
        protected void compute() {
            int mid;
            if (start < end) {
                mid = (start + end) / 2;
                invokeAll(new SortTask<T>(array, start, mid), new SortTask<T>(array, mid + 1, end));
                merge();
            }
        }

        private void merge() {
            int n1 = mid - start + 1;
            int n2 = end - mid;
            int i, j, k;

            List<T> leftList = new ArrayList<>(n1);
            List<T> rightList = new ArrayList<>(n2);

            for (i = 0; i < n1; i++) {
                leftList.add(array[start + i]);
            }

            for (j = 0; j < n2; j++) {
                rightList.add(array[mid + 1 + j]);
            }

            i = j = 0;
            k = start;

            while (i < n1 && j < n2) {
                if (leftList.get(i).compareTo(rightList.get(j)) < 0) {
                    array[k++] = leftList.get(i++);
                } else {
                    array[k++] = rightList.get(j++);
                }
            }

            while (i < n1) {
                array[k++] = leftList.get(i++);
            }

            while (j < n2) {
                array[k++] = rightList.get(j++);
            }
        }
    }

    public static void main(String[] args) {
        Integer[] array = new Integer[90];
        for (int i = 0; i < 90; i++) {
            array[i] = 90 - i;
        }

        SortTask<Integer> sortTask = new SortTask<>(array, 0, array.length - 1);
        ForkJoinPool pool = new ForkJoinPool();
        pool.submit(sortTask);
        try {
            pool.awaitTermination(3, TimeUnit.SECONDS);
            pool.shutdown();
            for (int i :  array) {
                System.out.println(i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

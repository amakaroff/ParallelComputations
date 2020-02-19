package com.makarov;

import java.util.Arrays;

public class ParallelSort {

    public static void main(String[] args) {
        Integer[] array = {5, 3, 6, 1, 5, 8, 9, 19, 3};
        sort(array);
        System.out.println(Arrays.toString(array));
    }

    public static <T extends Comparable<T>> void sort(T[] array) {
        sort(array, 0, array.length - 1);
    }

    public static <T extends Comparable<T>> void sort(T[] array, int first, int last) {
        sort(array, first, last, 0);
    }

    public static <T extends Comparable<T>> void sort(T[] array, int first, int last, int currentStackLevel) {
        if (first < last) {
            int i = first, j = last;
            T x = array[(i + j) / 2];

            do {
                while (array[i].compareTo(x) < 0) i++;
                while (x.compareTo(array[j]) < 0) j--;

                if (i <= j) {
                    T tmp = array[i];
                    array[i] = array[j];
                    array[j] = tmp;
                    i++;
                    j--;
                }

            } while (i <= j);

            int immutableI = i;
            int immutableJ = j;
            int newStackLevel = currentStackLevel + 1;

            if (currentStackLevel < 2) {
                Thread firstThread = new Thread(() -> sort(array, first, immutableJ, newStackLevel));
                Thread secondThread = new Thread(() -> sort(array, immutableI, last, newStackLevel));
                firstThread.start();
                secondThread.start();
                try {
                    secondThread.join();
                    firstThread.join();
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                }
            } else {
                sort(array, first, j, newStackLevel);
                sort(array, i, last, newStackLevel);
            }
        }
    }


}

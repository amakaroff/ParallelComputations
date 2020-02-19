package com.makarov;

public class MatrixCalculationParallel {

    public static void main(String[] args) {
        double[][] firstMatrix = {
                new double[]{1d, 5d},
                new double[]{2d, 3d},
                new double[]{1d, 7d}
        };

        double[][] secondMatrix = {
                new double[]{1d, 2d, 3d, 7d},
                new double[]{5d, 2d, 8d, 1d}
        };

        printlnMatrix(firstMatrix);
        System.out.println();
        printlnMatrix(secondMatrix);
        System.out.println();

        double[][] newMatrix = multiplyMatrices(firstMatrix, secondMatrix);
        printlnMatrix(newMatrix);
    }

    public static void printlnMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            for (double cell : row) {
                System.out.print(cell + "   ");
            }
            System.out.println();
        }
    }

    public static double[][] multiplyMatrices(double[][] firstMatrix, double[][] secondMatrix) {
        double[][] result = new double[firstMatrix.length][secondMatrix[0].length];

        int parallelSplit = Math.min(4, result.length);

        int countIterations = result.length / parallelSplit;

        Thread[] threads = new Thread[parallelSplit];

        for (int i = 0; i < parallelSplit; i++) {
            int start = i * countIterations;
            int end = i * countIterations + countIterations;

            Thread thread = new Thread(() -> multiplyMatrices(firstMatrix, secondMatrix, result, start, end));
            threads[i] = thread;
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
            }
        }

        return result;
    }

    public static void multiplyMatrices(double[][] firstMatrix, double[][] secondMatrix, double[][] result, int start, int end) {
        for (int row = start; row < end; row++) {
            for (int col = 0; col < result[row].length; col++) {
                result[row][col] = multiplyMatricesCell(firstMatrix, secondMatrix, row, col);
            }
        }
    }

    public static double multiplyMatricesCell(double[][] firstMatrix, double[][] secondMatrix, int row, int col) {
        double cell = 0;

        for (int i = 0; i < secondMatrix.length; i++) {
            cell += firstMatrix[row][i] * secondMatrix[i][col];
        }

        return cell;
    }
}
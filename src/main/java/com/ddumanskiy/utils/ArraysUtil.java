package com.ddumanskiy.utils;

/**
 * User: ddumanskiy
 * Date: 8/13/2014
 * Time: 9:36 AM
 */
public class ArraysUtil {

    public static final int SIZE = 8;

    public static final int[] ZIGZAG = {
            0, 1, 5, 6, 14, 15, 27, 28,
            2, 4, 7, 13, 16, 26, 29, 42,
            3, 8, 12, 17, 25, 30, 41, 43,
            9, 11, 18, 24, 31, 40, 44, 53,
            10, 19, 23, 32, 39, 45, 52, 54,
            20, 22, 33, 38, 46, 51, 55, 60,
            21, 34, 37, 47, 50, 56, 59, 61,
            35, 36, 48, 49, 57, 58, 62, 63
    };

    public static void multiplyMatrix(int a[][], int b[][]) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                 a[i][j] *= b[i][j];
            }
        }
    }

    public static void multiply(int[][] quantumMatrix, int[][] mcu) {
        multiplyMatrix(mcu, quantumMatrix);
    }


    public static void multiply(int[][] quantumMatrix, int[][]... mcus) {
        for (int[][] mcu : mcus) {
            multiplyMatrix(mcu, quantumMatrix);
        }
    }

    public static void fillInZigZagOrder(int[][] existing, int[] data) {
        for (int i = 0; i < ZIGZAG.length; i++) {
            existing[i / SIZE][i % SIZE] = data[ZIGZAG[i]];
        }
    }

    public static int[][] fillInZigZagOrder(byte[] data) {
        if (data.length > SIZE * SIZE) {
            throw new IllegalStateException("Wrong DQT table size.");
        }
        int[][] filledTable = new int[SIZE][SIZE];
        for (int i = 0; i < ZIGZAG.length; i++) {
            filledTable[i / SIZE][i % SIZE] = Byte.toUnsignedInt(data[ZIGZAG[i]]);
        }
        return filledTable;
    }
}

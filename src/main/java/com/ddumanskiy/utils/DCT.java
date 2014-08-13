package com.ddumanskiy.utils;

import java.util.Arrays;

/**
 * User: ddumanskiy
 * Date: 8/13/2014
 * Time: 9:36 AM
 */
public class DCT {

    private static double[][] c;
    private static double[][] temp = new double[ArraysUtil.SIZE][ArraysUtil.SIZE];

    static  {
        c = new double[ArraysUtil.SIZE][ArraysUtil.SIZE];
        temp = new double[ArraysUtil.SIZE][ArraysUtil.SIZE];
        init(c);
    }

    public static void inverseDCT(int[][]... mcus) {
        for (int[][] mcu : mcus) {
            inverseDCT(mcu);
        }
    }

    public static void inverseDCT(int[][] input) {
        double temp1;

        for (int i = 0; i < ArraysUtil.SIZE; i++) {
            for (int j=0; j < ArraysUtil.SIZE; j++) {
                temp[i][j] = 0.0;

                for (int k = 0; k < ArraysUtil.SIZE; k++) {
                    temp[i][j] += input[i][k] * c[k][j];
                }
            }
        }

        for (int i=0; i<ArraysUtil.SIZE; i++) {
            for (int j=0; j<ArraysUtil.SIZE; j++) {
                temp1 = 0.0;

                for (int k=0; k<ArraysUtil.SIZE; k++) {
                    temp1 += c[k][i] * temp[k][j];
                }

                //todo here is point for improvement. don't know how critical it is for quality. visually - no difference
                //input[i][j] = (int) temp1;
                //input[i][j] = (int) Math.round(temp1);
                input[i][j] = (int) (temp1 + 0.5f);

            }
        }
    }

    private static void init(double[][] c) {
        double nn = 1.0 / Math.sqrt((double) ArraysUtil.SIZE);

        Arrays.fill(c[0], nn);

        for (int i = 1; i < ArraysUtil.SIZE; i++) {
            for (int j = 0; j < ArraysUtil.SIZE; j++) {
                double jj = (double)j;
                double ii = (double)i;
                c[i][j]  = Math.sqrt(1.0 / 4.0) * Math.cos(((2.0 * jj + 1.0) * ii * Math.PI) / (16.0));
            }
        }
    }

}

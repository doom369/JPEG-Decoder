package com.ddumanskiy.utils;

/**
 * User: ddumanskiy
 * Date: 8/13/2014
 * Time: 9:36 AM
 */
public class DCT {

    private double[][] c;
    private double[][] temp = new double[ArraysUtil.SIZE][ArraysUtil.SIZE];

    public DCT() {
        c = new double[ArraysUtil.SIZE][ArraysUtil.SIZE];
        temp = new double[ArraysUtil.SIZE][ArraysUtil.SIZE];
        init(c);
    }

    public void inverseDCT(int[][]... mcus) {
        for (int[][] mcu : mcus) {
            inverseDCT(mcu);
        }
    }

    private void inverseDCT(int[][] input) {
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

                input[i][j] = (int) Math.round(temp1);
            }
        }
    }

    private static void init(double[][] c) {
        for (int i = 0; i < ArraysUtil.SIZE; i++) {
            double nn = (double)(ArraysUtil.SIZE);
            c[0][i]  = 1.0 / Math.sqrt(nn);
        }

        for (int i = 1; i < ArraysUtil.SIZE; i++) {
            for (int j = 0; j < ArraysUtil.SIZE; j++) {
                double jj = (double)j;
                double ii = (double)i;
                c[i][j]  = Math.sqrt(2.0/8.0) * Math.cos(((2.0 * jj + 1.0) * ii * Math.PI) / (2.0 * 8.0));
            }
        }
    }

}

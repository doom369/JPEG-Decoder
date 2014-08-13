package com.ddumanskiy;

import com.ddumanskiy.utils.ArraysUtil;

/**
 * User: ddumanskiy
 * Date: 8/13/2014
 * Time: 9:36 AM
 */
public class MCUBlockHolder {

    int[][][] yComponents = new int[4][ArraysUtil.SIZE][ArraysUtil.SIZE];
    int[][] cbComponent = new int[ArraysUtil.SIZE][ArraysUtil.SIZE];
    int[][] crComponent = new int[ArraysUtil.SIZE][ArraysUtil.SIZE];

    private int yDCPrev = 0;
    private int cbDCPrev = 0;
    private int crDCPrev = 0;

    public void add(int componentId, int[] mcu, int counter) {
        if (componentId == 1) {
            yDCPrev = incrDC(mcu, yDCPrev);
            ArraysUtil.fillInZigZagOrder(yComponents[counter], mcu);
        }
        if (componentId == 2) {
            cbDCPrev = incrDC(mcu, cbDCPrev);
            ArraysUtil.fillInZigZagOrder(cbComponent, mcu);
        }
        if (componentId == 3) {
            crDCPrev = incrDC(mcu,crDCPrev);
            ArraysUtil.fillInZigZagOrder(crComponent, mcu);
        }
    }

    private static int incrDC(int[] data, int dcVal) {
        data[0] += dcVal;
        return data[0];
    }

}

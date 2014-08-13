package com.ddumanskiy.segments;

import com.ddumanskiy.utils.ArraysUtil;

/**
 * User: ddumanskiy
 * Date: 8/13/2014
 * Time: 9:36 AM
 */
public class DQTTable {

    private int[][] data;
    
    private int id;
    
    private int sizeOfElement;

    public DQTTable(int size, int id, byte[] data) {
        this.sizeOfElement = size;
        this.id = id;
        this.data = ArraysUtil.fillInZigZagOrder(data);
    }

    public int[][] getData() {
        return data;
    }

    public int getId() {
        return id;
    }

    public int getSizeOfElement() {
        return sizeOfElement;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("DQTTable : \n");
        for (int i = 0; i < ArraysUtil.SIZE; i++) {
            for (int j = 0; j < ArraysUtil.SIZE; j++) {
                sb.append(Integer.toHexString(data[i][j])).append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();

    }
}

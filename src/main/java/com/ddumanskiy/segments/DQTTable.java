package com.ddumanskiy.segments;

/**
 * User: ddumanskiy
 * Date: 8/13/2014
 * Time: 9:36 AM
 */
public class DQTTable {

    private int[] data;
    
    private int id;

    //todo not used right now, but should
    private int sizeOfElement;

    public DQTTable(int size, int id, int[] data) {
        this.sizeOfElement = size;
        this.id = id;
        this.data = data;
    }

    public int[] getData() {
        return data;
    }

    public int getId() {
        return id;
    }

    public int getSizeOfElement() {
        return sizeOfElement;
    }

}

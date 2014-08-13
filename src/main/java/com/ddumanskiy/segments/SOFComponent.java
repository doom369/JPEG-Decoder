package com.ddumanskiy.segments;

/**
 * User: ddumanskiy
 * Date: 8/13/2014
 * Time: 9:36 AM
 */
public class SOFComponent {

    private int id;
    
    private int h;
    
    private int v;

    private int vh;
    
    private int quantumTableId;

    public SOFComponent(int id, int h, int v, int quantumTableId) {
        this.id = id;
        this.h = h;
        this.v = v;
        this.vh = v * h;
        this.quantumTableId = quantumTableId;
    }
      
    public int getId() {
        return id;
    }

    public int getH() {
        return h;
    }

    public int getV() {
        return v;
    }

    public int getVh() {
        return vh;
    }

    public int getQuantumTableId() {
        return quantumTableId;
    }

    @Override
    public String toString() {
        return "SOF0Component [id=" + id + ", h=" + h + ", v=" + v + ", quantumTableId=" + quantumTableId + "]";
    }  
 
    
}

package com.ddumanskiy.segments;

/**
 * User: ddumanskiy
 * Date: 8/13/2014
 * Time: 9:36 AM
 */
public class SOSComponent {

    //1 - Y, 2 - Cb, 3- Cr
    private int id;

    private int dcDhtTableId;

    private int acDhtTableId;

    public SOSComponent(int id, int dcDhtTableId, int acDhtTableId) {
        this.id = id;
        this.dcDhtTableId = dcDhtTableId;
        this.acDhtTableId = acDhtTableId;
    }

    public int getId() {
        return id;
    }

    public int getDcDhtTableId() {
        return dcDhtTableId;
    }

    public int getAcDhtTableId() {
        return acDhtTableId;
    }

    @Override
    public String toString() {
        return "SOSComponent{" +
                "id=" + id +
                ", dcDhtTableId=" + dcDhtTableId +
                ", acDhtTableId=" + acDhtTableId +
                '}';
    }
}

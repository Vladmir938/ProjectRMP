package com.example.scoorent;

public class ParkingModel {

    private String pos1, pos2;

    private ParkingModel() {

    }

    private ParkingModel(String pos1, String pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
    }


    public String getPos1() {
        return pos1;
    }

    public void setPos1(String pos1) {
        this.pos1 = pos1;
    }

    public String getPos2() {
        return pos2;
    }

    public void setPos2(String pos2) {
        this.pos2 = pos2;
    }
}

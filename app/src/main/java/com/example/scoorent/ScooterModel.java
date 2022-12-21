package com.example.scoorent;

public class ScooterModel {
    private String Battery, Model, Speed, pos1, pos2, scooterIMG;

    private ScooterModel() {

    }

    private ScooterModel(String Battery, String Model, String Speed, String pos1, String pos2, String scooterIMG) {
        this.Battery = Battery;
        this.Model = Model;
        this.Speed = Speed;
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.scooterIMG = scooterIMG;
    }


    public String getBattery() {
        return Battery;
    }

    public void setBattery(String Battery) {
        this.Battery = Battery;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String Model) {
        this.Model = Model;
    }

    public String getSpeed() {
        return Speed;
    }

    public void setSpeed(String Speed) {
        this.Speed = Speed;
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

    public String getScooterIMG() {
        return scooterIMG;
    }

    public void setScooterIMG(String scooterIMG) {
        this.scooterIMG = scooterIMG;
    }
}

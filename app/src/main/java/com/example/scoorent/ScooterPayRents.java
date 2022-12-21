package com.example.scoorent;

public class ScooterPayRents {
    private String Scooter, User, Rent, NowIsRent, Date, Time, Hour;

    private ScooterPayRents() {

    }

    private ScooterPayRents(String Scooter, String User, String Rent, String NowIsRent, String Date, String Time, String Hour) {
        this.Scooter = Scooter;
        this.User = User;
        this.Rent = Rent;
        this.NowIsRent = NowIsRent;
        this.Date = Date;
        this.Time = Time;
        this.Hour = Hour;
    }

    public String getScooter() {
        return Scooter;
    }

    public void setScooter(String Scooter) {
        this.Scooter = Scooter;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String User) {
        this.User = User;
    }

    public String getRent() {
        return Rent;
    }

    public void setRent(String Rent) {
        this.Rent = Rent;
    }

    public String getNowIsRent() {
        return NowIsRent;
    }

    public void setNowIsRent(String NowIsRent) {
        this.NowIsRent = NowIsRent;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }

    public String getHour() {
        return Hour;
    }

    public void setHour(String Hour) {
        this.Hour = Hour;
    }
}

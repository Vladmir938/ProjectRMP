package com.example.scoorent;

public class RentModels {

    private String ImgURL, Name, Price, Time;

    private RentModels() {

    }

    private RentModels(String ImgURL, String Name, String Price, String Time) {
        this.ImgURL = ImgURL;
        this.Name = Name;
        this.Price = Price;
        this.Time = Time;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }

    public String getImgURL() {
        return ImgURL;
    }

    public void setImgURL(String imgURL) {
        ImgURL = imgURL;
    }
}

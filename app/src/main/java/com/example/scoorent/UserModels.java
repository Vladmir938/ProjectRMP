package com.example.scoorent;

public class UserModels {
    private String Name, Surname, Patronymic, Email, Phone, ProfileIMG, Balance, id;

    private UserModels() {

    }

    public UserModels(String name, String surname, String patronymic, String email, String phone,
                      String profileIMG, String id, String Balance) {
        this.Name = name;
        this.Surname = surname;
        this.Patronymic = patronymic;
        this.Email = email;
        this.Phone = phone;
        this.ProfileIMG = profileIMG;
        this.Balance = Balance;
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getPatronymic() {
        return Patronymic;
    }

    public void setPatronymic(String patronymic) {
        Patronymic = patronymic;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getProfileIMG() {
        return ProfileIMG;
    }

    public void setProfileIMG(String profileIMG) {
        ProfileIMG = profileIMG;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }
}

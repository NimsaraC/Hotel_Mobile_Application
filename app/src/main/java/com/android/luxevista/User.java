package com.android.luxevista;

import java.time.format.DateTimeFormatter;

public class User {
    private int Id;
    private String Name;
    private String Username;
    private String Email;
    private String Password;
    private String Phone;
    private String Address;
    private String City;
    private String PostalCode;
    private String County;
    private Boolean loginStatus;
    private String ImageUrl;
    private String Gender;
    private String BirthDay;
    public User(String name, String username, String email, String password) {
        Name = name;
        Username = username;
        Email = email;
        Password = password;
    }
    public User(int id, String name, String username, String email, String password, String phone, String address, String city, String postalCode, String county, Boolean loginStatus, String imageUrl, String gender, String birthDay) {
        Id = id;
        Name = name;
        Username = username;
        Email = email;
        Password = password;
        Phone = phone;
        Address = address;
        City = city;
        PostalCode = postalCode;
        County = county;
        this.loginStatus = loginStatus;
        ImageUrl = imageUrl;
        Gender = gender;
        BirthDay = birthDay;
    }

    public User(String name, String username, String email, String password, String phone, String imageUrl, String gender, String birthDay) {
        Name = name;
        Username = username;
        Email = email;
        Password = password;
        Phone = phone;
        ImageUrl = imageUrl;
        Gender = gender;
        BirthDay = birthDay;
    }

    public User(String address, String city, String postalCode, String county, Boolean loginStatus) {
        Address = address;
        City = city;
        PostalCode = postalCode;
        County = county;
        this.loginStatus = loginStatus;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public String getCounty() {
        return County;
    }

    public void setCounty(String county) {
        County = county;
    }

    public Boolean getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Boolean loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getBirthDay() {
        return BirthDay;
    }

    public void setBirthDay(String birthDay) {
        BirthDay = birthDay;
    }
}

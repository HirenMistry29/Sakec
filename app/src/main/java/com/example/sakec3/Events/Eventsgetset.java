package com.example.sakec3.Events;

public class Eventsgetset {
    public Eventsgetset() {
    }

    String Title , description, name1 , name2 ,phn1 ,phn2,reglink , Image , Date , Time , Key;

    public Eventsgetset(String title, String description, String name1, String name2, String phn1, String phn2, String reglink, String image, String date, String time, String key) {
        this.Title = title;
        this.description = description;
        this.name1 = name1;
        this.name2 = name2;
        this.phn1 = phn1;
        this.phn2 = phn2;
        this.reglink = reglink;
        this.Image = image;
        this.Date = date;
        this.Time = time;
        this.Key = key;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getPhn1() {
        return phn1;
    }

    public void setPhn1(String phn1) {
        this.phn1 = phn1;
    }

    public String getPhn2() {
        return phn2;
    }

    public void setPhn2(String phn2) {
        this.phn2 = phn2;
    }

    public String getReglink() {
        return reglink;
    }

    public void setReglink(String reglink) {
        this.reglink = reglink;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }
}

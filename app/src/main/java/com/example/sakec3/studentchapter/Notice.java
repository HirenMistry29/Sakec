package com.example.sakec3.studentchapter;

public class Notice {
    String Title , Image , Date , Time , Key;

    public Notice() {
    }

    public Notice(String title, String image, String date, String time, String key) {
        Title = title;
        Image = image;
        Date = date;
        Time = time;
        Key = key;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
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

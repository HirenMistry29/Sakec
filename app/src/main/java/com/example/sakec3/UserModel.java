package com.example.sakec3;

public class UserModel {
    String full_name , e_mail , pass_word , phone_no , Year;

    public UserModel(String full_name, String e_mail, String pass_word, String phone_no, String Year) {
        this.full_name = full_name;
        this.e_mail = e_mail;
        this.pass_word = pass_word;
        this.phone_no = phone_no;
        this.Year = Year;
    }

    public UserModel() {
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getPass_word() {
        return pass_word;
    }

    public void setPass_word(String pass_word) {
        this.pass_word = pass_word;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }
}

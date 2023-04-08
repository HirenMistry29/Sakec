package com.example.sakec3.studentdetails;

public class StdDetailsgetset {

    String Fname , Mname , Lname , Email , phone , Registration_no , SmartCard_no , div , roll , Year , Branch , Key;

    public StdDetailsgetset(String fname, String mname, String lname, String email, String phone, String registration_no, String smartCard_no, String div, String roll , String year , String branch, String key) {
        Fname = fname;
        Mname = mname;
        Lname = lname;
        Email = email;
        this.phone = phone;
        Registration_no = registration_no;
        SmartCard_no = smartCard_no;
        this.div = div;
        this.roll = roll;
        this.Key = key;
        this.Branch = branch;
        this.Year = year;

    }

    public StdDetailsgetset() {
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getMname() {
        return Mname;
    }

    public void setMname(String mname) {
        Mname = mname;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegistration_no() {
        return Registration_no;
    }

    public void setRegistration_no(String registration_no) {
        Registration_no = registration_no;
    }

    public String getSmartCard_no() {
        return SmartCard_no;
    }

    public void setSmartCard_no(String smartCard_no) {
        SmartCard_no = smartCard_no;
    }

    public String getDiv() {
        return div;
    }

    public void setDiv(String div) {
        this.div = div;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        this.Year = year;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        this.Branch = branch;
    }


    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }
}

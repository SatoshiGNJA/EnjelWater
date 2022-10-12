package com.example.enjelwater.Model;

public class UserHelperClass {

    String name,homeaddress,email,phoneNo;

    public UserHelperClass() {
    }

    public UserHelperClass(String name, String homeaddress, String email, String phoneNo) {
        this.name = name;
        this.homeaddress = homeaddress;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getHomeaddress() {
        return homeaddress;
    }

    public void setHomeaddress(String homeaddress) {
        this.homeaddress = homeaddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}

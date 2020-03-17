package com.android.selvaraj.displaybus.model;

public class Student {
    private String name, rollNo, email, password, phone,createdAt;

    public Student() {
    }

    public Student(String name, String rollNo, String email, String password, String phone,String createdAt) {
        this.name = name;
        this.rollNo = rollNo;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.createdAt=createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}

package com.griddynamics.springcourse.entity;

public class PhoneBookEntry {
    private String name;
    private String phoneNumber;

    public PhoneBookEntry(String name, String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}


package com.hexaware.transportms.beans;

public class Passenger {
    private int passengerId;
    private String firstName;
    private String gender;
    private int age;
    private String email;
    private String phoneNumber;

    public Passenger() {}

    public Passenger(int passengerId, String firstName, String gender, int age, String email, String phoneNumber) {
        this.passengerId = passengerId;
        this.firstName = firstName;
        this.gender = gender;
        this.age = age;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public int getPassengerId() { return passengerId; }
    public String getFirstName() { return firstName; }
    public String getGender() { return gender; }
    public int getAge() { return age; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }

    @Override
    public String toString() {
        return passengerId + " | " + firstName + " | " + gender + " | " + age + " | " + email + " | " + phoneNumber;
    }
}

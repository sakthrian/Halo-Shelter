package com.example.myfirstapp.Model;

public class PropertyDetails {
    String name,uid;
    String bio;
    String age;
    String gender;
    String contact;
    String profile;
    String location;
    String rent;
    String smoking,drinking,overnight_guests,food_habit;
    String property_image;
    String family,student,batchelor,couples;
    String duration;

    public PropertyDetails(String uid, String name, String bio, String age, String gender, String contact_no, String profilepic, String location, String rent, String propertypic, String family, String batchelor, String student, String couples, String duration, String smoking, String drinking, String overnight_guests, String food_habits) {

        this.uid=uid;
        this.name=name;
        this.bio=bio;
        this.age=age;
        this.gender=gender;
        this.contact=contact_no;
        this.profile=profilepic;
        this.location=location;
        this.rent=rent;
        this.property_image=propertypic;
        this.family=family;
        this.couples=couples;
        this.batchelor=batchelor;
        this.student=student;
        this.duration=duration;
        this.smoking=smoking;
        this.drinking=drinking;
        this.overnight_guests=overnight_guests;
        this.food_habit=food_habits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public String getSmoking() {
        return smoking;
    }

    public void setSmoking(String smoking) {
        this.smoking = smoking;
    }

    public String getDrinking() {
        return drinking;
    }

    public void setDrinking(String drinking) {
        this.drinking = drinking;
    }

    public String getOvernight_guests() {
        return overnight_guests;
    }

    public void setOvernight_guests(String overnight_guests) {
        this.overnight_guests = overnight_guests;
    }

    public String getFood_habit() {
        return food_habit;
    }

    public void setFood_habit(String food_habit) {
        this.food_habit = food_habit;
    }

    public String getProperty_image() {
        return property_image;
    }

    public void setProperty_image(String property_image) {
        this.property_image = property_image;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getBatchelor() {
        return batchelor;
    }

    public void setBatchelor(String batchelor) {
        this.batchelor = batchelor;
    }

    public String getCouples() {
        return couples;
    }

    public void setCouples(String couples) {
        this.couples = couples;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public PropertyDetails(){

    }
}

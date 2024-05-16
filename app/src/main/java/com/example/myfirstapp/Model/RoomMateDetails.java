package com.example.myfirstapp.Model;

public class RoomMateDetails {

    String name;
    String bio;
    String age;
    String gender;
    String contact;
    String profile;
    String location;
    String rent;
    String occupancy;
    String looking_for;
    String teams;
    public RoomMateDetails(String uid, String name, String bio, String age, String gender, String contact_no, String profilepic, String location, String rent, String occupency_selected, String gender_required, String make_team) {

        this.uid=uid;
        this.name=name;
        this.bio=bio;
        this.age=age;
        this.gender=gender;
        this.contact=contact_no;
        this.profile=profilepic;
        this.location=location;
        this.rent=rent;
        this.occupancy=occupency_selected;
        this.looking_for=gender_required;
        this.teams=make_team;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    String uid;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(String occupancy) {
        this.occupancy = occupancy;
    }

    public String getLooking_for() {
        return looking_for;
    }

    public void setLooking_for(String looking_for) {
        this.looking_for = looking_for;
    }

    public String getTeams() {
        return teams;
    }

    public void setTeams(String teams) {
        this.teams = teams;
    }

    public RoomMateDetails(){

    }

}

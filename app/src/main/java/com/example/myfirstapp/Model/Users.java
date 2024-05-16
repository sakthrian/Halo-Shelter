package com.example.myfirstapp.Model;

public class Users {

    String profile_pic,email,userName,password,userID,bio,age,gender,phone_no,setup_done;

    public Users(String uid, String name, String email, String password, String imageuri, String bio, String age, String gender, String ph_no, String setup_done){
        this.userID=uid;
        this.userName=name;
        this.email=email;
        this.password=password;
        this.profile_pic=imageuri;
        this.bio=bio;
        this.age=age;
        this.gender=gender;
        this.phone_no=ph_no;
        this.setup_done=setup_done;

    }

    public String getBio() {return bio;}

    public void setBio(String bio) {this.bio = bio;}

    public String getAge() {return age;}

    public void setAge(String age) {this.age = age;}

    public String getGender() {return gender;}

    public void setGender(String gender) {this.gender = gender;}

    public String getPhone_no() {return phone_no;}

    public void setPhone_no(String phone_no) {this.phone_no = phone_no;}

    public String getProfile_pic() {return profile_pic;}

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSetup_done() {
        return setup_done;
    }

    public void setSetup_done(String setup_done) {
        this.setup_done = setup_done;
    }
}

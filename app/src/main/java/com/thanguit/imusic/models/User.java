package com.thanguit.imusic.models;

public class User {
    private String id;
    private String avatar;
    private String name;
    private String gender;
    private String birthday;
    private String email;
//    private int theme;

    public User() {
        this.id = "";
        this.avatar = "";
        this.name = "";
        this.gender = "";
        this.birthday = "";
        this.email = "";
    }

    public User(String id, String avatar, String name, String gender, String birthday, String email) {
        this.id = id;
        this.avatar = avatar;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", avatar='" + avatar + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday='" + birthday + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    //    public int getTheme() {
//        return theme;
//    }
}

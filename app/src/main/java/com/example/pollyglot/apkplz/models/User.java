package com.example.pollyglot.apkplz.models;

public class User {

    public String id;
    public String login;
    public String email;
    public String password;
    public String avatar;


    public User() {
    }

    public User(String id, String login, String email, String password, String avatar) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}

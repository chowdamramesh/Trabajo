package com.example.chandra.trabajo;

/**
 * Created by chandra on 7/6/16.
 */
public class User {
    public String name,username,password;

    public User(String name,String username,String password){
        this.name=name;
        this.username=username;
        this.password=password;
    }
    public User(){}
    public User(String username,String password){
        this.password=password;
        this.username=username;
    }
}

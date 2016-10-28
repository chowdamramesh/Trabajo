package com.example.chandra.trabajo;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by chandra on 7/6/16.
 */
public class UserLocalStore {
    public static final String SP_NAME="userDetailss";
    SharedPreferences userLocalDatabase;

    public  UserLocalStore(Context context){
        userLocalDatabase= context.getSharedPreferences(SP_NAME,0);
    }

    public void storeUserData(User user){

        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("name",user.name);
        spEditor.putString("username",user.username);
        spEditor.putString("password",user.password);
        spEditor.commit();
    }

    public User getLoggedInUser(){
        String name = userLocalDatabase.getString("name", "");
        String username = userLocalDatabase.getString("username","");
        String password = userLocalDatabase.getString("pasword","");

        User user = new User(name,username,password);
        return user;
    }
    public boolean getUserLoggedIn(){
        if(userLocalDatabase.getBoolean("loggedIn",false)==true){
            return true;
        }else{
            return  false;
        }
    }
    public void setUserLoggedIn(boolean loggedin){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn",loggedin);
        spEditor.commit();
    }

    public void clearUserData(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
    }
}

package com.shareads.user.myapplication.preferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by anjani on 1/12/16.
 */

public class BookPreferences {

    public static final String USER_PREFS = "Book_PREFS";
    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;
    private String userId = "user_Id";
    private String user_name = "user_name";
    private  String user_password = "user_password";
    private  String user_email = "user_email";
    private  String user_mob_num = "user_mob_num";
    private  String user_address= "user_address";
    private  String gender= "gender";
    private  String user_state = "user_state";
    private  String user_city= "user_city";
    private  String user_pincode= "user_pincode";

    public BookPreferences(Context context) {
        this.appSharedPrefs = context.getSharedPreferences(USER_PREFS, Activity.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }

    /* ------------------------------------User Id----------------------------------------*/

    public void setUserId(String User_Id) {
        prefsEditor.putString(this.userId, User_Id).commit();
    }

    public String getUserId() {

        return appSharedPrefs.getString(userId, "");
    }

    public void setUser_name(String user_name){
        prefsEditor.putString(this.user_name,user_name).commit();
    }

    public String getUser_name(){
        return appSharedPrefs.getString(user_name,"");
    }

    public void setUser_email(String user_email){
        prefsEditor.putString(this.user_email,user_email).commit();

    }

    public  String getUser_email(){
        return  appSharedPrefs.getString(user_email,"");
    }

    public void setUser_password(String user_password){
        prefsEditor.putString(this.user_password,user_password).commit();
    }

    public  String getUser_password(){
        return appSharedPrefs.getString(user_password,"");
    }

    public void setUser_mob_num(String user_mob_num){
        prefsEditor.putString(this.user_mob_num,user_mob_num).commit();
    }

    public String getUser_mob_num(){
        return  appSharedPrefs.getString(user_mob_num,"");
    }


    public  void setUser_address(String user_address){
        prefsEditor.putString(this.user_address,user_address).commit();
    }

    public String getUser_address(){

        return  appSharedPrefs.getString(user_address,"");
    }

    public String getUser_state(){

        return  appSharedPrefs.getString(user_state,"");
    }
    public  void setUser_state(String user_state){
        prefsEditor.putString(this.user_state,user_state).commit();
    }

    public String getUser_city(){

        return  appSharedPrefs.getString(user_city,"");
    }
    public  void setUser_pincode(String user_pincode){
        prefsEditor.putString(this.user_pincode,user_pincode).commit();
    }
    public String getUser_pincode(){

        return  appSharedPrefs.getString(user_pincode,"");
    }
    public  void setUser_city(String user_city){
        prefsEditor.putString(this.user_city,user_city).commit();
    }



    public void setGender(String gender){
        prefsEditor.putString(this.gender,gender).commit();
    }

    public String getGender(){
        return appSharedPrefs.getString(gender,"");
    }
}






package com.example.digicala.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.widget.TextView;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.FormatFlagsConversionMismatchException;

public class SharePrefernce {

    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    static final String SHARED_PREFRENCES_NAME = "Save";

    private final String LOGIN_KEY = "loginstate";
    private final String FONTSIZE_KEY = "fontsize";
    private final String LANGAGE_KEY = "language";
    private final String USERNAME_KEY = "username";
    private final String DARKORLIGHT_KEY = "themes";


    public SharePrefernce(Context context) {
        this.context = context;
    }

    public void makeshareprefrence() {

        sharedPreferences = context.getSharedPreferences(SHARED_PREFRENCES_NAME, Context.MODE_PRIVATE);

    }


    public void changeloginstate(boolean login) {

        makeshareprefrence();
        editor = sharedPreferences.edit();
        editor.putBoolean(LOGIN_KEY, login);
        editor.apply();


    }


    public Boolean getloginstate() {
        try {
            makeshareprefrence();
            if (sharedPreferences.contains(LOGIN_KEY)) {
                boolean loginstate = sharedPreferences.getBoolean(LOGIN_KEY, false);
                return loginstate;
            }
//
        } catch (Exception e) {
            makeshareprefrence();
            editor = sharedPreferences.edit();
            editor.putBoolean(LOGIN_KEY, false);
            editor.apply();

            return false;
        }
        return false;


    }


    public int getfontsize() {

        try {
            makeshareprefrence();
            if (sharedPreferences.contains(FONTSIZE_KEY)) {

                int fontsize = sharedPreferences.getInt(FONTSIZE_KEY, 0);

                return fontsize;
            }
        } catch (Exception e) {
            makeshareprefrence();
            editor = sharedPreferences.edit();
            editor.putInt(FONTSIZE_KEY, 0);
            editor.apply();
        }
        return 0;
    }


    public Boolean getlanguage() {

        try {
            if (sharedPreferences.contains(LANGAGE_KEY)) {

                Boolean farsi = sharedPreferences.getBoolean(LANGAGE_KEY, true);

                return farsi;
            }
        } catch (Exception e) {
            makeshareprefrence();
            editor = sharedPreferences.edit();
            editor.putBoolean(LANGAGE_KEY, true);
            editor.apply();
        }
        return true;
    }


    public void setusername(String username) {


        makeshareprefrence();
        editor = sharedPreferences.edit();
        editor.putString(USERNAME_KEY, username);
        editor.apply();


    }

    public String getusername() {

        makeshareprefrence();
        if (sharedPreferences.contains(USERNAME_KEY)) {
            String string = sharedPreferences.getString(USERNAME_KEY, "unknown");
            return string;
        }
        return "unknown";
    }


    public void setfontsize(int size) {


        makeshareprefrence();
        editor = sharedPreferences.edit();
        editor.putInt(FONTSIZE_KEY, size);
        editor.apply();


    }


    public Boolean gettheme() {
        try {
            makeshareprefrence();
            if (sharedPreferences.contains(DARKORLIGHT_KEY)) {
                boolean loginstate = sharedPreferences.getBoolean(DARKORLIGHT_KEY, false);
                return loginstate;
            }
//
        } catch (Exception e) {
            makeshareprefrence();
            editor = sharedPreferences.edit();
            editor.putBoolean(DARKORLIGHT_KEY, false);
            editor.apply();

            return false;
        }
        return false;


    }


    public void changetheme(boolean login) {

        makeshareprefrence();
        editor = sharedPreferences.edit();
        editor.putBoolean(DARKORLIGHT_KEY, login);
        editor.apply();


    }

}

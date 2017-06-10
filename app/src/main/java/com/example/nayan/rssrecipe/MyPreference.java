package com.example.nayan.rssrecipe;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Nayan on 6/11/2017.
 */

public class MyPreference {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean checked;
    private String userName;
    private String password;
    Context context;

    public MyPreference(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("myPrefs",
                context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public MyPreference(Context context, boolean checked, String userName, String password){
        this(context);
        this.context = context;
        this.checked = checked;
        this.userName = userName;
        this.password = password;
        editor.putBoolean("onOff", checked);
        editor.putString("userName",userName);
        editor.putString("password",password);
        editor.commit();

    }


    public String getUserName(){
        String userName = sharedPreferences.getString("userName",null);
        return userName;
    }

    public String getPassword(){
        String password = sharedPreferences.getString("password",null);
        return password;
    }

    public void setChecked(boolean checked){
        editor.putBoolean("onOff",checked);
        editor.commit();
    }

    public boolean isOn() {
        boolean checked = sharedPreferences.getBoolean("onOff", false);
        if(checked)
            return checked;
        else
            return false;
    }

}

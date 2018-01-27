package com.example.kgy_product.user;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ccc62 on 2018-01-27.
 */

public class User
{
    private static User _instance;

    public static User instance()
    {
        if( _instance == null )
        {
            _instance = new User(new Singleton());
        }

        return _instance;
    }

    private String id = "";
    private String name = "";

    public String getId()
    {
        return id;
    }

    public void setUser(String id, String name, Context context)
    {
        this.id = id;
        this.name = name;

        saveLogin(context);
    }

    public String getName()
    {
        return name;
    }

    public User(Singleton sigleton) { }

    private void saveLogin(Context context)
    {
        try
        {
            SharedPreferences setting = context.getSharedPreferences("setting",context.MODE_PRIVATE);
            SharedPreferences.Editor editor = setting.edit();

            Date date = new Date();

            SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMdd");
            String nowDate = sdf.format(date);

            editor.putString("date", nowDate);
            editor.putString("id", this.id);
            editor.putString("name", this.name);
            editor.commit();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
class Singleton{}

package com.example.tourismactivation;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class constants {
    public final static byte AR = 1;
    public final static byte EN = 0;
    public  static byte LANGUAGE;

    public static void getLANGUAGE(Activity activity){
        SharedPreferences preferences = activity.getSharedPreferences("userData", Context.MODE_PRIVATE);
        LANGUAGE = (byte) preferences.getInt("appLanguage", EN);

    }
}

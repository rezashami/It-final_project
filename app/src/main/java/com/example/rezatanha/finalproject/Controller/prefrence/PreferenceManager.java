package com.example.rezatanha.finalproject.Controller.prefrence;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor spEditor;
    private static final String FIRST_LAUNCH = "firstLaunch";
    private static final String PREFERENCE = "MyPrefs";
    private static final String INC = "increment";
    private static final String USER_CODE = "userCode";

    @SuppressLint("CommitPrefEdits")
    public PreferenceManager(Context ctx) {
        sharedPreferences = ctx.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        spEditor = sharedPreferences.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        spEditor.putBoolean(FIRST_LAUNCH, isFirstTime);
        spEditor.commit();
    }

    public boolean FirstLaunch() {
        return sharedPreferences.getBoolean(FIRST_LAUNCH, false);
    }

    public void setInc(int increment) {
        spEditor.putInt(INC, increment);
        spEditor.commit();
    }

    public int inc() {
        return sharedPreferences.getInt(INC, -1);
    }

    public int getUserCode() {
        return sharedPreferences.getInt(USER_CODE, -1);
    }

    public void setUserCode(int increment) {
        spEditor.putInt(USER_CODE, increment);
        spEditor.commit();
    }
}

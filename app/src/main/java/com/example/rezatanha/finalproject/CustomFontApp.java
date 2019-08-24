package com.example.rezatanha.finalproject;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class CustomFontApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/byekan.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

    }
}

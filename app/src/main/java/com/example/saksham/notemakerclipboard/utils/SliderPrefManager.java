package com.example.saksham.notemakerclipboard.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by saksham on 10/1/2017.
 */

public class SliderPrefManager {

    SharedPreferences mSharedPref;
    SharedPreferences.Editor mSharedEditor;
    Context context;

    private static final int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "slider-preference";
    private static final String KEY_IS_FIRST_TIME_LAUNCH = "string-key-for-first-launch";

    public SliderPrefManager(Context context) {

        this.context = context;
        mSharedPref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        mSharedEditor = mSharedPref.edit();

    }

    public void setFirstTimeLaunch() {

        mSharedEditor.putBoolean(KEY_IS_FIRST_TIME_LAUNCH,false);
        mSharedEditor.commit();

    }

    public boolean isFirstTimeLaunch() {

        return mSharedPref.getBoolean(KEY_IS_FIRST_TIME_LAUNCH, true);
    }
}

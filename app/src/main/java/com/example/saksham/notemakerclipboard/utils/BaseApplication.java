package com.example.saksham.notemakerclipboard.utils;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by saksham on 9/14/2017.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
    }
}

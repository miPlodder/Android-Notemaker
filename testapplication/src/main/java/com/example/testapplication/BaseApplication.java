package com.example.testapplication;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by saksham on 9/14/2017.
 */

public class BaseApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();


        Realm.init(this);

    }
}

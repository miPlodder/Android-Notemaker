package com.example.testapplication;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.Realm;
import io.realm.annotations.PrimaryKey;

/**
 * Created by saksham on 9/14/2017.
 */

public class POJO extends RealmObject {

    private String note;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "POJO{" +
                ", note='" + note + '\'' +
                '}';
    }
}



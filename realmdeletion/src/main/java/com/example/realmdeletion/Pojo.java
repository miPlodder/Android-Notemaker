package com.example.realmdeletion;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by saksham on 9/24/2017.
 */

public class Pojo extends RealmObject {

    @PrimaryKey
    int key;

    int text;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getText() {
        return text;
    }

    public void setText(int text) {
        this.text = text;
    }
}

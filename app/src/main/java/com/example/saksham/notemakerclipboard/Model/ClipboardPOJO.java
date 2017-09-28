package com.example.saksham.notemakerclipboard.Model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by saksham on 9/28/2017.
 */

public class ClipboardPOJO extends RealmObject {

    @PrimaryKey
    private int id;

    private String text;
    private String timeStamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}

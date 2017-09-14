package com.example.saksham.notemakerclipboard.Model;

import io.realm.RealmObject;

/**
 * Created by saksham on 9/13/2017.
 */

public class NotesPOJO extends RealmObject {

    private String text;
    private String timeStamp;

    public NotesPOJO() {

    }

    public NotesPOJO(String text, String timeStamp) {
        this.text = text;
        this.timeStamp = timeStamp;
    }

    public String getText() {
        return text;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}

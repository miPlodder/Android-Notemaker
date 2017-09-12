package com.example.saksham.notemakerclipboard.Model;

/**
 * Created by saksham on 9/13/2017.
 */

public class NotesPOJO {

    String text;
    String timeStamp;

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
}

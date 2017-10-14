package com.example.saksham.notemakerclipboard.utils;

/**
 * Created by saksham on 9/15/2017.
 */

public class Constant {

    public static final int ACTIVITY_EDIT_NOTE_CODE = 123 ;

    //request code for adding note
    public static final int ACTIVITY_ADD_NOTE_CODE = 101 ;

    //key used to pass data from add activty to notes fragment
    public static final String ACTIVITY_INTENT_KEY_ADD = "note";

    //key used to pass data from edit activty to notes fragment
    public static final String ACTIVITY_INTENT_KEY_EDIT = "note";

    public static final String ACTIVITY_INTENT_KEY_POSITION = "position";

    public static final String ACTIVITY_INTENT_EXTRA_CLIPBOARD_EDIT = "editClipboard";

    public static final String ACTIVTY_INTENT_EXTA_CLIPBOARD_POSITION = "positioninarraylist" ;

    public static final String ACTIVITY_INTENT_EXTRA_CLIPBOARD_RESPONSE = "editCLipboardResponse";

    public static final String ACTIVITY_INTENT_EXTRA_CLIPBOARD_POSITION_RESPONSE = "positionresponse";

    public static final String[] MONTH_INITIALS = {"Jan", "Feb", "Mar", "Apr" , "May" , "Jun" , "Jul" , "Aug", "Sep", "Oct", "Nov", "Dec"};

    public static int getMonthNumber(String month){

        int rv = -1;

        for (int i = 0 ; i < 12 ; i++){

            if(month.equals(MONTH_INITIALS[i])){
                rv = i + 1;
            }

        }
        return rv;
    }

}

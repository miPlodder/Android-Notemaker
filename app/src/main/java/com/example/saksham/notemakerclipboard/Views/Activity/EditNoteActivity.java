package com.example.saksham.notemakerclipboard.Views.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.saksham.notemakerclipboard.R;
import com.example.saksham.notemakerclipboard.utils.Constant;

/**
 * Created by saksham on 9/15/2017.
 */

public class EditNoteActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "EditNoteActivity";
    Toolbar toolbarAddNote;
    EditText etEditNote;
    ImageButton ibAddNote;


    //take the intent add add text to the edittext and
    //on done same it to the database

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        this.initialise();

        Intent i = getIntent();
        if(i != null){

            String note = i.getStringExtra(Constant.ACTIVITY_INTENT_KEY_EDIT);
            etEditNote.setText(note);
            //used to place the cursor at the end of text
            etEditNote.requestFocus();

        }

        Log.d(TAG, "onCreate: "+getIntent());

    }

    protected void initialise() {

        toolbarAddNote = (Toolbar) findViewById(R.id.toolbarAddNotes);
        etEditNote = (EditText) findViewById(R.id.etAddNote);
        ibAddNote = (ImageButton) findViewById(R.id.ibAddNote);
        ibAddNote.setOnClickListener(this);

        setSupportActionBar(toolbarAddNote);

        ActionBar actionBar = getSupportActionBar();

        //to enable back button on toolbar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Edit Note");
        toolbarAddNote.setTitleTextColor(Color.WHITE);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.ibAddNote:

                Intent i = new Intent();
                i.putExtra(Constant.ACTIVITY_INTENT_KEY_EDIT,
                        etEditNote.getText().toString());
                setResult(RESULT_OK,i);
                finish();
                break;
        }
    }
}

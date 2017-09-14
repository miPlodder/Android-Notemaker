package com.example.saksham.notemakerclipboard.Views.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.saksham.notemakerclipboard.R;

import io.realm.Realm;

public class AddNoteActivity extends AppCompatActivity {

    Toolbar toolbarAddNote;
    EditText etAddNote;

    protected void initialise(){

        toolbarAddNote = (Toolbar) findViewById(R.id.toolbarAddNotes);
        etAddNote = (EditText) findViewById(R.id.etAddNote);
        setSupportActionBar(toolbarAddNote);

        ActionBar actionBar = getSupportActionBar();

        //to enable back button on toolbar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("New Note");
        toolbarAddNote.setTitleTextColor(Color.WHITE);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        this.initialise();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                Intent resultIntent = new Intent();
                resultIntent.putExtra("note",etAddNote.getText().toString());
                setResult(RESULT_OK, resultIntent);
                finish();
                break;


        }

        return super.onOptionsItemSelected(item);
    }
}

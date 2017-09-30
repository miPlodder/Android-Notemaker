package com.example.saksham.notemakerclipboard.Views.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.saksham.notemakerclipboard.R;
import com.example.saksham.notemakerclipboard.utils.Constant;

public class AddNoteActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbarAddNote;
    EditText etAddNote;
    ImageButton ibAddNote;

    protected void initialise() {

        toolbarAddNote = (Toolbar) findViewById(R.id.toolbarAddNotes);
        etAddNote = (EditText) findViewById(R.id.etAddNote);
        ibAddNote = (ImageButton) findViewById(R.id.ibAddNote);
        ibAddNote.setOnClickListener(this);

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

        switch (item.getItemId()) {

            case android.R.id.home:
                Intent resultIntent = new Intent();
                resultIntent.putExtra("note", etAddNote.getText().toString().trim());
                setResult(RESULT_OK, resultIntent);
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ibAddNote:
                Intent resultIntent = new Intent();
                resultIntent.putExtra(Constant.ACTIVITY_INTENT_KEY_ADD, etAddNote.getText().toString().trim());
                setResult(RESULT_OK, resultIntent);
                finish();
                Toast.makeText(this, "Added Note", Toast.LENGTH_SHORT).show();
                break;


        }
    }
}

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

import com.example.saksham.notemakerclipboard.R;
import com.example.saksham.notemakerclipboard.utils.Constant;

import io.realm.Realm;

public class UpdateClipboardNoteActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbarUpdateNote;
    ImageButton ibUpdateNote;
    EditText etUpdateNote;
    int position; //position of the item in arraylist

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        this.initialise();

        Intent i = getIntent();
        if (i != null) {

            String text = i.getStringExtra(Constant.ACTIVITY_INTENT_EXTRA_CLIPBOARD_EDIT);
            etUpdateNote.setText(text);
            etUpdateNote.requestFocus();

            position = i.getIntExtra(Constant.ACTIVTY_INTENT_EXTA_CLIPBOARD_POSITION, -1);

        }

    }

    private void initialise() {

        toolbarUpdateNote = (Toolbar) findViewById(R.id.toolbarAddNotes);
        setSupportActionBar(toolbarUpdateNote);
        ActionBar actionBar = getSupportActionBar();

        //to enable back button on toolbar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Edit Clipboard Note");
        toolbarUpdateNote.setTitleTextColor(Color.WHITE);

        ibUpdateNote = (ImageButton) findViewById(R.id.ibAddNote);
        ibUpdateNote.setOnClickListener(this);
        etUpdateNote = (EditText) findViewById(R.id.etAddNote);
        etUpdateNote.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ibAddNote:
                Intent intent = new Intent();
                intent.putExtra(Constant.ACTIVITY_INTENT_EXTRA_CLIPBOARD_RESPONSE, etUpdateNote.getText().toString());
                intent.putExtra(Constant.ACTIVITY_INTENT_EXTRA_CLIPBOARD_POSITION_RESPONSE, position);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                Intent i = new Intent();
                i.putExtra(Constant.ACTIVITY_INTENT_EXTRA_CLIPBOARD_RESPONSE, etUpdateNote.getText().toString());
                i.putExtra(Constant.ACTIVITY_INTENT_EXTRA_CLIPBOARD_POSITION_RESPONSE, position);
                setResult(RESULT_OK, i);
                finish();
                break;


        }
        return true;
    }
}

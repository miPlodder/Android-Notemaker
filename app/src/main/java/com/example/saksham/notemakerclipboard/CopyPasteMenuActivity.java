package com.example.saksham.notemakerclipboard;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saksham.notemakerclipboard.Model.ClipboardPOJO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import io.realm.Realm;

public class CopyPasteMenuActivity extends AppCompatActivity {

    LinearLayout llOk, llCancel;
    TextView tvClipboard;
    String copiedText;
    Realm realm;
    ClipboardManager cm;
    ClipData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copy_paste_menu);

        this.initialise();

        //adding on click listener
        this.addOnClickListener();


        Intent i = getIntent();

        if (i != null && i.getAction().equals(Intent.ACTION_PROCESS_TEXT)) {

            //get data from clipboard code here
            copiedText = i.getStringExtra(Intent.EXTRA_PROCESS_TEXT);
            tvClipboard.setText(copiedText);

            data = ClipData.newPlainText("text", copiedText);
            cm.setPrimaryClip(data);

        }

    }

    private void initialise() {

        llOk = (LinearLayout) findViewById(R.id.llOk);
        llCancel = (LinearLayout) findViewById(R.id.llCancel);
        tvClipboard = (TextView) findViewById(R.id.tvClipboardCopied);
        realm = Realm.getDefaultInstance();

        cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);


    }

    private void addOnClickListener() {

        llOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addToRealmDB();
                finish();
            }
        });

        llCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                Toast.makeText(CopyPasteMenuActivity.this, "Cancel", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void addToRealmDB() {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                Random rand = new Random();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm aa");
                String format = simpleDateFormat.format(new Date());

                ClipData data = cm.getPrimaryClip();
                ClipData.Item note = data.getItemAt(0);

                ClipboardPOJO item = realm.createObject(ClipboardPOJO.class, rand.nextInt(Integer.MAX_VALUE));
                item.setText(note.getText().toString());
                item.setTimeStamp(format);

                realm.copyToRealmOrUpdate(item);

                Toast.makeText(CopyPasteMenuActivity.this, "Copied to Copier", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

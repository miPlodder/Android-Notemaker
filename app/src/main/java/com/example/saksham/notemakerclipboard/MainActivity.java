package com.example.saksham.notemakerclipboard;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "MainActivity";

    EditText et;
    TextView tv;
    Button btnCopy, btnPaste;
    ClipboardManager mClipboard;
    ClipData mClip;

    private void initialiseView() {


        et = (EditText) findViewById(R.id.et);
        tv = (TextView) findViewById(R.id.tv);
        btnCopy = (Button) findViewById(R.id.btnCopy);
        btnPaste = (Button) findViewById(R.id.btnPaste);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialising view
        initialiseView();
        mClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        btnCopy.setOnClickListener(this);
        btnPaste.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnCopy:

                String text = et.getText().toString();
                mClip = ClipData.newPlainText("text",text);

                //setting TEXT inside Clipboard
                mClipboard.setPrimaryClip(mClip);

                Toast.makeText(this, "Copied "+mClip.toString(), Toast.LENGTH_SHORT).show();

                Log.d(TAG, "onClick: "+mClip.toString());

                break;
            case R.id.btnPaste:

                //getting TEXT from Clipboard
                ClipData mClipCopied = mClipboard.getPrimaryClip();
                //array of size 1
                ClipData.Item item = mClipCopied.getItemAt(0);

                String text2 = item.getText().toString();
                tv.setText(text2);

                Toast.makeText(this, "Text Copied1", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "item1 "+mClipCopied.getItemAt(0));
                break;
        }
    }
}

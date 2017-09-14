package com.example.testapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private Realm realm;
    Button btn;
    EditText et;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        realm = Realm.getDefaultInstance();
        Log.d(TAG, "onCreate: " + realm);

        btn = (Button) findViewById(R.id.btn);
        et = (EditText) findViewById(R.id.et);
        tv = (TextView) findViewById(R.id.tv);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveToDB(et.getText().toString().trim());

            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();

    }

    protected void saveToDB(final String note) {

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                POJO user = bgRealm.createObject(POJO.class);
                user.setNote(note);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                refreshView();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Toast.makeText(MainActivity.this, "Error >>>" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refreshView() {

        // Build the query looking at all users:
        RealmQuery<POJO> query = realm.where(POJO.class);

        // Add query conditions:
        // Execute the query:
        RealmResults<POJO> result = query.findAll();

        String output = "";
        for (POJO item : result) {

            output += item.toString();
        }
        tv.setText(output);
    }
}
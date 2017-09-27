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

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private Realm realm;
    Button btn, btnDel;
    EditText et,etId;
    TextView tv;
    ArrayList<POJO> list ;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        list = new ArrayList<>();

        realm = Realm.getDefaultInstance();
        Log.d(TAG, "onCreate: " + realm);

        btn = (Button) findViewById(R.id.btn);
        et = (EditText) findViewById(R.id.et);
        tv = (TextView) findViewById(R.id.tv);
        btnDel = (Button) findViewById(R.id.btnDel);
        etId = (EditText) findViewById(R.id.etId);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveToDB(et.getText().toString().trim());

            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                delFromDB();
            }
        });

    }

    public void delFromDB(){

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                POJO item = list.get(counter);
                counter++;
                //list.remove(counter);
                if(list.get(counter).getId() == "Invalid object"){
                    Log.d(TAG, "execute: insideindindeiindendniednidnedined");
                }
                item.deleteFromRealm();
                Log.d(TAG, "delFromDB: "+list);
                Log.d(TAG, "execute:size "+list.size());
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

                /*final Dog managedDog = realm.copyToRealm(dog); // Persist unmanaged objects
                Person person = realm.createObject(Person.class); // Create managed objects directly
                person.getDogs().add(managedDog);*/

                POJO user = bgRealm.createObject(POJO.class,etId.getText().toString());
                user.setNote(note);


                bgRealm.copyToRealmOrUpdate(user);

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
                Log.d(TAG, "onError: "+error.getMessage());
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
            list.add(item);
        }
        tv.setText(output);
    }
}
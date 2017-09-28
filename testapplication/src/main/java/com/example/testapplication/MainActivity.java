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
    Button btnAdd, btnDel, btnView;
    EditText et, etId;
    TextView tv;
    ArrayList<POJO> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        list = new ArrayList<>();

        realm = Realm.getDefaultInstance();

        //every code of realm must be in the TRANSACTION
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                RealmQuery<POJO> query = realm.where(POJO.class);
                RealmResults<POJO> result = query.findAll();

                for (POJO item : result)
                    list.add(item);


            }
        });

        Log.d(TAG, "onCreate: " + realm);

        btnView = (Button) findViewById(R.id.btnView);
        et = (EditText) findViewById(R.id.et);
        tv = (TextView) findViewById(R.id.tv);
        btnDel = (Button) findViewById(R.id.btnDel);
        etId = (EditText) findViewById(R.id.etId);
        btnAdd = (Button) findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addToDB(et.getText().toString().trim());

            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                delFromDB();
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                viewFromDB();
                tv.setText(list.toString());
            }
        });

    }

    public void viewFromDB() {

        Log.d(TAG, "viewFromDB: " + list);

    }


    public void delFromDB() {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                POJO item = list.get(list.size() - 1);

                item.deleteFromRealm();
                list.remove(list.size() - 1);

                Log.d(TAG, "delFromDB: " + list);
                Log.d(TAG, "execute:size " + list.size());
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();

    }

    protected void addToDB(final String note) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                POJO user = realm.createObject(POJO.class, etId.getText().toString());
                user.setNote(note);

                list.add(user);

                realm.copyToRealmOrUpdate(user);

            }
        });
/*
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                */
/*final Dog managedDog = realm.copyToRealm(dog); // Persist unmanaged objects
                Person person = realm.createObject(Person.class); // Create managed objects directly
                person.getDogs().add(managedDog);*//*



            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Toast.makeText(MainActivity.this, "Error >>>" + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onError: " + error.getMessage());
            }
        });
    }*/

    }
}
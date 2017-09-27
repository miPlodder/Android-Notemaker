package com.example.realmdeletion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tv;
    EditText et;
    Button btnAdd, btnDel;
    Realm realm ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initialise();
        this.realmWork();
    }

    private void initialise(){

        tv = (TextView) findViewById(R.id.tv);
        et = (EditText) findViewById(R.id.et);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnDel = (Button) findViewById(R.id.btnDel);

    }

    private void realmWork(){

        realm = Realm.getDefaultInstance(); //realm object with default configuration

        realm.beginTransaction();
        Pojo user = realm.createObject(Pojo.class);
        //user.setKey();
        realm.commitTransaction();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){


            case R.id.btnAdd:

                 break;
            case R.id.btnDel:
                break;

        }

    }
}

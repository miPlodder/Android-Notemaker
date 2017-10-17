package com.example.saksham.notemakerclipboard.Views.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.saksham.notemakerclipboard.R;

public class SplashActivity extends AppCompatActivity {

    LinearLayout llSplash;
    int marginTop = 0;
    public static final String TAG = "SplashActivity";
    CountDownTimer timer ;
    boolean isAfterPause = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        llSplash = (LinearLayout) findViewById(R.id.llSplash);

        timer = (new CountDownTimer(700, 50) {

            @Override
            public void onTick(long millisUntilFinished) {

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(llSplash.getLayoutParams());

                marginTop += 50;
                Log.d(TAG, "onTick: "+marginTop);
                params.setMargins(0, marginTop, 0, 0);
                llSplash.setLayoutParams(params);
            }

            @Override
            public void onFinish() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        startActivity(new Intent(SplashActivity.this,
                                MainActivity.class));
                        finish();
                    }
                }, 800);

            }
        }).start();


    }

    @Override
    protected void onPause() {

        super.onPause();
        timer.cancel();
        isAfterPause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(isAfterPause){
            startActivity(new Intent(SplashActivity.this,
                    MainActivity.class));
        }

    }
}

package com.cs.lgubustracker;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cs.lgubustracker.util.Util;

public class SplashScreen extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Util.isConnectedWifi(this) || Util.isConnectedMobile(this)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    startActivity(new Intent(SplashScreen.this, LandingScreen.class));


                    finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        }else{
           findViewById(R.id.no_internet).setVisibility(View.VISIBLE);
           findViewById(R.id.ok).setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    public void closeApp(View view) {
        finish();
    }
}

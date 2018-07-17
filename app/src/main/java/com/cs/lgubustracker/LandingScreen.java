package com.cs.lgubustracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cs.lgubustracker.util.Util;

public class LandingScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_screen);
    }

    public void LoginDriver(View view) {
        Util.IS_DRIVER = true;
        startActivity(new Intent(LandingScreen.this,LoginScreen.class));
    }

    public void LoginUser(View view) {
        Util.IS_DRIVER = false;
        startActivity(new Intent(LandingScreen.this,LoginScreen.class));
    }
}

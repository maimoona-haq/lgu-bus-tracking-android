package com.cs.lgubustracker;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cs.lgubustracker.network.HttpClient;
import com.cs.lgubustracker.pref.PreferenceManager;
import com.cs.lgubustracker.util.Util;

import org.json.JSONObject;

public class LoginScreen extends AppCompatActivity {

    private TextView username, password;
    private ProgressDialog progressBar;
    private static final String TAG = "LoginScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);
        progressBar.setMessage("Loading");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        if (Util.IS_DRIVER)
            username.setHint("Bus ID");
        else
            username.setHint("Email");
    }

    public void login(View view) {
        Log.d("LoginScreen", "Login");

        if (username.getText() != null && !(username.getText().toString().equals(""))) {
            if (password.getText() != null && !(password.getText().toString().equals(""))) {
                if(HttpClient.isMobileOrWifiConnectivityAvailable(this)) {
                    new asyncTask().execute(Util.BASE_URL + Util.SIGN_IN_ACTION);
                }else{
                    Toast.makeText(LoginScreen.this,"Please Check your Internet Coonnection",Toast.LENGTH_SHORT).show();
                }
            } else {
                //TODO print toast
                Toast.makeText(LoginScreen.this,"Please enter your password",Toast.LENGTH_SHORT).show();
            }
        } else {
            //TODO print toast
            Toast.makeText(LoginScreen.this,"Please enter your email address ",Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressLint("StaticFieldLeak")
   public class  asyncTask extends  AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
                    progressBar.show();


        }

        @Override
        protected String doInBackground(String... objects) {
            try {
                Log.d(TAG, "doInBackground: "+objects[0]);

                JSONObject jsonObject = new JSONObject();
                JSONObject userPassObj = new JSONObject();
                userPassObj.put("email", username.getText().toString());
                userPassObj.put("password", password.getText().toString());
                jsonObject.put("user", userPassObj);
                Log.d(TAG, "data : " + jsonObject.toString());
                JSONObject json = HttpClient.sendPostRequest(LoginScreen.this, objects[0], jsonObject.toString());
                return  json.get("authentication_token").toString();
            } catch (Exception e) {
                Log.e(TAG, "[LoginScreen] inside doInBackground() Exception is :" + e.toString());
                return null;
            }


        }

        @Override
        protected void onPostExecute(String o) {
            super.onPostExecute(o);
            progressBar.dismiss();
            if (o != null) {
                PreferenceManager.setUserName(LoginScreen.this,username.getText().toString());
                PreferenceManager.setPassword(LoginScreen.this,password.getText().toString());
                PreferenceManager.setToken(LoginScreen.this,o);
                startActivity(new Intent(LoginScreen.this, MainScreen.class));
            } else {
                //TODO something error occur
            }
        }
    }
}

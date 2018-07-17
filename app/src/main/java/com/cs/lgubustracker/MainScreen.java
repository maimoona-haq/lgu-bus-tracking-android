package com.cs.lgubustracker;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cs.lgubustracker.model.BusSchedule;
import com.cs.lgubustracker.network.HttpClient;
import com.cs.lgubustracker.pref.PreferenceManager;
import com.cs.lgubustracker.util.Util;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;


public class MainScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    DrawerLayout drawer;
    private Context appContext;
    private TextView name_tv, phone_number_tv;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    boolean enabled = false;
    private ProgressDialog progressBar;

    private static final String TAG = "MainScreen";
    private String selectedBusCounts;
    BusSchedule schedule;
    List<String> buslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        appContext = getApplicationContext();
        try {
            // setLayoutFonts();
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            //initEasyLogin();
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            progressBar = new ProgressDialog(this);
            progressBar.setCancelable(true);
            progressBar.setMessage("Loading");
            //
            //setDrawerLeftEdgeSize(this, drawer, 0.7f);
            //
            //TODO when enable sociallogin the below code also enable plzzzzzzzzzzzzzzzzz
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            /*if (PreferenceManager.getUserName(this) != null) {
                Menu menu = navigationView.getMenu();
                MenuItem item = menu.findItem(R.id.nav_login);
                item.setTitle(R.string.nav_logout_title);
            }*/
            navigationView.setNavigationItemSelectedListener(this);
            View header = navigationView.getHeaderView(0);

            LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            Log.d(TAG, "onCreate: "+longitude);
            Log.d(TAG, "onCreate: "+latitude);
            TextView locationTv = findViewById(R.id.loc);
            if(Util.IS_DRIVER) {
                locationTv.setText("We are sending this(" + location.getLatitude() + "," + location.getLongitude() + ") location to Server");
            }
        } catch (Exception e) {
            Log.e(TAG, "[MainScreen] inside onCreate() Exception is : " + e.toString());
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_new_search) {
            showNearByDialog();
            // startActivity(new Intent(MainScreen.this, NewSearchScreen.class));

        }else if(id == R.id.nav_logout){
            if(HttpClient.isMobileOrWifiConnectivityAvailable(this)) {
                new asyncTask().execute(Util.BASE_URL + Util.SIGN_OUT_ACTION);
            }else{
                Toast.makeText(MainScreen.this,"Please Check your Internet Coonnection",Toast.LENGTH_SHORT).show();
            }
        }else if (id == R.id.nav_new_schedule){
            showNearByDialog2();
        }


        /*else if (id == R.id.nav_add_property) {
//            if (PreferenceManager.getUserName(MainScreen.this) != null) {
           // startActivity(new Intent(MainScreen.this, SubmitAddScreen.class));
//            } else {
//                startActivity(new Intent(MainScreen.this, SelectAddPropertyType.class));
//            }
        } else if (id == R.id.nav_contact_us) {
           // startActivity(new Intent(MainScreen.this, HelpScreen.class));
        } else if (id == R.id.nav_about_us) {
            //startActivity(new Intent(MainScreen.this, AboutUsScreen.class));
        } else if (id == R.id.nav_nearby_search) {
           // showNearByDialog();
            //startActivity(new Intent(MainScreen.this, ListingPageMapScreen.class));
        } else if (id == R.id.nav_login) {
            if (item.getTitle().equals(getString(R.string.nav_logout_title))) {
                //process logout
//                if (processLogout())
//                    item.setTitle(getString(R.string.nav_login_title));
                navigationView.postInvalidate();
            } else {
                //Intent intent = new Intent(MainScreen.this, LandingScreen.class);
                //TransferableData.getInstance(appContext).setRegisterUserType(Constants.LANDING_SCREEN_INTENT_EXTRA_SIGNIN);
//                startActivity(intent);
//                item.setTitle(getString(R.string.nav_logout_title));
                navigationView.invalidate();
            }
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void showNearByDialog() {
        try {
            String routes = "{\"buses\":[{\"id\":1,\"route\":\"uni-to-township\"},{\"id\":2,\"route\":\"uni-to-sadar\"},{\"id\":3,\"route\":\"uni-to-rabazar\"},{\"id\":4,\"route\":\"uni-to-joraypul\"},{\"id\":5,\"route\":\"uni-to-anarkali\"}]}";
            Gson gson = new Gson();
            schedule = gson.fromJson(routes, BusSchedule.class);
            // inflate alert dialog xml
            LayoutInflater li = LayoutInflater.from(this);
            View dialogView = li.inflate(R.layout.pick_schedule, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);
            // set title
            alertDialogBuilder.setTitle("Schedule");
            // set custom dialog icon
            //alertDialogBuilder.setIcon(R.drawable.ic_launcher);
            // set custom_dialog.xml to alertdialog builder
            alertDialogBuilder.setView(dialogView);
            Spinner pick_bus_sp = dialogView.findViewById(R.id.pick_bus_sp);
            buslist = new ArrayList<>();
            buslist.add("Select Bus");
            for(BusSchedule.Schedule sch : schedule.getSchedule()){
                buslist.add(sch.getRoute());
            }
            selectedBusCounts = buslist.get(0);
            ArrayAdapter<String> dataAdapterBath = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, buslist);
            dataAdapterBath.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            pick_bus_sp.setAdapter(dataAdapterBath);
            pick_bus_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.d(TAG, "User Selected : " + selectedBusCounts);
                    selectedBusCounts = parent.getItemAtPosition(position).toString();
                    //EmailDebugLog.getInstance(SubmitAddScreen.this).writeLog("[PropertySelectionScreen] inside Spinner Value : " + selectedAreaUnit);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    // get user input and set it to etOutput
                                    // edit text
                                    Log.d(TAG, "User Selected : OK");
                                    startActivity(new Intent(MainScreen.this,MapsActivity.class));
                                    buslist.clear();

                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    dialog.cancel();
                                    buslist.clear();
                                }
                            });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();

        } catch (Exception e) {
            Log.e(TAG, "[MainScreen] inside dailoge Exception is : " + e.toString());
        }
    }
    private void showNearByDialog2() {
        try {
            String routes = "{\"buses\":[{\"id\":1,\"route\":\"uni-to-township\"},{\"id\":2,\"route\":\"uni-to-sadar\"},{\"id\":3,\"route\":\"uni-to-rabazar\"},{\"id\":4,\"route\":\"uni-to-joraypul\"},{\"id\":5,\"route\":\"uni-to-anarkali\"}]}";
            Gson gson = new Gson();
            schedule = gson.fromJson(routes, BusSchedule.class);
            // inflate alert dialog xml
            LayoutInflater li = LayoutInflater.from(this);
            View dialogView = li.inflate(R.layout.pick_schedule, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);
            // set title
            alertDialogBuilder.setTitle("Schedule");
            // set custom dialog icon
            //alertDialogBuilder.setIcon(R.drawable.ic_launcher);
            // set custom_dialog.xml to alertdialog builder
            alertDialogBuilder.setView(dialogView);
            Spinner pick_bus_sp = dialogView.findViewById(R.id.pick_bus_sp);
            buslist = new ArrayList<>();
            buslist.add("Select Bus");
            for(BusSchedule.Schedule sch : schedule.getSchedule()){
                buslist.add(sch.getRoute());
            }
            selectedBusCounts = buslist.get(0);
            ArrayAdapter<String> dataAdapterBath = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, buslist);
            dataAdapterBath.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            pick_bus_sp.setAdapter(dataAdapterBath);
            pick_bus_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.d(TAG, "User Selected : " + selectedBusCounts);
                    selectedBusCounts = parent.getItemAtPosition(position).toString();
                    //EmailDebugLog.getInstance(SubmitAddScreen.this).writeLog("[PropertySelectionScreen] inside Spinner Value : " + selectedAreaUnit);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    // get user input and set it to etOutput
                                    // edit text
                                    Log.d(TAG, "User Selected : OK");
                                    startActivity(new Intent(MainScreen.this,ScheduleActivity.class));
                                    buslist.clear();

                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    dialog.cancel();
                                    buslist.clear();
                                }
                            });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();

        } catch (Exception e) {
            Log.e(TAG, "[MainScreen] inside dailoge Exception is : " + e.toString());
        }
    }



    public class  asyncTask extends  AsyncTask<String, String, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.show();
        }

        @Override
        protected Boolean doInBackground(String... objects) {
            try {

                boolean json = HttpClient.sendPostRequestLogout(MainScreen.this, objects[0],PreferenceManager.getToken(MainScreen.this));
                return  json;
            } catch (Exception e) {
                Log.e(TAG, "[LoginScreen] inside doInBackground() Exception is :" + e.toString());
                return false;
            }


        }

        @Override
        protected void onPostExecute(Boolean o) {
            super.onPostExecute(o);
            progressBar.dismiss();
            if (o) {
                PreferenceManager.setUserName(MainScreen.this,null);
                PreferenceManager.setPassword(MainScreen.this,null);
                PreferenceManager.setToken(MainScreen.this,null);
                finish();
                Intent i = new Intent(MainScreen.this,LoginScreen.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            } else {
                //TODO something error occur
                Toast.makeText(MainScreen.this,"Unable to Logout Please try again!",Toast.LENGTH_SHORT).show();

            }
        }
    };


}

package com.cs.lgubustracker;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.cs.lgubustracker.util.Util;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Marker marker;
    List<LatLng> locList;
    private Timer timer = new Timer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //write here
        // 1. Setup
       /* URI uri = new URI("ws://cable.example.com");
        Consumer consumer = ActionCable.createConsumer(uri);

// 2. Create subscription
        Channel appearanceChannel = new Channel("AppearanceChannel");
        Subscription subscription = consumer.getSubscriptions().create(appearanceChannel);

        subscription
                .onConnected(new Subscription.ConnectedCallback() {
                    @Override
                    public void call() {
                        // Called when the subscription has been successfully completed
                    }
                }).onRejected(new Subscription.RejectedCallback() {
            @Override
            public void call() {
                // Called when the subscription is rejected by the server
            }
        }).onReceived(new Subscription.ReceivedCallback() {
            @Override
            public void call(JsonElement data) {
                // Called when the subscription receives data from the server
                Log.d("TASAWWAR",data);
                locList.add(new LatLng(31.463655, 74.441635));
            }
        }).onDisconnected(new Subscription.DisconnectedCallback() {
            @Override
            public void call() {
                // Called when the subscription has been closed
            }
        }).onFailed(new Subscription.FailedCallback() {
            @Override
            public void call(ActionCableException e) {
                // Called when the subscription encounters any error
            }
        });
        // 3. Establish connection
consumer.connect();
        */


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(31.4639155,74.4417861);
        MarkerOptions markerOptions = new MarkerOptions().position(sydney).icon(BitmapDescriptorFactory.fromBitmap(Util.getBitmapFromVectorDrawable(this, R.drawable.ic_directions_bus_black_24dp)));

        marker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
        //startTravel();
        locList = new ArrayList<>();
        locList.add(new LatLng(31.463655, 74.441635));
        locList.add(new LatLng(31.464004, 74.440846));
        locList.add(new LatLng(31.464625, 74.441339));
        locList.add(new LatLng(31.464992, 74.441919));
        locList.add(new LatLng(31.465533, 74.442371));
        locList.add(new LatLng(31.465890, 74.442070));
        locList.add(new LatLng(31.468777, 74.434902));

        for(int i =0;i<locList.size();i++){
            try {
                timer.schedule(new MyTimerTask(i), 1000,2000);
            } catch (Exception e) {

            }

        }


    }


    private class MyTimerTask extends TimerTask {
        private int i;
        MyTimerTask(int i){
            this.i = i;
        }
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    marker.setPosition(locList.get(i));
                }
            });
        }
    }

}

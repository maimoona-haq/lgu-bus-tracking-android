package com.cs.lgubustracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cs.lgubustracker.adapter.ScheduleRecyclerViewAdapter;
import com.cs.lgubustracker.model.ScheduleModal;
import com.cs.lgubustracker.model.SchedulePojo;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ScheduleRecyclerViewAdapter myJobTypeRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        String schedule = "{\"schedule\":[{\"schedule\":\"TownShip\",\"arrivalTime\":\"9:00\",\"departureTime\":\"11:00\"},{\"schedule\":\"TownShip\",\"arrivalTime\":\"9:00\",\"departureTime\":\"11:00\"},{\"schedule\":\"TownShip\",\"arrivalTime\":\"9:00\",\"departureTime\":\"11:00\"},{\"schedule\":\"TownShip\",\"arrivalTime\":\"9:00\",\"departureTime\":\"11:00\"},{\"schedule\":\"TownShip\",\"arrivalTime\":\"9:00\",\"departureTime\":\"11:00\"},{\"schedule\":\"TownShip\",\"arrivalTime\":\"9:00\",\"departureTime\":\"11:00\"},{\"schedule\":\"TownShip\",\"arrivalTime\":\"9:00\",\"departureTime\":\"11:00\"},{\"schedule\":\"TownShip\",\"arrivalTime\":\"9:00\",\"departureTime\":\"11:00\"},{\"schedule\":\"TownShip\",\"arrivalTime\":\"9:00\",\"departureTime\":\"11:00\"}]}";
        Gson gson = new Gson();
        SchedulePojo schedulePojo = gson.fromJson(schedule, SchedulePojo.class);
        myJobTypeRecyclerViewAdapter = new ScheduleRecyclerViewAdapter(schedulePojo.getSchedule(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(myJobTypeRecyclerViewAdapter);
    }
}

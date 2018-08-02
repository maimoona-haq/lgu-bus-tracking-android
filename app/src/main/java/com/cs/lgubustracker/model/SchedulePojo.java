package com.cs.lgubustracker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/***********************************
 * Created by Farhat on 7/17/2018.  *
 ***********************************/
public class SchedulePojo {
    @SerializedName("schedule")
    @Expose
    private List<ScheduleModal> schedule;

    public List<ScheduleModal> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<ScheduleModal> schedule) {
        this.schedule = schedule;
    }
}

package com.cs.lgubustracker.model;

/***********************************
 * Created by Farhat on 7/17/2018.  *
 ***********************************/
public class ScheduleModal {

    private String schedule;
    private String arrivalTime;
    private String departureTime;

    public ScheduleModal(String schedule, String arrivalTime, String departureTime) {
        this.schedule = schedule;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }
}

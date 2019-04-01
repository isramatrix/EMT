package com.emt.sostenible.data;

public class Frequencie {
    private String trip_id;
    private String start_time;
    private String end_time;
    private String headway_secs;

    public Frequencie(String trip_id, String start_time, String end_time, String headway_secs) {
        this.trip_id = trip_id;
        this.start_time = start_time;
        this.end_time = end_time;
        this.headway_secs = headway_secs;
    }

    public String getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(String trip_id) {
        this.trip_id = trip_id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getHeadway_secs() {
        return headway_secs;
    }

    public void setHeadway_secs(String headway_secs) {
        this.headway_secs = headway_secs;
    }
}

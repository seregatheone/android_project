package com.example.project_x;

import java.io.Serializable;

public class EventDb implements Serializable {
    private long id;
    private String eventname;
    private String date;

    public EventDb (long id, String eventname, String date) {
        this.id = id;
        this.eventname = eventname;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public String getEventName() {
        return eventname;
    }

    public String getDate() {
        return date;
    }
}
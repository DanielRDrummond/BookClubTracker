package com.bookclubtracker.model;

import java.util.Date;

public class Meeting {
    private int id;
    private Date meetingDate;
    private String location;
    private String description;
    private int clubId;

    // Default constructor
    public Meeting() {
    }

    // Parameterized constructor
    public Meeting(int id, Date meetingDate, String location, String description, int clubId) {
        this.id = id;
        this.meetingDate = meetingDate;
        this.location = location;
        this.description = description;
        this.clubId = clubId;
    }

    // Getter and setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Date meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    // Overriding toString method for easier debugging and logging
    @Override
    public String toString() {
        return "Meeting{" +
                "id=" + id +
                ", meetingDate=" + meetingDate +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", clubId=" + clubId +
                '}';
    }
}

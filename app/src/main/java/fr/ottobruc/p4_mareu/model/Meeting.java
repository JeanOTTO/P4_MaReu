package fr.ottobruc.p4_mareu.model;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import fr.ottobruc.mareu.utils.DateTimeUtil;

public class Meeting {
    private int id;
    private Date startTime;
    private Date endTime;
    private Date date;
    private Room location;
    private String subject;
    private List<User> participants;



    public Meeting(int id, Date startTime, Date endTime, Date date, Room location, String subject, List<User> participants) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.location = location;
        this.subject = subject;
        this.participants = participants;
    }

    public Meeting(int id, String startTime, String endTime, String date, Room location, String subject, List<User> participants) throws ParseException {
        this.id = id;
        this.startTime = DateTimeUtil.stringsToDate(date, startTime);
        this.endTime = DateTimeUtil.stringsToDate(date, endTime);
        this.date = DateTimeUtil.stringsToDate(date, null);
        this.location = location;
        this.subject = subject;
        this.participants = participants;
    }

    public int getId() {
        return id;
    }
    public Date getDate() {
        return date;
    }
    public Room getLocation() {
        return location;
    }
    public String getSubject() {
        return subject;
    }
    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setLocation(Room location) {
        this.location = location;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

}

package DAO.POJO;

import java.util.Calendar;

public class Appointment {
    private int appointmentId; // PK
    private int customerId; // FK
    private int userId; // FK
    private String title;
    private String description;
    private String location;
    private String contact; // customer
    private String type;
    private String url;
    private Calendar start; // start Date
    private Calendar end; // end Date
    private Calendar createDate;
    private String createdBy; // userName
    private Calendar lastUpdate;
    private String lastUpdateBy;

    public Appointment(int appointmentId, int customerId, int userId, String title, String description, String location, String contact, String type, String url, Calendar start, Calendar end, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy) {
        this.appointmentId = appointmentId; // PK
        this.customerId = customerId; // FK customer - on update & delete restrict
        this.userId = userId; // FK user - on update & delete restrict
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    public int getAppointmentId() {
        // PK check
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getCustomerId() {
        // FK check
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        // FK check
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Calendar getStart() {
        return start;
    }

    public void setStart(Calendar start) {
        this.start = start;
    }

    public Calendar getEnd() {
        return end;
    }

    public void setEnd(Calendar end) {
        this.end = end;
    }

    public Calendar getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Calendar getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Calendar lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    @Override
    public String toString() {
        return   "Appointment{" +
                "appointmentId=" + getAppointmentId() + System.lineSeparator() +
                "customerId='" + getCustomerId() + '\'' + System.lineSeparator() +
                "userId=" + getUserId() + '\'' + System.lineSeparator() +
                "title=" + getTitle() + '\'' + System.lineSeparator() +
                "description=" + getDescription() +'\'' + System.lineSeparator() +
                "location=" + getLocation()  +'\'' + System.lineSeparator() +
                "contact=" + getContact()  +'\'' + System.lineSeparator() +
                "type=" + getType()  +'\'' + System.lineSeparator() +
                "url=" + getUrl()  +'\'' + System.lineSeparator() +
                "start=" + getStart()  +'\'' + System.lineSeparator() +
                "end=" + getEnd()  +'\'' + System.lineSeparator() +
                "createDate=" + getCreateDate()  + '\'' + System.lineSeparator() +
                "createdBy=" + getCreatedBy()  +'\'' + System.lineSeparator() +
                "lastUpdate=" + getLastUpdate()  +'\'' + System.lineSeparator() +
                "lastUpdateBy=" + getLastUpdateBy()  +'\'' + System.lineSeparator() +
                '}';
    }

}

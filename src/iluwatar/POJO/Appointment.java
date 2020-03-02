package iluwatar.POJO;

/**
 * An appointment POJO that represents the data that will be read from the data source.
 */

public class Appointment {
    private int id; // PK
    private int customerId;
    private int userId;

    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private String url;

    private String start;
    private String end;

    private String createDate;
    private String createdBy;
    private String lastUpdate;
    private String lastUpdateBy;

    public Appointment(int id, int customerId, int userId, String title, String description, String location, String contact, String type, String url, String start, String end, String createDate, String createdBy, String lastUpdate, String lastUpdateBy) {
        this.id = id;
        this.customerId = customerId;
        this.userId = userId;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
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
                "appointmentId=" + getId() +
                ", customerId='" + getCustomerId() + '\'' +
                ", userId=" + getUserId() + '\'' +
                ", title=" + getTitle() + '\'' +
                ", description=" + getDescription() +'\'' +
                ", location=" + getLocation()  +'\'' +
                ", contact=" + getContact()  +'\'' +
                ", type=" + getType()  +'\'' +
                ", url=" + getUrl()  +'\'' +
                ", start=" + getStart()  +'\'' +
                ", end=" + getEnd()  +'\'' +
                ", createDate=" + getCreateDate()  +'\'' +
                ", createdBy=" + getCreatedBy()  +'\'' +
                ", lastUpdate=" + getLastUpdate()  +'\'' +
                ", lastUpdateBy=" + getLastUpdateBy()  +'\'' +
                '}';
    }

    @Override
    public boolean equals(final Object that) {
        var isEqual = false;
        if (this == that) {
            isEqual = true;
        } else if( that != null && getClass() == that.getClass()) {
            final var address = (iluwatar.POJO.Address) that;
            if (getId() == address.getId()) {
                isEqual = true;
            }
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        return getId();
    }
}



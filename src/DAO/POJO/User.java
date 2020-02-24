package DAO.POJO;

import java.util.Calendar;

public class User {
    private int userId;
    private String userName;
    private String password;
    private boolean active = false;
    private Calendar createDate;
    private String createdBy;
    private Calendar lastUpdate;
    private String getLastUpdate;

    public User(int userId, String userName, String password, boolean active, Calendar createDate, String createdBy, Calendar lastUpdate, String getLastUpdate) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.active = active;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.getLastUpdate = getLastUpdate;
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId, boolean isUnique) {
        // PK
        this.userId = userId;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
         this.active = active;
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

    public String getGetLastUpdate() {
        return getLastUpdate;
    }

    public void setGetLastUpdate(String getLastUpdate) {
        this.getLastUpdate = getLastUpdate;
    }

    public String getUserName() {
        return userName;
    }

    public final void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public final void setPassword(String password) {
        this.password = password;
    }
}

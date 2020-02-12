package model;

import java.util.Calendar;

public class City {
    private int cityId; // PK -
    private String city;
    private int countryId; // FK -> country as city_ibfk_1 -- on update & delete restrict -
    private Calendar createDate;
    private String createdBy; // userName
    private Calendar lastUpdate;
    private String lastUpdatedBy; // userName

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        // PK check
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
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

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public City(int cityId, String city, int countryId, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdatedBy) {
        this.cityId = cityId;
        this.city = city;
        this.countryId = countryId;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }
}

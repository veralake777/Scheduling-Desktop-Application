package POJO;

public class City {
    private int id; // PK -
    private String city;
    private int countryId; // FK -> country as city_ibfk_1 -- on update & delete restrict -
    private String createDate;
    private String createdBy; // userName
    private String lastUpdate;
    private String lastUpdateBy; // userName

    public int getId() {
        return id;
    }

    public void setId(int cityId) {
        // PK check
        this.id = cityId;
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

    public City(int cityId, String city, int countryId, String createDate, String createdBy, String lastUpdate, String lastUpdatedBy) {
        this.id = cityId;
        this.city = city;
        this.countryId = countryId;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdatedBy;
    }
}

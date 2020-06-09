package DAO.POJO;

import java.util.Calendar;

/**
 * Data of addressLine1, addressLine2, city, postalCode, and country are fetched from their accompanying DAO in
 * the CustomerPresentationState.
 */

public class Customer {

    // customer data
    private int id; // PK
    private String name;
    private int addressId; // FK --> customer --customer_ibfk_1 -- on update & delete restrict
    private boolean active; // 1 = active, 0 = inactive


    // for db use only
    private Calendar createDate;
    private String createdBy; // userName
    private Calendar lastUpdate;
    private String lastUpdateBy; // userName

    // for DAO methods
    public Customer(int id, String name, int addressId, boolean active, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy) {
        this.id = id;
        this.name = name;
        this.addressId = addressId;
        this.active = active;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        // PK check
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        // FK check
        this.addressId = addressId;
    }

    public boolean isActive() {
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

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }
}

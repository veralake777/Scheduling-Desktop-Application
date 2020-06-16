package POJO;

import java.sql.Timestamp;

public class Customer {
    private int id; // PK
    private String customerName;
    private int addressId;
    private boolean active;
    private Timestamp createDate;
    private String createdBy; // userName
    private Timestamp lastUpdate;
    private String lastUpdateBy; // userName

    // without active, create, last update info
    public Customer(int id, String customerName, int addressId, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdateBy) {
        this.id = id;
        this.customerName = customerName;
        this.addressId = addressId;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    public Customer(int id, String customerName, int addressId, boolean active, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdateBy) {
        this.id = id;
        this.customerName = customerName;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
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
        return this.getCustomerName();
    }
}

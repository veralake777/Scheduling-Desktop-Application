package DAO.POJO;

import java.util.Calendar;

public class Customer {

    private int customerId; // PK
    private String customerName;
    private int addressId; // FK --> customer --customer_ibfk_1 -- on update & delete restrict
    private boolean active; // 1 = active, 0 = inactive

    // fomr join
    private String address;
    private String address2;
    private String city;
    private String country;

    // for db use only
    private Calendar createDate;
    private String createdBy; // userName
    private Calendar lastUpdate;
    private String lastUpdateBy; // userName

    // for CRUD methods
    public Customer(int customerId, String customerName, int addressId, boolean active, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.addressId = addressId;
        this.active = active;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * @link CustomerDao.getAllCustomers
     * @param customerIdG
     * @param customerName
     * @param activeBool
     * @param address
     * @param city
     * @param country
     */
    // for tableViews and Widgets
    public Customer(int customerIdG, String customerName, String address, String city, String country) {
        this.customerId = customerIdG;
        this. customerName = customerName;
//        this.active = activeBool;
        this.address = address;
//        this.address2 = address2;
        this.city = city;
        this.country = country;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        // PK check
        this.customerId = customerId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

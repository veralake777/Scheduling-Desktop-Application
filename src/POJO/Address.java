package POJO;

import DbDao.DbCityDao;
import utils.CustomException;
import utils.DBUtils;

import java.io.IOException;
import java.sql.SQLException;

/**
 * An address POJO that represents the data that will be read from the data source.
 */

public class Address {
    private int id; // PK
    private String address;
    private String address2;
    private int cityId; // FK as address_ibfk_1 -- on update and delete restrict
    private String postalCode;
    private String phone;
    private String createDate;
    private String createdBy;
    private String lastUpdate;
    private String lastUpdateBy;

    /*
    Creates an instance of address
     */

    public Address(final int addressId, final String address, final String address2, final int cityId,
                   final String postalCode, final String phone, final String createDate, final String createdBy,
                   final String lastUpdate, final String lastUpdateBy) {
        this.id = addressId;
        this.address = address;
        this.address2 = address2;
        this.cityId = cityId;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    public Address(int addressId, String address, String address2, String city, String postalCode, String phone) throws IOException, CustomException, SQLException {
        this.id = addressId;
        this.address = address;
        this.address2 = address2;
        this.cityId = new DbCityDao(DBUtils.getMySQLDataSource()).getByName(city);
        this.postalCode = postalCode;
        this.phone = phone;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
        return   "Address{" +
                "addressId=" + getId() +
                ", address='" + getAddress() + '\'' +
                ", address2=" + getAddress2() + '\'' +
                ", cityId=" + getCityId() + '\'' +
                ", postalCode=" + getPostalCode() +'\'' +
                ", phone=" + getPhone()  +'\'' +
                '}';
    }

    @Override
    public boolean equals(final Object that) {
        var isEqual = false;
        if (this == that) {
            isEqual = true;
        } else if( that != null && getClass() == that.getClass()) {
            final var address = (Address) that;
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



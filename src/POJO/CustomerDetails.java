package POJO;

public class CustomerDetails {
    int customerId;
    String customerName;
    String phone;
    String address;
    String address2;
    String city;
    String postalCode;
    String country;

    public CustomerDetails(Integer customerId, String customerName, String phone, String address, String address2, String city, String postalCode, String country) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.phone = phone;
        this.address = address;
        this.address2 = address2;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return   "CustomerDetails{" +
                "customerName=" + getCustomerName() +
                ", phone='" + getPhone() + '\'' +
                ", address1=" + getAddress() + '\'' +
                ", address2=" + getAddress2() + '\'' +
                ", city=" + getCity() +'\'' +
                ", country=" + getCountry()  +'\'' +
                '}';
    }

}

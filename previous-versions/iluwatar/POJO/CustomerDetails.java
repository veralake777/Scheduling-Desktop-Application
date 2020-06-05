package iluwatar.POJO;

public class CustomerDetails {
    int id;
    String name;
    String phone;
    String address1;
    String address2;
    String city;
    String postalCode;
    String country;

    public CustomerDetails(Integer id, String name, String phone, String address1, String address2, String city, String postalCode, String country) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
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
                "customerName=" + getName() +
                ", phone='" + getPhone() + '\'' +
                ", address1=" + getAddress1() + '\'' +
                ", address2=" + getAddress2() + '\'' +
                ", city=" + getCity() +'\'' +
                ", country=" + getCountry()  +'\'' +
                '}';
    }

}

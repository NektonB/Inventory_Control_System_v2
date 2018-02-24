package Modules;

public class Address {

    private int id;
    private String number;
    private String line01;
    private String line02;
    private String city;
    private String country;
    private String postalCode;

    public void resetAll() {
        id = 0;
        number = "";
        line01 = "";
        line02 = "";
        city = "";
        country = "";
        postalCode = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLine01() {
        return line01;
    }

    public void setLine01(String line01) {
        this.line01 = line01;
    }

    public String getLine02() {
        return line02;
    }

    public void setLine02(String line02) {
        this.line02 = line02;
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}

package Modules;

public class Employee {

    private int id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String dob;
    private String nic;
    private String joinDate;
    private String address;
    private String contactNO;
    private String activatonStatus;

    public void resetAll() {
        id = 0;
        firstName = "";
        middleName = "";
        lastName = "";
        dob = "";
        nic = "";
        joinDate = "";
        address = "";
        contactNO = "";
        activatonStatus = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNO() {
        return contactNO;
    }

    public void setContactNO(String contactNO) {
        this.contactNO = contactNO;
    }

    public String getActivatonStatus() {
        return activatonStatus;
    }

    public void setActivatonStatus(String activatonStatus) {
        this.activatonStatus = activatonStatus;
    }
}

package Modules;

public class CompanyPartner {

    private int id;
    private String partner;

    public void resetAll(){
        id = 0;
        partner = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }
}

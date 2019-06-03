package Modules;

public class CustomerType {

    private int id;
    private String type;

    public void resetAll() {
        id = 0;
        type = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

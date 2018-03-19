package Modules;

public class Approve {

    private int id;
    private String status;

    public void resetAll() {
        id = 0;
        status = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

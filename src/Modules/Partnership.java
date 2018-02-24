package Modules;

public class Partnership {
    private int id;
    private String partnership;

    public void resetAll() {
        id = 0;
        partnership = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPartnership() {
        return partnership;
    }

    public void setPartnership(String partnership) {
        this.partnership = partnership;
    }
}

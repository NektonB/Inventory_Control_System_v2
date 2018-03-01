package Modules;

public class Unit {

    int id;
    String unit;

    public void resetAll() {
        id = 0;
        unit = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}

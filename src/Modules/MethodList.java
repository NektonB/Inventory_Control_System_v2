package Modules;

public class MethodList {

    private int id;
    private double payedValue;

    public void resetAll(){
        id = 0;
        payedValue = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPayedValue() {
        return payedValue;
    }

    public void setPayedValue(double payedValue) {
        this.payedValue = payedValue;
    }
}

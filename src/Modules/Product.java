package Modules;

public class Product {

    String code;
    String barCode;
    String name;
    double refillingQty;

    public void resetAll(){
        code = "";
        barCode = "";
        name = "";
        refillingQty = 0;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRefillingQty() {
        return refillingQty;
    }

    public void setRefillingQty(double refillingQty) {
        this.refillingQty = refillingQty;
    }
}

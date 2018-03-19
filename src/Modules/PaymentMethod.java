package Modules;

public class PaymentMethod {

    private int id;
    private int supplierId;

    public void resetAll(){
        id  = 0;
        supplierId = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }
}

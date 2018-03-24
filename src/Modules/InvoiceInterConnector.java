package Modules;

public class InvoiceInterConnector {

    String productCode;
    double salePrice;
    double quantity;

    public void resetAll() {
        productCode = "";
        salePrice = 0;
        quantity = 0;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}

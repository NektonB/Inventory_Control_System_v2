package Modules;

public class InvoiceItems {

    private int id;
    private double salePrice;
    private double quantity;
    private double totalAmount;
    private double discValue;
    private double discRate;
    private double netAmount;
    private String itemStatus;
    private String stockIdList;

    public void resetAll() {
        id = 0;
        salePrice = 0;
        quantity = 0;
        discValue = 0;
        discRate = 0;
        netAmount = 0;
        itemStatus = "IN STOCK";
        stockIdList = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getDiscValue() {
        return discValue;
    }

    public void setDiscValue(double discValue) {
        this.discValue = discValue;
    }

    public double getDiscRate() {
        return discRate;
    }

    public void setDiscRate(double discRate) {
        this.discRate = discRate;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(double netAmount) {
        this.netAmount = netAmount;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public String getStockIdList() {
        return stockIdList;
    }

    public void setStockIdList(String stockIdList) {
        this.stockIdList = stockIdList;
    }
}

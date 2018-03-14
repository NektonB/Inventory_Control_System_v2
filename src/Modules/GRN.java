package Modules;

import java.time.LocalDate;
import java.time.LocalTime;

public class GRN {

    int id;
    String date;
    String time;
    double itemCount;
    double totalAmount;
    double totalDiscValue;
    double grossAmount;
    double manualDiscValue;
    double netDiscValue;
    double netAmount;
    double payedAmount;
    double deuAmount;

    public void resetAll(){
        id = 0;
        date = LocalDate.now().toString();
        time = LocalTime.now().toString();
        itemCount = 0;
        totalAmount = 0;
        totalDiscValue = 0;
        grossAmount = 0;
        manualDiscValue = 0;
        netDiscValue = 0;
        netAmount = 0;
        payedAmount = 0;
        deuAmount = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getItemCount() {
        return itemCount;
    }

    public void setItemCount(double itemCount) {
        this.itemCount = itemCount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalDiscValue() {
        return totalDiscValue;
    }

    public void setTotalDiscValue(double totalDiscValue) {
        this.totalDiscValue = totalDiscValue;
    }

    public double getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(double grossAmount) {
        this.grossAmount = grossAmount;
    }

    public double getManualDiscValue() {
        return manualDiscValue;
    }

    public void setManualDiscValue(double manualDiscValue) {
        this.manualDiscValue = manualDiscValue;
    }

    public double getNetDiscValue() {
        return netDiscValue;
    }

    public void setNetDiscValue(double netDiscValue) {
        this.netDiscValue = netDiscValue;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(double netAmount) {
        this.netAmount = netAmount;
    }

    public double getPayedAmount() {
        return payedAmount;
    }

    public void setPayedAmount(double payedAmount) {
        this.payedAmount = payedAmount;
    }

    public double getDeuAmount() {
        return deuAmount;
    }

    public void setDeuAmount(double deuAmount) {
        this.deuAmount = deuAmount;
    }
}

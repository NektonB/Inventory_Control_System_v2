package Modules;

import java.time.LocalDate;
import java.time.LocalTime;

public class Invoice {

    int id;
    String date;
    String time;
    double itemCount;
    double totalAmount;
    double grossDiscount;
    double manualDiscount;
    double netDiscount;
    double netAmount;
    double payedValue;
    double deuAmount;

    public void resetAll() {
        id = 0;
        date = LocalDate.now().toString();
        time = LocalTime.now().toString();
        itemCount = 0;
        totalAmount = 0;
        grossDiscount = 0;
        manualDiscount = 0;
        netDiscount = 0;
        netAmount = 0;
        payedValue = 0;
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

    public double getGrossDiscount() {
        return grossDiscount;
    }

    public void setGrossDiscount(double grossDiscount) {
        this.grossDiscount = grossDiscount;
    }

    public double getManualDiscount() {
        return manualDiscount;
    }

    public void setManualDiscount(double manualDiscount) {
        this.manualDiscount = manualDiscount;
    }

    public double getNetDiscount() {
        return netDiscount;
    }

    public void setNetDiscount(double netDiscount) {
        this.netDiscount = netDiscount;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(double netAmount) {
        this.netAmount = netAmount;
    }

    public double getPayedValue() {
        return payedValue;
    }

    public void setPayedValue(double payedValue) {
        this.payedValue = payedValue;
    }

    public double getDeuAmount() {
        return deuAmount;
    }

    public void setDeuAmount(double deuAmount) {
        this.deuAmount = deuAmount;
    }
}

package Modules;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.TableView;

public class InvoiceInterConnector {

    String productCode;
    double salePrice;
    double quantity;
    private JFXTextField txtSalePrice;
    private JFXTextField txtFocus;
    private JFXTextField txtFocusSearch;
    private TableView<?> tblItem;

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

    public JFXTextField getTxtSalePrice() {
        return txtSalePrice;
    }

    public void setTxtSalePrice(JFXTextField txtSalePrice) {
        this.txtSalePrice = txtSalePrice;
    }

    public TableView<?> getTblItem() {
        return tblItem;
    }

    public void setTblItem(TableView<?> tblItem) {
        this.tblItem = tblItem;
    }

    public JFXTextField getTxtFocus() {
        return txtFocus;
    }

    public void setTxtFocus(JFXTextField txtFocus) {
        this.txtFocus = txtFocus;
    }

    public JFXTextField getTxtFocusSearch() {
        return txtFocusSearch;
    }

    public void setTxtFocusSearch(JFXTextField txtFocusSearch) {
        this.txtFocusSearch = txtFocusSearch;
    }
}

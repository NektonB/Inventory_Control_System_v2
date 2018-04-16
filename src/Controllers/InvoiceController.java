package Controllers;

import DataControllers.DataReader;
import DataControllers.DataWriter;
import Modules.Category;
import Modules.ComponentSwitcher;
import Modules.InvoiceInterConnector;
import Modules.Product;
import com.gluonhq.charm.glisten.layout.Layer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class InvoiceController implements Initializable {

    @FXML
    private JFXTextField txtCode;

    @FXML
    private JFXTextField txtBarCode;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtCategory;

    @FXML
    private JFXTextField txtQuantity;

    @FXML
    private JFXTextField txtSalePrice;

    @FXML
    private JFXTextField txtCost;

    @FXML
    private JFXTextField txtDisValue;

    @FXML
    private JFXTextField txtDisPrecentage;

    @FXML
    private JFXButton btnAddToList;

    @FXML
    private JFXButton btnGoToPurchase;

    @FXML
    private TableView<Items> tblItems;

    @FXML
    private TableColumn<Items, String> tcCode;

    @FXML
    private TableColumn<Items, String> tcName;

    @FXML
    private TableColumn<Items, Double> tcQuantity;

    @FXML
    private TableColumn<Items, Double> tcSalePrice;

    @FXML
    private TableColumn<Items, Double> txTotalCost;

    @FXML
    private TableColumn<Items, Double> tcDiscValue;

    @FXML
    private TableColumn<Items, Double> tcDiscPrecentage;

    @FXML
    private TableColumn<Items, String> tcIdList;

    @FXML
    private JFXButton btnRemveItem;

    @FXML
    private JFXButton btnRemoveAll;

    @FXML
    private Layer lyCode;

    @FXML
    private JFXListView<String> lvCode;

    @FXML
    private Layer lyName;

    @FXML
    private JFXListView<String> lvName;

    TextValidator validator;
    DataWriter dataWriter;
    DataReader dataReader;
    Alerts alerts;
    Product product;
    Category category;
    ComponentSwitcher switcher;
    InvoiceInterConnector interConnector;

    Map<Integer, Double> qtyList;
    String idList = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            /*
             * Load Supporting classes by thread
             * All supporting classes load in the thread
             * call the table ready method outside of the thread
             * */
            //Thread readyData = new Thread(() -> {
            alerts = ObjectGenerator.getAlerts();
            dataWriter = ObjectGenerator.getDataWriter();
            dataReader = ObjectGenerator.getDataReader();
            validator = ObjectGenerator.getTextValidator();
            product = ObjectGenerator.getProduct();
            category = ObjectGenerator.getCategory();
            switcher = ObjectGenerator.getComponentSwitcher();
            interConnector = ObjectGenerator.getInterConnector();

            validator.validateDigit(txtQuantity, 10, 2);
            validator.validateDigit(txtSalePrice, 10, 2);
            validator.validateDigit(txtDisValue, 10, 2);
            validator.validateDigit(txtDisPrecentage, 3, 2);

            qtyList = new HashMap<>();


            //});
            //readyData.setName("Invoice Controller");
            //readyData.start();
            readyTable();
            lvCode.setVisible(false);
            lvName.setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    private void readyTable() {
        tcCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tcSalePrice.setCellValueFactory(new PropertyValueFactory<>("salePrice"));
        txTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        tcDiscValue.setCellValueFactory(new PropertyValueFactory<>("discValue"));
        tcDiscPrecentage.setCellValueFactory(new PropertyValueFactory<>("discPercentage"));
        tcIdList.setCellValueFactory(new PropertyValueFactory<>("idList"));
    }

    public void resetText() {
        txtCode.setText("");
        txtBarCode.setText("");
        txtName.setText("");
        txtCategory.setText("");
        txtQuantity.setText("0");
        txtSalePrice.setText("0");
        txtCost.setText("0");
        txtDisValue.setText("0");
        txtDisPrecentage.setText("0");
        txtCode.requestFocus();
        idList = "";
    }

    public void autoHideListView() {
        if (lvCode.isVisible()) {
            lvCode.setVisible(false);
        } else if (lvName.isVisible()) {
            lvName.setVisible(false);
        }
    }

    public void getProductByCode(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                product.setCode(txtCode.getText());

                dataReader.getProductByCode();

                txtBarCode.setText(product.getBarCode());
                txtName.setText(product.getName());
                txtCategory.setText(category.getName());

                loadProductView();

                txtQuantity.requestFocus();

                product.resetAll();
                category.resetAll();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    public void getProductByBarCode(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                product.setBarCode(txtBarCode.getText());

                dataReader.getProductByBarCode();

                txtCode.setText(product.getCode());
                txtName.setText(product.getName());
                txtCategory.setText(category.getName());

                txtQuantity.requestFocus();

                product.resetAll();
                category.resetAll();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    public void getProductByName(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                product.setName(txtName.getText());

                dataReader.getProductByName();

                txtCode.setText(product.getCode());
                txtBarCode.setText(product.getBarCode());
                txtCategory.setText(category.getName());

                loadProductView();

                txtQuantity.requestFocus();

                product.resetAll();
                category.resetAll();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    public double roundValue(double value) {
        value = Math.round(value * 100.00) / 100.00;
        return value;
    }

    public void calculateTotalCost() {
        double quantity = 0, purchasePrice = 0, salePrice = 0, totalCost = 0;
        try {
            if (txtQuantity.getText().isEmpty()) {
                quantity = 0;
            } else {
                quantity = Double.parseDouble(txtQuantity.getText());
            }

            if (txtSalePrice.getText().isEmpty()) {
                purchasePrice = 0;
            } else {
                purchasePrice = Double.parseDouble(txtSalePrice.getText());
            }

            if (txtSalePrice.getText().isEmpty()) {
                salePrice = 0;
            } else {
                salePrice = Double.parseDouble(txtSalePrice.getText());
            }

            totalCost = (quantity * purchasePrice);
            totalCost = roundValue(totalCost);
            txtCost.setText(Double.toString(totalCost));

        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void calculateDisPercentage() {
        double discValue = 0, discPrecentage = 0, totalCost = 0;
        try {
            if (txtCost.getText().isEmpty()) {
                totalCost = 0;
            } else {
                totalCost = Double.parseDouble(txtCost.getText());
            }

            if (txtDisValue.getText().isEmpty()) {
                discValue = 0;
            } else {
                discValue = Double.parseDouble(txtDisValue.getText());
            }

            discPrecentage = ((discValue / totalCost) * 100);
            discPrecentage = roundValue(discPrecentage);

            txtDisPrecentage.setText(Double.toString(discPrecentage));

        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void calculateDisValue() {
        double discValue = 0, discPercentage = 0, totalCost = 0;
        try {
            if (txtCost.getText().isEmpty()) {
                totalCost = 0;
            } else {
                totalCost = Double.parseDouble(txtCost.getText());
            }

            if (txtDisPrecentage.getText().isEmpty()) {
                discPercentage = 0;
            } else {
                discPercentage = Double.parseDouble(txtDisPrecentage.getText());
            }

            discValue = ((discPercentage * totalCost) / 100);
            discValue = roundValue(discValue);

            txtDisValue.setText(Double.toString(discValue));

        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void addItemToTable() {
        try {

            int decreaseItemQuantity = decreaseItemQuantity();

            if (decreaseItemQuantity > 0) {
                ObservableList<Items> itemList;
                itemList = tblItems.getItems();
                itemList.add(new Items(txtCode.getText(), txtName.getText(), Double.parseDouble(txtQuantity.getText()), Double.parseDouble(txtSalePrice.getText()), Double.parseDouble(txtCost.getText()), Double.parseDouble(txtDisValue.getText()), Double.parseDouble(txtDisPrecentage.getText()), idList));
                tblItems.setItems(itemList);
                System.out.println(idList);
                resetText();
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void addItemToTableKey(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            addItemToTable();
        }
    }

    public void txtQuantityKeyReleased(KeyEvent event) {

        calculateTotalCost();

        if (event.getCode().equals(KeyCode.ENTER)) {
            addItemToTable();
        }
    }

    public void txtSalePriceKeyReleased(KeyEvent event) {
        //calculateTotalCost();
        if (event.getCode().equals(KeyCode.ENTER)) {
            addItemToTable();
        }
    }

    public void txtDiscValueKeyReleased(KeyEvent event) {
        calculateDisPercentage();
        if (event.getCode().equals(KeyCode.ENTER)) {
            addItemToTable();
        }
    }

    public void txtDiscPercentageKeyReleased(KeyEvent event) {
        calculateDisValue();
        if (event.getCode().equals(KeyCode.ENTER)) {
            addItemToTable();
        }
    }

    public void removeRow() {
        try {
            int increaseProductQuantity = 0;
            if (tblItems.getItems().isEmpty()) {
                alerts.getWarningNotify("Warning !", "No more rows here...");
            } else {
                Items item = tblItems.getSelectionModel().getSelectedItem();

                String[] split = item.idList.get().split(",");

                for (String strId : split) {
                    int stockId = Integer.parseInt(strId);
                    increaseProductQuantity = dataWriter.increaseProductQuantity(stockId, qtyList.get(stockId));
                    if (increaseProductQuantity > 0) {
                        qtyList.remove(stockId);
                    }
                }

                if (increaseProductQuantity > 0) {
                    tblItems.getItems().remove(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void removeAll() {
        try {
            if (tblItems.getItems().isEmpty()) {
                alerts.getWarningNotify("Warning !", "No more rows here...");
            } else {
                int increaseProductQuantity = 0;
                ObservableList<? extends TableColumn<?, ?>> columns = tblItems.getColumns();

                for (int i = 0; i < tblItems.getItems().size(); ++i) {

                    String[] split = columns.get(7).getCellObservableValue(i).getValue().toString().split(",");

                    for (String strId : split) {

                        int stockId = Integer.parseInt(strId);
                        increaseProductQuantity = dataWriter.increaseProductQuantity(stockId, qtyList.get(stockId));

                        if (increaseProductQuantity > 0) {
                            qtyList.remove(stockId);
                        }
                    }
                }

                if (increaseProductQuantity > 0) {
                    qtyList.clear();
                    tblItems.getItems().clear();
                    resetText();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void tblItemsKeyReleased(KeyEvent event) {
        if (event.isShiftDown() & event.getCode().equals(KeyCode.DELETE)) {
            removeAll();
        } else if (event.getCode().equals(KeyCode.DELETE)) {
            removeRow();
        }
    }

    public void readyCodeAutoCompleter(KeyEvent event) {
        try {
            dataReader.autoCompleteProductCode(lvCode, txtCode);

            if (!lvCode.getItems().isEmpty()) {
                if (!lvCode.isVisible()) {
                    lvCode.setVisible(true);
                }
            } else {
                if (lvCode.isVisible()) {
                    lvCode.setVisible(false);
                }
            }

            if (event.getCode() == KeyCode.RIGHT) {
                lvCode.requestFocus();
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void completeProductCode(KeyEvent event) {
        if (event.getCode() == KeyCode.LEFT) {
            txtCode.requestFocus();
        }
        if (event.getCode() == KeyCode.ENTER) {
            txtCode.setText(lvCode.getSelectionModel().getSelectedItem());
            txtCode.requestFocus();
            lvCode.setVisible(false);
            getProductByCode(event);
        }
    }

    public void loadProductView() {
        try {

            interConnector.setProductCode(txtCode.getText());
            interConnector.setTxtSalePrice(txtSalePrice);

            Stage productViewStage = new Stage();
            Parent frmProductView = FXMLLoader.load(getClass().getClassLoader().getResource("Views/frmProductList.fxml"));
            productViewStage.setTitle("Product View");
            Scene scene = new Scene(frmProductView);
            productViewStage.setScene(scene);
            productViewStage.initStyle(StageStyle.UTILITY);
            productViewStage.setResizable(false);
            productViewStage.initModality(Modality.APPLICATION_MODAL);
            productViewStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void txtCodeKeyReleased(KeyEvent event) {
        try {
            readyCodeAutoCompleter(event);
            getProductByCode(event);
            loadGRNPurchaseKey(event);
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void readyNameAutoCompleter(KeyEvent event) {
        try {
            dataReader.autoCompleteProductName(lvName, txtName);

            if (!lvName.getItems().isEmpty()) {
                if (!lvName.isVisible()) {
                    lvName.setVisible(true);
                }
            } else {
                if (lvName.isVisible()) {
                    lvName.setVisible(false);
                }
            }

            if (event.getCode() == KeyCode.RIGHT) {
                lvName.requestFocus();
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void completeProductName(KeyEvent event) {
        if (event.getCode() == KeyCode.LEFT) {
            txtName.requestFocus();
        }
        if (event.getCode() == KeyCode.ENTER) {
            txtName.setText(lvName.getSelectionModel().getSelectedItem());
            txtName.requestFocus();
            lvName.setVisible(false);
            getProductByName(event);
        }
    }

    public void txtNameKeyReleased(KeyEvent event) {
        try {
            readyNameAutoCompleter(event);
            getProductByName(event);
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void loadInvoiceBuy() {
        try {
            if (tblItems.getItems().isEmpty()) {
                Toolkit.getDefaultToolkit().beep();
                alerts.getWarningNotify("Warning", "Cart is empty.Please add at least one");
            } else {
                interConnector.setTblItem(tblItems);

                Stage GrnStage = new Stage();
                Parent frmGRNPurchase = FXMLLoader.load(getClass().getClassLoader().getResource("Views/frmInvoiceSummary.fxml"));
                GrnStage.setTitle("Invoice Summary");
                Scene scene = new Scene(frmGRNPurchase);
                GrnStage.setScene(scene);
                GrnStage.initStyle(StageStyle.UTILITY);
                GrnStage.setResizable(false);
                GrnStage.initModality(Modality.APPLICATION_MODAL);
                GrnStage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadGRNPurchaseKey(KeyEvent event) {
        if (event.isControlDown() & event.getCode().equals(KeyCode.F)) {
            loadInvoiceBuy();
        }
    }

    private int decreaseItemQuantity() {
        int decreaseProductQuantity = 0;
        try {
            Map<Integer, Double> ids = dataReader.getStockIdsByProduct();
            double qty = Double.parseDouble(txtQuantity.getText());
            int done = 0;
            for (int stockId : ids.keySet()) {
                if (done == 1) {
                    break;
                } else {
                    if (ids.get(stockId) == qty) {

                        qtyList.put(stockId, ids.get(stockId));
                        idList += stockId + ",";

                        qty -= ids.get(stockId);
                        decreaseProductQuantity = dataWriter.decreaseProductQuantity(stockId, ids.get(stockId));
                        if (decreaseProductQuantity > 0) {
                            done = 1;
                        }
                    } else if (ids.get(stockId) < qty) {

                        qtyList.put(stockId, ids.get(stockId));
                        idList += stockId + ",";

                        qty -= ids.get(stockId);
                        decreaseProductQuantity = dataWriter.decreaseProductQuantity(stockId, ids.get(stockId));
                    } else if (ids.get(stockId) > qty) {

                        qtyList.put(stockId, ids.get(stockId));
                        idList += stockId + ",";

                        decreaseProductQuantity = dataWriter.decreaseProductQuantity(stockId, qty);
                        if (decreaseProductQuantity > 0) {
                            done = 1;
                        }
                    }
                }
            }
            interConnector.resetAll();
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
        return decreaseProductQuantity;
    }

    public static class Items {

        SimpleStringProperty code;
        SimpleStringProperty name;
        SimpleDoubleProperty quantity;
        SimpleDoubleProperty salePrice;
        SimpleDoubleProperty totalCost;
        SimpleDoubleProperty discValue;
        SimpleDoubleProperty discPercentage;
        SimpleStringProperty idList;

        public Items(String code, String name, double quantity, double salePrice, double totalCost, double discValue, double discPercentage, String idList) {
            this.code = new SimpleStringProperty(code);
            this.name = new SimpleStringProperty(name);
            this.quantity = new SimpleDoubleProperty(quantity);
            this.salePrice = new SimpleDoubleProperty(salePrice);
            this.totalCost = new SimpleDoubleProperty(totalCost);
            this.discValue = new SimpleDoubleProperty(discValue);
            this.discPercentage = new SimpleDoubleProperty(discPercentage);
            this.idList = new SimpleStringProperty(idList);
        }

        public String getCode() {
            return code.get();
        }

        public SimpleStringProperty codeProperty() {
            return code;
        }

        public void setCode(String code) {
            this.code.set(code);
        }

        public String getName() {
            return name.get();
        }

        public SimpleStringProperty nameProperty() {
            return name;
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public double getQuantity() {
            return quantity.get();
        }

        public SimpleDoubleProperty quantityProperty() {
            return quantity;
        }

        public void setQuantity(double quantity) {
            this.quantity.set(quantity);
        }

        public double getSalePrice() {
            return salePrice.get();
        }

        public SimpleDoubleProperty salePriceProperty() {
            return salePrice;
        }

        public void setSalePrice(double salePrice) {
            this.salePrice.set(salePrice);
        }

        public double getTotalCost() {
            return totalCost.get();
        }

        public SimpleDoubleProperty totalCostProperty() {
            return totalCost;
        }

        public void setTotalCost(double totalCost) {
            this.totalCost.set(totalCost);
        }

        public double getDiscValue() {
            return discValue.get();
        }

        public SimpleDoubleProperty discValueProperty() {
            return discValue;
        }

        public void setDiscValue(double discValue) {
            this.discValue.set(discValue);
        }

        public double getDiscPercentage() {
            return discPercentage.get();
        }

        public SimpleDoubleProperty discPercentageProperty() {
            return discPercentage;
        }

        public void setDiscPercentage(double discPercentage) {
            this.discPercentage.set(discPercentage);
        }

        public String getIdList() {
            return idList.get();
        }

        public SimpleStringProperty idListProperty() {
            return idList;
        }

        public void setIdList(String idList) {
            this.idList.set(idList);
        }
    }
}

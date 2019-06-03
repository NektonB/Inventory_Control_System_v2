package Controllers;

import DataControllers.DataReader;
import Modules.Category;
import Modules.Product;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class StockView implements Initializable {

    DataReader dataReader;
    Alerts alerts;
    Product product;
    Category category;
    ObservableList productList;
    @FXML
    private JFXTextField txtCode;
    @FXML
    private JFXTextField txtProductName;
    @FXML
    private JFXComboBox<String> cmbCategory;
    @FXML
    private TableView<Products> tblProducts;
    @FXML
    private TableColumn<Product, String> tcCode;
    @FXML
    private TableColumn<Product, String> tcProductName;
    @FXML
    private TableColumn<Product, String> tcCategory;
    @FXML
    private TableColumn<Product, Double> tcPurchasePrice;
    @FXML
    private TableColumn<Product, Double> tcSalePrice;
    @FXML
    private TableColumn<Product, Double> tcQuantity;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            alerts = ObjectGenerator.getAlerts();
            dataReader = ObjectGenerator.getDataReader();
            category = ObjectGenerator.getCategory();
            product = ObjectGenerator.getProduct();

            dataReader.fillCategoryCombo(cmbCategory);
            readyProductTable();
            fillProductTable();

            productList = tblProducts.getItems();

            filterProductTableByCode();
            filterProductTableByName();
            filterProductTableByCategory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readyProductTable() {
        tcCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        tcProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        tcPurchasePrice.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
        tcSalePrice.setCellValueFactory(new PropertyValueFactory<>("salePrice"));
        tcQuantity.setCellValueFactory(new PropertyValueFactory<>("aveQuantity"));
    }

    public void fillProductTable() {
        try {
            dataReader.fillStockViewTable(tblProducts);
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void filterProductTableByCode() {
        try {
            txtCode.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                if (oldValue != null && (newValue.length() < oldValue.length())) {
                    tblProducts.setItems(productList);
                }
                String value = newValue;
                ObservableList<Products> subentries = FXCollections.observableArrayList();

                long count = tblProducts.getColumns().stream().count();
                for (int i = 0; i < tblProducts.getItems().size(); i++) {
                    for (int j = 0; j < count; j++) {
                        String entry = "" + tblProducts.getColumns().get(0).getCellData(i);
                        if (entry.contains(value)) {
                            subentries.add(tblProducts.getItems().get(i));
                            break;
                        }
                    }
                }
                tblProducts.setItems(subentries);
            });
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void filterProductTableByName() {
        try {
            //ObservableList data = tblProducts.getItems();
            txtProductName.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                if (oldValue != null && (newValue.length() < oldValue.length())) {
                    tblProducts.setItems(productList);
                }
                String value = newValue;
                ObservableList<Products> subentries = FXCollections.observableArrayList();

                long count = tblProducts.getColumns().stream().count();
                for (int i = 0; i < tblProducts.getItems().size(); i++) {
                    for (int j = 0; j < count; j++) {
                        String entry = "" + tblProducts.getColumns().get(1).getCellData(i);
                        if (entry.contains(value)) {
                            subentries.add(tblProducts.getItems().get(i));
                            break;
                        }
                    }
                }
                tblProducts.setItems(subentries);
            });
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void filterProductTableByCategory() {
        try {
            //ObservableList data = tblProducts.getItems();
            cmbCategory.valueProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                if (oldValue != null && (newValue.length() < oldValue.length())) {
                    tblProducts.setItems(productList);
                }
                String value = newValue;
                ObservableList<Products> subentries = FXCollections.observableArrayList();

                long count = tblProducts.getColumns().stream().count();
                for (int i = 0; i < tblProducts.getItems().size(); i++) {
                    for (int j = 0; j < count; j++) {
                        String entry = "" + tblProducts.getColumns().get(2).getCellData(i);
                        if (entry.contains(value)) {
                            subentries.add(tblProducts.getItems().get(i));
                            break;
                        }
                    }
                }
                tblProducts.setItems(subentries);
            });
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public static class Products {
        SimpleStringProperty code;
        SimpleStringProperty name;
        SimpleStringProperty category;
        SimpleDoubleProperty purchasePrice;
        SimpleDoubleProperty salePrice;
        SimpleDoubleProperty aveQuantity;

        public Products(String code, String name, String category, double purchasePrice, double salePrice, double aveQuantity) {
            this.code = new SimpleStringProperty(code);
            this.name = new SimpleStringProperty(name);
            this.category = new SimpleStringProperty(category);
            this.purchasePrice = new SimpleDoubleProperty(purchasePrice);
            this.salePrice = new SimpleDoubleProperty(salePrice);
            this.aveQuantity = new SimpleDoubleProperty(aveQuantity);
        }

        public String getCode() {
            return code.get();
        }

        public void setCode(String code) {
            this.code.set(code);
        }

        public SimpleStringProperty codeProperty() {
            return code;
        }

        public String getName() {
            return name.get();
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public SimpleStringProperty nameProperty() {
            return name;
        }

        public String getCategory() {
            return category.get();
        }

        public void setCategory(String category) {
            this.category.set(category);
        }

        public SimpleStringProperty categoryProperty() {
            return category;
        }

        public double getPurchasePrice() {
            return purchasePrice.get();
        }

        public void setPurchasePrice(double purchasePrice) {
            this.purchasePrice.set(purchasePrice);
        }

        public SimpleDoubleProperty purchasePriceProperty() {
            return purchasePrice;
        }

        public double getSalePrice() {
            return salePrice.get();
        }

        public void setSalePrice(double salePrice) {
            this.salePrice.set(salePrice);
        }

        public SimpleDoubleProperty salePriceProperty() {
            return salePrice;
        }

        public double getAveQuantity() {
            return aveQuantity.get();
        }

        public void setAveQuantity(double aveQuantity) {
            this.aveQuantity.set(aveQuantity);
        }

        public SimpleDoubleProperty aveQuantityProperty() {
            return aveQuantity;
        }
    }
}

package Controllers;

import DataControllers.DataReader;
import Modules.Category;
import Modules.InvoiceInterConnector;
import Modules.Product;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductView implements Initializable {

    DataReader dataReader;
    Alerts alerts;
    Product product;
    Category category;
    ObservableList productList;
    InvoiceInterConnector interConnector;
    @FXML
    private TableView<StockView.Products> tblProducts;
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
            interConnector = ObjectGenerator.getInterConnector();

            readyProductTable();
            fillProductTable();
            filterProductTableByCode();
            productList = tblProducts.getItems();

            filterProductTableByCode();
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
            ObservableList<StockView.Products> subEntries = FXCollections.observableArrayList();

            long count = tblProducts.getColumns().stream().count();
            for (int i = 0; i < tblProducts.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + tblProducts.getColumns().get(0).getCellData(i);
                    if (entry.contains(interConnector.getProductCode())) {
                        subEntries.add(tblProducts.getItems().get(i));
                        break;
                    }
                }
            }
            tblProducts.setItems(subEntries);
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void selectProduct() {
        try {
            if (!tblProducts.getSelectionModel().isEmpty()) {

                interConnector.setProductCode(tblProducts.getSelectionModel().getSelectedItem().code.get());
                interConnector.setSalePrice(tblProducts.getSelectionModel().getSelectedItem().salePrice.get());
                interConnector.setQuantity(tblProducts.getSelectionModel().getSelectedItem().aveQuantity.get());
                interConnector.getTxtSalePrice().setText(Double.toString(interConnector.getSalePrice()));

                closeMe();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeMe() {
        try {
            Stage stage = (Stage) tblProducts.getScene().getWindow();
            stage.close();
            interConnector.getTxtFocus().requestFocus();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            interConnector.getTxtFocus().requestFocus();
        }
    }

    public void selectProductKey(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            selectProduct();
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

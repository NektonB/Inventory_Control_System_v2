package Controllers;

import DataControllers.DataReader;
import DataControllers.DataWriter;
import Modules.*;
import com.jfoenix.controls.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class GRNPurchaseController implements Initializable {

    TextValidator validator;
    DataWriter dataWriter;
    DataReader dataReader;
    Alerts alerts;
    DateFormatConverter dateFormatConverter;
    TimeFormatConverter timeFormatConverter;
    PaymentType paymentType;
    ComponentSwitcher switcher;
    PaymentMethod paymentMethod;
    GRN grn;
    MethodList methodList;
    Supplier supplier;
    PayStatus payStatus;
    Approve approve;
    User user;
    GrnItems grnItems;
    Product product;
    TableView tblItems;
    @FXML
    private JFXDatePicker dpGRNDate;
    @FXML
    private JFXTimePicker tpGRNTime;
    @FXML
    private JFXTextField txtItemCount;
    @FXML
    private JFXTextField txtTotalAmount;
    @FXML
    private JFXTextField txtDisValue;
    @FXML
    private JFXTextField txtDisPercentage;
    @FXML
    private JFXTextField txtGrossAmount;
    @FXML
    private JFXTextField txtManualDisValue;
    @FXML
    private JFXTextField txtManualDisPercentage;
    @FXML
    private JFXTextField txtTotalDisValue;
    @FXML
    private JFXTextField txtTotalDisPercentage;
    @FXML
    private JFXTextField txtNetAmount;
    @FXML
    private JFXTextField txtPayedAmount;
    @FXML
    private JFXTextField txtDeuAmount;
    @FXML
    private JFXComboBox<String> cmbType;
    @FXML
    private JFXTextField txtPayedAmountPart;
    @FXML
    private TableView<PaymentMethodList> tblPayment;
    @FXML
    private TableColumn<PaymentMethodList, Integer> tcTypeId;
    @FXML
    private TableColumn<PaymentMethodList, String> tcMethode;
    @FXML
    private TableColumn<PaymentMethodList, Double> tcAmount;
    @FXML
    private JFXButton btnGoBack;
    @FXML
    private JFXButton btnPurchaseNow;
    @FXML
    private JFXListView<String> lvSupplierName;
    @FXML
    private JFXTextField txtSupplierName;

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
            dateFormatConverter = ObjectGenerator.getDateFormatConverter();
            timeFormatConverter = ObjectGenerator.getTimeFormatConverter();
            paymentType = ObjectGenerator.getPaymentType();
            switcher = ObjectGenerator.getComponentSwitcher();
            paymentMethod = ObjectGenerator.getPaymentMethod();
            grn = ObjectGenerator.getGrn();
            methodList = ObjectGenerator.getMethodList();
            supplier = ObjectGenerator.getSupplier();
            payStatus = ObjectGenerator.getPayStatus();
            approve = ObjectGenerator.getApprove();
            user = ObjectGenerator.getUser();
            grnItems = ObjectGenerator.getGrnItems();
            product = ObjectGenerator.getProduct();

            dateFormatConverter.convert(dpGRNDate, "yyyy-MM-dd");
            //timeFormatConverter.convert(tpGRNTime, "hh:mm");
            tpGRNTime.setValue(LocalTime.now());
            validator.validateDigit(txtManualDisValue, 10, 2);
            validator.validateDigit(txtManualDisPercentage, 3, 2);
            validator.validateDigit(txtPayedAmountPart, 10, 2);
            dataReader.fillPaymentTypeCombo(cmbType);
            cmbType.setValue("CASH");

            calculateItemCount();
            calculateTotalAmount();
            calculateDiscValue();
            calculateDiscPercentage();
            calculateGrossAmount();
            calculateNetDiscPercentage();
            calculateNetDiscValue();
            calculateNetAmount();
            calculateDeuAmount();
            //});
            //readyData.setName("GRN Purchase Controller");
            //readyData.start();
            readyTable();
            lvSupplierName.setVisible(false);

            txtSupplierName.setText("Default");
            supplier.setName("Default");
            dataReader.getSupplierByName();
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    private void readyTable() {
        tcTypeId.setCellValueFactory(new PropertyValueFactory<>("typeId"));
        tcMethode.setCellValueFactory(new PropertyValueFactory<>("type"));
        tcAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
    }

    public void savePaymentType(KeyEvent event) {
        if (event.isControlDown() & event.getCode().equals(KeyCode.S)) {
            try {
                boolean paymentTypeAlready = dataReader.checkPaymentTypeAlready(cmbType.getValue());

                if (paymentTypeAlready) {
                    alerts.getWarningNotify("Warning", "This type is already in my Database..!");
                } else {
                    paymentType.setType(cmbType.getValue());
                    int savePaymentType = dataWriter.savePaymentType();

                    if (savePaymentType > 0) {
                        paymentType.resetAll();
                        dataReader.fillPaymentTypeCombo(cmbType);
                        alerts.getInformationAlert("Information", "Payment Type Registration", "Congratulation Chief..!\nType registration successful");
                    }
                }
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

    public void calculateItemCount() {
        try {
            double quantity = 0;

            ObservableList<? extends TableColumn<?, ?>> columns = switcher.getTblItem().getColumns();
            for (int i = 0; i < switcher.getTblItem().getItems().size(); ++i) {
                quantity += Double.parseDouble(columns.get(2).getCellObservableValue(i).getValue().toString());
            }
            quantity = roundValue(quantity);
            txtItemCount.setText(Double.toString(quantity));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void calculateTotalAmount() {
        try {
            double totalAmount = 0;

            ObservableList<? extends TableColumn<?, ?>> columns = switcher.getTblItem().getColumns();
            for (int i = 0; i < switcher.getTblItem().getItems().size(); ++i) {
                totalAmount += Double.parseDouble(columns.get(5).getCellObservableValue(i).getValue().toString());
            }
            totalAmount = roundValue(totalAmount);
            txtTotalAmount.setText(Double.toString(totalAmount));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void calculateDiscValue() {
        try {
            double discValue = 0;

            ObservableList<? extends TableColumn<?, ?>> columns = switcher.getTblItem().getColumns();
            for (int i = 0; i < switcher.getTblItem().getItems().size(); ++i) {
                discValue += Double.parseDouble(columns.get(6).getCellObservableValue(i).getValue().toString());
            }
            discValue = roundValue(discValue);
            txtDisValue.setText(Double.toString(discValue));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void calculateDiscPercentage() {
        double discValue = 0, discPercentage = 0, totalAmount = 0;
        try {
            totalAmount = Double.parseDouble(txtTotalAmount.getText());
            discValue = Double.parseDouble(txtDisValue.getText());

            discPercentage = ((discValue / totalAmount) * 100);
            discPercentage = roundValue(discPercentage);

            txtDisPercentage.setText(Double.toString(discPercentage));

        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void calculateGrossAmount() {
        try {
            double totalAmount = 0, grossAmount = 0, discValue = 0;

            totalAmount = Double.parseDouble(txtTotalAmount.getText());
            discValue = Double.parseDouble(txtDisValue.getText());

            grossAmount = (totalAmount - discValue);
            grossAmount = roundValue(grossAmount);
            txtGrossAmount.setText(Double.toString(grossAmount));
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void increasePayedAmount() {
        try {
            double payedAmountPart = 0, payedAmount = 0;

            if (txtPayedAmountPart.getText().isEmpty()) {
                payedAmountPart = 0;
            } else {
                payedAmountPart = Double.parseDouble(txtPayedAmountPart.getText());
            }

            payedAmount = Double.parseDouble(txtPayedAmount.getText());

            payedAmount += payedAmountPart;
            payedAmount = roundValue(payedAmount);
            txtPayedAmount.setText(Double.toString(payedAmount));

        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void decreasePayedAmount() {
        try {
            double payedAmountPart = 0, payedAmount = 0;

            PaymentMethodList methodList = tblPayment.getSelectionModel().getSelectedItem();
            payedAmountPart = methodList.amount.get();

            payedAmount = Double.parseDouble(txtPayedAmount.getText());

            payedAmount -= payedAmountPart;
            payedAmount = roundValue(payedAmount);
            txtPayedAmount.setText(Double.toString(payedAmount));

        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void calculateDeuAmount() {
        try {
            double deuAmount = 0, netAmount = 0, payedAmount = 0;

            if (txtNetAmount.getText().isEmpty()) {
                netAmount = 0;
            } else {
                netAmount = Double.parseDouble(txtNetAmount.getText());
            }

            if (txtPayedAmount.getText().isEmpty()) {
                payedAmount = 0;
            } else {
                payedAmount = Double.parseDouble(txtPayedAmount.getText());
            }

            deuAmount = (netAmount - payedAmount);
            deuAmount = roundValue(deuAmount);
            txtDeuAmount.setText(Double.toString(deuAmount));
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void calculateManualDiscPercentage() {
        double discValue = 0, discPercentage = 0, totalGrossAmount = 0;
        try {
            if (txtGrossAmount.getText().isEmpty()) {
                totalGrossAmount = 0;
            } else {
                totalGrossAmount = Double.parseDouble(txtGrossAmount.getText());
            }

            if (txtManualDisValue.getText().isEmpty()) {
                discValue = 0;
            } else {
                discValue = Double.parseDouble(txtManualDisValue.getText());
            }

            discPercentage = ((discValue / totalGrossAmount) * 100);
            discPercentage = roundValue(discPercentage);

            txtManualDisPercentage.setText(Double.toString(discPercentage));

        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void calculateManualDisValue() {
        double discValue = 0, discPercentage = 0, grossAmount = 0;
        try {
            if (txtGrossAmount.getText().isEmpty()) {
                grossAmount = 0;
            } else {
                grossAmount = Double.parseDouble(txtGrossAmount.getText());
            }

            if (txtManualDisPercentage.getText().isEmpty()) {
                discPercentage = 0;
            } else {
                discPercentage = Double.parseDouble(txtManualDisPercentage.getText());
            }

            discValue = ((discPercentage * grossAmount) / 100);
            discValue = roundValue(discValue);

            txtManualDisValue.setText(Double.toString(discValue));

        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void calculateNetDiscPercentage() {
        double netDiscPercentage = 0, discPercentage = 0, manualDiscPercentage = 0;
        try {

            discPercentage = Double.parseDouble(txtDisPercentage.getText());

            if (txtManualDisPercentage.getText().isEmpty()) {
                manualDiscPercentage = 0;
            } else {
                manualDiscPercentage = Double.parseDouble(txtManualDisPercentage.getText());
            }


            netDiscPercentage = (discPercentage + manualDiscPercentage);
            netDiscPercentage = roundValue(netDiscPercentage);

            txtTotalDisPercentage.setText(Double.toString(netDiscPercentage));

        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void calculateNetDiscValue() {
        double netDiscValue = 0, discValue = 0, manualDiscValue = 0;
        try {

            discValue = Double.parseDouble(txtDisValue.getText());

            if (txtManualDisValue.getText().isEmpty()) {
                manualDiscValue = 0;
            } else {
                manualDiscValue = Double.parseDouble(txtManualDisValue.getText());
            }

            netDiscValue = (discValue + manualDiscValue);
            netDiscValue = roundValue(netDiscValue);

            txtTotalDisValue.setText(Double.toString(netDiscValue));

        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void calculateNetAmount() {
        try {
            double grossAmount = 0, manualDiscValue = 0, netAmount = 0;

            if (txtManualDisValue.getText().isEmpty()) {
                manualDiscValue = 0;
            } else {
                manualDiscValue = Double.parseDouble(txtManualDisValue.getText());
            }

            grossAmount = Double.parseDouble(txtGrossAmount.getText());

            netAmount = (grossAmount - manualDiscValue);
            netAmount = roundValue(netAmount);
            txtNetAmount.setText(Double.toString(netAmount));

        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void txtManualDisValueKeyReleased() {
        calculateManualDiscPercentage();
        calculateNetDiscValue();
        calculateNetDiscPercentage();
        calculateNetAmount();
        calculateDeuAmount();
    }

    public void setTxtManualDisPercentageKeyReleased() {
        calculateManualDisValue();
        calculateNetDiscValue();
        calculateNetDiscPercentage();
        calculateNetAmount();
        calculateDeuAmount();
    }

    public void addMethodToTable(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                if (txtDeuAmount.getText().equals("0") | txtDeuAmount.getText().equals("0.0")) {
                    Toolkit.getDefaultToolkit().beep();
                    alerts.getWarningNotify("Warning", "Can't add.Deu Amount is zero...!");
                } else {
                    double payedAmountPart = 0;
                    if (txtPayedAmountPart.getText().isEmpty()) {
                        payedAmountPart = 0;
                    } else {
                        payedAmountPart = Double.parseDouble(txtPayedAmountPart.getText());
                    }

                    paymentType.setType(cmbType.getValue());
                    dataReader.getPaymentTypeByType();

                    ObservableList<PaymentMethodList> methodLists;
                    methodLists = tblPayment.getItems();
                    methodLists.add(new PaymentMethodList(paymentType.getId(), cmbType.getValue(), payedAmountPart));
                    tblPayment.setItems(methodLists);

                    increasePayedAmount();
                    calculateDeuAmount();

                    paymentType.resetAll();
                    cmbType.setValue("CASH");
                    txtPayedAmountPart.setText("0");
                    //txtPayedAmountPart.requestFocus();
                    btnPurchaseNow.requestFocus();
                }
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    public void removeRow(KeyEvent event) {
        if (event.getCode().equals(KeyCode.DELETE)) {
            try {
                if (tblPayment.getItems().isEmpty()) {
                    alerts.getWarningNotify("Warning !", "No more rows here...");
                } else {

                    decreasePayedAmount();
                    calculateDeuAmount();

                    PaymentMethodList methodList = tblPayment.getSelectionModel().getSelectedItem();
                    tblPayment.getItems().remove(methodList);
                    txtPayedAmountPart.requestFocus();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int savePaymentMethod() {
        int savePaymentMethodList = 0;
        try {
            int savePaymentMethod = dataWriter.savePaymentMethod(supplier.getId(), "SUPPLIER");

            if (savePaymentMethod > 0) {
                ObservableList<? extends TableColumn<?, ?>> columns = tblPayment.getColumns();
                for (int i = 0; i < tblPayment.getItems().size(); ++i) {
                    paymentType.setId(Integer.parseInt(columns.get(0).getCellObservableValue(i).getValue().toString()));
                    //System.out.println(paymentType.getId());
                    methodList.setPayedValue(Double.parseDouble(columns.get(2).getCellObservableValue(i).getValue().toString()));

                    savePaymentMethodList = dataWriter.savePaymentMethodList();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
        return savePaymentMethodList;
    }

    public void saveGRN() {
        try {
            int savePaymentMethod = savePaymentMethod();

            if (savePaymentMethod > 0) {

                grn.setDate(dpGRNDate.getValue().toString());
                grn.setTime(tpGRNTime.getValue().toString());
                grn.setItemCount(Double.parseDouble(txtItemCount.getText()));
                grn.setTotalAmount(Double.parseDouble(txtTotalAmount.getText()));
                grn.setGrossDiscount(Double.parseDouble(txtDisValue.getText()));
                grn.setManualDiscount(Double.parseDouble(txtManualDisValue.getText()));
                grn.setNetDiscount(Double.parseDouble(txtTotalDisValue.getText()));
                grn.setNetAmount(Double.parseDouble(txtNetAmount.getText()));
                grn.setPayedValue(Double.parseDouble(txtPayedAmount.getText()));
                grn.setDeuAmount(Double.parseDouble(txtDeuAmount.getText()));


                if (grn.getDeuAmount() == 0 || grn.getDeuAmount() < 0) {
                    payStatus.setId(1);
                } else if (grn.getDeuAmount() > 0) {
                    payStatus.setId(2);
                } else if (grn.getPayedValue() == 0) {
                    payStatus.setId(3);
                }

                approve.setId(1);
                //user.setId(1);
                System.out.println("Deu Amount " + grn.getDeuAmount());
                System.out.println("Pay Status Id " + payStatus.getId());
                int saveGRN = dataWriter.saveGRN();

                if (saveGRN > 0) {
                    saveGRNItems();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void saveGRNKey(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            saveGRN();
            ;
        }
    }

    public void saveGRNItems() {
        try {
            int saveGrnItems = 0;
            ObservableList<? extends TableColumn<?, ?>> columns = switcher.getTblItem().getColumns();
            for (int i = 0; i < switcher.getTblItem().getItems().size(); ++i) {

                product.setCode(columns.get(0).getCellObservableValue(i).getValue().toString());
                grnItems.setPurchasePrice(Double.parseDouble(columns.get(3).getCellObservableValue(i).getValue().toString()));
                grnItems.setSalePrice(Double.parseDouble(columns.get(4).getCellObservableValue(i).getValue().toString()));
                grnItems.setQuantity(Double.parseDouble(columns.get(2).getCellObservableValue(i).getValue().toString()));
                grnItems.setTotalAmount(Double.parseDouble(columns.get(5).getCellObservableValue(i).getValue().toString()));
                grnItems.setDiscValue(Double.parseDouble(columns.get(6).getCellObservableValue(i).getValue().toString()));
                grnItems.setDiscRate(Double.parseDouble(columns.get(7).getCellObservableValue(i).getValue().toString()));
                grnItems.setItemStatus("IN STOCK");

                saveGrnItems = dataWriter.saveGrnItems();
                product.resetAll();
                grnItems.resetAll();
            }

            if (saveGrnItems > 0) {
                saveStock();
            }

        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void saveStock() {
        try {
            int saveStock = 0;
            ObservableList<? extends TableColumn<?, ?>> columns = switcher.getTblItem().getColumns();
            for (int i = 0; i < switcher.getTblItem().getItems().size(); ++i) {

                product.setCode(columns.get(0).getCellObservableValue(i).getValue().toString());
                grnItems.setPurchasePrice(Double.parseDouble(columns.get(3).getCellObservableValue(i).getValue().toString()));
                grnItems.setSalePrice(Double.parseDouble(columns.get(4).getCellObservableValue(i).getValue().toString()));
                grnItems.setQuantity(Double.parseDouble(columns.get(2).getCellObservableValue(i).getValue().toString()));
                grnItems.setTotalAmount(Double.parseDouble(columns.get(5).getCellObservableValue(i).getValue().toString()));
                grnItems.setDiscValue(Double.parseDouble(columns.get(6).getCellObservableValue(i).getValue().toString()));
                grnItems.setDiscRate(Double.parseDouble(columns.get(7).getCellObservableValue(i).getValue().toString()));
                grnItems.setItemStatus("IN STOCK");

                saveStock = dataWriter.saveStock();
                product.resetAll();
                grnItems.resetAll();
            }

            if (saveStock > 0) {
                product.resetAll();
                grn.resetAll();
                payStatus.resetAll();
                approve.resetAll();
                //user.resetAll();
                paymentType.resetAll();
                paymentMethod.resetAll();
                methodList.resetAll();
                tblPayment.getItems().clear();
                switcher.getTblItem().getItems().clear();
                alerts.getInformationAlert("Information", "Purchase", "Congratulation Chief..!\n GRN  Purchase successful");
                ((Stage) btnPurchaseNow.getScene().getWindow()).close();
            }

        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void readySupplierNameAutoCompleter(KeyEvent event) {
        try {
            dataReader.autoCompleteSupplierName(lvSupplierName, txtSupplierName);

            if (!lvSupplierName.getItems().isEmpty()) {
                if (!lvSupplierName.isVisible()) {
                    lvSupplierName.setVisible(true);
                }
            } else {
                if (lvSupplierName.isVisible()) {
                    lvSupplierName.setVisible(false);
                }
            }

            if (event.getCode() == KeyCode.RIGHT) {
                lvSupplierName.requestFocus();
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void getSupplierByName(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                supplier.setName(txtSupplierName.getText());
                dataReader.getSupplierByName();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    public void completeSupplierName(KeyEvent event) {
        if (event.getCode() == KeyCode.LEFT) {
            txtSupplierName.requestFocus();
        }
        if (event.getCode() == KeyCode.ENTER) {
            txtSupplierName.setText(lvSupplierName.getSelectionModel().getSelectedItem());
            txtSupplierName.requestFocus();
            lvSupplierName.setVisible(false);
            getSupplierByName(event);
            txtPayedAmountPart.requestFocus();
        }
    }

    public void setTxtSupplierNameKeyReleased(KeyEvent event) {
        try {
            readySupplierNameAutoCompleter(event);
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void autoHidelvSupplierName() {
        if (lvSupplierName.isVisible()) {
            lvSupplierName.setVisible(false);
        }
    }

    public void goBack() {
        try {
            ((Stage) btnPurchaseNow.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void goBackKey(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            goBack();
        }
    }

    public void goToSupplier(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                txtSupplierName.requestFocus();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    public void goToPayAmount(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                txtPayedAmountPart.requestFocus();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    public static class PaymentMethodList {

        SimpleIntegerProperty typeId;
        SimpleStringProperty type;
        SimpleDoubleProperty amount;

        public PaymentMethodList(int typeId, String type, double amount) {
            this.typeId = new SimpleIntegerProperty(typeId);
            this.type = new SimpleStringProperty(type);
            this.amount = new SimpleDoubleProperty(amount);
        }

        public int getTypeId() {
            return typeId.get();
        }

        public void setTypeId(int typeId) {
            this.typeId.set(typeId);
        }

        public SimpleIntegerProperty typeIdProperty() {
            return typeId;
        }

        public String getType() {
            return type.get();
        }

        public void setType(String type) {
            this.type.set(type);
        }

        public SimpleStringProperty typeProperty() {
            return type;
        }

        public double getAmount() {
            return amount.get();
        }

        public void setAmount(double amount) {
            this.amount.set(amount);
        }

        public SimpleDoubleProperty amountProperty() {
            return amount;
        }
    }
}

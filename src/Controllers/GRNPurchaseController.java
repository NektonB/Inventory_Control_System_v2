package Controllers;

import DataControllers.DataReader;
import DataControllers.DataWriter;
import Modules.ComponentSwitcher;
import Modules.PaymentType;
import com.jfoenix.controls.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class GRNPurchaseController implements Initializable {

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
    private TableColumn<PaymentMethodList, String> tcMethode;

    @FXML
    private TableColumn<PaymentMethodList, Double> tcAmount;

    @FXML
    private JFXButton btnGoBack;

    @FXML
    private JFXButton btnPurchaseNow;

    TextValidator validator;
    DataWriter dataWriter;
    DataReader dataReader;
    Alerts alerts;
    DateFormatConverter dateFormatConverter;
    TimeFormatConverter timeFormatConverter;
    PaymentType paymentType;
    ComponentSwitcher switcher;

    TableView tblItems;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            /*
             * Load Supporting classes by thread
             * All supporting classes load in the thread
             * call the table ready method outside of the thread
             * */
            Thread readyData = new Thread(() -> {
                alerts = ObjectGenerator.getAlerts();
                dataWriter = ObjectGenerator.getDataWriter();
                dataReader = ObjectGenerator.getDataReader();
                validator = ObjectGenerator.getTextValidator();
                dateFormatConverter = ObjectGenerator.getDateFormatConverter();
                timeFormatConverter = ObjectGenerator.getTimeFormatConverter();
                paymentType = ObjectGenerator.getPaymentType();
                switcher = ObjectGenerator.getComponentSwitcher();

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

            });
            readyTable();
            readyData.setName("GRN Purchase Controller");
            readyData.start();

        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    private void readyTable() {
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

            discPercentage = Double.parseDouble(txtDisValue.getText());
            manualDiscPercentage = Double.parseDouble(txtManualDisValue.getText());

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
            manualDiscValue = Double.parseDouble(txtManualDisValue.getText());

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
        calculateNetDiscPercentage();
        calculateNetAmount();
    }

    public void setTxtManualDisPercentageKeyReleased() {
        calculateManualDisValue();
        calculateNetDiscValue();
        calculateNetAmount();
    }

    public void addMethodToTable(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                double payedAmountPart = 0;
                if (txtPayedAmountPart.getText().isEmpty()) {
                    payedAmountPart = 0;
                } else {
                    payedAmountPart = Double.parseDouble(txtPayedAmountPart.getText());
                }

                ObservableList<PaymentMethodList> methodLists;
                methodLists = tblPayment.getItems();
                methodLists.add(new PaymentMethodList(cmbType.getValue(), payedAmountPart));
                tblPayment.setItems(methodLists);

                increasePayedAmount();
                calculateDeuAmount();

                cmbType.setValue("CASH");
                txtPayedAmountPart.setText("0");
                txtPayedAmountPart.requestFocus();
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

    public static class PaymentMethodList {

        SimpleStringProperty type;
        SimpleDoubleProperty amount;

        public PaymentMethodList(String type, double amount) {
            this.type = new SimpleStringProperty(type);
            this.amount = new SimpleDoubleProperty(amount);
        }

        public String getType() {
            return type.get();
        }

        public SimpleStringProperty typeProperty() {
            return type;
        }

        public void setType(String type) {
            this.type.set(type);
        }

        public double getAmount() {
            return amount.get();
        }

        public SimpleDoubleProperty amountProperty() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount.set(amount);
        }
    }
}

package Controllers;

import DataControllers.DataReader;
import DataControllers.DataWriter;
import Modules.Address;
import Modules.ComponentSwitcher;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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

public class AddressController implements Initializable {

    DataWriter dataWriter;
    DataReader dataReader;
    Alerts alerts;
    Address address;
    ComponentSwitcher switcher;
    @FXML
    private JFXTextField txtId;
    @FXML
    private JFXTextField txtNumber;
    @FXML
    private JFXTextField txtLine01;
    @FXML
    private JFXTextField txtLine02;
    @FXML
    private JFXTextField txtCity;
    @FXML
    private JFXTextField txtCountry;
    @FXML
    private JFXTextField txtPostalCode;
    @FXML
    private JFXButton btnSave;
    @FXML
    private JFXButton btnUpdate;
    @FXML
    private TableView<AddressList> tblAddress;
    @FXML
    private TableColumn<AddressList, Integer> tcId;
    @FXML
    private TableColumn<AddressList, String> tcNumber;
    @FXML
    private TableColumn<AddressList, String> tcLine01;
    @FXML
    private TableColumn<AddressList, String> tcLine02;
    @FXML
    private TableColumn<AddressList, String> tcCity;
    @FXML
    private TableColumn<AddressList, String> tcCountry;
    @FXML
    private TableColumn<AddressList, String> tcPostalCode;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            /**
             * Load Supporting classes by thread
             * All supporting classes load in the thread
             * call the table ready method outside of the thread
             * */
            //Thread readyData = new Thread(() -> {
            alerts = ObjectGenerator.getAlerts();
            dataWriter = ObjectGenerator.getDataWriter();
            dataReader = ObjectGenerator.getDataReader();
            address = ObjectGenerator.getAddress();
            switcher = ObjectGenerator.getComponentSwitcher();

            dataReader.fillAddressTable(tblAddress);
            //dataReader.fillUserTypeCombo(cmbUserType);
            //dataReader.fillStatusCombo(cmbUserStatus);
            //dataReader.fillUserTable(tblUser);
            //});
            //readyData.setName("Address Controller");
            //readyData.start();
            readyTable();
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    /**
     * Reset all text
     */
    public void resetText() {
        txtId.setText("");
        txtNumber.setText("");
        txtLine01.setText("");
        txtLine02.setText("");
        txtCity.setText("");
        txtCountry.setText("");
        txtPostalCode.setText("");
        txtNumber.requestFocus();
    }

    /**
     * Table ready
     * Link my Address List module and TableView
     */
    public void readyTable() {
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        tcLine01.setCellValueFactory(new PropertyValueFactory<>("line01"));
        tcLine02.setCellValueFactory(new PropertyValueFactory<>("line02"));
        tcCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        tcCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        tcPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
    }

    /**
     * Set data to address module and save after
     * Save finish reset all modules and textfield
     */
    public void saveAddress() {
        try {
            address.setNumber(txtNumber.getText());
            address.setLine01(txtLine01.getText());
            address.setLine02(txtLine02.getText());
            address.setCity(txtCity.getText());
            address.setCountry(txtCountry.getText());
            address.setPostalCode(txtPostalCode.getText());

            int saveAddress = dataWriter.saveAddress();
            if (saveAddress > 0) {
                resetText();
                address.resetAll();
                alerts.getInformationAlert("Information", "Address Registration", "Congratulation Chief..!\nAddress registration successful");
                dataReader.fillAddressTable(tblAddress);
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    /**
     * Set data to address module and save after using enter key
     * Save finish reset all modules and textfield
     */
    public void saveAddressKey(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            saveAddress();
            txtId.requestFocus();
        }
    }

    /**
     * Select data from table and set to textfield on mouse click
     */
    public void getSelectedAddress() {
        try {
            if (!tblAddress.getSelectionModel().isEmpty()) {
                AddressList addressList = tblAddress.getSelectionModel().getSelectedItem();

                txtId.setText(Integer.toString(addressList.id.get()));
                txtNumber.setText(addressList.number.getValue());
                txtLine01.setText(addressList.line01.getValue());
                txtLine02.setText(addressList.line02.getValue());
                txtCity.setText(addressList.city.getValue());
                txtCountry.setText(addressList.country.getValue());
                txtPostalCode.setText(addressList.postalCode.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Select data from table and set to textfield on up and Down and Enter Key
     * Use the enter key get values of selected row and set the values to referenced textfield and textarea.
     */
    public void getSelectedAddressKey(KeyEvent event) {
        if (event.getCode().equals(KeyCode.UP) | event.getCode().equals(KeyCode.DOWN)) {
            getSelectedAddress();
        } else if (event.getCode().equals(KeyCode.ENTER)) {
            switcher.getTxt01().setText(txtId.getText());
            String address = txtNumber.getText() + "" +
                    ",\n" + txtLine01.getText() + "" +
                    ",\n" + txtLine02.getText() + "" +
                    ",\n" + txtCity.getText() + "" +
                    ",\n" + txtCountry.getText() + "" +
                    ",\n" + txtPostalCode.getText() + ".";
            switcher.getTxta01().setText(address);
            Stage stage = (Stage) tblAddress.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Set data to address module and save after
     * Update finish reset all modules and textfield
     */
    public void updateAddress() {
        try {
            address.setId(Integer.parseInt(txtId.getText()));
            address.setNumber(txtNumber.getText());
            address.setLine01(txtLine01.getText());
            address.setLine02(txtLine02.getText());
            address.setCity(txtCity.getText());
            address.setCountry(txtCountry.getText());
            address.setPostalCode(txtPostalCode.getText());

            int updateAddress = dataWriter.updateAddress();
            if (updateAddress > 0) {
                resetText();
                address.resetAll();
                alerts.getInformationAlert("Information", "Address Modification", "Congratulation Chief..!\n Address modification successful");
                dataReader.fillAddressTable(tblAddress);
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    /**
     * Set data to address module and save after using enter key
     * Update finish reset all modules and textfield
     */
    public void updateAddressKey(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            updateAddress();
            txtId.requestFocus();
        }
    }

    public void txtIdKeyReleased(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            tblAddress.requestFocus();
        }
    }

    /**
     * Data bin Module for tblAddress data view
     * according to table column and same their data type
     */
    public static class AddressList {
        SimpleIntegerProperty id;
        SimpleStringProperty number;
        SimpleStringProperty line01;
        SimpleStringProperty line02;
        SimpleStringProperty city;
        SimpleStringProperty country;
        SimpleStringProperty postalCode;

        public AddressList(int id, String number, String line01, String line02, String city, String country, String postalCode) {
            this.id = new SimpleIntegerProperty(id);
            this.number = new SimpleStringProperty(number);
            this.line01 = new SimpleStringProperty(line01);
            this.line02 = new SimpleStringProperty(line02);
            this.city = new SimpleStringProperty(city);
            this.country = new SimpleStringProperty(country);
            this.postalCode = new SimpleStringProperty(postalCode);
        }

        public int getId() {
            return id.get();
        }

        public void setId(int id) {
            this.id.set(id);
        }

        public SimpleIntegerProperty idProperty() {
            return id;
        }

        public String getNumber() {
            return number.get();
        }

        public void setNumber(String number) {
            this.number.set(number);
        }

        public SimpleStringProperty numberProperty() {
            return number;
        }

        public String getLine01() {
            return line01.get();
        }

        public void setLine01(String line01) {
            this.line01.set(line01);
        }

        public SimpleStringProperty line01Property() {
            return line01;
        }

        public String getLine02() {
            return line02.get();
        }

        public void setLine02(String line02) {
            this.line02.set(line02);
        }

        public SimpleStringProperty line02Property() {
            return line02;
        }

        public String getCity() {
            return city.get();
        }

        public void setCity(String city) {
            this.city.set(city);
        }

        public SimpleStringProperty cityProperty() {
            return city;
        }

        public String getCountry() {
            return country.get();
        }

        public void setCountry(String country) {
            this.country.set(country);
        }

        public SimpleStringProperty countryProperty() {
            return country;
        }

        public String getPostalCode() {
            return postalCode.get();
        }

        public void setPostalCode(String postalCode) {
            this.postalCode.set(postalCode);
        }

        public SimpleStringProperty postalCodeProperty() {
            return postalCode;
        }
    }
}

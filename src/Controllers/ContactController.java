package Controllers;

import DataControllers.DataReader;
import DataControllers.DataWriter;
import Modules.ComponentSwitcher;
import Modules.Contact;
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

public class ContactController implements Initializable {

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtMobile;

    @FXML
    private JFXTextField txtLand;

    @FXML
    private JFXTextField txtFax;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtWeb;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableView<ContactList> tblContacts;

    @FXML
    private TableColumn<ContactList, Integer> tcId;

    @FXML
    private TableColumn<ContactList, String> tcMobile;

    @FXML
    private TableColumn<ContactList, String> tcLand;

    @FXML
    private TableColumn<ContactList, String> tcFax;

    @FXML
    private TableColumn<ContactList, String> tcEmail;

    @FXML
    private TableColumn<ContactList, String> tcWeb;

    DataWriter dataWriter;
    DataReader dataReader;
    Alerts alerts;
    Contact contact;
    ComponentSwitcher switcher;

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
                contact = ObjectGenerator.getContact();
                switcher = ObjectGenerator.getComponentSwitcher();

                dataReader.fillContactTable(tblContacts);
            //});
            //readyData.setName("Contact Controller");
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
        txtMobile.setText("");
        txtLand.setText("");
        txtFax.setText("");
        txtEmail.setText("");
        txtWeb.setText("");
    }

    /**
     * Table ready
     * Link my Address List module and TableView
     */
    public void readyTable() {
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        tcLand.setCellValueFactory(new PropertyValueFactory<>("land"));
        tcFax.setCellValueFactory(new PropertyValueFactory<>("fax"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tcWeb.setCellValueFactory(new PropertyValueFactory<>("web"));
    }

    /**
     * Set data to contact module and save after
     * Save finish reset all modules and textfield
     */
    public void saveContact() {
        try {
            contact.setMobile(txtMobile.getText());
            contact.setLand(txtLand.getText());
            contact.setFax(txtFax.getText());
            contact.setEmail(txtEmail.getText());
            contact.setWeb(txtWeb.getText());

            int saveContact = dataWriter.saveContact();
            if (saveContact > 0) {
                resetText();
                contact.resetAll();
                alerts.getInformationAlert("Information", "Contact Registration", "Congratulation Chief..!\n Contact registration successful");
                dataReader.fillContactTable(tblContacts);
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    /**
     * Call save method using enter key
     */
    public void saveContactKey(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            saveContact();
            txtId.requestFocus();
        }
    }

    /**
     * Select data from table and set to textfield on mouse click
     */
    public void getSelectedAddress() {
        try {
            if (!tblContacts.getSelectionModel().isEmpty()) {
                ContactList contactList = tblContacts.getSelectionModel().getSelectedItem();

                txtId.setText(Integer.toString(contactList.id.get()));
                txtMobile.setText(contactList.mobile.getValue());
                txtLand.setText(contactList.land.getValue());
                txtFax.setText(contactList.fax.getValue());
                txtEmail.setText(contactList.email.getValue());
                txtWeb.setText(contactList.web.getValue());
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
            String address = "" +
                    "Mobile.............." + txtMobile.getText() + "\n" +
                    "Land.................." + txtLand.getText() + "\n" +
                    "Fax....................." + txtFax.getText() + "\n" +
                    "Email................." + txtEmail.getText() + "\n" +
                    "Web.................." + txtWeb.getText();
            switcher.getTxta01().setText(address);
            Stage stage = (Stage) tblContacts.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Set data to contact module and update after
     * Update finish reset all modules and textfield
     */
    public void updateContact() {
        try {
            contact.setId(Integer.parseInt(txtId.getText()));
            contact.setMobile(txtMobile.getText());
            contact.setLand(txtLand.getText());
            contact.setFax(txtFax.getText());
            contact.setEmail(txtEmail.getText());
            contact.setWeb(txtWeb.getText());

            int updateContact = dataWriter.updateContact();
            if (updateContact > 0) {
                resetText();
                contact.resetAll();
                alerts.getInformationAlert("Information", "Contact Modification", "Congratulation Chief..!\n Contact Modification successful");
                dataReader.fillContactTable(tblContacts);
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    /**
     * Set data to contact module and update after using enter key
     * Update finish reset all modules and textfield
     */
    public void updateContactKey(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            updateContact();
            txtId.requestFocus();
        }
    }

    public void txtIdKeyReleased(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            tblContacts.requestFocus();
        }
    }

    /**
     * Data bin Module for tblContacts data view
     * according to table column and same their data type
     */
    public static class ContactList {
        SimpleIntegerProperty id;
        SimpleStringProperty mobile;
        SimpleStringProperty land;
        SimpleStringProperty fax;
        SimpleStringProperty email;
        SimpleStringProperty web;

        public ContactList(int id, String mobile, String land, String fax, String email, String web) {
            this.id = new SimpleIntegerProperty(id);
            this.mobile = new SimpleStringProperty(mobile);
            this.land = new SimpleStringProperty(land);
            this.fax = new SimpleStringProperty(fax);
            this.email = new SimpleStringProperty(email);
            this.web = new SimpleStringProperty(web);
        }

        public int getId() {
            return id.get();
        }

        public SimpleIntegerProperty idProperty() {
            return id;
        }

        public void setId(int id) {
            this.id.set(id);
        }

        public String getMobile() {
            return mobile.get();
        }

        public SimpleStringProperty mobileProperty() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile.set(mobile);
        }

        public String getLand() {
            return land.get();
        }

        public SimpleStringProperty landProperty() {
            return land;
        }

        public void setLand(String land) {
            this.land.set(land);
        }

        public String getFax() {
            return fax.get();
        }

        public SimpleStringProperty faxProperty() {
            return fax;
        }

        public void setFax(String fax) {
            this.fax.set(fax);
        }

        public String getEmail() {
            return email.get();
        }

        public SimpleStringProperty emailProperty() {
            return email;
        }

        public void setEmail(String email) {
            this.email.set(email);
        }

        public String getWeb() {
            return web.get();
        }

        public SimpleStringProperty webProperty() {
            return web;
        }

        public void setWeb(String web) {
            this.web.set(web);
        }
    }
}

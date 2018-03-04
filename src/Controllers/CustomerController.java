package Controllers;

import DataControllers.DataReader;
import DataControllers.DataWriter;
import Modules.*;
import com.jfoenix.controls.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {
    @FXML
    private JFXTextField txt_customer_id;

    @FXML
    private JFXTextField txt_fname;

    @FXML
    private JFXTextField txt_mname;

    @FXML
    private JFXTextField txt_lname;

    @FXML
    private JFXTextField txt_nic;

    @FXML
    private JFXTextField txt_address_id;

    @FXML
    private JFXTextArea ta_address;

    @FXML
    private JFXTextField txt_contact_id;

    @FXML
    private JFXTextArea ta_contact;

    @FXML
    private JFXDatePicker dp_join_date;

    @FXML
    private JFXComboBox<String> cmb_customer_type;

    @FXML
    private JFXComboBox<String> cmb_activation_status;

    @FXML
    private JFXButton btn_Save;

    @FXML
    private JFXButton btn_Update;

    @FXML
    private TableView<CustomerList> tbl_customer;

    @FXML
    private TableColumn<CustomerList, Integer> tc_id;

    @FXML
    private TableColumn<CustomerList, String> tc_name;

    @FXML
    private TableColumn<CustomerList, String> tc_nic;

    @FXML
    private TableColumn<CustomerList, String> tc_joindate;

    @FXML
    private TableColumn<CustomerList, String> tc_cutomer_type;

    @FXML
    private TableColumn<CustomerList, String> tc_avtivation_status;

    @FXML
    private JFXTextField txt_type_nic;

    @FXML
    private JFXTextField txt_type_name;

    DataWriter dataWriter;
    DataReader dataReader;
    Alerts alerts;
    Address address;
    ComponentSwitcher switcher;
    DateFormatConverter dateFormatConverter;
    Modules.Contact contact;
    ADStatus adStatus;
    Customer customer;
    CustomerType customerType;

    @Override


    public void initialize(URL location, ResourceBundle resources) {

        try {
            /*
             * Load Supporting classes by thread
             * All supporting classes load in the thread
             * */
            Thread readyData = new Thread(() -> {
                alerts = ObjectGenerator.getAlerts();
                dataWriter = ObjectGenerator.getDataWriter();
                dataReader = ObjectGenerator.getDataReader();
                address = ObjectGenerator.getAddress();
                switcher = ObjectGenerator.getComponentSwitcher();
                dateFormatConverter = ObjectGenerator.getDateFormatConverter();
                contact = ObjectGenerator.getContact();
                adStatus = ObjectGenerator.getAdStatus();
                customer = ObjectGenerator.getCustomer();
                customerType = ObjectGenerator.getCustomerType();

                dateFormatConverter.convert(dp_join_date, "yyyy-MM-dd");
                dataReader.fillStatusCombo(cmb_activation_status);
                dataReader.fillCustomerTypeCombo(cmb_customer_type);
                dataReader.fillCustomerTable(tbl_customer);

            });
            readyData.setName("Customer Controller");
            readyData.start();
            readyCustomerTable();
            cmb_activation_status.setValue("ACTIVE");
            cmb_customer_type.setValue("NORMAL");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void resetText() {

        txt_contact_id.setText("");
        txt_fname.setText("");
        txt_mname.setText("");
        txt_lname.setText("");
        txt_nic.setText("");
        dp_join_date.setValue(LocalDate.now());
        txt_address_id.setText("");
        ta_address.setText("");
        cmb_activation_status.setValue("ACTIVE");
        txt_contact_id.setText("");
        ta_contact.setText("");
    }

    public void readyCustomerTable() {
        tc_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tc_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        tc_nic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        tc_joindate.setCellValueFactory(new PropertyValueFactory<>("joinDate"));
        tc_cutomer_type.setCellValueFactory(new PropertyValueFactory<>("customerType"));
        tc_avtivation_status.setCellValueFactory(new PropertyValueFactory<>("activationStatus"));
    }

    public void loadAddressManager(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                /*
                 * Set txtAddressId and tfAddress to ComponentSwitcher
                 * */
                switcher.setTxt01(txt_address_id);
                switcher.setTxta01(ta_address);

                Stage addressStage = new Stage();
                Parent frmAddress = FXMLLoader.load(getClass().getClassLoader().getResource("Views/frmAddress.fxml"));
                addressStage.setTitle("Address Management");
                Scene scene = new Scene(frmAddress);
                addressStage.setScene(scene);
                addressStage.initStyle(StageStyle.UTILITY);
                addressStage.setResizable(false);
                addressStage.initModality(Modality.APPLICATION_MODAL);
                addressStage.show();
                txt_contact_id.requestFocus();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    public void loadContactManager(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                switcher.setTxt01(txt_contact_id);
                switcher.setTxta01(ta_contact);

                Stage addressStage = new Stage();
                Parent frmContact = FXMLLoader.load(getClass().getClassLoader().getResource("Views/frmContact.fxml"));
                addressStage.setTitle("Contact Management");
                Scene scene = new Scene(frmContact);
                addressStage.setScene(scene);
                addressStage.initStyle(StageStyle.UTILITY);
                addressStage.setResizable(false);
                addressStage.initModality(Modality.APPLICATION_MODAL);
                addressStage.show();
                dp_join_date.requestFocus();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    public void saveCustomer() {
        try {
            customer.setFirstName(txt_fname.getText());
            customer.setMiddleName(txt_mname.getText());
            customer.setLastName(txt_lname.getText());
            customer.setNic(txt_nic.getText());
            customer.setJoinDate(dp_join_date.getValue().toString());

            customerType.setType(cmb_customer_type.getValue());
            dataReader.getCustomerTypeByType();

            address.setId(Integer.parseInt(txt_address_id.getText()));
            contact.setId(Integer.parseInt(txt_contact_id.getText()));

            adStatus.setStatus(cmb_activation_status.getValue());
            dataReader.getStatusDetailsByStatus();

            int saveCustomer = dataWriter.saveCustomer();
            if (saveCustomer > 0) {
                customer.resetAll();
                contact.resetAll();
                adStatus.resetAll();
                address.resetAll();

                resetText();
                dataReader.fillCustomerTable(tbl_customer);
                alerts.getInformationAlert("Information", "Customer Registration", "Congratulation Chief..!\nEmployee registration successful");
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void saveCustomerKey(KeyEvent event) {

        if (event.getCode().equals(KeyCode.ENTER)) {
            saveCustomer();
        }
    }

    public void selectCustomer() {
        try {
            if (!tbl_customer.getSelectionModel().isEmpty()) {

                CustomerList customerList = tbl_customer.getSelectionModel().getSelectedItem();

                customer.setId(customerList.id.get());
                dataReader.getCustomerByCustomerId();

                txt_customer_id.setText(Integer.toString(customer.getId()));
                txt_fname.setText((customer.getFirstName()));
                txt_mname.setText((customer.getMiddleName()));
                txt_lname.setText((customer.getLastName()));
                txt_nic.setText((customer.getNic()));

                txt_address_id.setText(Integer.toString(address.getId()));
                String address01 = address.getNumber() + "" +
                        ",\n" + address.getLine01() + "" +
                        ",\n" + address.getLine02() + "" +
                        ",\n" + address.getCity() + "" +
                        ",\n" + address.getCountry() + "" +
                        ",\n" + address.getPostalCode() + ".";
                ta_address.setText(address01);

                txt_contact_id.setText(Integer.toString(contact.getId()));
                String contact01 = "" +
                        "Mobile.............." + contact.getMobile() + "\n" +
                        "Land.................." + contact.getLand() + "\n" +
                        "Fax....................." + contact.getFax() + "\n" +
                        "Email................." + contact.getEmail() + "\n" +
                        "Web.................." + contact.getWeb();
                ta_contact.setText(contact01);

                dp_join_date.setValue(LocalDate.parse(customer.getJoinDate()));

                cmb_customer_type.setValue(customerType.getType());
                cmb_activation_status.setValue(adStatus.getStatus());

                customerType.resetAll();
                address.resetAll();
                contact.resetAll();
                adStatus.resetAll();
                customer.resetAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void selectCustomerKey(KeyEvent event) {
        if (event.getCode().equals(KeyCode.UP) | event.getCode().equals(KeyCode.DOWN)) {
            selectCustomer();
        }
    }

    public void filterCustomerTableByNic() {
        try {
            customer.setNic(txt_type_nic.getText());
            dataReader.filterCustomerTableByNic(tbl_customer);
            customer.resetAll();
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void filterCustomerTableByName() {
        try {
            customer.setFirstName(txt_type_name.getText());
            dataReader.filterCustomerTableByName(tbl_customer);
            customer.resetAll();
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void updateCustomer() {
        try {
            customer.setId(Integer.parseInt(txt_customer_id.getText()));
            customer.setFirstName(txt_fname.getText());
            customer.setMiddleName(txt_mname.getText());
            customer.setLastName(txt_lname.getText());
            customer.setNic(txt_nic.getText());
            customer.setJoinDate(dp_join_date.getValue().toString());

            customerType.setType(cmb_customer_type.getValue());
            dataReader.getCustomerTypeByType();

            address.setId(Integer.parseInt(txt_address_id.getText()));
            contact.setId(Integer.parseInt(txt_contact_id.getText()));

            adStatus.setStatus(cmb_activation_status.getValue());
            dataReader.getStatusDetailsByStatus();

            int updateCustomer = dataWriter.updateCustomer();
            if (updateCustomer > 0) {
                customer.resetAll();
                contact.resetAll();
                adStatus.resetAll();
                address.resetAll();

                resetText();
                dataReader.fillCustomerTable(tbl_customer);
                alerts.getInformationAlert("Information", "Employee Update modification", "Congratulation Chief..!\nEmployee registration successful");
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void updateCustomerKey(KeyEvent event) {

        if (event.getCode().equals(KeyCode.ENTER)) {
            updateCustomer();
        }
    }


    public static class CustomerList {
        SimpleIntegerProperty id;
        SimpleStringProperty name;
        SimpleStringProperty nic;
        SimpleStringProperty joinDate;
        SimpleStringProperty customerType;
        SimpleStringProperty activationStatus;

        public CustomerList(int id, String name, String nic, String joinDate, String customerType, String activationStatus) {
            this.id = new SimpleIntegerProperty(id);
            this.name = new SimpleStringProperty(name);
            this.nic = new SimpleStringProperty(nic);
            this.joinDate = new SimpleStringProperty(joinDate);
            this.customerType = new SimpleStringProperty(customerType);
            this.activationStatus = new SimpleStringProperty(activationStatus);
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

        public String getName() {
            return name.get();
        }

        public SimpleStringProperty nameProperty() {
            return name;
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public String getNic() {
            return nic.get();
        }

        public SimpleStringProperty nicProperty() {
            return nic;
        }

        public void setNic(String nic) {
            this.nic.set(nic);
        }

        public String getJoinDate() {
            return joinDate.get();
        }

        public SimpleStringProperty joinDateProperty() {
            return joinDate;
        }

        public void setJoinDate(String joinDate) {
            this.joinDate.set(joinDate);
        }

        public String getCustomerType() {
            return customerType.get();
        }

        public SimpleStringProperty customerTypeProperty() {
            return customerType;
        }

        public void setCustomerType(String customerType) {
            this.customerType.set(customerType);
        }

        public String getActivationStatus() {
            return activationStatus.get();
        }

        public SimpleStringProperty activationStatusProperty() {
            return activationStatus;
        }

        public void setActivationStatus(String activationStatus) {
            this.activationStatus.set(activationStatus);
        }
    }


}

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
    private TableView<?> tbl_customer;

    @FXML
    private TableColumn<?, ?> tc_id;

    @FXML
    private TableColumn<?, ?> tc_name;

    @FXML
    private TableColumn<?, ?> tc_nic;

    @FXML
    private TableColumn<?, ?> tc_joindate;

    @FXML
    private TableColumn<?, ?> tc_cutomer_type;

    @FXML
    private TableColumn<?, ?> tc_avtivation_status;

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

                dateFormatConverter.convert(dp_join_date, "yyyy-MM-dd");
                dataReader.fillStatusCombo(cmb_activation_status);
                dataReader.fillCustomerTypeCombo(cmb_customer_type);
            });
            readyData.setName("Customer Controller");
            readyData.start();
            redyCustomerTable();
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

    public void redyCustomerTable() {
        tc_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tc_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        tc_nic.setCellValueFactory(new PropertyValueFactory<>("join_date"));
        tc_joindate.setCellValueFactory(new PropertyValueFactory<>("nic"));
        tc_cutomer_type.setCellValueFactory(new PropertyValueFactory<>("cutomer_type"));
        tc_avtivation_status.setCellValueFactory(new PropertyValueFactory<>("status"));
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

    public static class CustomerList {
        SimpleIntegerProperty id;
        SimpleStringProperty name;
        SimpleStringProperty nic;
        SimpleStringProperty joindate;
        SimpleStringProperty customertype;
        SimpleStringProperty activationstatus;

        public CustomerList(int id, String name, String nic, String joindate, String customertype, String activationstatus) {
            this.id = new SimpleIntegerProperty(id);
            this.name = new SimpleStringProperty(name);
            this.nic = new SimpleStringProperty(nic);
            this.joindate = new SimpleStringProperty(joindate);
            this.customertype = new SimpleStringProperty(customertype);
            this.activationstatus = new SimpleStringProperty(activationstatus);
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

        public String getJoindate() {
            return joindate.get();
        }

        public SimpleStringProperty joindateProperty() {
            return joindate;
        }

        public void setJoindate(String joindate) {
            this.joindate.set(joindate);
        }

        public String getCustomertype() {
            return customertype.get();
        }

        public SimpleStringProperty customertypeProperty() {
            return customertype;
        }

        public void setCustomertype(String customertype) {
            this.customertype.set(customertype);
        }

        public String getActivationstatus() {
            return activationstatus.get();
        }

        public SimpleStringProperty activationstatusProperty() {
            return activationstatus;
        }

        public void setActivationstatus(String activationstatus) {
            this.activationstatus.set(activationstatus);
        }
    }
}

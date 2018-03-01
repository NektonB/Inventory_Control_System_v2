package Controllers;

import DataControllers.DataReader;
import DataControllers.DataWriter;
import Modules.ADStatus;
import Modules.Address;
import Modules.ComponentSwitcher;
import Modules.Employee;
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

public class EmployeeController implements Initializable {

    @FXML
    private JFXTextField txt_Employee_Id;

    @FXML
    private JFXTextField txt_First_Name;

    @FXML
    private JFXTextField txt_Middle_Name;

    @FXML
    private JFXTextField txt_Last_Name;

    @FXML
    private JFXTextField txt_NIC_No;

    @FXML
    private JFXTextField txt_Contact_Id;

    @FXML
    private JFXButton btn_Save;

    @FXML
    private JFXButton btn_Update;

    @FXML
    private JFXDatePicker dp_date_of_birth;

    @FXML
    private JFXComboBox<String> cmb_Activation_status;

    @FXML
    private TableView<EmployeeList> tbl_employee;

    @FXML
    private TableColumn<EmployeeList, Integer> tc_id;

    @FXML
    private TableColumn<EmployeeList, String> tc_name;

    @FXML
    private TableColumn<EmployeeList, String> tc_dob;

    @FXML
    private TableColumn<EmployeeList, String> tc_nic;

    @FXML
    private TableColumn<EmployeeList, String> tc_join_date;

    @FXML
    private TableColumn<EmployeeList, String> tc_status;

    @FXML
    private JFXDatePicker dp_join_date;

    @FXML
    private JFXTextArea ta_address;

    @FXML
    private JFXTextField txt_AddressId;

    @FXML
    private JFXTextArea ta_Contact;

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
    Employee employee;

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
                employee = ObjectGenerator.getEmployee();

                dateFormatConverter.convert(dp_date_of_birth, "yyyy-MM-dd");
                dateFormatConverter.convert(dp_join_date, "yyyy-MM-dd");
                dataReader.fillStatusCombo(cmb_Activation_status);
                dataReader.fillEmployeeTable(tbl_employee);
            });
            readyData.setName("Employee Controller");
            readyData.start();
            readyEmployeeTable();
            cmb_Activation_status.setValue("ACTIVE");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void resetText() {

        txt_Employee_Id.setText("");
        txt_First_Name.setText("");
        txt_Last_Name.setText("");
        txt_Middle_Name.setText("");
        txt_NIC_No.setText("");
        dp_date_of_birth.setValue(LocalDate.now());
        dp_join_date.setValue(LocalDate.now());
        txt_AddressId.setText("");
        ta_address.setText("");
        cmb_Activation_status.setValue("ACTIVE");
        txt_Contact_Id.setText("");
        ta_Contact.setText("");
    }

    public void readyEmployeeTable() {
        tc_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tc_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        tc_dob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        tc_nic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        tc_join_date.setCellValueFactory(new PropertyValueFactory<>("join_date"));
        tc_status.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    public void loadAddressManager(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                /*
                 * Set txtAddressId and tfAddress to ComponentSwitcher
                 * */
                switcher.setTxt01(txt_AddressId);
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
                txt_Contact_Id.requestFocus();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    /**
     * Load Contact Manager
     */
    public void loadContactManager(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                switcher.setTxt01(txt_Contact_Id);
                switcher.setTxta01(ta_Contact);

                Stage addressStage = new Stage();
                Parent frmContact = FXMLLoader.load(getClass().getClassLoader().getResource("Views/frmContact.fxml"));
                addressStage.setTitle("Contact Management");
                Scene scene = new Scene(frmContact);
                addressStage.setScene(scene);
                addressStage.initStyle(StageStyle.UTILITY);
                addressStage.setResizable(false);
                addressStage.initModality(Modality.APPLICATION_MODAL);
                addressStage.show();
                cmb_Activation_status.requestFocus();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    public void saveEmployee() {
        try {
            employee.setFirstName(txt_First_Name.getText());
            employee.setMiddleName(txt_Middle_Name.getText());
            employee.setLastName(txt_Last_Name.getText());
            employee.setDob(dp_date_of_birth.getValue().toString());
            employee.setNic(txt_NIC_No.getText());
            employee.setJoinDate(dp_join_date.getValue().toString());
            address.setId(Integer.parseInt(txt_AddressId.getText()));
            contact.setId(Integer.parseInt(txt_Contact_Id.getText()));

            adStatus.setStatus(cmb_Activation_status.getValue());
            dataReader.getStatusDetailsByStatus();

            int saveEmployee = dataWriter.saveEmployee();
            if (saveEmployee > 0) {
                employee.resetAll();
                contact.resetAll();
                adStatus.resetAll();
                address.resetAll();

                resetText();
                dataReader.fillEmployeeTable(tbl_employee);
                alerts.getInformationAlert("Information", "Employee Registration", "Congratulation Chief..!\nEmployee registration successful");
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void saveEmployeeKey(KeyEvent event) {

        if (event.getCode().equals(KeyCode.ENTER)) {
            saveEmployee();
        }
    }

    public void selectEmloyee() {
        try {
            if (!tbl_employee.getSelectionModel().isEmpty()) {

                EmployeeList employeeList = tbl_employee.getSelectionModel().getSelectedItem();

                employee.setId(employeeList.id.get());
                dataReader.getEmployeeByEmployeeId();

                txt_Employee_Id.setText(Integer.toString(employee.getId()));
                txt_First_Name.setText((employee.getFirstName()));
                txt_Middle_Name.setText((employee.getMiddleName()));
                txt_Last_Name.setText((employee.getLastName()));
                dp_date_of_birth.setValue(LocalDate.parse(employee.getDob()));
                txt_NIC_No.setText((employee.getNic()));
                dp_join_date.setValue(LocalDate.parse(employee.getJoinDate()));

                txt_AddressId.setText(Integer.toString(address.getId()));
                String address01 = address.getNumber() + "" +
                        ",\n" + address.getLine01() + "" +
                        ",\n" + address.getLine02() + "" +
                        ",\n" + address.getCity() + "" +
                        ",\n" + address.getCountry() + "" +
                        ",\n" + address.getPostalCode() + ".";
                ta_address.setText(address01);

                txt_Contact_Id.setText(Integer.toString(contact.getId()));
                String contact01 = "" +
                        "Mobile.............." + contact.getMobile() + "\n" +
                        "Land.................." + contact.getLand() + "\n" +
                        "Fax....................." + contact.getFax() + "\n" +
                        "Email................." + contact.getEmail() + "\n" +
                        "Web.................." + contact.getWeb();
                ta_Contact.setText(contact01);

                cmb_Activation_status.setValue(adStatus.getStatus());


                address.resetAll();
                contact.resetAll();
                adStatus.resetAll();
                employee.resetAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void selectEmloyeeKey(KeyEvent event) {
        if (event.getCode().equals(KeyCode.UP) | event.getCode().equals(KeyCode.DOWN)) {
            selectEmloyee();
        }
    }

    public void filterEmployeeTableByNic(KeyEvent event) {
        try {
            employee.setNic(txt_type_nic.getText());
            dataReader.filterEmployeeTableByNic(tbl_employee);
            employee.resetAll();
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void filterEmployeeTableByName(KeyEvent event) {
        try {
            employee.setNic(txt_type_name.getText());
            dataReader.filterEmployeeTableByName(tbl_employee);
            employee.resetAll();
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void updateEmployee() {
        try {
            employee.setId(Integer.parseInt(txt_Employee_Id.getText()));
            employee.setFirstName(txt_First_Name.getText());
            employee.setMiddleName(txt_Middle_Name.getText());
            employee.setLastName(txt_Last_Name.getText());
            employee.setDob(dp_date_of_birth.getValue().toString());
            employee.setNic(txt_NIC_No.getText());
            employee.setJoinDate(dp_join_date.getValue().toString());
            address.setId(Integer.parseInt(txt_AddressId.getText()));
            contact.setId(Integer.parseInt(txt_Contact_Id.getText()));

            adStatus.setStatus(cmb_Activation_status.getValue());
            dataReader.getStatusDetailsByStatus();

            int updateEmployee = dataWriter.updateEmployee();
            if (updateEmployee > 0) {
                employee.resetAll();
                contact.resetAll();
                adStatus.resetAll();
                address.resetAll();

                resetText();
                dataReader.fillEmployeeTable(tbl_employee);
                alerts.getInformationAlert("Information", "Employee Update modification", "Congratulation Chief..!\nEmployee registration successful");
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public static class EmployeeList {
        SimpleIntegerProperty id;
        SimpleStringProperty name;
        SimpleStringProperty dob;
        SimpleStringProperty nic;
        SimpleStringProperty join_date;
        SimpleStringProperty status;

        public EmployeeList(int id, String name, String dob, String nic, String join_date, String status) {
            this.id = new SimpleIntegerProperty(id);
            this.name = new SimpleStringProperty(name);
            this.dob = new SimpleStringProperty(dob);
            this.nic = new SimpleStringProperty(nic);
            this.join_date = new SimpleStringProperty(join_date);
            this.status = new SimpleStringProperty(status);
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

        public String getDob() {
            return dob.get();
        }

        public SimpleStringProperty dobProperty() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob.set(dob);
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

        public String getJoin_date() {
            return join_date.get();
        }

        public SimpleStringProperty join_dateProperty() {
            return join_date;
        }

        public void setJoin_date(String join_date) {
            this.join_date.set(join_date);
        }

        public String getStatus() {
            return status.get();
        }

        public SimpleStringProperty statusProperty() {
            return status;
        }

        public void setStatus(String status) {
            this.status.set(status);
        }
    }
}

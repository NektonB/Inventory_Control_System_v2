package Controllers;

import DataControllers.DataReader;
import DataControllers.DataWriter;
import Modules.ADStatus;
import Modules.Address;
import Modules.ComponentSwitcher;
import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
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
    private TableView<?> tbl_employee;

    @FXML
    private JFXDatePicker dp_join_date;

    @FXML
    private JFXTextArea ta_address;

    @FXML
    private JFXTextField txt_AddressId;

    @FXML
    private JFXTextArea ta_Contact;

    DataWriter dataWriter;
    DataReader dataReader;
    Alerts alerts;
    Address address;
    ComponentSwitcher switcher;
    DateFormatConverter dateFormatConverter;
    Modules.Contact contact;
    ADStatus adStatus;
    Modules.CompanyList companyList;

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
                companyList = ObjectGenerator.getCompanyList();

                dateFormatConverter.convert(dp_date_of_birth, "yyyy-MM-dd");
                dataReader.fillStatusCombo(cmb_Activation_status);
            });
            readyData.setName("Employee Controller");
            readyData.start();
            cmb_Activation_status.setValue("ACTIVE");
        } catch (Exception e) {
            e.printStackTrace();
        }


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
                Parent frmUser = FXMLLoader.load(getClass().getClassLoader().getResource("Views/frmAddress.fxml"));
                addressStage.setTitle("Address Management");
                Scene scene = new Scene(frmUser);
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
}

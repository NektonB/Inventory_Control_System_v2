package Controllers;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
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
    private JFXComboBox<?> txt_customer_type;

    @FXML
    private JFXTextField cmb_activation_status;

    @FXML
    private JFXButton btn_Save;

    @FXML
    private JFXButton btn_Update;

    @FXML
    private TableView<?> tbl_customer;

    @FXML
    private JFXTextField txt_type_nic;

    @FXML
    private JFXTextField txt_type_name;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

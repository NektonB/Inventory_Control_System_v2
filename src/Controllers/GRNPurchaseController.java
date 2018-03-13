package Controllers;

import DataControllers.DataReader;
import DataControllers.DataWriter;
import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class GRNPurchaseController implements Initializable{

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
    private JFXComboBox<String> cmbMethode;

    @FXML
    private JFXTextField txtPayedAmountPart;

    @FXML
    private TableView<?> tblPayment;

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

                dateFormatConverter.convert(dpGRNDate,"yyyy-MM-dd");
                timeFormatConverter.convert(tpGRNTime,"hh:mm");
                tpGRNTime.setValue(LocalTime.now());
                validator.validateDigit(txtManualDisValue, 10, 2);
                validator.validateDigit(txtManualDisPercentage, 3, 2);
                validator.validateDigit(txtPayedAmountPart, 10, 2);

            });
            readyData.setName("GRN Purchase Controller");
            readyData.start();

        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }
}

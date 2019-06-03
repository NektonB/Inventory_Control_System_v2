package Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class StockLogReportController implements Initializable {

    @FXML
    private JFXDatePicker dpStartDate;

    @FXML
    private JFXDatePicker dpEndDate;

    @FXML
    private JFXButton btnShowNow;

    DateFormatConverter dateFormatConverter;
    Alerts alerts;
    ReportViewer reportViewer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            alerts = ObjectGenerator.getAlerts();
            dateFormatConverter = ObjectGenerator.getDateFormatConverter();
            reportViewer = ObjectGenerator.getReportViewer();

            dateFormatConverter.convert(dpStartDate,"yyyy-MM-dd");
            dateFormatConverter.convert(dpEndDate,"yyyy-MM-dd");
        } catch (Exception e) {
            alerts.getErrorAlert(e);
        }
    }

    public void showNow(){
        try{
            reportViewer.getStockLogByStockDates(dpStartDate.getValue().toString(),dpEndDate.getValue().toString(),"VIEW");
        }catch(Exception e){
            alerts.getErrorAlert(e);
        }
    }
}

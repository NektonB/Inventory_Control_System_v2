package Controllers;

import DataControllers.DataReader;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import org.hibernate.cfg.Configuration;
import org.hibernate.event.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class InvoiceViewer implements javafx.fxml.Initializable {

    DataReader dataReader;
    Alerts alerts;
    ReportViewer reportViewer;

    @FXML
    JFXTextField txtInvoiceNumber;

    @FXML
    JFXButton btnViewNow;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            alerts = ObjectGenerator.getAlerts();
            dataReader = ObjectGenerator.getDataReader();
            reportViewer = ObjectGenerator.getReportViewer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewInvoice() {
        try {
            int invoiceId = Integer.parseInt(txtInvoiceNumber.getText());
            String invoiceTypeById = dataReader.getInvoiceTypeById(invoiceId);

            if (invoiceTypeById.equals("PAYED")) {
                reportViewer.getPaidInvoice(Integer.toString(invoiceId), "VIEW");
            } else if (invoiceTypeById.equals("NOT PAYED")){
                reportViewer.getCreditInvoice(Integer.toString(invoiceId), "VIEW");
            }

        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }
}

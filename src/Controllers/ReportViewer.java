package Controllers;

import DB_Conn.ConnectDB;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.awt.*;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class ReportViewer {

    Connection conn;

    public ReportViewer() {
        conn = ConnectDB.getConn();
    }

    public void getPaidInvoice(String invoiceId, String viewType) {
        try {
            String path = "C:\\Program Files\\Common Files\\ICSv2\\Reports\\Paid_Invoice.jrxml";
            JasperReport RI = JasperCompileManager.compileReport(path);
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("InvoiceNumber", invoiceId);
            JasperPrint printIt = JasperFillManager.fillReport(RI, parameter, conn);
            if (viewType.equals("PRINT")) {
                JasperPrintManager.printReport(printIt, false);
                //JasperPrintManager.printReport(printIt, false);
            } else if (viewType.equals("VIEW")) {
                JasperViewer.viewReport(printIt, false);
            }
        } catch (Exception e) {
            Toolkit.getDefaultToolkit().beep();
            e.printStackTrace();

        }
    }

    public void getCreditInvoice(String invoiceId, String viewType) {
        try {
            String path = "C:\\Program Files\\Common Files\\ICSv2\\Reports\\Credit_Invoice.jrxml";
            JasperReport RI = JasperCompileManager.compileReport(path);
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("InvoiceNumber", invoiceId);
            JasperPrint printIt = JasperFillManager.fillReport(RI, parameter, conn);
            if (viewType.equals("PRINT")) {
                JasperPrintManager.printReport(printIt, false);
                //JasperPrintManager.printReport(printIt, false);
            } else if (viewType.equals("VIEW")) {
                JasperViewer.viewReport(printIt, false);
            }
        } catch (Exception e) {
            Toolkit.getDefaultToolkit().beep();
            e.printStackTrace();

        }
    }
}

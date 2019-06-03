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
                /*try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    //JasperViewer viewer = new JasperViewer(printIt, false);
                    //viewer.setVisible(true);
                    JasperViewer.viewReport(printIt, false);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }*/
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

    public void getStockLogByStockDates(String startDate, String endDate, String viewType) {
        try {
            String path = "C:\\Program Files\\Common Files\\ICSv2\\Reports\\StockLogByStockDate.jrxml";
            JasperReport RI = JasperCompileManager.compileReport(path);
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("startDate", startDate);
            parameter.put("endDate", endDate);
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

    public void getDailyInvoiceSummary(String invoicedDate, String viewType) {
        try {
            String path = "C:\\Program Files\\Common Files\\ICSv2\\Reports\\DailyInvoiceSummary.jrxml";
            JasperReport RI = JasperCompileManager.compileReport(path);
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("invoicedDate", invoicedDate);

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

    public void getDailyUserInvoiceSummary(String invoicedDate, String userName, String viewType) {
        try {
            String path = "C:\\Program Files\\Common Files\\ICSv2\\Reports\\DailyUserInvoiceSummary.jrxml";
            JasperReport RI = JasperCompileManager.compileReport(path);
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("invoicedDate", invoicedDate);
            parameter.put("userName", userName);

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

    public void getDailySaleSummary(String invoicedDate, String viewType) {
        try {
            String path = "C:\\Program Files\\Common Files\\ICSv2\\Reports\\SaleReport.jrxml";
            JasperReport RI = JasperCompileManager.compileReport(path);
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("saleDate", invoicedDate);

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

package Controllers;

import DataControllers.DataReader;
import DataControllers.DataWriter;
import Modules.*;

public class ObjectGenerator {

    private static DataReader dataReader;
    private static DataWriter dataWriter;
    private static Alerts alerts;
    private static BackupData backupData;
    private static ConnectionInfo connectionInfo;
    private static AutoBackup autoBackup;
    private static User user;
    private static UserType userType;
    private static ADStatus adStatus;
    private static ComponentSwitcher componentSwitcher;
    private static Address address;
    private static Contact contact;
    private static DateFormatConverter dateFormatConverter;
    private static Company company;
    private static Supplier supplier;
    private static CompanyPartner companyPartner;
    private static Partnership partnership;
    private static SupplierType supplierType;
    private static CompanyList companyList;
    private static Employee employee;
    private static Category category;
    private static Unit unit;
    private static Customer customer;
    private static SupplierPartner supplierPartner;
    private static CustomerType customerType;
    private static Product product;
    private static TextValidator textValidator;
    private static TimeFormatConverter timeFormatConverter;
    private static PaymentType paymentType;
    private static PaymentMethod paymentMethod;
    private static GRN grn;
    private static MethodList methodList;
    private static PayStatus payStatus;
    private static Approve approve;
    private static GrnItems grnItems;
    private static Stock stock;
    private static InvoiceInterConnector interConnector;
    private static Invoice invoice;
    private static InvoiceItems invoiceItems;
    private static ReportViewer reportViewer;

    public static synchronized void readyAll() {
        try {
            String ready = "ready";
            alerts = new Alerts();
            dataReader = new DataReader();
            dataWriter = new DataWriter();
            backupData = new BackupData();

        } catch (Exception e) {
            e.printStackTrace();
            alerts.getWarningAlert("Error Alert", "Something went wrong..", e.toString());
        }
    }

    public static synchronized Alerts getAlerts() {
        if (alerts == null) {
            alerts = new Alerts();
        }
        return alerts;
    }

    public static synchronized DataReader getDataReader() {
        if (dataReader == null) {
            dataReader = new DataReader();
        }
        return dataReader;
    }

    public static synchronized DataWriter getDataWriter() {
        if (dataWriter == null) {
            dataWriter = new DataWriter();
        }
        return dataWriter;
    }

    public static synchronized BackupData getBackupData() {
        if (backupData == null) {
            backupData = new BackupData();
        }
        return backupData;
    }

    public static synchronized ConnectionInfo getConnectionInfo() {
        if (connectionInfo == null) {
            connectionInfo = new ConnectionInfo();
        }
        return connectionInfo;
    }

    public static synchronized AutoBackup getAutoBackup() {
        if (autoBackup == null) {
            autoBackup = new AutoBackup();
        }
        return autoBackup;
    }

    public static synchronized User getUser() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public static synchronized UserType getUserType() {
        if (userType == null) {
            userType = new UserType();
        }
        return userType;
    }

    public static synchronized ADStatus getAdStatus() {
        if (adStatus == null) {
            adStatus = new ADStatus();
        }
        return adStatus;
    }

    public static synchronized ComponentSwitcher getComponentSwitcher() {
        if (componentSwitcher == null) {
            componentSwitcher = new ComponentSwitcher();
        }
        return componentSwitcher;
    }

    public static synchronized Address getAddress() {
        if (address == null) {
            address = new Address();
        }
        return address;
    }

    public static synchronized Contact getContact() {
        if (contact == null) {
            contact = new Contact();
        }
        return contact;
    }

    public static synchronized DateFormatConverter getDateFormatConverter() {
        if (dateFormatConverter == null) {
            dateFormatConverter = new DateFormatConverter();
        }
        return dateFormatConverter;
    }

    public static synchronized Company getCompany() {
        if (company == null) {
            company = new Company();
        }
        return company;
    }

    public static synchronized Supplier getSupplier() {
        if (supplier == null) {
            supplier = new Supplier();
        }
        return supplier;
    }

    public static synchronized CompanyPartner getCompanyPartner() {
        if (companyPartner == null) {
            companyPartner = new CompanyPartner();
        }
        return companyPartner;
    }

    public static synchronized Partnership getPartnership() {
        if (partnership == null) {
            partnership = new Partnership();
        }
        return partnership;
    }

    public static synchronized SupplierType getSupplierType() {
        if (supplierType == null) {
            supplierType = new SupplierType();
        }
        return supplierType;
    }

    public static synchronized CompanyList getCompanyList() {
        if (companyList == null) {
            companyList = new CompanyList();
        }
        return companyList;
    }

    public static synchronized Employee getEmployee() {
        if (employee == null) {
            employee = new Employee();
        }
        return employee;
    }

    public static synchronized Category getCategory() {
        if (category == null) {
            category = new Category();
        }
        return category;
    }

    public static synchronized Unit getUnit() {
        if (unit == null) {
            unit = new Unit();
        }
        return unit;
    }

    public static synchronized Customer getCustomer() {
        if (customer == null) {
            customer = new Customer();
        }
        return customer;
    }

    public static synchronized SupplierPartner getSupplierPartner() {
        if (supplierPartner == null) {
            supplierPartner = new SupplierPartner();
        }
        return supplierPartner;
    }

    public static synchronized CustomerType getCustomerType() {
        if (customerType == null) {
            customerType = new CustomerType();
        }

        return customerType;
    }

    public static synchronized Product getProduct() {
        if (product == null) {
            product = new Product();
        }
        return product;

    }

    public static synchronized TextValidator getTextValidator() {
        if (textValidator == null) {
            textValidator = new TextValidator();
        }
        return textValidator;
    }

    public static synchronized TimeFormatConverter getTimeFormatConverter() {
        if (timeFormatConverter == null) {
            timeFormatConverter = new TimeFormatConverter();
        }
        return timeFormatConverter;
    }

    public static synchronized PaymentType getPaymentType() {
        if (paymentType == null) {
            paymentType = new PaymentType();
        }
        return paymentType;
    }

    public static synchronized PaymentMethod getPaymentMethod() {
        if (paymentMethod == null) {
            paymentMethod = new PaymentMethod();
        }
        return paymentMethod;
    }

    public static synchronized GRN getGrn() {
        if (grn == null) {
            grn = new GRN();
        }
        return grn;
    }

    public static synchronized MethodList getMethodList() {
        if (methodList == null) {
            methodList = new MethodList();
        }
        return methodList;
    }

    public static synchronized PayStatus getPayStatus() {
        if (payStatus == null) {
            payStatus = new PayStatus();
        }
        return payStatus;
    }

    public static synchronized Approve getApprove() {
        if (approve == null) {
            approve = new Approve();
        }
        return approve;
    }

    public static synchronized GrnItems getGrnItems() {
        if (grnItems == null) {
            grnItems = new GrnItems();
        }
        return grnItems;
    }

    public static synchronized Stock getStock() {
        if (stock == null) {
            stock = new Stock();
        }
        return stock;
    }

    public static synchronized InvoiceInterConnector getInterConnector() {
        if (interConnector == null) {
            interConnector = new InvoiceInterConnector();
        }
        return interConnector;
    }

    public static synchronized Invoice getInvoice() {
        if (invoice == null) {
            invoice = new Invoice();
        }
        return invoice;
    }

    public static synchronized InvoiceItems getInvoiceItems() {
        if (invoiceItems == null) {
            invoiceItems = new InvoiceItems();
        }
        return invoiceItems;
    }

    public static synchronized ReportViewer getReportViewer() {
        if (reportViewer == null) {
            reportViewer = new ReportViewer();
        }
        return reportViewer;
    }
}

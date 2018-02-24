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

    public static User getUser() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public static UserType getUserType() {
        if (userType == null) {
            userType = new UserType();
        }
        return userType;
    }

    public static ADStatus getAdStatus() {
        if (adStatus == null) {
            adStatus = new ADStatus();
        }
        return adStatus;
    }

    public static ComponentSwitcher getComponentSwitcher() {
        if (componentSwitcher == null) {
            componentSwitcher = new ComponentSwitcher();
        }
        return componentSwitcher;
    }

    public static Address getAddress() {
        if (address == null) {
            address = new Address();
        }
        return address;
    }

    public static Contact getContact() {
        if (contact == null) {
            contact = new Contact();
        }
        return contact;
    }

    public static DateFormatConverter getDateFormatConverter() {
        if (dateFormatConverter == null) {
            dateFormatConverter = new DateFormatConverter();
        }
        return dateFormatConverter;
    }

    public static Company getCompany() {
        if (company == null) {
            company = new Company();
        }
        return company;
    }

    public static Supplier getSupplier() {
        if (supplier == null) {
            supplier = new Supplier();
        }
        return supplier;
    }

    public static CompanyPartner getCompanyPartner() {
        if (companyPartner == null) {
            companyPartner = new CompanyPartner();
        }
        return companyPartner;
    }

    public static Partnership getPartnership() {
        if (partnership == null) {
            partnership = new Partnership();
        }
        return partnership;
    }

    public static SupplierType getSupplierType() {
        if (supplierType == null) {
            supplierType = new SupplierType();
        }
        return supplierType;
    }

    public static CompanyList getCompanyList() {
        if (companyList == null) {
            companyList = new CompanyList();
        }
        return companyList;
    }
}

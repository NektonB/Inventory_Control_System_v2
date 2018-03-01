package DataControllers;

import Controllers.*;
import DB_Conn.ConnConfig;
import DB_Conn.ConnectDB;
import Modules.*;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DataReader {

    PreparedStatement pst;
    Connection conn;

    BackupData backupData;
    ConnectionInfo connectionInfo;
    Alerts alerts;
    User user;
    UserType userType;
    ADStatus adStatus;
    Address address;
    Company company;
    SupplierType supplierType;
    Supplier supplier;
    Contact contact;
    CompanyPartner companyPartner;
    CompanyList companyList;
    Employee employee;
    Category category;

    public DataReader() {
        try {
            Thread readyData = new Thread(() -> {
                conn = ConnectDB.getConn();
                backupData = ObjectGenerator.getBackupData();
                connectionInfo = ObjectGenerator.getConnectionInfo();
                alerts = ObjectGenerator.getAlerts();
                user = ObjectGenerator.getUser();
                userType = ObjectGenerator.getUserType();
                adStatus = ObjectGenerator.getAdStatus();
                address = ObjectGenerator.getAddress();
                company = ObjectGenerator.getCompany();
                supplierType = ObjectGenerator.getSupplierType();
                supplier = ObjectGenerator.getSupplier();
                contact = ObjectGenerator.getContact();
                companyPartner = ObjectGenerator.getCompanyPartner();
                companyList = ObjectGenerator.getCompanyList();
                employee = ObjectGenerator.getEmployee();
                category = ObjectGenerator.getCategory();
            });
            readyData.setName("DataReader");
            readyData.start();
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void getSQLDumpPath() {
        ResultSet rs = null;
        try {
            pst = ConnConfig.getServerConfig().prepareStatement("SELECT * FROM tbl_exepath WHERE id = ? ");
            pst.setString(1, "1");
            rs = pst.executeQuery();
            while (rs.next()) {
                backupData.setMysqlDumpPath(rs.getString("path"));
            }
        } catch (Exception e) {
            //WebOptionPane.showMessageDialog(null, e, "Error", WebOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (Exception e) {
                //WebOptionPane.showMessageDialog(null, e, "Error", WebOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    public void getConnectionInformation() {
        ResultSet rs = null;
        try {
            pst = ConnConfig.getServerConfig().prepareStatement("SELECT * FROM tbl_connection WHERE Id = ? ");
            pst.setString(1, "1");
            rs = pst.executeQuery();
            while (rs.next()) {
                connectionInfo.setServerIP(rs.getString("server_ip"));
                connectionInfo.setPort(rs.getString("server_port"));
                connectionInfo.setDatabase(rs.getString("database"));
                connectionInfo.setUsername(rs.getString("user_name"));
                connectionInfo.setPassword(rs.getString("password"));
            }
        } catch (Exception e) {
            //WebOptionPane.showMessageDialog(null, e, "Error", WebOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (Exception e) {
                //WebOptionPane.showMessageDialog(null, e, "Error", WebOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    public void getUserTypeByType() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM user_type WHERE type = ?");
            pst.setString(1, userType.getType());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                userType.resetAll();
            }
            while (rs.next()) {
                userType.setId(rs.getInt(1));
                userType.setType(rs.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    public boolean checkAlreadyType(String userType01) {
        ResultSet rs = null;
        boolean already = false;
        try {
            pst = conn.prepareStatement("SELECT * FROM user_type WHERE type = ?");
            pst.setString(1, userType01);
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                already = false;
            }
            if (rs.next()) {
                already = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
        return already;
    }

    public void getUserTypeById() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM user_type WHERE id = ?");
            pst.setInt(1, userType.getId());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                userType.resetAll();
            }
            while (rs.next()) {
                userType.setId(rs.getInt(1));
                userType.setType(rs.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void getStatusDetailsByStatus() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM ad_status WHERE status = ?");
            pst.setString(1, adStatus.getStatus());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                adStatus.resetAll();
            }
            while (rs.next()) {
                adStatus.setId(rs.getInt(1));
                adStatus.setStatus(rs.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void getUserStatusById() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM ad_status WHERE id = ?");
            pst.setInt(1, adStatus.getId());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                adStatus.resetAll();
            }
            while (rs.next()) {
                adStatus.setId(rs.getInt(1));
                adStatus.setStatus(rs.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    public void fillUserTypeCombo(JFXComboBox cmbUserType) {
        ResultSet rs = null;
        cmbUserType.getItems().clear();
        try {
            pst = conn.prepareStatement("SELECT type FROM user_type");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {
                userType.resetAll();
            }
            while (rs.next()) {
                cmbUserType.getItems().add(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void fillStatusCombo(JFXComboBox cmbStatus) {
        ResultSet rs = null;
        cmbStatus.getItems().clear();
        try {
            pst = conn.prepareStatement("SELECT status FROM ad_status");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {
                //userType.resetAll();
            }
            while (rs.next()) {
                cmbStatus.getItems().add(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void fillUserTable(TableView tblUser) {
        ResultSet rs = null;
        ObservableList<UserController.UserList> userList = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement("SELECT user.id,user_name,email,user_type.type,ad_status.status FROM user INNER JOIN user_type ON user.type_id = user_type.id INNER JOIN ad_status ON user.status_id = ad_status.id");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {
                //userType.resetAll();
            }
            while (rs.next()) {
                userList.add(new UserController.UserList(rs.getInt("user.id"), rs.getString("user_name"), rs.getString("email"), rs.getString("user_type.type"), rs.getString("ad_status.status")));
            }
            tblUser.setItems(userList);
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    public void getUserByUserName() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT user.id,user_name,password,email,mobile,user_type.type,ad_status.status FROM user INNER JOIN user_type ON user.type_id = user_type.id INNER JOIN ad_status ON user.status_id = ad_status.id WHERE  user_name = ?");
            pst.setString(1, user.getUserName());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                user.resetAll();
            }
            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setMobile(rs.getString("mobile"));
                userType.setType(rs.getString("user_type.type"));
                adStatus.setStatus(rs.getString("ad_status.status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void getUserByUserId() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT user.id,user_name,password,email,mobile,user_type.type,ad_status.status FROM user INNER JOIN user_type ON user.type_id = user_type.id INNER JOIN ad_status ON user.status_id = ad_status.id WHERE  user.id = ?");
            pst.setInt(1, user.getId());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                user.resetAll();
            }
            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setMobile(rs.getString("mobile"));
                userType.setType(rs.getString("user_type.type"));
                adStatus.setStatus(rs.getString("ad_status.status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Filling the Address table using DB
     */
    public void fillAddressTable(TableView tblAddress) {
        ResultSet rs = null;
        ObservableList<AddressController.AddressList> addressList = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement("SELECT * FROM address");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {
                //userType.resetAll();
            }
            while (rs.next()) {
                addressList.add(new AddressController.AddressList(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
            }
            tblAddress.setItems(addressList);
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    /**
     * Search Address By Id
     * Search result set to Address Module
     */
    public void getAddressById() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM address WHERE id = ?");
            pst.setInt(1, address.getId());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                address.resetAll();
            }
            while (rs.next()) {
                address.setId(rs.getInt(1));
                address.setNumber(rs.getString(2));
                address.setLine01(rs.getString(3));
                address.setLine02(rs.getString(4));
                address.setCity(rs.getString(5));
                address.setCountry(rs.getString(6));
                address.setPostalCode(rs.getString(7));
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    /**
     * Filling the Address table using DB
     */
    public void fillContactTable(TableView tblContacts) {
        ResultSet rs = null;
        ObservableList<ContactController.ContactList> contactList = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement("SELECT * FROM contact");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {
                //userType.resetAll();
            }
            while (rs.next()) {
                contactList.add(new ContactController.ContactList(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
            }
            tblContacts.setItems(contactList);
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    /**
     * Check Company is already
     * if already return true.else false
     */
    public boolean checkCompanyAlready(String name) {
        boolean already = false;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM company WHERE name = ?");
            pst.setString(1, name);
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                company.resetAll();
                already = false;
            }
            if (rs.next()) {
                already = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
        return already;
    }

    /**
     * Fill data using company table
     */
    public void fillCompanyCombo(JFXComboBox cmbCompany) {
        ResultSet resultSet = null;
        cmbCompany.getItems().clear();
        try {
            pst = conn.prepareStatement("SELECT name FROM company");
            resultSet = pst.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                //category.resetData();
            }
            if (!cmbCompany.getItems().isEmpty()) {
                cmbCompany.getItems().clear();
            }
            while (resultSet.next()) {
                cmbCompany.getItems().add(resultSet.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
                pst.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void fillSupplierTypeCombo(JFXComboBox cmbSupplierType) {
        ResultSet resultSet = null;
        try {
            pst = conn.prepareStatement("SELECT type FROM supplier_type");
            resultSet = pst.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                //category.resetData();
            }
            if (!cmbSupplierType.getItems().isEmpty()) {
                cmbSupplierType.getItems().clear();
            }
            while (resultSet.next()) {
                cmbSupplierType.getItems().add(resultSet.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
                pst.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get Company Details using Company Name.
     * Search
     */
    public void getCompanyByName() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM company  WHERE  name = ?");
            pst.setString(1, company.getName());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                company.resetAll();
            }
            while (rs.next()) {
                company.setId(rs.getInt(1));
                company.setName(rs.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get Supplier Type Details using Company Name.
     * Search
     */
    public void getSupplierTypeDetailsByType() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM supplier_type  WHERE  type = ?");
            pst.setString(1, supplierType.getType());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                supplierType.resetAll();
            }
            while (rs.next()) {
                supplierType.setId(rs.getInt(1));
                supplierType.setType(rs.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Filling the Supplier table using Database supplier table support address,status,type
     */
    public void fillSupplierTable(TableView tblSupplier) {
        ResultSet rs = null;
        ObservableList<SupplierController.SupplierList> contactList = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement("SELECT supplier.id,supplier.namel,a.number,a.line_01,a.line_02,a.city,a.country,a.postal_code,st.type,ad.status FROM supplier INNER JOIN address a ON supplier.address_id = a.id INNER JOIN supplier_type st ON supplier.type_id = st.id INNER JOIN ad_status ad ON supplier.ad_status_id = ad.id");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {
                //userType.resetAll();
            }
            while (rs.next()) {
                String addressNow = rs.getString(3) + "," + rs.getString(4) + "," + rs.getString(5) + "," + rs.getString(6) + "," + rs.getString(7) + "," + rs.getString(8);
                contactList.add(new SupplierController.SupplierList(rs.getInt(1), rs.getString(2), addressNow, rs.getString(9), rs.getString(10)));
            }
            tblSupplier.setItems(contactList);
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    /**
     * Filter the Supplier table using Entered name.and wild card
     */
    public void filterSupplierTableByName(TableView tblSupplier) {
        ResultSet rs = null;
        ObservableList<SupplierController.SupplierList> contactList = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement("SELECT supplier.id,supplier.namel,a.number,a.line_01,a.line_02,a.city,a.country,a.postal_code,st.type,ad.status FROM supplier INNER JOIN address a ON supplier.address_id = a.id INNER JOIN supplier_type st ON supplier.type_id = st.id INNER JOIN ad_status ad ON supplier.ad_status_id = ad.id WHERE supplier.namel LIKE ?");
            pst.setString(1, supplier.getName() + "%");

            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {
                //userType.resetAll();
            }
            while (rs.next()) {
                String addressNow = rs.getString(3) + "," + rs.getString(4) + "," + rs.getString(5) + "," + rs.getString(6) + "," + rs.getString(7) + "," + rs.getString(8);
                contactList.add(new SupplierController.SupplierList(rs.getInt(1), rs.getString(2), addressNow, rs.getString(9), rs.getString(10)));
            }
            tblSupplier.setItems(contactList);
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    /**
     * Get Supplier Details using Supplier Id.
     * Search
     */
    public void getSupplierById() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT supplier.id,supplier.namel,supplier.join_date,a.id,a.number,a.line_01,a.line_02,a.city,a.country,a.postal_code,ct.id,ct.mobile,ct.land,ct.fax,ct.email,ct.web,st.id,st.type,ad.id,ad.status,supplier.partner_id FROM supplier INNER JOIN address a ON supplier.address_id = a.id INNER JOIN contact ct ON supplier.contact_id = ct.id INNER JOIN ad_status ad ON supplier.ad_status_id = ad.id INNER JOIN supplier_type st ON supplier.type_id = st.id WHERE  supplier.id = ?");
            pst.setInt(1, supplier.getId());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                supplier.resetAll();
                address.resetAll();
                contact.resetAll();
                supplierType.resetAll();
                adStatus.resetAll();
                companyPartner.resetAll();
            }
            if (rs.next()) {
                supplier.setId(rs.getInt(1));
                supplier.setName(rs.getString(2));
                supplier.setJoinDate(rs.getString(3));

                address.setId(rs.getInt(4));
                address.setNumber(rs.getString(5));
                address.setLine01(rs.getString(6));
                address.setLine02(rs.getString(7));
                address.setCity(rs.getString(8));
                address.setCountry(rs.getString(9));
                address.setPostalCode(rs.getString(10));

                contact.setId(rs.getInt(11));
                contact.setMobile(rs.getString(12));
                contact.setLand(rs.getString(13));
                contact.setFax(rs.getString(14));
                contact.setEmail(rs.getString(15));
                contact.setWeb(rs.getString(16));

                supplierType.setId(rs.getInt(17));
                supplierType.setType(rs.getString(18));

                adStatus.setId(rs.getInt(19));
                adStatus.setStatus(rs.getString(20));

                companyPartner.setId(rs.getInt(21));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get Company List Details using Partner Id.
     * Search
     */
    public void fillCompanyListTableByPartnerId(TableView tblCompany) {
        ResultSet rs = null;
        ObservableList<SupplierController.CompanyList> companyLists = FXCollections.observableArrayList();

        try {
            pst = conn.prepareStatement("SELECT com.id,com.name,company_list.partnership_id FROM company_list INNER JOIN company com ON company_list.company_id = com.id INNER  JOIN partnership p ON company_list.partnership_id = p.id WHERE  company_list.company_partner_id = ?");
            pst.setInt(1, companyPartner.getId());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                company.resetAll();
                companyPartner.resetAll();
                companyList.resetAll();
            }
            while (rs.next()) {

                int partnershipId = rs.getInt(3);
                JFXCheckBox cbSelect = new JFXCheckBox();
                if (partnershipId == 1) {
                    cbSelect.setSelected(true);
                } else if (partnershipId == 2) {
                    cbSelect.setSelected(false);
                }

                companyLists.add(new SupplierController.CompanyList(rs.getInt(1), rs.getString(2), cbSelect));
            }
            tblCompany.setItems(companyLists);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get Company Details using Company Name.
     * Search
     */
    public void getPartnerBySupplierId() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT partner_id FROM supplier  WHERE  id = ?");
            pst.setInt(1, supplier.getId());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                supplier.resetAll();
            }
            while (rs.next()) {
                companyPartner.setId(rs.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Check Company is already in the company list
     * if already return true.else false
     */
    public boolean checkCompanyAlreadyCompanyList(int partnerId, int companyId) {
        boolean already = false;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM company_list WHERE company_partner_id = ? AND company_id = ?");
            pst.setInt(1, partnerId);
            pst.setInt(2, companyId);
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                //company.resetAll();
                //companyPartner.resetAll();
                already = false;
            }
            if (rs.next()) {
                already = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
        return already;
    }

    public boolean checkAlreadyUser(String user) {
        ResultSet rs = null;
        boolean already = false;
        try {
            pst = conn.prepareStatement("SELECT * FROM user WHERE user_name = ?");
            pst.setString(1, user);
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                already = false;
            }
            if (rs.next()) {
                already = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
        return already;
    }

    public void fillEmployeeTable(TableView tblEmployee) {
        ResultSet rs = null;
        ObservableList<EmployeeController.EmployeeList> employeeList = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement("SELECT employee.id,employee.fname,employee.mname,employee.lname,employee.dob,employee.nic,employee.join_date,ad_status.status FROM employee INNER JOIN ad_status ON employee.ad_status_id = ad_status.id");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {
                //userType.resetAll();
            }
            while (rs.next()) {
                String name = rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4);
                employeeList.add(new EmployeeController.EmployeeList(rs.getInt(1), name, rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
            }
            tblEmployee.setItems(employeeList);
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    public void getEmployeeByEmployeeId() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT employee.id,employee.fname,employee.mname,employee.lname,employee.dob,employee.nic,employee.join_date,address.id,address.number,address.line_01,address.line_02,address.city,address.country,address.postal_code,contact.id,contact.mobile,contact.land,contact.fax,contact.email,contact.web,ad_status.id,ad_status.status FROM employee INNER JOIN address ON employee.address_id = address.id INNER JOIN contact ON employee.contact_id = contact.id INNER JOIN ad_status ON employee.ad_status_id = ad_status.id WHERE employee.id = ?");
            pst.setInt(1, employee.getId());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                employee.resetAll();
            }
            while (rs.next()) {
                employee.setId(rs.getInt(1));
                employee.setFirstName(rs.getString(2));
                employee.setMiddleName(rs.getString(3));
                employee.setLastName(rs.getString(4));
                employee.setDob(rs.getString(5));
                employee.setNic(rs.getString(6));
                employee.setJoinDate(rs.getString(7));

                address.setId(rs.getInt(8));
                address.setNumber(rs.getString(9));
                address.setLine01(rs.getString(10));
                address.setLine02(rs.getString(11));
                address.setCity(rs.getString(12));
                address.setCountry(rs.getString(13));
                address.setPostalCode(rs.getString(14));

                contact.setId(rs.getInt(15));
                contact.setMobile(rs.getString(16));
                contact.setLand(rs.getString(17));
                contact.setFax(rs.getString(18));
                contact.setEmail(rs.getString(19));
                contact.setWeb(rs.getString(20));

                adStatus.setId(rs.getInt(21));
                adStatus.setStatus(rs.getString(22));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Check Category is already
     * if already return true.else false
     */
    public boolean checkCategoryAlready(String name) {
        boolean already = false;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM category WHERE name = ?");
            pst.setString(1, name);
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                company.resetAll();
                already = false;
            }
            if (rs.next()) {
                already = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
        return already;
    }

    /**
     * Fill category combo using category table
     */
    public void fillCategoryCombo(JFXComboBox cmbCategory) {
        ResultSet rs = null;
        cmbCategory.getItems().clear();
        try {
            pst = conn.prepareStatement("SELECT name FROM category");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {
                category.resetAll();
            }
            while (rs.next()) {
                cmbCategory.getItems().add(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);

            }
        }
    }

    public void filterEmployeeTableByNic(TableView tblEmployee) {
        ResultSet rs = null;
        ObservableList<EmployeeController.EmployeeList> employeeLists = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement("SELECT employee.id,employee.fname,employee.mname,employee.lname,employee.dob,employee.nic,employee.join_date,address.id,address.number,address.line_01,address.line_02,address.city,address.country,address.postal_code,contact.id,contact.mobile,contact.land,contact.fax,contact.email,contact.web,ad_status.id,ad_status.status FROM employee INNER JOIN address ON employee.address_id = address.id INNER JOIN contact ON employee.contact_id = contact.id INNER JOIN ad_status ON employee.ad_status_id = ad_status.id WHERE employee.nic LIKE ?");
            pst.setString(1, employee.getNic() + "%");
            rs = pst.executeQuery();


            if (!rs.isBeforeFirst()) {
                employee.resetAll();
            }
            while (rs.next()) {
                String name = rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4);
                employeeLists.add(new EmployeeController.EmployeeList(rs.getInt(1), name, rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(22)));
            }
            tblEmployee.setItems(employeeLists);
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    /**
     * Get Category Details using Category Name.
     * Search
     */
    public void getCategoryByName() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM category  WHERE  name = ?");
            pst.setString(1, category.getName());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                company.resetAll();
            }
            while (rs.next()) {
                category.setId(rs.getInt(1));
                category.setName(rs.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    public void filterEmployeeTableByName(TableView tblEmployee) {
        ResultSet rs = null;
        ObservableList<EmployeeController.EmployeeList> employeeLists = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement("SELECT employee.id,employee.fname,employee.mname,employee.lname,employee.dob,employee.nic,employee.join_date,address.id,address.number,address.line_01,address.line_02,address.city,address.country,address.postal_code,contact.id,contact.mobile,contact.land,contact.fax,contact.email,contact.web,ad_status.id,ad_status.status FROM employee INNER JOIN address ON employee.address_id = address.id INNER JOIN contact ON employee.contact_id = contact.id INNER JOIN ad_status ON employee.ad_status_id = ad_status.id WHERE employee.fname LIKE ?");
            pst.setString(1, employee.getNic() + "%");
            rs = pst.executeQuery();


            if (!rs.isBeforeFirst()) {
                employee.resetAll();
            }
            while (rs.next()) {
                String name = rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4);
                employeeLists.add(new EmployeeController.EmployeeList(rs.getInt(1), name, rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(22)));
            }
            tblEmployee.setItems(employeeLists);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

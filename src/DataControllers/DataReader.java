package DataControllers;

import Controllers.*;
import DB_Conn.ConnConfig;
import DB_Conn.ConnectDB;
import Modules.*;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
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
    Unit unit;
    CustomerType customerType;
    Customer customer;
    Product product;
    SupplierPartner supplierPartner;
    PaymentType paymentType;

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
                unit = ObjectGenerator.getUnit();
                customerType = ObjectGenerator.getCustomerType();
                customer = ObjectGenerator.getCustomer();
                product = ObjectGenerator.getProduct();
                supplierPartner = ObjectGenerator.getSupplierPartner();
                paymentType = ObjectGenerator.getPaymentType();
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

    /**
     * Check Unit is already
     * if already return true.else false
     */
    public boolean checkUnitAlready(String unit) {
        boolean already = false;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM unit WHERE unit = ?");
            pst.setString(1, unit);
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
     * Fill Unit combo using Unit table
     */
    public void fillUnitCombo(JFXComboBox cmbUnit) {
        ResultSet rs = null;
        cmbUnit.getItems().clear();
        try {
            pst = conn.prepareStatement("SELECT unit FROM unit");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {
                unit.resetAll();
            }
            while (rs.next()) {
                cmbUnit.getItems().add(rs.getString(1));
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

    /**
     * Get Unit Details using Category Name.
     * Search
     */
    public void getUnitByUnit() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM unit  WHERE  unit = ?");
            pst.setString(1, unit.getUnit());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                unit.resetAll();
            }
            while (rs.next()) {
                unit.setId(rs.getInt(1));
                unit.setUnit(rs.getString(2));
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
     * Fill Supplier combo using Unit table
     */
    public void fillSupplierCombo(JFXComboBox cmbSupplier) {
        ResultSet rs = null;
        cmbSupplier.getItems().clear();
        try {
            pst = conn.prepareStatement("SELECT namel FROM supplier");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {
                supplier.resetAll();
            }
            while (rs.next()) {
                cmbSupplier.getItems().add(rs.getString(1));
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

    /**
     * Get Supplier Details using Supplier Name.
     * Search
     */
    public void getSupplierByName() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT supplier.id,supplier.namel,supplier.join_date,a.id,a.number,a.line_01,a.line_02,a.city,a.country,a.postal_code,ct.id,ct.mobile,ct.land,ct.fax,ct.email,ct.web,st.id,st.type,ad.id,ad.status,supplier.partner_id FROM supplier INNER JOIN address a ON supplier.address_id = a.id INNER JOIN contact ct ON supplier.contact_id = ct.id INNER JOIN ad_status ad ON supplier.ad_status_id = ad.id INNER JOIN supplier_type st ON supplier.type_id = st.id WHERE  supplier.namel = ?");
            pst.setString(1, supplier.getName());
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

    public void fillCustomerTypeCombo(JFXComboBox cmbCustomerType) {
        ResultSet rs = null;
        cmbCustomerType.getItems().clear();
        try {
            pst = conn.prepareStatement("SELECT type FROM  customer_type");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {
                customerType.resetAll();
            }
            while (rs.next()) {
                cmbCustomerType.getItems().add(rs.getString(1));
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

    public void getCustomerTypeByType() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM customer_type WHERE type = ?");
            pst.setString(1, customerType.getType());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                customerType.resetAll();
            }
            while (rs.next()) {
                customerType.setId(rs.getInt(1));
                customerType.setType(rs.getString(2));
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

    public void fillCustomerTable(TableView tblCustomer) {
        ResultSet rs = null;
        ObservableList<CustomerController.CustomerList> customerLists = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement("SELECT customer.id,customer.fname,customer.mname,customer.lname,customer.nic,customer.join_date,customer_type.type,ad_status.status FROM customer INNER JOIN customer_type ON customer.customer_type_id=customer_type.id INNER JOIN ad_status ON customer.ad_status_id = ad_status.id");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {
                //userType.resetAll();
            }
            while (rs.next()) {
                String name = rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4);
                customerLists.add(new CustomerController.CustomerList(rs.getInt(1), name, rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
            }
            tblCustomer.setItems(customerLists);
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
     * Filling the Product table using Database product table support status,type
     */
    public void fillProductTable(TableView tblProduct) {
        ResultSet rs = null;
        ObservableList<ProductController.ProductList> productList = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement("SELECT product.code,product.name,ct.name,u.unit,ad.status FROM product INNER JOIN category ct ON product.category_id = ct.id INNER JOIN unit u ON product.unit_id = u.id INNER JOIN ad_status ad ON product.ad_status_id = ad.id");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {
                //userType.resetAll();
            }
            while (rs.next()) {
                productList.add(new ProductController.ProductList(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
            tblProduct.setItems(productList);
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
     * Filling the Product table using Database product table support status,type
     */
    public void fillterProductTableByName(TableView tblProduct) {
        ResultSet rs = null;
        ObservableList<ProductController.ProductList> productList = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement("SELECT product.code,product.name,ct.name,u.unit,ad.status FROM product INNER JOIN category ct ON product.category_id = ct.id INNER JOIN unit u ON product.unit_id = u.id INNER JOIN ad_status ad ON product.ad_status_id = ad.id WHERE product.name LIKE ?");
            pst.setString(1, product.getName() + "%");

            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {
                product.resetAll();
            }
            while (rs.next()) {
                productList.add(new ProductController.ProductList(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
            tblProduct.setItems(productList);
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
     * Filling the Product table using Database product table support status,type
     */
    public void fillterProductTableByCode(TableView tblProduct) {
        ResultSet rs = null;
        ObservableList<ProductController.ProductList> productList = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement("SELECT product.code,product.name,ct.name,u.unit,ad.status FROM product INNER JOIN category ct ON product.category_id = ct.id INNER JOIN unit u ON product.unit_id = u.id INNER JOIN ad_status ad ON product.ad_status_id = ad.id WHERE product.code LIKE ?");
            pst.setString(1, product.getCode() + "%");

            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {
                product.resetAll();
            }
            while (rs.next()) {
                productList.add(new ProductController.ProductList(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
            tblProduct.setItems(productList);
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
     * Filling the Product table using Database product table support status,type
     */
    public void fillterProductTableByCategory(TableView tblProduct) {
        ResultSet rs = null;
        ObservableList<ProductController.ProductList> productList = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement("SELECT product.code,product.name,ct.name,u.unit,ad.status FROM product INNER JOIN category ct ON product.category_id = ct.id INNER JOIN unit u ON product.unit_id = u.id INNER JOIN ad_status ad ON product.ad_status_id = ad.id WHERE ct.name = ?");
            pst.setString(1, category.getName());

            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {
                category.resetAll();
            }
            while (rs.next()) {
                productList.add(new ProductController.ProductList(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
            tblProduct.setItems(productList);
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
     * Get Product Details using Product Code.
     * Search
     */
    public void getProductByCode() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT product.code,product.bar_code,product.name,product.refilling_qty,ct.id,ct.name,u.id,u.unit,ad.id,ad.status,com.id,com.name,partner.id,partner.partner FROM product INNER JOIN category ct ON product.category_id = ct.id INNER JOIN unit u ON product.unit_id = u.id INNER JOIN ad_status ad ON product.ad_status_id = ad.id INNER JOIN company com ON product.company_id = com.id INNER JOIN supplier_partner partner ON product.supplier_partner_id = partner.id WHERE product.code = ?");
            pst.setString(1, product.getCode());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                product.resetAll();
                category.resetAll();
                unit.resetAll();
                adStatus.resetAll();
                company.resetAll();
                supplierPartner.resetAll();
            }
            if (rs.next()) {
                product.setCode(rs.getString(1));
                product.setBarCode(rs.getString(2));
                product.setName(rs.getString(3));
                product.setRefillingQty(rs.getDouble(4));

                category.setId(rs.getInt(5));
                category.setName(rs.getString(6));

                unit.setId(rs.getInt(7));
                unit.setUnit(rs.getString(8));

                adStatus.setId(rs.getInt(9));
                adStatus.setStatus(rs.getString(10));

                company.setId(rs.getInt(11));
                company.setName(rs.getString(12));

                supplierPartner.setId(rs.getInt(13));
                supplierPartner.setPartner(rs.getString(14));
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
     * Get Product Details using Product BarCode.
     * Search
     */
    public void getProductByBarCode() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT product.code,product.bar_code,product.name,product.refilling_qty,ct.id,ct.name,u.id,u.unit,ad.id,ad.status,com.id,com.name,partner.id,partner.partner FROM product INNER JOIN category ct ON product.category_id = ct.id INNER JOIN unit u ON product.unit_id = u.id INNER JOIN ad_status ad ON product.ad_status_id = ad.id INNER JOIN company com ON product.company_id = com.id INNER JOIN supplier_partner partner ON product.supplier_partner_id = partner.id WHERE product.bar_code = ?");
            pst.setString(1, product.getBarCode());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                product.resetAll();
                category.resetAll();
                unit.resetAll();
                adStatus.resetAll();
                company.resetAll();
                supplierPartner.resetAll();
            }
            if (rs.next()) {
                product.setCode(rs.getString(1));
                product.setBarCode(rs.getString(2));
                product.setName(rs.getString(3));
                product.setRefillingQty(rs.getDouble(4));

                category.setId(rs.getInt(5));
                category.setName(rs.getString(6));

                unit.setId(rs.getInt(7));
                unit.setUnit(rs.getString(8));

                adStatus.setId(rs.getInt(9));
                adStatus.setStatus(rs.getString(10));

                company.setId(rs.getInt(11));
                company.setName(rs.getString(12));

                supplierPartner.setId(rs.getInt(13));
                supplierPartner.setPartner(rs.getString(14));
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
     * Get Product Details using Product BarCode.
     * Search
     */
    public void getProductByName() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT product.code,product.bar_code,product.name,product.refilling_qty,ct.id,ct.name,u.id,u.unit,ad.id,ad.status,com.id,com.name,partner.id,partner.partner FROM product INNER JOIN category ct ON product.category_id = ct.id INNER JOIN unit u ON product.unit_id = u.id INNER JOIN ad_status ad ON product.ad_status_id = ad.id INNER JOIN company com ON product.company_id = com.id INNER JOIN supplier_partner partner ON product.supplier_partner_id = partner.id WHERE product.name = ?");
            pst.setString(1, product.getName());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                product.resetAll();
                category.resetAll();
                unit.resetAll();
                adStatus.resetAll();
                company.resetAll();
                supplierPartner.resetAll();
            }
            if (rs.next()) {
                product.setCode(rs.getString(1));
                product.setBarCode(rs.getString(2));
                product.setName(rs.getString(3));
                product.setRefillingQty(rs.getDouble(4));

                category.setId(rs.getInt(5));
                category.setName(rs.getString(6));

                unit.setId(rs.getInt(7));
                unit.setUnit(rs.getString(8));

                adStatus.setId(rs.getInt(9));
                adStatus.setStatus(rs.getString(10));

                company.setId(rs.getInt(11));
                company.setName(rs.getString(12));

                supplierPartner.setId(rs.getInt(13));
                supplierPartner.setPartner(rs.getString(14));
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

    public void getCustomerByCustomerId() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT customer.id,customer.fname,customer.mname,customer.lname,customer.nic,customer.join_date,customer_type.id,customer_type.type, address.id,address.number,address.line_01,address.line_02,address.city,address.country,address.postal_code,contact.id,contact.mobile,contact.land,contact.fax,contact.email,contact.web,ad_status.id,ad_status.status FROM customer INNER JOIN customer_type ON customer.customer_type_id = customer_type.id INNER JOIN address ON customer.address_id = address.id INNER JOIN contact ON customer.contact_id = contact.id INNER JOIN ad_status ON customer.ad_status_id = ad_status.id WHERE customer.id = ?");
            pst.setInt(1, customer.getId());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                customer.resetAll();
            }
            while (rs.next()) {
                customer.setId(rs.getInt(1));
                customer.setFirstName(rs.getString(2));
                customer.setMiddleName(rs.getString(3));
                customer.setLastName(rs.getString(4));
                customer.setNic(rs.getString(5));
                customer.setJoinDate(rs.getString(6));

                customerType.setId(rs.getInt(7));
                customerType.setType(rs.getString(8));

                address.setId(rs.getInt(9));
                address.setNumber(rs.getString(10));
                address.setLine01(rs.getString(11));
                address.setLine02(rs.getString(12));
                address.setCity(rs.getString(13));
                address.setCountry(rs.getString(14));
                address.setPostalCode(rs.getString(15));

                contact.setId(rs.getInt(16));
                contact.setMobile(rs.getString(17));
                contact.setLand(rs.getString(18));
                contact.setFax(rs.getString(19));
                contact.setEmail(rs.getString(20));
                contact.setWeb(rs.getString(21));

                adStatus.setId(rs.getInt(22));
                adStatus.setStatus(rs.getString(23));
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
    public void fillSupplierListTableByPartnerId(TableView tblSupplier) {
        ResultSet rs = null;
        ObservableList<ProductController.SupplierList> supplierLists = FXCollections.observableArrayList();

        try {
            pst = conn.prepareStatement("SELECT sup.id,sup.namel,p.id FROM supplier_list INNER JOIN supplier sup ON supplier_list.supplier_id = sup.id INNER JOIN partnership p ON supplier_list.partnership_id = p.id WHERE supplier_list.supplier_partner_id = ?");
            pst.setInt(1, supplierPartner.getId());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                supplier.resetAll();
                supplierPartner.resetAll();
            }
            while (rs.next()) {

                int partnershipId = rs.getInt(3);
                JFXCheckBox cbSelect = new JFXCheckBox();
                if (partnershipId == 1) {
                    cbSelect.setSelected(true);
                } else if (partnershipId == 2) {
                    cbSelect.setSelected(false);
                }

                supplierLists.add(new ProductController.SupplierList(rs.getInt(1), rs.getString(2), cbSelect));
            }
            tblSupplier.setItems(supplierLists);
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
     * Get Supplier partner Details using Product Code.
     * Search
     */
    public void getPartnerByCode() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT supplier_partner_id FROM product  WHERE  code = ?");
            pst.setString(1, product.getCode());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                supplier.resetAll();
                supplierPartner.resetAll();
            }
            while (rs.next()) {
                supplierPartner.setId(rs.getInt(1));
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
     * Check Supplier is already in the supplier list
     * if already return true.else false
     */
    public boolean checkSupplierAlreadySupplierList(int partnerId, int supplierId) {
        boolean already = false;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM supplier_list WHERE supplier_partner_id = ? AND supplier_id = ?");
            pst.setInt(1, partnerId);
            pst.setInt(2, supplierId);
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

    public void filterCustomerTableByNic(TableView tblCustomer) {
        ResultSet rs = null;
        ObservableList<CustomerController.CustomerList> customerLists = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement("SELECT customer.id,customer.fname,customer.mname,customer.lname,customer.nic,customer.join_date,ad_status.id,ad_status.status,customer_type.id,customer_type.type FROM customer INNER JOIN ad_status ON customer.ad_status_id = ad_status.id INNER JOIN customer_type ON customer.customer_type_id = customer_type.id  WHERE customer.nic LIKE ?");
            pst.setString(1, customer.getNic() + "%");
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                customer.resetAll();
            }
            while (rs.next()) {
                String name = rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4);
                customerLists.add(new CustomerController.CustomerList(rs.getInt(1), name, rs.getString(5), rs.getString(6), rs.getString(8), rs.getString(10)));
            }
            tblCustomer.setItems(customerLists);
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

    public void filterCustomerTableByName(TableView tblCustomer) {
        ResultSet rs = null;
        ObservableList<CustomerController.CustomerList> customerLists = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement("SELECT customer.id,customer.fname,customer.mname,customer.lname,customer.nic,customer.join_date,ad_status.id,ad_status.status,customer_type.id,customer_type.type FROM customer INNER JOIN ad_status ON customer.ad_status_id = ad_status.id INNER JOIN customer_type ON customer.customer_type_id = customer_type.id  WHERE customer.fname LIKE ?");
            pst.setString(1, customer.getFirstName() + "%");
            rs = pst.executeQuery();


            if (!rs.isBeforeFirst()) {
                customer.resetAll();
            }
            while (rs.next()) {
                String name = rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4);
                customerLists.add(new CustomerController.CustomerList(rs.getInt(1), name, rs.getString(5), rs.getString(6), rs.getString(8), rs.getString(10)));
            }
            tblCustomer.setItems(customerLists);
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

    public void autoCompleteProductCode(JFXListView<String> lvCode, JFXTextField txtCode) {
        ResultSet rs = null;
        ObservableList<String> productCodeList = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement("SELECT code FROM product WHERE code LIKE ?");
            pst.setString(1, txtCode.getText() + "%");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {
                lvCode.setVisible(false);
                //alerts.getWarningAlert("Warning Alert", "Something went wrong..", "Apologetic Chief..!\nI have no suitable data in my database for your choice.\nPlease try another.");
            }
            while (rs.next()) {
                productCodeList.add(rs.getString(1));
            }
            lvCode.setItems(productCodeList);
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

    public void autoCompleteProductName(JFXListView<String> lvName, JFXTextField txtName) {
        ResultSet rs = null;
        ObservableList<String> productCodeList = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement("SELECT name FROM product WHERE product.name LIKE ?");
            pst.setString(1, txtName.getText() + "%");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {
                lvName.setVisible(false);
                //alerts.getWarningAlert("Warning Alert", "Something went wrong..", "Apologetic Chief..!\nI have no suitable data in my database for your choice.\nPlease try another.");
            }
            while (rs.next()) {
                productCodeList.add(rs.getString(1));
            }
            lvName.setItems(productCodeList);
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

    /**
     * Check Payment Type is already
     * if already return true.else false
     */
    public boolean checkPaymentTypeAlready(String type) {
        boolean already = false;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT type FROM payment_type WHERE type = ?");
            pst.setString(1, type);
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                paymentType.resetAll();
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

    public void fillPaymentTypeCombo(JFXComboBox cmbPaymentType) {
        ResultSet rs = null;
        cmbPaymentType.getItems().clear();
        try {
            pst = conn.prepareStatement("SELECT type FROM  payment_type");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {
                paymentType.resetAll();
            }
            while (rs.next()) {
                cmbPaymentType.getItems().add(rs.getString(1));
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

    /**
     * Get Payment type Details using Payment type.
     * Search
     */
    public void getPaymentTypeByType() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM payment_type WHERE type = ?");
            pst.setString(1, paymentType.getType());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                paymentType.resetAll();
            }
            while (rs.next()) {
                paymentType.setId(rs.getInt(1));
                paymentType.setType(rs.getString(2));
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

    public void autoCompleteSupplierName(JFXListView<String> lvSupplierName, JFXTextField txtSupplierName) {
        ResultSet rs = null;
        ObservableList<String> productCodeList = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement("SELECT namel FROM supplier WHERE namel LIKE ?");
            pst.setString(1, txtSupplierName.getText() + "%");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {
                lvSupplierName.setVisible(false);
                //alerts.getWarningAlert("Warning Alert", "Something went wrong..", "Apologetic Chief..!\nI have no suitable data in my database for your choice.\nPlease try another.");
            }
            while (rs.next()) {
                productCodeList.add(rs.getString(1));
            }
            lvSupplierName.setItems(productCodeList);
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

    /**
     * Filling the Stock View table using Database product,stock,category
     */
    public void fillStockViewTable(TableView tblProduct) {
        ResultSet rs = null;
        ObservableList<StockView.Products> productList = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement("SELECT product.code,product.name,ct.name,st.purchasing_price,st.sale_price,SUM(st.quantity) FROM product INNER JOIN category ct ON product.category_id = ct.id LEFT OUTER JOIN stock st ON product.code = st.product_code GROUP BY product.code,st.purchasing_price,st.sale_price");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {
                //userType.resetAll();
            }
            while (rs.next()) {
                productList.add(new StockView.Products(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5), rs.getDouble(6)));
            }
            tblProduct.setItems(productList);
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
     * Filling the Stock View table using Database product,stock,category
     */
    public void fillterStockViewTableByCode(TableView tblProduct) {
        ResultSet rs = null;
        ObservableList<StockView.Products> productList = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement("SELECT product.code,product.name,ct.name,st.purchasing_price,st.sale_price,SUM(st.quantity) FROM product INNER JOIN category ct ON product.category_id = ct.id LEFT OUTER JOIN stock st ON product.code = st.product_code WHERE product.code LIKE ? GROUP BY product.code,st.purchasing_price,st.sale_price");
            pst.setString(1,product.getCode()+"%");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {
                //userType.resetAll();
            }
            while (rs.next()) {
                productList.add(new StockView.Products(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5), rs.getDouble(6)));
            }
            tblProduct.setItems(productList);
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
}

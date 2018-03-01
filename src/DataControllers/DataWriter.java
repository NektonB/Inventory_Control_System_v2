package DataControllers;

import Controllers.Alerts;
import Controllers.ObjectGenerator;
import DB_Conn.ConnectDB;
import Modules.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataWriter {
    PreparedStatement pst;
    Connection conn;


    BackupData backupData;
    ConnectionInfo connectionInfo;
    Alerts alerts;
    User user;
    UserType userType;
    ADStatus adStatus;
    Address address;
    Contact contact;
    Company company;
    CompanyPartner companyPartner;
    Partnership partnership;
    Supplier supplier;
    SupplierType supplierType;
    Employee employee;
    Category category;


    /**
     * Load Supporting classes by thread
     * All supporting classes load in the thread
     */
    public DataWriter() {
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
                contact = ObjectGenerator.getContact();
                company = ObjectGenerator.getCompany();
                companyPartner = ObjectGenerator.getCompanyPartner();
                partnership = ObjectGenerator.getPartnership();
                supplier = ObjectGenerator.getSupplier();
                supplierType = ObjectGenerator.getSupplierType();
                employee = ObjectGenerator.getEmployee();
                category = ObjectGenerator.getCategory();

            });
            readyData.setName("Data Writer");
            readyData.start();
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public int saveUserType() {
        int saveDone = 0;
        try {
            pst = conn.prepareStatement("INSERT INTO user_type(type) VALUES (?)");
            pst.setString(1, userType.getType());

            saveDone = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                pst.close();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
        return saveDone;

    }

    public int saveUser() {
        int saveDone = 0;
        try {
            pst = conn.prepareStatement("INSERT INTO user(user_name, password, email, mobile, type_id, status_id) VALUES (?,?,?,?,?,?)");
            pst.setString(1, user.getUserName());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getEmail());
            pst.setString(4, user.getMobile());
            pst.setInt(5, userType.getId());
            pst.setInt(6, adStatus.getId());

            saveDone = pst.executeUpdate();
            // saveDone = Statement.RETURN_GENERATED_KEYS;
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                pst.close();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
        return saveDone;

    }

    public int updateUser() {
        int updateDone = 0;
        try {
            pst = conn.prepareStatement("UPDATE user SET user_name = ?, password = ?, email = ?, mobile = ?, type_id = ?, status_id = ? WHERE id = ?");
            pst.setString(1, user.getUserName());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getEmail());
            pst.setString(4, user.getMobile());
            pst.setInt(5, userType.getId());
            pst.setInt(6, adStatus.getId());
            pst.setInt(7, user.getId());

            updateDone = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                pst.close();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
        return updateDone;

    }

    /**
     * Save all input data in Address Module to database
     * Return 0 not save any record
     * Return grater than 0 data save ok...
     */
    public int saveAddress() {
        int saveDone = 0;
        try {
            pst = conn.prepareStatement("INSERT INTO address(number, line_01, line_02, city, country, postal_code) VALUES (?,?,?,?,?,?)");
            pst.setString(1, address.getNumber());
            pst.setString(2, address.getLine01());
            pst.setString(3, address.getLine02());
            pst.setString(4, address.getCity());
            pst.setString(5, address.getCountry());
            pst.setString(6, address.getPostalCode());

            saveDone = pst.executeUpdate();
            // saveDone = Statement.RETURN_GENERATED_KEYS;
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                pst.close();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
        return saveDone;

    }

    /**
     * Update all input data in Address Module to database
     * Return 0 not update any record
     * Return grater than 0 data update ok...
     */
    public int updateAddress() {
        int updateDone = 0;
        try {
            pst = conn.prepareStatement("UPDATE address SET number = ?, line_01 = ?, line_02 = ?, city = ?, country = ?, postal_code = ? WHERE id = ?");
            pst.setString(1, address.getNumber());
            pst.setString(2, address.getLine01());
            pst.setString(3, address.getLine02());
            pst.setString(4, address.getCity());
            pst.setString(5, address.getCountry());
            pst.setString(6, address.getPostalCode());
            pst.setInt(7, address.getId());

            updateDone = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                pst.close();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
        return updateDone;

    }

    /**
     * Save all input data in Address Module to database
     * Return 0 not save any record
     * Return grater than 0 data save ok...
     */
    public int saveContact() {
        int saveDone = 0;
        try {
            pst = conn.prepareStatement("INSERT INTO contact(mobile, land, fax, email, web) VALUES (?,?,?,?,?)");
            pst.setString(1, contact.getMobile());
            pst.setString(2, contact.getLand());
            pst.setString(3, contact.getFax());
            pst.setString(4, contact.getEmail());
            pst.setString(5, contact.getWeb());

            saveDone = pst.executeUpdate();
            // saveDone = Statement.RETURN_GENERATED_KEYS;
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                pst.close();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
        return saveDone;
    }

    /**
     * Save all input data in Address Module to database
     * Return 0 not save any record
     * Return grater than 0 data save ok...
     */
    public int updateContact() {
        int updateDone = 0;
        try {
            pst = conn.prepareStatement("UPDATE contact SET mobile = ?, land = ?, fax = ?, email = ?, web = ? WHERE id = ?");
            pst.setString(1, contact.getMobile());
            pst.setString(2, contact.getLand());
            pst.setString(3, contact.getFax());
            pst.setString(4, contact.getEmail());
            pst.setString(5, contact.getWeb());
            pst.setInt(6, contact.getId());

            updateDone = pst.executeUpdate();
            // saveDone = Statement.RETURN_GENERATED_KEYS;
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                pst.close();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
        return updateDone;
    }

    /**
     * Save all input data in Company Module to database
     * Return 0 not save any record
     * Return grater than 0 data save ok...
     */
    public int saveCompany() {
        int saveDone = 0;
        //ResultSet rs;
        try {
            pst = conn.prepareStatement("INSERT INTO company(name) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, company.getName());

            saveDone = pst.executeUpdate();
            //rs = pst.getGeneratedKeys();

            //if (rs.next()) {
            //System.out.println(rs.getInt(1));
            //}
            // saveDone = Statement.RETURN_GENERATED_KEYS;
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                pst.close();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
        return saveDone;

    }

    /**
     * Update all input data in Company Module to database
     * Return 0 not update any record
     * Return grater than 0 data update ok...
     */
    public int updateCompany() {
        int updateDone = 0;
        try {
            pst = conn.prepareStatement("UPDATE company SET  name = ? WHERE id = ?");
            pst.setString(1, company.getName());
            pst.setInt(2, company.getId());

            updateDone = pst.executeUpdate();
            // saveDone = Statement.RETURN_GENERATED_KEYS;
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                pst.close();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
        return updateDone;

    }

    /**
     * Save all input data in CompanyPartner Module to database
     * Return 0 not save any record
     * Return grater than 0 data save ok...
     */
    public int saveCompanyPartner() {
        ResultSet rs = null;
        int saveDone = 0;
        try {
            pst = conn.prepareStatement("INSERT INTO company_partner(partner) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, companyPartner.getPartner());

            saveDone = pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                companyPartner.setId(rs.getInt(1));
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
        return saveDone;
    }

    /**
     * Save all input data in CompanyPartner Module to database
     * Return 0 not save any record
     * Return grater than 0 data save ok...
     */
    public int saveCompanyList() {
        int saveDone = 0;
        try {
            pst = conn.prepareStatement("INSERT INTO company_list(company_partner_id, company_id, partnership_id) VALUES(?,?,?)");
            pst.setInt(1, companyPartner.getId());
            pst.setInt(2, company.getId());
            pst.setInt(3, partnership.getId());

            saveDone = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                pst.close();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
        return saveDone;
    }

    /**
     * Save all input data in Supplier Module to database
     * Return 0 not save any record
     * Return grater than 0 data save ok...
     */
    public int saveSupplier() {
        int saveDone = 0;
        try {
            pst = conn.prepareStatement("INSERT INTO supplier(namel, join_date, address_id, contact_id, ad_status_id, type_id, partner_id) VALUES(?,?,?,?,?,?,?)");
            pst.setString(1, supplier.getName());
            pst.setString(2, supplier.getJoinDate());
            pst.setInt(3, address.getId());
            pst.setInt(4, contact.getId());
            pst.setInt(5, adStatus.getId());
            pst.setInt(6, supplierType.getId());
            pst.setInt(7, companyPartner.getId());

            saveDone = pst.executeUpdate();
            // saveDone = Statement.RETURN_GENERATED_KEYS;
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                pst.close();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
        return saveDone;

    }

    /**
     * Update all input data in Supplier Module to database
     * Return 0 not update any record
     * Return grater than 0 data save ok...
     */
    public int updateSupplier() {
        int updateDone = 0;
        try {
            pst = conn.prepareStatement("UPDATE supplier SET namel = ? , join_date = ?, address_id = ?, contact_id = ?, ad_status_id = ?, type_id = ? WHERE id = ?");
            pst.setString(1, supplier.getName());
            pst.setString(2, supplier.getJoinDate());
            pst.setInt(3, address.getId());
            pst.setInt(4, contact.getId());
            pst.setInt(5, adStatus.getId());
            pst.setInt(6, supplierType.getId());
            pst.setInt(7, supplier.getId());

            updateDone = pst.executeUpdate();
            // saveDone = Statement.RETURN_GENERATED_KEYS;
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                pst.close();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
        return updateDone;

    }

    /**
     * Update company partnership status CompanyPartner table in the database
     * Return 0 not update any record
     * Return grater than 0 data update ok...
     */
    public int updateCompanyList() {
        int updateDone = 0;
        try {
            pst = conn.prepareStatement("UPDATE company_list SET partnership_id = ? WHERE company_partner_id = ? AND company_id = ?");
            pst.setInt(1, partnership.getId());
            pst.setInt(2, companyPartner.getId());
            pst.setInt(3, company.getId());

            updateDone = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                pst.close();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
        return updateDone;
    }

    public int saveEmployee() {
        int saveDone = 0;
        try {
            pst = conn.prepareStatement("INSERT INTO employee(fname, mname, lname, dob, nic, join_date, address_id, contact_id, ad_status_id) VALUES(?,?,?,?,?,?,?,?,?)");
            pst.setString(1, employee.getFirstName());
            pst.setString(2, employee.getMiddleName());
            pst.setString(3, employee.getLastName());
            pst.setString(4, employee.getDob());
            pst.setString(5, employee.getNic());
            pst.setString(6, employee.getJoinDate());
            pst.setInt(7, address.getId());
            pst.setInt(8, contact.getId());
            pst.setInt(9, adStatus.getId());

            saveDone = pst.executeUpdate();
            // saveDone = Statement.RETURN_GENERATED_KEYS;
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                pst.close();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
        return saveDone;

    }

    /**
     * Save all input data in Category Module to database
     * Return 0 not save any record
     * Return grater than 0 data save ok...
     */
    public int saveCategory() {
        int saveDone = 0;
        //ResultSet rs;
        try {
            pst = conn.prepareStatement("INSERT INTO category(name) VALUES(?)");
            pst.setString(1, category.getName());

            saveDone = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                pst.close();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
        return saveDone;
    }

    /**
     * Update all input data in Category Module to database
     * Return 0 not update any record
     * Return grater than 0 data save ok...
     */
    public int updateCategory() {
        int updateDone = 0;
        //ResultSet rs;
        try {
            pst = conn.prepareStatement("UPDATE category SET name = ? WHERE id = ?");
            pst.setString(1, category.getName());
            pst.setInt(2, category.getId());

            updateDone = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                pst.close();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
        return updateDone;

    }

    public int updateEmployee() {
        int saveDone = 0;
        try {
            pst = conn.prepareStatement("UPDATE employee SET fname = ?, mname = ?, lname = ?, dob = ?, nic = ?, join_date = ?, address_id = ?, contact_id = ?, ad_status_id = ? WHERE id = ?");
            pst.setString(1, employee.getFirstName());
            pst.setString(2, employee.getMiddleName());
            pst.setString(3, employee.getLastName());
            pst.setString(4, employee.getDob());
            pst.setString(5, employee.getNic());
            pst.setString(6, employee.getJoinDate());
            pst.setInt(7, address.getId());
            pst.setInt(8, contact.getId());
            pst.setInt(9, adStatus.getId());
            pst.setInt(10, employee.getId());

            saveDone = pst.executeUpdate();
            // saveDone = Statement.RETURN_GENERATED_KEYS;
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                pst.close();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
        return saveDone;

    }
}

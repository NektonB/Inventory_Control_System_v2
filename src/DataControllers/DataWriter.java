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
    Unit unit;
    SupplierPartner supplierPartner;
    Product product;
    Customer customer;
    CustomerType customerType;
    PaymentType paymentType;
    GRN grn;
    PaymentMethod paymentMethod;
    MethodList methodList;
    PayStatus payStatus;
    Approve approve;
    GrnItems grnItems;
    Invoice invoice;


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
                unit = ObjectGenerator.getUnit();
                supplierPartner = ObjectGenerator.getSupplierPartner();
                product = ObjectGenerator.getProduct();
                customer = ObjectGenerator.getCustomer();
                customerType = ObjectGenerator.getCustomerType();
                paymentType = ObjectGenerator.getPaymentType();
                grn = ObjectGenerator.getGrn();
                paymentMethod = ObjectGenerator.getPaymentMethod();
                methodList = ObjectGenerator.getMethodList();
                payStatus = ObjectGenerator.getPayStatus();
                approve = ObjectGenerator.getApprove();
                grnItems = ObjectGenerator.getGrnItems();
                invoice = ObjectGenerator.getInvoice();

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

    /**
     * Save all input data in Unit Module to database
     * Return 0 not save any record
     * Return grater than 0 data save ok...
     */
    public int saveUnit() {
        int saveDone = 0;
        //ResultSet rs;
        try {
            pst = conn.prepareStatement("INSERT INTO unit(unit) VALUES(?)");
            pst.setString(1, unit.getUnit());

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
    public int updateUnit() {
        int updateDone = 0;
        //ResultSet rs;
        try {
            pst = conn.prepareStatement("UPDATE unit SET unit = ? WHERE id = ?");
            pst.setString(1, unit.getUnit());
            pst.setInt(2, unit.getId());

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
     * Save all input data in CompanyPartner Module to database
     * Return 0 not save any record
     * Return grater than 0 data save ok...
     */
    public int saveSupplierPartner() {
        ResultSet rs = null;
        int saveDone = 0;
        try {
            pst = conn.prepareStatement("INSERT INTO supplier_partner(partner) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, supplierPartner.getPartner());

            saveDone = pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                supplierPartner.setId(rs.getInt(1));
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
    public int saveSupplierList() {
        int saveDone = 0;
        try {
            pst = conn.prepareStatement("INSERT INTO supplier_list(supplier_id,supplier_partner_id, partnership_id) VALUES(?,?,?)");
            pst.setInt(1, supplier.getId());
            pst.setInt(2, supplierPartner.getId());
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
     * Save all input data in Product Module to database
     * Return 0 not save any record
     * Return grater than 0 data save ok...
     */
    public int saveProduct() {
        int saveDone = 0;
        //ResultSet rs;
        try {
            pst = conn.prepareStatement("INSERT INTO product(code, bar_code, name, category_id, unit_id,refilling_qty, ad_status_id, company_id, supplier_partner_id) VALUES (?,?,?,?,?,?,?,?,?)");
            pst.setString(1, product.getCode());
            pst.setString(2, product.getBarCode());
            pst.setString(3, product.getName());
            pst.setInt(4, category.getId());
            pst.setInt(5, unit.getId());
            pst.setDouble(6, product.getRefillingQty());
            pst.setInt(7, adStatus.getId());
            pst.setInt(8, company.getId());
            pst.setInt(9, supplierPartner.getId());

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

    public int saveCustomer() {
        int saveDone = 0;
        try {
            pst = conn.prepareStatement("INSERT INTO  customer(fname, mname, lname, nic, join_date, customer_type_id, address_id, contact_id, ad_status_id) VALUES (?,?,?,?,?,?,?,?,?)");
            pst.setString(1, customer.getFirstName());
            pst.setString(2, customer.getMiddleName());
            pst.setString(3, customer.getLastName());
            pst.setString(4, customer.getNic());
            pst.setString(5, customer.getJoinDate());
            pst.setInt(6, customerType.getId());
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
     * Update company partnership status CompanyPartner table in the database
     * Return 0 not update any record
     * Return grater than 0 data update ok...
     */
    public int updateSupplierList() {
        int updateDone = 0;
        try {
            pst = conn.prepareStatement("UPDATE supplier_list SET partnership_id = ? WHERE supplier_partner_id = ? AND supplier_id = ?");
            pst.setInt(1, partnership.getId());
            pst.setInt(2, supplierPartner.getId());
            pst.setInt(3, supplier.getId());

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
     * Save all input data in Product Module to database
     * Return 0 not save any record
     * Return grater than 0 data save ok...
     */
    public int updateProduct(String newcode) {
        int saveDone = 0;
        //ResultSet rs;
        try {
            pst = conn.prepareStatement("UPDATE product SET code = ?, bar_code = ?, name = ?, category_id = ?, unit_id = ?,refilling_qty = ?, ad_status_id = ?, company_id = ?, supplier_partner_id = ? WHERE product.code = ?");
            pst.setString(1, newcode);
            pst.setString(2, product.getBarCode());
            pst.setString(3, product.getName());
            pst.setInt(4, category.getId());
            pst.setInt(5, unit.getId());
            pst.setDouble(6, product.getRefillingQty());
            pst.setInt(7, adStatus.getId());
            pst.setInt(8, company.getId());
            pst.setInt(9, supplierPartner.getId());
            pst.setString(10, product.getCode());

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

    public int updateCustomer() {
        int saveDone = 0;
        try {
            pst = conn.prepareStatement("UPDATE customer SET fname = ?, mname = ?, lname = ?, nic = ?, join_date = ?,customer_type_id=?, address_id = ?, contact_id = ?, ad_status_id = ? WHERE id = ?");
            pst.setString(1, customer.getFirstName());
            pst.setString(2, customer.getMiddleName());
            pst.setString(3, customer.getLastName());
            pst.setString(4, customer.getNic());
            pst.setString(5, customer.getJoinDate());

            pst.setInt(6, customerType.getId());

            pst.setInt(7, address.getId());
            pst.setInt(8, contact.getId());
            pst.setInt(9, adStatus.getId());
            pst.setInt(10, customer.getId());

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
     * Save all input data in Payment Type Module to database
     * Return 0 not save any record
     * Return grater than 0 data save ok...
     */
    public int savePaymentType() {
        int saveDone = 0;
        try {
            pst = conn.prepareStatement("INSERT INTO payment_type(type) VALUES (?)");
            pst.setString(1, paymentType.getType());

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
     * Save all input data in Payment Method Module to database
     * Return 0 not save any record
     * Return grater than 0 data save ok...
     */
    public int savePaymentMethod() {
        ResultSet rs = null;
        int saveDone = 0;
        try {
            pst = conn.prepareStatement("INSERT INTO pay_methode(owner_id) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, supplier.getId());

            saveDone = pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                paymentMethod.setId(rs.getInt(1));
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
     * Save all input data in Payment Method List Module to database
     * Return 0 not save any record
     * Return grater than 0 data save ok...
     */
    public int savePaymentMethodList() {
        int saveDone = 0;
        try {
            pst = conn.prepareStatement("INSERT INTO methode_list(pay_methode_id, payment_type_id, payed_value) VALUES(?,?,?)");
            pst.setInt(1, paymentMethod.getId());
            pst.setInt(2, paymentType.getId());
            pst.setDouble(3, methodList.getPayedValue());

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
     * Save all input data in GRN Module to database
     * Return 0 not save any record
     * Return grater than 0 data save ok...
     */
    public int saveGRN() {
        ResultSet rs = null;
        int saveDone = 0;
        try {
            pst = conn.prepareStatement("INSERT INTO grn(date, time, supplier_id, item_count, total_amount, gross_discount, manual_discount, net_discount, net_amount, pay_methode_id, payed_value, deu_amount, pay_status_id, approve_id, approved_user_id, user_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, grn.getDate());
            pst.setString(2, grn.getTime());
            pst.setInt(3, supplier.getId());
            pst.setDouble(4, grn.getItemCount());
            pst.setDouble(5, grn.getTotalAmount());
            pst.setDouble(6, grn.getGrossDiscount());
            pst.setDouble(7, grn.getManualDiscount());
            pst.setDouble(8, grn.getNetDiscount());
            pst.setDouble(9, grn.getNetAmount());
            pst.setInt(10, paymentMethod.getId());
            pst.setDouble(11, grn.getPayedValue());
            pst.setDouble(12, grn.getDeuAmount());
            pst.setInt(13, payStatus.getId());
            pst.setInt(14, approve.getId());
            pst.setInt(15, user.getId());
            pst.setInt(16, user.getId());

            saveDone = pst.executeUpdate();

            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                grn.setId(rs.getInt(1));
            }

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
     * Save all input data in GRN Module to database
     * Return 0 not save any record
     * Return grater than 0 data save ok...
     */
    public int saveGrnItems() {
        int saveDone = 0;
        try {
            pst = conn.prepareStatement("INSERT INTO grn_items(grn_id, product_code, purchasing_price, sale_price, quantity, total_amount, discount_value, discount_rate, net_amount, item_status) VALUES (?,?,?,?,?,?,?,?,?,?)");
            pst.setInt(1, grn.getId());
            pst.setString(2, product.getCode());
            pst.setDouble(3, grnItems.getPurchasePrice());
            pst.setDouble(4, grnItems.getSalePrice());
            pst.setDouble(5, grnItems.getQuantity());
            pst.setDouble(6, grnItems.getTotalAmount());
            pst.setDouble(7, grnItems.getDiscValue());
            pst.setDouble(8, grnItems.getDiscRate());
            pst.setDouble(9, grnItems.getNetAmount());
            pst.setString(10, grnItems.getItemStatus());

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
     * Save all input data in GRN Module to database
     * Return 0 not save any record
     * Return grater than 0 data save ok...
     */
    public int saveInvoice() {
        ResultSet rs = null;
        int saveDone = 0;
        try {
            pst = conn.prepareStatement("INSERT INTO invoice(date, time, customer_id, item_count, total_amount, gross_discount, manual_discount, net_discount, net_amount, pay_methode_id, payed_value, deu_amount, pay_status_id, approve_id, approved_user_id, user_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, invoice.getDate());
            pst.setString(2, invoice.getTime());
            pst.setInt(3, customer.getId());
            pst.setDouble(4, invoice.getItemCount());
            pst.setDouble(5, invoice.getTotalAmount());
            pst.setDouble(6, invoice.getGrossDiscount());
            pst.setDouble(7, invoice.getManualDiscount());
            pst.setDouble(8, invoice.getNetDiscount());
            pst.setDouble(9, invoice.getNetAmount());
            pst.setInt(10, paymentMethod.getId());
            pst.setDouble(11, invoice.getPayedValue());
            pst.setDouble(12, invoice.getDeuAmount());
            pst.setInt(13, payStatus.getId());
            pst.setInt(14, approve.getId());
            pst.setInt(15, user.getId());
            pst.setInt(16, user.getId());

            saveDone = pst.executeUpdate();

            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                grn.setId(rs.getInt(1));
            }

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
     * Save all input data in GRN Module to database
     * Return 0 not save any record
     * Return grater than 0 data save ok...
     */
    public int saveStock() {
        int saveDone = 0;
        try {
            pst = conn.prepareStatement("INSERT INTO stock(grn_id, product_code, purchasing_price, sale_price, quantity, total_amount, discount_value, discount_rate, net_amount, item_status) VALUES (?,?,?,?,?,?,?,?,?,?)");
            pst.setInt(1, grn.getId());
            pst.setString(2, product.getCode());
            pst.setDouble(3, grnItems.getPurchasePrice());
            pst.setDouble(4, grnItems.getSalePrice());
            pst.setDouble(5, grnItems.getQuantity());
            pst.setDouble(6, grnItems.getTotalAmount());
            pst.setDouble(7, grnItems.getDiscValue());
            pst.setDouble(8, grnItems.getDiscRate());
            pst.setDouble(9, grnItems.getNetAmount());
            pst.setString(10, grnItems.getItemStatus());

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

    public int decreaseProductQuantity(int stockId, double quantity) {
        int updateDone = 0;
        try {
            pst = conn.prepareStatement("UPDATE stock SET quantity = (quantity - ?) WHERE id = ?");
            pst.setDouble(1, quantity);
            pst.setInt(2, stockId);

            updateDone = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                pst.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return updateDone;
    }

    public int increaseProductQuantity(int stockId, double quantity) {
        int updateDone = 0;
        try {
            pst = conn.prepareStatement("UPDATE stock SET quantity = ? WHERE id = ?");
            pst.setDouble(1, quantity);
            pst.setInt(2, stockId);

            updateDone = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pst.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return updateDone;
    }
}

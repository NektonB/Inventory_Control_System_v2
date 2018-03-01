package Controllers;

import DataControllers.DataReader;
import DataControllers.DataWriter;
import Modules.*;
import com.jfoenix.controls.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SupplierController implements Initializable {

    @FXML
    private JFXTextField txtSupplierId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXDatePicker dpJoinDate;

    @FXML
    private JFXTextField txtAddressId;

    @FXML
    private JFXTextArea tfAddress;

    @FXML
    private JFXTextField txtContactId;

    @FXML
    private JFXTextArea txtContact;

    @FXML
    private JFXComboBox<String> cmbStatus;

    @FXML
    private JFXComboBox<String> cmbType;

    @FXML
    private JFXComboBox<String> cmbCompany;

    @FXML
    private JFXButton btnAddCompany;

    @FXML
    private TableView<CompanyList> tblCompany;

    @FXML
    private TableColumn<CompanyList, Integer> tcCompanyId;

    @FXML
    private TableColumn<CompanyList, String> tcCompanyName;

    @FXML
    private TableColumn<CompanyList, JFXCheckBox> tcPartner;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableView<SupplierList> tblSupplier;

    @FXML
    private TableColumn<SupplierList, Integer> tcId;

    @FXML
    private TableColumn<SupplierList, String> tcName;

    @FXML
    private TableColumn<SupplierList, String> tcAddress;

    @FXML
    private TableColumn<SupplierList, String> tcType;

    @FXML
    private TableColumn<SupplierList, String> tcStatus;

    @FXML
    private JFXTextField txtSearcSupplier;

    DataWriter dataWriter;
    DataReader dataReader;
    Alerts alerts;
    Address address;
    ComponentSwitcher switcher;
    DateFormatConverter dateFormatConverter;
    Company company;
    CompanyPartner companyPartner;
    Partnership partnership;
    Supplier supplier;
    SupplierType supplierType;
    Contact contact;
    ADStatus adStatus;
    Modules.CompanyList companyList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            /*
             * Load Supporting classes by thread
             * All supporting classes load in the thread
             * */
            Thread readyData = new Thread(() -> {
                alerts = ObjectGenerator.getAlerts();
                dataWriter = ObjectGenerator.getDataWriter();
                dataReader = ObjectGenerator.getDataReader();
                address = ObjectGenerator.getAddress();
                switcher = ObjectGenerator.getComponentSwitcher();
                company = ObjectGenerator.getCompany();
                dateFormatConverter = ObjectGenerator.getDateFormatConverter();
                companyPartner = ObjectGenerator.getCompanyPartner();
                partnership = ObjectGenerator.getPartnership();
                contact = ObjectGenerator.getContact();
                adStatus = ObjectGenerator.getAdStatus();
                supplier = ObjectGenerator.getSupplier();
                supplierType = ObjectGenerator.getSupplierType();
                companyList = ObjectGenerator.getCompanyList();

                dateFormatConverter.convert(dpJoinDate, "yyyy-MM-dd");
                dataReader.fillCompanyCombo(cmbCompany);
                dataReader.fillStatusCombo(cmbStatus);
                dataReader.fillSupplierTypeCombo(cmbType);
                dataReader.fillSupplierTable(tblSupplier);
            });
            readyData.setName("Supplier Controller");
            readyData.start();
            readyCompanyTable();
            readySupplierTable();
            cmbStatus.setValue("ACTIVE");
            cmbType.setValue("NORMAL");
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void resetText() {
        txtSupplierId.setText("");
        txtName.setText("");
        txtName.setText("");
        dpJoinDate.setValue(LocalDate.now());
        txtAddressId.setText("");
        tfAddress.setText("");
        txtContactId.setText("");
        txtContact.setText("");
        cmbStatus.setValue("ACTIVE");
        cmbType.setValue("NORMAL");
        tblCompany.getItems().clear();
        txtSupplierId.requestFocus();
    }

    public void readyCompanyTable() {
        tcCompanyId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcCompanyName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcPartner.setCellValueFactory(new PropertyValueFactory<>("cbSelect"));
    }

    public void readySupplierTable() {
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        tcType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    /**
     * Load Address Manager
     * Using Enter Key in txtAddress
     */
    public void loadAddressManager(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {

                /*
                 * Set txtAddressId and tfAddress to ComponentSwitcher
                 * */
                switcher.setTxt01(txtAddressId);
                switcher.setTxta01(tfAddress);

                Stage addressStage = new Stage();
                Parent frmUser = FXMLLoader.load(getClass().getClassLoader().getResource("Views/frmAddress.fxml"));
                addressStage.setTitle("Address Management");
                Scene scene = new Scene(frmUser);
                addressStage.setScene(scene);
                addressStage.initStyle(StageStyle.UTILITY);
                addressStage.setResizable(false);
                addressStage.initModality(Modality.APPLICATION_MODAL);
                addressStage.show();
                txtContactId.requestFocus();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    /**
     * Load Contact Manager
     */
    public void loadContactManager(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {

                switcher.setTxt01(txtContactId);
                switcher.setTxta01(txtContact);

                Stage addressStage = new Stage();
                Parent frmUser = FXMLLoader.load(getClass().getClassLoader().getResource("Views/frmContact.fxml"));
                addressStage.setTitle("Contact Management");
                Scene scene = new Scene(frmUser);
                addressStage.setScene(scene);
                addressStage.initStyle(StageStyle.UTILITY);
                addressStage.setResizable(false);
                addressStage.initModality(Modality.APPLICATION_MODAL);
                addressStage.show();
                cmbStatus.requestFocus();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    /**
     * Save Company Using Ctrl + S Key
     */
    public void saveCompanyKey(KeyEvent event) {
        if (event.isControlDown() & event.getCode().equals(KeyCode.S)) {
            try {
                boolean companyAlready = dataReader.checkCompanyAlready(cmbCompany.getValue());

                if (companyAlready == false) {
                    company.setName(cmbCompany.getValue());

                    int saveCompany = dataWriter.saveCompany();

                    if (saveCompany > 0) {
                        company.resetAll();
                        dataReader.fillCompanyCombo(cmbCompany);
                        alerts.getInformationAlert("Information", "Company Registration", "Congratulation Chief..!\nCompany registration successful");
                    }
                } else {
                    address.resetAll();
                    alerts.getWarningAlert("Information", "Company Registration", "Apologetic Chief..!\nCompany is you entered is already in my database.Please try another");
                }

            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    /**
     * Save Company Using Ctrl + U Key
     */
    public void updateCompanyKey(KeyEvent event) {
        if (event.isControlDown() & event.getCode().equals(KeyCode.U)) {
            try {
                company.setName(cmbCompany.getValue());

                int updateCompany = dataWriter.updateCompany();

                if (updateCompany > 0) {
                    company.resetAll();
                    dataReader.fillCompanyCombo(cmbCompany);
                    alerts.getInformationAlert("Information", "Company Modification", "Congratulation Chief..!\nCompany Modification successful");
                }
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    /**
     * Search Company details by Name when combo box hiding
     */
    public void searchCompanyDetailByName() {
        try {
            if (!cmbCompany.getValue().isEmpty()) {
                company.setName(cmbCompany.getValue());
                dataReader.getCompanyByName();
            }
        } catch (Exception e) {
            e.printStackTrace();
            //alerts.getErrorAlert(e);
        }
    }

    public void cmbCompanyOnKey(KeyEvent event) {
        saveCompanyKey(event);
        updateCompanyKey(event);
        addCompanyToList(event);
    }

    /**
     * Add company to company table using enter key
     */
    public void addCompanyToList(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                boolean already = false;
                ObservableList<? extends TableColumn<?, ?>> columns = tblCompany.getColumns();

                /*
                 * Search duplicates in the company table*/
                if (!tblCompany.getItems().isEmpty()) {
                    for (int i = 0; i < tblCompany.getItems().size(); ++i) {
                        if (columns.get(1).getCellObservableValue(i).getValue().equals(cmbCompany.getValue())) {
                            JFXCheckBox cb = (JFXCheckBox) columns.get(2).getCellObservableValue(i).getValue();
                            if (cb.isSelected()) {
                                System.out.println("Ok");
                            }
                            already = true;
                            if (already) {
                                break;
                            }
                        } else {
                            already = false;
                        }
                    }
                }

                if (!already) {
                    ObservableList<CompanyList> companyList = tblCompany.getItems();
                    JFXCheckBox cbSelect = new JFXCheckBox();
                    cbSelect.setSelected(true);
                    companyList.add(new CompanyList(company.getId(), company.getName(), cbSelect));
                    company.resetAll();
                } else {
                    alerts.getWarningNotify("Warning", "This company already in the table.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    /**
     * Remove Company from table using delete key
     */
    public void removeCompany(KeyEvent event) {
        if (event.getCode().equals(KeyCode.DELETE)) {
            try {
                tblCompany.getItems().remove(tblCompany.getSelectionModel().getSelectedItem());
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    /**
     * Save Supplier list table data to database.using loop
     */
    public int saveCompanyList() {
        int saveCompanyList = 0;
        try {
            if (!tblCompany.getItems().isEmpty()) {
                companyPartner.setPartner(txtName.getText());

                int saveCompanyPartner = dataWriter.saveCompanyPartner();

                if (saveCompanyPartner > 0) {

                    ObservableList<? extends TableColumn<?, ?>> columns = tblCompany.getColumns();

                    for (int i = 0; i < tblCompany.getItems().size(); ++i) {
                        company.setId(Integer.parseInt(columns.get(0).getCellObservableValue(i).getValue().toString()));
                        JFXCheckBox cb = (JFXCheckBox) columns.get(2).getCellObservableValue(i).getValue();
                        if (cb.isSelected()) {
                            partnership.setId(1);
                        } else {
                            partnership.setId(2);
                        }
                        saveCompanyList = dataWriter.saveCompanyList();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }

        return saveCompanyList;
    }

    /**
     * Save supplier after save the supplier list table success
     */
    public void saveSupplier() {
        try {

            int saveCompanyList = saveCompanyList();

            if (saveCompanyList > 0) {
                supplier.setName(txtName.getText());
                supplier.setJoinDate(dpJoinDate.getValue().toString());
                address.setId(Integer.parseInt(txtAddressId.getText()));

                contact.setId(Integer.parseInt(txtContactId.getText()));

                adStatus.setStatus(cmbStatus.getValue());
                dataReader.getStatusDetailsByStatus();

                supplierType.setType(cmbType.getValue());
                dataReader.getSupplierTypeDetailsByType();

                int saveSupplier = dataWriter.saveSupplier();
                if (saveSupplier > 0) {
                    company.resetAll();
                    companyPartner.resetAll();
                    address.resetAll();
                    contact.resetAll();
                    adStatus.resetAll();
                    supplierType.resetAll();
                    supplier.resetAll();
                    resetText();
                    //dataReader.fillCompanyCombo(cmbCompany);
                    dataReader.fillSupplierTable(tblSupplier);
                    alerts.getInformationAlert("Information", "Supplier Registration", "Congratulation Chief..!\nSupplier registration successful");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void saveSupplierKey(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            saveSupplier();
        }
    }

    public void filterSupplierTableByName(KeyEvent event) {
        try {
            supplier.setName(txtSearcSupplier.getText());
            dataReader.filterSupplierTableByName(tblSupplier);
            supplier.resetAll();
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void selectSupplierById() {
        try {
            if (!tblSupplier.getItems().isEmpty()) {
                SupplierList supplierList = tblSupplier.getSelectionModel().getSelectedItem();
                //if (supplierList.id.get() > 0) {
                supplier.setId(supplierList.id.get());

                dataReader.getSupplierById();

                txtSupplierId.setText(Integer.toString(supplier.getId()));
                txtName.setText(supplier.getName());
                dpJoinDate.setValue(LocalDate.parse(supplier.getJoinDate()));

                txtAddressId.setText(Integer.toString(address.getId()));
                String address01 = address.getNumber() + "" +
                        ",\n" + address.getLine01() + "" +
                        ",\n" + address.getLine02() + "" +
                        ",\n" + address.getCity() + "" +
                        ",\n" + address.getCountry() + "" +
                        ",\n" + address.getPostalCode() + ".";
                tfAddress.setText(address01);

                txtContactId.setText(Integer.toString(contact.getId()));
                String contact01 = "" +
                        "Mobile.............." + contact.getMobile() + "\n" +
                        "Land.................." + contact.getLand() + "\n" +
                        "Fax....................." + contact.getFax() + "\n" +
                        "Email................." + contact.getEmail() + "\n" +
                        "Web.................." + contact.getWeb();
                txtContact.setText(contact01);

                cmbStatus.setValue(adStatus.getStatus());
                cmbType.setValue(supplierType.getType());

                dataReader.fillCompanyListTableByPartnerId(tblCompany);

                supplier.resetAll();
                address.resetAll();
                contact.resetAll();
                supplierType.resetAll();
                adStatus.resetAll();
                companyPartner.resetAll();
                companyList.resetAll();
                company.resetAll();
                //}
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void selectSupplierByIdKey(KeyEvent event) {
        if (event.getCode().equals(KeyCode.UP) | event.getCode().equals(KeyCode.DOWN)) {
            selectSupplierById();
        } else if (event.getCode().equals(KeyCode.ENTER)) {
            txtSupplierId.requestFocus();
        }
    }

    public void gotoSupplierTable(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            tblSupplier.requestFocus();
        }
    }

    public int updateCompanyList() {

        supplier.setId(Integer.parseInt(txtSupplierId.getText()));
        dataReader.getPartnerBySupplierId();

        int updateCompanyList = 0;
        try {
            ObservableList<? extends TableColumn<?, ?>> columns = tblCompany.getColumns();
            for (int i = 0; i < tblCompany.getItems().size(); ++i) {
                boolean alreadyCompanyList = dataReader.checkCompanyAlreadyCompanyList(companyPartner.getId(), Integer.parseInt(columns.get(0).getCellObservableValue(i).getValue().toString()));
                if (alreadyCompanyList) {
                    company.setId(Integer.parseInt(columns.get(0).getCellObservableValue(i).getValue().toString()));
                    JFXCheckBox cb = (JFXCheckBox) columns.get(2).getCellObservableValue(i).getValue();
                    if (cb.isSelected()) {
                        partnership.setId(1);
                    } else {
                        partnership.setId(2);
                    }
                    updateCompanyList = dataWriter.updateCompanyList();
                } else {
                    company.setId(Integer.parseInt(columns.get(0).getCellObservableValue(i).getValue().toString()));
                    JFXCheckBox cb = (JFXCheckBox) columns.get(2).getCellObservableValue(i).getValue();
                    if (cb.isSelected()) {
                        partnership.setId(1);
                    } else {
                        partnership.setId(2);
                    }
                    updateCompanyList = dataWriter.saveCompanyList();
                }
            }
        } catch (Exception e) {
            updateCompanyList = 0;
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
        return updateCompanyList;
    }

    public void updateSupplier() {
        try {

            int updateCompanyList = updateCompanyList();
            if (updateCompanyList > 0) {
                supplier.setId(Integer.parseInt(txtSupplierId.getText()));
                supplier.setName(txtName.getText());
                supplier.setJoinDate(dpJoinDate.getValue().toString());
                address.setId(Integer.parseInt(txtAddressId.getText()));
                contact.setId(Integer.parseInt(txtContactId.getText()));

                adStatus.setStatus(cmbStatus.getValue());
                dataReader.getStatusDetailsByStatus();

                supplierType.setType(cmbType.getValue());
                dataReader.getSupplierTypeDetailsByType();

                int updateSupplier = dataWriter.updateSupplier();
                if (updateSupplier > 0) {
                    company.resetAll();
                    address.resetAll();
                    contact.resetAll();
                    adStatus.resetAll();
                    supplierType.resetAll();
                    supplier.resetAll();
                    companyPartner.resetAll();
                    companyList.resetAll();
                    resetText();
                    dataReader.fillSupplierTable(tblSupplier);
                    alerts.getInformationAlert("Information", "Supplier Modification", "Congratulation Chief..!\nSupplier modification successful");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSupplierKey(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            updateSupplier();
        }
    }

    public static class CompanyList {
        SimpleIntegerProperty id;
        SimpleStringProperty name;
        JFXCheckBox cbSelect;

        public CompanyList(int id, String name, JFXCheckBox cbSelect) {
            this.id = new SimpleIntegerProperty(id);
            this.name = new SimpleStringProperty(name);
            this.cbSelect = cbSelect;
        }

        public int getId() {
            return id.get();
        }

        public SimpleIntegerProperty idProperty() {
            return id;
        }

        public void setId(int id) {
            this.id.set(id);
        }

        public String getName() {
            return name.get();
        }

        public SimpleStringProperty nameProperty() {
            return name;
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public JFXCheckBox getCbSelect() {
            return cbSelect;
        }

        public void setCbSelect(JFXCheckBox cbSelect) {
            this.cbSelect = cbSelect;
        }
    }

    public static class SupplierList {

        SimpleIntegerProperty id;
        SimpleStringProperty name;
        SimpleStringProperty address;
        SimpleStringProperty type;
        SimpleStringProperty status;

        public SupplierList(int id, String name, String address, String type, String status) {
            this.id = new SimpleIntegerProperty(id);
            this.name = new SimpleStringProperty(name);
            this.address = new SimpleStringProperty(address);
            this.type = new SimpleStringProperty(type);
            this.status = new SimpleStringProperty(status);
        }

        public int getId() {
            return id.get();
        }

        public SimpleIntegerProperty idProperty() {
            return id;
        }

        public void setId(int id) {
            this.id.set(id);
        }

        public String getName() {
            return name.get();
        }

        public SimpleStringProperty nameProperty() {
            return name;
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public String getAddress() {
            return address.get();
        }

        public SimpleStringProperty addressProperty() {
            return address;
        }

        public void setAddress(String address) {
            this.address.set(address);
        }

        public String getType() {
            return type.get();
        }

        public SimpleStringProperty typeProperty() {
            return type;
        }

        public void setType(String type) {
            this.type.set(type);
        }

        public String getStatus() {
            return status.get();
        }

        public SimpleStringProperty statusProperty() {
            return status;
        }

        public void setStatus(String status) {
            this.status.set(status);
        }
    }
}

package Controllers;

import DataControllers.DataReader;
import DataControllers.DataWriter;
import Modules.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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
import java.util.ResourceBundle;

public class ProductController implements Initializable {

    @FXML
    private JFXTextField txtCode;

    @FXML
    private JFXTextField txtBarCode;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXComboBox<String> cmbCategory;

    @FXML
    private JFXComboBox<String> cmbUnit;

    @FXML
    private JFXComboBox<String> cmbCompany;

    @FXML
    private JFXComboBox<String> cmbStatus;

    @FXML
    private JFXTextField txtRefillQty;

    @FXML
    private JFXComboBox<String> cmbSupplier;

    @FXML
    private JFXButton btnAddSupplier;

    @FXML
    private TableView<SupplierList> tblSupplier;

    @FXML
    private TableColumn<SupplierList, Integer> tcSupplierId;

    @FXML
    private TableColumn<SupplierList, String> tcSupplierName;

    @FXML
    private TableColumn<SupplierList, JFXCheckBox> tcPartner;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableView<?> tblProducts;

    @FXML
    private TableColumn<?, ?> tcId;

    @FXML
    private TableColumn<?, ?> tcName;

    @FXML
    private TableColumn<?, ?> tcAddress;

    @FXML
    private TableColumn<?, ?> tcType;

    @FXML
    private TableColumn<?, ?> tcStatus;

    @FXML
    private JFXTextField txtSearcProducts;

    DataWriter dataWriter;
    DataReader dataReader;
    Alerts alerts;
    Address address;
    ComponentSwitcher switcher;
    DateFormatConverter dateFormatConverter;
    Company company;
    Category category;
    Unit unit;
    Supplier supplier;

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
                category = ObjectGenerator.getCategory();
                unit = ObjectGenerator.getUnit();
                supplier = ObjectGenerator.getSupplier();

                dataReader.fillCompanyCombo(cmbCompany);
                dataReader.fillStatusCombo(cmbStatus);
                dataReader.fillCategoryCombo(cmbCategory);
                dataReader.fillUnitCombo(cmbUnit);
                dataReader.fillSupplierCombo(cmbSupplier);
                //dataReader.fillSupplierTable(tblSupplier);
            });
            readyData.setName("Product Controller");
            readyData.start();
            cmbStatus.setValue("ACTIVE");
            readyCompanyTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readyCompanyTable() {
        tcSupplierId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcSupplierName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcPartner.setCellValueFactory(new PropertyValueFactory<>("cbSelect"));
    }

    /**
     * Save new category using Ctrl+S
     */
    public void saveCategory(KeyEvent event) {
        if (event.isControlDown() & event.getCode().equals(KeyCode.S)) {
            try {
                boolean categoryAlready = dataReader.checkCategoryAlready(cmbCategory.getValue());
                if (categoryAlready) {
                    category.resetAll();
                    alerts.getWarningAlert("Warning", "Data Duplication", "Sorry Chief..! Category is you entered.already in my database.Please try another..");
                } else {
                    category.setName(cmbCategory.getValue());
                    int saveCategory = dataWriter.saveCategory();
                    if (saveCategory > 0) {
                        category.resetAll();
                        dataReader.fillCategoryCombo(cmbCategory);
                        alerts.getInformationAlert("Information", "Category Save", "Congratulation Chief..!\nCategory save successful");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    /**
     * Search Category details using combo box on hiding
     */
    public void getCategoryByName() {
        try {
            category.setName(cmbCategory.getValue());
            dataReader.getCategoryByName();
            System.out.println(category.getId());
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    /**
     * Update already saved Category
     * Ctrl+U
     */
    public void updateCategory(KeyEvent event) {
        if (event.isControlDown() & event.getCode().equals(KeyCode.U)) {
            try {
                category.setName(cmbCategory.getValue());
                int updateCategory = dataWriter.updateCategory();
                if (updateCategory > 0) {
                    category.resetAll();
                    dataReader.fillCategoryCombo(cmbCategory);
                    alerts.getInformationAlert("Information", "Category Modification", "Congratulation Chief..!\nCategory modification successful");
                }
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    /**
     * */
    public void cmbCategoryKey(KeyEvent event) {
        saveCategory(event);
        updateCategory(event);
    }

    public void saveUnit(KeyEvent event) {
        if (event.isControlDown() & event.getCode().equals(KeyCode.S)) {
            try {
                boolean unitAlready = dataReader.checkUnitAlready(cmbUnit.getValue());
                if (unitAlready) {
                    unit.resetAll();
                    alerts.getWarningAlert("Warning", "Data Duplication", "Sorry Chief..! Unit is you entered.already in my database.Please try another..");
                } else {
                    unit.setUnit(cmbUnit.getValue());
                    int saveUnit = dataWriter.saveUnit();
                    if (saveUnit > 0) {
                        unit.resetAll();
                        dataReader.fillUnitCombo(cmbUnit);
                        alerts.getInformationAlert("Information", "Unit Save", "Congratulation Chief..!\nUnit save successful");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    public void getUnitByName() {
        try {
            unit.setUnit(cmbUnit.getValue());
            dataReader.getUnitByUnit();
            System.out.println(unit.getId());
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void updateUnit(KeyEvent event) {
        if (event.isControlDown() & event.getCode().equals(KeyCode.U)) {
            try {
                unit.setUnit(cmbUnit.getValue());
                int updateUnit = dataWriter.updateUnit();
                if (updateUnit > 0) {
                    unit.resetAll();
                    dataReader.fillUnitCombo(cmbUnit);
                    alerts.getInformationAlert("Information", "Unit Modification", "Congratulation Chief..!\nUnit modification successful");
                }
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    public void cmbUnitKey(KeyEvent event) {
        saveUnit(event);
        updateUnit(event);
    }

    public void saveCompany(KeyEvent event) {
        if (event.isControlDown() & event.getCode().equals(KeyCode.S)) {
            try {
                boolean companyAlready = dataReader.checkCompanyAlready(cmbCompany.getValue());
                if (companyAlready) {
                    company.resetAll();
                    alerts.getWarningAlert("Warning", "Data Duplication", "Sorry Chief..! Company is you entered.already in my database.Please try another..");
                } else {
                    company.setName(cmbCompany.getValue());
                    int saveCompany = dataWriter.saveCompany();
                    if (saveCompany > 0) {
                        company.resetAll();
                        dataReader.fillCompanyCombo(cmbCompany);
                        alerts.getInformationAlert("Information", "Company Save", "Congratulation Chief..!\nCompany save successful");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    public void getCompanyByName() {
        try {
            company.setName(cmbCompany.getValue());
            dataReader.getCompanyByName();
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void updateCompany(KeyEvent event) {
        if (event.isControlDown() & event.getCode().equals(KeyCode.U)) {
            try {
                company.setName(cmbCompany.getValue());
                int updateCompany = dataWriter.updateCompany();
                if (updateCompany > 0) {
                    company.resetAll();
                    dataReader.fillCompanyCombo(cmbCompany);
                    alerts.getInformationAlert("Information", "Company Modification", "Congratulation Chief..!\nCompany modification successful");
                }
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    public void companyComboKey(KeyEvent event) {
        saveCompany(event);
        updateCompany(event);
    }

    public void cmbSupplierKey(KeyEvent event) {
        try {
            if (event.isControlDown() & event.getCode().equals(KeyCode.S)) {
                Stage supplierStage = new Stage();
                Parent frmUser = FXMLLoader.load(getClass().getClassLoader().getResource("Views/frmSupplier.fxml"));
                supplierStage.setTitle("Supplier Management");
                Scene scene = new Scene(frmUser);
                supplierStage.setScene(scene);
                supplierStage.initStyle(StageStyle.UTILITY);
                supplierStage.setResizable(false);
                supplierStage.initModality(Modality.APPLICATION_MODAL);
                supplierStage.show();
            }
            addCompanyToList(event);
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    /**
     * Search Supplier details by Name when combo box hiding
     */
    public void searchCompanyDetailByName() {
        try {
            if (!cmbSupplier.getValue().isEmpty()) {
                supplier.setName(cmbSupplier.getValue());
                dataReader.getSupplierByName();
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    /**
     * Add company to company table using enter key
     */
    public void addCompanyToList(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                boolean already = false;
                ObservableList<? extends TableColumn<?, ?>> columns = tblSupplier.getColumns();

                /*
                 * Search duplicates in the company table*/
                if (!tblSupplier.getItems().isEmpty()) {
                    for (int i = 0; i < tblSupplier.getItems().size(); ++i) {
                        if (columns.get(1).getCellObservableValue(i).getValue().equals(cmbSupplier.getValue())) {
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
                    ObservableList<SupplierList> companyList = tblSupplier.getItems();
                    JFXCheckBox cbSelect = new JFXCheckBox();
                    cbSelect.setSelected(true);
                    companyList.add(new SupplierList(supplier.getId(), supplier.getName(), cbSelect));
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

    public static class SupplierList {
        SimpleIntegerProperty id;
        SimpleStringProperty name;
        JFXCheckBox cbSelect;

        public SupplierList(int id, String name, JFXCheckBox cbSelect) {
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
}

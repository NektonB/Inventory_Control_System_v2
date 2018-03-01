package Controllers;

import DataControllers.DataReader;
import DataControllers.DataWriter;
import Modules.Address;
import Modules.Category;
import Modules.Company;
import Modules.ComponentSwitcher;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
    private TableView<?> tblSupplier;

    @FXML
    private TableColumn<?, ?> tcCompanyId;

    @FXML
    private TableColumn<?, ?> tcCompanyName;

    @FXML
    private TableColumn<?, ?> tcPartner;

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

                dataReader.fillCompanyCombo(cmbCompany);
                dataReader.fillStatusCombo(cmbStatus);
                dataReader.fillCategoryCombo(cmbCategory);
                //dataReader.fillSupplierTable(tblSupplier);
            });
            readyData.setName("Product Controller");
            readyData.start();
            cmbStatus.setValue("ACTIVE");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    public void getCategoryByName() {
        try {
            category.setName(cmbCategory.getValue());
            dataReader.getCategoryByName();
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

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

    public void cmbCategoryKey(KeyEvent event) {
        saveCategory(event);
        updateCategory(event);
    }
}

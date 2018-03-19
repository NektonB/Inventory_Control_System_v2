package Controllers;

import DataControllers.DataReader;
import DataControllers.DataWriter;
import Modules.ADStatus;
import Modules.User;
import Modules.UserType;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    private JFXTextField txtUserId;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    private JFXTextField txtPassword;
    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXComboBox<String> cmbUserType;

    @FXML
    private JFXTextField txtMobile;

    @FXML
    private JFXComboBox<String> cmbUserStatus;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableView<UserList> tblUser;

    @FXML
    private TableColumn<UserList, Integer> tcUserId;

    @FXML
    private TableColumn<UserList, String> tcUserName;

    @FXML
    private TableColumn<UserList, String> tcEmail;

    @FXML
    private TableColumn<UserList, String> tcType;

    @FXML
    private TableColumn<UserList, String> tcStatus;

    DataWriter dataWriter;
    DataReader dataReader;
    Alerts alerts;
    User user;
    UserType userType;
    ADStatus adStatus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Thread readyData = new Thread(() -> {
                alerts = ObjectGenerator.getAlerts();
                dataWriter = ObjectGenerator.getDataWriter();
                dataReader = ObjectGenerator.getDataReader();
                user = ObjectGenerator.getUser();
                userType = ObjectGenerator.getUserType();
                adStatus = ObjectGenerator.getAdStatus();

                dataReader.fillUserTypeCombo(cmbUserType);
                dataReader.fillStatusCombo(cmbUserStatus);

                dataReader.fillUserTable(tblUser);
            });
            readyData.setName("User Controller");
            readyData.start();
            readyTable();
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void readyTable() {

        tcUserId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tcType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    public void resetText() {
        txtUserId.setText("");
        txtUserName.setText("");
        txtPassword.setText("");
        txtEmail.setText("");
        txtMobile.setText("");
        cmbUserType.setValue("ADMIN");
        cmbUserStatus.setValue("ACTIVE");
        txtUserName.requestFocus();
    }

    public void saveUserType(KeyEvent event) {
        try {
            if (event.isControlDown() && event.getCode().equals(KeyCode.S)) {
                if (!cmbUserType.getValue().toString().isEmpty()) {
                    boolean alreadyType = dataReader.checkAlreadyType(cmbUserType.getValue());

                    if (alreadyType) {
                        userType.resetAll();
                        alerts.getWarningAlert("Warning", "Data Duplication", "Sorry Chief..! Data is you entered.already in my database.Please try another..");
                    } else {
                        userType.setType(cmbUserType.getValue());

                        int saveUserType = dataWriter.saveUserType();
                        if (saveUserType > 0) {
                            userType.resetAll();
                            dataReader.fillUserTypeCombo(cmbUserType);
                            alerts.getInformationAlert("Information", "User Type Save", "Congratulation Chief..!\nUser type save successful");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void saveUser() {
        try {
            if (!txtUserName.getText().isEmpty() && !txtPassword.getText().isEmpty() && !cmbUserType.getValue().toString().isEmpty() && !cmbUserStatus.getValue().toString().isEmpty()) {
                boolean alreadyUser = dataReader.checkAlreadyUser(txtUserName.getText());
                if (alreadyUser) {
                    alerts.getWarningAlert("Warning", "User Registration", "Apologetic Chief..!\n This user already in my database.Please try another...!");
                } else {
                    user.setUserName(txtUserName.getText());
                    user.setPassword(txtPassword.getText());
                    user.setEmail(txtEmail.getText());
                    user.setMobile(txtMobile.getText());

                    userType.setType(cmbUserType.getValue());
                    dataReader.getUserTypeByType();

                    adStatus.setStatus(cmbUserStatus.getValue());
                    dataReader.getStatusDetailsByStatus();

                    int saveUser = dataWriter.saveUser();
                    if (saveUser > 0) {
                        //System.out.println(saveUser);
                        userType.resetAll();
                        adStatus.resetAll();
                        resetText();
                        dataReader.fillUserTable(tblUser);
                        alerts.getInformationAlert("Information", "User Registration", "Congratulation Chief..!\n User registration successful");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveUserKey(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            saveUser();
        }
    }

    public void updateUser() {
        try {
            if (!txtUserName.getText().isEmpty() && !txtPassword.getText().isEmpty()) {
                user.setId(Integer.parseInt(txtUserId.getText()));
                user.setUserName(txtUserName.getText());
                user.setPassword(txtPassword.getText());
                user.setEmail(txtEmail.getText());
                user.setMobile(txtMobile.getText());

                userType.setType(cmbUserType.getValue());
                dataReader.getUserTypeByType();

                adStatus.setStatus(cmbUserStatus.getValue());
                dataReader.getStatusDetailsByStatus();

                int updateUser = dataWriter.updateUser();
                if (updateUser > 0) {
                    userType.resetAll();
                    adStatus.resetAll();
                    resetText();
                    dataReader.fillUserTable(tblUser);
                    alerts.getInformationAlert("Information", "User Modification", "Congratulation Chief..!\n User modification successful");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUserKey(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            updateUser();
        }
    }

    public void getSelectedUser() {
        try {
            if (!tblUser.getSelectionModel().isEmpty()) {
                UserList userList = tblUser.getSelectionModel().getSelectedItem();

                user.setId(userList.id.get());

                dataReader.getUserByUserId();

                txtUserId.setText(Integer.toString(user.getId()));
                txtUserName.setText(user.getUserName());
                txtPassword.setText(user.getPassword());
                txtEmail.setText(user.getEmail());
                cmbUserType.setValue(userType.getType());
                txtMobile.setText(user.getMobile());
                cmbUserStatus.setValue(adStatus.getStatus());

                user.resetAll();
                userType.resetAll();
                adStatus.resetAll();

            }
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void getSelectedUserKey(KeyEvent event) {
        if (event.getCode().equals(KeyCode.UP) || event.getCode().equals(KeyCode.DOWN)) {
            getSelectedUser();
        }
        if (event.getCode().equals(KeyCode.ENTER)) {
            txtUserName.requestFocus();
        }
    }

    public void gotoTable(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            tblUser.requestFocus();
        }
    }

    public static class UserList {

        SimpleIntegerProperty id;
        SimpleStringProperty userName;
        SimpleStringProperty email;
        SimpleStringProperty type;
        SimpleStringProperty status;

        public UserList(int id, String userName, String email, String type, String status) {
            this.id = new SimpleIntegerProperty(id);
            this.userName = new SimpleStringProperty(userName);
            this.email = new SimpleStringProperty(email);
            this.type = new SimpleStringProperty(type);
            this.status = new SimpleStringProperty(status);
        }

        public String getUserName() {
            return userName.get();
        }

        public SimpleStringProperty userNameProperty() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName.set(userName);
        }

        public String getEmail() {
            return email.get();
        }

        public SimpleStringProperty emailProperty() {
            return email;
        }

        public void setEmail(String email) {
            this.email.set(email);
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

        public int getId() {
            return id.get();
        }

        public SimpleIntegerProperty idProperty() {
            return id;
        }

        public void setId(int id) {
            this.id.set(id);
        }
    }
}

package Controllers;


import DataControllers.DataReader;
import DataControllers.DataWriter;
import Modules.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    DataWriter dataWriter;
    DataReader dataReader;
    Alerts alerts;
    User user;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXButton btnLogin;

    @FXML
    private JFXButton btnExit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        alerts = ObjectGenerator.getAlerts();
        dataWriter = ObjectGenerator.getDataWriter();
        dataReader = ObjectGenerator.getDataReader();
        user = ObjectGenerator.getUser();
    }

    public void getLogin() {

        String userName = txtUserName.getText();
        String password = txtPassword.getText();

        dataReader.getUserByUNandPW(userName, password);

        if (!txtUserName.getText().isEmpty() && !txtPassword.getText().isEmpty()) {
            if (userName.equals(user.getUserName()) && password.equals(user.getPassword())) {
                loadMain();
                closeMe(btnLogin);
            } else if (!userName.equals(user.getUserName()) && !password.equals(user.getPassword())) {
                alerts.getWarningAlert("Warning", "Something went wrong", "Your user name and password incorrect !.\nPlease connect your system administrator.");
            } else if (user.getUserName() == "" || user.getPassword() == "") {
                alerts.getWarningAlert("Warning", "Something went wrong", "Could not found your login information !.\nPlease connect your system administrator.");
            }
        }
    }

    public void closeMe(JFXButton btnLogin) {
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        stage.close();
    }

    public void getLoginKey(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            loadMain();
        }
    }

    public void loadMain() {
        try {
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Views/frmMain.fxml"));
            primaryStage.setTitle("Inventory Master v2.0");
            Scene mainScene = new Scene(root);
            Font.loadFont(getClass().getClassLoader().getResource("Fonts/SriBhashitha.ttf").toExternalForm(), 15);
            mainScene.getStylesheets().add(getClass().getClassLoader().getResource("CSS/Decorator.css").toExternalForm());
            primaryStage.setScene(mainScene);
            primaryStage.getIcons().add(new Image("/Graphics/Main_01.png"));
            primaryStage.setOnCloseRequest(event -> {
                loadLogin();
            });
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void loadLogin() {
        try {
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Views/frmLogin.fxml"));
            //primaryStage.setTitle("Inventory Master v2.0");
            Scene mainScene = new Scene(root);
            Font.loadFont(getClass().getClassLoader().getResource("Fonts/SriBhashitha.ttf").toExternalForm(), 15);
            //mainScene.getStylesheets().add(getClass().getClassLoader().getResource("CSS/Decorator.css").toExternalForm());
            primaryStage.setScene(mainScene);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.getIcons().add(new Image("/Graphics/Main_01.png"));
            primaryStage.setOnCloseRequest(event -> {
                System.exit(0);
            });
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void exit() {
        System.exit(0);
    }

    public void exitKey(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ESCAPE)) {
            System.exit(0);
        }
    }
}

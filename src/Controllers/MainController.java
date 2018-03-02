package Controllers;

import eu.hansolo.enzo.notification.Notification;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private AnchorPane apParent;

    @FXML
    private AnchorPane apStatus;

    @FXML
    private MenuItem miRetail;

    @FXML
    private MenuItem miProductRegistration;

    @FXML
    private MenuItem mi_employee_manegment;

    @FXML
    private MenuItem mi_customer_manegment;

    @FXML
    private MenuItem mi_full_screen;

    @FXML
    private BorderPane rootpane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Thread readyData = new Thread(() -> {
                ObjectGenerator.getAutoBackup().AutoGet();
            });
            readyData.setName("Main Controller");
            readyData.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exitNow(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Attention !, This is a System Exit conformation.");
        alert.setContentText("Hi User, Do you really want to exit ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        } else {

        }
    }

    public void loadUserManagement() {
        try {
            Stage userStage = new Stage();
            Parent frmUser = FXMLLoader.load(getClass().getClassLoader().getResource("Views/frmUser.fxml"));
            userStage.setTitle("User Management");
            Scene scene = new Scene(frmUser);
            userStage.setScene(scene);
            userStage.initStyle(StageStyle.UTILITY);
            userStage.setResizable(false);
            userStage.initModality(Modality.APPLICATION_MODAL);
            userStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadSupplierManagement() {
        try {
            Stage supplierStage = new Stage();
            Parent frmSupplier = FXMLLoader.load(getClass().getClassLoader().getResource("Views/frmSupplier.fxml"));
            supplierStage.setTitle("Supplier Management");
            Scene scene = new Scene(frmSupplier);
            supplierStage.setScene(scene);
            supplierStage.initStyle(StageStyle.UTILITY);
            supplierStage.setResizable(false);
            supplierStage.initModality(Modality.APPLICATION_MODAL);
            supplierStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadProductManagement() {
        try {
            Stage productsStage = new Stage();
            Parent frmProduct = FXMLLoader.load(getClass().getClassLoader().getResource("Views/frmProducts.fxml"));
            productsStage.setTitle("Products Management");
            Scene scene = new Scene(frmProduct);
            productsStage.setScene(scene);
            productsStage.initStyle(StageStyle.UTILITY);
            productsStage.setResizable(false);
            productsStage.initModality(Modality.APPLICATION_MODAL);
            productsStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadEmployeeManagement() {
        try {
            Stage productsStage = new Stage();
            Parent frmEmployee = FXMLLoader.load(getClass().getClassLoader().getResource("Views/frmEmployee.fxml"));
            productsStage.setTitle("Employee Management");
            Scene scene = new Scene(frmEmployee);
            productsStage.setScene(scene);
            productsStage.initStyle(StageStyle.UTILITY);
            productsStage.setResizable(false);
            productsStage.initModality(Modality.APPLICATION_MODAL);
            productsStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadCustomerManagement() {
        try {
            Stage productsStage = new Stage();
            Parent frmCustomer = FXMLLoader.load(getClass().getClassLoader().getResource("Views/frmCustomer.fxml"));
            productsStage.setTitle("Customer Management");
            Scene scene = new Scene(frmCustomer);
            productsStage.setScene(scene);
            productsStage.initStyle(StageStyle.UTILITY);
            productsStage.setResizable(false);
            productsStage.initModality(Modality.APPLICATION_MODAL);
            productsStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void fullscreen(){
        Stage stage=((Stage) rootpane.getScene().getWindow());
        stage.setFullScreen(!stage.isFullScreen());
    }


    public void showNotifi() {
        Notification.Notifier.INSTANCE.notifySuccess("Database Backup", "Database backup successfully");
        Notifications notifications = Notifications.create().title("Database Backup").text("Ok").graphic(null).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT).onAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        notifications.darkStyle();
        notifications.show();
    }


}

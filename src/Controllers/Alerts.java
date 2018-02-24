package Controllers;

import eu.hansolo.enzo.notification.Notification;
import javafx.scene.control.Alert;

import java.awt.*;

public class Alerts {

    public void getInformationAlert(String title, String hearder, String content) {
        //new Thread(() -> {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        //alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText(hearder);
        alert.setContentText(content);
        alert.showAndWait();
        //}).start();
    }

    public void getWarningAlert(String title, String hearder, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        //alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText(hearder);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void getErrorAlert(String title, String hearder, String content) {
        Toolkit.getDefaultToolkit().beep();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(hearder);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void getErrorAlert(Exception content) {
        Toolkit.getDefaultToolkit().beep();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Something went wrong...!");
        alert.setContentText(content.toString());
        alert.showAndWait();
    }

    public void getSuccessNotify(String title, String message) {
        Notification.Notifier.INSTANCE.notifySuccess(title, message);
    }

    public void getWarningNotify(String title, String message) {
        Notification.Notifier.INSTANCE.notifyWarning(title, message);
    }

    public void getErrorNotify(String title, String message) {
        Notification.Notifier.INSTANCE.notifyError(title, message);
    }
}

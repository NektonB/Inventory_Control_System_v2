package Controllers;

import DB_Conn.ConnectDB;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.*;
import java.sql.Connection;

public class Main extends Application {

    private static Connection dbConn;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Views/frmMain.fxml"));
        primaryStage.setTitle("Inventory Master v2.0");
        Scene mainScene = new Scene(root,1366,798);
        Font.loadFont(getClass().getClassLoader().getResource("Fonts/SriBhashitha.ttf").toExternalForm(),15);
        mainScene.getStylesheets().add(getClass().getClassLoader().getResource("CSS/Decorator.css").toExternalForm());
        primaryStage.setScene(mainScene);
        //primaryStage.setFullScreen(true);
        //primaryStage.setMaximized(true);
        primaryStage.getIcons().add(new Image("/Graphics/Main_01.png"));
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        //primaryStage.resizableProperty().setValue(false);
        primaryStage.setOnCloseRequest(event -> {
            System.exit(0);
        });
        primaryStage.show();
    }

    private static void initialing() {
        ObjectGenerator.readyAll();
        //ObjectGenerator.getAutoBackup().AutoGet();

    }

    public static void main(String[] args) {

        Thread readyAll = new Thread(() -> initialing());
        //readyAll.setPriority(Thread.MAX_PRIORITY);
        dbConn = ConnectDB.getConn();
        if (dbConn != null) {
            //System.out.println("Connection success...!");
            //Toolkit.getDefaultToolkit().beep();
           /* Platform.runLater(() -> {
                ///notifications.show();
                ObjectGenerator.getAlerts().getSuccessNotify("Connection", "Database connection success...!");
            });*/
            readyAll.start();
            initialing();
        }

        launch(args);
    }


}

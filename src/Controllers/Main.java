package Controllers;

import DB_Conn.ConnectDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.sql.Connection;

public class Main extends Application {

    private static Connection dbConn;

    private static void initialing() {
        ObjectGenerator.readyAll();
    }

    public static void main(String[] args) {
        //Thread readyAll = new Thread(() -> initialing());
        //readyAll.setPriority(Thread.MAX_PRIORITY);
        initialing();
        dbConn = ConnectDB.getConn();
        if (dbConn != null) {
            //System.out.println("Connection success...!");
            //Toolkit.getDefaultToolkit().beep();
           /* Platform.runLater(() -> {
                ///notifications.show();
                ObjectGenerator.getAlerts().getSuccessNotify("Connection", "Database connection success...!");
            });*/
            //readyAll.start();
            //initialing();
        }
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
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
    }


}

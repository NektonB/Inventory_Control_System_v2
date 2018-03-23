/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

//import View.Backup;

import DataControllers.DataReader;
import Modules.BackupData;
import Modules.ConnectionInfo;
import javafx.application.Platform;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Buddhika Prasanna
 */
public class AutoBackup {

    DataReader dataReader;
    ConnectionInfo connectionInfo;
    BackupData backupData;
    Alerts alerts;

    private String database, username, password;
    private String dumppath;
    private Thread Backuper;
    int count, fileCount = 0;


    public AutoBackup() {
        try {
            //Thread readyData = new Thread(() -> {
                alerts = ObjectGenerator.getAlerts();
                dataReader = ObjectGenerator.getDataReader();
                connectionInfo = ObjectGenerator.getConnectionInfo();
                backupData = ObjectGenerator.getBackupData();
            //});
            //readyData.setName("AutoBackup");
            //readyData.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AutoBackup(String ready) {

    }

    private void prepareBackup() {

        String backUpPath = "C:\\Backup";
        File bpath = new File(backUpPath);
        if (bpath.exists()) {
            getBackUp(backUpPath, bpath);
        } else {
            boolean mkdir = bpath.mkdir();
            if (mkdir) {
                getBackUp(backUpPath, bpath);
            }
        }

    }

    private void getBackUp(String backpath, File file) {

        String mgsSucces = "Database backup successful.";
        String mgsFailure = "Database backup failure";

        dataReader.getSQLDumpPath();
        dataReader.getConnectionInformation();

        database = connectionInfo.getDatabase();
        username = connectionInfo.getUsername();
        password = connectionInfo.getPassword();
        dumppath = backupData.getMysqlDumpPath();

        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String strFilename = dateFormat.format(now);

        String[] myFilesList;
        myFilesList = file.list();

        for (int i = 0; i < myFilesList.length; i++) {
            fileCount = 0;
            fileCount += i;
        }

        if (fileCount >= 10) {
            for (int i = 0; i < 10; i++) {
                File selectFile = new File(file, myFilesList[i]);
                selectFile.delete();
            }
/*//            for (String myFile : myFilesList) {
//                File selectFile = new File(file, myFile);
//                selectFile.delete();
//            }*/
            fileCount = 0;
        }

        String command = " " + dumppath + " -u" + username + " -p" + password + " --add-drop-database -B " + database + " -r " + "\"" + backpath + "\\" + strFilename + ".sql\"";
        Process p = null;
        try {
            Runtime runtime = Runtime.getRuntime();
            p = runtime.exec(command);

            int processComplete = p.waitFor();

            if (processComplete == 0) {
                if (count > 0) {
                    //Platform.runLater(() -> {
                        ///notifications.show();
                        alerts.getSuccessNotify("Database Backup", mgsSucces);
                    //});
                }
                count = 1;
                //fileCount++;
                fileCount = 0;
            } else {
                Toolkit.getDefaultToolkit().beep();
                //Platform.runLater(() -> {
                    ///notifications.show();
                    alerts.getWarningNotify("Database Backup", mgsFailure);
                //});
            }

        } catch (IOException | InterruptedException | HeadlessException e) {
            e.printStackTrace();
        }

    }

    public void AutoGet() {
        Backuper = new Thread(() -> {
            while (true) {
                prepareBackup();
                try {
                    Thread.sleep(300000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    //Platform.runLater(() -> {
                        alerts.getErrorNotify("Database Backup", ex.getMessage());
                    //});
                }
            }
        });
        Backuper.setName("AutoGet");
        Backuper.start();

    }
}

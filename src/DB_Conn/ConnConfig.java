/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB_Conn;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnConfig {

    private static Connection ConfigConn;
    private static ResultSet serverConfig, path;
    private static String serverIP, port, database, username, password, id = "1";

    public static Connection getServerConfig() {
        if (ConfigConn == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                ConfigConn = DriverManager.getConnection("jdbc:sqlite:C:\\Program Files\\Common Files\\ICSv2\\server_connection.db");
            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, "No Database Driver found", "Database Driver Error", JOptionPane.ERROR_MESSAGE);
                Toolkit.getDefaultToolkit().beep();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Database Connection fail", "SQL Error", JOptionPane.ERROR_MESSAGE);
                Toolkit.getDefaultToolkit().beep();
            }
        }
        return ConfigConn;
    }

    public static String getServerIP() {
        try {
            serverConfig = getServerConfig().createStatement().executeQuery("SELECT server_ip FROM tbl_connection WHERE Id = '1' ");
            while (serverConfig.next()) {
                serverIP = serverConfig.getString("server_ip");//serverConfig.getString("server_ip");
//                serverConfig.getString("server_ip");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            Toolkit.getDefaultToolkit().beep();
        } finally {
            try {
                serverConfig.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return serverIP;
    }

    public static String getServerPort() {
        try {
            serverConfig = getServerConfig().createStatement().executeQuery("SELECT server_port FROM tbl_connection WHERE Id = '1' ");
            while (serverConfig.next()) {
                port = serverConfig.getString("server_port");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            Toolkit.getDefaultToolkit().beep();
        } finally {
            try {
                serverConfig.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return port;
    }

    public static String getDatabase() {
        try {
            serverConfig = getServerConfig().createStatement().executeQuery("SELECT database FROM tbl_connection WHERE Id = '1' ");
            while (serverConfig.next()) {
                database = serverConfig.getString("database");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            Toolkit.getDefaultToolkit().beep();
        } finally {
            try {
                serverConfig.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return database;
    }

    public static String getLoggedUser() {
        try {
            serverConfig = getServerConfig().createStatement().executeQuery("SELECT user_name FROM tbl_connection WHERE Id = '1' ");
            while (serverConfig.next()) {
                username = serverConfig.getString("user_name");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            Toolkit.getDefaultToolkit().beep();
        } finally {
            try {
                serverConfig.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return username;
    }

    public static String getLoggedPassword() {
        try {
            serverConfig = getServerConfig().createStatement().executeQuery("SELECT password FROM tbl_connection WHERE Id = '1' ");
            while (serverConfig.next()) {
                password = serverConfig.getString("password");
            }
        } catch (Exception e) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                serverConfig.close();
            } catch (Exception e) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return password;
    }

}

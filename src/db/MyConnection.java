/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class MyConnection {
    private static final String url = "jdbc:mysql://localhost:3306/students_management?autoReconnect=true&useSSL=false";
    private static final String username = "root";
    private static final String password = "131204";
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        // nap driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        return (Connection) DriverManager.getConnection(url, username, password);
    }
}

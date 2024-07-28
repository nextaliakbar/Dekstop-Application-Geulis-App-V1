/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
/**
 *
 * @author usER
 */
public class Koneksi {
    
    private static Connection connection;
    
    public static Connection getConnection() {
    String database = "geulis";
    String username = "root";
    String password = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/".concat(database), username, password);
        } catch(ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        
        return connection;
    }
}

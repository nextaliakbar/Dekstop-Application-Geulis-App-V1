/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ModelPengguna;
import swing.Pagination;

/**
 *
 * @author Alfito Dwi
 */
public class ServicePengguna {
    private Connection connection;

    public ServicePengguna() {
        connection = Koneksi.getConnection();
    }
    
     public void loadData(int page, DefaultTableModel tabmodel, Pagination pagination) {
        String sqlCount = "SELECT COUNT(ID_Pengguna) AS Jumlah FROM pengguna";
        int limit = 15;
        int count = 0;
        
        String query = "SELECT * FROM pengguna LIMIT "+(page-1) * limit+","+limit+"";
        
        try {
            PreparedStatement pst = connection.prepareStatement(sqlCount);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                count = rst.getInt("Jumlah");
            }
            
            pst.close();
            rst.close();
            
            pst = connection.prepareStatement(query);
            rst = pst.executeQuery();
            while(rst.next()) {
                String IdPengguna = rst.getString("ID_Pengguna");
                String NamaPengguna= rst.getString("Nama");
                String UsernamePengguna = rst.getString("Username");
                String PasswordPengguna = rst.getString("Password");
                String EmailPengguna = rst.getString("Email");
                String LevelPengguna = rst.getString("Level");
               
                tabmodel.addRow(new Object[]{IdPengguna, NamaPengguna, UsernamePengguna, EmailPengguna, LevelPengguna});
            }
            pst.close();
            rst.close();
            
            int totalPage = (int) Math.ceil((double)count / limit);
            pagination.setPagination(page, totalPage);
        } catch(Exception ex) {
            ex.printStackTrace();
        }    
    }
                
    public void addData(ModelPengguna modelPengguna) {
        String query = "INSERT INTO pengguna (ID_Pengguna, Nama, Username, Password, Email, Level ) VALUES (?,?,?,?,?,?)";
        System.out.println(query);
        try {
           PreparedStatement pst = connection.prepareStatement(query);
           pst.setString(1, modelPengguna.getIdpengguna());
           pst.setString(2, modelPengguna.getNama());
           pst.setString(3, modelPengguna.getUsername());
           pst.setString(4, modelPengguna.getPassword());
            System.out.println(modelPengguna.getPassword());
           pst.setString(5, modelPengguna.getEmail());
           pst.setString(6, modelPengguna.getLevel());
           pst.executeUpdate();
           JOptionPane.showMessageDialog(null, "Data Berhasil Ditambahkan");
           pst.close();
           
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    
}
    public void updateData(ModelPengguna modelPengguna){
     String query = "UPDATE pengguna SET Nama=?, Username=?, Password=?, Email=?, Level=? WHERE ID_Pengguna=?"; 
     try {
          PreparedStatement pst = connection.prepareStatement(query);
           pst.setString(1, modelPengguna.getNama());
           pst.setString(2, modelPengguna.getUsername());
           pst.setString(3, modelPengguna.getPassword());
           pst.setString(4, modelPengguna.getEmail());
           pst.setString(5, modelPengguna.getLevel());
           pst.setString(6, modelPengguna.getIdpengguna());
           pst.executeUpdate();
           JOptionPane.showMessageDialog(null, "Data Pengguna Berhasil Diperbarui");
           pst.close();
           
        } catch(Exception ex) {
            ex.printStackTrace();
        }
     }
    public void deleteData(ModelPengguna modelPengguna){
    String query = "DELETE FROM pengguna WHERE ID_Pengguna=?";
    try{
        PreparedStatement pst = connection.prepareCall(query);
        pst.setString(1, modelPengguna.getIdpengguna());
        pst.executeUpdate();
        JOptionPane.showMessageDialog(null, "Pengguna Berhasil Di Hapus");
    } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public String createId() {
        String idPasien = null;
        String query = "SELECT RIGHT(ID_Pengguna, 3) AS ID FROM pengguna ORDER BY ID_Pengguna DESC";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                int number = Integer.parseInt(rst.getString("ID"));
                number++;
                idPasien = "USR-" + String.format("%03d", number);
            } else {
                idPasien = "USR-001";
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return idPasien;
    }
}

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

/**
 *
 * @author Alfito Dwi
 */
public class ServicePengguna {
    private Connection connection;

    public ServicePengguna() {
        connection = Koneksi.getConnection();
    }
    
     public void loadData(DefaultTableModel tabmodel) {
        String query = "SELECT * FROM pengguna";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                String IdPengguna = rst.getString("ID_Pengguna");
                String NamaPengguna= rst.getString("Nama");
                String UsernamePengguna = rst.getString("Username");
                String EmailPengguna = rst.getString("Email");
                String LevelPengguna = rst.getString("Level");
                String StatusPengguna = rst.getString("Status_Pengguna");
               
                tabmodel.addRow(new Object[]{IdPengguna, NamaPengguna, UsernamePengguna, EmailPengguna, LevelPengguna, StatusPengguna});
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }    
    }
                
    public void addData(ModelPengguna modelPengguna) {
        String query = "INSERT INTO pengguna (ID_Pengguna, Nama, Username, Password, Email, Level, Status_Pengguna ) VALUES (?,?,?,?,?,?,?)";
        try {
           PreparedStatement pst = connection.prepareStatement(query);
           pst.setString(1, modelPengguna.getIdpengguna());
           pst.setString(2, modelPengguna.getNama());
           pst.setString(3, modelPengguna.getUsername());
           pst.setString(4, modelPengguna.getPassword());
           pst.setString(5, modelPengguna.getEmail());
           pst.setString(6, modelPengguna.getLevel());
           pst.setString(7, modelPengguna.getStatus());
           pst.executeUpdate();
           pst.close();
           JOptionPane.showMessageDialog(null, "Data Pengguna Berhasil Ditambahkan");
           
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    
}
    public void updateData(ModelPengguna modelPengguna){
     String query = "UPDATE pengguna SET Nama=?, Username=?, Password=?, Email=?, Level=?, Status_Pengguna=? WHERE ID_Pengguna=?"; 
     try {
          PreparedStatement pst = connection.prepareStatement(query);
           pst.setString(1, modelPengguna.getNama());
           pst.setString(2, modelPengguna.getUsername());
           pst.setString(3, modelPengguna.getPassword());
           pst.setString(4, modelPengguna.getEmail());
           pst.setString(5, modelPengguna.getLevel());
           pst.setString(6, modelPengguna.getStatus());
           pst.setString(7, modelPengguna.getIdpengguna());
           pst.executeUpdate();
           pst.close();
           JOptionPane.showMessageDialog(null, "Data Pengguna Berhasil Diperbarui");
           
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
        pst.close();
        JOptionPane.showMessageDialog(null, "Data Pengguna Berhasil Di Hapus");
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
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return idPasien;
    }
    
    public boolean validationDelete(ModelPengguna modelPengguna) {
        boolean valid = false;
        String query1 = "SELECT ID_Pengguna FROM pemeriksaan WHERE ID_Pengguna='"+modelPengguna.getIdpengguna()+"' ";
        String query2 = "SELECT ID_Pengguna FROM pemesanan WHERE ID_Pengguna='"+modelPengguna.getIdpengguna()+"' ";
        String query3 = "SELECT ID_Pengguna FROM penjualan WHERE ID_Pengguna='"+modelPengguna.getIdpengguna()+"' ";
        String query4 = "SELECT ID_Pengguna FROM restok WHERE ID_Pengguna='"+modelPengguna.getIdpengguna()+"' ";
        try {
            PreparedStatement pst1 = connection.prepareStatement(query1);
            ResultSet rst1 = pst1.executeQuery();
            PreparedStatement pst2 = connection.prepareStatement(query2);
            ResultSet rst2 = pst2.executeQuery();
            PreparedStatement pst3 = connection.prepareStatement(query3);
            ResultSet rst3 = pst3.executeQuery();
            PreparedStatement pst4 = connection.prepareStatement(query4);
            ResultSet rst4 = pst4.executeQuery();
            if(rst1.next() || rst2.next() || rst3.next() || rst4.next()) {
                JOptionPane.showMessageDialog(null, "Tidak dapat menghapus pengguna ini\n"
               + "Pengguna ini pernah melakukan\n"
               + "Transaksi silahkan ubah status\n"
               + "Pengguna ini menjadi nonaktif", "Peringatan", JOptionPane.WARNING_MESSAGE);
            } else {
                valid = true;
            }
            rst1.close();
            rst2.close();
            rst3.close();
            pst1.close();
            pst2.close();
            pst3.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return valid;
    }
    
    public boolean validationAddEmail(ModelPengguna modelPengguna) {
        boolean valid = true;
        String query = "SELECT Email FROM pengguna";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                if(modelPengguna.getEmail().equalsIgnoreCase(rst.getString("Email"))) {
                    JOptionPane.showMessageDialog(null, "Email telah terdaftar");
                    valid = false;
                    break;
                }
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return valid;
    }
    
}

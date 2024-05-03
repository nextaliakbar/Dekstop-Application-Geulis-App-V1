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
import model.ModelKaryawan;
import swing.Pagination;

/**
 *
 * @author Alfito Dwi
 */
public class ServiceKaryawan {
    private Connection connection;

    public ServiceKaryawan() {
        connection = Koneksi.getConnection();
    }
     public void loadData(int page, DefaultTableModel tabmodel, Pagination pagination) {
        String sqlCount = "SELECT COUNT(ID_Karyawan) AS Jumlah FROM karyawan";
        int limit = 15;
        int count = 0;
        String query ="SELECT * FROM karyawan LIMIT "+(page-1) * limit+","+limit+"";            
        try {
           PreparedStatement pst = connection.prepareStatement(sqlCount);
           ResultSet rst = pst.executeQuery();
           if(rst.next()){
               count = rst.getInt("Jumlah");
                       }
                
           pst.close();
           rst.close();
           
           pst = connection.prepareStatement(query);
           rst = pst.executeQuery();
           while(rst.next()){
               String IdKaryawan = rst.getString("ID_Karyawan");
               String NamaKaryawan = rst.getString("Nama");
               String TeleponKaryawan = rst.getString("No_Telp");
               String EmailKaryawan = rst.getString("Email");
               String AlamatKaryawan = rst.getString("Alamat");
               String JabatanKaryawan = rst.getString("Jabatan");
               tabmodel.addRow(new Object[]{IdKaryawan, NamaKaryawan, TeleponKaryawan, EmailKaryawan, AlamatKaryawan, JabatanKaryawan});
           }
           
           pst.close();
           rst.close();
           
            int totalPage = (int) Math.ceil((double)count / limit);
            pagination.setPagination(page, totalPage);    
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    public void addData(ModelKaryawan modelKaryawan) {
        String query = "INSERT INTO karyawan (ID_Karyawan, Nama, No_Telp, Email, Alamat, Jabatan) VALUES (?,?,?,?,?,?)";
        try {
           PreparedStatement pst = connection.prepareStatement(query);
           pst.setString(1, modelKaryawan.getIdKaryawan());
           pst.setString(2, modelKaryawan.getNama());
           pst.setString(3, modelKaryawan.getNoTelp());
           pst.setString(4, modelKaryawan.getEmail());
           pst.setString(5, modelKaryawan.getAlamat());
           pst.setString(6, modelKaryawan.getJabatan());
           pst.executeUpdate();
           JOptionPane.showMessageDialog(null, "Karyawan Berhasil Ditambahkan");
           pst.close();
           
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    
    }
    public void updateData(ModelKaryawan modelKaryawan){
     String query = "UPDATE karyawan SET Nama=?, No_Telp=?, Email=?, Jabatan=?, Alamat=? WHERE ID_Karyawan=?";
     try {
          PreparedStatement pst = connection.prepareStatement(query);
           pst.setString(1, modelKaryawan.getNama());
           pst.setString(2, modelKaryawan.getNoTelp());
           pst.setString(3, modelKaryawan.getEmail());
           pst.setString(4, modelKaryawan.getJabatan());
           pst.setString(5, modelKaryawan.getAlamat());
           pst.setString(6, modelKaryawan.getIdKaryawan());
           pst.executeUpdate();
           JOptionPane.showMessageDialog(null, "Data Karyawan  Berhasil Diperbarui");
           pst.close();
           
        } catch(Exception ex) {
            ex.printStackTrace();
        }
     }
    public void deleteData(ModelKaryawan modelKaryawan){
    String query = "DELETE FROM karyawan WHERE ID_Karyawan=?";
    try{
        PreparedStatement pst = connection.prepareCall(query);
        pst.setString(1, modelKaryawan.getIdKaryawan());
        pst.executeUpdate();
        JOptionPane.showMessageDialog(null, "Data Karyawan Berhasil Di Hapus");
    } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public String createId() {
        String idPasien = null;
        String query = "SELECT RIGHT(ID_Karyawan, 3) AS ID FROM karyawan ORDER BY ID_Karyawan DESC";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                int number = Integer.parseInt(rst.getString("ID"));
                number++;
                idPasien = "STAFF-" + String.format("%03d", number);
            } else {
                idPasien = "STAFF-001";
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return idPasien;
    }
}
                

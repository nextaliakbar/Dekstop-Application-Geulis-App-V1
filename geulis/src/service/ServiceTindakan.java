/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import java.sql.Connection;
import model.ModelTindakan;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import swing.Pagination;

/**
 *
 * @author User
 */
public class ServiceTindakan {
    private Connection connection;
    
    public ServiceTindakan() {
        connection = Koneksi.getConnection();
    }
    
    public void loadData(int page, Pagination pagination, DefaultTableModel model) {
        String sqlCount = "SELECT COUNT(Kode_Tindakan) AS Jumlah FROM Tindakan";
        int limit = 15;
        int count = 0;
        String query = "SELECT * FROM tindakan LIMIT "+(page-1) * limit+","+limit+"";
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
                String kodeTindakan = rst.getString("Kode_Tindakan");
                String namaTindakan = rst.getString("Nama_Tindakan");
                int biaya = rst.getInt("Biaya_Tindakan");
                model.addRow(new Object[]{kodeTindakan, namaTindakan, biaya});
            }
            
            pst.close();
            rst.close();
            
            int totalPage = (int) Math.ceil((double)count / limit);
            pagination.setPagination(page, totalPage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void addData(ModelTindakan modelTindakan) {
        String query = "INSERT INTO tindakan (Kode_Tindakan, Nama_Tindakan, Biaya_Tindakan) VALUES (?,?,?)";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelTindakan.getKodeTindakan());
            pst.setString(2, modelTindakan.getNamaTindakan());
            pst.setInt(3, modelTindakan.getBiaya());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void updateData(ModelTindakan modelTindakan) {
        String query = "UPDATE tindakan SET Nama_Tindakan=?, Biaya_Tindakan=? WHERE Kode_Tindakan=?";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelTindakan.getNamaTindakan());
            pst.setInt(2, modelTindakan.getBiaya());
            pst.setString(3, modelTindakan.getKodeTindakan());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil diperbarui");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void deletaData(ModelTindakan modelTindakan) {
        String query = "DELETE FROM tindakan WHERE Kode_Tindakan=?";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelTindakan.getKodeTindakan());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public String createKodeTindakan() {
        String kodeTindakan = null;
        String query = "SELECT RIGHT(Kode_Tindakan, 3) AS Kode FROM Tindakan ORDER BY Kode_Tindakan DESC";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                int number = Integer.parseInt(rst.getString("Kode"));
                number++;
                kodeTindakan = "TDKN-" + String.format("%03d", number);
            } else {
                kodeTindakan = "TDKN-001";
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return kodeTindakan;
    }
    
    public boolean validationDelete(ModelTindakan modelTindakan) {
        boolean valid = false;
        String query = "SELECT Kode_Tindakan FROM detail_pemeriksaan WHERE Kode_Tindakan='"+modelTindakan.getKodeTindakan()+"' ";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                JOptionPane.showMessageDialog(null, "Tidak dapat menghapus tindakan ini\n"
               + "Tindakan ini pernah digunakan di transaksi", "Peringatan", JOptionPane.WARNING_MESSAGE);
            } else {
                System.out.println(valid);
                valid = true;
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return valid;
    }
}
       

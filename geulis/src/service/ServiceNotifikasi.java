/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.ModelNotifikasi;
/**
 *
 * @author usER
 */
public class ServiceNotifikasi {
    
    private Connection connection;
    
    public ServiceNotifikasi() {
        connection = Koneksi.getConnection();
    }
    
    public void addNotification(ModelNotifikasi modelNotifikasi) {
        String query = "INSERT INTO notifikasi (ID_Notifikasi, Tanggal_Notifikasi, Nama_Notifikasi, Deskripsi, Kode_Barang, Status_Sudah_Dibaca) "
                + "VALUES (?,?,?,?,?,?)";
        try(PreparedStatement pst = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
           pst.setString(1, modelNotifikasi.getIdNotifkasi());
           pst.setTimestamp(2, Timestamp.valueOf(modelNotifikasi.getTanggalNotifikasi()));
           pst.setString(3, "Perubahan Harga Jual");
           pst.setString(4, modelNotifikasi.getDeskripsi());
           pst.setString(5, modelNotifikasi.getKodeBrg());
           pst.setBoolean(6, true);
           pst.executeUpdate();
        } catch(SQLException exception) {
            throw new IllegalArgumentException(exception);
        }
    }
    
    public List<ModelNotifikasi> getAll() {
        String query = "SELECT Tanggal_Notifikasi, Nama_Notifikasi, Deskripsi, Kode_Barang, Status FROM notifikasi";
        try(PreparedStatement pst = connection.prepareStatement(query);
                ResultSet rst = pst.executeQuery()) {
            while(rst.next()) {
                List<ModelNotifikasi> notifications = new ArrayList<>();
                ModelNotifikasi modelNotifikasi = new ModelNotifikasi();
                modelNotifikasi.setNamaNotifikasi(rst.getString("Nama_Notifikasi"));
                modelNotifikasi.setKodeBrg(rst.getString("Kode_Barang"));
                notifications.add(modelNotifikasi);
                return notifications;
            }
            return null;
        } catch(SQLException exception) {
            throw new IllegalArgumentException(exception);
        }
    }
    
}

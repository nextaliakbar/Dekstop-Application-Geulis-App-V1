/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import model.ModelDetailPemesanan;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Sementara;
/**
 *
 * @author usER
 */
public class ServiceDetailPemesanan {
    private Connection connection;
    
    public ServiceDetailPemesanan() {
        connection = Koneksi.getConnection();
    }
    
    public void loadData(ModelDetailPemesanan modelDetail, DefaultTableModel tabmodel) {
        String query = "SELECT dtl.Kode_Barang, brg.Nama_Barang, " +
                "brg.Satuan, brg.Harga_Beli, dtl.Jumlah, dtl.Subtotal FROM detail_pemesanan dtl " +
                "INNER JOIN barang brg ON dtl.Kode_Barang=brg.Kode_Barang " +
                "WHERE No_Pemesanan='"+modelDetail.getModelPemesanan().getNoPemesanan()+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                String kodeBarang = rst.getString("Kode_Barang");
                String namaBarang = rst.getString("Nama_Barang");
                String satuan = rst.getString("Satuan");
                int hrgBeli = rst.getInt("Harga_Beli");
                int jumlah = rst.getInt("Jumlah");
                int subtotal = rst.getInt("Subtotal");
                tabmodel.addRow(new Object[]{kodeBarang, namaBarang, satuan, hrgBeli, jumlah, subtotal});
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void addData(ModelDetailPemesanan modelDetail, Sementara ps) {
        String query = "INSERT INTO detail_pemesanan (No_Pemesanan, Kode_Barang, Jumlah, Subtotal) VALUES (?,?,?,?) ";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelDetail.getModelPemesanan().getNoPemesanan());
            
            String[] listKodeBrg = ps.getKodeBrg();
            for(String kodeBrg : listKodeBrg) {
                pst.setString(2, kodeBrg);
            }
            
            int[] listJumlah = ps.getJumlah();
            for(int jumlah : listJumlah) {
                pst.setInt(3, jumlah);
            }
            
            double[] listSubtotal = ps.getSubtotal();
            for(double subtotal : listSubtotal) {
                pst.setDouble(4, subtotal);
            }
            
            pst.executeUpdate();
            
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}

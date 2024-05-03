/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import model.ModelDetailPenjualan;
import model.Sementara;
/**
 *
 * @author usER
 */
public class ServiceDetailPenjualan {
    private Connection connection;
    public ServiceDetailPenjualan() {
        connection = Koneksi.getConnection();
    }
    
    public void loadData(ModelDetailPenjualan modelDetail, DefaultTableModel tabmodel) {
        String query = "SELECT dtl.Kode_Barang, brg.Nama_Barang, brg.Satuan, brg.Harga_Jual, "
                + "dtl.Jumlah, dtl.Subtotal FROM detail_penjualan dtl JOIN barang brg "
                + "ON dtl.Kode_Barang=brg.Kode_Barang WHERE No_Penjualan='"+modelDetail.getModelPenjualan().getNoPenjualan()+"' ";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                String kodeBrg = rst.getString("Kode_Barang");
                String namaBrg = rst.getString("Nama_Barang");
                String satuan = rst.getString("Satuan");
                int hrgJual = rst.getInt("Harga_Jual");
                int jumlah = rst.getInt("Jumlah");
                int subtotal = rst.getInt("Subtotal");
                tabmodel.addRow(new Object[]{kodeBrg, namaBrg, satuan, hrgJual, jumlah, subtotal});
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void addData(ModelDetailPenjualan modelDetail, Sementara ps) {
        String query = "INSERT INTO detail_penjualan (No_Penjualan, Kode_Barang, Jumlah, Subtotal) VALUES (?,?,?,?) ";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelDetail.getModelPenjualan().getNoPenjualan());
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

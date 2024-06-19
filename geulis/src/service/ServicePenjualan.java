/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ModelBarang;
import model.ModelPenjualan;
import swing.Pagination;
/**
 *
 * @author usER
 */
public class ServicePenjualan {
    private Connection connection;
    private final DecimalFormat df = new DecimalFormat("#,##0.##");
    public ServicePenjualan() {
        connection = Koneksi.getConnection();
    }
    
        public void loadData(int page, DefaultTableModel tabmodel, Pagination pagination) {
        String sqlCount = "SELECT COUNT(No_Penjualan) AS Jumlah FROM penjualan";
        int limit = 16;
        int count = 0;
        
        String query = "SELECT pjl.No_Penjualan, DATE_FORMAT(pjl.Tanggal, '%d - %M - %Y') AS Tanggal_Penjualan, "
                + "pjl.Total_Penjualan, pjl.Bayar, pjl.Kembali, pjl.Jenis_Pembayaran, pjl.ID_Pengguna, "
                + "pgn.Nama FROM penjualan pjl INNER JOIN pengguna pgn ON pjl.ID_Pengguna=pgn.ID_Pengguna "
                + "ORDER BY No_Penjualan DESC LIMIT "+(page-1) * limit+","+limit+"";
        
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
                String noPenjualan = rst.getString("No_Penjualan");
                String idPengguna = rst.getString("ID_Pengguna");
                String namaPengguna = rst.getString("pgn.Nama");
                String tglPenjualan = rst.getString("Tanggal_Penjualan");
                int total = rst.getInt("Total_Penjualan");
                double bayar = rst.getDouble("Bayar");
                double kembali = rst.getDouble("Kembali");
                String jenisPembayaran = rst.getString("Jenis_Pembayaran");
                tabmodel.addRow(new Object[]{noPenjualan, idPengguna, namaPengguna, tglPenjualan, df.format(total), bayar, kembali, jenisPembayaran});
            }
            pst.close();
            rst.close();
            
            int totalPage = (int) Math.ceil((double)count / limit);
            pagination.setPagination(page, totalPage);
        } catch(Exception ex) {
            ex.printStackTrace();
        }    
    }
        
    public void loadAll(DefaultTableModel tabmodel) {
        String query = "SELECT pjl.No_Penjualan, DATE_FORMAT(pjl.Tanggal, '%d - %M - %Y') AS Tanggal_Penjualan, "
                + "pjl.Total_Penjualan, pjl.Bayar, pjl.Kembali, pjl.Jenis_Pembayaran, pjl.ID_Pengguna, "
                + "pgn.Nama FROM penjualan pjl INNER JOIN pengguna pgn ON pjl.ID_Pengguna=pgn.ID_Pengguna ";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                String noPenjualan = rst.getString("No_Penjualan");
                String idPengguna = rst.getString("ID_Pengguna");
                String namaPengguna = rst.getString("pgn.Nama");
                String tglPenjualan = rst.getString("Tanggal_Penjualan");
                int total = rst.getInt("Total_Penjualan");
                double bayar = rst.getDouble("Bayar");
                double kembali = rst.getDouble("Kembali");
                String jenisPembayaran = rst.getString("Jenis_Pembayaran");
                tabmodel.addRow(new Object[]{noPenjualan, idPengguna, namaPengguna, tglPenjualan, total, bayar, kembali, jenisPembayaran});
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void addData(ModelPenjualan modelPenjualan) {
        String query = "INSERT INTO penjualan (No_Penjualan, Tanggal, Total_Penjualan, Bayar, Kembali, Jenis_Pembayaran, ID_Pengguna) "
                + "VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelPenjualan.getNoPenjualan());
            pst.setString(2, modelPenjualan.getTglPenjualan());
            pst.setString(3, modelPenjualan.getTotalPenjualan());
            pst.setDouble(4, modelPenjualan.getBayar());
            pst.setDouble(5, modelPenjualan.getKembali());
            pst.setString(6, modelPenjualan.getJenisPembayaran());
            pst.setString(7, modelPenjualan.getModelPengguna().getIdpengguna());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Berhasil");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public String createNo() {
        String noPenjualan = null;
        Date date = new Date();
        String format = new SimpleDateFormat("yyMM").format(date);
        String query = "SELECT RIGHT(No_Penjualan, 3) AS Nomor FROM penjualan WHERE No_Penjualan LIKE 'PJLN-"+format+"%' ORDER BY No_Penjualan DESC";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                int number = Integer.parseInt(rst.getString("Nomor"));
                number++;
                noPenjualan = "PJLN-" + format + "-" + String.format("%03d", number);
            } else {
                noPenjualan = "PJLN-"+format+"-001";
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return noPenjualan;
    }
    
    public List<ModelBarang> setFieldBrg(ModelBarang modelBarang) {
        List<ModelBarang> listDetail = new ArrayList<>();
        String query = "SELECT Kode_Barang, Nama_Barang, Satuan, Harga_Jual, Stok FROM barang WHERE Nomor_Barcode='"+modelBarang.getNomor_Barcode()+"' ";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                ModelBarang barang = new ModelBarang();
                barang.setKode_Barang(rst.getString("Kode_Barang"));
                barang.setNama_Barang(rst.getString("Nama_Barang"));
                barang.setSatuan(rst.getString("Satuan"));
                barang.setHarga_Jual(rst.getInt("Harga_Jual"));
                barang.setStok(rst.getInt("Stok"));
                listDetail.add(barang);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return listDetail;
    }
}

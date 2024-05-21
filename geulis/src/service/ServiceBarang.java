/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import model.ModelJenisBarang;
import java.sql.Connection;
import model.ModelBarang;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author User
 */
public class ServiceBarang {
    private Connection connection;

    public ServiceBarang() {
       connection = Koneksi.getConnection();
    }
    
    public void loadData(DefaultTableModel model) {
        String query = "SELECT * FROM barang barang JOIN jenis_barang jenis_barang ON barang.Kode_Jenis=jenis_barang.Kode_Jenis";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();            
            while(rst.next()) {
                String kodeBarang = rst.getString("Kode_Barang");
                String noBarcode = rst.getString("Nomor_Barcode");
                String kodeJenis = rst.getString("Kode_Jenis");
                String jenis_barang = rst.getString("Nama_Jenis");
                String namaBarang = rst.getString("Nama_Barang");
                String satuan = rst.getString("Satuan");
                int hargaBeli = rst.getInt("Harga_Beli");
                int hargaJual = rst.getInt("Harga_Jual");
                int stok = rst.getInt("Stok");
                model.addRow(new Object[]{kodeBarang, noBarcode, kodeJenis, jenis_barang, namaBarang, satuan, hargaBeli, hargaJual, stok});
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void addData(ModelBarang modelBarang) {
     String query = "INSERT INTO barang (Kode_Barang, Nomor_Barcode, Kode_Jenis, Nama_Barang, Satuan, Harga_Beli, Harga_Jual, Stok) VALUES (?,?,?,?,?,?,?,?)"; 
     try {
         PreparedStatement pst = connection.prepareStatement(query);
         pst.setString(1, modelBarang.getKode_Barang());
         pst.setString(2, modelBarang.getNomor_Barcode());
         pst.setString(3, modelBarang.getKode_Jenis());
         pst.setString(4, modelBarang.getNama_Barang());
         pst.setString(5, modelBarang.getSatuan());
         pst.setInt(6, modelBarang.getHarga_Beli());
         pst.setInt(7, modelBarang.getHarga_Jual());
         pst.setInt(8, modelBarang.getStok());
         pst.executeUpdate();
         JOptionPane.showMessageDialog(null, "Data Barang berhasil ditambahkan");
     } catch(Exception ex) {
         ex.printStackTrace();
     }
    }
    
    public void updateData(ModelBarang modelBarang) {
     String query = "UPDATE barang SET Nomor_Barcode=?, Nama_Barang=?, Satuan=?, Harga_Beli=?, Harga_Jual=?, Stok=? WHERE Kode_Barang=?";
     try {
         PreparedStatement pst = connection.prepareStatement(query);
         pst.setString(1, modelBarang.getNomor_Barcode());
         pst.setString(2, modelBarang.getNama_Barang());
         pst.setString(3, modelBarang.getSatuan());
         pst.setInt(4, modelBarang.getHarga_Beli());
         pst.setInt(5, modelBarang.getHarga_Jual());
         pst.setInt(6, modelBarang.getStok());
         pst.setString(7, modelBarang.getKode_Barang());
         pst.executeUpdate();
         JOptionPane.showMessageDialog(null, "Data Barang berhasil diperbarui");
     } catch(Exception ex) {
         ex.printStackTrace();
     }
    }
    
    public void deleteData(ModelBarang modelBarang) {
     String query = "DELETE FROM barang WHERE Kode_Barang=?";
     try {
         PreparedStatement pst = connection.prepareStatement(query);
         pst.setString(1, modelBarang.getKode_Barang());
         pst.executeUpdate();
         JOptionPane.showMessageDialog(null, "Data Barang berhasil dihapus");
     } catch(Exception ex) {
         ex.printStackTrace();
     }
    }
    
    public String createKodeBarang(ModelJenisBarang modelJenisBarang) {
        String kodeBarang = null;
        String formatJenis = modelJenisBarang.getNamaJenis().substring(0, 3).toUpperCase();
        Date date = new Date();
        String format = new SimpleDateFormat("yyMM").format(date);
        String query = "SELECT RIGHT (Kode_Barang, 3) AS Kode FROM barang WHERE Kode_Barang LIKE '"+formatJenis+"-"+format+"%' ORDER BY Kode_Barang DESC";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                int kode = Integer.parseInt(rst.getString("Kode"));
                kode++;
                kodeBarang = formatJenis + "-" + format + "-" + String.format("%03d", kode);
            } else {
                kodeBarang = formatJenis + "-" + format + "-" +"001";
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
        return kodeBarang;
    }
    
    public String createKodeJenis() {
        String kodeJenis = null;
        String query = "SELECT RIGHT (Kode_Jenis, 3) AS Kode FROM jenis_barang ORDER BY Kode_Jenis DESC";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                int kode = Integer.parseInt(rst.getString("Kode"));
                kode++;
                kodeJenis = "JB-" + String.format("%03d", kode);
            } else {
                kodeJenis = "JB-001";
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
        return kodeJenis;
    }
    
    public String selectKodeJenis(ModelJenisBarang modelJenis) {
        String kodeJenis = null;
        String query = "SELECT Kode_Jenis FROM jenis_barang WHERE Nama_Jenis='"+modelJenis.getNamaJenis()+"' ";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                kodeJenis = rst.getString("Kode_Jenis");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
        return kodeJenis;
    }
    
    public void addJenisBarang(ModelJenisBarang modelJenis) {
        String query = "INSERT INTO jenis_barang (Kode_Jenis, Nama_Jenis) VALUES(?,?)";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelJenis.getKodeJenis());
            pst.setString(2, modelJenis.getNamaJenis());
            pst.executeUpdate();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public List<String> loadDataJenisBarang() {
        List<String> listJenisBarang = new ArrayList<>();
        String query = "SELECT Nama_Jenis FROM jenis_barang";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                String namaJenis = rst.getString("Nama_Jenis");
                listJenisBarang.add(namaJenis);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
        return listJenisBarang;
    }
    
    public boolean validationAddJenis(ModelJenisBarang modelJenis) {
        boolean valid = false;
        String query = "SELECT * FROM jenis_barang WHERE nama_jenis='"+modelJenis.getNamaJenis()+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                JOptionPane.showMessageDialog(null, "Jenis Barang Sudah Tersedia");
            } else {
                valid = true;
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return valid;
    }
    
    public boolean validationDelete(ModelBarang modelBarang) {
        boolean valid = false;
        String query1 = "SELECT Kode_Barang FROM detail_penjualan WHERE Kode_Barang='"+modelBarang.getKode_Barang()+"' ";
        String query2 = "SELECT Kode_Barang FROM detail_pemesanan WHERE Kode_Barang='"+modelBarang.getKode_Barang()+"' ";
        String query3 = "SELECT Kode_Barang FROM detail_restok WHERE Kode_Barang='"+modelBarang.getKode_Barang()+"' ";
        try {
            PreparedStatement pst1 = connection.prepareStatement(query1);
            ResultSet rst1 = pst1.executeQuery();         
            PreparedStatement pst2 = connection.prepareStatement(query2);
            ResultSet rst2 = pst2.executeQuery();
            PreparedStatement pst3 = connection.prepareStatement(query3);
            ResultSet rst3 = pst3.executeQuery();
            if(rst3.next() || rst2.next() || rst1.next()) {
                JOptionPane.showMessageDialog(null, "Tidak dapat menghapus barang ini\n"
               + "Barang ini pernah digunakan di transaksi", "Peringatan", JOptionPane.WARNING_MESSAGE);
            } else {
                valid = true;
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return valid;
    }
}

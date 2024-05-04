/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import javax.swing.table.DefaultTableModel;
import model.ModelPemesanan;
import model.ModelPengguna;
import model.ModelSupplier;
import swing.StatusType;
/**
 *
 * @author usER
 */
public class ServiceLaporan {
    
    private final LocalDate date = LocalDate.now();
    
    private final String queryPemeriksaan = "SELECT pmn.No_Pemeriksaan,pmn.No_Reservasi ,DATE_FORMAT(pmn.Tanggal_Pemeriksaan, '%d - %M - %Y') AS Tanggal_Pemeriksaan, "
        + "pmn.Deskripsi, pmn.Total, pmn.Bayar, pmn.Kembalian, pmn.Jenis_Pembayaran, pmn.ID_Pasien, "
        + "psn.Nama, pmn.ID_Karyawan, krn.Nama, pmn.ID_Pengguna, pgn.Nama FROM pemeriksaan pmn "
        + "INNER JOIN pasien psn ON pmn.ID_Pasien=psn.ID_Pasien "
        + "INNER JOIN karyawan krn ON pmn.ID_Karyawan=krn.ID_Karyawan "
        + "INNER JOIN pengguna pgn ON pmn.ID_Pengguna=pgn.ID_Pengguna ";
    
    private final String queryPenjualan = "SELECT pjl.No_Penjualan, DATE_FORMAT(pjl.Tanggal, '%d - %M - %Y') AS Tanggal_Penjualan, "
                + "pjl.Total_Penjualan, pjl.Bayar, pjl.Kembali, pjl.Jenis_Pembayaran, pjl.ID_Pengguna, "
                + "pgn.Nama FROM penjualan pjl INNER JOIN pengguna pgn ON pjl.ID_Pengguna=pgn.ID_Pengguna ";
    
    private final String queryPemesanan = "SELECT pmsn.No_Pemesanan, DATE_FORMAT(pmsn.Tanggal_Pemesanan, '%d - %M - %Y') AS Tanggal_Pemesanan, "
        + "pmsn.Status_Pemesanan, pmsn.Total_Pemesanan, pmsn.Bayar, pmsn.Kembali, pmsn.Jenis_Pembayaran, "
        + "pmsn.ID_Supplier, slr.Nama, pmsn.ID_Pengguna, pgn.Nama FROM pemesanan pmsn "
        + "INNER JOIN supplier slr ON pmsn.ID_Supplier=slr.ID_Supplier "
        + "INNER JOIN pengguna pgn ON pmsn.ID_Pengguna=pgn.ID_Pengguna ";
    
    private final String queryPengeluaran = "SELECT plrn.No_Pengeluaran, plrn.ID_Pengguna, pg.Nama, "
                + "DATE_FORMAT(plrn.Tanggal_Pengeluaran, '%d - %M - %Y') AS Tanggal_Pengeluaran, "
                + "plrn.Total_Pengeluaran, plrn.Deskripsi FROM pengeluaran plrn INNER JOIN pengguna pg "
                + "ON plrn.ID_Pengguna=pg.ID_Pengguna ";
    
    private Connection connection;
    public ServiceLaporan() {
        connection = Koneksi.getConnection();
    }
//  Pemeriksaan
    private void addRowTablePemeriksaan(ResultSet rst, DefaultTableModel tabmodel) throws Exception{
        String noPemeriksaan = rst.getString("No_Pemeriksaan");
        String noReservasi = rst.getString("No_Reservasi");
        String idPasien = rst.getString("ID_Pasien");
        String namaPasien = rst.getString("psn.Nama");
        String idKaryawan = rst.getString("ID_Karyawan");
        String tgl = rst.getString("Tanggal_Pemeriksaan");
        int total = rst.getInt("Total");
        String deskripsi = rst.getString("Deskripsi");
        double bayar = rst.getDouble("Bayar");
        double kembalian = rst.getDouble("Kembalian");
        String jenisPembayaran = rst.getString("Jenis_Pembayaran");
        String idPengguna = rst.getString("ID_Pengguna");
        String namaPengguna = rst.getString("pgn.Nama");
        tabmodel.addRow(
        new Object[]{noPemeriksaan, noReservasi, idPasien, namaPasien, 
        idKaryawan, tgl, total, deskripsi, bayar, kembalian, 
        jenisPembayaran, idPengguna, namaPengguna});
    }
    
//  Penjualan
    private void addRowTablePenjualan(ResultSet rst, DefaultTableModel tabmodel) throws Exception{
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
    
    //    Pemesanan
    private void addRowTablePemesanan(ModelPengguna modelPengguna, ModelSupplier modelSupplier, ResultSet rst, DefaultTableModel tabmodel) throws Exception{
        String noPemeriksaan = rst.getString("No_Pemesanan");
        String idSupplier = rst.getString("ID_Supplier");
        String namaSupplier = rst.getString("slr.Nama");
        modelSupplier.setIdSupplier(idSupplier);
        modelSupplier.setNamaSupplier(namaSupplier);
        String tgl = rst.getString("Tanggal_Pemesanan");
        int total = rst.getInt("Total_Pemesanan");
        double bayar = rst.getDouble("Bayar");
        double kembali = rst.getDouble("Kembali");
        String jenisPembayaran = rst.getString("Jenis_Pembayaran");
        String status = rst.getString("Status_Pemesanan");
        String idPengguna = rst.getString("ID_Pengguna");
        String namaPengguna = rst.getString("pgn.Nama");
        modelPengguna.setIdpengguna(idPengguna);
        modelPengguna.setNama(namaPengguna);

        StatusType type = StatusType.Send;
        if(status.equals("Diterima")) {
            type = StatusType.Finish;
        }

        tabmodel.addRow(new ModelPemesanan(
                noPemeriksaan, tgl, type, status, 
                total, bayar, kembali, jenisPembayaran, modelSupplier, 
                modelPengguna).toRowTable());
    }
    
//    Pengeluaran
    private void addRowTablePengeluaran(ResultSet rst, DefaultTableModel tabmodel) throws Exception{
        String noPengeluaran = rst.getString("No_Pengeluaran");
        String idPengguna = rst.getString("ID_Pengguna");
        String namaPengguna = rst.getString("Nama");
        String tgl = rst.getString("Tanggal_Pengeluaran");
        int total = rst.getInt("Total_Pengeluaran");
        String deskripsi = rst.getString("Deskripsi");
        tabmodel.addRow(new Object[]{noPengeluaran, idPengguna, namaPengguna, tgl, total, deskripsi});
    }
    
    public void loadAll(DefaultTableModel tabmodel, String slide) {
        String query = queryPemeriksaan.concat("WHERE YEAR(Tanggal_Pemeriksaan)="+date.getYear()+" AND MONTH(Tanggal_Pemeriksaan)="+date.getMonthValue()+" ORDER BY No_Pemeriksaan DESC");
        switch(slide) {
            case "Penjualan":
                query = queryPenjualan.concat("WHERE YEAR(Tanggal)="+date.getYear()+" AND MONTH(Tanggal)="+date.getMonthValue()+" ORDER BY No_Penjualan DESC");
                break;
            case "Pemesanan":
                query = queryPemesanan.concat("WHERE YEAR(Tanggal_Pemesanan)="+date.getYear()+" AND MONTH(Tanggal_Pemesanan)="+date.getMonthValue()+" ORDER BY No_Pemesanan DESC");
                break;
            case "Pengeluaran":
                query = queryPengeluaran.concat("WHERE YEAR(Tanggal_Pengeluaran)="+date.getYear()+" AND MONTH(Tanggal_Pengeluaran)="+date.getMonthValue()+" ORDER BY No_Pengeluaran DESC");
                break;
        }
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {   
                switch(slide) {
                case "Pemeriksaan":
                    addRowTablePemeriksaan(rst, tabmodel);  
                    break;
                case "Penjualan":
                    addRowTablePenjualan(rst, tabmodel);
                    break;
                case "Pemesanan":
                    ModelSupplier modelSupplier = new ModelSupplier();
                    ModelPengguna modelPengguna = new ModelPengguna();
                    addRowTablePemesanan(modelPengguna, modelSupplier, rst, tabmodel);
                    break;
                case "Pengeluaran":
                    addRowTablePengeluaran(rst, tabmodel);
                    break;
                }
            
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
        
    public void loadBetween(String fromDate, String toDate, DefaultTableModel tabmodel, String slide) {
        String query = queryPemeriksaan.concat("WHERE Tanggal_Pemeriksaan BETWEEN '"+fromDate+"' AND '"+toDate+"' ORDER BY No_Pemeriksaan DESC");
        switch(slide) {
                case "Penjualan":
                    query = queryPenjualan.concat("WHERE Tanggal BETWEEN '"+fromDate+"' AND '"+toDate+"' ORDER BY No_Penjualan DESC");
                    break;
                case "Pemesanan":
                    query = queryPemesanan.concat("WHERE Tanggal_Pemesanan BETWEEN '"+fromDate+"' AND '"+toDate+"' ORDER BY No_Pemesanan DESC");
                    break;
                case "Pengeluaran":
                    query = queryPengeluaran.concat("WHERE Tanggal_Pengeluaran BETWEEN '"+fromDate+"' AND '"+toDate+"' ORDER BY No_Pengeluaran DESC");
                    break;
                }
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                switch(slide) {
                case "Pemeriksaan":
                    addRowTablePemeriksaan(rst, tabmodel);  
                    break;
                case "Penjualan":
                    addRowTablePenjualan(rst, tabmodel);
                    break;
                case "Pemesanan":
                    ModelSupplier modelSupplier = new ModelSupplier();
                    ModelPengguna modelPengguna = new ModelPengguna();
                    addRowTablePemesanan(modelPengguna, modelSupplier, rst, tabmodel);
                    break;
                case "Pengeluaran":
                    addRowTablePengeluaran(rst, tabmodel);
                    break;
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

}

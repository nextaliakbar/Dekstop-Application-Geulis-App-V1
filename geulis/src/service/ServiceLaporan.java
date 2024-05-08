/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import control.FieldsLaporanPemeriksaan;
import control.FieldsLaporanPemesanan;
import control.FieldsLaporanPenjualan;
import control.FieldsLaporanganPengeluaran;
import control.FieldsPemeriksaan;
import control.FieldsPengeluaran;
import control.FieldsPenjualan;
import control.LaporanPemeriksaan;
import control.LaporanPenjualan;
import control.ParamLaporan;
import control.Report;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.ModelPemesanan;
import model.ModelPengeluaran;
import model.ModelPengguna;
import model.ModelSupplier;
import swing.StatusType;
import swing.Table;
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
        tabmodel.addRow(new LaporanPemeriksaan(noPemeriksaan, noReservasi, idPasien, namaPasien, idKaryawan, 
        tgl, total, deskripsi, bayar, kembalian, jenisPembayaran, idPengguna, namaPengguna).toRowTable());
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
        tabmodel.addRow(new LaporanPenjualan(noPenjualan, idPengguna, namaPengguna, tglPenjualan, total, bayar, kembali, jenisPembayaran).toRowTable());
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
                modelPengguna).toRowTable2());
    }
    
//    Pengeluaran
    private void addRowTablePengeluaran(ResultSet rst, DefaultTableModel tabmodel) throws Exception{
        ModelPengguna modelPengguna = new ModelPengguna();
        String noPengeluaran = rst.getString("No_Pengeluaran");
        String idPengguna = rst.getString("ID_Pengguna");
        String namaPengguna = rst.getString("Nama");
        modelPengguna.setIdpengguna(idPengguna);
        modelPengguna.setNama(namaPengguna);
        String tgl = rst.getString("Tanggal_Pengeluaran");
        int total = rst.getInt("Total_Pengeluaran");
        String deskripsi = rst.getString("Deskripsi");
        tabmodel.addRow(new ModelPengeluaran(noPengeluaran, tgl, total, deskripsi, modelPengguna).toRowTable());
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
    
    public void printReportPemeriksaan(JTable table, JTextField txtTgl, JLabel lbTotal) {
        try {
            List<FieldsLaporanPemeriksaan> fields = new ArrayList<>();
            String query = "SELECT tdk.Nama_Tindakan, tdk.Biaya_Tindakan, "
                + "detPem.Potongan, detPem.Subtotal FROM detail_pemeriksaan detPem INNER JOIN "
                + "tindakan tdk ON detPem.Kode_Tindakan=tdk.Kode_Tindakan WHERE No_Pemeriksaan=?";
            PreparedStatement pst = connection.prepareStatement(query);
            for(int a = 0 ; a < table.getRowCount(); a++) {
                LaporanPemeriksaan pemeriksaan = (LaporanPemeriksaan) table.getValueAt(a, 0);
                pst.setString(1, pemeriksaan.getNoPemeriksaan());
                ResultSet rst = pst.executeQuery();
                List<FieldsPemeriksaan> detail = new ArrayList<>();
                while(rst.next()) {
                    String nama = rst.getString("Nama_Tindakan");
                    int harga = rst.getInt("Biaya_Tindakan");
                    int potongan = rst.getInt("Potongan");
                    int totalHarga = rst.getInt("Subtotal");
                    detail.add(new FieldsPemeriksaan(nama, harga, potongan, totalHarga));
                }
                rst.close();
                fields.add(new FieldsLaporanPemeriksaan(a+1, pemeriksaan.getNoPemeriksaan(), pemeriksaan.getNoReservasi(), 
                        pemeriksaan.getNamaPasien(), pemeriksaan.getIdKaryawan(), pemeriksaan.getTglPemeriksaan(), 
                        pemeriksaan.getTotal(), pemeriksaan.getBayar(), pemeriksaan.getKembali(), detail));
            }
            pst.close();
            ParamLaporan paramater = new ParamLaporan();
            paramater.setRentang(txtTgl.getText());
            paramater.setTotalKeseluruhan("Rp "+lbTotal.getText());
            paramater.setFieldsPemeriksaan(fields);
            Report.getInstance().printLaporanPemeriksaan(paramater);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void printReportPenjualan(JTable table, JTextField txtTgl, JLabel lbTotal) {
        try {
            List<FieldsLaporanPenjualan> fields = new ArrayList<>();
            String query = "SELECT brg.Nama_Barang, brg.Harga_Jual, "
                + "dtl.Jumlah, dtl.Subtotal FROM detail_penjualan dtl JOIN barang brg "
                + "ON dtl.Kode_Barang=brg.Kode_Barang WHERE No_Penjualan=?";
            String totalCount = "SELECT SUM(Jumlah) AS Jumlah FROM detail_penjualan "
                    + "WHERE No_Penjualan=?";
            
            int totalQty = 0;
            int totalJumlah = 0;
            PreparedStatement pst = connection.prepareStatement(query);
            PreparedStatement pstCount = connection.prepareStatement(totalCount);
            for(int a = 0; a < table.getRowCount(); a++) {
                LaporanPenjualan penjualan = (LaporanPenjualan) table.getValueAt(a, 0);
                pst.setString(1, penjualan.getNoPenjualan());
                ResultSet rst = pst.executeQuery();
                List<FieldsPenjualan> detail = new ArrayList<>();
                while(rst.next()) {
                    String namaBrg = rst.getString("Nama_Barang");
                    double hrgJual = rst.getDouble("Harga_Jual");
                    int jumlah = rst.getInt("Jumlah");
                    double subtotal = rst.getDouble("Subtotal");
                    detail.add(new FieldsPenjualan(namaBrg, hrgJual, jumlah, subtotal));
                }
                pstCount.setString(1, penjualan.getNoPenjualan());
                ResultSet rstCount = pstCount.executeQuery();
                while(rstCount.next()) {
                    totalJumlah = rstCount.getInt("Jumlah");
                    totalQty+=totalJumlah;
                }
                
                rst.close();
                rstCount.close();
                fields.add(new FieldsLaporanPenjualan(a+1, penjualan.getNoPenjualan(), penjualan.getKasir(), penjualan.getTglPenjualan(), 
                totalJumlah, penjualan.getTotal(), penjualan.getBayar(), penjualan.getKembali(), detail));
            }
            pst.close();
            ParamLaporan paramater = new ParamLaporan();
            paramater.setRentang(txtTgl.getText());
            paramater.setTotalQty(String.valueOf(totalQty));
            paramater.setTotalKeseluruhan("Rp "+lbTotal.getText());
            paramater.setFieldsPenjualan(fields);
            Report.getInstance().printLaporanPenjualan(paramater);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void printReportPemesanan(Table table, JTextField txtTgl, JLabel lbTotal) {
        try {
            List<FieldsLaporanPemesanan> fields = new ArrayList<>();
            String query = "SELECT brg.Nama_Barang, brg.Satuan, brg.Harga_Beli, dtl.Jumlah, dtl.Subtotal "
                    + "FROM detail_pemesanan dtl INNER JOIN barang brg ON dtl.Kode_Barang=brg.Kode_Barang "
                    + "WHERE No_Pemesanan=?";
            String totalCount = "SELECT SUM(Jumlah) AS Jumlah FROM detail_pemesanan WHERE No_Pemesanan=?";
            PreparedStatement pst = connection.prepareStatement(query);
            PreparedStatement pstCount = connection.prepareStatement(totalCount);
            int totalQty = 0;
            int totalJumlah = 0;
            for(int a = 0; a < table.getRowCount(); a++) {
                ModelPemesanan pemesanan = (ModelPemesanan) table.getValueAt(a, 0);
                pst.setString(1, pemesanan.getNoPemesanan());
                ResultSet rst = pst.executeQuery();
                List<FieldsPenjualan> detail = new ArrayList<>();
                while(rst.next()) {
                    String namaBrg = rst.getString("Nama_Barang");
                    double hargaBeli = rst.getDouble("Harga_Beli");
                    int jumlah = rst.getInt("Jumlah");
                    double subtotal = rst.getDouble("Subtotal");
                    detail.add(new FieldsPenjualan(namaBrg, hargaBeli, jumlah, subtotal));
                }
                pstCount.setString(1, pemesanan.getNoPemesanan());
                ResultSet rstCount = pstCount.executeQuery();
                while(rstCount.next()) {
                    totalJumlah = rstCount.getInt("Jumlah");
                    totalQty+=totalJumlah;
                }
                rst.close();
                rstCount.close();
                fields.add(new FieldsLaporanPemesanan(a+1, pemesanan.getNoPemesanan()+" / "+pemesanan.getStatusPemesanan(), 
                        pemesanan.getModelSupplier().getNamaSupplier(), 
                        pemesanan.getTglPemesanan(), totalJumlah, pemesanan.getTotalPemesanan(), pemesanan.getBayar(), 
                        pemesanan.getKembali(), detail));
            }
                pst.close();
                ParamLaporan paramater = new ParamLaporan();
                paramater.setRentang(txtTgl.getText());
                paramater.setTotalQty(String.valueOf(totalQty));
                paramater.setTotalKeseluruhan("Rp "+lbTotal.getText());
                paramater.setFieldsPemesanan(fields);
                Report.getInstance().printLaporanPemesanan(paramater);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void printReportPengeluaran(JTable table, JTextField txtTgl, JLabel lbTotal) {
        try {
            List<FieldsLaporanganPengeluaran> fields = new ArrayList<>();
            String query = "SELECT jns.Nama_Jenis, "
                + "dtl.Detail_Jenis, dtl.Subtotal FROM detail_pengeluaran dtl "
                + "JOIN jenis_pengeluaran jns ON dtl.No_Jenis=jns.No_Jenis "
                + "WHERE No_Pengeluaran=?";
            PreparedStatement pst = connection.prepareStatement(query);
            for(int a = 0; a < table.getRowCount(); a++) {
                ModelPengeluaran pengeluaran = (ModelPengeluaran) table.getValueAt(a, 0);
                pst.setString(1, pengeluaran.getNoPengeluaran());
                ResultSet rst = pst.executeQuery();
                List<FieldsPengeluaran> detail = new ArrayList<>();
                while(rst.next()) {
                    String namaJenis = rst.getString("Nama_Jenis");
                    String detailJenis = rst.getString("Detail_Jenis");
                    double subtotal = rst.getDouble("Subtotal");
                    detail.add(new FieldsPengeluaran(namaJenis, detailJenis, subtotal));
                }
                rst.close();
                fields.add(new FieldsLaporanganPengeluaran(a+1, pengeluaran.getNoPengeluaran(), pengeluaran.getModelPengguna().getNama(), 
                        pengeluaran.getTglPengeluaran(), pengeluaran.getTotal(), detail));
            }
            pst.close();
            ParamLaporan paramater = new ParamLaporan();
            paramater.setRentang(txtTgl.getText());
            paramater.setTotalKeseluruhan("Rp "+lbTotal.getText());
            paramater.setFieldsPengeluaran(fields);
            Report.getInstance().printLaporanPengeluaran(paramater);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import component.Chart;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import model.ModelChart;
import model.ModelDashboard;
import model.ModelLastReservasi;
import swing.StatusType;
import swing.Table;
/**
 *
 * @author usER
 */
public class ServiceDashboard {
    private Connection connection;
    
    public ServiceDashboard() {
        connection = Koneksi.getConnection();
    }
    
    public double revenuePemeriksaan(ModelDashboard modelDashboard) {
        double revenue = 0;
        String query= "SELECT SUM(total) AS Total FROM pemeriksaan WHERE Tanggal_Pemeriksaan BETWEEN '"+modelDashboard.getFromDate()+"' AND '"+modelDashboard.getToDate()+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                revenue = rst.getInt("Total");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
        return revenue;
                        
    }
    
    public double totalPenjualan(ModelDashboard modelDashboard) {
        double total = 0;
        String query= "SELECT SUM(Total_Penjualan) AS Total FROM penjualan WHERE Tanggal BETWEEN '"+modelDashboard.getFromDate()+"' AND '"+modelDashboard.getToDate()+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                total = rst.getInt("Total");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
        return total;
    }
    
    public double keuntunganPenjualan(ModelDashboard modelDashboard) {
        double keuntungan = 0;
        String query = "SELECT dtl.No_Penjualan, dtl.Kode_Barang, bg.Harga_Beli, bg.Harga_Jual, dtl.Jumlah, pjn.Tanggal FROM detail_penjualan dtl "
                + "INNER JOIN barang bg ON dtl.Kode_Barang=bg.Kode_Barang INNER JOIN penjualan pjn ON dtl.No_Penjualan=pjn.No_Penjualan "
                + "WHERE Tanggal BETWEEN '"+modelDashboard.getFromDate()+"' AND '"+modelDashboard.getToDate()+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                double hargaBeli = rst.getDouble("Harga_Beli");
                double hargaJual = rst.getDouble("Harga_Jual");
                int jumlah = rst.getInt("Jumlah");
                keuntungan += (jumlah * hargaJual) - (jumlah * hargaBeli);
            }
        } catch(Exception ex) {
            
        }
        return keuntungan;
    }
    
    public double pengeluaran(ModelDashboard modelDashboard) {
        double pengeluaran = 0;
        String query = "SELECT SUM(Total_Pengeluaran) AS Total FROM pengeluaran WHERE Tanggal_Pengeluaran BETWEEN '"+modelDashboard.getFromDate()+"' AND '"+modelDashboard.getToDate()+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                pengeluaran = rst.getDouble("Total");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
        return pengeluaran;
    }
    
    public void lastReseravsi(Table table) {
        String query = "SELECT rsv.No_Reservasi, rsv.ID_Pasien, psn.Nama, psn.Jenis_Kelamin, rsv.Tanggal_Kedatangan, "
                + "rsv.Jam_Kedatangan, rsv.Status_Reservasi FROM reservasi rsv INNER JOIN pasien psn "
                + "ON rsv.ID_Pasien=psn.ID_Pasien ORDER BY CONCAT(Tanggal_Kedatangan, ' ', Jam_Kedatangan) DESC";
        
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                String noReservasi = rst.getString("No_Reservasi");
                String nama = rst.getString("Nama");
                String jenisKelamin = rst.getString("Jenis_Kelamin");
                String tglKedatangan = rst.getString("Tanggal_Kedatangan");
                String jamKedatangan = rst.getString("Jam_Kedatangan");
                String status = rst.getString("Status_Reservasi");
                String sourceImage = "/image/businesswoman.png";
                
                if(jenisKelamin.equals("Laki-laki")) {
                    sourceImage = "/image/office-worker.png";
                }
                
                StatusType type;
                if(status.equals("Menunggu")) {
                    type = StatusType.Wait;
                } else if(status.equals("Selesai")) {
                    type = StatusType.Finish;
                } else {
                    type = StatusType.Cancel;
                }
                
                table.addRow(new ModelLastReservasi(noReservasi,new ImageIcon(getClass().getResource(sourceImage)), nama, tglKedatangan.concat(" / " + jamKedatangan),type).toRowTable());
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private List<Double> getRevenues(List<Integer> months, String year) {
        List<Double> revenues = new ArrayList<>();
        for(int month : months) {
        String query = "SELECT SUM(Total) AS Revenues FROM pemeriksaan WHERE MONTH(Tanggal_Pemeriksaan)='"+month+"' AND YEAR(Tanggal_Pemeriksaan)='"+year+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                revenues.add(rst.getDouble("Revenues"));
            } else {
                revenues.add((double)0);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
      }
      
        return revenues;
    }
    
    private List<Double> getTotalSells(List<Integer> months, String year) {
        List<Double> totalSells = new ArrayList<>();
        for(int month : months) {
        String query = "SELECT SUM(Total_Penjualan) AS TotalSells FROM penjualan WHERE MONTH(Tanggal)='"+month+"' AND YEAR(Tanggal)='"+year+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                totalSells.add(rst.getDouble("TotalSells"));      
            } else {
                totalSells.add((double)0);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
      }
      
        return totalSells;
    }
        
    private List<Double> getProfits(List<Integer> months, String year) {
        List<Double> profits = new ArrayList<>();
        for(int month : months) {
            String query = "SELECT dtl.No_Penjualan, dtl.Kode_Barang, bg.Harga_Beli, bg.Harga_Jual, dtl.Jumlah, "
                    + "pjn.Tanggal FROM detail_penjualan dtl INNER JOIN barang bg ON dtl.Kode_Barang=bg.Kode_Barang "
                    + "INNER JOIN penjualan pjn ON dtl.No_Penjualan=pjn.No_Penjualan WHERE MONTH(Tanggal)='"+month+"' AND YEAR(Tanggal)='"+year+"'";
            try {
                PreparedStatement pst = connection.prepareStatement(query);
                ResultSet rst = pst.executeQuery();
                if(rst.next()) {
                    double hargaBeli = rst.getDouble("Harga_Beli");
                    double hargaJual = rst.getDouble("Harga_Jual");
                    int jumlah = rst.getInt("Jumlah");
                    double profit = (hargaJual * jumlah) - (hargaBeli * jumlah);
                    profits.add(profit);
                } else {
                    profits.add((double)0);
                }
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
        
        return profits;
    }
    
    private List<Double> getExpenditures(List<Integer> months, String year) {
        List<Double> expenditures = new ArrayList<>();
        for(int month : months) {
            String query = "SELECT SUM(Total_Pengeluaran) AS Expenditures FROM pengeluaran WHERE MONTH(Tanggal_Pengeluaran)='"+month+"' AND YEAR(Tanggal_Pengeluaran)='"+year+"'";
            try {
                PreparedStatement pst = connection.prepareStatement(query);
                ResultSet rst = pst.executeQuery();
                if(rst.next()) {
                    expenditures.add(rst.getDouble("Expenditures"));
                } else {
                    expenditures.add((double)0);
                }
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
        
        return expenditures;
    }
    
    
    public void chartDiagram(List<Integer> months, Chart chart, String year) {
        List<Double> revenues = getRevenues(months, year);
        List<Double> totalSells = getTotalSells(months, year);
        List<Double> profits = getProfits(months, year);
        List<Double> expenditures = getExpenditures(months, year);
        for(int a = 0; a < months.size(); a++) {
            Month month = Month.of(a + 1);
            String bulan = styleString(month.toString());
            double[] values = new double[4];
            values[0] = revenues.get(a);
            values[1] = totalSells.get(a);
            values[2] = profits.get(a);
            values[3] = expenditures.get(a);
            chart.addData(new ModelChart(bulan, values));
        }
    }
    
    private String styleString(String str) {
        String firstStr = str.substring(0, 1);
        String otherStr =  str.substring(1);
        return firstStr + otherStr.toLowerCase();
    }
}

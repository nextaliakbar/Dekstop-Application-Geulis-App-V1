/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

/**
 *
 * @author usER
 */
public class LaporanPenjualan {

    public LaporanPenjualan(String noPenjualan, String idPengguna, String kasir, String tglPenjualan, int total, double bayar, double kembali, String jenisPembayaran) {
        this.noPenjualan = noPenjualan;
        this.idPengguna = idPengguna;
        this.kasir = kasir;
        this.tglPenjualan = tglPenjualan;
        this.total = total;
        this.bayar = bayar;
        this.kembali = kembali;
        this.jenisPembayaran = jenisPembayaran;
    }

    public LaporanPenjualan() {
    }
    
    private String noPenjualan;
    private String idPengguna;
    private String kasir;
    private String tglPenjualan;
    private int total;
    private double bayar;
    private double kembali;
    private String jenisPembayaran;

    public String getNoPenjualan() {
        return noPenjualan;
    }

    public String getIdPengguna() {
        return idPengguna;
    }

    public String getKasir() {
        return kasir;
    }

    public String getTglPenjualan() {
        return tglPenjualan;
    }

    public int getTotal() {
        return total;
    }

    public double getBayar() {
        return bayar;
    }

    public double getKembali() {
        return kembali;
    }

    public String getJenisPembayaran() {
        return jenisPembayaran;
    }
    
    public Object[] toRowTable() {
        return new Object[]{this, noPenjualan, idPengguna, kasir, tglPenjualan, total, bayar, kembali, jenisPembayaran};
    }
}

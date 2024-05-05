/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

/**
 *
 * @author usER
 */
public class LaporanPemeriksaan {

    public LaporanPemeriksaan(String noPemeriksaan, String noReservasi, String idPasien, String namaPasien, 
    String idKaryawan, String tglPemeriksaan, int total, String deskripsi, double bayar, double kembali, 
    String jenisPembayaran, String idPengguna, String namaPengguna) {
        this.noPemeriksaan = noPemeriksaan;
        this.noReservasi = noReservasi;
        this.idPasien = idPasien;
        this.namaPasien = namaPasien;
        this.idKaryawan = idKaryawan;
        this.tglPemeriksaan = tglPemeriksaan;
        this.total = total;
        this.deskripsi = deskripsi;
        this.bayar = bayar;
        this.kembali = kembali;
        this.jenisPembayaran = jenisPembayaran;
        this.idPengguna = idPengguna;
        this.namaPengguna = namaPengguna;
    }

    public LaporanPemeriksaan() {
    }
    
    private String noPemeriksaan;
    private String noReservasi;
    private String idPasien;
    private String namaPasien;
    private String idKaryawan;
    private String tglPemeriksaan;
    private int total;
    private String deskripsi;
    private double bayar;
    private double kembali;
    private String jenisPembayaran;
    private String idPengguna;
    private String namaPengguna;

    public String getNoPemeriksaan() {
        return noPemeriksaan;
    }

    public String getNoReservasi() {
        return noReservasi;
    }

    public String getIdPasien() {
        return idPasien;
    }

    public String getNamaPasien() {
        return namaPasien;
    }

    public String getIdKaryawan() {
        return idKaryawan;
    }

    public String getTglPemeriksaan() {
        return tglPemeriksaan;
    }

    public int getTotal() {
        return total;
    }

    public String getDeskripsi() {
        return deskripsi;
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

    public String getIdPengguna() {
        return idPengguna;
    }

    public String getNamaPengguna() {
        return namaPengguna;
    }
    
    public Object[] toRowTable() {
        return new Object[]{this, noPemeriksaan, noReservasi, idPasien, namaPasien, 
        idKaryawan, tglPemeriksaan, total, deskripsi, bayar, kembali, jenisPembayaran,
        idPengguna, namaPengguna};
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.util.List;

/**
 *
 * @author usER
 */
public class FieldsLaporanPenjualan {

    public FieldsLaporanPenjualan(int no, String noPenjualan, String kasir, String tgl, int totalJumlah, int totalPenjualan, double bayar, double kembali, List<FieldsPenjualan> detail) {
        this.no = no;
        this.noPenjualan = noPenjualan;
        this.kasir = kasir;
        this.tgl = tgl;
        this.totalJumlah = totalJumlah;
        this.totalPenjualan = totalPenjualan;
        this.bayar = bayar;
        this.kembali = kembali;
        this.detail = detail;
    }

    public FieldsLaporanPenjualan() {
    }
    
    private int no;
    private String noPenjualan;
    private String kasir;
    private String tgl;
    private int totalJumlah;
    private int totalPenjualan;
    private double bayar;
    private double kembali;
    private List<FieldsPenjualan> detail;

    public int getNo() {
        return no;
    }

    public String getNoPenjualan() {
        return noPenjualan;
    }

    public String getKasir() {
        return kasir;
    }

    public String getTgl() {
        return tgl;
    }

    public int getTotalJumlah() {
        return totalJumlah;
    }

    public int getTotalPenjualan() {
        return totalPenjualan;
    }

    public double getBayar() {
        return bayar;
    }

    public double getKembali() {
        return kembali;
    }

    public List<FieldsPenjualan> getDetail() {
        return detail;
    }
}

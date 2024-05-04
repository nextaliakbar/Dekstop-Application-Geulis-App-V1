/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

/**
 *
 * @author usER
 */
public class Penjualan {

    public Penjualan(String kodeBrg, String namaBrg, String satuan, double hargaJual, int jumlah, double subtotal) {
        this.kodeBrg = kodeBrg;
        this.namaBrg = namaBrg;
        this.satuan = satuan;
        this.hargaJual = hargaJual;
        this.jumlah = jumlah;
        this.subtotal = subtotal;
    }

    public Penjualan() {
    }
    
    private String kodeBrg;
    private String namaBrg;
    private String satuan;
    private double hargaJual;
    private int jumlah;
    private double subtotal;

    public String getKodeBrg() {
        return kodeBrg;
    }

    public void setKodeBrg(String kodeBrg) {
        this.kodeBrg = kodeBrg;
    }

    public String getNamaBrg() {
        return namaBrg;
    }

    public void setNamaBrg(String namaBrg) {
        this.namaBrg = namaBrg;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public double getHargaJual() {
        return hargaJual;
    }

    public void setHargaJual(double hargaJual) {
        this.hargaJual = hargaJual;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    
    public Object[] toRowTable() {
        return new Object[]{this, kodeBrg, namaBrg, satuan, hargaJual, jumlah, subtotal};
    }
}

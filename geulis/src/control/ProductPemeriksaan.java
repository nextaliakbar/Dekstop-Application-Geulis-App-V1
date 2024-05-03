/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

/**
 *
 * @author usER
 */
public class ProductPemeriksaan {

    public ProductPemeriksaan(String kodeTindakan, String namaTindakan, int harga, int potongan, int totalHarga) {
        this.kodeTindakan = kodeTindakan;
        this.namaTindakan = namaTindakan;
        this.harga = harga;
        this.potongan = potongan;
        this.totalHarga = totalHarga;
    }

    public ProductPemeriksaan() {
    }
    
    
    private String kodeTindakan;
    private String namaTindakan;
    private int harga;
    private int potongan;
    private int totalHarga;

    public String getKodeTindakan() {
        return kodeTindakan;
    }

    public void setKodeTindakan(String kodeTindakan) {
        this.kodeTindakan = kodeTindakan;
    }

    public String getNamaTindakan() {
        return namaTindakan;
    }

    public void setNamaTindakan(String namaTindakan) {
        this.namaTindakan = namaTindakan;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getPotongan() {
        return potongan;
    }

    public void setPotongan(int potongan) {
        this.potongan = potongan;
    }

    public int getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(int totalHarga) {
        this.totalHarga = totalHarga;
    }
    
    public Object[] toTableRow() {
        return new Object[]{this, kodeTindakan, namaTindakan, harga, potongan, totalHarga};
    }
}

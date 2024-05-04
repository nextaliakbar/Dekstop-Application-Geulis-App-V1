/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

/**
 *
 * @author usER
 */
public class Pemeriksaan {

    public Pemeriksaan(String kodeTindakan, String namaTindakan, int harga, int potongan, int totalHarga) {
        this.kodeTindakan = kodeTindakan;
        this.namaTindakan = namaTindakan;
        this.harga = harga;
        this.potongan = potongan;
        this.totalHarga = totalHarga;
    }

    public Pemeriksaan() {
    }
    
    
    private String kodeTindakan;
    private String namaTindakan;
    private int harga;
    private int potongan;
    private int totalHarga;

    public String getKodeTindakan() {
        return kodeTindakan;
    }

    public String getNamaTindakan() {
        return namaTindakan;
    }

    public int getHarga() {
        return harga;
    }

    public int getPotongan() {
        return potongan;
    }

    public int getTotalHarga() {
        return totalHarga;
    }
    
    public Object[] toTableRow() {
        return new Object[]{this, kodeTindakan, namaTindakan, harga, potongan, totalHarga};
    }
}

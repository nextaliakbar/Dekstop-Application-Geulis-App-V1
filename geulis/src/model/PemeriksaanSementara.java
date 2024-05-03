/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author usER
 */
public class PemeriksaanSementara {

    public PemeriksaanSementara(String[] kodeTindakan, int[] potongan, int[] subtotal) {
        this.kodeTindakan = kodeTindakan;
        this.potongan = potongan;
        this.subtotal = subtotal;
    }

    public PemeriksaanSementara() {
    
    }
    
    private String[] kodeTindakan;
    private int[] potongan;
    private int[] subtotal;

    public String[] getKodeTindakan() {
        return kodeTindakan;
    }

    public void setKodeTindakan(String[] kodeTindakan) {
        this.kodeTindakan = kodeTindakan;
    }
    
    public int[] getPotongan() {
        return potongan;
    }

    public void setPotongan(int[] potongan) {
        this.potongan = potongan;
    }

    public int[] getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int[] subtotal) {
        this.subtotal = subtotal;
    }
}

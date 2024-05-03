/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author usER
 */
public class Sementara {

    public Sementara(String[] kodeBrg, int[] jumlah, double[] subtotal) {
        this.kodeBrg = kodeBrg;
        this.jumlah = jumlah;
        this.subtotal = subtotal;
    }

    public Sementara() {
    
    }
    private String[] kodeBrg;
    private int[] jumlah;
    private double[] subtotal;

    public String[] getKodeBrg() {
        return kodeBrg;
    }

    public void setKodeBrg(String[] kodeBrg) {
        this.kodeBrg = kodeBrg;
    }

    public int[] getJumlah() {
        return jumlah;
    }

    public void setJumlah(int[] jumlah) {
        this.jumlah = jumlah;
    }

    public double[] getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double[] subtotal) {
        this.subtotal = subtotal;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author usER
 */
public class PengeluaranSementara {

    public PengeluaranSementara(String[] noJenis, String[] detailJenis, int[] subtotal) {
        this.noJenis = noJenis;
        this.detailJenis = detailJenis;
        this.subtotal = subtotal;
    }

    public PengeluaranSementara() {
    }
    
    private String[] noJenis;
    private String[] detailJenis;
    private int[] subtotal;
    
    public String[] getNoJenis() {
        return noJenis;
    }

    public void setNoJenis(String[] noJenis) {
        this.noJenis = noJenis;
    }

    public String[] getDetailJenis() {
        return detailJenis;
    }

    public void setDetailJenis(String[] detailJenis) {
        this.detailJenis = detailJenis;
    }

    public int[] getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int[] subtotal) {
        this.subtotal = subtotal;
    }
}

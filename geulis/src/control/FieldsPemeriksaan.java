/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

/**
 *
 * @author usER
 */
public class FieldsPemeriksaan {

    public FieldsPemeriksaan(String action, int price, int disc, int subtotal) {
        this.action = action;
        this.price = price;
        this.disc = disc;
        this.subtotal = subtotal;
    }

    public FieldsPemeriksaan() {
    }
    
    
    
    private String action;
    private int price;
    private int disc;
    private int subtotal;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDisc() {
        return disc;
    }

    public void setDisc(int disc) {
        this.disc = disc;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }
}

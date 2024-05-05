/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

/**
 *
 * @author usER
 */
public class FieldsPengeluaran {

    public FieldsPengeluaran(String type, String detail, double subtotal) {
        this.type = type;
        this.detail = detail;
        this.subtotal = subtotal;
    }

    public FieldsPengeluaran() {
    }

    private String type;
    private String detail;
    private double subtotal;

    public String getType() {
        return type;
    }

    public String getDetail() {
        return detail;
    }

    public double getSubtotal() {
        return subtotal;
    }
}

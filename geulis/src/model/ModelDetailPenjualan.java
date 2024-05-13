/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author usER
 */
public class ModelDetailPenjualan {

    public ModelDetailPenjualan(ModelPenjualan modelPenjualan, ModelBarang modelBarang, int jumlah, double subtotal) {
        this.modelPenjualan = modelPenjualan;
        this.modelBarang = modelBarang;
        this.jumlah = jumlah;
        this.subtotal = subtotal;
    }

    public ModelDetailPenjualan() {
    
    }
    
    private ModelPenjualan modelPenjualan;
    private ModelBarang modelBarang;
    private int jumlah;
    private double subtotal;

    public ModelPenjualan getModelPenjualan() {
        return modelPenjualan;
    }

    public void setModelPenjualan(ModelPenjualan modelPenjualan) {
        this.modelPenjualan = modelPenjualan;
    }

    public ModelBarang getModelBarang() {
        return modelBarang;
    }

    public void setModelBarang(ModelBarang modelBarang) {
        this.modelBarang = modelBarang;
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
        return new Object[]{this, modelBarang.getKode_Barang(), modelBarang.getNama_Barang(), 
        modelBarang.getSatuan(), modelBarang.getHarga_Jual(), jumlah, subtotal};
    }
}

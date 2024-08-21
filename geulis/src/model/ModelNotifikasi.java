/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;
/**
 *
 * @author usER
 */
public class ModelNotifikasi {

    public ModelNotifikasi() {
    }
    
    private String idNotifkasi;
    private final LocalDateTime tanggalNotifikasi = LocalDateTime.now();
    private String namaNotifikasi;
    private String deskripsi;
    private String kodeBrg;
    private boolean statusSudahDibaca;

    public String getIdNotifkasi() {
        return idNotifkasi;
    }

    public void setIdNotifkasi(String idNotifkasi) {
        this.idNotifkasi = idNotifkasi;
    }

    public LocalDateTime getTanggalNotifikasi() {
        return tanggalNotifikasi;
    }
    
    public String getNamaNotifikasi() {
        return namaNotifikasi;
    }

    public void setNamaNotifikasi(String namaNotifikasi) {
        this.namaNotifikasi = namaNotifikasi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getKodeBrg() {
        return kodeBrg;
    }

    public void setKodeBrg(String kodeBrg) {
        this.kodeBrg = kodeBrg;
    }

    public boolean isStatusSudahDibaca() {
        return statusSudahDibaca;
    }

    public void setStatusSudahDibaca(boolean statusSudahDibaca) {
        this.statusSudahDibaca = statusSudahDibaca;
    }
       
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author usER
 */
public class ReportPemeriksaan {
    
    private static ReportPemeriksaan instance;
    private JasperReport report;
    
    public static ReportPemeriksaan getInstance() {
        if(instance == null) {
            instance = new ReportPemeriksaan();
        }
        
        return instance;
    }
    
    private ReportPemeriksaan() {
        
    }
    
    public void compileReport() throws JRException{
        report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/ReportPemeriksaan.jrxml"));
    }
    
    public void printReport(ParamPemeriksaan data) throws JRException {
        Map parameter = new HashMap();
        parameter.put("noPmrksn", data.getNoPemeriksaan());
        parameter.put("tanggal", data.getTglPemeriksaan());
        parameter.put("jam", data.getJamPemeriksaan());
        parameter.put("pasien", data.getPasien());
        parameter.put("staff", data.getKaryawan());
        parameter.put("admin", data.getAdmin());
        parameter.put("total", data.getTotal());
        parameter.put("bayar", data.getBayar());
        parameter.put("kembalian", data.getKembalian());
        parameter.put("jenis", data.getJenis());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data.getFields());
        JasperPrint print = JasperFillManager.fillReport(report, parameter,  dataSource);
        viewReport(print);
    }
    
    private void viewReport(JasperPrint print) throws JRException {
    JasperViewer viewer = new JasperViewer(print, false);
    viewer.setTitle("Geulis App");
    viewer.setIconImage(null);
    viewer.setAlwaysOnTop(true);
    viewer.setVisible(true);
    }
}

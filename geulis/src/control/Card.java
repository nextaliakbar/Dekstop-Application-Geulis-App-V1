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
public class Card {
    private static Card instance;
    private JasperReport card;
    
    public static Card getInstance() {
        if(instance == null) {
            instance = new Card();
        }
        
        return instance;
    }
    
    public void compileCard(int index) throws JRException{
        switch(index) {
            case 0: 
                card = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/KartuKaryawan.jrxml"));
                break;
            case 1: 
                card = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/KartuMember.jrxml"));
                break;
        }
    }
    
    public void printCard(Parameter data) throws JRException{
        Map paramater = new HashMap();
        paramater.put("qrcode", data.getQrcode());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data.getFields());
        JasperPrint print = JasperFillManager.fillReport(card, paramater, dataSource);
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

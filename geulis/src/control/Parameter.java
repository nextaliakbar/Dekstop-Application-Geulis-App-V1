/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.io.InputStream;
import java.util.List;

/**
 *
 * @author usER
 */
public class Parameter {

    public Parameter(List<FieldsCard> fields, InputStream qrcode) {
        this.fields = fields;
        this.qrcode = qrcode;
    }

    public Parameter() {
    }
    
    private List<FieldsCard> fields;
    private InputStream qrcode;

    public List<FieldsCard> getFields() {
        return fields;
    }

    public void setFields(List<FieldsCard> fields) {
        this.fields = fields;
    }
    
    public InputStream getQrcode() {
        return this.qrcode;
    }
    
    public void setQrcode(InputStream qrcode) {
        this.qrcode = qrcode;
    }
}

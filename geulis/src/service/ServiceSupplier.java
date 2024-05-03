
package service;
import model.ModelSupplier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import swing.Pagination;
public class ServiceSupplier {
    private Connection connection;

    public ServiceSupplier() {
        connection = Koneksi.getConnection();
    }
    public void loadData(int page, DefaultTableModel tabmodel, Pagination pagination) {
        String sqlCount = "SELECT COUNT(ID_Supplier) AS Jumlah FROM supplier";
        int limit = 15;
        int count = 0;
        
        String query = "SELECT * FROM supplier LIMIT "+(page-1) * limit+","+limit+"";
        
        try {
            PreparedStatement pst = connection.prepareStatement(sqlCount);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                count = rst.getInt("Jumlah");
            }
            
            pst.close();
            rst.close();
            
            pst = connection.prepareStatement(query);
            rst = pst.executeQuery();
            while(rst.next()) {
                String IdSupplier = rst.getString("ID_Supplier");
                String NamaSupplier= rst.getString("Nama");
                String TeleponSupplier = rst.getString("No_Telp");
                String EmailSupplier = rst.getString("Email");
                String AlamatSupplier = rst.getString("Alamat");
               
                tabmodel.addRow(new Object[]{IdSupplier, NamaSupplier, TeleponSupplier, EmailSupplier, AlamatSupplier});
            }
            pst.close();
            rst.close();
            
            int totalPage = (int) Math.ceil((double)count / limit);
            pagination.setPagination(page, totalPage);
        } catch(Exception ex) {
            ex.printStackTrace();
        }    
    }
                
                
    public void addData(ModelSupplier modelSupplier) {
        String query = "INSERT INTO supplier (ID_Supplier, Nama, No_Telp, Email, Alamat) VALUES (?,?,?,?,?)";
        try {
           PreparedStatement pst = connection.prepareStatement(query);
           pst.setString(1, modelSupplier.getIdSupplier());
           pst.setString(2, modelSupplier.getNamaSupplier());
           pst.setString(3, modelSupplier.getTeleponSupplier());
           pst.setString(4, modelSupplier.getEmailSupplier());
           pst.setString(5, modelSupplier.getAlamatSupplier());
           pst.executeUpdate();
           JOptionPane.showMessageDialog(null, "Supplier Berhasil Ditambahkan");
           pst.close();
           
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void updateData(ModelSupplier modelSupplier){
     String query = "UPDATE supplier SET Nama=?, No_Telp=?, Email=?, Alamat=? WHERE ID_Supplier=?";
     try {
          PreparedStatement pst = connection.prepareStatement(query);
           pst.setString(1, modelSupplier.getNamaSupplier());
           pst.setString(2, modelSupplier.getTeleponSupplier());
           pst.setString(3, modelSupplier.getEmailSupplier());
           pst.setString(4, modelSupplier.getAlamatSupplier());
           pst.setString(5, modelSupplier.getIdSupplier());
           pst.executeUpdate();
           JOptionPane.showMessageDialog(null, "Data Supplier Berhasil Diperbarui");
           pst.close();
           
        } catch(Exception ex) {
            ex.printStackTrace();
        }
     }
    
    
    public void deleteData(ModelSupplier modelSupplier){
    String query = "DELETE FROM supplier WHERE ID_Supplier=?";
    try{
        PreparedStatement pst = connection.prepareCall(query);
        pst.setString(1, modelSupplier.getIdSupplier());
        pst.executeUpdate();
         JOptionPane.showMessageDialog(null, "Supplier Berhasil Di Hapus");
    } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public String createId() {
        String idSupplier = null;
        String query = "SELECT RIGHT(ID_Supplier, 3) AS ID FROM supplier ORDER BY ID_Supplier DESC";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                int number = Integer.parseInt(rst.getString("ID"));
                number++;
                idSupplier = "SLR-" + String.format("%03d", number);
            } else {
                idSupplier = "SLR-001";
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return idSupplier;
    }
}



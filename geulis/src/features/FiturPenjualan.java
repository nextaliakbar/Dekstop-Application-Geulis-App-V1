/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package features;

import action.ActionPagination;
import action.TableAction;
import control.FieldsPenjualan;
import control.ParamPenjualan;
import control.Penjualan;
import control.Report;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import model.ModelBarang;
import model.ModelDetailPenjualan;
import model.ModelHeaderTable;
import model.ModelPengguna;
import model.ModelPenjualan;
import model.ModelRenderTable;
import model.Sementara;
import net.sf.jasperreports.engine.JRException;
import service.ServiceDetailPenjualan;
import service.ServicePenjualan;
import swing.TableCellActionRender;
import swing.TableCellEditor;

/**
 *
 * @author usER
 */
public class FiturPenjualan extends javax.swing.JPanel {

    /**
     * Creates new form FiturBarang
     */
    private DefaultTableModel tabmodel1;
    private DefaultTableModel tabmodel2;
    private TableAction action;
    private ModelPengguna modelPengguna;
    private ServicePenjualan servicePenjualan = new ServicePenjualan();
    private ServiceDetailPenjualan serviceDetail = new ServiceDetailPenjualan();
    private final DecimalFormat df = new DecimalFormat("#,##0.##");
    public FiturPenjualan(ModelPengguna modelPengguna) {
        initComponents();
        this.modelPengguna = modelPengguna;
        styleTable(scrollPane, table, 8);
        tabmodel1 = (DefaultTableModel) table.getModel();
        tampilData();
        styleTable(scrollPanePasien, tableDetail, 7);
        tabmodel2 = (DefaultTableModel) tableDetail.getModel();
        instanceReport();
        actionTableMain();    
    }
    
//  Style Table
    private void styleTable(JScrollPane scroll, JTable table, int columnTable) {
        scroll.getViewport().setBackground(new Color(255,255,255));
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255,255,255));
        scroll.setCorner(JScrollPane.UPPER_RIGHT_CORNER, panel);
        scroll.setBorder(new EmptyBorder(5,10,5,10));
        table.setRowHeight(40);        
        table.getTableHeader().setDefaultRenderer(new ModelHeaderTable());
        table.setDefaultRenderer(Object.class, new ModelRenderTable(columnTable));
    }

    private void tampilData() {
        servicePenjualan.loadData(1, tabmodel1, pagination);
        pagination.addActionPagination(new ActionPagination() {
            @Override
            public void pageChanged(int page) {
                tabmodel1.setRowCount(0);
                servicePenjualan.loadData(page, tabmodel1, pagination);
            }
        });
    }
    
//  Update,Delete,Detail Table Main
    private void actionTableMain() {
        action = new TableAction() {
        @Override
        public void edit(int row) {
            System.out.println("Edit row : " + row);
        }

        @Override
        public void delete(int row) {
            if(table.isEditing()) {
                table.getCellEditor().stopCellEditing();
            }
            tabmodel1.removeRow(row);
        }

        @Override
        public void view(int row) {
            tampilDetail(row);
        }
    };        
        table.getColumnModel().getColumn(8).setCellRenderer(new TableCellActionRender(false, false, true));
        table.getColumnModel().getColumn(8).setCellEditor(new TableCellEditor(action, false, false, true));
    }
    
//  Update,Delete,Detail Table Detail
    private void actionTableDetail() {
        action = new TableAction() {
        @Override
        public void edit(int row) {
        }

        @Override
        public void delete(int row) {
            if(tableDetail.isEditing()) {
                tableDetail.getCellEditor().stopCellEditing();
            }
            tabmodel2.removeRow(row);
            lbTotal.setText(df.format(total()));
            double kembali = Double.parseDouble(txtBayar.getText()) - total();
            lbKembalian.setText(df.format(kembali));
        }

        @Override
        public void view(int row) {
            
        }
    };        
        tableDetail.getColumnModel().getColumn(7).setCellRenderer(new TableCellActionRender(false, true, false));
        tableDetail.getColumnModel().getColumn(7).setCellEditor(new TableCellEditor(action, false, true, false));
    }
    
//    Instance Report Penjualan
    private void instanceReport() {
        try {
            Report.getInstance().compileReport("Penjualan");
        } catch(JRException ex) {
            ex.printStackTrace();
        }
    }
    
//    Print Penjualan
    private void printPenjualan() {
        try {
            List<FieldsPenjualan> fields = new ArrayList<>();
            for(int a = 0; a < tableDetail.getRowCount(); a++) {
                Penjualan penjualan = (Penjualan) tableDetail.getValueAt(a, 0);
                fields.add(new FieldsPenjualan(penjualan.getNamaBrg(), penjualan.getHargaJual(), penjualan.getJumlah(), penjualan.getSubtotal()));
            }
            String noPenjualan = lbNoPenjualan.getText();
            Date dateNow = new Date();
            String tglPenjualan = new SimpleDateFormat("dd-MM-yyyy").format(dateNow);
            String jamPenjualan = new SimpleDateFormat("HH:mm").format(dateNow) + " WIB";
            String admin = modelPengguna.getIdpengguna();
            String total = lbTotal.getText();
            String bayar = df.format(Double.parseDouble(txtBayar.getText()));
            String kembali = lbKembalian.getText();
            String jenisPembayaran = (String) cbx_jenisPembayaran.getSelectedItem();
            ParamPenjualan paramater = new ParamPenjualan(tglPenjualan+","+jamPenjualan, noPenjualan, admin, total, bayar, kembali, jenisPembayaran, fields);
            Report.getInstance().printReportPenjualan(paramater);
        } catch(JRException ex) {
            ex.printStackTrace();
        }
    }
    
    private void tampilDetail(int row) {
        ModelPengguna modelPengguna = new ModelPengguna();
        String noPenjualan = (String) table.getValueAt(row, 0);
        String idPengguna = (String) table.getValueAt(row, 1);
        String namaPengguna = (String) table.getValueAt(row, 2);
        modelPengguna.setIdpengguna(idPengguna);
        modelPengguna.setNama(namaPengguna);
        String tglPenjualan = (String) table.getValueAt(row, 3);
        int totalPenjualan = (int) table.getValueAt(row, 4);
        double bayar = (double) table.getValueAt(row, 5);
        double kembali = (double) table.getValueAt(row, 6);
        String jenisPembayaran = (String) table.getValueAt(row, 7);
        ModelPenjualan modelPenjualan = new ModelPenjualan(noPenjualan, tglPenjualan, totalPenjualan, bayar, kembali, jenisPembayaran, modelPengguna);
        ModelDetailPenjualan modelDetail = new ModelDetailPenjualan();
        modelDetail.setModelPenjualan(modelPenjualan);
        DialogDetail detail = new DialogDetail(null, true, "Slide-6", null, modelDetail, null, null);
        detail.setVisible(true);
    }
    
    private void tambahData() {
//      Tambah Penjualan
        ModelDetailPenjualan detail = new ModelDetailPenjualan();
        String noPenjualan = lbNoPenjualan.getText();
        LocalDate dateNow = LocalDate.now();
        String tglPenjualan = dateNow.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int totalPenjualan = (int) total();
        double bayar = Double.parseDouble(txtBayar.getText());
        double kembalian = 0;
        String jenisPembayaran = (String) cbx_jenisPembayaran.getSelectedItem();
        String strKembalian = lbKembalian.getText();
        try {
            Number formatNumber = df.parse(strKembalian);
            kembalian = Double.parseDouble(formatNumber.toString());
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        ModelPenjualan modelPenjualan = new ModelPenjualan(noPenjualan, tglPenjualan, totalPenjualan, bayar, kembalian, jenisPembayaran, modelPengguna);
        servicePenjualan.addData(modelPenjualan);
        
//        Tambah Detail Penjualan
        for(int a = 0; a < tableDetail.getRowCount(); a++) {
            String kodeBrg = (String) tableDetail.getValueAt(a, 1);
            int jumlah = (int) tableDetail.getValueAt(a, 5);
            double subtotal = (double) tableDetail.getValueAt(a, 6);
            Sementara ps = new Sementara(new String[]{kodeBrg}, new int[]{jumlah}, new double[]{subtotal});
            detail.setModelPenjualan(modelPenjualan);
            serviceDetail.addData(detail, ps);
        }
    }
    
    private void tambahSementara() {
        String kodeBrg = lbKodeBrg.getText();
        String namaBrg = lbNamaBrg.getText();
        String satuan = lbSatuan.getText();
        double hrgJual = Double.parseDouble(lbHrgJual.getText());
        int jumlah = (int) spnJumlah.getValue();
        double subtotal = Double.parseDouble(lbSubtotal.getText());
        tabmodel2.addRow(new Penjualan(kodeBrg, namaBrg, satuan, hrgJual, jumlah, subtotal).toRowTable());
        lbTotal.setText(df.format(total()));
    }
    
    private double total() {
        double total = 0;
        for(int a = 0; a < tableDetail.getRowCount(); a++) {
            double subtotal = (double) tableDetail.getValueAt(a, 6);
            total += subtotal;
        }
        return total;
    }
    
    private boolean validationAddDataTemporary() {
        boolean valid = true;
        int jumlah = (int) spnJumlah.getValue();
        int rowCount = tableDetail.getRowCount();
        String kodeBrg = lbKodeBrg.getText();            
        try {
            if(lbNoBarcode.getText().trim().length() == 0 ) {
                valid = false;
                JOptionPane.showMessageDialog(null, "Silahkan Pilih Barang");
            } else if(jumlah <= 0) {
                valid = false;
                JOptionPane.showMessageDialog(null, "Silahkan Masukkan Jumlah Penjualan");
            } else {
             for(int a = 0; a < rowCount; a++) {
                String kodeBrgInTable = (String) tableDetail.getValueAt(a, 1);
                    if(kodeBrg.equals(kodeBrgInTable)) {
                        valid = false;
                        JOptionPane.showMessageDialog(null, "Barang ini sudah ditambahkan");
                        break;
                    } else {
                        valid = true;
                    }
                }            
            }
        } catch(NullPointerException ex) {
            valid = false;
            JOptionPane.showMessageDialog(null, "Silahkan Pilih Barang");
        }
        return valid;
    }
    
    private boolean validation() {
        boolean valid = false;
        try {
            if(tableDetail.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Silahkan Pilih Barang");       
            } else if(txtBayar.getText().trim().length() == 0) {
                JOptionPane.showMessageDialog(null, "Silahkan masukkan jumlah pembayaran");
            } else if(Double.parseDouble(txtBayar.getText()) < total()) {
                JOptionPane.showMessageDialog(null, "Jumlah Pembayaran Kurang dari Total Penjualan");    
            } else {
                valid = true;
            }
        } catch(NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "Silahkan Pilih Barang");    
        }
        
        return valid;
    }
    
    private void clearFieldAll() {
        spnJumlah.setValue((int) 0);
        lbTotal.setText("0");
        txtBayar.setText("");
        lbKembalian.setText("0");
        tabmodel2.setRowCount(0);
        clearField();
    }
    
    private void clearField() {
        lbNoBarcode.setText("Klik disini dan Scan Barcode Barang atau Klik Pilih");
        lbNoBarcode.setFont(new Font("sansserif", Font.ITALIC, 14));
        lbNoBarcode.setForeground(new Color(185, 185, 185));
        lbKodeBrg.setText("");
        lbNamaBrg.setText("");
        lbSatuan.setText("");
        lbHrgJual.setText("");
        spnJumlah.setValue((int) 0);
        lbSubtotal.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelData = new javax.swing.JPanel();
        panel1 = new javax.swing.JPanel();
        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        btnTambah = new swing.Button();
        label = new javax.swing.JLabel();
        pagination = new swing.Pagination();
        txtCari = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        panelTambah = new javax.swing.JPanel();
        panel3 = new javax.swing.JPanel();
        scrollPanePasien = new javax.swing.JScrollPane();
        tableDetail = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lbTotal = new javax.swing.JLabel();
        btnPrint = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txtBayar = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        lbKembalian = new javax.swing.JLabel();
        cbx_jenisPembayaran = new javax.swing.JComboBox<>();
        panel2 = new javax.swing.JPanel();
        btnTambahSementara = new swing.Button();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        btnPilih = new swing.Button();
        jLabel16 = new javax.swing.JLabel();
        spnJumlah = new javax.swing.JSpinner();
        lbSubtotal = new javax.swing.JLabel();
        lbHrgJual = new javax.swing.JLabel();
        lbNamaBrg = new javax.swing.JLabel();
        lbSatuan = new javax.swing.JLabel();
        lbTgl = new javax.swing.JLabel();
        lbNoPenjualan = new javax.swing.JLabel();
        lbNoBarcode = new javax.swing.JTextField();
        lbKodeBrg = new javax.swing.JLabel();
        lbStok = new javax.swing.JLabel();
        lbNotif = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        label1 = new javax.swing.JLabel();
        btnBatal = new swing.Button();
        btnSimpan = new swing.Button();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.CardLayout());

        panelData.setBackground(new java.awt.Color(153, 153, 153));
        panelData.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));

        panel1.setBackground(new java.awt.Color(255, 255, 255));

        scrollPane.setBackground(new java.awt.Color(255, 255, 255));
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);

        table.setBackground(new java.awt.Color(255, 255, 255));
        table.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No Penjualan", "ID Pengguna", "Kasir", "Tanggal", "Total", "Bayar", "Kembali", "Jenis Pembayaran", "Detail"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setGridColor(new java.awt.Color(185, 185, 185));
        table.setOpaque(false);
        table.setSelectionBackground(new java.awt.Color(255, 255, 255));
        scrollPane.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(1).setMinWidth(0);
            table.getColumnModel().getColumn(1).setPreferredWidth(0);
            table.getColumnModel().getColumn(1).setMaxWidth(0);
            table.getColumnModel().getColumn(5).setMinWidth(0);
            table.getColumnModel().getColumn(5).setPreferredWidth(0);
            table.getColumnModel().getColumn(5).setMaxWidth(0);
            table.getColumnModel().getColumn(6).setMinWidth(0);
            table.getColumnModel().getColumn(6).setPreferredWidth(0);
            table.getColumnModel().getColumn(6).setMaxWidth(0);
            table.getColumnModel().getColumn(7).setMinWidth(0);
            table.getColumnModel().getColumn(7).setPreferredWidth(0);
            table.getColumnModel().getColumn(7).setMaxWidth(0);
        }

        btnTambah.setBackground(new java.awt.Color(135, 15, 50));
        btnTambah.setForeground(new java.awt.Color(255, 255, 255));
        btnTambah.setText("TAMBAH");
        btnTambah.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        label.setBackground(new java.awt.Color(135, 15, 50));
        label.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        label.setForeground(new java.awt.Color(135, 15, 50));
        label.setText("PENJUALAN");

        pagination.setBackground(new java.awt.Color(135, 15, 50));
        pagination.setForeground(new java.awt.Color(255, 255, 255));
        pagination.setOpaque(false);

        txtCari.setBackground(new java.awt.Color(255, 255, 255));
        txtCari.setFont(new java.awt.Font("SansSerif", 2, 14)); // NOI18N
        txtCari.setForeground(new java.awt.Color(185, 185, 185));
        txtCari.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCari.setText("Cari Berdasarkan Kode Barang & Nama Barang");
        txtCari.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(185, 185, 185)));
        txtCari.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCariFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCariFocusLost(evt);
            }
        });

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search-2.png"))); // NOI18N

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 651, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pagination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1148, Short.MAX_VALUE)
                        .addGroup(panel1Layout.createSequentialGroup()
                            .addComponent(label)
                            .addGap(0, 933, Short.MAX_VALUE)))
                    .addContainerGap()))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 765, Short.MAX_VALUE)
                .addComponent(pagination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(label)
                    .addGap(63, 63, 63)
                    .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                    .addGap(53, 53, 53)))
        );

        javax.swing.GroupLayout panelDataLayout = new javax.swing.GroupLayout(panelData);
        panelData.setLayout(panelDataLayout);
        panelDataLayout.setHorizontalGroup(
            panelDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDataLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(40, 40, 40))
        );
        panelDataLayout.setVerticalGroup(
            panelDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDataLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );

        add(panelData, "card2");

        panelTambah.setBackground(new java.awt.Color(153, 153, 153));
        panelTambah.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));

        panel3.setBackground(new java.awt.Color(255, 255, 255));

        scrollPanePasien.setBorder(null);
        scrollPanePasien.setOpaque(false);

        tableDetail.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tableDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Data", "Kode Barang", "Nama Barang", "Satuan", "Harga Jual", "Jumlah", "Subtotal", "Aksi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableDetail.setOpaque(false);
        scrollPanePasien.setViewportView(tableDetail);
        if (tableDetail.getColumnModel().getColumnCount() > 0) {
            tableDetail.getColumnModel().getColumn(0).setMinWidth(0);
            tableDetail.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableDetail.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        jPanel2.setBackground(new java.awt.Color(135, 15, 50));

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("TOTAL : ");

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Rp");

        lbTotal.setBackground(new java.awt.Color(255, 255, 255));
        lbTotal.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        lbTotal.setForeground(new java.awt.Color(255, 255, 255));
        lbTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbTotal.setText("0");

        btnPrint.setBackground(new java.awt.Color(135, 15, 50));
        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/printerwhite.png"))); // NOI18N
        btnPrint.setBorder(null);
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnPrint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel24.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 0));
        jLabel24.setText("BAYAR");

        jLabel25.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 0));
        jLabel25.setText("KEMBALI");

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Rp");

        jLabel26.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 0));
        jLabel26.setText("Rp");

        txtBayar.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        txtBayar.setForeground(new java.awt.Color(0, 0, 0));
        txtBayar.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));
        txtBayar.setOpaque(false);
        txtBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBayarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBayarKeyTyped(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));
        jPanel6.setOpaque(false);

        lbKembalian.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lbKembalian.setForeground(new java.awt.Color(0, 0, 0));
        lbKembalian.setText("0");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(lbKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbKembalian, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        cbx_jenisPembayaran.setBackground(new java.awt.Color(255, 255, 255));
        cbx_jenisPembayaran.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        cbx_jenisPembayaran.setForeground(new java.awt.Color(0, 0, 0));
        cbx_jenisPembayaran.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tunai", "Non Tunai" }));
        cbx_jenisPembayaran.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPanePasien, javax.swing.GroupLayout.DEFAULT_SIZE, 807, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel3Layout.createSequentialGroup()
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel26))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel3Layout.createSequentialGroup()
                                .addComponent(txtBayar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbx_jenisPembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel3Layout.createSequentialGroup()
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtBayar)
                                .addComponent(cbx_jenisPembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panel3Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPanePasien, javax.swing.GroupLayout.DEFAULT_SIZE, 644, Short.MAX_VALUE)
                .addContainerGap())
        );

        panel2.setBackground(new java.awt.Color(255, 255, 255));

        btnTambahSementara.setBackground(new java.awt.Color(135, 15, 50));
        btnTambahSementara.setForeground(new java.awt.Color(255, 255, 255));
        btnTambahSementara.setText("Tambah");
        btnTambahSementara.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnTambahSementara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahSementaraActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Tanggal");

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("No Penjualan");

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Satuan");

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Harga Jual");

        jLabel10.setBackground(new java.awt.Color(134, 15, 50));
        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Pilih Barang");
        jLabel10.setOpaque(true);

        jLabel13.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Nama Barang");

        jLabel14.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Jumlah");

        btnPilih.setBackground(new java.awt.Color(135, 15, 50));
        btnPilih.setForeground(new java.awt.Color(255, 255, 255));
        btnPilih.setText("PILIH");
        btnPilih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPilihActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("Subtotal");

        spnJumlah.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        spnJumlah.setModel(new javax.swing.SpinnerNumberModel());
        spnJumlah.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnJumlahStateChanged(evt);
            }
        });

        lbSubtotal.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbSubtotal.setForeground(new java.awt.Color(0, 0, 0));
        lbSubtotal.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        lbHrgJual.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbHrgJual.setForeground(new java.awt.Color(0, 0, 0));
        lbHrgJual.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        lbNamaBrg.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbNamaBrg.setForeground(new java.awt.Color(0, 0, 0));
        lbNamaBrg.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        lbSatuan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbSatuan.setForeground(new java.awt.Color(0, 0, 0));
        lbSatuan.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        lbTgl.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbTgl.setForeground(new java.awt.Color(0, 0, 0));
        lbTgl.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        lbNoPenjualan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbNoPenjualan.setForeground(new java.awt.Color(0, 0, 0));
        lbNoPenjualan.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        lbNoBarcode.setBackground(new java.awt.Color(255, 255, 255));
        lbNoBarcode.setFont(new java.awt.Font("SansSerif", 2, 14)); // NOI18N
        lbNoBarcode.setForeground(new java.awt.Color(185, 185, 185));
        lbNoBarcode.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        lbNoBarcode.setText("Klik disini dan Scan Barcode Barang atau Klik Pilih");
        lbNoBarcode.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));
        lbNoBarcode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                lbNoBarcodeFocusGained(evt);
            }
        });
        lbNoBarcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lbNoBarcodeActionPerformed(evt);
            }
        });

        lbKodeBrg.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbKodeBrg.setForeground(new java.awt.Color(0, 0, 0));
        lbKodeBrg.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        lbStok.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        lbStok.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lbNotif.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lbNotif.setForeground(new java.awt.Color(135, 15, 50));
        lbNotif.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbNotif.setText("Jumlah penjualan melebihi stok yang tersedia");

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbKodeBrg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbTgl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbNoPenjualan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(6, 6, 6)
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addComponent(spnJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbStok, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbSatuan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbHrgJual, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)))
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbSubtotal, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                            .addComponent(lbNamaBrg, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbNotif, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnTambahSementara, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                        .addComponent(lbNoBarcode)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPilih, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbNoPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbTgl, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnPilih, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(lbNoBarcode))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbKodeBrg, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbNamaBrg, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbSatuan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbHrgJual, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(spnJumlah, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(lbStok, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbNotif)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnTambahSementara, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(135, 15, 50));

        label1.setBackground(new java.awt.Color(135, 15, 50));
        label1.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        label1.setForeground(new java.awt.Color(255, 255, 255));
        label1.setText("Tambah Penjualan");

        btnBatal.setForeground(new java.awt.Color(135, 15, 50));
        btnBatal.setText("BATAL");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        btnSimpan.setForeground(new java.awt.Color(135, 15, 50));
        btnSimpan.setText("SIMPAN");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(label1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label1)
                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelTambahLayout = new javax.swing.GroupLayout(panelTambah);
        panelTambah.setLayout(panelTambahLayout);
        panelTambahLayout.setHorizontalGroup(
            panelTambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTambahLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelTambahLayout.createSequentialGroup()
                        .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelTambahLayout.setVerticalGroup(
            panelTambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTambahLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(panelTambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelTambahLayout.createSequentialGroup()
                        .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        add(panelTambah, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        changePanel(panelTambah);
        actionTableDetail();
        lbNoPenjualan.setText(servicePenjualan.createNo());
        Date date = new Date();
        lbTgl.setText(new SimpleDateFormat("dd - MMMM - yyyy").format(date));
        lbKodeBrg.setVisible(false);
        lbStok.setVisible(false);
        lbNotif.setVisible(false);
    }//GEN-LAST:event_btnTambahActionPerformed

    private void txtCariFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCariFocusGained
        txtCari.setText(null);
        txtCari.setForeground(new Color(0,0,0));
        txtCari.setFont(new Font("sansserif",0,14));
        pagination.setVisible(false);
        tabmodel1.setRowCount(0);
        servicePenjualan.loadAll(tabmodel1);
    }//GEN-LAST:event_txtCariFocusGained

    private void txtCariFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCariFocusLost
        pagination.setVisible(true);
    }//GEN-LAST:event_txtCariFocusLost

    private void btnTambahSementaraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahSementaraActionPerformed
        if(validationAddDataTemporary()) {
            tambahSementara();
            clearField();
            lbNoBarcode.requestFocus();
        }
    }//GEN-LAST:event_btnTambahSementaraActionPerformed

    private void txtBayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBayarKeyReleased
        String strBayar = txtBayar.getText();
        double total = (double) total();
        double bayar = 0;
        double kembalian = 0;
        if(strBayar.length() > 0) {
            bayar = Double.parseDouble(strBayar);
        }
        kembalian = bayar - total;
        lbKembalian.setText(df.format(kembalian));
    }//GEN-LAST:event_txtBayarKeyReleased

    private void txtBayarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBayarKeyTyped
        characterDigit(evt);
    }//GEN-LAST:event_txtBayarKeyTyped

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        if(validation()) {
            printPenjualan();
        }
    }//GEN-LAST:event_btnPrintActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        if(tableDetail.getRowCount() != 0) {
            int confirm = JOptionPane.showConfirmDialog(null, "Data yang telah diinput akan dihapus", "Konfirmasi", JOptionPane.OK_OPTION);
            if(confirm == 0) {
            clearFieldAll();
            changePanel(panelData);
            }   
        } else {
            clearFieldAll();
            changePanel(panelData); 
        }
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnPilihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPilihActionPerformed
        TambahPemesanan pilihBrg = new TambahPemesanan(null, true, "Slide-2");
        pilihBrg.setVisible(true);
        lbNoBarcode.setText(pilihBrg.modelBarang.getKode_Barang());
        lbKodeBrg.setText(pilihBrg.modelBarang.getKode_Barang());
        lbNamaBrg.setText(pilihBrg.modelBarang.getNama_Barang());
        lbSatuan.setText(pilihBrg.modelBarang.getSatuan());
        lbHrgJual.setText(String.valueOf(pilihBrg.modelBarang.getHarga_Jual()));
        lbStok.setText(String.valueOf(pilihBrg.modelBarang.getStok()));
        spnJumlah.setValue((int) 1);
        int jumlah = (int) spnJumlah.getValue();
        double hargaJual = Double.parseDouble(lbHrgJual.getText());
        double subtotal = hargaJual * jumlah;
        lbNoBarcode.setFont(new Font("sansserif", 0, 20));
        lbNoBarcode.setForeground(new Color(0, 0, 0));
        lbSubtotal.setText(String.valueOf(subtotal));
    }//GEN-LAST:event_btnPilihActionPerformed

    private void lbNoBarcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lbNoBarcodeActionPerformed
        String noBarcode = lbNoBarcode.getText();
        ModelBarang modelBarang = new ModelBarang();
        modelBarang.setNomor_Barcode(noBarcode);
        for(ModelBarang barang : servicePenjualan.setFieldBrg(modelBarang)) {
            lbKodeBrg.setText(barang.getKode_Barang());
            lbNamaBrg.setText(barang.getNama_Barang());
            lbSatuan.setText(barang.getSatuan());
            lbHrgJual.setText(String.valueOf(barang.getHarga_Jual()));
        }
        spnJumlah.setValue((int) 1);
        int jumlah = (int) spnJumlah.getValue();
        double hargaJual = Double.parseDouble(lbHrgJual.getText());
        double subtotal = hargaJual * jumlah;
        lbSubtotal.setText(String.valueOf(subtotal));
    }//GEN-LAST:event_lbNoBarcodeActionPerformed

    private void lbNoBarcodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lbNoBarcodeFocusGained
        lbNoBarcode.setText("");
        lbNoBarcode.setFont(new Font("sansserif", 0, 20));
        lbNoBarcode.setForeground(new Color(0, 0, 0));
    }//GEN-LAST:event_lbNoBarcodeFocusGained

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if(validation()) {
            tambahData();
            clearFieldAll();
            tabmodel1.setRowCount(0);
            changePanel(panelData);
            tampilData();
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void spnJumlahStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnJumlahStateChanged
        int jumlah = (int) spnJumlah.getValue();
        int stok = Integer.parseInt(lbStok.getText());
        if(jumlah > stok) {
            lbNotif.setVisible(true);
        } else {
            String hrgJual = lbHrgJual.getText();
            double subtotal = 0;
            if(hrgJual.length() != 0) {
                if(jumlah > 0) {
                    subtotal = Double.parseDouble(hrgJual) * jumlah;    
                } else if(jumlah == 0) {
                    subtotal = Double.parseDouble(hrgJual) + jumlah;         
                } else {
                    subtotal = Double.parseDouble(hrgJual) + 0;             
                }
            }
            lbNotif.setVisible(false);
            lbSubtotal.setText(String.valueOf(subtotal));    
        }
    }//GEN-LAST:event_spnJumlahStateChanged

    private void changePanel(JPanel panel) {
        removeAll();
        add(panel);
        repaint();
        revalidate();
    }
    
    private void characterDigit(KeyEvent evt) {
        char typed = evt.getKeyChar();
        if(!Character.isDigit(typed)) {
            evt.consume();
        }
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.Button btnBatal;
    private swing.Button btnPilih;
    private javax.swing.JButton btnPrint;
    private swing.Button btnSimpan;
    private swing.Button btnTambah;
    private swing.Button btnTambahSementara;
    private javax.swing.JComboBox<String> cbx_jenisPembayaran;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel label;
    private javax.swing.JLabel label1;
    private javax.swing.JLabel lbHrgJual;
    private javax.swing.JLabel lbKembalian;
    private javax.swing.JLabel lbKodeBrg;
    private javax.swing.JLabel lbNamaBrg;
    private javax.swing.JTextField lbNoBarcode;
    private javax.swing.JLabel lbNoPenjualan;
    private javax.swing.JLabel lbNotif;
    private javax.swing.JLabel lbSatuan;
    private javax.swing.JLabel lbStok;
    private javax.swing.JLabel lbSubtotal;
    private javax.swing.JLabel lbTgl;
    private javax.swing.JLabel lbTotal;
    private swing.Pagination pagination;
    private javax.swing.JPanel panel1;
    private javax.swing.JPanel panel2;
    private javax.swing.JPanel panel3;
    private javax.swing.JPanel panelData;
    private javax.swing.JPanel panelTambah;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JScrollPane scrollPanePasien;
    private javax.swing.JSpinner spnJumlah;
    private javax.swing.JTable table;
    private javax.swing.JTable tableDetail;
    private javax.swing.JTextField txtBayar;
    private javax.swing.JTextField txtCari;
    // End of variables declaration//GEN-END:variables
}

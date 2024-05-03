/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package main;

import action.ActionMenuSelected;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import component.Content;
import component.Navbar;
import component.Sidebar;
import features.Dashboard;
import features.FiturAbsensi;
import features.FiturBarang;
import features.FiturCetakKartu;
import features.FiturKaryawan;
import features.FiturLaporan;
import features.FiturPasien;
import features.FiturPemeriksaan;
import features.FiturPemesanan;
import features.FiturPengaturan;
import features.FiturPengeluaran;
import features.FiturPengguna;
import features.FiturPenjualan;
import features.FiturReservasi;
import features.FiturRestok;
import features.FiturRiwayatPasien;
import features.FiturSupplier;
import features.FiturTindakan;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.ModelPengguna;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import service.ServicePromo;

/**
 *
 * @author usER
 */
public class Main extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    private MigLayout layout;
    private Navbar navbar;
    private Content content;
    private Sidebar menu;
    private Animator animator;
    public Main(ModelPengguna modelPengguna) {
        initComponents();
        initiation(modelPengguna);
        endPromoAuto();
    }
    
    public void initiation(ModelPengguna modelPengguna) {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        layout = new MigLayout("fill", "0[]0[100%, fill]0", "0[fill, top]0");
        background.setLayout(layout);
        menu = new Sidebar();
        content = new Content();
        navbar = new Navbar(modelPengguna);
        String level = modelPengguna.getLevel();
        if(level.equals("Owner")) {
        content.showContent(new Dashboard(modelPengguna));   
        } else {
        content.showContent(new FiturReservasi(modelPengguna));   
            
        }
        menu.addAction(new ActionMenuSelected() {
            @Override
            public void menuSelected(int menuIndex, int subMenuIndex) {
                
//          Owner
                if(level.equals("Owner")) {
                if (menuIndex == 0 && subMenuIndex == -1) {
//              fitur beranda
                      content.showContent(new Dashboard(modelPengguna));
                } else if (menuIndex == 1) {
//              fitur master                

                    if (subMenuIndex == 0) {
                        content.showContent(new FiturBarang());
                    } else if (subMenuIndex == 1) {
//                        fitur tindakan
                        content.showContent(new FiturTindakan());

                    } else if (subMenuIndex == 2) {
//                        fitur pasien
                        content.showContent(new FiturPasien());

                    } else if (subMenuIndex == 3) {
//                        fitur supplier
                        content.showContent(new FiturSupplier());

                    } else if (subMenuIndex == 4) {
//                        fitur karyawan
                        content.showContent(new FiturKaryawan());

                    } else if (subMenuIndex == 5) {
//                        fitur pengguna
                        content.showContent(new FiturPengguna());

                    }
                } else if (menuIndex == 2 && subMenuIndex == -1) {
//              fitur reservasi
                    content.showContent(new FiturReservasi(modelPengguna));

                } else if (menuIndex == 3) {
//              fitur transaksi
                    if (subMenuIndex == 0) {
//                        fitur pemeriksaan
                        content.showContent(new FiturPemeriksaan(modelPengguna));
                    } else if (subMenuIndex == 1) {
//                        fitur penjualan
                        content.showContent(new FiturPenjualan(modelPengguna));
                    } else if (subMenuIndex == 2) {
//                        fitur pemesanan
                        content.showContent(new FiturPemesanan(modelPengguna));
                    }
                } else if (menuIndex == 4 && subMenuIndex == -1) {
//              fitur riwayat pasien 
                        content.showContent(new FiturPengeluaran(modelPengguna));
                } else if(menuIndex == 5 && subMenuIndex == -1) {
                        content.showContent(new FiturRiwayatPasien());                   
                } else if (menuIndex == 6) {
//              fitur lain lain
                    if (subMenuIndex == 0) {
//                        fitur restok
                        content.showContent(new FiturRestok(modelPengguna));
                    } else if (subMenuIndex == 1) {
//                        fitur absensi
                        content.showContent(new FiturAbsensi());
                    } else if (subMenuIndex == 2) {
//                        fitur cetak kartu
                        content.showContent(new FiturCetakKartu());
                    } else if (subMenuIndex == 3) {
//                        fitur laporan
                        content.showContent(new FiturLaporan());
                    }
                }
                
//          Admin        
                } else {
                    if (menuIndex == 0 && subMenuIndex == -1) {
//                  fitur reservasi
                      content.showContent(new FiturReservasi(modelPengguna));
                    } else if(menuIndex == 1) {
//                  fitur transaksasi
                        if (subMenuIndex == 0) {
    //                        fitur pemeriksaan
                            content.showContent(new FiturPemeriksaan(modelPengguna));
                        } else if (subMenuIndex == 1) {
    //                        fitur penjualan
                            content.showContent(new FiturPenjualan(modelPengguna));
                        } else if (subMenuIndex == 2) {
    //                        fitur pemesanan
                            content.showContent(new FiturPemesanan(modelPengguna));
                        }
                    }  else if(menuIndex == 2 && subMenuIndex == -1) {
//                     Fitur Riwayat Pasien
                       content.showContent(new FiturRiwayatPasien());
                    } else if(menuIndex == 3) {
//                        Fitur lain-lain
                        if (subMenuIndex == 0) {
    //                        fitur restok
                            content.showContent(new FiturRestok(modelPengguna));
                        } else if (subMenuIndex == 1) {
    //                        fitur absensi
                            content.showContent(new FiturAbsensi());
                        } else if (subMenuIndex == 2) {
    //                        fitur cetak kartu
                            content.showContent(new FiturCetakKartu());
                        } else if (subMenuIndex == 3) {
    //                        fitur laporan
                            content.showContent(new FiturLaporan());
                        }
                    }
                }

            }
        });
        
        actionSettings(modelPengguna);
        
        menu.initiationMenu(modelPengguna);
//        background.setLayer(panelDetail, JLayeredPane.POPUP_LAYER);
//        background.add(panelDetail, "pos 0 0 100% 100%");
        background.add(menu, "w 230!, spany2");
        background.add(navbar, "h 50!, wrap");
        background.add(content, "w 100%, h 100%");
        

        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                double width;
                if (menu.isShowMenu()) {
                    width = 52.5 + (170 * (1f - fraction));
                } else {
                    width = 60 + (170 * fraction);
                }

                layout.setComponentConstraints(menu, "w " + width + "!, spany2");
                menu.revalidate();
            }

            @Override
            public void end() {
                menu.setShowMenu(!menu.isShowMenu());
                menu.setEnableMenu(true);
            }
            
            
        };

        animator = new Animator(500, target);
        animator.setResolution(0);
        animator.setDeceleration(0.5f);
        animator.setAcceleration(0.5f);

        navbar.addAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!animator.isRunning()) {
                    animator.start();
                }

                menu.setEnableMenu(false);

                if (menu.isShowMenu()) {
                    menu.hidenMenu();
                }
            }
        });
    }
        
//    action settings
    private void actionSettings(ModelPengguna modelPengguna) {
        navbar.settings.account(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                content.showContent(new FiturPengaturan("Slide-Akun", modelPengguna));
                navbar.settings.dispose();
            }
        });
        
        navbar.settings.changePassword(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                content.showContent(new FiturPengaturan("Slide-Password", modelPengguna));
                navbar.settings.dispose();
            }
        });
        
        navbar.settings.promo(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                content.showContent(new FiturPengaturan("Slide-Promo", modelPengguna));
                navbar.settings.dispose();
            }
        });
        
        navbar.settings.logout(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "Yakin ingin keluar?", "Konfirmasi", JOptionPane.OK_OPTION);
                if(confirm == 0) {
                    Login login = new Login();
                    login.setVisible(true);
                    navbar.settings.dispose();
                    dispose();
                }
            }
        });
    }
    
//    End Promo Auto
    private void endPromoAuto() {
        ServicePromo servicePromo = new ServicePromo();
        servicePromo.autoChangeKeteranganPromo();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 971, Short.MAX_VALUE)
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 821, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        
        FlatMacLightLaf.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane background;
    // End of variables declaration//GEN-END:variables
}


import java.io.File;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MainClientProgram extends javax.swing.JFrame {

    public PeerOperations tcpclient1;
    public File files[];
    public String[] filelist;

    public MainClientProgram() {
        initComponents();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btn_share_files = new javax.swing.JButton();
        btn_get_file_list = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextField_server_ip = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField_server_port = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableDownloads = new javax.swing.JTable();
        btn_download = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableFileList = new javax.swing.JTable();
        connect_to_server = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Client/Peer");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        btn_share_files.setText("Share Files");
        btn_share_files.setEnabled(false);
        btn_share_files.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_share_filesActionPerformed(evt);
            }
        });

        btn_get_file_list.setText("GetFileList");
        btn_get_file_list.setEnabled(false);
        btn_get_file_list.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_get_file_listActionPerformed(evt);
            }
        });

        jLabel1.setText("Shared File List");

        jLabel2.setText("File Download Status");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setText("Server Ip");

        jLabel4.setText("Server Port");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jTextField_server_ip, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField_server_port, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_server_ip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_server_port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jTableDownloads.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "File Name", "Size", "Status", "Chunks"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTableDownloads);

        btn_download.setText("Download");
        btn_download.setEnabled(false);
        btn_download.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_downloadActionPerformed(evt);
            }
        });

        jTableFileList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "File Name", "Size"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTableFileList);

        connect_to_server.setText("Connect to Server");
        connect_to_server.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connect_to_serverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btn_share_files, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(connect_to_server, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(btn_download, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_get_file_list, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btn_download, btn_get_file_list, btn_share_files, connect_to_server});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(connect_to_server)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_share_files)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_get_file_list)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_download))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void btn_share_filesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_share_filesActionPerformed
        JFileChooser FileChooser1 = new JFileChooser();
        FileChooser1.setMultiSelectionEnabled(true);
        int result = FileChooser1.showOpenDialog(this);
        if (result == FileChooser1.APPROVE_OPTION) {
            files = FileChooser1.getSelectedFiles();

            String successMessage = tcpclient1.registerFiles(files);

            JOptionPane.showMessageDialog(this, successMessage);

        } else if (result == FileChooser1.CANCEL_OPTION) {

        }
        btn_get_file_listActionPerformed(evt);
    }//GEN-LAST:event_btn_share_filesActionPerformed
    private void btn_get_file_listActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_get_file_listActionPerformed

        String[] Columnnames = {"File Name", "Size"};

        String[] listOfFiles = tcpclient1.getListOfFiles();
        if(listOfFiles!=null){
            
        
        String[] row = null;
        filelist = new String[listOfFiles.length];
        
        DefaultTableModel dtm = new DefaultTableModel(Columnnames, 0);
        jTableFileList.setModel(dtm);

        for (int i = 0; i < listOfFiles.length; i++) {
            row = listOfFiles[i].split(",,,");
            if (row.length >= 2) {
                dtm.addRow(listOfFiles[i].split(",,,"));
                filelist[i] = row[0] + ",,," + row[1];
            }
        }
        }

    }//GEN-LAST:event_btn_get_file_listActionPerformed
    private void btn_downloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_downloadActionPerformed
        String[] selected = new String[4];
        String[] Columnnames = {"File Name", "Size", "Status", "Chunks"};

        if (jTableFileList.getSelectedRowCount() > 0) {
            selected[0] = filelist[jTableFileList.getSelectedRow()].split(",,,")[0];
            selected[1] = filelist[jTableFileList.getSelectedRow()].split(",,,")[1];
            selected[2] = "0%";
            selected[3] = "0/0";

            ((DefaultTableModel) jTableDownloads.getModel()).addRow(selected);
            new FileDownloader(tcpclient1, jTableDownloads, ((DefaultTableModel) jTableDownloads.getModel()).getRowCount() - 1, selected[0], selected[1]).start();

        }

         // <editor-fold>
        /*
         String[] selected;
         String[] ip_and_port_list = null;

         String[] Columnnames = {"File Name", "Size", "Ip", "Port", "Chunk_No"};

         if (filelist != null && (jTable2.getSelectedRowCount() > 0)) {

         selected = filelist[0].split(",,,");
         for (int i = 0; i < filelist.length; i++) {
         System.out.println(filelist[i]);
         }
         ip_and_port_list = tcpclient1.getFileLocation(selected[0], selected[1]);
         DefaultTableModel dtm = new DefaultTableModel(Columnnames, 0);
         jTable1.setModel(dtm);

         for (int i = 0; i < ip_and_port_list.length; i++) {
         dtm.addRow(ip_and_port_list[i].split(",,,"));
         }

         }
         */
        // </editor-fold>

    }//GEN-LAST:event_btn_downloadActionPerformed
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

        if (tcpclient1 != null) {
            tcpclient1.leaveRequest();
            tcpclient1.close();
        }

    }//GEN-LAST:event_formWindowClosing

    private void connect_to_serverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connect_to_serverActionPerformed

        if (jTextField_server_ip.getText() != null && jTextField_server_port.getText() != null
                && !jTextField_server_ip.getText().equals("") && !jTextField_server_port.getText().equals("")) {
            tcpclient1 = new PeerOperations(this,jTextField_server_ip.getText(), jTextField_server_port.getText());
            btn_download.setEnabled(true);
            btn_share_files.setEnabled(true);
            btn_get_file_list.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(this, "Please enter Server Address and Port correctly!!");
        }
    }//GEN-LAST:event_connect_to_serverActionPerformed
    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainClientProgram.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainClientProgram.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainClientProgram.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainClientProgram.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainClientProgram().setVisible(true);
            }
        });
    }
// <editor-fold>
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_download;
    private javax.swing.JButton btn_get_file_list;
    private javax.swing.JButton btn_share_files;
    private javax.swing.JButton connect_to_server;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTableDownloads;
    private javax.swing.JTable jTableFileList;
    private javax.swing.JTextField jTextField_server_ip;
    private javax.swing.JTextField jTextField_server_port;
    // End of variables declaration//GEN-END:variables
// </editor-fold>
}


package aps;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import net.proteanit.sql.DbUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class TelaPrincipal extends javax.swing.JFrame {

    String UsuarioAtivo = Main.Usuario;
    String IdUsuarioAtivo = "2";

    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaPrincipal() {
        initComponents();
        con = ConnectionApsDB.ConnectionDB();
        lbUsuarioAtivo.setText(UsuarioAtivo);
        updateTable();
    }

    private void updateTable() {
        try {
            String sql = "SELECT Sobrenome_Familia AS \"Sobrenome da Família\", Endereco_Familia AS \"Endereço\", Telefone_Familia AS \"Contato\" FROM Familias WHERE Ativo = 1";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            jTableFamiliasNecessitadas.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableFamiliasNecessitadas = new javax.swing.JTable();
        BtnDoar = new javax.swing.JButton();
        lbUsuarioAtivo = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnCadastrarFamilia = new javax.swing.JButton();
        jPanelGraficoBarras = new javax.swing.JPanel();
        btnGerarGraficos = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Menu Principal");

        jLabel2.setText("Usuario ativo");

        jTableFamiliasNecessitadas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTableFamiliasNecessitadas);

        BtnDoar.setText("Doar");
        BtnDoar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDoarActionPerformed(evt);
            }
        });

        lbUsuarioAtivo.setText("USUARIO");

        jLabel3.setText("FAMILIAS NECESSITADAS:");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        btnCadastrarFamilia.setText("Cadastrar Familia");
        btnCadastrarFamilia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarFamiliaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelGraficoBarrasLayout = new javax.swing.GroupLayout(jPanelGraficoBarras);
        jPanelGraficoBarras.setLayout(jPanelGraficoBarrasLayout);
        jPanelGraficoBarrasLayout.setHorizontalGroup(
            jPanelGraficoBarrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 592, Short.MAX_VALUE)
        );
        jPanelGraficoBarrasLayout.setVerticalGroup(
            jPanelGraficoBarrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 340, Short.MAX_VALUE)
        );

        btnGerarGraficos.setText("jButton1");
        btnGerarGraficos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerarGraficosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(BtnDoar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnCadastrarFamilia))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jPanelGraficoBarras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnGerarGraficos)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(29, 29, 29)
                                .addComponent(lbUsuarioAtivo)))))
                .addGap(49, 49, 49))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(50, 50, 50)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BtnDoar)
                            .addComponent(btnCadastrarFamilia))
                        .addGap(64, 64, 64)
                        .addComponent(btnGerarGraficos))
                    .addComponent(jPanelGraficoBarras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lbUsuarioAtivo))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    

    
    
    private void BtnDoarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDoarActionPerformed
        TelaDoar TD = new TelaDoar();
        TD.setVisible(true);
        TD.toFront();


    }//GEN-LAST:event_BtnDoarActionPerformed

    private void btnCadastrarFamiliaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarFamiliaActionPerformed
        TelaCadastro TC = new TelaCadastro();
        TC.setVisible(true);
        TC.toFront();
        TC.CadastrarFamiliaJComboBoxTipo();
        
    }//GEN-LAST:event_btnCadastrarFamiliaActionPerformed

    private void btnGerarGraficosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGerarGraficosActionPerformed
        try {
            String sql = "SELECT a.Nome_Alimento, SUM(l.Qtd_List_int_Doacao) AS Quantidade " +
                         "FROM Alimentos a " +
                         "JOIN Lista_Int_Doacao l ON a.ID_Alimento = l.ID_Alimento_List_Int_Doacao " +
                         "JOIN Transacao_Rec_Int_Doacao t ON l.ID_Int_Doacao_List = t.ID_Int_Doacao_Transac_Rec " +
                         "GROUP BY a.Nome_Alimento";

            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            while (rs.next()) {
                String nomeAlimento = rs.getString("Nome_Alimento");
                int quantidade = rs.getInt("Quantidade");
                dataset.addValue(quantidade, "Quantidade", nomeAlimento);
            }

            JFreeChart chart = ChartFactory.createBarChart(
                    "Gráfico de Barras", // Título do gráfico
                    "Alimento", // Rótulo do eixo X
                    "Quantidade", // Rótulo do eixo Y
                    dataset, // Conjunto de dados
                    PlotOrientation.VERTICAL, // Orientação do gráfico
                    true, // Exibir legenda
                    true, // Exibir tooltips
                    false // URLs de navegação
            );

            jPanelGraficoBarras.removeAll();
            ChartPanel chartPanel = new ChartPanel(chart);
            jPanelGraficoBarras.setLayout(new BorderLayout());
            jPanelGraficoBarras.add(chartPanel, BorderLayout.CENTER);
            jPanelGraficoBarras.validate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        
        
    }//GEN-LAST:event_btnGerarGraficosActionPerformed

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
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnDoar;
    private javax.swing.JButton btnCadastrarFamilia;
    private javax.swing.JButton btnGerarGraficos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanelGraficoBarras;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableFamiliasNecessitadas;
    private javax.swing.JLabel lbUsuarioAtivo;
    // End of variables declaration//GEN-END:variables
}

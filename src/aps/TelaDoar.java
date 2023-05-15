/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package aps;

import java.sql.*;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;



    
public class TelaDoar extends javax.swing.JFrame {

    String UsuarioAtivo = Main.Usuario;
    String IdUsuarioAtivo = Main.IdUsuario;
    
    String idAlimento = "";
    String idIntencaoDoacao = "";
    String idFamilia = "";

    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    
    
    
    

    public TelaDoar() {
        initComponents();
        con = ConnectionApsDB.ConnectionDB();
        updateTableListaAlimentos();
        updateComboboxLista();
        preencherComboBoxAlimento();
        updateTableFamilias();
        updateComboBoxFamilias();
    }
    
    private void updateComboBoxFamilias(){
        try{
            String sql = "select Sobrenome_Familia from Familias where Ativo = 1;";
            pst=con.prepareStatement(sql);
            rs=pst.executeQuery();
            
            jComboBoxFamilias.removeAllItems(); // Limpa os itens existentes na JComboBox
            
            while (rs.next()) {
                String familia = rs.getString("Sobrenome_Familia");
                jComboBoxFamilias.addItem(familia); // Adiciona identificação da Familias à JComboBox
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        
    }
    
    
    private void updateTableFamilias(){
        // \"Sobrenome da Familia\", Endereco_Familia \"Endereço\", ,  
        try{
            String sql = "select ID_Familia \"ID\", Sobrenome_Familia \"Sobrenome da Familia\", Endereco_Familia \"Endereço\", Telefone_Familia \"Contato\" from Familias where Ativo = 1;";
            pst=con.prepareStatement(sql);
            rs=pst.executeQuery();
            jTableListaFamiliasNecessitadas.setModel(DbUtils.resultSetToTableModel(rs));
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        
    }

    
    
    private void preencherComboBoxAlimento() {
        try {
            String sql = "SELECT Nome_Alimento FROM Alimentos";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            jComboBoxAlimento.removeAllItems(); // Limpa os itens existentes na JComboBox

            while (rs.next()) {
                String nomeAlimento = rs.getString("Nome_Alimento");
                jComboBoxAlimento.addItem(nomeAlimento); // Adiciona cada nome de alimento à JComboBox
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    
        String alimentoSelecionado = jComboBoxAlimento.getSelectedItem().toString();
        
        try {
            String sql = "SELECT Unidade_Medida_Alimento FROM Alimentos WHERE Nome_Alimento like ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, alimentoSelecionado);
            rs = pst.executeQuery();

            if (rs.next()) {
                String unidadeMedida = rs.getString("Unidade_Medida_Alimento");
                lbUM.setText(unidadeMedida);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
        try {
            String sql = "SELECT ID_Alimento FROM Alimentos WHERE Nome_Alimento like ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, alimentoSelecionado);
            rs = pst.executeQuery();

            if (rs.next()) {
                int codAlimento = rs.getInt("ID_Alimento");
                idAlimento = String.valueOf(codAlimento);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }
    
    
    
    
    
    
    
    private void updateComboboxLista(){
        jComboBoxIDLista.removeAllItems();
            
        jComboBoxIDLista.addItem("Todas");
        
        try {
            String sql = "SELECT ID_Int_Doacao FROM Intencao_Doacao WHERE ID_Usuario_Int_Doacao like ? AND Ativo = 1";
            pst = con.prepareStatement(sql);
            pst.setString(1,IdUsuarioAtivo);
            rs = pst.executeQuery();    
            

            
            while(rs.next()){
                String idLista = rs.getString("ID_Int_Doacao");
                jComboBoxIDLista.addItem(idLista);
            
            }
            
        }catch (Exception e){
        
        }
    }
    private void updateTableSelecionado(String selecionado){
        if (selecionado.equals("Todas")) {
            // Se o valor selecionado for vazio, chama o método updateTableListaAlimentos() para listar todos os resultados
            updateTableListaAlimentos();
        }else{               
            try {
                String sql = "select a.Nome_Alimento, lista.Qtd_List_int_Doacao , a.Unidade_Medida_Alimento from Lista_Int_Doacao lista, Intencao_Doacao int, Alimentos a WHERE a.ID_Alimento = lista.ID_Alimento_List_Int_Doacao AND int.ID_Usuario_Int_Doacao like ? AND int.ID_Int_Doacao = lista.ID_Int_Doacao_List AND lista.ID_Int_Doacao_List LIKE ? AND int.Ativo = 1;";
                pst = con.prepareStatement(sql);
                pst.setString(1, IdUsuarioAtivo);
                pst.setString(2, selecionado);
                rs = pst.executeQuery();
                System.out.println("Consulta executada: " + sql);
                jTableListaAlimentos.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    
    
    
    private void updateTableListaAlimentos(){
        try {
            String sql = "select a.Nome_Alimento, lista.Qtd_List_int_Doacao , a.Unidade_Medida_Alimento from Lista_Int_Doacao lista, Intencao_Doacao int, Alimentos a WHERE a.ID_Alimento = lista.ID_Alimento_List_Int_Doacao AND int.ID_Usuario_Int_Doacao like ? AND int.ID_Int_Doacao = lista.ID_Int_Doacao_List AND int.Ativo = 1;";
            pst = con.prepareStatement(sql);
            pst.setString(1, IdUsuarioAtivo);
            rs = pst.executeQuery();
            System.out.println("Consulta executada: " + sql);
            jTableListaAlimentos.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
   
        
        /*
        try{
            String sql = "select * from Lista_Int_Doacao lista, Intencao_Doacao int WHERE int.ID_Usuario_Int_Doacao like 1 AND int.ID_Int_Doacao = lista.ID_Int_Doacao_List;";
            pst=con.prepareStatement(sql);
            rs=pst.executeQuery();
            System.out.println("Consulta executada: " + sql);

            while (rs.next()) {
                String idUsuario = rs.getString("ID_Usuario_Int_Doacao");
                String idIntDoacao = rs.getString("ID_Int_Doacao");

                System.out.println("ID_Usuario_Int_Doacao: " + idUsuario);
                System.out.println("ID_Int_Doacao: " + idIntDoacao);
            }

        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        */
      
    }
        
    
    //"select * from Lista_Int_Doacao lista, Intencao_Doacao int WHERE int.ID_Usuario_Int_Doacao = ? AND int.ID_Int_Doacao = lista.ID_int_Doacao_List"
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableListaAlimentos = new javax.swing.JTable();
        jComboBoxIDLista = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        btnAdicionarParaLista = new javax.swing.JButton();
        jComboBoxAlimento = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtQtd = new javax.swing.JTextField();
        lbUM = new javax.swing.JLabel();
        btnAtualizarLista = new javax.swing.JButton();
        jButtonNovaDoação = new javax.swing.JButton();
        btnExcluirLista = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        btnDoarParaFamilia = new javax.swing.JButton();
        jComboBoxFamilias = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableListaFamiliasNecessitadas = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnSairSemDoar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Tela para Doar");

        jTableListaAlimentos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jTableListaAlimentos);

        jComboBoxIDLista.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxIDLista.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxIDListaItemStateChanged(evt);
            }
        });
        jComboBoxIDLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxIDListaActionPerformed(evt);
            }
        });

        jLabel2.setText("Lista de Doacao Selecionada:");

        btnAdicionarParaLista.setText("Adicionar a lista");
        btnAdicionarParaLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarParaListaActionPerformed(evt);
            }
        });

        jComboBoxAlimento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxAlimento.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxAlimentoItemStateChanged(evt);
            }
        });
        jComboBoxAlimento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBoxAlimentoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jComboBoxAlimentoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jComboBoxAlimentoMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jComboBoxAlimentoMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jComboBoxAlimentoMouseReleased(evt);
            }
        });
        jComboBoxAlimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxAlimentoActionPerformed(evt);
            }
        });

        jLabel3.setText("Alimento:");

        jLabel4.setText("Qtd:");

        txtQtd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQtdActionPerformed(evt);
            }
        });

        lbUM.setText("Unidade de medida");

        btnAtualizarLista.setText("Carregar Lista");
        btnAtualizarLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarListaActionPerformed(evt);
            }
        });

        jButtonNovaDoação.setText(" Nova Lista de Doação");
        jButtonNovaDoação.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNovaDoaçãoActionPerformed(evt);
            }
        });

        btnExcluirLista.setText("Excluir Lista de Doacao");
        btnExcluirLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirListaActionPerformed(evt);
            }
        });

        jLabel5.setText("Seleciona a lista a esquerda a uma familia abaixo  para quem deseja doar");

        btnDoarParaFamilia.setText("Doar para essa familia");
        btnDoarParaFamilia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoarParaFamiliaActionPerformed(evt);
            }
        });

        jComboBoxFamilias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxFamilias.setEnabled(false);
        jComboBoxFamilias.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxFamiliasItemStateChanged(evt);
            }
        });
        jComboBoxFamilias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBoxFamiliasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jComboBoxFamiliasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jComboBoxFamiliasMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jComboBoxFamiliasMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jComboBoxFamiliasMouseReleased(evt);
            }
        });
        jComboBoxFamilias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxFamiliasActionPerformed(evt);
            }
        });

        jLabel6.setText("Familia Selecionada:");

        jTableListaFamiliasNecessitadas.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableListaFamiliasNecessitadas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableListaFamiliasNecessitadasMouseClicked(evt);
            }
        });
        jTableListaFamiliasNecessitadas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTableListaFamiliasNecessitadasKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableListaFamiliasNecessitadasKeyReleased(evt);
            }
        });
        jScrollPane4.setViewportView(jTableListaFamiliasNecessitadas);

        jLabel7.setText("Familias Necessitadas:");

        btnSairSemDoar.setText("Sair sem Doar");
        btnSairSemDoar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairSemDoarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(21, 21, 21)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jComboBoxAlimento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(37, 37, 37))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(42, 42, 42)
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtQtd, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lbUM)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addComponent(btnAdicionarParaLista, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(jComboBoxIDLista, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(btnAtualizarLista, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(jButtonNovaDoação, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnExcluirLista, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(18, 18, 18)
                                        .addComponent(jComboBoxFamilias, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(13, 13, 13)))
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel7))
                                    .addGap(53, 53, 53))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(btnSairSemDoar, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(btnDoarParaFamilia, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(19, 19, 19)))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBoxIDLista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(btnAtualizarLista))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonNovaDoação, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                            .addComponent(btnExcluirLista, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAdicionarParaLista, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(jComboBoxAlimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(txtQtd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbUM))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jComboBoxFamilias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnDoarParaFamilia, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSairSemDoar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(194, 194, 194))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdicionarParaListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarParaListaActionPerformed
        String qtd = txtQtd.getText();
        if (qtd.equals("")){ 
            JOptionPane.showMessageDialog(null, "Favor inserir uma quantidade para o alimento doado","Alerta",JOptionPane.INFORMATION_MESSAGE);

        }else{
            try {
                // Código para desconectar o sistema do banco de dados
                con.close();
            } catch (SQLException e) {
                // Tratar exceções ao fechar a conexão
                e.printStackTrace();
            } finally {
                // Código para reconectar o sistema ao banco de dados
                try {
                    Thread.sleep(1000); // Aguarda 1 segundo (opcional, você pode ajustar o tempo conforme necessário)
                    con = ConnectionApsDB.ConnectionDB();// Reconecta ao banco de dados usando as informações de conexão
                } catch (InterruptedException ex) {
                    // Tratar exceções ao esperar
                    ex.printStackTrace();
                }
            }
            con = ConnectionApsDB.ConnectionDB();


            System.out.println("Botão Adicionar Para Lista acionado");

            String lista = jComboBoxIDLista.getSelectedItem().toString();
            System.out.println("Lista selecionada: " + lista);

            String alimentoSelecionado = idAlimento;
            System.out.println("Alimento selecionado: " + alimentoSelecionado);

            String quantidade = txtQtd.getText();
            System.out.println("Quantidade: " + quantidade);

            try {
                String sql = "INSERT INTO Lista_Int_Doacao (ID_Int_Doacao_List, ID_Alimento_List_Int_Doacao, Qtd_List_int_Doacao) VALUES (?, ?, ?)";
                pst = con.prepareStatement(sql);
                pst.setString(1, lista);
                pst.setString(2, alimentoSelecionado);
                pst.setString(3, quantidade);
                pst.executeUpdate();

                System.out.println("Dados adicionados à tabela Lista_Int_Doacao com sucesso!");

                // Limpar os campos ou realizar outras ações necessárias após a inserção dos dados

            } catch (Exception e) {
                System.err.println("Erro ao adicionar dados à tabela Lista_Int_Doacao: " + e.getMessage());
            }

            String listaSelecionada = jComboBoxIDLista.getSelectedItem().toString();
            updateTableSelecionado(listaSelecionada);
        }


    }//GEN-LAST:event_btnAdicionarParaListaActionPerformed

    private void txtQtdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQtdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQtdActionPerformed

    private void jComboBoxIDListaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxIDListaItemStateChanged
        btnAdicionarParaLista.setEnabled(false);
        Object listaselecionada = jComboBoxIDLista.getSelectedItem();
        if (listaselecionada != null) {
            btnAtualizarLista.doClick();
            idIntencaoDoacao = String.valueOf(listaselecionada);
        }
    }//GEN-LAST:event_jComboBoxIDListaItemStateChanged

    private void btnAtualizarListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarListaActionPerformed
        
        String listaSelecionada = jComboBoxIDLista.getSelectedItem().toString();
        
        if (listaSelecionada.equals("Todas")) {  // Usando o .equals() para comparar strings
            btnAdicionarParaLista.setEnabled(false);
            btnExcluirLista.setEnabled(false);
            btnDoarParaFamilia.setEnabled(false);
            updateTableListaAlimentos();
        } else {
            btnAdicionarParaLista.setEnabled(true);
            btnExcluirLista.setEnabled(true);
            btnDoarParaFamilia.setEnabled(true);
            updateTableSelecionado(listaSelecionada);
        }

    }//GEN-LAST:event_btnAtualizarListaActionPerformed

    private void jComboBoxIDListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxIDListaActionPerformed

    }//GEN-LAST:event_jComboBoxIDListaActionPerformed

    private void jComboBoxAlimentoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxAlimentoItemStateChanged

    }//GEN-LAST:event_jComboBoxAlimentoItemStateChanged

    private void jComboBoxAlimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxAlimentoActionPerformed
        System.out.println("Evento actionPerformed acionado!");

        Object selectedObject = jComboBoxAlimento.getSelectedItem();
        if (selectedObject != null) {
            String alimentoSelecionado = jComboBoxAlimento.getSelectedItem().toString();

            try {
                String sql = "SELECT Unidade_Medida_Alimento FROM Alimentos WHERE Nome_Alimento like ?";
                pst = con.prepareStatement(sql);
                pst.setString(1, alimentoSelecionado);
                rs = pst.executeQuery();

                if (rs.next()) {
                    String unidadeMedida = rs.getString("Unidade_Medida_Alimento");
                    lbUM.setText(unidadeMedida);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
            
            
            try {
                String sql = "SELECT ID_Alimento FROM Alimentos WHERE Nome_Alimento like ?";
                pst = con.prepareStatement(sql);
                pst.setString(1, alimentoSelecionado);
                rs = pst.executeQuery();

                if (rs.next()) {
                    int codAlimento = rs.getInt("ID_Alimento");
                    idAlimento = String.valueOf(codAlimento);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
            
            
            
            
        }else{
            preencherComboBoxAlimento();
            String alimentoSelecionado = jComboBoxAlimento.getSelectedItem().toString();
            try {
                String sql = "SELECT ID_Alimento FROM Alimentos WHERE Nome_Alimento like ?";
                pst = con.prepareStatement(sql);
                pst.setString(1, alimentoSelecionado);
                rs = pst.executeQuery();

                if (rs.next()) {
                    int codAlimento = rs.getInt("ID_Alimento");
                    idAlimento = String.valueOf(codAlimento);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_jComboBoxAlimentoActionPerformed

    private void jComboBoxAlimentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBoxAlimentoMouseClicked

    }//GEN-LAST:event_jComboBoxAlimentoMouseClicked

    private void jComboBoxAlimentoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBoxAlimentoMouseEntered

    }//GEN-LAST:event_jComboBoxAlimentoMouseEntered

    private void jComboBoxAlimentoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBoxAlimentoMouseExited

    }//GEN-LAST:event_jComboBoxAlimentoMouseExited

    private void jComboBoxAlimentoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBoxAlimentoMousePressed

    }//GEN-LAST:event_jComboBoxAlimentoMousePressed

    private void jComboBoxAlimentoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBoxAlimentoMouseReleased

    }//GEN-LAST:event_jComboBoxAlimentoMouseReleased

    private void jButtonNovaDoaçãoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNovaDoaçãoActionPerformed
        try {
            String sql = "INSERT INTO Intencao_Doacao (ID_Usuario_Int_Doacao) VALUES (?)";
            pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, IdUsuarioAtivo);
            pst.executeUpdate();
            System.out.println("Dados adicionados à tabela Intenção de Doação com sucesso!");
            
            ResultSet generatedKeys = pst.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idIntDoacao = generatedKeys.getInt(1);
                System.out.println("ID_Int_Doacao gerado: " + idIntDoacao);
                
            
            
            updateComboboxLista();
            jComboBoxIDLista.setSelectedItem(String.valueOf(idIntDoacao));
            
            String listaSelecionada = jComboBoxIDLista.getSelectedItem().toString();
            updateTableSelecionado(listaSelecionada);
            
            btnAdicionarParaLista.setEnabled(true);
            }
        } catch (Exception e) {
            System.err.println("Erro ao adicionar dados à tabela: " + e.getMessage());
        }
            
            


        
    }//GEN-LAST:event_jButtonNovaDoaçãoActionPerformed

    private void btnExcluirListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirListaActionPerformed
        try {
            // Excluir da tabela Lista_Int_Doacao
            String deleteListaIntDoacao = "DELETE FROM Lista_Int_Doacao WHERE ID_Int_Doacao_List = ?";
            PreparedStatement pstDeleteListaIntDoacao = con.prepareStatement(deleteListaIntDoacao);
            pstDeleteListaIntDoacao.setString(1, idIntencaoDoacao);
            pstDeleteListaIntDoacao.executeUpdate();

            // Excluir da tabela Intencao_Doacao
            String deleteIntencaoDoacao = "DELETE FROM Intencao_Doacao WHERE ID_Int_Doacao = ?";
            PreparedStatement pstDeleteIntencaoDoacao = con.prepareStatement(deleteIntencaoDoacao);
            pstDeleteIntencaoDoacao.setString(1, idIntencaoDoacao);
            pstDeleteIntencaoDoacao.executeUpdate();

            System.out.println("Registros excluídos com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao excluir registros das tabelas: " + e.getMessage());
        }
        updateTableListaAlimentos();
        updateComboboxLista();
    }//GEN-LAST:event_btnExcluirListaActionPerformed

    private void btnDoarParaFamiliaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoarParaFamiliaActionPerformed
        try {
            // Atualizar o campo "Ativo" nas tabelas Familias e Intencao_Doacao
            String updateFamilias = "UPDATE Familias SET Ativo = 0 WHERE ID_Familia = ?";
            String updateIntencaoDoacao = "UPDATE Intencao_Doacao SET Ativo = 0 WHERE ID_Int_Doacao = ?";

            PreparedStatement pstFamilias = con.prepareStatement(updateFamilias);
            pstFamilias.setString(1, idFamilia);
            pstFamilias.executeUpdate();

            PreparedStatement pstIntencaoDoacao = con.prepareStatement(updateIntencaoDoacao);
            pstIntencaoDoacao.setString(1, idIntencaoDoacao);
            pstIntencaoDoacao.executeUpdate();

            // Inserir na tabela Transacao_Rec_Int_Doacao
            String insertTransacaoRecIntDoacao = "INSERT INTO Transacao_Rec_Int_Doacao (ID_Int_Doacao_Transac_Rec, ID_Familia_Transac_Rec_Int_Doacao) VALUES (?, ?)";
            PreparedStatement pstTransacaoRecIntDoacao = con.prepareStatement(insertTransacaoRecIntDoacao);
            pstTransacaoRecIntDoacao.setString(1, idIntencaoDoacao);
            pstTransacaoRecIntDoacao.setString(2, idFamilia);
            pstTransacaoRecIntDoacao.executeUpdate();

            System.out.println("Registros atualizados e inseridos com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao atualizar registros e inserir na tabela: " + e.getMessage());
        }
        
        updateComboboxLista();
        preencherComboBoxAlimento();
        updateTableFamilias();
        updateComboBoxFamilias();
        
        TelaPrincipal TP = new TelaPrincipal();
        TP.AtualizarDados();
        
        
    }//GEN-LAST:event_btnDoarParaFamiliaActionPerformed

    private void jComboBoxFamiliasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxFamiliasItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxFamiliasItemStateChanged

    private void jComboBoxFamiliasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBoxFamiliasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxFamiliasMouseClicked

    private void jComboBoxFamiliasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBoxFamiliasMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxFamiliasMouseEntered

    private void jComboBoxFamiliasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBoxFamiliasMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxFamiliasMouseExited

    private void jComboBoxFamiliasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBoxFamiliasMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxFamiliasMousePressed

    private void jComboBoxFamiliasMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBoxFamiliasMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxFamiliasMouseReleased

    private void jComboBoxFamiliasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxFamiliasActionPerformed

    }//GEN-LAST:event_jComboBoxFamiliasActionPerformed

    private void jTableListaFamiliasNecessitadasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableListaFamiliasNecessitadasMouseClicked
    int row = jTableListaFamiliasNecessitadas.getSelectedRow();
    Object valueColuna1  = jTableListaFamiliasNecessitadas.getValueAt(row, 0);
    Object valueColuna2  = jTableListaFamiliasNecessitadas.getValueAt(row, 1);
    
    // Faça algo com o valor da primeira coluna da linha selecionada
    if (valueColuna1 != null && valueColuna2 != null) {
        String familiaSelecionadaID = valueColuna1.toString();
        String familiaSelecionadaSobrenome = valueColuna2.toString();
        System.out.println("Família selecionada (Coluna 1): " + familiaSelecionadaID);
        System.out.println("Família selecionada (Coluna 2): " + familiaSelecionadaSobrenome);
                
        idFamilia = familiaSelecionadaID;
        jComboBoxFamilias.setSelectedItem(valueColuna2);
        // Ou faça outra ação com o valor da célula
        // ...
    }
    }//GEN-LAST:event_jTableListaFamiliasNecessitadasMouseClicked

    private void jTableListaFamiliasNecessitadasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableListaFamiliasNecessitadasKeyPressed

    }//GEN-LAST:event_jTableListaFamiliasNecessitadasKeyPressed

    private void jTableListaFamiliasNecessitadasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableListaFamiliasNecessitadasKeyReleased
    int row = jTableListaFamiliasNecessitadas.getSelectedRow();
    Object valueColuna1  = jTableListaFamiliasNecessitadas.getValueAt(row, 0);
    Object valueColuna2  = jTableListaFamiliasNecessitadas.getValueAt(row, 1);
    
    // Faça algo com o valor da primeira coluna da linha selecionada
    if (valueColuna1 != null && valueColuna2 != null) {
        String familiaSelecionadaID = valueColuna1.toString();
        String familiaSelecionadaSobrenome = valueColuna2.toString();
        System.out.println("Família selecionada (Coluna 1): " + familiaSelecionadaID);
        System.out.println("Família selecionada (Coluna 2): " + familiaSelecionadaSobrenome);
                
        idFamilia = familiaSelecionadaID;
        jComboBoxFamilias.setSelectedItem(valueColuna2);
        // Ou faça outra ação com o valor da célula
        // ...
    }        // TODO add your handling code here:
    }//GEN-LAST:event_jTableListaFamiliasNecessitadasKeyReleased

    private void btnSairSemDoarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairSemDoarActionPerformed
        TelaPrincipal TP = new TelaPrincipal();
        TP.AtualizarDados();
        this.dispose();
    }//GEN-LAST:event_btnSairSemDoarActionPerformed

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
            java.util.logging.Logger.getLogger(TelaDoar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaDoar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaDoar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaDoar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaDoar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionarParaLista;
    private javax.swing.JButton btnAtualizarLista;
    private javax.swing.JButton btnDoarParaFamilia;
    private javax.swing.JButton btnExcluirLista;
    private javax.swing.JButton btnSairSemDoar;
    private javax.swing.JButton jButtonNovaDoação;
    private javax.swing.JComboBox<String> jComboBoxAlimento;
    private javax.swing.JComboBox<String> jComboBoxFamilias;
    private javax.swing.JComboBox<String> jComboBoxIDLista;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTableListaAlimentos;
    private javax.swing.JTable jTableListaFamiliasNecessitadas;
    private javax.swing.JLabel lbUM;
    private javax.swing.JTextField txtQtd;
    // End of variables declaration//GEN-END:variables
}

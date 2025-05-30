/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package View;

import java.awt.Frame;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import DAO.*;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.Dish;
/**
 *
 * @author Admin
 */
public class SearchDishFrm extends javax.swing.JDialog {

    /**
     * Creates new form SearchDishFrm
     */
    public static final int EDIT_MODE = 1;
    public static final int DELETE_MODE = 2;
    private int mode;
    public boolean check = true;

    /**
     * Creates new form SearchDishFrm
     */
    
    private void configureMode() {
        switch (mode) {
            
            case EDIT_MODE:
                
                btnEditDish.setVisible(true);
                btnDeleteDish.setVisible(false);
                break;
            case DELETE_MODE:
               
                btnEditDish.setVisible(false);
                btnDeleteDish.setVisible(true);
                break;
        }
    }
    public SearchDishFrm(java.awt.Dialog parent, boolean modal,int mode) {
        super(parent, modal);
        this.mode = mode;
        initComponents();
        configureMode();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        DishList = new javax.swing.JTable();
        btnEditDish = new javax.swing.JButton();
        btnDeleteDish = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Dishes List");

        DishList.setModel(loadTableModel());
        jScrollPane1.setViewportView(DishList);

        btnEditDish.setText("Edit");
        btnEditDish.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditDishActionPerformed(evt);
            }
        });

        btnDeleteDish.setText("Delete");
        btnDeleteDish.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteDishActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(155, 155, 155)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(44, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnEditDish)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDeleteDish)
                .addGap(34, 34, 34))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditDish)
                    .addComponent(btnDeleteDish))
                .addGap(27, 27, 27))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public DefaultTableModel loadTableModel(){
        String[] columnNames = { "ID","Name", "Type", "Price","Description","" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0){
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                
                if (columnIndex == 5) return Boolean.class;
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
               
                return column == 5;
            }
        };


        DishDAO dd = new DishDAO();
        List<Dish> dishList = dd.getDishList();

        for (Dish dish :dishList) {
            
            
            Object[] rowData = { dish.getId(), dish.getName(), dish.getType(),dish.getPrice(),dish.getDes(), false};
            model.addRow(rowData);
                
            
        }
        return model;
    }
    private Dish getDish() {
        Dish dish = null;
        int count = 0;
        DefaultTableModel model = (DefaultTableModel) DishList.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            Boolean checked = (Boolean) model.getValueAt(i, 5);
            if (Boolean.TRUE.equals(checked)) {
                count++;
                // Gán dish tạm thời
                dish = new Dish();
                dish.setId((Integer) model.getValueAt(i, 0));
                dish.setName((String) model.getValueAt(i, 1));
                dish.setType((String) model.getValueAt(i, 2));
                dish.setPrice((Integer) model.getValueAt(i, 3));
                dish.setDes((String) model.getValueAt(i, 4));
            }
        }

        if (count == 1) {
            check = true;
            return dish;
        } else {
            check = false;
            JOptionPane.showMessageDialog(this, "Only 1 dish can be edited at a time");
            return null;
        }
}

    private List<Dish> getPickedDish(){
        List<Dish> dishes = new ArrayList<>();
        
        DefaultTableModel model = (DefaultTableModel) DishList.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            Boolean checked = (Boolean) model.getValueAt(i, 5);
            if (Boolean.TRUE.equals(checked)) {
                Dish dish = new Dish();
                dish.setId ((Integer)model.getValueAt(i, 0));
                dish.setName((String)model.getValueAt(i, 1));
                dish.setType((String)model.getValueAt(i, 2));
                dish.setPrice((Integer)model.getValueAt(i, 3));
                dish.setDes((String)model.getValueAt(i, 4));
                dishes.add(dish);
                
            }
            
        }
        return dishes;
    }
    private void btnEditDishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditDishActionPerformed
        // TODO add your handling code here:
        Dish selectedDish = getDish();
        if (selectedDish != null) {
            EditDishFrm ef = new EditDishFrm(this, true, selectedDish);
            ef.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            ef.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    DishList.setModel(loadTableModel());
                }
            });
            ef.setVisible(true);
        }
    }//GEN-LAST:event_btnEditDishActionPerformed

    private void btnDeleteDishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteDishActionPerformed
        // TODO add your handling code here:
        DeleteDishFrm ef =  new DeleteDishFrm(this, true, getPickedDish());
          ef.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            ef.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                DishList.setModel(loadTableModel()); 
            }
            });
            ef.setVisible(true);
    }//GEN-LAST:event_btnDeleteDishActionPerformed

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
            java.util.logging.Logger.getLogger(SearchDishFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SearchDishFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SearchDishFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SearchDishFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                SearchDishFrm dialog = new SearchDishFrm(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable DishList;
    private javax.swing.JButton btnDeleteDish;
    private javax.swing.JButton btnEditDish;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}

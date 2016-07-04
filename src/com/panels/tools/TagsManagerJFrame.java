/* **********   TagsManagerJFrame.java   **********
 *
 * This piece of garbage was brought to you by nothing less than the almighty lord
 * of programming, the Java God and ruler of all the non living things, McBeengs, 
 * A.K.A. myself. I don't mind anyone steal or using my codes at their own business,
 * but at least, and I meant VERY least, give me the proper credit for it. I really
 * don't know what the code below does at this point in time while I write this stuff, 
 * but if you took all this time to sit, rip the .java files and read all this 
 * unnecessary bullshit, you know for what you came, doesn't ?
 * 
 * Copyright(c) {YEAR!!!} Mc's brilliant mind. All Rights (kinda) Reserved.
 */

 /*
 * {Insert class description here}
 */
package com.panels.tools;

import com.util.UsefulMethods;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class TagsManagerJFrame extends javax.swing.JFrame {

    private Connection conn;
    private DefaultTableModel model;

    public TagsManagerJFrame() {
        initComponents();
        conn = UsefulMethods.getDBInstance();

        model = (DefaultTableModel) table.getModel();

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                final int id = Integer.parseInt(model.getValueAt(row, 0).toString());
                final String tag = model.getValueAt(row, 1).toString();

                if (e.getClickCount() == 1 && e.getButton() == 3) {
                    JPopupMenu menu = new JPopupMenu("Delete tag");
                    JMenuItem item = new JMenuItem(new AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (JOptionPane.showConfirmDialog(null, "Delete tag \"" + tag + "\"?") == JOptionPane.YES_OPTION) {
                                try {
                                    conn.createStatement().execute("DELETE FROM tag WHERE id = " + id);
                                    setTable();
                                } catch (SQLException ex) {
                                    Logger.getLogger(TagsManagerJFrame.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    });
                    item.setText("Delete tag \"" + tag + "\"");
                    menu.add(item);
                    menu.show(e.getComponent(), e.getX(), e.getY());
                } else if (e.getButton() == 1) {
                    boolean enabled = false;
                    for (int i = model.getRowCount() - 1; i >= 0; i--) {
                        if (id > 0 && Boolean.parseBoolean(model.getValueAt(i, 2).toString())) {
                            enabled = true;
                        }
                    }
                    jButton1.setEnabled(enabled);
                }
            }
        });

        table.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                try {
                    CustomTableModel model = (CustomTableModel) e.getSource();
                    Object obj = model.getValueAt(e.getFirstRow(), 0);
                    int id;
                    
                    if (obj instanceof String) {
                        id = Integer.parseInt(model.getValueAt(e.getFirstRow(), 0).toString());
                    } else {
                        id = (int) model.getValueAt(e.getFirstRow(), 0);
                    }

                    if (id > 0 && e.getType() == TableModelEvent.UPDATE) {
                        String text = (String) table.getModel().getValueAt(e.getFirstRow(), 1);
                        PreparedStatement statement = conn.prepareStatement("UPDATE tag SET tag = ? WHERE id = ?");
                        statement.setString(1, text);
                        statement.setInt(2, id);
                        statement.execute();
                        statement.close();
                        setTable();

                    }
                } catch (SQLException ex) {
                    Logger.getLogger(TagsManagerJFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ArrayIndexOutOfBoundsException ex) {
                }
            }
        });
        setTable();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        message = new javax.swing.JLabel();
        button = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        table.setModel(new CustomTableModel());
        table.setColumnSelectionAllowed(true);
        table.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(table);
        table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setPreferredWidth(40);
            table.getColumnModel().getColumn(0).setMaxWidth(40);
            table.getColumnModel().getColumn(2).setPreferredWidth(22);
            table.getColumnModel().getColumn(2).setMaxWidth(22);
        }

        message.setText("Showing all &num tags");

        button.setText("Add Tag");
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });

        jButton1.setText("Erase Selected");
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(message, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addComponent(message, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonActionPerformed
        String tag = JOptionPane.showInputDialog(null, "Type the new tag below:", "", JOptionPane.INFORMATION_MESSAGE);

        if (tag != null && !tag.isEmpty()) {
            try {
                PreparedStatement statement = conn.prepareStatement("INSERT INTO tag (`id`, `tag`) VALUES (NULL, ?)");
                statement.setString(1, tag);
                statement.execute();
                statement.close();
                setTable();
            } catch (SQLException ex) {
                Logger.getLogger(TagsManagerJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_buttonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int enabled = 0;
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            if (Boolean.parseBoolean(model.getValueAt(i, 2).toString())) {
                enabled++;
                list.add(Integer.parseInt(model.getValueAt(i, 0).toString()));
            }
        }

        if (JOptionPane.showConfirmDialog(null, "You really want to delete " + enabled + " tags?", "",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            for (Integer i : list) {
                try {
                    conn.createStatement().execute("DELETE FROM tag WHERE id = " + i);
                    conn.createStatement().execute("DELETE FROM inner_tag WHERE tag_id = " + i);
                    jButton1.setEnabled(false);
                    setTable();
                } catch (SQLException ex) {
                    Logger.getLogger(TagsManagerJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void setTable() {
        //Remove rows one by one from the end of the table
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
        }

        new Thread() {
            @Override
            public void run() {
                try {
                    ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM tag");

                    while (rs.next()) {
                        model.addRow(new Object[]{rs.getInt("id"), rs.getString("tag"), false});
                    }

                    if (model.getRowCount() == 0) {
                        message.setText("Create new tags using the button aside.");
                        model.addRow(new Object[]{"0", "No tags registered"});
                    } else {
                        message.setText("Showing all " + model.getRowCount() + " tags.");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(TagsManagerJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
        table.revalidate();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel message;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables

    private class CustomTableModel extends DefaultTableModel {

        public CustomTableModel() {
            super(new String[]{"Id", "Tag", ""}, 0);
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            Class send = String.class;
            switch (columnIndex) {
                case 0:
                    send = Integer.class;
                    break;
                case 2:
                    send = Boolean.class;
                    break;
            }
            return send;
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return column > 0;
        }

        @Override
        public void setValueAt(Object aValue, int row, int column) {
            if (aValue instanceof Boolean && column == 2) {
                @SuppressWarnings("UseOfObsoleteCollectionType")
                Vector rowData = (Vector) getDataVector().get(row);
                rowData.set(2, (Boolean) aValue);
            } else {
                super.setValueAt(aValue, row, column);
            }
        }
    }
}

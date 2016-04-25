package com.panels.options;

import com.util.UsefulMethods;
import com.util.xml.XmlManager;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import static javax.swing.tree.TreeSelectionModel.SINGLE_TREE_SELECTION;
import org.xml.sax.SAXException;

public class OptionsJFrame extends javax.swing.JFrame {

    private final String originalContent;
    private DefaultTreeModel model;
    private DefaultMutableTreeNode hierarchy;
    private DefaultMutableTreeNode batch;
    private final GridBagConstraints c;
    private final XmlManager xml;
    private final XmlManager language;

    public OptionsJFrame() throws SAXException, IOException {
        xml = UsefulMethods.loadManager(UsefulMethods.OPTIONS);
        language = UsefulMethods.loadManager(UsefulMethods.LANGUAGE);

        originalContent = xml.toString();
        initComponents();
        setTree();

        mainContainer.setLayout(new GridBagLayout());
        c = new GridBagConstraints();

        final Option1 o1 = new Option1(xml);
        final Option2 o2 = new Option2(xml);
        final Option3 o3 = new Option3(xml);
        final Option4 o4 = new Option4(xml);
        final Option5 o5 = new Option5(xml);
        final Option6 o6 = new Option6(xml);
        final Option7 o7 = new Option7(xml);
        o1.setVisible(true);
        o2.setVisible(false);
        o3.setVisible(false);
        o4.setVisible(false);
        o5.setVisible(false);
        o6.setVisible(false);
        o7.setVisible(false);
        c.gridx = 0;
        c.gridy = 0;
        mainContainer.add(o1, c);
        mainContainer.add(o2, c);
        mainContainer.add(o3, c);
        mainContainer.add(o4, c);
        mainContainer.add(o5, c);
        mainContainer.add(o6, c);
        mainContainer.add(o7, c);

        optionsTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switch (optionsTree.getSelectionModel().getLeadSelectionRow()) {
                    case 0:
                        o1.setVisible(false);
                        o2.setVisible(false);
                        o3.setVisible(false);
                        o4.setVisible(false);
                        o5.setVisible(false);
                        o6.setVisible(false);
                        o7.setVisible(false);
                        break;
                    case 1:
                        o1.setVisible(true);
                        o2.setVisible(false);
                        o3.setVisible(false);
                        o4.setVisible(false);
                        o5.setVisible(false);
                        o6.setVisible(false);
                        o7.setVisible(false);
                        break;
                    case 2:
                        o1.setVisible(false);
                        o2.setVisible(true);
                        o3.setVisible(false);
                        o4.setVisible(false);
                        o5.setVisible(false);
                        o6.setVisible(false);
                        o7.setVisible(false);
                        break;
                    case 3:
                        o1.setVisible(false);
                        o2.setVisible(false);
                        o3.setVisible(false);
                        o4.setVisible(false);
                        o5.setVisible(false);
                        o6.setVisible(false);
                        o7.setVisible(false);
                        break;
                    case 4:
                        o1.setVisible(false);
                        o2.setVisible(false);
                        o3.setVisible(true);
                        o4.setVisible(false);
                        o5.setVisible(false);
                        o6.setVisible(false);
                        o7.setVisible(false);
                        break;
                    case 5:
                        o1.setVisible(false);
                        o2.setVisible(false);
                        o3.setVisible(false);
                        o4.setVisible(true);
                        o5.setVisible(false);
                        o6.setVisible(false);
                        o7.setVisible(false);
                        break;
                    case 6:
                        o1.setVisible(false);
                        o2.setVisible(false);
                        o3.setVisible(false);
                        o4.setVisible(false);
                        o5.setVisible(true);
                        o6.setVisible(false);
                        o7.setVisible(false);
                        break;
                    case 7:
                        o1.setVisible(false);
                        o2.setVisible(false);
                        o3.setVisible(false);
                        o4.setVisible(false);
                        o5.setVisible(false);
                        o6.setVisible(true);
                        o7.setVisible(false);
                        break;
                    case 8:
                        o1.setVisible(false);
                        o2.setVisible(false);
                        o3.setVisible(false);
                        o4.setVisible(false);
                        o5.setVisible(false);
                        o6.setVisible(false);
                        o7.setVisible(true);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void setTree() {
        this.setTitle(language.getContentById("options"));
        //This creates the root of the tree on which all the others will be linked (not visible)
        hierarchy = new DefaultMutableTreeNode("root");

        //This sets the first group of options...
        DefaultMutableTreeNode section1 = new DefaultMutableTreeNode(language.getContentById("first"));

        //... and add them to the root
        hierarchy.add(section1);

        //Sets all the options pulled from the file... 
        String[] section1Ops = new String[2];

        section1Ops[0] = language.getContentByName("mainLabel", 0);
        section1Ops[1] = language.getContentByName("mainLabel", 1);

        //... and sets them to the section
        for (String section1Op : section1Ops) {
            batch = new DefaultMutableTreeNode(section1Op);
            section1.add(batch);
        }

        //And repeats the whole process until everything is in place
        DefaultMutableTreeNode section2 = new DefaultMutableTreeNode(language.getContentById("second"));
        hierarchy.add(section2);
        String[] section2Ops = new String[5];

        for (int i = 2; i < language.getAllContentsByName("mainLabel").size() - 2; i++) {
            section2Ops[i - 2] = language.getContentByName("mainLabel", i);
        }
        for (String section2Op : section2Ops) {
            batch = new DefaultMutableTreeNode(section2Op);
            section2.add(batch);
        }

        model = new DefaultTreeModel(hierarchy);

        optionsTree.setModel(model);
        optionsTree.setCellRenderer(new JTreeRenderer());
        optionsTree.getSelectionModel().setSelectionMode(SINGLE_TREE_SELECTION);

        for (int i = 0; i < optionsTree.getRowCount(); i++) {
            optionsTree.expandRow(i);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cancelButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        optionsTree = new javax.swing.JTree();
        mainContainer = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        cancelButton.setText(language.getContentById("cancel"));
        cancelButton.setFocusable(false);
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        saveButton.setText(language.getContentById("save"));
        saveButton.setFocusPainted(false);
        saveButton.setFocusable(false);
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        optionsTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        optionsTree.setRootVisible(false);
        jScrollPane1.setViewportView(optionsTree);

        javax.swing.GroupLayout mainContainerLayout = new javax.swing.GroupLayout(mainContainer);
        mainContainer.setLayout(mainContainerLayout);
        mainContainerLayout.setHorizontalGroup(
            mainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        mainContainerLayout.setVerticalGroup(
            mainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 496, Short.MAX_VALUE)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(mainContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                    .addComponent(mainContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(saveButton))
                .addContainerGap())
        );

        cancelButton.setFocusPainted(false);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        int option = JOptionPane.showConfirmDialog(this, language.getContentById("saveConfirm"),
                language.getContentById("save"), 0);

        if (option == JOptionPane.OK_OPTION) {
            try {
                xml.saveXml();
                this.dispose();
            } catch (IOException ex) {
                Logger.getLogger(OptionsJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        if (xml.toString().equals(originalContent)) {
            this.dispose();
        } else {
            int option = JOptionPane.showConfirmDialog(this, language.getContentById("cancelConfirm"),
                    language.getContentById("cancel"), 0);

            if (option == JOptionPane.OK_OPTION) {
                this.dispose();
            }
        }
    }//GEN-LAST:event_cancelButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainContainer;
    private javax.swing.JTree optionsTree;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables

    private class JTreeRenderer extends DefaultTreeCellRenderer implements TreeCellRenderer {

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected,
                boolean expanded, boolean leaf, int row, boolean hasFocus) {

            super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

            setOpenIcon(new ImageIcon(getClass().getResource("/com/style/icons/openArrow.png")));
            setClosedIcon(new ImageIcon(getClass().getResource("/com/style/icons/closedArrow.png")));
            setLeafIcon(new ImageIcon(getClass().getResource("/com/style/icons/mainBullet.png")));

            return this;
        }
    }
}

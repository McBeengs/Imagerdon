package com.panels.options;

import com.util.crypto.PasswordManager;
import com.util.xml.XmlManager;
import javax.swing.JFileChooser;

public class Option7 extends javax.swing.JPanel {

    private final XmlManager xml;
    private final XmlManager language;
    private final PasswordManager pass;
    private final JFileChooser directory;
    private int buttonPressedResult;
    private String userOutput;

    public Option7(XmlManager xml) {
        this.xml = xml;
        language = new XmlManager();
        pass = new PasswordManager();
        userOutput = xml.getContentById("FAoutput");
        directory = new JFileChooser(userOutput);

        String set = xml.getContentByName("language", 0);
        set = set.substring(0, set.indexOf(","));
        language.loadFile("language\\" + set.toLowerCase() + ".xml");
        initComponents();

        if (Boolean.getBoolean(xml.getContentById("sub"))) {
            folderTextField.setEnabled(false);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        mainLabel = new javax.swing.JLabel();
        topSeparator = new javax.swing.JSeparator();
        folderLabel = new javax.swing.JLabel();
        folderTextField = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();

        setMinimumSize(new java.awt.Dimension(516, 434));

        mainLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 24)); // NOI18N
        mainLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mainLabel.setText(language.getContentByName("mainLabel", 6) + " " + language.getContentById("options"));

        folderLabel.setText("Set default folder name");

        folderTextField.setEditable(false);
        folderTextField.setText(xml.getContentById("E621output"));
        folderTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                folderTextFieldMouseClicked(evt);
            }
        });

        jCheckBox1.setText("Use advance naming options");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Doesn't auto name the files with the image name, instead...");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("Name then by order of upload");
        jRadioButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton1MouseClicked(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Name them by time of download");
        jRadioButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(topSeparator, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainLabel)
                .addContainerGap(296, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(folderLabel)
                        .addGap(18, 18, 18)
                        .addComponent(folderTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jCheckBox1)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButton2)
                            .addComponent(jRadioButton1))))
                .addGap(25, 25, 25))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(topSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(folderLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(folderTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton2)
                .addContainerGap(195, Short.MAX_VALUE))
        );

        jCheckBox1.setSelected(Boolean.parseBoolean(xml.getContentById("E621advancedNaming")));
        jLabel2.setVisible(Boolean.parseBoolean(xml.getContentById("E621advancedNaming")));
        jRadioButton1.setVisible(Boolean.parseBoolean(xml.getContentById("E621advancedNaming")));
        jRadioButton1.setSelected(xml.getContentById("E621namingOption").equals("0"));
        jRadioButton2.setVisible(Boolean.parseBoolean(xml.getContentById("FAadvancedNaming")));
        jRadioButton2.setSelected(xml.getContentById("FAnamingOption").equals("1"));
    }// </editor-fold>//GEN-END:initComponents

    private void folderTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_folderTextFieldMouseClicked
        if (evt.getClickCount() == 2) {
            buttonPressedResult = directory.showOpenDialog(this);
            if (buttonPressedResult == JFileChooser.APPROVE_OPTION) {
                userOutput = directory.getSelectedFile().getPath();
                folderTextField.setText(userOutput);
                xml.setContentByName("directory", 0, userOutput);
            }
        }
    }//GEN-LAST:event_folderTextFieldMouseClicked

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        if (jCheckBox1.isSelected()) {
            jLabel2.setVisible(true);
            jRadioButton1.setVisible(true);
            jRadioButton2.setVisible(true);
            xml.setContentById("E621advancedNaming", "true");
        } else {
            jLabel2.setVisible(false);
            jRadioButton1.setVisible(false);
            jRadioButton2.setVisible(false);
            xml.setContentById("E621advancedNaming", "false");
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jRadioButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton1MouseClicked
        xml.setContentById("E621namingOption", "0");
    }//GEN-LAST:event_jRadioButton1MouseClicked

    private void jRadioButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton2MouseClicked
       xml.setContentById("E621namingOption", "1");
    }//GEN-LAST:event_jRadioButton2MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel folderLabel;
    private javax.swing.JTextField folderTextField;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JLabel mainLabel;
    private javax.swing.JSeparator topSeparator;
    // End of variables declaration//GEN-END:variables
}

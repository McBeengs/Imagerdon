/* **********   NewTabJFrame.java   **********
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
package com.panels.main;

import com.util.UsefulMethods;
import com.util.xml.XmlManager;

public class NewTabJFrame extends javax.swing.JFrame {
    
    private int status = 0;
    private final XmlManager language;

    @SuppressWarnings("")
    public NewTabJFrame() {
        language = UsefulMethods.loadManager(UsefulMethods.LANGUAGE);
        
        setTitle(language.getContentById("NTLabel"));
        initComponents();
        confirmLabel.setVisible(false);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        DAButton = new javax.swing.JToggleButton();
        tumblrButton = new javax.swing.JToggleButton();
        GHButton = new javax.swing.JToggleButton();
        FAButton = new javax.swing.JToggleButton();
        e621Button = new javax.swing.JToggleButton();
        googleButton = new javax.swing.JToggleButton();
        okButton = new javax.swing.JButton();
        taskLabel = new javax.swing.JLabel();
        infoLabel = new javax.swing.JLabel();
        confirmLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        buttonGroup1.add(DAButton);
        DAButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/deviantArtIconBig.png"))); // NOI18N
        DAButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DAButtonActionPerformed(evt);
            }
        });

        buttonGroup1.add(tumblrButton);
        tumblrButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/tumblrIconBig.png"))); // NOI18N
        tumblrButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tumblrButtonActionPerformed(evt);
            }
        });

        buttonGroup1.add(GHButton);
        GHButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/galleryHentaiIconBig.png"))); // NOI18N
        GHButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GHButtonActionPerformed(evt);
            }
        });

        buttonGroup1.add(FAButton);
        FAButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/FAIconBig.png"))); // NOI18N
        FAButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FAButtonActionPerformed(evt);
            }
        });

        buttonGroup1.add(e621Button);
        e621Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/e621IconBig.png"))); // NOI18N
        e621Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                e621ButtonActionPerformed(evt);
            }
        });

        buttonGroup1.add(googleButton);
        googleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/googleIconBig.png"))); // NOI18N
        googleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                googleButtonActionPerformed(evt);
            }
        });

        okButton.setFocusPainted(false);
        okButton.setText(language.getContentById("ok"));
        okButton.setEnabled(false);
        okButton.setFocusable(false);

        taskLabel.setFont(new java.awt.Font("Eras Bold ITC", 0, 24)); // NOI18N
        taskLabel.setText(language.getContentById("NTLabel"));

        infoLabel.setText(language.getContentById("infoText1"));

        confirmLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        confirmLabel.setText("<null>");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(taskLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(infoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(DAButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tumblrButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(FAButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(GHButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(e621Button))
                            .addComponent(confirmLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(googleButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(taskLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(infoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(googleButton)
                    .addComponent(e621Button)
                    .addComponent(GHButton)
                    .addComponent(FAButton)
                    .addComponent(DAButton)
                    .addComponent(tumblrButton))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(okButton)
                    .addComponent(confirmLabel))
                .addContainerGap())
        );

        DAButton.setFocusPainted(false);
        tumblrButton.setFocusPainted(false);
        GHButton.setFocusPainted(false);
        FAButton.setFocusPainted(false);
        e621Button.setFocusPainted(false);
        googleButton.setFocusPainted(false);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void DAButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DAButtonActionPerformed
        status = 1;
        confirmLabel.setVisible(true);
        okButton.setEnabled(true);
        confirmLabel.setText(language.getContentById("NTconfirm").replace("&string", "DeviantArt"));
    }//GEN-LAST:event_DAButtonActionPerformed

    private void tumblrButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tumblrButtonActionPerformed
        status = 2;
        confirmLabel.setVisible(true);
        okButton.setEnabled(true);
        confirmLabel.setText(language.getContentById("NTconfirm").replace("&string", "Tumblr"));
    }//GEN-LAST:event_tumblrButtonActionPerformed

    private void GHButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GHButtonActionPerformed
        status = 3;
        confirmLabel.setVisible(true);
        okButton.setEnabled(true);
        confirmLabel.setText(language.getContentById("NTconfirm").replace("&string", "Gallery Hentai"));
    }//GEN-LAST:event_GHButtonActionPerformed

    private void FAButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FAButtonActionPerformed
        status = 4;
        confirmLabel.setVisible(true);
        okButton.setEnabled(true);
        confirmLabel.setText(language.getContentById("NTconfirm").replace("&string", "FurAffinity"));
    }//GEN-LAST:event_FAButtonActionPerformed

    private void e621ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_e621ButtonActionPerformed
        status = 5;
        confirmLabel.setVisible(true);
        okButton.setEnabled(true);
        confirmLabel.setText(language.getContentById("NTconfirm").replace("&string", "e621"));
    }//GEN-LAST:event_e621ButtonActionPerformed

    private void googleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_googleButtonActionPerformed
        status = 6;
        confirmLabel.setVisible(true);
        okButton.setEnabled(true);
        confirmLabel.setText(language.getContentById("NTconfirm").replace("&string", "Google"));
    }//GEN-LAST:event_googleButtonActionPerformed

    public int getServer() {
        return status;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton DAButton;
    private javax.swing.JToggleButton FAButton;
    private javax.swing.JToggleButton GHButton;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel confirmLabel;
    private javax.swing.JToggleButton e621Button;
    private javax.swing.JToggleButton googleButton;
    private javax.swing.JLabel infoLabel;
    public javax.swing.JButton okButton;
    private javax.swing.JLabel taskLabel;
    private javax.swing.JToggleButton tumblrButton;
    // End of variables declaration//GEN-END:variables

}

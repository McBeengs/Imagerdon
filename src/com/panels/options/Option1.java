package com.panels.options;

import com.util.UsefulMethods;
import com.util.xml.XmlManager;
import java.awt.Color;
import javax.swing.UIManager;

public class Option1 extends javax.swing.JPanel {

    private final String[] languages;
    private final String[] styles;
    private final int languageActive;
    private final int styleSelected;
    private final XmlManager xml;
    private final XmlManager language;

    public Option1(XmlManager xml) {
        this.xml = xml;
        language = UsefulMethods.loadManager(UsefulMethods.LANGUAGE);

        languages = new String[]{"English"};

        UIManager.LookAndFeelInfo[] plafs = UIManager.getInstalledLookAndFeels();
        styles = new String[plafs.length];

        for (int i = 0; i < plafs.length; i++) {
            styles[i] = plafs[i].getName();
        }

        initComponents();

        languageActive = Integer.parseInt(xml.getContentByAttribute("language", 0, "selected"));
        languageSelector.setSelectedIndex(languageActive);

        styleSelected = Integer.parseInt(xml.getContentByAttribute("style", 0, "selected"));
        styleSelector.setSelectedIndex(styleSelected);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        mainLabel = new javax.swing.JLabel();
        topSeparator = new javax.swing.JSeparator();
        languageLabel = new javax.swing.JLabel();
        languageSelector = new javax.swing.JComboBox<>();
        styleLabel = new javax.swing.JLabel();
        styleSelector = new javax.swing.JComboBox<>();
        updates = new javax.swing.JCheckBox();

        setMinimumSize(new java.awt.Dimension(516, 434));

        mainLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 24)); // NOI18N
        mainLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mainLabel.setText(language.getContentByName("mainLabel", 0) + " " + language.getContentById("options"));

        languageLabel.setText(language.getContentById("language"));

        languageSelector.setModel(new javax.swing.DefaultComboBoxModel<>(languages));
        languageSelector.setFocusable(false);
        languageSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                languageSelectorActionPerformed(evt);
            }
        });

        styleLabel.setText(language.getContentById("style"));

        styleSelector.setModel(new javax.swing.DefaultComboBoxModel<>(styles));
        styleSelector.setFocusable(false);
        styleSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                styleSelectorActionPerformed(evt);
            }
        });

        updates.setText("Check updates on the start");
        updates.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(topSeparator, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(mainLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(updates)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(languageLabel)
                                    .addComponent(styleLabel))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(languageSelector, 0, 100, Short.MAX_VALUE)
                                    .addComponent(styleSelector, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap(381, Short.MAX_VALUE))
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
                    .addComponent(languageLabel)
                    .addComponent(languageSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(styleLabel)
                    .addComponent(styleSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(113, 113, 113)
                .addComponent(updates)
                .addContainerGap(152, Short.MAX_VALUE))
        );

        updates.setSelected(Boolean.parseBoolean(xml.getContentById("update")));
    }// </editor-fold>//GEN-END:initComponents

    private void languageSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_languageSelectorActionPerformed
        if (languageSelector.getSelectedIndex() != languageActive) {
            UsefulMethods.makeBalloon(styleSelector, "The program must be restarted", Color.yellow);
        }

        xml.setContentByAttribute("language", "selected", 0, "" + languageSelector.getSelectedIndex());
        xml.setContentByName("language", 0, languageSelector.getItemAt(languageSelector.getSelectedIndex()));
    }//GEN-LAST:event_languageSelectorActionPerformed

    private void styleSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_styleSelectorActionPerformed
        if (styleSelector.getSelectedIndex() != styleSelected) {
            UsefulMethods.makeBalloon(styleSelector, "The program must be restarted", Color.yellow);
        }

        xml.setContentByAttribute("style", "selected", 0, "" + styleSelector.getSelectedIndex());
        xml.setContentByName("style", 0, styleSelector.getItemAt(styleSelector.getSelectedIndex()));
    }//GEN-LAST:event_styleSelectorActionPerformed

    private void updatesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatesActionPerformed
        if (updates.isSelected()) {
            xml.setContentById("update", "true");
        } else {
            xml.setContentById("update", "false");
        }
    }//GEN-LAST:event_updatesActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel languageLabel;
    private javax.swing.JComboBox<String> languageSelector;
    private javax.swing.JLabel mainLabel;
    private javax.swing.JLabel styleLabel;
    private javax.swing.JComboBox<String> styleSelector;
    private javax.swing.JSeparator topSeparator;
    private javax.swing.JCheckBox updates;
    // End of variables declaration//GEN-END:variables
}

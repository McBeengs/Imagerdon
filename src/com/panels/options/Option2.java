package com.panels.options;

import com.util.UsefulMethods;
import com.util.xml.XmlManager;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import org.xml.sax.SAXException;

public class Option2 extends javax.swing.JPanel {

    private final JFileChooser directory;
    private int buttonPressedResult;
    private String userOutput;
    private final String[] duality;
    private final XmlManager xml;
    private final XmlManager language;

    public Option2(XmlManager xml) throws SAXException, IOException {
        this.xml = xml;
        language = UsefulMethods.loadManager(UsefulMethods.LANGUAGE);

        userOutput = xml.getContentByName("directory", 0);
        directory = new JFileChooser(userOutput);
        directory.setDialogTitle("Select where save");
        directory.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        duality = new String[3];
        duality[0] = language.getContentById("skipArtist");
        duality[1] = language.getContentById("downloadCopy");
        duality[2] = language.getContentById("askUser");

        initComponents();

        if (downloadsSlider.getValue() > 5) {
            downloadsAlert.setVisible(true);
        }

        optionsDropdown.setSelectedIndex(Integer.parseInt(xml.getContentById("existed")));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        mainLabel = new javax.swing.JLabel();
        downloadsLabel = new javax.swing.JLabel();
        downloadsSlider = new javax.swing.JSlider();
        downloadsDisplayer = new javax.swing.JLabel();
        downloadsAlert = new javax.swing.JLabel();
        outputLabel = new javax.swing.JLabel();
        outputDirectory = new javax.swing.JTextField();
        directoriesCB1 = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        optionsDropdown = new javax.swing.JComboBox<>();
        topSeparator = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        alertLazyFix = new javax.swing.JPanel();

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        setMinimumSize(new java.awt.Dimension(516, 434));

        mainLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 24)); // NOI18N
        mainLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mainLabel.setText(language.getContentByName("mainLabel", 1));

        downloadsLabel.setText(language.getContentById("simult"));

        downloadsSlider.setMaximum(10);
        downloadsSlider.setMinimum(1);
        downloadsSlider.setValue(Integer.parseInt(xml.getContentByName("scroll", 0))
        );
        downloadsSlider.setRequestFocusEnabled(false);
        int cur = Integer.parseInt(xml.getContentByName("scroll", 0));
        downloadsSlider.setValue(cur);

        if (cur > 5){
            downloadsAlert.setVisible(true);
        }
        downloadsSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                downloadsSliderStateChanged(evt);
            }
        });

        downloadsDisplayer.setText("" + downloadsSlider.getValue());

        downloadsAlert.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/info.png")));
        downloadsAlert.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        downloadsAlert.setVisible(false);
        downloadsAlert.setForeground(new java.awt.Color(51, 109, 243));
        downloadsAlert.setText(language.getContentByName("alert", 1));

        outputLabel.setText(language.getContentById("output"));

        outputDirectory.setEditable(false);
        outputDirectory.setText(userOutput);
        outputDirectory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                outputDirectoryMouseClicked(evt);
            }
        });

        directoriesCB1.setText(language.getContentById("subfolder"));
        directoriesCB1.setFocusable(false);
        directoriesCB1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                directoriesCB1MouseClicked(evt);
            }
        });

        jLabel4.setText(language.getContentById("existed"));

        optionsDropdown.setModel(new javax.swing.DefaultComboBoxModel<>(duality));
        optionsDropdown.setRequestFocusEnabled(false);
        optionsDropdown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionsDropdownActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout alertLazyFixLayout = new javax.swing.GroupLayout(alertLazyFix);
        alertLazyFix.setLayout(alertLazyFixLayout);
        alertLazyFixLayout.setHorizontalGroup(
            alertLazyFixLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        alertLazyFixLayout.setVerticalGroup(
            alertLazyFixLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(topSeparator, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jSeparator2)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(outputLabel)
                                .addGap(18, 18, 18)
                                .addComponent(outputDirectory))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(directoriesCB1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(downloadsAlert)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(alertLazyFix, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(downloadsLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(downloadsSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(downloadsDisplayer, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(mainLabel))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jLabel4)
                                .addGap(30, 30, 30)
                                .addComponent(optionsDropdown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(mainLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(topSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(downloadsDisplayer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(downloadsSlider, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(downloadsLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(alertLazyFix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(downloadsAlert))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(outputDirectory)
                    .addComponent(outputLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(directoriesCB1)
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(optionsDropdown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(178, Short.MAX_VALUE))
        );

        directoriesCB1.setSelected(Boolean.parseBoolean(xml.getContentById("sub")));
    }// </editor-fold>//GEN-END:initComponents

    private void downloadsSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_downloadsSliderStateChanged
        downloadsDisplayer.setText("" + downloadsSlider.getValue());

        if (downloadsSlider.getValue() > 5) {
            downloadsAlert.setVisible(true);
        } else {
            downloadsAlert.setVisible(false);
        }

        xml.setContentByName("scroll", 0, "" + downloadsSlider.getValue());
    }//GEN-LAST:event_downloadsSliderStateChanged

    private void outputDirectoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_outputDirectoryMouseClicked
        if (evt.getClickCount() == 2) {
            buttonPressedResult = directory.showOpenDialog(this);
            if (buttonPressedResult == JFileChooser.APPROVE_OPTION) {
                userOutput = directory.getSelectedFile().getPath();
                outputDirectory.setText(userOutput);
                xml.setContentById("mainOutput", userOutput);
            }
        }
    }//GEN-LAST:event_outputDirectoryMouseClicked

    private void optionsDropdownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionsDropdownActionPerformed
        xml.setContentById("existed", "" + optionsDropdown.getSelectedIndex());
    }//GEN-LAST:event_optionsDropdownActionPerformed

    private void directoriesCB1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_directoriesCB1MouseClicked
        if (directoriesCB1.isSelected()) {
            xml.setContentById("sub", "true");
            directoriesCB1.setSelected(true);
        } else {
            xml.setContentById("sub", "false");
            directoriesCB1.setSelected(false);
        }
    }//GEN-LAST:event_directoriesCB1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel alertLazyFix;
    private javax.swing.JCheckBox directoriesCB1;
    private javax.swing.JLabel downloadsAlert;
    private javax.swing.JLabel downloadsDisplayer;
    private javax.swing.JLabel downloadsLabel;
    private javax.swing.JSlider downloadsSlider;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel mainLabel;
    private javax.swing.JComboBox<String> optionsDropdown;
    private javax.swing.JTextField outputDirectory;
    private javax.swing.JLabel outputLabel;
    private javax.swing.JSeparator topSeparator;
    // End of variables declaration//GEN-END:variables
}

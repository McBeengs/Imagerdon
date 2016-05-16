/* **********   BatchDownloads.java   **********
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

import com.panels.main.StylizedMainJFrame;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class BatchDownloads extends javax.swing.JFrame {

    public static final int IMAGES = 1;
    public static final int SERVERS = 2;
    private int type;

    public BatchDownloads(int type) {
        initComponents();
        setTitle("Download Batch");
        this.type = type;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        textInput = new javax.swing.JTextArea();
        importButton = new javax.swing.JButton();
        fileLabel = new javax.swing.JLabel();
        downloadButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        textInput.setColumns(20);
        textInput.setRows(5);
        jScrollPane1.setViewportView(textInput);

        importButton.setText("Import from file");
        importButton.setFocusable(false);
        importButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importButtonActionPerformed(evt);
            }
        });

        fileLabel.setText("   ");

        downloadButton.setText("Download");
        downloadButton.setFocusable(false);
        downloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("<html><body>Type or paste each individual link on a new line. If you instead have an \".txt\" file with the same<br>"
            + "style, import it above. When the links are all displayed in the text box below, click \"Download\"</body></html>");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(importButton)
                        .addGap(18, 18, 18)
                        .addComponent(fileLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(downloadButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(importButton)
                    .addComponent(fileLabel)
                    .addComponent(downloadButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void importButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importButtonActionPerformed
        JFileChooser chooser = new JFileChooser(System.getProperty("user.home"));
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileFilter(new FileNameExtensionFilter(".txt Files", "txt", "text"));

        if (chooser.showOpenDialog(getComponent()) == JFileChooser.APPROVE_OPTION) {
            try {
                FileReader is = new FileReader(chooser.getSelectedFile());
                BufferedReader br = new BufferedReader(is);
                String line = br.readLine();

                while (line != null) {
                    textInput.setText(textInput.getText() + line + System.lineSeparator());
                    line = br.readLine();
                }

                fileLabel.setText(chooser.getSelectedFile().getPath());

            } catch (FileNotFoundException ex) {
                Logger.getLogger(BatchDownloads.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(BatchDownloads.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_importButtonActionPerformed

    private void downloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadButtonActionPerformed
        String text = textInput.getText();
        final String[] links = text.split("\n");

        if (type == IMAGES) {
            StylizedMainJFrame.ADD_TASK.addBatchTask(links);
        } else if (type == SERVERS) {
            final String[] outcomes = new String[]{".deviantart.com/gallery/", ".tumblr.com", "http://g.e-hentai.org/g/",
                "http://www.furaffinity.net/gallery/", "https://e621.net/post/index/"};

            new Thread() {
                @Override
                public void run() {
                    for (String link : links) {
                        for (int j = 0; j < outcomes.length; j++) {
                            if (link.contains(outcomes[j])) {
                                StylizedMainJFrame.ADD_TASK.addTask(link, j, -2);
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(BatchDownloads.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                }
            }.start();
        }
        dispose();
    }//GEN-LAST:event_downloadButtonActionPerformed

    private Component getComponent() {
        return this;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton downloadButton;
    private javax.swing.JLabel fileLabel;
    private javax.swing.JButton importButton;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea textInput;
    // End of variables declaration//GEN-END:variables

}

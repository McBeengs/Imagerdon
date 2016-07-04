/* **********   SetupContainer.java   **********
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
package com.panels.modal;

import com.util.xml.XmlManager;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SetupContainer extends javax.swing.JFrame {

    private final XmlManager xml;
    private final GridBagConstraints c = new GridBagConstraints();
    private int currentPanel = 0;
    public static Notifier NOTIFIER;

    public SetupContainer(XmlManager xml) {
        initComponents();
        NOTIFIER = new Notifier();
        this.xml = xml;
        container.setLayout(new GridBagLayout());
        c.gridx = 0;
        c.gridy = 0;
        container.add(new SetupLanguageJPanel(xml), c);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        container = new javax.swing.JPanel();
        previous = new javax.swing.JButton();
        next = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Alpha V3 - Setup");
        setResizable(false);

        javax.swing.GroupLayout containerLayout = new javax.swing.GroupLayout(container);
        container.setLayout(containerLayout);
        containerLayout.setHorizontalGroup(
            containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        containerLayout.setVerticalGroup(
            containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 125, Short.MAX_VALUE)
        );

        previous.setText("Previous");
        previous.setEnabled(false);
        previous.setFocusable(false);
        previous.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousActionPerformed(evt);
            }
        });

        next.setText("Next");
        next.setFocusable(false);
        next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(previous, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 290, Short.MAX_VALUE)
                .addComponent(next, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(next)
                    .addComponent(previous))
                .addGap(0, 15, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextActionPerformed
        switch (currentPanel) {
            case 0:
                container.removeAll();
                container.add(new SetupDAJPanel(xml), c);
                container.revalidate();
                container.repaint();
                previous.setEnabled(true);
                next.setEnabled(false);
                currentPanel++;
                break;
            case 1:
                container.removeAll();
                container.add(new SetupTUJPanel(xml), c);
                container.revalidate();
                container.repaint();
                next.setEnabled(false);
                currentPanel++;
                break;
            case 2:
                container.removeAll();
                container.add(new SetupFAJPanel(xml), c);
                container.revalidate();
                container.repaint();
                next.setEnabled(false);
                currentPanel++;
                break;
            case 3:
                try {
                    xml.saveXml();
                    restartApplication();
                } catch (IOException | URISyntaxException ex) {
                    Logger.getLogger(SetupContainer.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
    }//GEN-LAST:event_nextActionPerformed

    private void previousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousActionPerformed
        switch (currentPanel) {
            case 3:
                container.removeAll();
                container.add(new SetupTUJPanel(xml), c);
                container.revalidate();
                currentPanel--;
                break;
            case 2:
                container.removeAll();
                container.add(new SetupDAJPanel(xml), c);
                container.revalidate();
                previous.setEnabled(true);
                currentPanel--;
                break;
            case 1:
                container.removeAll();
                container.add(new SetupLanguageJPanel(xml), c);
                container.revalidate();
                previous.setEnabled(false);
                next.setEnabled(true);
                currentPanel--;
                break;
        }
    }//GEN-LAST:event_previousActionPerformed

    public void restartApplication() throws URISyntaxException, IOException {
        final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        final File currentJar = new File(SetupContainer.class.getProtectionDomain().getCodeSource().getLocation().toURI());

        /* is it a jar file? */
        if (!currentJar.getName().endsWith(".jar")) {
            return;
        }

        /* Build command: java -jar application.jar */
        final ArrayList<String> command = new ArrayList<>();
        command.add(javaBin);
        command.add("-jar");
        command.add(currentJar.getPath());

        final ProcessBuilder builder = new ProcessBuilder(command);
        builder.start();
        System.exit(0);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel container;
    private javax.swing.JButton next;
    private javax.swing.JButton previous;
    // End of variables declaration//GEN-END:variables

    public class Notifier {

        public void enableNext() {
            next.setEnabled(true);
        }

        public void enablePrevious() {
            previous.setEnabled(true);
        }
    }
}

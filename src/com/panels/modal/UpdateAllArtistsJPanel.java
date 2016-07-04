/* **********   NovoJPanel.java   **********
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

import javax.swing.ImageIcon;

public class UpdateAllArtistsJPanel extends javax.swing.JPanel {

    public UpdateAllArtistsJPanel() {
        initComponents();
    }

    public void setServerDone(int server) {
        switch (server) {
            case 0:
                da.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/ok.png")));
                da.setForeground(new java.awt.Color(50, 205, 50));
                tu.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/spinner.gif")));
                break;
            case 1:
                tu.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/ok.png")));
                tu.setForeground(new java.awt.Color(50, 205, 50));
                fa.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/spinner.gif")));
                break;
            case 2:
                fa.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/ok.png")));
                fa.setForeground(new java.awt.Color(50, 205, 50));
                e621.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/spinner.gif")));
                break;
            case 3:
                e621.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/ok.png")));
                e621.setForeground(new java.awt.Color(50, 205, 50));
                break;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        da = new javax.swing.JLabel();
        tu = new javax.swing.JLabel();
        fa = new javax.swing.JLabel();
        e621 = new javax.swing.JLabel();
        updatingProgress = new javax.swing.JProgressBar();

        setMinimumSize(new java.awt.Dimension(445, 100));
        setPreferredSize(new java.awt.Dimension(445, 100));
        setRequestFocusEnabled(false);

        da.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/spinner.gif"))); // NOI18N
        da.setText("DeviantArt");

        tu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/tumblrIconSmall.png"))); // NOI18N
        tu.setText("Tumblr");

        fa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/FAIconSmall.png"))); // NOI18N
        fa.setText("FurAffinity");

        e621.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/e621IconSmall.png"))); // NOI18N
        e621.setText("e621");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(updatingProgress, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(da)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tu)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(fa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(e621)
                        .addGap(70, 70, 70)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(da)
                    .addComponent(e621)
                    .addComponent(fa)
                    .addComponent(tu))
                .addGap(18, 18, 18)
                .addComponent(updatingProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    public void setProgressMinimum(int min) {
        updatingProgress.setMinimum(min);
    }

    public void setProgressMaximum(int max) {
        updatingProgress.setMaximum(max);
    }

    public void setProgressValue(int val) {
        updatingProgress.setValue(val);
    }

    public int getProgressValue() {
        return updatingProgress.getValue();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel da;
    private javax.swing.JLabel e621;
    private javax.swing.JLabel fa;
    private javax.swing.JLabel tu;
    private javax.swing.JProgressBar updatingProgress;
    // End of variables declaration//GEN-END:variables

}

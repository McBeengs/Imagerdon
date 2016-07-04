/* **********   ErrorPane.java   **********
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
package com.core.web.explorer.panes;

import com.panels.main.StylizedMainJFrame;
import com.util.UsefulMethods;
import com.util.xml.XmlManager;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;


public class ErrorPane extends javax.swing.JPanel {
    
    private final XmlManager language;
    private final JTabbedPane tabbedPane;
    private final int tabIndex;
    private final ImageIcon icon;
    private final String url;

    public ErrorPane(String errorString, JTabbedPane tabbedPane, int tabIndex, ImageIcon icon, String url) {
        language = UsefulMethods.loadManager(UsefulMethods.LANGUAGE);
        
        this.tabbedPane = tabbedPane;
        this.tabIndex = tabIndex;
        this.icon = icon;
        this.url = url;
        
        initComponents();
        
        errorLabel.setText(errorString);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        taskLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        errorMessage = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        errorLabel = new javax.swing.JLabel();
        taskLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 208, 208));
        setBorder(new RoundedCornerBorder());

        taskLabel.setFont(new java.awt.Font("Eras Bold ITC", 0, 24)); // NOI18N
        taskLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/stopPage.png"))); // NOI18N
        taskLabel.setText(language.getContentById("errorLabel"));

        errorMessage.setFont(new java.awt.Font("Verdana", 2, 14)); // NOI18N
        errorMessage.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jLabel2.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText(language.getContentById("errorDetails"));

        errorLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        errorLabel.setText("jLabel1");

        taskLabel1.setFont(new java.awt.Font("Eras Bold ITC", 0, 24)); // NOI18N
        taskLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/refresh.png"))); // NOI18N
        taskLabel1.setText("Retry");
        taskLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                taskLabel1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                taskLabel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                taskLabel1MouseExited(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(errorMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(taskLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(errorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(taskLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(45, 45, 45))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(taskLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(errorMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(errorLabel)
                    .addComponent(taskLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(79, Short.MAX_VALUE))
        );

        errorMessage.setHorizontalTextPosition(JLabel.RIGHT);
        errorMessage.setText("<html><body style=\"text-align: justify;\">   " + language.getContentById("errorConnMessage").replace("&br", "<br>") + "</body></html>");
    }// </editor-fold>//GEN-END:initComponents

    private void taskLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taskLabel1MouseEntered
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_taskLabel1MouseEntered

    private void taskLabel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taskLabel1MouseExited
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_taskLabel1MouseExited

    private void taskLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taskLabel1MouseClicked
        tabbedPane.setTitleAt(tabIndex, language.getContentById("newTabTitle") + "     ");
        tabbedPane.setIconAt(tabIndex, icon);
        tabbedPane.setComponentAt(tabIndex, new WebViewPage((StylizedMainJFrame.ClosableTabbedPane) tabbedPane, tabIndex, icon, url));
    }//GEN-LAST:event_taskLabel1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel errorLabel;
    private javax.swing.JLabel errorMessage;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel taskLabel;
    private javax.swing.JLabel taskLabel1;
    // End of variables declaration//GEN-END:variables

    private class RoundedCornerBorder extends javax.swing.border.AbstractBorder {
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int r = height - 1;
            RoundRectangle2D round = new RoundRectangle2D.Float(x + 10, y + 10, width - 21, height - 21, 80, 80);
            Container parent = c.getParent();
            if (parent != null) {
                g2.setColor(parent.getBackground());
                Area corner = new Area(new Rectangle2D.Float(x, y, width, height));
                corner.subtract(new Area(round));
                g2.fill(corner);
            }
            g2.setColor(Color.GRAY);
            g2.draw(round);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(4, 8, 4, 8);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = 8;
            insets.top = insets.bottom = 4;
            return insets;
        }
    }
}

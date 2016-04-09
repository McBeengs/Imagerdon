package com.core.web.explorer.panes;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.util.UsefulMethods;
import com.util.xml.XmlManager;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

public class MainPane extends javax.swing.JPanel {

    private final XmlManager xml;
    private final XmlManager language;
    private final ArrayList<String> randoms;

    public MainPane() {
        xml = UsefulMethods.loadManager(UsefulMethods.OPTIONS);
        language = UsefulMethods.loadManager(UsefulMethods.LANGUAGE);

        initComponents();
        initSubComponents();

        randoms = language.getAllContentsByName("random");

        Random random = new Random();
        niceMessage.setText(randoms.get(random.nextInt(17)));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        taskLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        niceMessage = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        imagesOnDisk = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        artistsOnDisk = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        imagesPerArtist = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        internetConnection = new javax.swing.JLabel();

        taskLabel.setFont(new java.awt.Font("Eras Bold ITC", 0, 24)); // NOI18N
        taskLabel.setText("Alpha V3 - Welcome");

        niceMessage.setFont(new java.awt.Font("Verdana", 2, 14)); // NOI18N
        niceMessage.setText("Lorem ipsun, dolor sit amet.");

        jLabel1.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        jLabel1.setText("Number of images on disk:");

        imagesOnDisk.setText("jLabel2");

        jLabel3.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        jLabel3.setText("Number of artists on disk:");

        artistsOnDisk.setText("jLabel2");

        jLabel5.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        jLabel5.setText("Images per artist:");

        imagesPerArtist.setText("jLabel2");

        jLabel2.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        jLabel2.setText("Internet connection:");

        internetConnection.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        internetConnection.setForeground(new java.awt.Color(51, 109, 243));
        internetConnection.setText(null);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(niceMessage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(imagesOnDisk))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(artistsOnDisk))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(imagesPerArtist)
                                    .addComponent(internetConnection, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 75, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(taskLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(taskLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(niceMessage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(imagesOnDisk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(artistsOnDisk, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(imagesPerArtist))
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(internetConnection, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void initSubComponents() {
        this.setBorder(new RoundedCornerBorder());
        this.setBackground(Color.white);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                String s = System.getProperty("file.separator");
                File daXml = new File(xml.getContentById("DAoutput") + s + "artists-log.xml");
                File tuXml = new File(xml.getContentById("TUoutput") + s + "artists-log.xml");
                File ghXml = new File(xml.getContentById("GHoutput") + s + "artists-log.xml");
                File faXml = new File(xml.getContentById("FAoutput") + s + "artists-log.xml");
                File e621Xml = new File(xml.getContentById("E621output") + s + "artists-log.xml");
                File[] xmls = new File[]{daXml, tuXml, ghXml, faXml, e621Xml};

                int totalImagesNumber = 0;
                for (File xml : xmls) {
                    if (xml.exists()) {
                        try {
                            XmlManager get = new XmlManager();
                            get.loadFile(xml);
                            ArrayList<String> num = get.getAllContentsByName("imageCount");
                            for (int i = 0; i < num.size(); i++) {
                                totalImagesNumber += Integer.parseInt(num.get(i));
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(MainPane.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

                int totalArtistsNumber = 0;
                for (File xml : xmls) {
                    if (xml.exists()) {
                        try {
                            XmlManager get = new XmlManager();
                            get.loadFile(xml);
                            ArrayList<String> num = get.getAllContentsByName("name");
                            totalArtistsNumber += num.size();
                        } catch (IOException ex) {
                            Logger.getLogger(MainPane.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

                imagesOnDisk.setText("" + totalImagesNumber);
                artistsOnDisk.setText("" + totalArtistsNumber);
                float show;
                if (totalArtistsNumber == 0) {
                    show = 0;
                } else {
                    show = (totalImagesNumber / totalArtistsNumber);
                }
                imagesPerArtist.setText("" + show);

                checkConnection();
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                Random random = new Random();
                niceMessage.setText(randoms.get(random.nextInt(17)));
            }
        });
    }

    private void checkConnection() {
        internetConnection.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/spinner.gif")));
        internetConnection.setForeground(new java.awt.Color(51, 109, 243));
        internetConnection.setText(language.getContentById("connecting"));

        new Thread("Checking internet connection") {
            @Override
            public void run() {
                WebClient webClient = new WebClient(BrowserVersion.CHROME);
                webClient.getOptions().setCssEnabled(false);
                webClient.getOptions().setJavaScriptEnabled(false);
                webClient.getOptions().setAppletEnabled(false);
                try {
                    webClient.getPage("https://www.google.com");
                    internetConnection.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/ok.png")));
                    internetConnection.setForeground(new java.awt.Color(50, 205, 50));
                    internetConnection.setText(language.getContentById("connectionOk"));
                } catch (IOException | FailingHttpStatusCodeException ex) {
                    internetConnection.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/error2.png")));
                    internetConnection.setForeground(new java.awt.Color(238, 44, 44));
                    internetConnection.setText(language.getContentById("connectionFailed"));
                }
            }
        }.start();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel artistsOnDisk;
    private javax.swing.JLabel imagesOnDisk;
    private javax.swing.JLabel imagesPerArtist;
    private javax.swing.JLabel internetConnection;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel niceMessage;
    private javax.swing.JLabel taskLabel;
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

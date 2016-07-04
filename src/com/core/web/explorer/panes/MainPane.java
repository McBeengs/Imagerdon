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
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class MainPane extends javax.swing.JPanel {

    private final XmlManager xml;
    private final XmlManager language;
    private final List<String> randoms;

    public MainPane() {
        xml = UsefulMethods.loadManager(UsefulMethods.OPTIONS);
        language = UsefulMethods.loadManager(UsefulMethods.LANGUAGE);

        initComponents();
        initSubComponents();
        checkVersion();

        randoms = language.getAllContentsByName("random");
        Random random = new Random();
        niceMessage.setText(randoms.get(random.nextInt(randoms.size() - 1)));

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
        TULogin1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        taskLabel.setFont(new java.awt.Font("Eras Bold ITC", 0, 24)); // NOI18N
        taskLabel.setText(language.getContentById("appName") + " - " + language.getContentById("welcomeLabel"));

        niceMessage.setFont(new java.awt.Font("Verdana", 2, 14)); // NOI18N
        niceMessage.setText("Lorem ipsun, dolor sit amet.");

        jLabel1.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        jLabel1.setText(language.getContentById("imagesOnDisk"));

        imagesOnDisk.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        imagesOnDisk.setText("Getting info...");

        jLabel3.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        jLabel3.setText(language.getContentById("artistsOnDisk"));

        artistsOnDisk.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        artistsOnDisk.setText("Getting info...");

        jLabel5.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        jLabel5.setText(language.getContentById("imagesPerArtist"));

        imagesPerArtist.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        imagesPerArtist.setText("Getting info...");

        jLabel2.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        jLabel2.setText(language.getContentById("checkInternet"));

        internetConnection.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        internetConnection.setForeground(new java.awt.Color(51, 109, 243));
        internetConnection.setText(null);

        TULogin1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TULogin1.setForeground(new java.awt.Color(51, 109, 243));
        TULogin1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/spinner.gif"))); // NOI18N
        TULogin1.setText("Checking version");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/updateTask.png"))); // NOI18N
        jButton1.setText(language.getContentById("retry"));
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TULogin1)
                            .addComponent(taskLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(niceMessage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(imagesOnDisk, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(artistsOnDisk, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(5, 5, 5)
                                        .addComponent(internetConnection, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton1))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(imagesPerArtist, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addContainerGap())))
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
                    .addComponent(imagesPerArtist, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(internetConnection, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addComponent(TULogin1)
                .addGap(34, 34, 34))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        checkConnection();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void initSubComponents() {
        this.setBorder(new RoundedCornerBorder());
        this.setBackground(Color.white);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                checkConnection();
                setupWebClient();

                new Thread() {
                    @Override
                    public void run() {
                        Connection conn = UsefulMethods.getDBInstance();
                        int totalImagesNumber = 0;
                        int totalArtistsNumber = 0;
                        try {

                            ResultSet rs = conn.createStatement().executeQuery("SELECT image_count FROM artist");
                            while (rs.next()) {
                                totalArtistsNumber++;
                                totalImagesNumber += rs.getInt("image_count");
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(MainPane.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        NumberFormat format = DecimalFormat.getInstance();
                        format.setRoundingMode(RoundingMode.FLOOR);
                        format.setMinimumFractionDigits(0);
                        format.setMaximumFractionDigits(2);
                        imagesOnDisk.setText("" + format.format(totalImagesNumber));
                        artistsOnDisk.setText("" + totalArtistsNumber);
                        double show;
                        if (totalArtistsNumber == 0) {
                            show = 0;
                        } else {
                            show = (double) ((double) totalImagesNumber / (double) totalArtistsNumber);
                        }
                        imagesPerArtist.setText("" + format.format(show));
                    }
                }.start();
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                Random random = new Random();
                niceMessage.setText(randoms.get(random.nextInt(randoms.size() - 1)));
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

    private void checkVersion() {
        if (Boolean.parseBoolean(xml.getContentById("update"))) {

            new Thread() {
                @Override
                public void run() {
                    try {
                        Document page = Jsoup.parse(new URL("http://version-check.hol.es/"), 5000);
                        if (!page.getElementById("newer_version").text().equals("1.0.1")) {
                            String get = language.getContentById("newerVersion");
                            get = get.replace("&string", page.getElementById("newer_version").text());
                            String[] temp = get.split("&br");
                            String[] message = new String[]{temp[0], language.getContentById("here").toLowerCase() + temp[1]};
                            String link = page.getElementById("download").toString();
                            link = link.substring(link.indexOf("\"", 20) + 1);
                            link = "http://version-check.hol.es/" + link.substring(0, link.indexOf("\""));
                            UsefulMethods.makeHyperlinkOptionPane(message, link, 1, JOptionPane.INFORMATION_MESSAGE, language.getContentById("genericInfoTitle"));

                            TULogin1.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/error2.png")));
                            TULogin1.setForeground(new java.awt.Color(238, 44, 44));
                            TULogin1.setText(language.getContentById("alertVersionNew"));
                        } else {
                            TULogin1.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/ok.png")));
                            TULogin1.setForeground(new java.awt.Color(50, 205, 50));
                            TULogin1.setText(language.getContentById("alertVersionOld"));
                        }
                    } catch (IOException ex) {
                    }
                }
            }.start();
        } else {
            TULogin1.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/ok.png")));
            TULogin1.setForeground(new java.awt.Color(50, 205, 50));
            TULogin1.setText(language.getContentById("alertVersionOld"));
        }
    }

    private void setupWebClient() {
        new Thread("Setting up webClient (MainPane.java)") {
            @Override
            public void run() {
                try {
                    UsefulMethods.getWebClientInstance();
                } catch (java.net.UnknownHostException ex) {
                    JOptionPane.showMessageDialog(null, language.getContentById("internetDroppedOut"), language.getContentById("genericErrorTitle"), JOptionPane.OK_OPTION);
                } catch (Exception ex) {
                    switch (ex.getMessage()) {
                        case "DeviantArt":
                            JOptionPane.showMessageDialog(null, language.getContentById("loginFailed").replace("&string", "DeviantArt"),
                                    language.getContentById("genericErrorTitle"), JOptionPane.OK_OPTION);
                            break;
                        case "Tumblr":
                            JOptionPane.showMessageDialog(null, language.getContentById("loginFailed").replace("&string", "Tumblr"),
                                    language.getContentById("genericErrorTitle"), JOptionPane.OK_OPTION);
                            break;
                        case "FurAffinity":
                            JOptionPane.showMessageDialog(null, language.getContentById("loginFailed").replace("&string", "FurAffinity"),
                                    language.getContentById("genericErrorTitle"), JOptionPane.OK_OPTION);
                            break;
                        default:
                            ex.printStackTrace();
                            break;
                    }
                }
            }
        }.start();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel TULogin1;
    private javax.swing.JLabel artistsOnDisk;
    private javax.swing.JLabel imagesOnDisk;
    private javax.swing.JLabel imagesPerArtist;
    private javax.swing.JLabel internetConnection;
    private javax.swing.JButton jButton1;
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

            Rectangle2D rect = new Rectangle(width - 1, height - 1);
            g2.draw(rect);
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

package com.core.web.explorer.panes;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class MainPane extends javax.swing.JPanel {

    private final XmlManager xml;
    private final XmlManager language;
    private final List<String> randoms;

    public MainPane() {
        xml = UsefulMethods.loadManager(UsefulMethods.OPTIONS);
        language = UsefulMethods.loadManager(UsefulMethods.LANGUAGE);

        initComponents();
        initSubComponents();

        randoms = language.getAllContentsByName("random");

        Random random = new Random();
        niceMessage.setText(randoms.get(random.nextInt(randoms.size() - 1)));

        retryButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                checkConnection();
                setupWebClient();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                retryButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                retryButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
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
        webClientSetup = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        DALogin = new javax.swing.JLabel();
        TULogin = new javax.swing.JLabel();
        FALogin = new javax.swing.JLabel();
        retryButton = new javax.swing.JLabel();

        taskLabel.setFont(new java.awt.Font("Eras Bold ITC", 0, 24)); // NOI18N
        taskLabel.setText(language.getContentById("welcomeLabel"));

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

        webClientSetup.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        webClientSetup.setForeground(new java.awt.Color(51, 109, 243));
        webClientSetup.setText(null);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/leftBrace.png"))); // NOI18N

        DALogin.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        DALogin.setForeground(new java.awt.Color(51, 109, 243));
        DALogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/spinner.gif"))); // NOI18N
        DALogin.setText("DeviantArt");

        TULogin.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TULogin.setForeground(new java.awt.Color(51, 109, 243));
        TULogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/spinner.gif"))); // NOI18N
        TULogin.setText("Tumblr");

        FALogin.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        FALogin.setForeground(new java.awt.Color(51, 109, 243));
        FALogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/spinner.gif"))); // NOI18N
        FALogin.setText("FurAffinity");

        retryButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        retryButton.setForeground(new java.awt.Color(51, 109, 243));
        retryButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/ok.png"))); // NOI18N
        retryButton.setText("Retry");

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
                        .addComponent(taskLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(niceMessage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(webClientSetup, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(FALogin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(DALogin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(TULogin)
                                        .addGap(51, 51, 51)
                                        .addComponent(retryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(5, 5, 5)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(internetConnection, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(imagesPerArtist, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(54, 54, 54))))
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
                                        .addComponent(artistsOnDisk, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))))
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
                    .addComponent(imagesPerArtist))
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(internetConnection, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(DALogin)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TULogin)
                            .addComponent(retryButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FALogin))
                    .addComponent(webClientSetup, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void initSubComponents() {
        this.setBorder(new RoundedCornerBorder());
        this.setBackground(Color.white);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                new Thread() {
                    @Override
                    public void run() {
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
                                    List<String> num = get.getAllContentsByName("imageCount");
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
                                    List<String> num = get.getAllContentsByName("name");
                                    totalArtistsNumber += num.size();
                                } catch (IOException ex) {
                                    Logger.getLogger(MainPane.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
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

                        checkConnection();
                        setupWebClient();
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

    private void setupWebClient() {
        webClientSetup.setForeground(new java.awt.Color(51, 109, 243));
        webClientSetup.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/spinner.gif")));
        DALogin.setForeground(new java.awt.Color(51, 109, 243));
        DALogin.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/spinner.gif")));
        TULogin.setForeground(new java.awt.Color(51, 109, 243));
        TULogin.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/spinner.gif")));
        FALogin.setForeground(new java.awt.Color(51, 109, 243));
        FALogin.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/spinner.gif")));
        webClientSetup.setText("Setting logins instance");

        new Thread("Setting up webClient (MainPane.java)") {
            @Override
            public void run() {
                try {
                    UsefulMethods.getWebClientInstance();
                } catch (java.net.UnknownHostException ex) {
                    webClientSetup.setForeground(new java.awt.Color(238, 44, 44));
                    webClientSetup.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/error2.png")));
                    DALogin.setForeground(new java.awt.Color(238, 44, 44));
                    DALogin.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/error2.png")));
                    TULogin.setForeground(new java.awt.Color(238, 44, 44));
                    TULogin.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/error2.png")));
                    FALogin.setForeground(new java.awt.Color(238, 44, 44));
                    FALogin.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/error2.png")));
                    return;
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

                    webClientSetup.setForeground(new java.awt.Color(238, 44, 44));
                    webClientSetup.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/error2.png")));
                    DALogin.setForeground(new java.awt.Color(238, 44, 44));
                    DALogin.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/error2.png")));
                    TULogin.setForeground(new java.awt.Color(238, 44, 44));
                    TULogin.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/error2.png")));
                    FALogin.setForeground(new java.awt.Color(238, 44, 44));
                    FALogin.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/error2.png")));
                    return;
                }

                webClientSetup.setForeground(new java.awt.Color(50, 205, 50));
                webClientSetup.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/ok.png")));
                DALogin.setForeground(new java.awt.Color(50, 205, 50));
                DALogin.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/ok.png")));
                TULogin.setForeground(new java.awt.Color(50, 205, 50));
                TULogin.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/ok.png")));
                FALogin.setForeground(new java.awt.Color(50, 205, 50));
                FALogin.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/ok.png")));
            }
        }.start();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel DALogin;
    private javax.swing.JLabel FALogin;
    private javax.swing.JLabel TULogin;
    private javax.swing.JLabel artistsOnDisk;
    private javax.swing.JLabel imagesOnDisk;
    private javax.swing.JLabel imagesPerArtist;
    private javax.swing.JLabel internetConnection;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel niceMessage;
    private javax.swing.JLabel retryButton;
    private javax.swing.JLabel taskLabel;
    private javax.swing.JLabel webClientSetup;
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

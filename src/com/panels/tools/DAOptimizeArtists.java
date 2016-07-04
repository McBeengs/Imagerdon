/* **********   texp.java   **********
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

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.util.UsefulMethods;
import com.util.xml.XmlManager;
import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class DAOptimizeArtists {

    private Connection conn;
    private final XmlManager xml;
    private final XmlManager language;
    private UpdatingPanel panel;
    private int totalArtistsNum = 0;
    private int foundedArtistNum = 0;
    private ArrayList<String> onDisk;
    private ArrayList<String> onDB;
    private ArrayList<String> notFounded;
    private boolean isDone = false;

    @SuppressWarnings("CallToThreadStartDuringObjectConstruction")
    public DAOptimizeArtists() {
        xml = UsefulMethods.loadManager(UsefulMethods.OPTIONS);
        language = UsefulMethods.loadManager(UsefulMethods.LANGUAGE);
        onDisk = new ArrayList<>();
        onDB = new ArrayList<>();
        notFounded = new ArrayList<>();

        JOptionPane loading = new JOptionPane();
        final JLabel message = new JLabel(new ImageIcon(getClass().getResource("/com/style/icons/spinner.gif")));
        message.setText("Searching artists missing on DB...");
        loading.setMessage(message);

        new Thread() {
            @Override
            public void run() {
                try {
                    File check = new File(xml.getContentById("DAoutput"));
                    if (!check.exists()) {
                        check.mkdirs();
                    }

                    for (File f : check.listFiles()) {
                        onDisk.add(f.getName());
                    }
                    totalArtistsNum = onDisk.size();

                    conn = UsefulMethods.getDBInstance();
                    ResultSet rs = conn.createStatement().executeQuery("SELECT name FROM artist WHERE server = 0");
                    while (rs.next()) {
                        onDB.add(rs.getString("name"));
                    }
                    rs.close();
                    foundedArtistNum = onDB.size();

                    for (String s : onDisk) {
                        rs = conn.createStatement().executeQuery("SELECT * FROM artist WHERE name = '" + s + "' AND server = 0");
                        if (!rs.next()) {
                            notFounded.add(s);
                        }
                    }

                    message.setVisible(false);
                    isDone = true;
                } catch (SQLException ex) {
                    Logger.getLogger(DAOptimizeArtists.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();

        while (!isDone) {
            try {
                Thread.sleep(2);
            } catch (InterruptedException ex) {
                Logger.getLogger(DAOptimizeArtists.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (notFounded.isEmpty()) {
            JOptionPane.showMessageDialog(null, "The DB is up to date. No need of an optimize.");
            return;
        }

        if (JOptionPane.showConfirmDialog(null, notFounded.size() + " need to be optimized. Are you sure you want to continue?", "", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            panel = new UpdatingPanel();
            panel.setLabelText("Updating... " + (totalArtistsNum - foundedArtistNum) + " artists left");
            panel.setLabelIcon(new ImageIcon(getClass().getResource("/com/style/icons/spinner.gif")));
            panel.setProgressMinimum(0);
            panel.setProgressMaximum((totalArtistsNum - foundedArtistNum));

            JButton[] buttons = new JButton[]{};
            panel.start();
            JOptionPane.showOptionDialog(null, panel, "Updating", 0, 1, null, buttons, null);
        }
    }

    private void updateDB() throws Exception {
        while (!UsefulMethods.isWebClientReady()) {
            Thread.sleep(2);
        }

        WebClient webClient = UsefulMethods.getWebClientInstance();

        for (String artist : notFounded) {
            HtmlPage page = webClient.getPage("http://" + artist.toLowerCase() + ".deviantart.com/gallery/?catpath=/");
            Document parsed = Jsoup.parse(page.asXml());

            if (!page.asXml().contains("The page you were looking for doesn't exist.")) {
                PreparedStatement statement;
                if (!conn.createStatement().executeQuery("SELECT * FROM artist WHERE name = '" + artist + "' AND server = 0").next()) {
                    statement = conn.prepareStatement("INSERT INTO artist (`id`, `server`, `name`, `avatar_url`, `first_downloaded`, `last_updated`, `image_count`) VALUES (NULL, ?, ?, ?, ?, ?, ?);");
                    statement.setInt(1, 0);
                    statement.setString(2, artist);
                    statement.setString(3, parsed.select("img.avatar.float-left").get(0).attr("src"));
                    statement.setDate(4, new Date(new java.util.Date().getTime()));
                    statement.setDate(5, new Date(new java.util.Date().getTime()));
                    File[] getImageCount = new File(xml.getContentById("DAoutput") + File.separator
                            + artist).listFiles();
                    statement.setInt(6, getImageCount.length);

                    statement.execute();
                }
                totalArtistsNum--;
                panel.setLabelText("Updating... " + (totalArtistsNum - foundedArtistNum) + " artists left");
                panel.setProgressValue(panel.getProgressValue() + 1);
            } else {
                totalArtistsNum--;
                panel.setLabelText("Updating... " + (totalArtistsNum - foundedArtistNum) + " artists left");
                panel.setProgressValue(panel.getProgressValue() + 1);
            }
        }

        panel.setLabelIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/ok.png")));
        panel.setLabelText("Completed. You can exit now.");
    }

    private class UpdatingPanel extends JPanel {

        private final JLabel updatingLabel = new javax.swing.JLabel();
        private final javax.swing.JProgressBar updatingProgress = new javax.swing.JProgressBar();

        public UpdatingPanel() {
            initComponents();
        }

        private void initComponents() {
            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
            this.setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(updatingProgress, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                                    .addComponent(updatingLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addContainerGap())
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addContainerGap(25, Short.MAX_VALUE)
                            .addComponent(updatingLabel)
                            .addGap(18, 18, 18)
                            .addComponent(updatingProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(19, 19, 19))
            );
        }

        public void start() {
            new Thread() {
                @Override
                public void run() {
                    try {
                        updateDB();
                    } catch (Exception ex) {
                        Logger.getLogger(DAOptimizeArtists.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }.start();
        }

        public void setLabelText(String text) {
            updatingLabel.setText(text);
        }

        public void setLabelIcon(ImageIcon icon) {
            updatingLabel.setIcon(icon);
        }

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
    }
}

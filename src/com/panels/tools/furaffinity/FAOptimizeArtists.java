/* **********   FAOptimizeArtists.java   **********
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
package com.panels.tools.furaffinity;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.util.UsefulMethods;
import com.util.xml.XmlManager;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class FAOptimizeArtists extends javax.swing.JFrame {

    private final XmlManager xml;
    private final XmlManager language;
    private XmlManager artists;
    private final DefaultTableModel tableModel;
    private final ArrayList<String> missing;
    private UpdatingPanel panel;
    private int totalArtistsNum;
    private int foundedArtistNum;
    private boolean isDone = false;
    private String newContent;

    public FAOptimizeArtists() {
        xml = UsefulMethods.loadManager(UsefulMethods.OPTIONS);
        language = UsefulMethods.loadManager(UsefulMethods.LANGUAGE);
        missing = new ArrayList<>();

        initComponents();
        tableModel = (DefaultTableModel) table.getModel();
        table.getColumnModel().getColumn(1).setCellRenderer(new StatusColumnCellRenderer());
        initSubComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        displayer = new javax.swing.JLabel();
        button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Artist Recorded on Disk", "Artist Recorded on File"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setEnabled(false);
        table.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(table);

        displayer.setText("temp");

        button.setText("Update");
        button.setEnabled(false);
        button.setFocusable(false);
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(displayer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(button, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(displayer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonActionPerformed
        if (isDone) {
            panel = new UpdatingPanel();
            panel.setLabelText("Updating... " + (totalArtistsNum - foundedArtistNum) + " artists left");
            panel.setLabelIcon(new ImageIcon(getClass().getResource("/com/style/icons/spinner.gif")));
            panel.setProgressMinimum(0);
            panel.setProgressMaximum((totalArtistsNum - foundedArtistNum));

            final JButton btn = new JButton("Ok");
            btn.setFocusable(false);

            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (isDone) {
                        dispose();
                    }
                }
            });

            JButton[] buttons = new JButton[]{btn};
            panel.start();
            JOptionPane.showOptionDialog(null, panel, "Updating", 0, 1, null, buttons, null);
        }
    }//GEN-LAST:event_buttonActionPerformed

    private void initSubComponents() {
        setTitle("Optimize artists - FurAffinity");
        displayer.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/spinner.gif")));
        displayer.setText("Searching artists missing on DB...");

        new Thread() {
            @Override
            public void run() {
                try {
                    artists = new XmlManager();
                    File artistsXml = new File(xml.getContentById("FAoutput") + System.getProperty("file.separator") + "artists-log.xml");

                    if (!artistsXml.exists()) {
                        artists.createFile(artistsXml.getAbsolutePath());
                    } else {
                        artists.loadFile(artistsXml);
                    }

                    newContent = artists.toString();

                    File check = new File(xml.getContentById("FAoutput"));
                    if (!check.exists()) {
                        check.mkdirs();
                    }
                    File[] temp = check.listFiles();

                    File[] getArtistsOnDisk;
                    if (temp.length > 0) {
                        getArtistsOnDisk = new File[temp.length - 1];
                    } else {
                        getArtistsOnDisk = new File[0];
                    }

                    int skip = 0;
                    for (int i = 0; i < temp.length - 1; i++) {
                        if (!temp[i].getAbsolutePath().equals(xml.getContentById("FAoutput")
                                + System.getProperty("file.separator") + "artists-log.xml")) {
                            getArtistsOnDisk[i] = temp[i + skip];
                        } else {
                            skip++;
                            getArtistsOnDisk[i] = temp[i + skip];
                        }
                    }
                    totalArtistsNum = getArtistsOnDisk.length;
                    Object[] getArtistsOnFile = artists.getAllContentsByName("name").toArray();
                    Arrays.sort(getArtistsOnFile);
                    ArrayList<String> notFounded = new ArrayList<>();
                    foundedArtistNum = getArtistsOnFile.length;

                    for (int i = 0; i < getArtistsOnDisk.length; i++) {
                        Object[] set;
                        String nameDisk;
                        String nameFile;

                        if (getArtistsOnFile.length > i) {
                            nameDisk = getArtistsOnDisk[i].getName();
                            nameFile = getArtistsOnFile[i].toString();

                            if (nameDisk.equals(nameFile)) {
                                set = new Object[]{nameDisk, nameFile};
                            } else {
                                String founded = null;
                                for (int j = 0; j < notFounded.size(); j++) {
                                    if (notFounded.get(j).equals(nameDisk)) {
                                        founded = notFounded.get(j);
                                        notFounded.remove(j);
                                        break;
                                    }
                                }

                                if (founded != null) {
                                    set = new Object[]{nameDisk, founded};
                                } else {
                                    notFounded.add(nameFile);
                                    set = new Object[]{getArtistsOnDisk[i].getName(), "Not found"};
                                }
                            }
                        } else {
                            nameDisk = getArtistsOnDisk[i].getName();
                            String founded = null;
                            for (int j = 0; j < notFounded.size(); j++) {
                                if (notFounded.get(j).equals(nameDisk)) {
                                    founded = notFounded.get(j);
                                    notFounded.remove(j);
                                    break;
                                }
                            }

                            if (founded != null) {
                                set = new Object[]{nameDisk, founded};
                            } else {
                                set = new Object[]{getArtistsOnDisk[i].getName(), "Not found"};
                            }
                        }

                        tableModel.addRow(set);
                    }

                    displayer.setIcon(null);
                    displayer.setText(foundedArtistNum + " artists registered, " + totalArtistsNum + " founded on disk. "
                            + (totalArtistsNum - foundedArtistNum) + " need to be updated.");
                    isDone = true;

                    if ((totalArtistsNum - foundedArtistNum) != 0) {
                        button.setEnabled(true);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(FAOptimizeArtists.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
    }

    public void updateDB() {
        isDone = false;
        newContent = newContent.substring(0, newContent.length() - 8);

        try {
            for (int i = 0; i < table.getRowCount(); i++) {
                if (table.getValueAt(i, 1).toString().equals("Not found")) {
                    missing.add(table.getValueAt(i, 0).toString());
                }
            }

            while (!UsefulMethods.isWebClientReady()) {
                Thread.sleep(2);
            }

            WebClient webClient = UsefulMethods.getWebClientInstance();

            for (String artist : missing) {
                HtmlPage page = webClient.getPage("http://www.furaffinity.net/user/" + artist.toLowerCase());
                Document parsed = Jsoup.parse(page.asXml());

                if (!page.asXml().contains("has voluntarily disabled access to their account")
                        || !page.asXml().contains("This user cannot be found.")) {
                    String newTag = "<artist>\n<name>" + artist + "</name>\n" + "<avatarUrl>http:"
                            + parsed.select("img.avatar").get(0).attr("src") + "</avatarUrl>\n<firstDownloaded>"
                            + UsefulMethods.getSimpleDateFormat() + "</firstDownloaded>\n<lastUpdated>" + UsefulMethods.getSimpleDateFormat()
                            + "</lastUpdated>\n<pageVisits>";

                    Elements infoTable = parsed.select("table").get(3).select("td");
                    String temp = infoTable.get(12).text();
                    temp = temp.substring(temp.indexOf(":") + 2);
                    newTag += temp.substring(0, temp.indexOf(" ")) + "</pageVisits>\n<commentsReceived>";
                    temp = temp.substring(temp.indexOf(":") + 2);
                    temp = temp.substring(temp.indexOf(":") + 2);
                    newTag += temp.substring(0, temp.indexOf(" ")) + "</commentsReceived>\n<commentsGiven>";
                    temp = temp.substring(temp.indexOf(":") + 2);
                    newTag += temp.substring(0, temp.indexOf(" ")) + "</commentsGiven>\n<journals>";
                    temp = temp.substring(temp.indexOf(":") + 2);
                    newTag += temp.substring(0, temp.indexOf(" ")) + "</journals>\n<favourites>";
                    temp = temp.substring(temp.indexOf(":") + 2);
                    newTag += temp.replaceAll("[^0-9]", "") + "</favourites>\n<imageCount>";

                    File[] getImageCount = new File(xml.getContentById("FAoutput") + System.getProperty("file.separator")
                            + artist).listFiles();
                    newTag += getImageCount.length + "</imageCount>\n</artist>\n";

                    totalArtistsNum--;
                    panel.setLabelText("Updating... " + (totalArtistsNum - foundedArtistNum) + " artists left");
                    panel.setProgressValue(panel.getProgressValue() + 1);

                    newContent += newTag;
                } else {
                    totalArtistsNum--;
                    panel.setLabelText("Updating... " + (totalArtistsNum - foundedArtistNum) + " artists left");
                    panel.setProgressValue(panel.getProgressValue() + 1);
                }
            }

            File file = new File(xml.getContentById("FAoutput") + System.getProperty("file.separator") + "artists-log.xml");
            if (file.exists()) {
                file.delete();
            } else {
                file.createNewFile();
            }

            newContent += "</root>";
            artists.createFile(file);
            artists.setContent(newContent);
            artists.saveXml();

            panel.setLabelIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/ok.png")));
            panel.setLabelText("Completed. Press \"Ok\" to exit.");

        } catch (Exception ex) {
            Logger.getLogger(FAOptimizeArtists.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button;
    private javax.swing.JLabel displayer;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables

    private class StatusColumnCellRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (label.getText().equals("Not found")) {
                label.setBackground(new Color(255, 64, 64));
            } else {
                label.setBackground(new Color(84, 255, 156));
            }

            return label;
        }
    }

    private class UpdatingPanel extends JPanel {

        private JLabel updatingLabel = new javax.swing.JLabel();
        private javax.swing.JProgressBar updatingProgress = new javax.swing.JProgressBar();

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
                    updateDB();
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

/* **********   ArtistsInfoPanel.java   **********
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
package com.panels.main;

import com.util.UsefulMethods;
import com.util.xml.XmlManager;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ArtistInfoPanel extends javax.swing.JPanel {

    private Connection conn;
    private final XmlManager xml;
    private final XmlManager language;
    private final String artist;
    private final int server;
    private final int artistId;
    private boolean isFaved;

    public ArtistInfoPanel(String artist, int server, final int artistId) {
        this.artist = artist;
        this.server = server;
        this.artistId = artistId;
        xml = UsefulMethods.loadManager(UsefulMethods.OPTIONS);
        language = UsefulMethods.loadManager(UsefulMethods.LANGUAGE);
        initComponents();

        jTabbedPane1.setTitleAt(0, language.getContentById("tabActions"));
        jTabbedPane1.setTitleAt(1, language.getContentById("tabInfo"));

        tagTable.getColumnModel().getColumn(0).setCellRenderer(new TableRenderer());

        tagTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    final int row = tagTable.rowAtPoint(e.getPoint());
                    final String tag = (String) tagTable.getValueAt(row, 0);
                    final DefaultTableModel model = (DefaultTableModel) tagTable.getModel();

                    final ArrayList<String> tags = new ArrayList<>();
                    ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM tag");
                    tags.add("99999- " + language.getContentById("selectTag") + " -");
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String helper;
                        if (id < 10) {
                            helper = "0000" + id;
                        } else if (id > 9) {
                            helper = "000" + id;
                        } else if (id > 99) {
                            helper = "00" + id;
                        } else if (id > 999) {
                            helper = "0" + id;
                        } else {
                            helper = "" + id;
                        }
                        tags.add(helper + rs.getString("tag"));
                    }
                    rs.close();

                    if (row == 0 && e.getClickCount() == 2) {

                        Object[] choose = new Object[tags.size()];
                        for (int i = 0; i < tags.size(); i++) {
                            choose[i] = tags.get(i).substring(5);
                        }

                        Object res = JOptionPane.showInputDialog(null, language.getContentById("selectTag"), "", JOptionPane.INFORMATION_MESSAGE, null, choose, null);
                        if (res != null) {
                            int i;
                            for (i = 0; i < tags.size(); i++) {
                                if (tags.get(i).contains(res.toString())) {
                                    String s = tags.get(i);
                                    i = Integer.parseInt(s.substring(0, 5));
                                    break;
                                }
                            }
                            rs = conn.createStatement().executeQuery("SELECT * FROM inner_tag WHERE artist_id = " + artistId + " AND tag_id = " + i);
                            if (!rs.next()) {
                                conn.createStatement().execute("INSERT INTO inner_tag (`artist_id`, `tag_id`) VALUES (" + artistId + ", " + i + ")");
                            }
                            setInfo();
                            rs.close();
                        }

                    } else if (row > 0 && e.getButton() == 3) {
                        final String show = language.getContentById("eraseTag");
                        JPopupMenu menu = new JPopupMenu(show);
                        JMenuItem item = new JMenuItem(new AbstractAction() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (JOptionPane.showConfirmDialog(null, show + " \"" + tag + "\"?") == JOptionPane.YES_OPTION) {
                                    int i;
                                    for (i = 0; i < tags.size(); i++) {
                                        if (tags.get(i).contains(model.getValueAt(row, 0).toString())) {
                                            String s = tags.get(i);
                                            i = Integer.parseInt(s.substring(0, 5));
                                            break;
                                        }
                                    }
                                    try {
                                        conn.createStatement().execute("DELETE FROM inner_tag WHERE artist_id = " + artistId + " AND tag_id = " + i);
                                        setInfo();
                                    } catch (SQLException ex) {
                                        Logger.getLogger(ArtistInfoPanel.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                        });
                        item.setText(show + " \"" + tag + "\"");
                        menu.add(item);
                        menu.show(e.getComponent(), e.getX(), e.getY());
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ArtistInfoPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        setInfo();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        favButton = new javax.swing.JLabel();
        folderButton = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        updateButton = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        deleteButton = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        infoSubapanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        descriptionText = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        tagTable = new javax.swing.JTable();

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText(language.getContentById("markFav"));

        favButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        favButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/favorite.png"))); // NOI18N
        favButton.setEnabled(false);
        favButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                favButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                favButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                favButtonMouseExited(evt);
            }
        });

        folderButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        folderButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/folder.png"))); // NOI18N
        folderButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                folderButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                folderButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                folderButtonMouseExited(evt);
            }
        });

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText(language.getContentById("openFolder"));

        updateButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        updateButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/update.png"))); // NOI18N
        updateButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                updateButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                updateButtonMouseExited(evt);
            }
        });

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText(language.getContentById("updateTitle"));

        deleteButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        deleteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/delete.png"))); // NOI18N
        deleteButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                deleteButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                deleteButtonMouseExited(evt);
            }
        });

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText(language.getContentById("deleteGallery"));

        jLabel2.setText("Random picture");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel2MouseExited(evt);
            }
        });

        jLabel4.setText(language.getContentById("openPage"));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel4MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 70, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(updateButton)
                                    .addComponent(folderButton))
                                .addGap(27, 27, 27)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(favButton)
                                    .addGap(27, 27, 27)))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(deleteButton)
                                    .addGap(27, 27, 27))))
                        .addGap(0, 64, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(favButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(folderButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)))
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(updateButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(deleteButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Actions", jPanel2);

        jButton1.setText(language.getContentById("edit"));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText(language.getContentById("description") + ":");

        descriptionText.setEditable(false);
        descriptionText.setColumns(20);
        descriptionText.setLineWrap(true);
        descriptionText.setRows(5);
        descriptionText.setWrapStyleWord(true);
        descriptionText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                descriptionTextKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(descriptionText);

        tagTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tagTable);
        if (tagTable.getColumnModel().getColumnCount() > 0) {
            tagTable.getColumnModel().getColumn(0).setHeaderValue(language.getContentById("tags")
            );
        }

        javax.swing.GroupLayout infoSubapanelLayout = new javax.swing.GroupLayout(infoSubapanel);
        infoSubapanel.setLayout(infoSubapanelLayout);
        infoSubapanelLayout.setHorizontalGroup(
            infoSubapanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoSubapanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(infoSubapanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                    .addGroup(infoSubapanelLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        infoSubapanelLayout.setVerticalGroup(
            infoSubapanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoSubapanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(infoSubapanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Info", infoSubapanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (!descriptionText.isEditable()) {
            jButton1.setText(language.getContentById("save"));
        } else {
            jButton1.setEnabled(false);
            try {
                PreparedStatement statement = conn.prepareStatement("UPDATE info SET description = ? WHERE artist_id = ?");
                statement.setString(1, descriptionText.getText());
                statement.setInt(2, artistId);
                statement.execute();
            } catch (SQLException ex) {
                Logger.getLogger(ArtistInfoPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            jButton1.setText(language.getContentById("edit"));
            jButton1.setEnabled(true);
        }

        descriptionText.setEditable(!descriptionText.isEditable());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void favButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_favButtonMouseEntered
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_favButtonMouseEntered

    private void favButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_favButtonMouseExited
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_favButtonMouseExited

    private void favButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_favButtonMouseClicked
        if (isFaved) {
            favButton.setEnabled(false);
            jLabel3.setText(language.getContentById("markFav"));
        } else {
            favButton.setEnabled(true);
            jLabel3.setText(language.getContentById("unmarkFav"));
        }

        isFaved = !isFaved;
        try {
            PreparedStatement statement = conn.prepareStatement("UPDATE info SET fav = ? WHERE artist_id = ?");
            statement.setBoolean(1, isFaved);
            statement.setInt(2, artistId);
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ArtistInfoPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_favButtonMouseClicked

    private void folderButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_folderButtonMouseClicked
        String path = null;
        switch (server) {
            case 0:
                path = xml.getContentById("DAoutput") + File.separator;
                break;
            case 1:
                path = xml.getContentById("TUoutput") + File.separator;
                break;
            case 2:
                path = xml.getContentById("FAoutput") + File.separator;
                break;
            case 3:
                path = xml.getContentById("E621output") + File.separator;
                break;
        }

        try {
            Desktop.getDesktop().open(new File(path + artist));
        } catch (IOException ex) {
            Logger.getLogger(ArtistInfoPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_folderButtonMouseClicked

    private void folderButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_folderButtonMouseEntered
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_folderButtonMouseEntered

    private void folderButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_folderButtonMouseExited
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_folderButtonMouseExited

    private void updateButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateButtonMouseClicked
        String artistUrl = "";
        switch (server) {
            case 0:
                artistUrl = "http://" + artist.toLowerCase() + ".deviantart.com/gallery/?catpath=/";
                break;
            case 1:
                artistUrl = "http://" + artist.toLowerCase() + ".tumblr.com/archive/";
                break;
            case 2:
                artistUrl = "http://www.furaffinity.net/gallery/" + artist.toLowerCase() + "/";
                break;
            case 3:
                artistUrl = "https://e621.net/post/index/1/" + artist.toLowerCase() + "/";
                break;
        }
        StylizedMainJFrame.ADD_TASK.addTask(artistUrl, server, -1);
    }//GEN-LAST:event_updateButtonMouseClicked

    private void updateButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateButtonMouseEntered
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_updateButtonMouseEntered

    private void updateButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateButtonMouseExited
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_updateButtonMouseExited

    private void deleteButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteButtonMouseClicked
        if (JOptionPane.showConfirmDialog(null, language.getContentById("deleteArtist").replace("&string", artist),
                language.getContentById("genericWarningTitle"), JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
            try {
                conn.createStatement().execute("DELETE FROM artist WHERE id = " + artistId);
                conn.createStatement().execute("DELETE FROM inner_tag WHERE artist_id = " + artistId);
                conn.createStatement().execute("DELETE FROM info WHERE artist_id = " + artistId);

                if (JOptionPane.showConfirmDialog(null, language.getContentById("deleteImages"), "",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    String path = null;
                    switch (server) {
                        case 0:
                            path = xml.getContentById("DAoutput") + File.separator;
                            break;
                        case 1:
                            path = xml.getContentById("TUoutput") + File.separator;
                            break;
                        case 2:
                            path = xml.getContentById("FAoutput") + File.separator;
                            break;
                        case 3:
                            path = xml.getContentById("E621output") + File.separator;
                            break;
                    }

                    File[] images = new File(path + artist).listFiles();
                    if (images != null && images.length > 0) {
                        for (File f : images) {
                            f.delete();
                        }
                    }
                    new File(path + artist).delete();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ArtistInfoPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            ArtistsOptionsJFrame.NOTIFIER.fireArtistDeletedEvent(artist, server);
        }
    }//GEN-LAST:event_deleteButtonMouseClicked

    private void deleteButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteButtonMouseEntered
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_deleteButtonMouseEntered

    private void deleteButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteButtonMouseExited
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_deleteButtonMouseExited

    private void jLabel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseEntered
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_jLabel2MouseEntered

    private void jLabel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseExited
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_jLabel2MouseExited

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        String path = null;
        switch (server) {
            case 0:
                path = xml.getContentById("DAoutput") + File.separator;
                break;
            case 1:
                path = xml.getContentById("TUoutput") + File.separator;
                break;
            case 2:
                path = xml.getContentById("FAoutput") + File.separator;
                break;
            case 3:
                path = xml.getContentById("E621output") + File.separator;
                break;
        }

        File[] images = new File(path + artist).listFiles();
        Random rgn = new Random();

        try {
            if (images.length > 0) {
                Desktop.getDesktop().open(images[rgn.nextInt(images.length)]);
            }
        } catch (IOException ex) {
            Logger.getLogger(ArtistInfoPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel2MouseClicked

    private void descriptionTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_descriptionTextKeyPressed
        if (descriptionText.getText().length() >= 255) {
            descriptionText.setText(descriptionText.getText().substring(0, 254));
        }
    }//GEN-LAST:event_descriptionTextKeyPressed

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        String url = null;
        switch (server) {
            case 0:
                url = "http://" + artist.toLowerCase() + ".deviantart.com/gallery/";
                break;
            case 1:
                url = "http://" + artist.toLowerCase() + ".tumblr.com/";
                break;
            case 2:
                url = "http://www.furaffinity.net/gallery/" + artist.toLowerCase();
                break;
            case 3:
                url = "https://e621.net/post/index/" + artist.toLowerCase();
                break;
        }

        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(ArtistInfoPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseEntered
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_jLabel4MouseEntered

    private void jLabel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseExited
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_jLabel4MouseExited

    private void setInfo() {
        try {
            DefaultTableModel model = (DefaultTableModel) tagTable.getModel();

            for (int i = model.getRowCount() - 1; i >= 0; i--) {
                model.removeRow(i);
            }

            conn = UsefulMethods.getDBInstance();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM info WHERE artist_id = " + artistId);
            if (!rs.next()) {
                conn.createStatement().execute("INSERT INTO info (`artist_id`, `description`, `fav`) VALUES (" + artistId + ", '" + language.getContentById("descTemp") + "', 'false')");
                rs = conn.createStatement().executeQuery("SELECT * FROM info INNER JOIN artist WHERE artist_id = " + artistId);
                rs.next();
            }
            descriptionText.setText(rs.getString("description"));
            isFaved = rs.getBoolean("fav");

            if (isFaved) {
                favButton.setEnabled(true);
                jLabel3.setText(language.getContentById("unmarkFav"));
            }

            model.addRow(new Object[]{language.getContentById("addTag")});

            rs = conn.createStatement().executeQuery("SELECT tag_id FROM inner_tag WHERE artist_id = " + artistId);
            while (rs.next()) {
                ResultSet tag = conn.createStatement().executeQuery("SELECT tag FROM tag WHERE id = " + rs.getInt("tag_id"));
                if (tag.next()) {
                    model.addRow(new Object[]{tag.getString("tag")});
                }
            }

            if (model.getRowCount() == 1) {
                model.addRow(new Object[]{language.getContentById("noTags")});
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArtistInfoPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setTab(int tab) {
        jTabbedPane1.setSelectedIndex(tab);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel deleteButton;
    private javax.swing.JTextArea descriptionText;
    private javax.swing.JLabel favButton;
    private javax.swing.JLabel folderButton;
    private javax.swing.JPanel infoSubapanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tagTable;
    private javax.swing.JLabel updateButton;
    // End of variables declaration//GEN-END:variables

    private class TableRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = new JLabel(value.toString());

            if (value.toString().equals(language.getContentById("addTag"))) {
                label.setBackground(new Color(153, 255, 102));
                label.setText("<html><p style='color: green;'>" + label.getText() + "</p></html>");
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setOpaque(true);
            }
            return label;
        }

    }
}

/* **********   ArtistsOptionsJFrame.java   **********
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
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class ArtistsOptionsJFrame extends javax.swing.JFrame {

    private Connection conn;
    private final XmlManager xml;
    private final XmlManager language;
    private DefaultTableModel tableModel;
    private JPopupMenu menu;
    private Map<String, ArrayList<String>> allArtists;
    private int selectedServer = 0;
    private int selectedIndex = 0;
    private String avatarUrl;
    public static DeleteNotifier NOTIFIER;

    public ArtistsOptionsJFrame() {
        NOTIFIER = new DeleteNotifier();
        conn = UsefulMethods.getDBInstance();
        xml = UsefulMethods.loadManager(UsefulMethods.OPTIONS);
        language = UsefulMethods.loadManager(UsefulMethods.LANGUAGE);
        allArtists = new HashMap<>();

        initComponents();
        showPanel.setLayout(new GridLayout(0, 1));

        ImageIcon[] icons = new ImageIcon[7];
        icons[0] = new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/search.png"));
        icons[1] = new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/deviantArtIconBig.png"));
        icons[2] = new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/tumblrIconBig.png"));
        icons[3] = new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/FAIconBig.png"));
        icons[4] = new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/e621IconBig.png"));
        icons[5] = new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/favorite.png"));
        icons[6] = new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/list.png"));

        String[] items = new String[]{"All artists", language.getContentById("deviantArt"), language.getContentById("tumblr"),
            language.getContentById("furAffinity"), language.getContentById("e621"), "By Favorite", "By Tag"};

        artistsCombo.setRenderer(new IconComboReader(icons));
        artistsCombo.setModel(new DefaultComboBoxModel<>(items));

        tableModel = (DefaultTableModel) artistsTable.getModel();

        artistsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int r = artistsTable.rowAtPoint(e.getPoint());
                if (r >= 0 && r < artistsTable.getRowCount()) {
                    artistsTable.setRowSelectionInterval(r, r);
                } else {
                    artistsTable.clearSelection();
                }

                int rowindex = artistsTable.getSelectedRow();
                if (rowindex < 0) {
                    return;
                }
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
                    JPopupMenu popup = createArtistPopup(rowindex);

                    popup.show(e.getComponent(), e.getX(), e.getY());
                } else if (e.getClickCount() == 2) {
                    try {
                        selectedIndex = rowindex;
                        selectArtist(rowindex);
                    } catch (Exception ex) {
                        Logger.getLogger(ArtistsOptionsJFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        setTable(0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        searchPanel = new javax.swing.JPanel();
        searchText = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        artistsCombo = new javax.swing.JComboBox<>();
        showingLabel = new javax.swing.JLabel();
        artistAvatar = new javax.swing.JLabel();
        artistNameLabel = new javax.swing.JLabel();
        serverLabel = new javax.swing.JLabel();
        firstDownloadedLabel = new javax.swing.JLabel();
        date1Label = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        artistsTable = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        lastUpdateLabel = new javax.swing.JLabel();
        date2Label = new javax.swing.JLabel();
        showPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        searchPanel.setBackground(new java.awt.Color(255, 255, 255));
        searchPanel.setBorder(new RoundedCornerBorder());

        searchText.setText("Search an artist...");
        searchText.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 0));
        searchText.setOpaque(false);
        searchText.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                searchTextCaretUpdate(evt);
            }
        });
        searchText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchTextMouseClicked(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/search.png"))); // NOI18N

        javax.swing.GroupLayout searchPanelLayout = new javax.swing.GroupLayout(searchPanel);
        searchPanel.setLayout(searchPanelLayout);
        searchPanelLayout.setHorizontalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchText, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        searchPanelLayout.setVerticalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(searchText, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jLabel1)
        );

        artistsCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6" }));
        artistsCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                artistsComboActionPerformed(evt);
            }
        });

        showingLabel.setText("Showing:");

        artistAvatar.setBackground(new java.awt.Color(153, 153, 153));
        artistAvatar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        artistAvatar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/noAvatar.jpg"))); // NOI18N
        artistAvatar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        artistAvatar.setOpaque(true);
        artistAvatar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                artistAvatarMouseClicked(evt);
            }
        });

        artistNameLabel.setFont(new java.awt.Font("Eras Bold ITC", 0, 18)); // NOI18N
        artistNameLabel.setText("A Great Name");

        serverLabel.setText("DeviantArt... Maybe?");

        firstDownloadedLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        firstDownloadedLabel.setText("First downloaded:");

        date1Label.setText("October 26, 1985");

        artistsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        artistsTable.setColumnSelectionAllowed(true);
        artistsTable.getTableHeader().setReorderingAllowed(false);
        artistsTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                artistsTableKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(artistsTable);
        artistsTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (artistsTable.getColumnModel().getColumnCount() > 0) {
            artistsTable.getColumnModel().getColumn(0).setResizable(false);
        }

        lastUpdateLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        lastUpdateLabel.setText("Last update:");

        date2Label.setText("Someday at 20XX");

        javax.swing.GroupLayout showPanelLayout = new javax.swing.GroupLayout(showPanel);
        showPanel.setLayout(showPanelLayout);
        showPanelLayout.setHorizontalGroup(
            showPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        showPanelLayout.setVerticalGroup(
            showPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 303, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(showingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(artistsCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(searchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(artistAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(serverLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(firstDownloadedLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(date1Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(artistNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lastUpdateLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(date2Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                    .addComponent(jSeparator2)
                    .addComponent(showPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(searchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(artistAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(artistNameLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(serverLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(firstDownloadedLabel)
                                    .addComponent(date1Label, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lastUpdateLabel)
                                    .addComponent(date2Label, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(showPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(showingLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(artistsCombo, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2)))
                .addGap(20, 20, 20))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    MenuScroller scroller;

    private void searchTextCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_searchTextCaretUpdate
        if (searchText.getText().isEmpty()) {
            menu.setVisible(false);
            scroller = null;
            return;
        }

        artistsCombo.setSelectedIndex(0);
        final String text = searchText.getText();
        menu = new JPopupMenu();
        menu.setFocusable(false);
        scroller = new MenuScroller(menu);

        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM artist WHERE name LIKE '%" + text + "%'");
            while (rs.next()) {
                final String db = rs.getString("name");
                JMenuItem open = new JMenuItem(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int i;
                        for (i = 0; i < tableModel.getRowCount(); i++) {
                            if (tableModel.getValueAt(i, 0).toString().substring(1).equals(db)) {
                                break;
                            }
                        }
                        try {
                            selectArtist(i);
                        } catch (Exception ex) {
                            Logger.getLogger(ArtistsOptionsJFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });

                String painted = "<html>";
                for (int j = 0; j < db.length(); j++) {
                    if (j + text.length() <= db.length() && db.toLowerCase()
                            .substring(j, j + text.length()).equals(text.toLowerCase())) {
                        painted += "<span style='color: red;'>" + db.substring(j, j + text.length())
                                + "</span>";

                        if (text.length() < 1) {
                            j += text.length() + 1;
                        } else {
                            j += text.length() - 1;
                        }
                    } else {
                        painted += db.substring(j, j + 1);
                    }
                }
                painted += "</html>";

                open.setText(painted);
                menu.add(open);
            }
        } catch (SQLException ex) {

        }

        if (menu.getComponentCount() > 0) {
            menu.setVisible(true);
            menu.show(searchText, searchText.getX() - 18, searchText.getY() + 32);
        } else {
            String show = "No artists found :(";

            if (show.length() < 75) {
                while (show.length() < 75) {
                    show += " ";
                }
            }

            JMenuItem open = new JMenuItem(show);
            menu.add(open);
            menu.setVisible(true);
            menu.show(searchText, searchText.getX() - 18, searchText.getY() + 32);
        }
    }//GEN-LAST:event_searchTextCaretUpdate

    private void searchTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTextMouseClicked
        searchText.setText("");
        artistsCombo.setSelectedItem(0);
        setTable(0);
        try {
            selectArtist(0);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, language.getContentById("internetDroppedOut"), language.getContentById("genericErrorTitle"), JOptionPane.OK_OPTION);
        }
    }//GEN-LAST:event_searchTextMouseClicked

    private void artistsComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_artistsComboActionPerformed
        setTable(selectedServer);
    }//GEN-LAST:event_artistsComboActionPerformed

    private void artistAvatarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_artistAvatarMouseClicked
        if (evt.getButton() == 3) {
            JPopupMenu popup = new JPopupMenu();

            JMenuItem download = new JMenuItem(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser chooser = new JFileChooser(System.getProperty("user.home"));
                    chooser.setAcceptAllFileFilterUsed(false);
                    chooser.setSelectedFile(new File(System.getProperty("user.home") + System.getProperty("file.separator")
                            + avatarUrl));

                    if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                        try {
                            InputStream is = new URL(avatarUrl).openStream();
                            OutputStream imageFile = new FileOutputStream(chooser.getSelectedFile());
                            BufferedOutputStream writeImg = new BufferedOutputStream(imageFile);

                            int bytes;
                            while ((bytes = is.read()) != -1) {
                                writeImg.write(bytes);
                            }

                            writeImg.close();
                            imageFile.close();
                            is.close();

                        } catch (MalformedURLException ex) {
                            Logger.getLogger(ArtistsOptionsJFrame.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(ArtistsOptionsJFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });

            download.setText("Download Icon");
            popup.add(download);

            popup.show(artistAvatar, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_artistAvatarMouseClicked

    private void artistsTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_artistsTableKeyPressed
        try {
            switch (evt.getKeyCode()) {
                case 40: // down
                    if (selectedIndex + 1 < tableModel.getRowCount()) {
                        selectedIndex++;
                    }
                    selectArtist(selectedIndex);
                    break;
                case 38: // up
                    if (selectedIndex - 1 >= 0) {
                        selectedIndex--;
                    }
                    selectArtist(selectedIndex);
                    break;
            }
        } catch (Exception ex) {

        }
    }//GEN-LAST:event_artistsTableKeyPressed

    private void setTable(int status) {
        try {
            if (tableModel.getRowCount() > 0) {
                tableModel.setRowCount(0);
            }

            if (allArtists.isEmpty()) {
                ArrayList<String> da = new ArrayList<>();
                ArrayList<String> tu = new ArrayList<>();
                ArrayList<String> fa = new ArrayList<>();
                ArrayList<String> e621 = new ArrayList<>();

                ResultSet rs = conn.createStatement().executeQuery("SELECT server, name FROM artist");
                while (rs.next()) {
                    switch (rs.getInt("server")) {
                        case 0:
                            da.add("0" + rs.getString("name"));
                            break;
                        case 1:
                            tu.add("1" + rs.getString("name"));
                            break;
                        case 2:
                            fa.add("2" + rs.getString("name"));
                            break;
                        case 3:
                            e621.add("3" + rs.getString("name"));
                            break;
                    }
                }

                allArtists.put("DA", da);
                allArtists.put("TU", tu);
                allArtists.put("FA", fa);
                allArtists.put("E621", e621);
            }

            ArrayList<String> inside;
            switch (status) {
                case 0:
                    inside = new ArrayList<>();
                    for (String s : allArtists.get("DA")) {
                        inside.add(s);
                    }
                    for (String s : allArtists.get("TU")) {
                        inside.add(s);
                    }
                    for (String s : allArtists.get("FA")) {
                        inside.add(s);
                    }
                    for (String s : allArtists.get("E621")) {
                        inside.add(s);
                    }
                    break;
                case 1:
                    inside = allArtists.get("DA");
                    break;
                case 2:
                    inside = allArtists.get("TU");
                    break;
                case 3:
                    inside = allArtists.get("FA");
                    break;
                case 4:
                    inside = allArtists.get("E621");
                    break;
                case 5:
                    inside = new ArrayList<>();
                    ResultSet rs = conn.createStatement().executeQuery("SELECT id, server, name FROM artist");
                    while (rs.next()) {
                        ResultSet favs = conn.createStatement().executeQuery("SELECT fav FROM info WHERE artist_id = " + rs.getInt("id"));
                        while(favs.next()) {
                            if (favs.getBoolean("fav")) {
                                inside.add(rs.getInt("server") + rs.getString("name"));
                            }
                        }
                    }
                    break;
                case 6:
                    inside = new ArrayList<>();
                    //SELECT artist_id FROM inner_tag WHERE tag_id = " + tags.getInt("id") + ")
                    ResultSet tags = conn.createStatement().executeQuery("SELECT * FROM tag");
                    while (tags.next()) {
                        inside.add("99999" + tags.getString("tag"));
                        ResultSet artists = conn.createStatement().executeQuery("SELECT id, server, name FROM artist");
                        while (artists.next()) {
                            rs = conn.createStatement().executeQuery("SELECT * FROM inner_tag WHERE tag_id = " + tags.getInt("id"));
                            while (rs.next()) {
                                if (rs.getInt("artist_id") == artists.getInt("id") && rs.getInt("tag_id") == tags.getInt("id")) {
                                    inside.add(artists.getInt("server") + artists.getString("name"));
                                    break;
                                }
                            }
                        }
                    }
                    break;
                default:
                    inside = new ArrayList<>();
                    break;
            }

            artistsTable.getColumnModel().getColumn(0).setCellRenderer(getRenderer());
            if (!inside.isEmpty()) {
                for (String s : inside) {
                    tableModel.addRow(new Object[]{s});
                }
                selectArtist(0);
            } else {
                tableModel.addRow(new Object[]{"-No data found :("});
            }

        } catch (SQLException ex) {
            Logger.getLogger(ArtistsOptionsJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ArtistsOptionsJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private TableCellRenderer getRenderer() {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                String text = value.toString().substring(1);
                JLabel component = new JLabel(text);

                try {
                    ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM tag");
                    while (rs.next()) {
                        if (text.substring(4).equals(rs.getString("tag"))) {
                            component.setText(text.substring(4));
                            component.setBackground(new Color(255, 102, 102));
                            component.setHorizontalAlignment(SwingConstants.CENTER);
                            component.setOpaque(true);
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ArtistsOptionsJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if (isSelected) {
                    component.setBackground(new Color(51, 153, 255));
                    component.setText("<html><p style='color: white;'>" + component.getText() + "</p></html>");
                    component.setOpaque(hasFocus);
                }
                return component;
            }
        };
    }

    private void selectArtist(int rowIndex) throws Exception {
        String name = tableModel.getValueAt(rowIndex, 0).toString().substring(1);
        if (name.equals("No data found :(")) {
            return;
        }

        selectedServer = Integer.parseInt(tableModel.getValueAt(rowIndex, 0).toString().substring(0, 1));
        if (selectedServer >= 7) {
            return;
        }
        final ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM artist WHERE name = '" + name + "' AND server = " + selectedServer);
        rs.next();

        if (name.length() > 20) {
            artistNameLabel.setText(name.substring(0, 20) + "...");
        } else {
            artistNameLabel.setText(name);
        }

        switch (selectedServer) {
            case 0:
                serverLabel.setText("DeviantArt");
                break;
            case 1:
                serverLabel.setText("Tumblr");
                break;
            case 2:
                serverLabel.setText("FurAffinity");
                break;
            case 3:
                serverLabel.setText("e621");
                break;
        }

        artistAvatar.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/spinner.gif")));
        new Thread() {
            @Override
            public void run() {
                try {
                    artistAvatar.setIcon(new ImageIcon(new URL(rs.getString("avatar_url"))));
                } catch (MalformedURLException | SQLException ex) {
                    artistAvatar.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/noAvatar.jpg")));
                }
            }
        }.start();

        Date today = new Date(new java.util.Date().getTime());
        date1Label.setText(rs.getDate("first_downloaded").toString().replace("-", "/"));
        long days = TimeUnit.MILLISECONDS.toDays(today.getTime() - rs.getDate("last_updated").getTime());
        if (days == 0) {
            date2Label.setText("Just a while ago");
        } else if (days == 1) {
            date2Label.setText("A day ago");
        } else {
            date2Label.setText(days + " days ago");
        }

        showPanel.removeAll();
        showPanel.add(new ArtistInfoPanel(name, selectedServer, rs.getInt("id")));
    }

    private JPopupMenu createArtistPopup(final int rowIndex) {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem open = new JMenuItem(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    selectArtist(rowIndex);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, language.getContentById("internetDroppedOut"), language.getContentById("genericErrorTitle"), JOptionPane.OK_OPTION);
                }
            }
        });

        open.setText("Open artists details");
        popup.add(open);
        return popup;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel artistAvatar;
    private javax.swing.JLabel artistNameLabel;
    private javax.swing.JComboBox<String> artistsCombo;
    private javax.swing.JTable artistsTable;
    private javax.swing.JLabel date1Label;
    private javax.swing.JLabel date2Label;
    private javax.swing.JLabel firstDownloadedLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lastUpdateLabel;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JTextField searchText;
    private javax.swing.JLabel serverLabel;
    private javax.swing.JPanel showPanel;
    private javax.swing.JLabel showingLabel;
    // End of variables declaration//GEN-END:variables

    private class RoundedCornerBorder extends javax.swing.border.AbstractBorder {

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int r = height - 1;
            RoundRectangle2D round = new RoundRectangle2D.Float(x, y, width - 1, height - 1, r, r);
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

    private class IconComboReader extends DefaultListCellRenderer {

        private final ImageIcon[] icons;
        private ImageIcon show;

        public IconComboReader(ImageIcon[] icons) {

            this.icons = icons;
            show = icons[0];
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            switch (index) {
                case -1:
                    label.setIcon(show);
                    break;
                case 0:
                    label.setIcon(icons[0]);
                    break;
                case 1:
                    label.setIcon(icons[1]);
                    break;
                case 2:
                    label.setIcon(icons[2]);
                    break;
                case 3:
                    label.setIcon(icons[3]);
                    break;
                case 4:
                    label.setIcon(icons[4]);
                    break;
                case 5:
                    label.setIcon(icons[5]);
                    break;
                case 6:
                    label.setIcon(icons[6]);
                    break;
                case 7:
                    label.setIcon(icons[7]);
                    break;
            }

            if (isSelected) {
                if (index >= 0) {
                    show = icons[index];
                    selectedServer = index;
                }
            }

            return label;
        }
    }

    public class DeleteNotifier {

        public void fireArtistDeletedEvent(String artist, int server) {
            try {
                for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
                    if (tableModel.getValueAt(i, 0).toString().substring(1).equals(artist)) {
                        tableModel.removeRow(i);
                    }
                }
                selectArtist(0);
            } catch (Exception ex) {
                Logger.getLogger(ArtistsOptionsJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

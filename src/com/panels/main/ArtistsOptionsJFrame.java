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
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ArtistsOptionsJFrame extends javax.swing.JFrame {

    private final XmlManager xml;
    private final XmlManager language;
    private DefaultTableModel tableModel;
    private JScrollPopupMenu menu;
    private Map<String, Object[]> allArtists;
    private List<Integer> allArtistsServers;
    private int selectedServer = 0;
    private String avatarUrl;

    public ArtistsOptionsJFrame() {
        xml = UsefulMethods.loadManager(UsefulMethods.OPTIONS);
        language = UsefulMethods.loadManager(UsefulMethods.LANGUAGE);
        allArtists = new HashMap<>();
        allArtistsServers = new ArrayList<>();

        initComponents();
        showPanel.setLayout(new GridLayout());

        menu = new JScrollPopupMenu();
        menu.setFocusable(false);

        ImageIcon[] icons = new ImageIcon[6];
        icons[0] = new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/search.png"));
        icons[1] = new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/deviantArtIconBig.png"));
        icons[2] = new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/tumblrIconBig.png"));
        icons[3] = new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/galleryHentaiIconBig.png"));
        icons[4] = new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/FAIconBig.png"));
        icons[5] = new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/e621IconBig.png"));

        String[] items = new String[]{"All artists", language.getContentByName("mainLabel", 2), language.getContentByName("mainLabel", 3),
            language.getContentByName("mainLabel", 4), language.getContentByName("mainLabel", 5), language.getContentByName("mainLabel", 6)};

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
                        selectArtist(rowindex);
                    } catch (IOException ex) {
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
        showPanel = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        lastUpdateLabel = new javax.swing.JLabel();
        date2Label = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

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
        artistAvatar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        artistAvatar.setOpaque(true);
        artistAvatar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                artistAvatarMouseClicked(evt);
            }
        });

        artistNameLabel.setFont(new java.awt.Font("Eras Bold ITC", 0, 18)); // NOI18N
        artistNameLabel.setText("[Artist Name]");

        serverLabel.setText("DeviantArt");

        firstDownloadedLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        firstDownloadedLabel.setText("First downloaded:");

        date1Label.setText("April 8, 2009");

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
        jScrollPane2.setViewportView(artistsTable);
        artistsTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (artistsTable.getColumnModel().getColumnCount() > 0) {
            artistsTable.getColumnModel().getColumn(0).setResizable(false);
        }

        javax.swing.GroupLayout showPanelLayout = new javax.swing.GroupLayout(showPanel);
        showPanel.setLayout(showPanelLayout);
        showPanelLayout.setHorizontalGroup(
            showPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        showPanelLayout.setVerticalGroup(
            showPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        lastUpdateLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        lastUpdateLabel.setText("Last update:");

        date2Label.setText("April 8, 2009");

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
                        .addGap(0, 12, Short.MAX_VALUE)
                        .addComponent(searchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(artistAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(serverLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
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
                    .addComponent(showPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(showingLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(artistsCombo, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(searchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addComponent(showPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private boolean isReady = false;

    private void searchTextCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_searchTextCaretUpdate
        final String text = searchText.getText();
        menu.removeAll();
        menu.setVisible(false);
        isReady = false;

        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String artistRowName = tableModel.getValueAt(i, 0).toString();

                    final int f = i;
                    if (artistRowName.toLowerCase().contains(text.toLowerCase())) {
                        JMenuItem open = new JMenuItem(new AbstractAction() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                try {
                                    selectArtist(f);
                                } catch (IOException ex) {
                                    JOptionPane.showMessageDialog(null, language.getContentById("internetDroppedOut"),
                                            language.getContentById("genericErrorTitle"), JOptionPane.OK_OPTION);
                                }
                            }
                        });

                        if (text.length() > 1) {
                            String painted = "<html>";
                            for (int j = 0; j < artistRowName.length(); j++) {
                                if (j + text.length() <= artistRowName.length() && artistRowName.toLowerCase()
                                        .substring(j, j + text.length()).equals(text.toLowerCase())) {
                                    painted += "<span style='color: red;'>" + artistRowName.substring(j, j + text.length())
                                            + "</span>";

                                    if (text.length() < 1) {
                                        j += text.length() + 1;
                                    } else {
                                        j += text.length() - 1;
                                    }
                                } else {
                                    painted += artistRowName.substring(j, j + 1);
                                }
                            }
                            painted += "</html>";
                            open.setText(painted);
                            menu.add(open);
                        }
                    }
                }
                isReady = true;
            }
        }.start();

        while (!isReady) {
            try {
                Thread.sleep(2);
            } catch (InterruptedException ex) {
                Logger.getLogger(ArtistsOptionsJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (menu.getComponentCount() > 0) {
            menu.setVisible(true);
            menu.popupScrollBar.setVisible(true);
            menu.show(searchText, searchText.getX() - 18, searchText.getY() + 32);
        } else {
            String show = "No artists found :(";

            if (text.length() < 2) {
                show = "The text must be at least 2 letters long";
            }

            if (show.length() < 75) {
                while (show.length() < 75) {
                    show += " ";
                }
            }

            JMenuItem open = new JMenuItem(show);
            menu.add(open);
            menu.setVisible(true);
            menu.popupScrollBar.setVisible(true);
            menu.show(searchText, searchText.getX() - 18, searchText.getY() + 32);
        }
    }//GEN-LAST:event_searchTextCaretUpdate

    private void searchTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTextMouseClicked
        searchText.setText("");
        artistsCombo.setSelectedItem(0);
        setTable(0);
        try {
            selectArtist(0);
        } catch (IOException ex) {
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

    private XmlManager loadServerManager(int server) throws IOException {
        XmlManager manager = new XmlManager();
        File artistsXml;
        switch (server) {
            case 0:
                artistsXml = new File(xml.getContentById("DAoutput") + System.getProperty("file.separator") + "artists-log.xml");
                if (!artistsXml.exists()) {
                    manager.createFile(artistsXml.getAbsolutePath());
                } else {
                    manager.loadFile(artistsXml);
                }
                return manager;
            case 1:
                artistsXml = new File(xml.getContentById("TUoutput") + System.getProperty("file.separator") + "artists-log.xml");
                if (!artistsXml.exists()) {
                    manager.createFile(artistsXml.getAbsolutePath());
                } else {
                    manager.loadFile(artistsXml);
                }
                return manager;
            case 2:
                artistsXml = new File(xml.getContentById("GHoutput") + System.getProperty("file.separator") + "artists-log.xml");
                if (!artistsXml.exists()) {
                    manager.createFile(artistsXml.getAbsolutePath());
                } else {
                    manager.loadFile(artistsXml);
                }
                return manager;
            case 3:
                artistsXml = new File(xml.getContentById("FAoutput") + System.getProperty("file.separator") + "artists-log.xml");
                if (!artistsXml.exists()) {
                    manager.createFile(artistsXml.getAbsolutePath());
                } else {
                    manager.loadFile(artistsXml);
                }
                return manager;
            case 4:
                artistsXml = new File(xml.getContentById("E621output") + System.getProperty("file.separator") + "artists-log.xml");
                if (!artistsXml.exists()) {
                    manager.createFile(artistsXml.getAbsolutePath());
                } else {
                    manager.loadFile(artistsXml);
                }
                return manager;
            default:
                return null;
        }
    }

    private void getAllArtists() {
        XmlManager artists;
        try {
            artists = loadServerManager(0);
            allArtists.put("DA", artists.getAllContentsByName("name").toArray());

            artists = loadServerManager(1);
            allArtists.put("TU", artists.getAllContentsByName("name").toArray());

            artists = loadServerManager(2);
            allArtists.put("GH", artists.getAllContentsByName("name").toArray());

            artists = loadServerManager(3);
            allArtists.put("FA", artists.getAllContentsByName("name").toArray());

            artists = loadServerManager(4);
            allArtists.put("E621", artists.getAllContentsByName("name").toArray());
        } catch (IOException ex) {
            Logger.getLogger(ArtistsOptionsJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setTable(int status) {
        try {
            XmlManager artists = new XmlManager();

            if (tableModel.getRowCount() > 0) {
                tableModel.setRowCount(0);
            }

            switch (status) {
                case 0:
                    getAllArtists();
                    String[] keys = new String[]{"DA", "TU", "GH", "FA", "E621"};
                    List<String> array = new ArrayList<>();

                    for (int i = 0; i < keys.length; i++) {
                        Object[] temp = allArtists.get(keys[i]);

                        if (temp != null) {
                            for (Object o : temp) {
                                array.add(o.toString() + (i + 1));
                            }
                        }
                    }

                    Object[] found = array.toArray();
                    Arrays.sort(found);
                    for (Object o : found) {
                        String name = o.toString();
                        allArtistsServers.add(Integer.parseInt(name.substring(name.length() - 1)));
                        tableModel.addRow(new Object[]{name.substring(0, name.length() - 1)});
                    }

                    selectArtist(0);
                    return;
                case 1:
                    artists = loadServerManager(0);
                    selectedServer = 1;
                    break;
                case 2:
                    artists = loadServerManager(1);
                    selectedServer = 2;
                    break;
                case 3:
                    artists = loadServerManager(2);
                    selectedServer = 3;
                    break;
                case 4:
                    artists = loadServerManager(3);
                    selectedServer = 4;
                    break;
                case 5:
                    artists = loadServerManager(4);
                    selectedServer = 5;
                    break;
            }

            if (artists.toString() != null) {
                List<String> names = artists.getAllContentsByName("name");

                if (!names.isEmpty()) {
                    Object[] obs = names.toArray();
                    Arrays.sort(obs);
                    for (Object o : obs) {
                        tableModel.addRow(new Object[]{o.toString()});
                    }
                    selectArtist(0);
                } else {
                    Object[] rowData = new Object[]{"No data found :("};
                    tableModel.addRow(rowData);
                }
            } else {
                Object[] rowData = new Object[]{"No data found :("};
                tableModel.addRow(rowData);
            }
        } catch (java.io.IOException ex) {
            JOptionPane.showMessageDialog(null, language.getContentById("internetDroppedOut"), language.getContentById("genericErrorTitle"), JOptionPane.OK_OPTION);
        }
    }

    private void selectArtist(int rowIndex) throws IOException {
        if (tableModel.getValueAt(rowIndex, 0).toString().equals("No data found :(")) {
            return;
        }
        artistNameLabel.setText(tableModel.getValueAt(rowIndex, 0).toString());
        artistsTable.setRowSelectionInterval(rowIndex, rowIndex);

        XmlManager manager = new XmlManager();
        int occ = 0;
        String[] titles = null;
        String[] messages = null;
        String artistUrl = null;

        switch (selectedServer) {
            case 0:
                switch (allArtistsServers.get(rowIndex)) {
                    case 1:
                        selectedServer = 1;
                        manager = loadServerManager(0);
                        serverLabel.setText("DeviantArt");
                        occ = manager.getTagIndex("name", tableModel.getValueAt(rowIndex, 0).toString());

                        artistUrl = "https:/" + manager.getContentByName("name", occ).toLowerCase() + ".deviantart.com/gallery/?catpath=";
                        titles = new String[]{"Update DA infos... Like rn.", " ", " ", " ", " "};
                        messages = new String[]{" ", " ", " ", " ", " "};
                        break;
                    case 2:
                        selectedServer = 2;
                        manager = loadServerManager(1);
                        serverLabel.setText("Tumblr");
                        occ = manager.getTagIndex("name", tableModel.getValueAt(rowIndex, 0).toString());

                        artistUrl = "https:/" + manager.getContentByName("name", occ).toLowerCase() + ".tumblr.com/archive/";
                        titles = new String[]{"Tumblr is a poor site. Therefore there's nothing to show here. (jk)", " ", " ", " ", " "};
                        messages = new String[]{" ", " ", " ", " ", " "};
                        break;
                    case 3:
                        selectedServer = 3;
                        manager = loadServerManager(2);
                        serverLabel.setText("Gallery Hentai");
                        occ = manager.getTagIndex("name", tableModel.getValueAt(rowIndex, 0).toString());
                        break;
                    case 4:
                        selectedServer = 4;
                        manager = loadServerManager(3);
                        serverLabel.setText("FurAffinity");
                        occ = manager.getTagIndex("name", tableModel.getValueAt(rowIndex, 0).toString());

                        artistUrl = "http://www.furaffinity.net/gallery/" + manager.getContentByName("name", occ) + "/";
                        titles = new String[]{"Page visits:", "Comments received:", "Comments given:", "Journals:", "Favourites:"};
                        messages = new String[]{manager.getContentByName("pageVisits", occ), manager.getContentByName("commentsReceived", occ),
                            manager.getContentByName("commentsGiven", occ), manager.getContentByName("journals", occ), manager.getContentByName("favourites", occ)};
                        break;
                    case 5:
                        selectedServer = 5;
                        manager = loadServerManager(4);
                        serverLabel.setText("e621");
                        occ = manager.getTagIndex("name", tableModel.getValueAt(rowIndex, 0).toString());

                        artistUrl = "https://e621.net/post/index/1/" + manager.getContentByName("name", occ) + "/";
                        titles = new String[]{"e621 is a poor site. Therefore there's nothing\n to show here. (jk)", " ", " ", " ", " "};
                        messages = new String[]{" ", " ", " ", " ", " "};
                        break;
                }
                break;
            case 1:
                manager = loadServerManager(0);
                serverLabel.setText("DeviantArt");
                occ = manager.getTagIndex("name", tableModel.getValueAt(rowIndex, 0).toString());

                artistUrl = "https:/" + manager.getContentByName("name", occ).toLowerCase() + ".deviantart.com/gallery/?catpath=";
                titles = new String[]{"Update DA infos... Like rn.", " ", " ", " ", " "};
                messages = new String[]{" ", " ", " ", " ", " "};
                break;
            case 2:
                manager = loadServerManager(1);
                serverLabel.setText("Tumblr");
                occ = manager.getTagIndex("name", tableModel.getValueAt(rowIndex, 0).toString());

                artistUrl = "https:/" + manager.getContentByName("name", occ).toLowerCase() + ".tumblr.com/archive/";
                titles = new String[]{"Tumblr is a poor site. Therefore there's nothing to show here. (jk)", " ", " ", " ", " "};
                messages = new String[]{" ", " ", " ", " ", " "};
                break;
            case 3:
                manager = loadServerManager(2);
                serverLabel.setText("Gallery Hentai");
                occ = manager.getTagIndex("name", tableModel.getValueAt(rowIndex, 0).toString());
                break;
            case 4:
                manager = loadServerManager(3);
                serverLabel.setText("FurAffinity");
                occ = manager.getTagIndex("name", tableModel.getValueAt(rowIndex, 0).toString());

                artistUrl = "http://www.furaffinity.net/gallery/" + manager.getContentByName("name", occ).toLowerCase() + "/";
                titles = new String[]{"Page visits:", "Comments received:", "Comments given:", "Journals:", "Favourites:"};
                messages = new String[]{manager.getContentByName("pageVisits", occ), manager.getContentByName("commentsReceived", occ),
                    manager.getContentByName("commentsGiven", occ), manager.getContentByName("journals", occ), manager.getContentByName("favourites", occ)};
                break;
            case 5:
                manager = loadServerManager(4);
                serverLabel.setText("e621");
                occ = manager.getTagIndex("name", tableModel.getValueAt(rowIndex, 0).toString());

                artistUrl = "https://e621.net/post/index/1/" + manager.getContentByName("name", occ) + "/";
                titles = new String[]{"e621 is a poor site. Therefore there's nothing to show here. (jk)", " ", " ", " ", " "};
                messages = new String[]{" ", " ", " ", " ", " "};
                break;
        }

        avatarUrl = manager.getContentByName("avatarUrl", occ);
        if (!avatarUrl.equals("null")) {
            URL url = new URL(avatarUrl);
            artistAvatar.setIcon(new ImageIcon(url));
        } else {
            artistAvatar.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/noAvatar.jpg")));
        }

        date1Label.setText(manager.getContentByName("firstDownloaded", occ));
        date2Label.setText(manager.getContentByName("lastUpdated", occ));

        showPanel.removeAll();
        showPanel.add(new ArtistInfoPanel(manager, manager.getContentByName("name", occ), occ, artistUrl,
                selectedServer - 1, titles, messages));
    }

    private JPopupMenu createArtistPopup(final int rowIndex) {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem open = new JMenuItem(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    selectArtist(rowIndex);
                } catch (IOException ex) {
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

    private class JScrollPopupMenu extends JPopupMenu {

        protected int maximumVisibleRows = 10;

        public JScrollPopupMenu() {
            this("");
        }

        @SuppressWarnings("")
        public JScrollPopupMenu(String label) {
            super(label);

            super.add(getScrollBar());
            addMouseWheelListener(new MouseWheelListener() {
                @Override
                public void mouseWheelMoved(MouseWheelEvent event) {
                    JScrollBar scrollBar = getScrollBar();
                    int amount = (event.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL)
                            ? event.getUnitsToScroll() * scrollBar.getUnitIncrement()
                            : (event.getWheelRotation() < 0 ? -1 : 1) * scrollBar.getBlockIncrement();

                    scrollBar.setValue(scrollBar.getValue() + amount);
                    event.consume();
                }
            });
        }

        private JScrollBar popupScrollBar;

        protected JScrollBar getScrollBar() {
            if (popupScrollBar == null) {
                popupScrollBar = new JScrollBar(JScrollBar.VERTICAL);
                popupScrollBar.addAdjustmentListener(new AdjustmentListener() {
                    @Override
                    public void adjustmentValueChanged(AdjustmentEvent e) {
                        doLayout();
                        repaint();
                    }
                });

                popupScrollBar.setVisible(true);
            }

            return popupScrollBar;
        }

        @Override
        public void paintChildren(Graphics g) {
            Insets insets = getInsets();
            g.clipRect(insets.left, insets.top, getWidth(), getHeight() - insets.top - insets.bottom);
            super.paintChildren(g);
        }

        @Override
        protected void addImpl(Component comp, Object constraints, int index) {
            super.addImpl(comp, constraints, index);

            if (maximumVisibleRows < getComponentCount() - 1) {
                getScrollBar().setVisible(true);
            }
        }

        @Override
        public void remove(int index) {
            // can't remove the scrollbar
            index++;

            super.remove(index);

            if (maximumVisibleRows >= getComponentCount() - 1) {
                getScrollBar().setVisible(true);
            }
        }

        @Override
        public void show(Component invoker, int x, int y) {
            JScrollBar scrollBar = getScrollBar();
            if (scrollBar.isVisible()) {
                int extent = 0;
                int max = 0;
                int i = 0;
                int unit = -1;
                int width = 0;
                for (Component comp : getComponents()) {
                    if (!(comp instanceof JScrollBar)) {
                        Dimension preferredSize = comp.getPreferredSize();
                        width = Math.max(width, preferredSize.width);
                        if (unit < 0) {
                            unit = preferredSize.height;
                        }
                        if (i++ < maximumVisibleRows) {
                            extent += preferredSize.height;
                        }
                        max += preferredSize.height;
                    }
                }

                Insets insets = getInsets();
                int widthMargin = insets.left + insets.right;
                int heightMargin = insets.top + insets.bottom;
                scrollBar.setUnitIncrement(unit);
                scrollBar.setBlockIncrement(extent);
                scrollBar.setValues(0, heightMargin + extent, 0, heightMargin + max);

                width += scrollBar.getPreferredSize().width + widthMargin;
                int height = heightMargin + extent;

                setPopupSize(new Dimension(width, height));
            }

            super.show(invoker, x, y);
        }
    }

    private class ArtistInfoPanel extends JPanel {

        private javax.swing.JLabel actionsLabel;
        private javax.swing.JButton deleteButton;
        private javax.swing.JSeparator jSeparator1;
        private javax.swing.JSeparator jSeparator2;
        private javax.swing.JLabel mainLabel;
        private javax.swing.JButton openFolderButton;
        private javax.swing.JLabel show1;
        private javax.swing.JLabel show2;
        private javax.swing.JLabel show3;
        private javax.swing.JLabel show4;
        private javax.swing.JLabel show5;
        private javax.swing.JLabel text1;
        private javax.swing.JLabel text2;
        private javax.swing.JLabel text3;
        private javax.swing.JLabel text4;
        private javax.swing.JLabel text5;
        private javax.swing.JButton updateButton;
        //-----------------------------------------

        public ArtistInfoPanel(final XmlManager manager, final String artistName, final int artistIndex,
                final String artistUrl, final int server, String[] titles, String[] messages) {
            if (titles.length < 5 || messages.length < 5) {
                throw new IndexOutOfBoundsException();
            }

            initComponents();
            text1.setText(titles[0]);
            text2.setText(titles[1]);
            text3.setText(titles[2]);
            text4.setText(titles[3]);
            text5.setText(titles[4]);
            show1.setText(messages[0]);
            show2.setText(messages[1]);
            show3.setText(messages[2]);
            show4.setText(messages[3]);
            show5.setText(messages[4]);

            deleteButton.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete [name] from the DB?",
                            "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                        System.out.println(artistIndex);
                        manager.deleteTagByName("artist", artistIndex);
                        System.out.println(manager.toString());
                        setTable(0);
                    }
                }
            });

            updateButton.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    StylizedMainJFrame.ADD_TASK.addTask(artistUrl, server, -1);
                }
            });

            openFolderButton.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    String path = null;
                    switch (server) {
                        case 0:
                            path = xml.getContentById("DAoutput") + System.getProperty("file.separator");
                            break;
                        case 1:
                            path = xml.getContentById("TUoutput") + System.getProperty("file.separator");
                            break;
                        case 2:
                            path = xml.getContentById("GHoutput") + System.getProperty("file.separator");
                            break;
                        case 3:
                            path = xml.getContentById("FAoutput") + System.getProperty("file.separator");
                            break;
                        case 4:
                            path = xml.getContentById("E621output") + System.getProperty("file.separator");
                            break;
                    }

                    try {
                        Desktop.getDesktop().open(new File(path + artistName));
                    } catch (IOException ex) {
                        Logger.getLogger(ArtistsOptionsJFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }

        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">
        private void initComponents() {
            mainLabel = new javax.swing.JLabel();
            jSeparator1 = new javax.swing.JSeparator();
            text1 = new javax.swing.JLabel();
            text2 = new javax.swing.JLabel();
            text3 = new javax.swing.JLabel();
            text5 = new javax.swing.JLabel();
            text4 = new javax.swing.JLabel();
            actionsLabel = new javax.swing.JLabel();
            jSeparator2 = new javax.swing.JSeparator();
            updateButton = new javax.swing.JButton();
            deleteButton = new javax.swing.JButton();
            openFolderButton = new javax.swing.JButton();
            show1 = new javax.swing.JLabel();
            show2 = new javax.swing.JLabel();
            show3 = new javax.swing.JLabel();
            show4 = new javax.swing.JLabel();
            show5 = new javax.swing.JLabel();

            mainLabel.setFont(new java.awt.Font("Eras Bold ITC", 0, 18)); // NOI18N
            mainLabel.setText("Possible-to-find info");

            text1.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
            text1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
            text1.setText("text1");

            text2.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
            text2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
            text2.setText("text2");

            text3.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
            text3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
            text3.setText("text3");

            text5.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
            text5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
            text5.setText("text5");

            text4.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
            text4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
            text4.setText("text4");

            actionsLabel.setFont(new java.awt.Font("Eras Bold ITC", 0, 18)); // NOI18N
            actionsLabel.setText("Actions");

            updateButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/updateTask.png"))); // NOI18N
            updateButton.setText("Update");
            updateButton.setFocusable(false);

            deleteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/delete.png"))); // NOI18N
            deleteButton.setText("Delete");
            deleteButton.setFocusable(false);

            openFolderButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/folder.png"))); // NOI18N
            openFolderButton.setText("Open Folder");
            openFolderButton.setFocusable(false);

            show1.setText("jLabel1");
            show2.setText("jLabel1");
            show3.setText("jLabel1");
            show4.setText("jLabel1");
            show5.setText("jLabel1");

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
            this.setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(mainLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                            .addGap(120, 120, 120))
                    .addGroup(layout.createSequentialGroup()
                            .addGap(20, 20, 20)
                            .addComponent(jSeparator1)
                            .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addGap(10, 10, 10)
                                            .addComponent(jSeparator2))
                                    .addComponent(actionsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE))
                            .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                            .addGap(0, 0, Short.MAX_VALUE)
                                            .addComponent(openFolderButton)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(updateButton)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(deleteButton))
                                    .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(text1, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                                                    .addComponent(text2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(text3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(text4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(text5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(show1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(show2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(show3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(show4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(show5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addContainerGap())
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(mainLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(text1)
                                    .addComponent(show1))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(text2)
                                    .addComponent(show2))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(text3)
                                    .addComponent(show3))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(text4)
                                    .addComponent(show4))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(text5)
                                    .addComponent(show5))
                            .addGap(27, 27, 27)
                            .addComponent(actionsLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(updateButton)
                                    .addComponent(deleteButton)
                                    .addComponent(openFolderButton))
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
        }// </editor-fold>

    }
}

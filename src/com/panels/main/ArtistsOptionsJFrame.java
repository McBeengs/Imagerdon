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
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ArtistsOptionsJFrame extends javax.swing.JFrame {

    private final XmlManager xml;
    private final XmlManager language;
    private XmlManager artists;
    private DefaultTableModel tableModel;
    private Map<String, Object[]> allArtists;
    private List<Integer> allArtistsServers;
    private int selectedServer = 0;
    private int currentRowIndex;
    private String avatarUrl;

    public ArtistsOptionsJFrame() {
        xml = UsefulMethods.loadManager(UsefulMethods.OPTIONS);
        language = UsefulMethods.loadManager(UsefulMethods.LANGUAGE);
        allArtists = new HashMap<>();
        allArtistsServers = new ArrayList<>();

        initComponents();

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
                    selectArtist(rowindex);
                    System.out.println(avatarUrl);
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
        jPanel1 = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        lastUpdateLabel = new javax.swing.JLabel();
        date2Label = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        searchPanel.setBackground(new java.awt.Color(255, 255, 255));
        searchPanel.setBorder(new RoundedCornerBorder());

        searchText.setText("Search an artist...");
        searchText.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 0));

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void artistsComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_artistsComboActionPerformed
        setTable(selectedServer);
    }//GEN-LAST:event_artistsComboActionPerformed

    private void getAllArtists() {
        File artistsXml;
        artists = new XmlManager();

        try {
            artistsXml = new File(xml.getContentById("DAoutput") + System.getProperty("file.separator") + "artists-log.xml");
            if (artistsXml.exists()) {
                artists.loadFile(artistsXml);
                allArtists.put("DA", artists.getAllContentsByName("name").toArray());
            }

            artistsXml = new File(xml.getContentById("TUoutput") + System.getProperty("file.separator") + "artists-log.xml");
            if (artistsXml.exists()) {
                artists.loadFile(artistsXml);
                allArtists.put("TU", artists.getAllContentsByName("name").toArray());
            }

            artistsXml = new File(xml.getContentById("GHoutput") + System.getProperty("file.separator") + "artists-log.xml");
            if (artistsXml.exists()) {
                artists.loadFile(artistsXml);
                allArtists.put("GH", artists.getAllContentsByName("name").toArray());
            }

            artistsXml = new File(xml.getContentById("FAoutput") + System.getProperty("file.separator") + "artists-log.xml");
            if (artistsXml.exists()) {
                artists.loadFile(artistsXml);
                allArtists.put("FA", artists.getAllContentsByName("name").toArray());
            }

            artistsXml = new File(xml.getContentById("E621output") + System.getProperty("file.separator") + "artists-log.xml");
            if (artistsXml.exists()) {
                artists.loadFile(artistsXml);
                allArtists.put("E621", artists.getAllContentsByName("name").toArray());
            }
        } catch (IOException ex) {
            Logger.getLogger(ArtistsOptionsJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void getManagerForArtist(int rowIndex) {
        
    }

    private void setTable(int status) {
        File artistsXml;
        artists = new XmlManager();

        if (tableModel.getRowCount() > 0) {
            tableModel.setRowCount(0);
        }

        try {
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
                    artistsXml = new File(xml.getContentById("DAoutput") + System.getProperty("file.separator") + "artists-log.xml");
                    if (!artistsXml.exists()) {
                        artists.createFile(artistsXml.getAbsolutePath());
                    } else {
                        artists.loadFile(artistsXml);
                    }
                    selectedServer = 1;
                    break;
                case 2:
                    artistsXml = new File(xml.getContentById("TUoutput") + System.getProperty("file.separator") + "artists-log.xml");
                    if (!artistsXml.exists()) {
                        artists.createFile(artistsXml.getAbsolutePath());
                    } else {
                        artists.loadFile(artistsXml);
                    }
                    selectedServer = 2;
                    break;
                case 3:
                    artistsXml = new File(xml.getContentById("GHoutput") + System.getProperty("file.separator") + "artists-log.xml");
                    if (!artistsXml.exists()) {
                        artists.createFile(artistsXml.getAbsolutePath());
                    } else {
                        artists.loadFile(artistsXml);
                    }
                    selectedServer = 3;
                    break;
                case 4:
                    artistsXml = new File(xml.getContentById("FAoutput") + System.getProperty("file.separator") + "artists-log.xml");
                    if (!artistsXml.exists()) {
                        artists.createFile(artistsXml.getAbsolutePath());
                    } else {
                        artists.loadFile(artistsXml);
                    }
                    selectedServer = 4;
                    break;
                case 5:
                    artistsXml = new File(xml.getContentById("E621output") + System.getProperty("file.separator") + "artists-log.xml");
                    if (!artistsXml.exists()) {
                        artists.createFile(artistsXml.getAbsolutePath());
                    } else {
                        artists.loadFile(artistsXml);
                    }
                    selectedServer = 5;
                    break;
            }
        } catch (java.io.IOException ex) {
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
    }

    private void selectArtist(int rowIndex) {
        artistNameLabel.setText(tableModel.getValueAt(rowIndex, 0).toString());

        switch (selectedServer) {
            case 0:
                switch (allArtistsServers.get(rowIndex)) {
                    case 1:
                        serverLabel.setText("DeviantArt");
                        break;
                    case 2:
                        serverLabel.setText("Tumblr");
                        break;
                    case 3:
                        serverLabel.setText("Gallery Hentai");
                        break;
                    case 4:
                        serverLabel.setText("FurAffinity");
                        break;
                    case 5:
                        serverLabel.setText("e621");
                        break;
                }
                break;
            case 1:
                System.out.println(avatarUrl);
                serverLabel.setText("DeviantArt");
                break;
            case 2:
                serverLabel.setText("Tumblr");
                break;
            case 3:
                serverLabel.setText("Gallery Hentai");
                break;
            case 4:
                serverLabel.setText("FurAffinity");
                break;
            case 5:
                serverLabel.setText("e621");
                break;
        }
    }

    private JPopupMenu createArtistPopup(final int rowIndex) {
        JPopupMenu menu = new JPopupMenu();
        JMenuItem open = new JMenuItem(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectArtist(rowIndex);
                try {
                    System.out.println(avatarUrl);
                    BufferedImage img = ImageIO.read(new URL(avatarUrl));
                    artistAvatar.setIcon(new ImageIcon(img));
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, language.getContentById("internetDroppedOut"), language.getContentById("genericErrorTitle"), JOptionPane.OK_OPTION);
                }
            }
        });
        open.setText("Open artists details");

        JMenuItem update = new JMenuItem(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Update artist [" + tableModel.getValueAt(rowIndex, 0) + "]");
            }
        });
        update.setText("Update artist \"" + tableModel.getValueAt(rowIndex, 0) + "\"");

        menu.add(open);
        menu.add(update);

        menu.add(new JSeparator(JSeparator.HORIZONTAL));
        JMenuItem delete = new JMenuItem(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(getParent(), "Are you sure you want to delete \""
                        + tableModel.getValueAt(rowIndex, 0) + "\" ?", "Alert", JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    System.out.println("Delete artist [" + tableModel.getValueAt(rowIndex, 0) + "]");
                }
            }
        });
        delete.setText("Delete artist \"" + tableModel.getValueAt(rowIndex, 0) + "\"");
        menu.add(delete);

        return menu;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lastUpdateLabel;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JTextField searchText;
    private javax.swing.JLabel serverLabel;
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

}

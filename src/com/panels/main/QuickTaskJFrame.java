package com.panels.main;

import com.panels.main.MainJFrame.AddTask;
import com.util.xml.XmlManager;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

public class QuickTaskJFrame extends javax.swing.JFrame {

    private boolean isDownload = false;
    private int status = 0;
    private String url;
    private JLabel urlHint;
    private JLabel artistHint;
    private JTextField urlText;
    private JComboBox artistsCombo;
    private final GridBagConstraints c;
    private final XmlManager xml;
    private final XmlManager language;

    @SuppressWarnings("")
    public QuickTaskJFrame() {
        xml = new XmlManager();
        xml.loadFile("config/options.xml");
        String selected = xml.getContentByName("language", 0);
        selected = selected.substring(0, selected.indexOf(","));

        language = new XmlManager();
        language.loadFile("language/" + selected.toLowerCase() + ".xml");
        setTitle(language.getContentById("QTLabel"));

        initComponents();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        inputPanel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        serverGroup = new javax.swing.ButtonGroup();
        downloadUpload = new javax.swing.ButtonGroup();
        taskLabel = new javax.swing.JLabel();
        infoLabel = new javax.swing.JLabel();
        DAButton = new javax.swing.JToggleButton();
        tumblrButton = new javax.swing.JToggleButton();
        GHButton = new javax.swing.JToggleButton();
        FAButton = new javax.swing.JToggleButton();
        e621Button = new javax.swing.JToggleButton();
        downloadButton = new javax.swing.JToggleButton();
        updateButton = new javax.swing.JToggleButton();
        jSeparator2 = new javax.swing.JSeparator();
        inputPanel = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        okButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(610, 325));

        taskLabel.setFont(new java.awt.Font("Eras Bold ITC", 0, 24)); // NOI18N
        taskLabel.setText(language.getContentById("QTLabel"));

        infoLabel.setText(language.getContentById("infoText1"));

        serverGroup.add(DAButton);
        DAButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/deviantArtIconBig.png"))); // NOI18N
        DAButton.setText("DeviantArt");
        DAButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DAButtonActionPerformed(evt);
            }
        });

        serverGroup.add(tumblrButton);
        tumblrButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/tumblrIconBig.png"))); // NOI18N
        tumblrButton.setText("Tumblr");
        tumblrButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tumblrButtonActionPerformed(evt);
            }
        });

        serverGroup.add(GHButton);
        GHButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/galleryHentaiIconBig.png"))); // NOI18N
        GHButton.setText("Gallery Hentai");
        GHButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GHButtonActionPerformed(evt);
            }
        });

        serverGroup.add(FAButton);
        FAButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/FAIconBig.png"))); // NOI18N
        FAButton.setText("FurAffinity");
        FAButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FAButtonActionPerformed(evt);
            }
        });

        serverGroup.add(e621Button);
        e621Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/e621IconBig.png"))); // NOI18N
        e621Button.setText("e621");
        e621Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                e621ButtonActionPerformed(evt);
            }
        });

        downloadUpload.add(downloadButton);
        downloadButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/downloadTask.png"))); // NOI18N
        downloadButton.setText(language.getContentById("downloadButton"));
        downloadButton.setFocusPainted(false);
        downloadButton.setFocusable(false);
        downloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadButtonActionPerformed(evt);
            }
        });

        downloadUpload.add(updateButton);
        updateButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/updateTask.png"))); // NOI18N
        updateButton.setText(language.getContentById("updateButton"));
        updateButton.setFocusPainted(false);
        updateButton.setFocusable(false);
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        inputPanel.setMinimumSize(new java.awt.Dimension(371, 101));
        inputPanel.setPreferredSize(new java.awt.Dimension(371, 101));

        javax.swing.GroupLayout inputPanelLayout = new javax.swing.GroupLayout(inputPanel);
        inputPanel.setLayout(inputPanelLayout);
        inputPanelLayout.setHorizontalGroup(
            inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        inputPanelLayout.setVerticalGroup(
            inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 101, Short.MAX_VALUE)
        );

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        okButton.setText(language.getContentById("ok"));
        okButton.setEnabled(false);
        okButton.setFocusable(false);
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(GHButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tumblrButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(DAButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(FAButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(e621Button, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(taskLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(59, 59, 59)
                                .addComponent(downloadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(inputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(okButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(21, 21, 21))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(infoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(infoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(taskLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(downloadButton)
                            .addComponent(updateButton))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(okButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(DAButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tumblrButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(GHButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(FAButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(e621Button))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 18, Short.MAX_VALUE)))
                .addContainerGap())
        );

        DAButton.setFocusPainted(false);
        tumblrButton.setFocusPainted(false);
        GHButton.setFocusPainted(false);
        FAButton.setFocusPainted(false);
        e621Button.setFocusPainted(false);
        downloadButton.setVisible(false);
        updateButton.setVisible(false);
        jSeparator2.setVisible(false);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void DAButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DAButtonActionPerformed
        status = 1;
        setComponents();
    }//GEN-LAST:event_DAButtonActionPerformed

    private void tumblrButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tumblrButtonActionPerformed
        status = 2;
        setComponents();
    }//GEN-LAST:event_tumblrButtonActionPerformed

    private void GHButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GHButtonActionPerformed
        status = 3;
        setComponents();
    }//GEN-LAST:event_GHButtonActionPerformed

    private void FAButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FAButtonActionPerformed
        status = 4;
        setComponents();
    }//GEN-LAST:event_FAButtonActionPerformed

    private void e621ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_e621ButtonActionPerformed
        status = 5;
        setComponents();
    }//GEN-LAST:event_e621ButtonActionPerformed

    private void downloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadButtonActionPerformed
        isDownload = true;
        inputPanel.removeAll();
        inputPanel.add(new DownloadInput(), c);
        inputPanel.revalidate();
        inputPanel.repaint();
    }//GEN-LAST:event_downloadButtonActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        isDownload = false;
        inputPanel.removeAll();
        inputPanel.add(new UpdateInput(), c);
        inputPanel.revalidate();
        inputPanel.repaint();
    }//GEN-LAST:event_updateButtonActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        new Thread("Adding Quick Task") {
            @Override
            public void run() {
                if (isDownload) {
                    AddTask add = MainJFrame.ADD_TASK;
                    add.addTask(urlText.getText(), status - 1, DownloadTaskJPanel.DOWNLOAD_TASK);
                } else {
                    AddTask add = MainJFrame.ADD_TASK;
                    add.addTask(url, status - 1, DownloadTaskJPanel.UPDATE_TASK);
                }
            }
        }.start();

        this.dispose();
    }//GEN-LAST:event_okButtonActionPerformed

    private void setComponents() {
        infoLabel.setText(language.getContentById("infoText2"));
        downloadButton.setVisible(true);
        updateButton.setVisible(true);
        jSeparator2.setVisible(true);
        inputPanel.removeAll();
        inputPanel.repaint();
        okButton.setEnabled(false);

        urlHint = new JLabel("Url: ");
        urlText = new JTextField();

        artistHint = new JLabel(language.getContentById("selectArtist"));
        artistsCombo = new JComboBox();
        artistsCombo.setFocusable(false);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton DAButton;
    private javax.swing.JToggleButton FAButton;
    private javax.swing.JToggleButton GHButton;
    private javax.swing.JToggleButton downloadButton;
    private javax.swing.ButtonGroup downloadUpload;
    private javax.swing.JToggleButton e621Button;
    private javax.swing.JLabel infoLabel;
    private javax.swing.JPanel inputPanel;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JButton okButton;
    private javax.swing.ButtonGroup serverGroup;
    private javax.swing.JLabel taskLabel;
    private javax.swing.JToggleButton tumblrButton;
    private javax.swing.JToggleButton updateButton;
    // End of variables declaration//GEN-END:variables

    private class DownloadInput extends javax.swing.JPanel {

        public DownloadInput() {
            initComponents();
        }

        @SuppressWarnings("unchecked")
        private void initComponents() {
            urlText.addCaretListener(new CaretListener() {
                @Override
                public void caretUpdate(CaretEvent e) {
                    switch (status) {
                        case 1:
                            if (urlText.getText().endsWith("/gallery/?catpath=/")) {
                                okButton.setEnabled(true);
                            } else {
                                okButton.setEnabled(false);
                            }
                            break;
                        case 2:
                            break;
                        case 3:
                            if (urlText.getText().contains("http://g.e-hentai.org/g/")) {
                                okButton.setEnabled(true);
                            } else {
                                okButton.setEnabled(false);
                            }
                            break;
                        case 4:
                            if (urlText.getText().contains("http://www.furaffinity.net/gallery/")) {
                                okButton.setEnabled(true);
                            } else {
                                okButton.setEnabled(false);
                            }
                            break;
                        case 5:
                            if (urlText.getText().contains("https://e621.net/post/index/")) {
                                okButton.setEnabled(true);
                            } else {
                                okButton.setEnabled(false);
                            }
                            break;
                    }
                }
            });

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
            this.setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                            .addContainerGap(34, Short.MAX_VALUE)
                            .addComponent(urlHint)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(urlText, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(33, 33, 33))
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                            .addGap(26, 26, 26)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(urlText)
                                    .addComponent(urlHint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addContainerGap(55, Short.MAX_VALUE))
            );
        }
    }

    private class UpdateInput extends javax.swing.JPanel {

        public UpdateInput() {
            initComponents();
        }

        @SuppressWarnings("unchecked")
        private void initComponents() {
            XmlManager artists = new XmlManager();
            File artistsXml;

            try {
                switch (status) {
                    case 1:
                        artistsXml = new File(xml.getContentById("DAoutput") + "/artists-log.xml");
                        if (!artistsXml.exists()) {
                            artists.createFile(artistsXml.getAbsolutePath());
                        } else {
                            artists.loadFile(artistsXml);
                        }
                        break;
                    case 2:
                        artistsXml = new File(xml.getContentById("TUoutput") + "/artists-log.xml");
                        if (!artistsXml.exists()) {
                            artists.createFile(artistsXml.getAbsolutePath());
                        } else {
                            artists.loadFile(artistsXml);
                        }
                        break;
                    case 3:
                        artistsXml = new File(xml.getContentById("GHoutput") + "/artists-log.xml");
                        if (!artistsXml.exists()) {
                            artists.createFile(artistsXml.getAbsolutePath());
                        } else {
                            artists.loadFile(artistsXml);
                        }
                        break;
                    case 4:
                        artistsXml = new File(xml.getContentById("FAoutput") + "/artists-log.xml");
                        if (!artistsXml.exists()) {
                            artists.createFile(artistsXml.getAbsolutePath());
                        } else {
                            artists.loadFile(artistsXml);
                        }
                        break;
                    case 5:
                        artistsXml = new File(xml.getContentById("E621output") + "/artists-log.xml");
                        if (!artistsXml.exists()) {
                            artists.createFile(artistsXml.getAbsolutePath());
                        } else {
                            artists.loadFile(artistsXml);
                        }
                        break;
                }
            } catch (java.io.IOException ex) {
            }

            final ArrayList<String> list = artists.getAllContentsByName("name");
            int size = list.size();

            String[] comboFiller = new String[size + 1];
            String label = language.getContentById("selectArtist");
            label = label.substring(0, label.length() - 1);
            comboFiller[0] = label;
            final String lambda = label;

            for (int i = 1; i < size + 1; i++) {
                comboFiller[i] = list.get(i - 1);
            }

            artistsCombo.setModel(new javax.swing.DefaultComboBoxModel<>(comboFiller));

            artistsCombo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!artistsCombo.getSelectedItem().toString().equals(lambda)) {
                        okButton.setEnabled(true);
                        switch (status) {
                            case 1:
                                url = artistsCombo.getSelectedItem().toString().toLowerCase() + "deviantart.com/gallery/?catpath=/";
                                break;
                            case 2:
                                break;
                            case 3:
                                break;
                            case 4:
                                url = "http://www.furaffinity.net/gallery/" + artistsCombo.getSelectedItem().toString().toLowerCase() + "/";
                                break;
                            case 5:
                                url = "https://e621.net/post/index/" + artistsCombo.getSelectedItem().toString().toLowerCase() + "/";
                                break;
                        }
                    } else {
                        okButton.setEnabled(false);
                    }
                }
            });

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);

            this.setLayout(layout);

            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                            .addGap(59, 59, 59)
                            .addComponent(artistHint, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(artistsCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(82, Short.MAX_VALUE))
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                            .addGap(21, 21, 21)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(artistsCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(artistHint, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addContainerGap(60, Short.MAX_VALUE))
            );
        }
    }
}

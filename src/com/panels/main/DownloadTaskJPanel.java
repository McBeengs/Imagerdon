/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panels.main;

import com.core.web.download.BasicCore;
import com.core.web.download.DeviantArt;
import com.core.web.download.E621;
import com.core.web.download.FurAffinity;
import com.core.web.download.GalleryHentai;
import com.core.web.download.UpdateFurAffinity;
import com.core.web.download.UpdateGalleryHentai;
import com.panels.main.StylizedMainJFrame.RemoveTask;
import com.util.UsefulMethods;
import com.util.xml.XmlManager;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static java.lang.Thread.sleep;

public final class DownloadTaskJPanel extends javax.swing.JPanel {

    public static final int DOWNLOAD_TASK = -2;
    public static final int UPDATE_TASK = -1;
    public static final int DEVIANT_ART = 0;
    public static final int TUMBLR = 1;
    public static final int GALLERY_HENTAI = 2;
    public static final int FUR_AFFINITY = 3;
    public static final int E621 = 4;
    private boolean isExecuting = false;
    private boolean isTerminated = false;
    private boolean firstClick = false;
    private int numOfTask;
    private BasicCore extractor;
    private final XmlManager language;

    public DownloadTaskJPanel(String url, int numOfTask, int typeOfServer, int typeOfTask) {
        initComponents();
        this.numOfTask = numOfTask;

        if (typeOfTask == DOWNLOAD_TASK) {
            switch (typeOfServer) {
                case DEVIANT_ART:
                    extractor = new DeviantArt(url, this);
                    break;
                case TUMBLR:
                    break;
                case GALLERY_HENTAI:
                    extractor = new GalleryHentai(url, this);
                    break;
                case FUR_AFFINITY:
                    extractor = new FurAffinity(url, this);
                    break;
                case E621:
                    extractor = new E621(url, this);
                    break;
            }
        } else {
            switch (typeOfServer) {
                case DEVIANT_ART:
                    //extractor = new DeviantArt(url, this);
                    break;
                case TUMBLR:
                    break;
                case GALLERY_HENTAI:
                    extractor = new UpdateGalleryHentai(url, this);
                    break;
                case FUR_AFFINITY:
                    extractor = new UpdateFurAffinity(url, this);
                    break;
                case E621:
                    //extractor = new E621(url, this);
                    break;
            }
        }

        language = UsefulMethods.loadManager(UsefulMethods.LANGUAGE);

        if (typeOfTask == DOWNLOAD_TASK) {
            taskIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/downloadTask.png")));
            taskLabel.setText(language.getContentById("downloadTitle"));
        } else if (typeOfTask == UPDATE_TASK) {
            taskIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/updateTask.png")));
            taskLabel.setText(language.getContentById("updateTitle"));
        }

        initSubComponents(numOfTask);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        taskLabel = new javax.swing.JLabel();
        playButton = new javax.swing.JLabel();
        author = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        infoDisplay = new javax.swing.JLabel();
        taskIcon = new javax.swing.JLabel();
        stopButton = new javax.swing.JLabel();

        setLayout(null);

        taskLabel.setFont(new java.awt.Font("Eras Bold ITC", 0, 24)); // NOI18N
        taskLabel.setText("Download Gallery");
        add(taskLabel);
        taskLabel.setBounds(50, 0, 230, 40);

        playButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        playButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/playButtonStandard.png"))); // NOI18N
        add(playButton);
        playButton.setBounds(420, 30, 60, 70);
        add(author);
        author.setBounds(290, 0, 210, 40);
        add(progressBar);
        progressBar.setBounds(20, 70, 330, 20);

        infoDisplay.setText("Awaiting Start...");
        add(infoDisplay);
        infoDisplay.setBounds(20, 44, 230, 20);

        taskIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        taskIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/downloadTask.png"))); // NOI18N
        add(taskIcon);
        taskIcon.setBounds(10, 0, 32, 40);

        stopButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/stopButtonStandard.png"))); // NOI18N
        add(stopButton);
        stopButton.setBounds(370, 40, 50, 60);
    }// </editor-fold>//GEN-END:initComponents

    private void initSubComponents(int numOfTask) {
        this.setBounds(0, 0, 500, 100);

        Color bg;
        if (numOfTask % 2 == 0) {
            bg = new Color(205, 205, 205); // Darker
        } else {
            bg = new Color(240, 240, 240); //Lighter
        }

        this.setSize(500, 100);
        this.setBackground(bg);

        playButton.addMouseListener(playButtonNormalBehavior());
        stopButton.addMouseListener(stopButtonNormalBehavior());
        infoDisplay.setText(language.getContentById("connecting"));
        progressBar.setIndeterminate(true);
        playButton.setVisible(false);
        stopButton.setVisible(false);
        extractor.start();
    }

    public MouseListener playButtonNormalBehavior() {
        playButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/playButtonStandard.png")));

        return new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                if (!isTerminated) {
                    if (!isExecuting) {
                        playButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/playButtonPressed.png")));
                    } else {
                        playButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/pauseButtonPressed.png")));
                    }
                } else {
                    playButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/deleteButtonHover.png")));
                }
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                if (!isTerminated) {
                    if (!isExecuting) {
                        playButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/playButtonStandard.png")));
                    } else {
                        playButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/pauseButtonStandard.png")));
                    }
                } else {
                    playButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/deleteButtonStandard.png")));
                }
            }

            @Override
            public void mouseClicked(MouseEvent evt) {
                if (!firstClick) {
                    stopButton.setVisible(true);
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    playButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/pauseButtonStandard.png")));
                    isExecuting = true;
                    firstClick = true;
                } else if (isExecuting) {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                    playButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/playButtonStandard.png")));
                    extractor.pause();
                    isExecuting = false;
                } else if (!isExecuting) {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                    playButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/pauseButtonStandard.png")));
                    infoDisplay.setText(language.getContentById("connecting"));
                    extractor.play();
                    isExecuting = true;
                }
            }
        };
    }

    public MouseListener stopButtonNormalBehavior() {
        return new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                stopButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/stopButtonHover.png")));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                stopButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/stopButtonStandard.png")));
            }

            @Override
            public void mouseClicked(MouseEvent evt) {
                int result = JOptionPane.showConfirmDialog(null, language.getContentById("cancelDownload"),
                        language.getContentById("genericWarningTitle"), 2, JOptionPane.WARNING_MESSAGE);

                if (result == JOptionPane.YES_OPTION) {
                    isTerminated = true;

                    try {
                        extractor.terminate();
                        sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(DownloadTaskJPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    int missed = Integer.parseInt(infoDisplay.getText().replaceAll("[^0-9]", ""));
                    infoDisplay.setText(language.getContentById("downloadCancelled").replace("&num", "" + missed));
                    stopButton.setVisible(false);

                    for (MouseListener listener : playButton.getMouseListeners()) {
                        playButton.removeMouseListener(listener);
                    }
                    playButton.addMouseListener(playButtonErrorBehavior());
                }
            }
        };
    }

    public MouseListener playButtonErrorBehavior() {
        playButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/deleteButtonStandard.png")));

        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                RemoveTask remove = StylizedMainJFrame.REMOVE_TASK;
                remove.removeTask(numOfTask - 1);
                StylizedMainJFrame.GET_STACK.notifyStack();
            }

            @Override
            public void mouseEntered(MouseEvent evt) {
                playButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                playButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/deleteButtonHover.png")));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                playButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                playButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/deleteButtonStandard.png")));
            }
        };
    }

    public MouseListener playButtonDownloadFinishedBehavior() {
        playButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/finishedButtonStandard.png")));
        isTerminated = true;

        return new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                playButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/finishedButtonHover.png")));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                playButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/finishedButtonStandard.png")));
            }

            @Override
            public void mouseClicked(MouseEvent evt) {
                RemoveTask remove = StylizedMainJFrame.REMOVE_TASK;
                remove.removeTask(numOfTask - 1);
                StylizedMainJFrame.GET_STACK.notifyStack();
            }
        };
    }

    public void resetControls() {
        infoDisplay.setText("Preparing download...");
        progressBar.setIndeterminate(true);
        playButton.setVisible(false);
        stopButton.setVisible(false);
        repaint();
        revalidate();
    }

    public void setNewTaskNumber(int newTaskNum) {
        this.numOfTask = newTaskNum;
    }

    public void setNewTaskType(int typeOfTask) {
        infoDisplay.setText(language.getContentById("connecting"));
        playButton.setVisible(false);
        if (typeOfTask == DOWNLOAD_TASK) {
            taskIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/downloadTask.png")));
            taskLabel.setText(language.getContentById("downloadTitle"));
        } else if (typeOfTask == UPDATE_TASK) {
            taskIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/updateTask.png")));
            taskLabel.setText(language.getContentById("updateTitle"));
        }
    }

    public void setNewExtractor(BasicCore extractor) {
        this.extractor = extractor;
        if (!this.extractor.isAlive()) {
            this.extractor.start();
        }
    }

    public int getTaskNumber() {
        return numOfTask;
    }

    public boolean isTerminated() {
        return isTerminated;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel author;
    public javax.swing.JLabel infoDisplay;
    public javax.swing.JLabel playButton;
    public javax.swing.JProgressBar progressBar;
    public javax.swing.JLabel stopButton;
    private javax.swing.JLabel taskIcon;
    private javax.swing.JLabel taskLabel;
    // End of variables declaration//GEN-END:variables
}

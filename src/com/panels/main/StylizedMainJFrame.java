/* **********   StylizedMainJFrame.java   **********
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

import aurelienribon.slidinglayout.SLAnimator;
import aurelienribon.slidinglayout.SLConfig;
import aurelienribon.slidinglayout.SLKeyframe;
import aurelienribon.slidinglayout.SLPanel;
import aurelienribon.slidinglayout.SLSide;
import com.core.web.explorer.panes.MainPane;
import com.core.web.explorer.panes.WebViewPage;
import com.panels.options.OptionsJFrame;
import com.panels.tools.FADownloadFavs;
import com.util.UsefulMethods;
import com.util.xml.XmlManager;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JPopupMenu.Separator;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import net.java.balloontip.BalloonTip;
import net.java.balloontip.styles.MinimalBalloonStyle;
import net.java.balloontip.utils.FadingUtils;
import net.java.balloontip.utils.TimingUtils;
import org.xml.sax.SAXException;

public class StylizedMainJFrame extends javax.swing.JFrame {

    //Swing variables
    private SLPanel mainPanel;
    private SLPanel tasksPanel;
    private SLPanel scrollPane;
    private SLPanel blankCanvas;
    private SLPanel overtaskNotifier;
    private SLPanel overtaskNotifierPanel;
    private SLConfig showCfg;
    private SLConfig hideCfg;
    private SLConfig notificationCfg;
    private JPanel mainButtonsPanel;
    private JPanel searchPane;
    private JLabel hideTasksButton;
    private JLabel searchButton;
    private JButton quickTaskButton;
    private JButton artistsTasksButton;
    private JMenuBar menuBar;
    private JMenu fileOption;
    private JMenu toolsOption;
    private JMenu optionsOption;
    private JMenuItem downloadFAFavs;
    private JMenu furAffinityTools;
    private JMenuItem settingsOptions;
    private JSeparator jSeparator3;
    private JTextField searchText;
    private JScrollPane tasksCanvas;
    //General variables
    private boolean isTasksPanelHidden = false;
    private int sizeToScroll;
    private int numOfThreads = 0;
    private XmlManager language;
    private ArrayList<Object> stack;
    public static RemoveTask REMOVE_TASK;
    public static AddTask ADD_TASK;
    public static TasksStack GET_STACK;

    public StylizedMainJFrame() {
        ADD_TASK = new AddTask();
        REMOVE_TASK = new RemoveTask();
        GET_STACK = new TasksStack();

        language = UsefulMethods.loadManager(UsefulMethods.LANGUAGE);
        stack = new ArrayList<>();

        initComponents();
    }

    private void initComponents() {
        mainPanel = new SLPanel();
        tasksCanvas = new JScrollPane();
        scrollPane = new SLPanel();
        tasksPanel = new SLPanel();
        blankCanvas = new SLPanel();
        overtaskNotifierPanel = new SLPanel();
        hideTasksButton = new JLabel();
        quickTaskButton = new JButton();
        artistsTasksButton = new JButton();
        mainButtonsPanel = new JPanel();
        searchPane = new JPanel();
        jSeparator3 = new JSeparator();
        searchButton = new JLabel();
        searchText = new JTextField();
        menuBar = new JMenuBar();
        fileOption = new JMenu();
        toolsOption = new JMenu();
        furAffinityTools = new JMenu();
        downloadFAFavs = new JMenuItem();
        optionsOption = new JMenu();
        settingsOptions = new JMenuItem();

        this.setTitle(language.getContentById("appName"));
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setMinimumSize(new Dimension(500, 590));
        setPreferredSize(new Dimension(1142, 530));

        mainPanel.setTweenManager(SLAnimator.createTweenManager());
        SLAnimator.start();

        tasksCanvas.setBorder(null);
        tasksCanvas.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        tasksCanvas.setHorizontalScrollBar(null);

        scrollPane.setBackground(new java.awt.Color(225, 225, 225));
        scrollPane.setOpaque(true);
        scrollPane.setMinimumSize(new java.awt.Dimension(510, 105));
        scrollPane.setPreferredSize(new java.awt.Dimension(510, 120));
        javax.swing.GroupLayout scrollPaneLayout = new javax.swing.GroupLayout(scrollPane);
        scrollPane.setLayout(scrollPaneLayout);
        scrollPaneLayout.setHorizontalGroup(
                scrollPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 510, Short.MAX_VALUE)
        );
        scrollPaneLayout.setVerticalGroup(
                scrollPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 509, Short.MAX_VALUE)
        );

        tasksCanvas.setViewportView(scrollPane);
        javax.swing.GroupLayout tasksPanelLayout = new javax.swing.GroupLayout(tasksPanel);
        tasksPanel.setLayout(tasksPanelLayout);
        tasksPanelLayout.setHorizontalGroup(
                tasksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(tasksPanelLayout.createSequentialGroup()
                        .addComponent(tasksCanvas, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 20, Short.MAX_VALUE))
        );
        tasksPanelLayout.setVerticalGroup(
                tasksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(tasksCanvas, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
        );

        blankCanvas.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.gridx = 1;
        gc.gridy = 1;
        gc.gridheight = 1;
        gc.gridwidth = 1;
        gc.weightx = 50;
        gc.weighty = 100;
        gc.insets = new Insets(12, 25, 10, 10);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.NORTH;

        blankCanvas.add(mainButtonsPanel, gc);

        gc.insets = new Insets(60, 25, 10, 10);
        gc.fill = GridBagConstraints.BOTH;
        gc.anchor = GridBagConstraints.CENTER;
        blankCanvas.add(new ClosableTabbedPane(), gc);

        showCfg = new SLConfig(mainPanel)
                .row(1f).col(530).col(1f)
                .beginGrid(0, 0)
                .row(1f).col(1f)
                .place(0, 0, tasksPanel)
                .endGrid()
                .beginGrid(0, 1)
                .row(1f).col(1f)
                .place(0, 0, blankCanvas)
                .endGrid();

        hideCfg = new SLConfig(mainPanel)
                .row(1f).col(1f)
                .beginGrid(0, 0)
                .row(1f).col(1f)
                .place(0, 0, blankCanvas)
                .endGrid();

        overtaskNotifier = new OvertaskNotifierJPanel("", "");
        overtaskNotifierPanel.setLayout(new GridBagLayout());
        overtaskNotifierPanel.add(overtaskNotifier);

        notificationCfg = new SLConfig(mainPanel)
                .row(1f).col(530).col(1f)
                .beginGrid(0, 0)
                .row(1f).row(60).col(1f)
                .place(0, 0, tasksPanel)
                .place(1, 0, overtaskNotifierPanel)
                .endGrid()
                .beginGrid(0, 1)
                .row(1f).col(1f)
                .place(0, 0, blankCanvas)
                .endGrid();

        mainPanel.initialize(showCfg);
        add(mainPanel);

        fileOption.setText(language.getContentById("file"));
        menuBar.add(fileOption);

        toolsOption.setText(language.getContentById("tools"));
        //Add DeviantArt tools here
        toolsOption.add(new Separator());
        //Add Tumblr tools here
        toolsOption.add(new Separator());
        //Add GalleryHentai tools here
        toolsOption.add(new Separator());
        furAffinityTools.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/FAIconSmall.png")));
        furAffinityTools.setText(language.getContentByName("mainLabel", 5));
        downloadFAFavs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/FAIconSmall.png")));
        downloadFAFavs.setText(language.getContentById("downloadFavs"));
        furAffinityTools.add(downloadFAFavs);
        toolsOption.add(furAffinityTools);
        toolsOption.add(new Separator());
        //Add E621 tools here
        menuBar.add(toolsOption);

        optionsOption.setText(language.getContentById("options"));
        settingsOptions.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/settings.png"))); // NOI18N
        settingsOptions.setText(language.getContentById("settings"));
        optionsOption.add(settingsOptions);
        menuBar.add(optionsOption);

        setJMenuBar(menuBar);

        hideTasksButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/notVisible.png"))); // NOI18N

        quickTaskButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/lightning.png"))); // NOI18N
        quickTaskButton.setText(language.getContentById("addTaskButton"));

        artistsTasksButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/list.png"))); // NOI18N
        artistsTasksButton.setText(language.getContentById("artistsTasks"));

        searchPane.setBackground(new java.awt.Color(255, 255, 255));
        searchPane.setBorder(new RoundedCornerBorder());

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        searchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/search.png"))); // NOI18N
        searchButton.setToolTipText("");

        searchText.setText(language.getContentById("searchPlaceholder"));
        searchText.setBorder(null);
        searchText.setFocusable(false);
        searchText.setOpaque(false);

        javax.swing.GroupLayout searchPaneLayout = new javax.swing.GroupLayout(searchPane);
        searchPane.setLayout(searchPaneLayout);
        searchPaneLayout.setHorizontalGroup(
                searchPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, searchPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(searchText, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchButton)
                        .addGap(5, 5, 5))
        );
        searchPaneLayout.setVerticalGroup(
                searchPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jSeparator3)
                .addComponent(searchButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(searchText)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(mainButtonsPanel);
        mainButtonsPanel.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(hideTasksButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(quickTaskButton, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(artistsTasksButton, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(quickTaskButton)
                        .addComponent(artistsTasksButton))
                .addComponent(hideTasksButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(searchPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        quickTaskButton.setFocusPainted(false);
        artistsTasksButton.setFocusPainted(false);
        addListeners();

        pack();
    }

    private void addListeners() {
        hideTasksButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adjustHiddenObjects();
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                if (isTasksPanelHidden) {
                    hideTasksButton.setToolTipText(language.getContentById("hidingTip"));
                } else {
                    hideTasksButton.setToolTipText(language.getContentById("showingTip"));
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            }
        });
        //end of hideTasksButton

        quickTaskButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new Thread() {
                    @Override
                    public void run() {
                        new QuickTaskJFrame().setVisible(true);
                    }
                }.start();
            }
        });
        //end of quickTaskButton

        searchButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PopupMenu popup = new PopupMenu();

                String text = language.getContentById("searchOnServers").replace("&string", searchText.getText());
                popup.addItem(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/notVisible.png")), text, new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent evt) {
                        System.out.println("yes");
                    }
                });

                int maxSize = popup.getComponent().getGraphics().getFontMetrics().stringWidth(text);
                popup.show(searchPane, searchPane.getWidth() - maxSize - 70, searchPane.getHeight() - 1);
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        //end of searchButton

        searchText.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchText.setText("");
            }
        });
        //end of searchText

        downloadFAFavs.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FADownloadFavs favs = new FADownloadFavs();
                favs.setVisible(true);
            }
        });
        //end of downloadFAFavs

        settingsOptions.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new Thread("Opening OptionsJFrame") {
                    @Override
                    public void run() {
                        try {
                            new OptionsJFrame().setVisible(true);
                        } catch (SAXException | IOException ex) {
                            Logger.getLogger(StylizedMainJFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }.start();
            }
        });
        //end of settingsOptions

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (numOfThreads > 0 && checkIfTerminated() == false) {
                    String[] get = language.getContentById("confirmExit").split("&br");
                    int result = JOptionPane.showConfirmDialog(null, get[0] + "\n" + get[1], language.getContentById("exit"), JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);

                    if (result == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                } else {
                    System.exit(0);
                }
            }
        });
    }

    private boolean checkIfTerminated() {
        for (int i = 0; i < numOfThreads; i++) {
            DownloadTaskJPanel temp = (DownloadTaskJPanel) scrollPane.getComponent(i);
            if (!temp.isTerminated()) {
                return false;
            }
        }

        return true;
    }

    private void adjustTasks() {
        sizeToScroll += 105;
        scrollPane.setPreferredSize(new Dimension(510, sizeToScroll + 5));

        for (int i = 0; i < numOfThreads; i++) {
            DownloadTaskJPanel task = (DownloadTaskJPanel) scrollPane.getComponent(i);
            task.setLocation(5, (105 * i) + 5);
            task.setNewTaskNumber(i + 1);
        }
    }

    private void adjustHiddenObjects() {
        if (!isTasksPanelHidden) {
            mainPanel.createTransition().push(new SLKeyframe(hideCfg, 1f).setEndSideForOldCmps(SLSide.LEFT)).play();
            hideTasksButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/visible.png")));
            isTasksPanelHidden = true;
        } else {
            mainPanel.createTransition().push(new SLKeyframe(showCfg, 1f).setStartSideForNewCmps(SLSide.LEFT)).play();
            hideTasksButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/notVisible.png")));
            isTasksPanelHidden = false;
        }
    }

    private void makeBalloon(final JComponent component, final String text, final Color color) {
        new Thread("Showing ballon \"" + text + "\"") {
            @Override
            public void run() {
                BalloonTip balloonTip = new BalloonTip(component, new JLabel(text), new MinimalBalloonStyle(color, 10),
                        BalloonTip.Orientation.LEFT_BELOW, BalloonTip.AttachLocation.SOUTH, 25, 10, false);

                FadingUtils.fadeInBalloon(balloonTip, null, 200, 24);
                try {
                    java.lang.Thread.sleep(4000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(StylizedMainJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                FadingUtils.fadeOutBalloon(balloonTip, null, 200, 24);
                TimingUtils.showTimedBalloon(balloonTip, 200);
            }
        }.start();
    }

    public static void main(String args[]) {
        XmlManager style = UsefulMethods.loadManager(UsefulMethods.OPTIONS);
        String set = style.getContentByName("style", 0);
        set = set.substring(0, set.indexOf(","));

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if (set.equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StylizedMainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StylizedMainJFrame().setVisible(true);
            }
        });
    }

    public class AddTask {

        public void addTask(String url, int server, int type) {
            int c = 0;
            if (scrollPane.getComponentCount() > 0) {
                for (int i = 0; i < numOfThreads; i++) {
                    DownloadTaskJPanel task = (DownloadTaskJPanel) scrollPane.getComponent(i);
                    task.setNewTaskNumber(i + 1);

                    if (!task.isTerminated()) {
                        c++;
                    }
                    if (c == 10) { // number of simult tasks
                        c = -1;
                        break;
                    }
                }
            }

            if (c >= 0) {
                numOfThreads++;
                scrollPane.add(new DownloadTaskJPanel(url, numOfThreads, server, type));
                adjustTasks();

                if (isTasksPanelHidden) {
                    makeBalloon(hideTasksButton, language.getContentById("newTaskBalloon"), new Color(0, 0, 200));
                }
            } else {
                Object[] store = new Object[]{url, server, type};
                stack.add(Arrays.asList(store));

                if (!isTasksPanelHidden) {
                    String artist = url.substring(35, url.lastIndexOf("/"));
                    artist = artist.substring(0, 1).toUpperCase() + artist.substring(1);

                    if (artist.length() > 15) {
                        artist = artist.substring(0, 12) + "...";
                    }

                    overtaskNotifier = new OvertaskNotifierJPanel("Added task \"" + artist + "\" to waiting stack",
                            (stack.size() - 1) + " others are also awaiting");
                    overtaskNotifier.setOpaque(true);
                    overtaskNotifierPanel.removeAll();
                    overtaskNotifierPanel.add(overtaskNotifier);

                    mainPanel.createTransition().push(new SLKeyframe(notificationCfg, 0.3f)
                            .setStartSideForNewCmps(SLSide.BOTTOM).setCallback(new SLKeyframe.Callback() {
                        @Override
                        public void done() {
                            new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        sleep(5000);
                                        mainPanel.createTransition().push(new SLKeyframe(showCfg, 0.3f).setEndSideForOldCmps(SLSide.BOTTOM)).play();
                                    } catch (InterruptedException ex) {
                                        Logger.getLogger(StylizedMainJFrame.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }.start();
                        }
                    })).play();
                }
            }
        }
    }

    public class RemoveTask {

        public void removeTask(int numOfTask) {
            if (scrollPane.getComponentCount() > numOfTask) {
                scrollPane.remove(numOfTask);
                numOfThreads--;
                sizeToScroll -= 105;
                scrollPane.setPreferredSize(new Dimension(510, sizeToScroll + 5));

                if (numOfThreads > 0) {
                    for (int i = 0; i < numOfThreads; i++) {
                        DownloadTaskJPanel temp = (DownloadTaskJPanel) scrollPane.getComponent(i);
                        Color bg;
                        if (i % 2 == 0) {
                            bg = new Color(240, 240, 240); //Lighter
                        } else {
                            bg = new Color(205, 205, 205); // Darker
                        }

                        temp.setLocation(5, (105 * i) + 5);
                        temp.setBackground(bg);
                        temp.setNewTaskNumber(i + 1);
                    }
                }

                scrollPane.repaint();
            }
        }
    }

    public class TasksStack {

        public void notifyStack() {
            if (stack.size() > 0) {
                String array = (String) stack.get(0).toString();
                int comma;

                comma = array.indexOf(",");
                String url = array.substring(1, comma);
                int server = Integer.parseInt(array.substring(comma + 2, array.indexOf(",", comma + 2)));
                comma = array.indexOf(",", comma + 1) + 2;
                int type = Integer.parseInt(array.substring(comma, array.indexOf("]")));

                ADD_TASK.addTask(url, server, type);
            }
        }
    }

    private class ClosableTabbedPane extends JTabbedPane {

        private int tabCount = 0;
        private TabCloseUI closeUI = new TabCloseUI(this);

        public ClosableTabbedPane() {
            super.addTab("Home", new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/homeTab.png")), new MainPane());
            super.addTab("", new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/addTab.png")), new JLabel("aaa"));
            super.setFocusable(false);
            super.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        }

        public boolean tabAboutToClose(int tabIndex) {
            return !(tabIndex == 0 || tabIndex == this.getTabCount());
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            this.closeUI.paint(g);
        }

        @Override
        public void addTab(String title, Icon icon, Component component) {
            tabCount = this.getTabCount() - 1;
            super.insertTab(title, icon, component, null, tabCount);
            this.setSelectedIndex(tabCount);
        }

        @Override
        public void removeTabAt(int index) {
            super.removeTabAt(index);
            tabCount--;
            super.setSelectedIndex(tabCount);
        }

        @Override
        public void setSelectedIndex(int index) {
            if (index == this.getTabCount() - 1 && index != 0) {
                boolean isJavaFxAvailable;

                try {
                    ClassLoader.getSystemClassLoader().loadClass("javafx.embed.swing.JFXPanel");
                    isJavaFxAvailable = true;
                } catch (ClassNotFoundException e) {
                    isJavaFxAvailable = false;
                }

                if (isJavaFxAvailable) {
                    WebViewPage page = new WebViewPage("http://furaffinity.net/");
                    this.addTab(language.getContentById("newTab") + "     ", new javax.swing.ImageIcon(getClass().getResource("/com/style/icons/FAIconBig.png")), page);
                } else {
                    String[] texts = new String[]{language.getContentById("missingJavaFX").replace("&br", "<br>"), language.getContentById("thisNormal")};

                    UsefulMethods.makeHyperlinkOptionPane(texts, "https://java.com/en/download/",
                            1, JOptionPane.ERROR_MESSAGE, language.getContentById("genericErrorTitle"));
                }
            } else {
                super.setSelectedIndex(index);
            }
        }

        private class TabCloseUI implements MouseListener, MouseMotionListener {

            private ClosableTabbedPane tabbedPane;
            private int closeX = 0;
            private int closeY = 0;
            private int meX = 0;
            private int meY = 0;
            private int selectedTab;
            private final Rectangle rectangle = new Rectangle(0, 0, 8, 8);

            private TabCloseUI() {
            }

            public TabCloseUI(ClosableTabbedPane pane) {
                this.tabbedPane = pane;
                this.tabbedPane.addMouseMotionListener(this);
                this.tabbedPane.addMouseListener(this);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mouseDragged(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (closeUnderMouse(me.getX(), me.getY())) {
                    boolean isToCloseTab = ClosableTabbedPane.this.tabAboutToClose(this.selectedTab);
                    if ((isToCloseTab) && (this.selectedTab > -1)) {
                        this.tabbedPane.removeTabAt(this.selectedTab);
                    }
                    this.selectedTab = this.tabbedPane.getSelectedIndex();
                }
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseMoved(MouseEvent me) {
                this.meX = me.getX();
                this.meY = me.getY();
                if (mouseOverTab(this.meX, this.meY)) {
                    controlCursor();
                    this.tabbedPane.repaint();
                }
            }

            private void controlCursor() {
                if (this.tabbedPane.getTabCount() > 0) {
                    if (closeUnderMouse(this.meX, this.meY)) {
                        this.tabbedPane.setCursor(new Cursor(12));
                        if (this.selectedTab > -1) {
                            String tabName = this.tabbedPane.getTitleAt(this.selectedTab);
                            this.tabbedPane.setToolTipTextAt(this.selectedTab, language.getContentById("close") + " "
                                    + tabName.substring(0, tabName.length() - 5));
                        }
                    } else {
                        this.tabbedPane.setCursor(new Cursor(0));
                        if (this.selectedTab > -1) {
                            this.tabbedPane.setToolTipTextAt(this.selectedTab, "");
                        }
                    }
                }
            }

            private boolean closeUnderMouse(int x, int y) {
                this.rectangle.x = this.closeX;
                this.rectangle.y = this.closeY;
                return this.rectangle.contains(x, y);
            }

            public void paint(Graphics g) {
                int tabCount = this.tabbedPane.getTabCount() - 1;
                for (int j = 1; j < tabCount; j++) {
                    int x = this.tabbedPane.getBoundsAt(j).x + this.tabbedPane.getBoundsAt(j).width - 8 - 5;
                    int y = this.tabbedPane.getBoundsAt(j).y + 5;
                    drawClose(g, x, y);
                }
                if (mouseOverTab(this.meX, this.meY)) {
                    drawClose(g, this.closeX, this.closeY);
                }
            }

            private void drawClose(Graphics g, int x, int y) {
                if ((this.tabbedPane != null) && (this.tabbedPane.getTabCount() > 0)) {
                    Graphics2D g2 = (Graphics2D) g;
                    drawColored(g2, isUnderMouse(x, y) ? Color.RED : Color.WHITE, x, y);
                }
            }

            private void drawColored(Graphics2D g2, Color color, int x, int y) {
                g2.setStroke(new BasicStroke(5.0F, 1, 1));
                g2.setColor(Color.BLACK);
                g2.drawLine(x, y, x + 8, y + 8);
                g2.drawLine(x + 8, y, x, y + 8);
                g2.setColor(color);
                g2.setStroke(new BasicStroke(3.0F, 1, 1));
                g2.drawLine(x, y, x + 8, y + 8);
                g2.drawLine(x + 8, y, x, y + 8);
            }

            private boolean isUnderMouse(int x, int y) {
                return (Math.abs(x - this.meX) < 8) && (Math.abs(y - this.meY) < 8);
            }

            private boolean mouseOverTab(int x, int y) {
                int tabCount = this.tabbedPane.getTabCount() - 1;
                for (int j = 1; j < tabCount; j++) {
                    if (this.tabbedPane.getBoundsAt(j).contains(this.meX, this.meY)) {
                        this.selectedTab = j;
                        this.closeX = (this.tabbedPane.getBoundsAt(j).x + this.tabbedPane.getBoundsAt(j).width - 8 - 5);
                        this.closeY = (this.tabbedPane.getBoundsAt(j).y + 5);
                        return true;
                    }
                }
                return false;
            }
        }
    }

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

    private class PopupMenu extends JPopupMenu {

        @SuppressWarnings("")
        public PopupMenu() {
            setVisible(true);
        }

        public void addItem(ImageIcon icon, String text, MouseListener evt) {
            JMenuItem item = new JMenuItem(text);
            item.setIcon(icon);
            item.addMouseListener(evt);
            add(item);
        }
    }
}

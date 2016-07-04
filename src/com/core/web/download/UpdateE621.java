/* **********   UpdateFurAffinity.java   **********
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
package com.core.web.download;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.panels.main.DownloadTaskJPanel;
import com.util.UsefulMethods;
import com.util.xml.XmlManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class UpdateE621 extends BasicCore {

    private boolean isPaused = false;
    private boolean isTerminated = false;
    private boolean isDownloading = false;
    private int numOfImages;
    private int originalNumOfImages;
    private int numOfPages = 0;
    private String finalPath;
    private String link;
    private String artist;
    private final Connection conn;
    private WebClient webClient;
    private ExecutorService executor;
    private final XmlManager xml;
    private final XmlManager language;
    private final DownloadTaskJPanel taskManager;

    public UpdateE621(String url, DownloadTaskJPanel taskManager) {
        link = url;
        this.taskManager = taskManager;

        this.taskManager.playButton.setVisible(false);
        this.taskManager.progressBar.setIndeterminate(true);

        xml = UsefulMethods.loadManager(UsefulMethods.OPTIONS);
        language = UsefulMethods.loadManager(UsefulMethods.LANGUAGE);
        conn = UsefulMethods.getDBInstance();
    }

    private boolean getInformationAboutGallery() {
        try {
            taskManager.infoDisplay.setText(language.getContentById("getImages"));
            HtmlPage page = webClient.getPage(link);
            Document getNumberImages = Jsoup.parse(page.asXml());
            Elements testURL = getNumberImages.select("body");

            if (testURL.toString().contains("No posts matched your search.") == true) {
                JOptionPane.showMessageDialog(null, language.getContentById("errorUrlNonExistent"), language.getContentById("genericErrorTitle"), 0);
                return false;
            } else {
                Elements getNumber = getNumberImages.select("a[href~=/post/index/]");
                if (getNumber.isEmpty()) {
                    page = webClient.getPage(link);
                    getNumberImages = Jsoup.parse(page.asXml());
                    getNumber = getNumberImages.select("a[href~=/post/index/]");
                }
                String tempMax = getNumber.get(getNumber.size() - 2).toString();
                int last = tempMax.indexOf("/", 21);
                String end = tempMax.substring(21, last);
                numOfPages = Integer.parseInt(end);

                String fix = link.substring(0, 28);
                int end2 = link.indexOf("/", 28) + 1;

                for (int c = 1; c <= numOfPages; c++) {
                    HtmlPage currentPage = webClient.getPage(fix + c + "/" + link.substring(end2));
                    Document doc = Jsoup.parse(currentPage.asXml());
                    Elements get = doc.select("img.preview");
                    numOfImages += get.size();
                }
            }

        } catch (java.net.UnknownHostException ex) {
            JOptionPane.showMessageDialog(null, language.getContentById("internetDroppedOut"), language.getContentById("genericErrorTitle"), JOptionPane.OK_OPTION);
            return false;
        } catch (IOException | FailingHttpStatusCodeException ex) {
            Logger.getLogger(UpdateE621.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

    private void download() throws Exception {
        if (link.endsWith("/")) {
            link = link.substring(0, link.length() - 1);
        }
        artist = link.substring(link.lastIndexOf("/") + 1);
        artist = artist.substring(0, 1).toUpperCase() + artist.substring(1);

        taskManager.author.setText(artist + " | e621");
        taskManager.infoDisplay.setText(language.getContentById("loginIn"));
        while (!UsefulMethods.isWebClientReady()) {
            sleep(2);
        }

        webClient = UsefulMethods.getWebClientInstance();
        if (getInformationAboutGallery()) {
            taskManager.infoDisplay.setText(language.getContentById("wentOK"));

            java.awt.event.MouseListener[] teste = this.taskManager.playButton.getMouseListeners();
            for (MouseListener teste1 : teste) {
                taskManager.playButton.removeMouseListener(teste1);
            }

            taskManager.playButton.addMouseListener(taskManager.playButtonNormalBehavior());
            taskManager.playButton.setVisible(true);
            taskManager.progressBar.setIndeterminate(false);

            try {
                PreparedStatement statement = conn.prepareStatement("UPDATE artist SET last_updated = ? WHERE name = ? AND server = ?");
                statement.setDate(1, new Date(new java.util.Date().getTime()));
                statement.setString(2, artist);
                statement.setInt(3, 3);
                statement.execute();
            } catch (SQLException ex) {
                Logger.getLogger(UpdateE621.class.getName()).log(Level.SEVERE, null, ex);
            }

            taskManager.playButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    downloadAction();
                }
            });
        } else {
            taskManager.progressBar.setIndeterminate(false);
            taskManager.infoDisplay.setText(language.getContentById("taskError"));
            taskManager.stopButton.setVisible(true);
            taskManager.playButton.setVisible(true);

            for (MouseListener listener : taskManager.stopButton.getMouseListeners()) {
                taskManager.stopButton.removeMouseListener(listener);
            }
            taskManager.stopButton.addMouseListener(taskManager.stopButtonErrorBehavior());
            taskManager.playButton.addMouseListener(taskManager.playButtonErrorBehavior());
        }
    }

    private void downloadAction() {
        if (!isDownloading) {
            isDownloading = true;
            new Thread() {
                @Override
                public void run() {
                    try {
                        int count = 1;
                        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM artist WHERE name = '" + artist + "' AND server = 3");
                        rs.next();
                        int onDisk = rs.getInt("image_count");

                        if (onDisk >= numOfImages) {
                            JOptionPane.showMessageDialog(null, language.getContentById("artistUp2Date").replaceAll("&string", artist));
                            taskManager.progressBar.setValue(taskManager.progressBar.getMaximum());
                            taskManager.progressBar.setStringPainted(true);
                            taskManager.progressBar.setString("100%");
                            taskManager.infoDisplay.setText(language.getContentById("downloadFinished"));
                            taskManager.stopButton.setVisible(false);
                            taskManager.playButton.setVisible(true);
                            taskManager.playButton.removeMouseListener(taskManager.playButton.getMouseListeners()[0]);
                            taskManager.playButton.addMouseListener(taskManager.playButtonDownloadFinishedBehavior());
                        } else {
                            int cut = numOfImages - ((numOfPages - 1) * 75);
                            originalNumOfImages = numOfImages;
                            numOfImages -= onDisk;
                            finalPath = xml.getContentById("E621output") + File.separator + artist
                                    + File.separator;
                            String temp;

                            taskManager.progressBar.setIndeterminate(false);
                            taskManager.progressBar.setMinimum(0);
                            taskManager.progressBar.setMaximum(numOfImages);
                            taskManager.progressBar.setStringPainted(true);
                            taskManager.progressBar.setString("0%");
                            taskManager.infoDisplay.setText(language.getContentById("downloading").replace("&num", "" + numOfImages));

                            executor = Executors.newFixedThreadPool(Integer.parseInt(xml.getContentById("simult")));
                            executor.awaitTermination(0L, TimeUnit.SECONDS);

                            for (int c = 1; c <= numOfPages; c++) {
                                if (isTerminated) {
                                    break;
                                }

                                HtmlPage conn = webClient.getPage(link.substring(0, 28) + c + "/" + link.substring(link.indexOf("/", 28) + 1));
                                Document docLinks = Jsoup.parse(conn.asXml());
                                Elements getLinks = docLinks.select("a[href~=/post/show/]");

                                for (int i = 0; i < 75; i++) {
                                    while (isPaused) {
                                        sleep(2);
                                    }

                                    if (c == numOfPages) {
                                        if (i == cut) {
                                            break;
                                        }
                                    }

                                    if (getLinks.size() != i) {
                                        temp = getLinks.get(i).toString();
                                    } else {
                                        break;
                                    }

                                    if (count < (originalNumOfImages - onDisk)) {
                                        int end = temp.indexOf("\"", 35);
                                        String placeholder = temp.substring(31, end);
                                        temp = "https://e621.net" + placeholder;
                                        conn = webClient.getPage(temp);
                                        Document docImages = Jsoup.parse(conn.asXml());
                                        Elements getPages = docImages.select("a[href~=https://static1.e621.net/]");
                                        String tempImagesArray = getPages.toString();
                                        end = tempImagesArray.indexOf("\"", 11);
                                        try {
                                            temp = tempImagesArray.substring(9, end);
                                        } catch (java.lang.StringIndexOutOfBoundsException ex) {
                                            conn = webClient.getPage(temp);
                                            docImages = Jsoup.parse(conn.asXml());
                                            getPages = docImages.select("a[href~=https://static1.e621.net/]");
                                            tempImagesArray = getPages.toString();
                                            end = tempImagesArray.indexOf("\"", 11);
                                            temp = tempImagesArray.substring(9, end);
                                        }
                                        executor.execute(new ImageExtractor(3, artist, temp, numOfImages, count, taskManager, finalPath));
                                        numOfImages--;
                                    }
                                    count++;
                                }
                            }
                        }
                    } catch (IOException | StringIndexOutOfBoundsException ex) {
                    } catch (InterruptedException | SQLException ex) {
                        Logger.getLogger(UpdateE621.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException ex) {
                        JOptionPane.showMessageDialog(null, language.getContentById("internetDroppedOut"), language.getContentById("genericErrorTitle"), JOptionPane.OK_OPTION);
                        terminate();
                        taskManager.progressBar.setIndeterminate(false);
                        taskManager.infoDisplay.setText(language.getContentById("taskError"));
                        taskManager.stopButton.setVisible(true);
                        taskManager.playButton.setVisible(true);

                        for (MouseListener listener : taskManager.stopButton.getMouseListeners()) {
                            taskManager.stopButton.removeMouseListener(listener);
                        }
                        taskManager.stopButton.addMouseListener(taskManager.stopButtonErrorBehavior());
                        taskManager.playButton.addMouseListener(taskManager.playButtonErrorBehavior());
                    }
                }
            }.start();
        }
    }

    @Override
    public void pause() {
        taskManager.infoDisplay.setText(language.getContentById("pause"));
        isPaused = true;
    }

    @Override
    public void play() {
        isPaused = false;
    }

    @Override
    public void terminate() {
        isTerminated = true;

        if (executor != null) {
            executor.shutdownNow();
        }

        try {
            PreparedStatement statement = conn.prepareStatement("UPDATE artist SET image_count = ? WHERE name = ? AND server = ?");
            statement.setInt(1, originalNumOfImages - numOfImages);
            statement.setString(2, artist);
            statement.setInt(3, 3);
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UpdateE621.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            download();
        } catch (Exception ex) {
            Logger.getLogger(UpdateE621.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

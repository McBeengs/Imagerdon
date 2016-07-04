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
import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

public class UpdateDeviantArt extends BasicCore {

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

    public UpdateDeviantArt(String url, DownloadTaskJPanel taskManager) {
        link = url;
        this.taskManager = taskManager;

        this.taskManager.playButton.setVisible(false);
        this.taskManager.progressBar.setIndeterminate(true);

        xml = UsefulMethods.loadManager(UsefulMethods.OPTIONS);
        language = UsefulMethods.loadManager(UsefulMethods.LANGUAGE);
        conn = UsefulMethods.getDBInstance();
    }

    private boolean getInformationAboutGallery() throws IOException {
        try {
            taskManager.infoDisplay.setText(language.getContentById("getImages"));
            HtmlPage page = webClient.getPage(link + "0");

            Document getNumberImages = Jsoup.parse(page.asXml());
            Elements testURL = getNumberImages.select("body");

            if (testURL.toString().contains("The page you were looking for doesn't exist.")) {
                JOptionPane.showMessageDialog(null, language.getContentById("errorUrlNonExistent"), language.getContentById("genericErrorTitle"), 0);
                return false;
            } else {
                numOfImages = 0;
                while (true) {
                    if (!link.contains("%2F&offset=")) {
                        link += "%2F&offset=";
                    }

                    page = webClient.getPage(link + numOfImages);

                    if (page.asText().contains("This section has no deviations yet!")) {
                        break;
                    }

                    numOfImages += 24;
                    numOfPages++;
                }
                //by now "numOfImages" is holding the last avaliable page, but we need specify how many
                //images are on the last page

                numOfImages -= 24;

                page = webClient.getPage(link + numOfImages);
                getNumberImages = Jsoup.parse(page.asXml());
                Elements getNumber = getNumberImages.select("div[class~=tt-a tt-fh]");

                numOfImages += getNumber.size();
            }
        } catch (java.net.UnknownHostException ex) {
            JOptionPane.showMessageDialog(null, language.getContentById("internetDroppedOut"), language.getContentById("genericErrorTitle"), JOptionPane.OK_OPTION);
            return false;
        }

        return true;
    }

    private void download() throws Exception {
        artist = link.substring(7, link.indexOf("."));
        artist = artist.substring(0, 1).toUpperCase() + artist.substring(1);

        taskManager.author.setText(artist + " | DeviantArt");
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
                statement.setInt(3, 0);
                statement.execute();
            } catch (SQLException ex) {
                Logger.getLogger(UpdateDeviantArt.class.getName()).log(Level.SEVERE, null, ex);
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
                        int count = 0;
                        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM artist WHERE name = '" + artist + "' AND server = 0");
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
                            int cut = numOfImages - (numOfPages * 24);
                            int c = 0;
                            originalNumOfImages = numOfImages;
                            numOfImages -= onDisk;
                            finalPath = xml.getContentById("DAoutput") + File.separator + artist
                                    + File.separator;

                            taskManager.progressBar.setIndeterminate(false);
                            taskManager.progressBar.setMinimum(0);
                            taskManager.progressBar.setMaximum(numOfImages);
                            taskManager.progressBar.setStringPainted(true);
                            taskManager.progressBar.setString("0%");
                            taskManager.infoDisplay.setText(language.getContentById("downloading").replace("&num", "" + numOfImages));

                            executor = Executors.newFixedThreadPool(Integer.parseInt(xml.getContentById("simult")));
                            executor.awaitTermination(0L, TimeUnit.SECONDS);

                            while (c < originalNumOfImages) {
                                if (isTerminated) {
                                    break;
                                }

                                HtmlPage html = webClient.getPage(link + c);
                                Document docLinks = Jsoup.parse(html.asXml());
                                Elements getPages = docLinks.select("div span span span a[href~=" + artist.toLowerCase() + ".deviantart.com/art/]");

                                for (int i = 0; i < getPages.size(); i++) {
                                    while (isPaused) {
                                        sleep(2);
                                    }

                                    String temp = getPages.get(i).toString();
                                    temp = temp.substring(temp.indexOf("href"));
                                    temp = temp.substring(6, temp.indexOf("\"", 6));
                                    HtmlPage page = webClient.getPage(temp);

                                    Document docImages = Jsoup.parse(page.asXml());
                                    Elements getLinks = docImages.select("img[data-embed-format~=thumb]");
                                    if (!getLinks.isEmpty()) {
                                        if (count < (originalNumOfImages - onDisk)) {
                                            temp = getLinks.get(1).toString();
                                            temp = temp.substring(temp.indexOf("src") + 5);
                                            temp = temp.substring(0, temp.indexOf("\""));
                                            executor.execute(new ImageExtractor(0, artist, temp, numOfImages, count, taskManager, finalPath));
                                        } else if (increaseProgress()) {
                                            break;
                                        }
                                        count++;
                                    } else if (increaseProgress()) {
                                        break;
                                    }
                                    numOfImages--;
                                }
                                c += 24;
                            }
                        }
                    } catch (IOException | StringIndexOutOfBoundsException ex) {
                    } catch (InterruptedException | SQLException ex) {
                        Logger.getLogger(UpdateDeviantArt.class.getName()).log(Level.SEVERE, null, ex);
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

    private boolean increaseProgress() throws SQLException {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(1);
        nf.setGroupingUsed(true);

        String show = nf.format(taskManager.progressBar.getPercentComplete() * 100) + "%";
        taskManager.progressBar.setString(show);
        conn.createStatement().execute("UPDATE artist SET image_count = " + (originalNumOfImages - numOfImages) + " WHERE name = '" + artist + "' AND server = 0");

        if (show.equals("100%")) {
            taskManager.stopButton.setVisible(false);
            taskManager.infoDisplay.setText(language.getContentById("downloadFinished"));

            for (java.awt.event.MouseListener listener : taskManager.playButton.getMouseListeners()) {
                taskManager.playButton.removeMouseListener(listener);
            }
            taskManager.playButton.addMouseListener(taskManager.playButtonDownloadFinishedBehavior());
            return true;
        }
        taskManager.infoDisplay.setText(language.getContentById("downloading").replace("&num", "" + numOfImages));
        taskManager.progressBar.setValue(taskManager.progressBar.getValue() + 1);
        return false;
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
            statement.setInt(3, 0);
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UpdateDeviantArt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            download();
        } catch (Exception ex) {
            Logger.getLogger(UpdateDeviantArt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

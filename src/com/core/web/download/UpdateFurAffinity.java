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

public class UpdateFurAffinity extends BasicCore {

    private boolean isPaused = false;
    private boolean isTerminated = false;
    private boolean isDownloading = false;
    private int numOfImages;
    private int originalNumOfImages;
    private int numOfPages = 0;
    private String finalPath;
    private final String link;
    private String artist;
    private final Connection conn;
    private WebClient webClient;
    private ExecutorService executor;
    private final XmlManager xml;
    private final XmlManager language;
    private final DownloadTaskJPanel taskManager;

    public UpdateFurAffinity(String url, DownloadTaskJPanel taskManager) {
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
            HtmlPage page = webClient.getPage(link);

            Document getNumberImages = Jsoup.parse(page.asXml());
            Elements testURL = getNumberImages.select("body");

            if (testURL.toString().contains("could not be found.")) {
                JOptionPane.showMessageDialog(null, language.getContentById("errorUrlNonExistent"), language.getContentById("genericErrorTitle"), 0);
                return false;
            } else {
                int c = 1;
                while (1 > 0) {
                    page = webClient.getPage(link + c + "/");

                    if (page.asXml().contains("There are no submissions to list") == true) {
                        break;
                    }
                    c++;
                    numOfPages = c - 1;
                }

                for (int i = 1; i <= numOfPages; i++) {
                    String currentPage = (link + i + "/");
                    HtmlPage getLinks = webClient.getPage(currentPage);
                    Document doc = Jsoup.parse(getLinks.asXml());
                    Elements get = doc.select("a[href~=/view/]");
                    numOfImages += get.size();
                }
            }
        } catch (java.net.UnknownHostException | FailingHttpStatusCodeException ex) {
            JOptionPane.showMessageDialog(null, language.getContentById("internetDroppedOut"), language.getContentById("genericErrorTitle"), JOptionPane.OK_OPTION);
            return false;
        }

        return true;
    }

    private void download() throws Exception {
        artist = link.substring(35, link.lastIndexOf("/"));
        artist = artist.substring(0, 1).toUpperCase() + artist.substring(1);

        taskManager.author.setText(artist + " | FurAfinity");
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
                statement.setInt(3, 2);
                statement.execute();
            } catch (SQLException ex) {
                Logger.getLogger(UpdateFurAffinity.class.getName()).log(Level.SEVERE, null, ex);
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
                        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM artist WHERE name = '" + artist + "' AND server = 2");
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
                            int cut = numOfImages - ((numOfPages - 1) * 72);
                            originalNumOfImages = numOfImages;
                            numOfImages -= onDisk;
                            finalPath = xml.getContentById("FAoutput") + File.separator + artist
                                    + File.separator;

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

                                HtmlPage page = webClient.getPage(link + c + "/");
                                Document docLinks = Jsoup.parse(page.asXml());
                                Elements getPages = docLinks.select("a[href~=/view/]");

                                for (int i = 0; i < 72; i++) {
                                    while (isPaused) {
                                        sleep(2);
                                    }

                                    if (c == numOfPages) {
                                        if (i == cut) {
                                            break;
                                        }
                                    }

                                    if (count < (originalNumOfImages - onDisk)) {
                                        String temp = getPages.get(i).toString();
                                        int end = temp.indexOf("/", 15);
                                        temp = "http://www.furaffinity.net" + temp.substring(9, end);
                                        page = webClient.getPage(temp);
                                        Document docImages = Jsoup.parse(page.asXml());
                                        Elements getLinks = docImages.select("a[href~=//d.facdn.net/art/]");
                                        String tempImagesArray = getLinks.toString();
                                        end = tempImagesArray.indexOf("\"", 10);
                                        temp = "http://" + tempImagesArray.substring(11, end);

                                        if (isTerminated) {
                                            break;
                                        } else {
                                            executor.execute(new ImageExtractor(0, artist, temp, numOfImages, count, taskManager, finalPath));
                                        }
                                    } else {
                                        NumberFormat nf = NumberFormat.getNumberInstance();
                                        nf.setMaximumFractionDigits(1);
                                        nf.setGroupingUsed(true);

                                        String show = nf.format(taskManager.progressBar.getPercentComplete() * 100) + "%";
                                        taskManager.progressBar.setString(show);
                                        conn.createStatement().execute("UPDATE artist SET image_count = " + (originalNumOfImages - numOfImages) + " WHERE name = '" + artist + "' AND server = 2");

                                        if (show.equals("100%")) {
                                            taskManager.stopButton.setVisible(false);
                                            taskManager.infoDisplay.setText(language.getContentById("downloadFinished"));

                                            for (java.awt.event.MouseListener listener : taskManager.playButton.getMouseListeners()) {
                                                taskManager.playButton.removeMouseListener(listener);
                                            }
                                            taskManager.playButton.addMouseListener(taskManager.playButtonDownloadFinishedBehavior());
                                            break;
                                        }
                                        taskManager.infoDisplay.setText(language.getContentById("downloading").replace("&num", "" + numOfImages));
                                        taskManager.progressBar.setValue(taskManager.progressBar.getValue() + 1);
                                    }
                                    numOfImages--;
                                    count++;
                                }
                            }
                        }
                    } catch (IOException | StringIndexOutOfBoundsException ex) {
                    } catch (InterruptedException | SQLException ex) {
                        Logger.getLogger(UpdateFurAffinity.class.getName()).log(Level.SEVERE, null, ex);
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
            statement.setInt(3, 2);
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UpdateFurAffinity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            download();
        } catch (Exception ex) {
            Logger.getLogger(UpdateFurAffinity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

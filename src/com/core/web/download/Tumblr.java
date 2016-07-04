/* **********   Tumblr.java   **********
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
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.panels.main.DownloadTaskJPanel;
import com.util.UsefulMethods;
import com.util.xml.XmlManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Tumblr extends BasicCore {

    private boolean isPaused = false;
    private boolean isTerminated = false;
    private boolean isDownloading = false;
    private boolean convertedToUpdate = false;
    private int numOfImages;
    private int originalNumOfImages;
    private int numOfPages = 0;
    private String finalPath;
    private String link;
    private String artist;
    private String avatarUrl;
    private final Connection conn;
    private WebClient webClient;
    private ExecutorService executor;
    private final XmlManager xml;
    private final XmlManager language;
    private final DownloadTaskJPanel taskManager;

    public Tumblr(String url, DownloadTaskJPanel taskManager) {
        link = url;
        this.taskManager = taskManager;

        if (!link.endsWith("archive/") || !link.endsWith("archive")) {
            link = link.substring(0, link.indexOf("/", 9));
            link += "/archive/";
        }

        xml = UsefulMethods.loadManager(UsefulMethods.OPTIONS);
        language = UsefulMethods.loadManager(UsefulMethods.LANGUAGE);
        conn = UsefulMethods.getDBInstance();
    }

    private boolean getInformationAboutGallery() throws IOException {
        taskManager.infoDisplay.setText(language.getContentById("getImages"));
        HtmlPage page = webClient.getPage(link);

        Document getNumberImages = Jsoup.parse(page.asXml());
        Elements testURL = getNumberImages.select("body");

        if (testURL.toString().contains("There's nothing here.")) {
            JOptionPane.showMessageDialog(null, language.getContentById("errorUrlNonExistent"), language.getContentById("genericErrorTitle"), 0);
            return false;
        } else {
            avatarUrl = getNumberImages.select("img.avatar").get(0).attr("src");
            avatarUrl = avatarUrl.substring(0, avatarUrl.lastIndexOf(".") - 2) + "128.png";

            while (true) {
                getNumberImages = Jsoup.parse(page.asXml());
                Elements images = getNumberImages.getElementsByClass("hover"); // 50
                numOfImages += images.size();
                HtmlAnchor next = (HtmlAnchor) page.getElementById("next_page_link");
                if (next != null) {
                    page = next.click();
                } else {
                    break;
                }
            }

            numOfPages = numOfImages / 50;
        }

        return true;
    }

    private boolean checkArtistExistance() throws IOException {
        finalPath = xml.getContentById("TUoutput") + File.separator + artist;
        
        File getDir = new File(finalPath);
        if (!getDir.exists()) {
            getDir.mkdirs();
        }

        PreparedStatement prepared;
        boolean wasFound = false;
        try {
            prepared = conn.prepareStatement("SELECT * FROM artist WHERE name = ? AND server = ?");
            prepared.setString(1, artist);
            prepared.setInt(2, 1);
            wasFound = prepared.executeQuery().next();
        } catch (SQLException ex) {
            Logger.getLogger(FurAffinity.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (Integer.parseInt(xml.getContentById("existed")) == 1) {
            if (finalPath.contains(artist)) {
                File[] getImages = new File(finalPath).listFiles();
                for (File image : getImages) {
                    if (image.isFile()) {
                        image.delete();
                    }
                }
            }

            if (wasFound) {
                originalNumOfImages = numOfImages;
                try {
                    PreparedStatement statement = conn.prepareStatement("UPDATE artist SET last_updated = ?, image_count = ? WHERE name = ? AND server = ?");
                    statement.setDate(1, new Date(new java.util.Date().getTime()));
                    statement.setInt(2, numOfImages);
                    statement.setString(3, artist);
                    statement.setInt(4, 1);
                    statement.execute();
                } catch (SQLException ex) {
                    Logger.getLogger(FurAffinity.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                createArtistTag();
            }

            return true;
        }

        if (wasFound) {
            if (Integer.parseInt(xml.getContentById("existed")) == 0) {
                String[] texts = language.getContentById("skipEnabled").replace("&string", artist).split("&br");
                JOptionPane.showMessageDialog(null, texts[0] + "\n" + texts[1], language.getContentById("genericInfoTitle"), JOptionPane.INFORMATION_MESSAGE);
                return false;
            }

            if (!finalPath.contains(artist)) {
                originalNumOfImages = numOfImages;
                try {
                    PreparedStatement statement = conn.prepareStatement("UPDATE artist SET last_updated = ?, image_count = ? WHERE name = ? AND server = ?");
                    statement.setDate(1, new Date(new java.util.Date().getTime()));
                    statement.setInt(2, numOfImages);
                    statement.setString(3, artist);
                    statement.setInt(4, 1);
                    statement.execute();
                } catch (SQLException ex) {
                    Logger.getLogger(FurAffinity.class.getName()).log(Level.SEVERE, null, ex);
                }
                return true;
            }

            File getImages = new File(finalPath);
            int older = getImages.listFiles().length;

            if (older >= numOfImages) {
                JOptionPane.showMessageDialog(null, language.getContentById("artistUp2Date").replaceAll("&string", artist));
                return false;
            }

            String[] texts = language.getContentById("artistExists").replace("&string", artist)
                    .replace("&num", "" + (numOfImages - older)).split("&br");
            int result = JOptionPane.showConfirmDialog(null, texts[0] + "\n" + texts[1], language.getContentById("genericInfoTitle"),
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (result == JOptionPane.YES_OPTION) {
                taskManager.setNewTaskType(DownloadTaskJPanel.UPDATE_TASK);
                new Thread("Changed to Download to Update task") {
                    @Override
                    public void run() {
                        taskManager.setNewExtractor(new UpdateTumblr(link, taskManager));
                    }
                }.start();
                convertedToUpdate = true;
                return false;
            } else {
                originalNumOfImages = numOfImages;
                try {
                    PreparedStatement statement = conn.prepareStatement("UPDATE artist SET last_updated = ?, image_count = ? WHERE name = ? AND server = ?");
                    statement.setDate(1, new Date(new java.util.Date().getTime()));
                    statement.setInt(2, numOfImages);
                    statement.setString(3, artist);
                    statement.setInt(4, 1);
                    statement.execute();
                } catch (SQLException ex) {
                    Logger.getLogger(FurAffinity.class.getName()).log(Level.SEVERE, null, ex);
                }
                return true;
            }
        } else {
            createArtistTag();
            return true;
        }
    }

    private void createArtistTag() {
        try {
            originalNumOfImages = numOfImages;
            PreparedStatement prepared = conn.prepareStatement("INSERT INTO artist (`id`, `server`, `name`, `avatar_url`, `first_downloaded`, `last_updated`, `image_count`) VALUES (NULL, ?, ?, ?, ?, ?, ?);");
            prepared.setInt(1, 1);
            prepared.setString(2, artist);
            prepared.setString(3, avatarUrl);
            prepared.setDate(4, new Date(new java.util.Date().getTime()));
            prepared.setDate(5, new Date(new java.util.Date().getTime()));
            prepared.setInt(6, numOfImages);
            prepared.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DeviantArt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void download() throws Exception {
        artist = link.substring(7, link.indexOf("."));
        artist = artist.substring(0, 1).toUpperCase() + artist.substring(1);

        taskManager.author.setText(artist + " | Tumblr");
        taskManager.infoDisplay.setText(language.getContentById("loginIn"));
        while (!UsefulMethods.isWebClientReady()) {
            sleep(2);
        }

        webClient = UsefulMethods.getWebClientInstance();

        if (getInformationAboutGallery() && checkArtistExistance()) {
            taskManager.infoDisplay.setText(language.getContentById("wentOK"));
            taskManager.playButton.setVisible(true);
            taskManager.progressBar.setIndeterminate(false);
            taskManager.playButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    downloadAction();
                }
            });
        } else if (!convertedToUpdate) {
            taskManager.progressBar.setIndeterminate(false);
            taskManager.infoDisplay.setText(language.getContentById("taskError"));
            taskManager.playButton.setVisible(true);
            for (java.awt.event.MouseListener listener : taskManager.playButton.getMouseListeners()) {
                taskManager.playButton.removeMouseListener(listener);
            }
            taskManager.playButton.addMouseListener(taskManager.playButtonErrorBehavior());
        }
    }

    private void downloadAction() {
        if (!isDownloading) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        taskManager.progressBar.setIndeterminate(false);
                        taskManager.progressBar.setMinimum(0);
                        taskManager.progressBar.setMaximum(numOfImages);
                        taskManager.progressBar.setStringPainted(true);
                        taskManager.progressBar.setString("0%");
                        taskManager.infoDisplay.setText(language.getContentById("downloading").replace("&num", "" + numOfImages));
                        isDownloading = true;

                        int cut = numOfImages - (numOfPages * 50);
                        int count = 0;
                        HtmlPage page = webClient.getPage(link);
                        executor = Executors.newFixedThreadPool(Integer.parseInt(xml.getContentById("simult")));

                        for (int c = 0; c < numOfPages; c++) {
                            if (isTerminated) {
                                break;
                            }

                            Document getImages = Jsoup.parse(page.asXml());
                            Elements getPages = getImages.getElementsByClass("hover");

                            for (int i = 0; i < getPages.size(); i++) {
                                while (isPaused) {
                                    sleep(2);
                                }

                                if (c == numOfPages) {
                                    if (i == cut) {
                                        break;
                                    }
                                }

                                String temp = getPages.get(i).toString();
                                temp = temp.substring(temp.indexOf("href") + 6);
                                temp = temp.substring(0, temp.indexOf("\""));

                                HtmlPage post = webClient.getPage(temp);
                                Elements image = Jsoup.parse(post.asXml()).getElementsByTag("img");
                                boolean wasFound = false;

                                for (Element el : image) {
                                    if (!el.toString().contains("avatar") && !el.toString().contains("assets.tumblr")
                                            && !el.toString().contains("px.srvcs")) {
                                        temp = el.toString();
                                        temp = temp.substring(temp.indexOf("src") + 5);
                                        temp = temp.substring(0, temp.indexOf("\""));
                                        wasFound = true;

                                        executor.execute(new ImageExtractor(0, artist, temp, numOfImages, count, taskManager, finalPath));
                                        numOfImages--;
                                        break;
                                    }
                                }

                                if (!wasFound) {
                                    if (post.asXml().contains("class=\"photoset\"")) { //is a image
                                        DomElement photoset = (DomElement) post.getByXPath("//iframe[@class='photoset']").get(0);
                                        temp = photoset.asXml();
                                        temp = temp.substring(temp.indexOf("src") + 5);
                                        temp = temp.substring(0, temp.indexOf("\""));

                                        HtmlPage hidden = webClient.getPage(temp);
                                        image = Jsoup.parse(hidden.asXml()).getElementsByTag("img");

                                        for (Element el : image) {
                                            if (!el.toString().contains("avatar") && !el.toString().contains("assets.tumblr")
                                                    && !el.toString().contains("px.srvcs")) {
                                                temp = el.toString();
                                                temp = temp.substring(temp.indexOf("src") + 5);
                                                temp = temp.substring(0, temp.indexOf("\""));

                                                executor.execute(new ImageExtractor(1, artist, temp, numOfImages, count, taskManager, finalPath));
                                                numOfImages--;
                                            }
                                        }
                                    } else { //is everything else
                                        numOfImages--;
                                        taskManager.infoDisplay.setText(language.getContentById("downloading").replace("&num", "" + numOfImages));
                                        taskManager.progressBar.setValue(taskManager.progressBar.getValue() + 1);

                                        NumberFormat nf = NumberFormat.getNumberInstance();
                                        nf.setMaximumFractionDigits(1);
                                        nf.setGroupingUsed(true);

                                        String show = nf.format(taskManager.progressBar.getPercentComplete() * 100) + "%";
                                        taskManager.progressBar.setString(show);
                                        PreparedStatement statement = conn.prepareStatement("UPDATE artist SET image_count = ? WHERE name = ? AND server = ?");
                                        statement.setInt(1, numOfImages);
                                        statement.setString(2, artist);
                                        statement.setInt(3, 1);
                                        statement.execute();

                                        if (show.equals("100%")) {
                                            taskManager.stopButton.setVisible(false);
                                            taskManager.infoDisplay.setText(language.getContentById("downloadFinished"));

                                            for (java.awt.event.MouseListener listener : taskManager.playButton.getMouseListeners()) {
                                                taskManager.playButton.removeMouseListener(listener);
                                            }
                                            taskManager.playButton.addMouseListener(taskManager.playButtonDownloadFinishedBehavior());
                                        }
                                    }
                                }
                                count++;
                            }

                            HtmlAnchor next = (HtmlAnchor) page.getElementById("next_page_link");
                            page = next.click();
                        }
                    } catch (FailingHttpStatusCodeException | java.net.UnknownHostException ex) {
                        JOptionPane.showMessageDialog(null, language.getContentById("internetDroppedOut"), language.getContentById("genericErrorTitle"), JOptionPane.OK_OPTION);
                    } catch (IOException | InterruptedException ex) {
                        Logger.getLogger(FurAffinity.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(Tumblr.class.getName()).log(Level.SEVERE, null, ex);
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
            statement.setInt(3, 1);
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(Tumblr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            download();
        } catch (Exception ex) {
            Logger.getLogger(FurAffinity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

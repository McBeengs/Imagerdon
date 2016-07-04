/* **********   FurAffinity.java   **********
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
import java.sql.SQLException;

public class DeviantArt extends BasicCore {

    private int numOfImages;
    private int originalNumOfImages;
    private boolean isPaused = false;
    private boolean isTerminated = false;
    private boolean isDownloading = false;
    private boolean convertedToUpdate;
    private String finalPath;
    private String link;
    private String artist;
    private String avatarUrl;
    private final Connection conn;
    private WebClient webClient;
    private final XmlManager xml;
    private final XmlManager language;
    private ExecutorService executor;
    private final DownloadTaskJPanel taskManager;
    int helper = 0;

    public DeviantArt(String url, DownloadTaskJPanel taskManager) {
        link = url;
        this.taskManager = taskManager;

        if (link.endsWith("/")) {
            link = link.substring(0, link.length() - 1);
        }

        if (!link.contains("?catpath=")) {
            link += "?catpath=%2F&offset=";
        }

        if (!link.contains("?catpath=")) {
            link += "%2F&offset=";
        }

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
                avatarUrl = getNumberImages.select("img.avatar.float-left").get(0).attr("src");

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

    private boolean checkArtistExistance() throws IOException {
        finalPath = xml.getContentById("DAoutput") + File.separator + artist;

        File getDir = new File(finalPath);
        if (!getDir.exists()) {
            getDir.mkdirs();
        }

        PreparedStatement prepared;
        boolean wasFound = false;
        try {
            prepared = conn.prepareStatement("SELECT * FROM artist WHERE name = ? AND server = ?");
            prepared.setString(1, artist);
            prepared.setInt(2, 0);
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
                    statement.setInt(4, 0);
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
            try {
                if (Integer.parseInt(xml.getContentById("existed")) == 0) {
                    String[] texts = language.getContentById("skipEnabled").replace("&string", artist).split("&br");
                    JOptionPane.showMessageDialog(null, texts[0] + "\n" + texts[1], language.getContentById("genericInfoTitle"), JOptionPane.INFORMATION_MESSAGE);
                    return false;
                }

                if (!finalPath.contains(artist)) {
                    originalNumOfImages = numOfImages;
                    PreparedStatement statement = conn.prepareStatement("UPDATE artist SET last_updated = ?, image_count = ? WHERE name = ? AND server = ?");
                    statement.setDate(1, new Date(new java.util.Date().getTime()));
                    statement.setInt(2, numOfImages);
                    statement.setString(3, artist);
                    statement.setInt(4, 0);
                    statement.execute();
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
                            taskManager.setNewExtractor(new UpdateDeviantArt(link, taskManager));
                        }
                    }.start();
                    convertedToUpdate = true;
                    return false;
                } else {
                    originalNumOfImages = numOfImages;
                    PreparedStatement statement = conn.prepareStatement("UPDATE artist SET last_updated = ?, image_count = ? WHERE name = ? AND server = ?");
                    statement.setDate(1, new Date(new java.util.Date().getTime()));
                    statement.setInt(2, numOfImages);
                    statement.setString(3, artist);
                    statement.setInt(4, 0);
                    statement.execute();
                    return true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(DeviantArt.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            createArtistTag();
            return true;
        }

        return false;
    }

    private void createArtistTag() {
        try {
            originalNumOfImages = numOfImages;
            PreparedStatement prepared = conn.prepareStatement("INSERT INTO artist (`id`, `server`, `name`, `avatar_url`, `first_downloaded`, `last_updated`, `image_count`) VALUES (NULL, ?, ?, ?, ?, ?, ?);");
            prepared.setInt(1, 0);
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

        taskManager.author.setText(artist + " | DeviantArt");
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

                        int c = 0;
                        int count = 0;
                        executor = Executors.newFixedThreadPool(Integer.parseInt(xml.getContentById("simult")));

                        while (c < originalNumOfImages) {
                            if (isTerminated) {
                                break;
                            }

                            HtmlPage conn = webClient.getPage(link + c);
                            Document docLinks = Jsoup.parse(conn.asXml());
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
                                    temp = getLinks.get(1).toString();

                                    temp = temp.substring(temp.indexOf("src") + 5);
                                    temp = temp.substring(0, temp.indexOf("\""));
                                    count++;

                                    executor.execute(new ImageExtractor(0, artist, temp, numOfImages, count, taskManager, finalPath));
                                }
                                numOfImages--;
                            }
                            c += 24;
                        }
                    } catch (FailingHttpStatusCodeException | java.net.UnknownHostException ex) {
                        JOptionPane.showMessageDialog(null, language.getContentById("internetDroppedOut"), language.getContentById("genericErrorTitle"), JOptionPane.OK_OPTION);
                    } catch (IOException | InterruptedException ex) {
                        Logger.getLogger(FurAffinity.class.getName()).log(Level.SEVERE, null, ex);
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
            statement.setInt(3, 0);
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DeviantArt.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void run() {
        try {
            download();
        } catch (Exception ex) {
            Logger.getLogger(DeviantArt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

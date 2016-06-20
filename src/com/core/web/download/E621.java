/* **********   E621.java   **********
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

public class E621 extends BasicCore {

    private boolean isPaused = false;
    private boolean isTerminated = false;
    private boolean isDownloading = false;
    private int numOfImages;
    private int numOfPages = 0;
    private int originalNumOfImages;
    private String finalPath;
    private final String link;
    private String artist;
    private final Connection conn;
    private WebClient webClient;
    private final XmlManager xml;
    private final XmlManager language;
    private ExecutorService executor;
    private final DownloadTaskJPanel taskManager;

    public E621(String url, DownloadTaskJPanel taskManager) {
        link = url;
        this.taskManager = taskManager;

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
            Logger.getLogger(E621.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

    private boolean checkArtistExistance() throws IOException {
        finalPath = xml.getContentById("e621output") + System.getProperty("file.separator") + artist;

        PreparedStatement prepared;
        boolean wasFound = false;
        try {
            prepared = conn.prepareStatement("SELECT * FROM artist WHERE name = ? AND server = ?");
            prepared.setString(1, artist);
            prepared.setInt(2, 3);
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
                    statement.setInt(4, 3);
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
                    statement.setInt(4, 3);
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
                new UpdateE621(link, taskManager).start();
                return false;
            } else {
                originalNumOfImages = numOfImages;
                try {
                    PreparedStatement statement = conn.prepareStatement("UPDATE artist SET last_updated = ?, image_count = ? WHERE name = ? AND server = ?");
                    statement.setDate(1, new Date(new java.util.Date().getTime()));
                    statement.setInt(2, numOfImages);
                    statement.setString(3, artist);
                    statement.setInt(4, 3);
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
            prepared.setInt(1, 3);
            prepared.setString(2, artist);
            prepared.setString(3, "null");
            prepared.setDate(4, new Date(new java.util.Date().getTime()));
            prepared.setDate(5, new Date(new java.util.Date().getTime()));
            prepared.setInt(6, numOfImages);
            prepared.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DeviantArt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void download() throws Exception {
        artist = link.substring(link.lastIndexOf("/") + 1);
        artist = artist.substring(0, 1).toUpperCase() + artist.substring(1);
        
        taskManager.author.setText(artist + " | e621");
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
        } else {
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
                    String temp;
                    try {
                        taskManager.progressBar.setIndeterminate(false);
                        taskManager.progressBar.setMinimum(0);
                        taskManager.progressBar.setMaximum(numOfImages);
                        taskManager.progressBar.setStringPainted(true);
                        taskManager.progressBar.setString("0%");
                        taskManager.infoDisplay.setText(language.getContentById("downloading").replace("&num", "" + numOfImages));
                        isDownloading = true;

                        int cut = numOfImages - ((numOfPages - 1) * 75);
                        int count = 0;
                        executor = Executors.newFixedThreadPool(Integer.parseInt(xml.getContentById("simult")));

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
                                count++;

                                executor.execute(new ImageExtractor(0, artist, temp, numOfImages, count, taskManager, finalPath));
                            }
                        }

                    } catch (FailingHttpStatusCodeException | java.net.UnknownHostException ex) {
                        JOptionPane.showMessageDialog(null, language.getContentById("internetDroppedOut"), language.getContentById("genericErrorTitle"), JOptionPane.OK_OPTION);
                    } catch (java.net.SocketTimeoutException ex) {
                        Logger.getLogger(E621.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException | InterruptedException ex) {
                        Logger.getLogger(E621.class.getName()).log(Level.SEVERE, null, ex);
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
    }

    @Override
    public void run() {
        try {
            download();
        } catch (Exception ex) {
            Logger.getLogger(E621.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

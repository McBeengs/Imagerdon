/* **********   UpdateAllArtists.java   **********
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

import com.panels.modal.UpdateAllArtistsJPanel;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.panels.main.DownloadTaskJPanel;
import com.panels.main.StylizedMainJFrame;
import com.util.UsefulMethods;
import com.util.xml.XmlManager;
import java.awt.GridLayout;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class UpdateAllArtists extends Thread {

    private final Connection conn;
    private WebClient webClient;
    private final XmlManager xml;
    private final XmlManager language;
    private ExecutorService executor;
    private UpdateAllArtistsJPanel panel;

    public UpdateAllArtists() {
        xml = UsefulMethods.loadManager(UsefulMethods.OPTIONS);
        language = UsefulMethods.loadManager(UsefulMethods.LANGUAGE);
        conn = UsefulMethods.getDBInstance();
    }

    @Override
    public void run() {
        try {
            JFrame frame = new JFrame(language.getContentById("updateArtists"));
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.setLayout(new GridLayout(0, 1));
            panel = new UpdateAllArtistsJPanel();
            frame.add(panel);
            frame.setSize(panel.getMinimumSize());
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            ArrayList<String> artists = new ArrayList<>();
            ArrayList<Integer> images = new ArrayList<>();

            ResultSet rs = conn.createStatement().executeQuery("SELECT name, image_count FROM artist WHERE server = 0");
            while (rs.next()) {
                artists.add(rs.getString("name"));
                images.add(rs.getInt("image_count"));
            }
            rs.close();
            panel.setProgressMaximum(artists.size());

            while (!UsefulMethods.isWebClientReady()) {
                sleep(2);
            }

            webClient = UsefulMethods.getWebClientInstance();
            executor = Executors.newFixedThreadPool(Integer.parseInt(xml.getContentById("simult")));

            for (int i = 0; i < artists.size(); i++) {
                executor.execute(updateDA(artists.get(i), images.get(i)));
            }
            executor.shutdown();
            while (!executor.isTerminated()) {
                sleep(2);
            }

            panel.setServerDone(0);
            panel.setProgressValue(0);
            artists.clear();
            images.clear();

            rs = conn.createStatement().executeQuery("SELECT name, image_count FROM artist WHERE server = 1");
            while (rs.next()) {
                artists.add(rs.getString("name"));
                images.add(rs.getInt("image_count"));
            }
            rs.close();
            panel.setProgressMaximum(artists.size());
            executor = Executors.newFixedThreadPool(Integer.parseInt(xml.getContentById("simult")));

            for (int i = 0; i < artists.size(); i++) {
                executor.execute(updateTU(artists.get(i), images.get(i)));
            }
            executor.shutdown();
            while (!executor.isTerminated()) {
                sleep(2);
            }

            panel.setServerDone(1);
            panel.setProgressValue(0);
            artists.clear();
            images.clear();

            rs = conn.createStatement().executeQuery("SELECT name, image_count FROM artist WHERE server = 2");
            while (rs.next()) {
                artists.add(rs.getString("name"));
                images.add(rs.getInt("image_count"));
            }
            rs.close();
            panel.setProgressMaximum(artists.size());
            executor = Executors.newFixedThreadPool(Integer.parseInt(xml.getContentById("simult")));

            for (int i = 0; i < artists.size(); i++) {
                executor.execute(updateFA(artists.get(i), images.get(i)));
            }
            executor.shutdown();
            while (!executor.isTerminated()) {
                sleep(2);
            }

            panel.setServerDone(2);
            panel.setProgressValue(0);
            artists.clear();
            images.clear();

            rs = conn.createStatement().executeQuery("SELECT name, image_count FROM artist WHERE server = 3");
            while (rs.next()) {
                artists.add(rs.getString("name"));
                images.add(rs.getInt("image_count"));
            }
            rs.close();
            panel.setProgressMaximum(artists.size());
            executor = Executors.newFixedThreadPool(Integer.parseInt(xml.getContentById("simult")));

            for (int i = 0; i < artists.size(); i++) {
                executor.execute(updateE621(artists.get(i), images.get(i)));
            }
            executor.shutdown();
            while (!executor.isTerminated()) {
                sleep(2);
            }

            panel.setServerDone(3);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.dispose();

        } catch (Exception ex) {
            Logger.getLogger(UpdateAllArtists.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Runnable updateDA(final String artist, final int imageCount) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    String link = "http://" + artist.toLowerCase() + ".deviantart.com/gallery/?catpath=";
                    int numOfImages = 0;
                    HtmlPage page;

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

                    numOfImages -= 24;
                    page = webClient.getPage(link + numOfImages);
                    Document getNumberImages = Jsoup.parse(page.asXml());
                    Elements getNumber = getNumberImages.select("div[class~=tt-a tt-fh]");

                    numOfImages += getNumber.size();

                    if (numOfImages > imageCount) {
                        StylizedMainJFrame.AddTask add = StylizedMainJFrame.ADD_TASK;
                        add.addTask(link, DownloadTaskJPanel.DEVIANT_ART, DownloadTaskJPanel.UPDATE_TASK);
                    }

                    panel.setProgressValue(panel.getProgressValue() + 1);
                } catch (IOException | FailingHttpStatusCodeException ex) {
                    updateDA(artist, imageCount);
                }
            }
        };
    }

    private Runnable updateTU(final String artist, final int imageCount) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    String link = "http://" + artist.toLowerCase() + ".tumblr.com/archive/";
                    int numOfImages = 0;
                    HtmlPage page = webClient.getPage(link);

                    while (true) {
                        Document getNumberImages = Jsoup.parse(page.asXml());
                        Elements images = getNumberImages.getElementsByClass("hover"); // 50
                        numOfImages += images.size();
                        HtmlAnchor next = (HtmlAnchor) page.getElementById("next_page_link");
                        if (next != null) {
                            page = next.click();
                        } else {
                            break;
                        }
                    }

                    if (numOfImages > imageCount) {
                        StylizedMainJFrame.AddTask add = StylizedMainJFrame.ADD_TASK;
                        add.addTask(link, DownloadTaskJPanel.TUMBLR, DownloadTaskJPanel.UPDATE_TASK);
                    }

                    panel.setProgressValue(panel.getProgressValue() + 1);
                } catch (IOException | FailingHttpStatusCodeException ex) {
                    updateTU(artist, imageCount);
                }
            }
        };
    }

    private Runnable updateFA(final String artist, final int imageCount) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    String link = "http://www.furaffinity.net/gallery/" + artist.toLowerCase() + "/";
                    int numOfPages = 0;
                    int numOfImages = 0;
                    int c = 1;
                    HtmlPage page;

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

                    if (numOfImages > imageCount) {
                        StylizedMainJFrame.AddTask add = StylizedMainJFrame.ADD_TASK;
                        add.addTask(link, DownloadTaskJPanel.FUR_AFFINITY, DownloadTaskJPanel.UPDATE_TASK);
                    }

                    panel.setProgressValue(panel.getProgressValue() + 1);
                } catch (IOException | FailingHttpStatusCodeException ex) {
                    updateFA(artist, imageCount);
                }
            }
        };
    }

    private Runnable updateE621(final String artist, final int imageCount) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    String link = "https://e621.net/post/index/1/" + artist.toLowerCase() + "/";
                    int numOfPages;
                    int numOfImages = 0;
                    HtmlPage page = webClient.getPage(link);
                    Document getNumberImages = Jsoup.parse(page.asXml());

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

                    if (numOfImages > imageCount) {
                        StylizedMainJFrame.AddTask add = StylizedMainJFrame.ADD_TASK;
                        add.addTask(link, DownloadTaskJPanel.E621, DownloadTaskJPanel.UPDATE_TASK);
                    }

                    panel.setProgressValue(panel.getProgressValue() + 1);
                } catch (IOException | FailingHttpStatusCodeException ex) {
                    updateE621(artist, imageCount);
                }
            }
        };
    }
}

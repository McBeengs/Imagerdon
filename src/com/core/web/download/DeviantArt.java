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
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import static java.lang.Thread.sleep;
import java.util.Calendar;
import java.util.Locale;

public class DeviantArt extends BasicCore {

    private int numOfImages;
    private int numOfPages = 0;
    private int originalNumOfImages;
    private int tagOcc;
    private boolean isPaused = false;
    private boolean isTerminated = false;
    private boolean isDownloading = false;
    private boolean convertedToUpdate;
    private String finalPath;
    private String link;

    private String avatarUrl;
    private String memberSince;
    private String sexNacionality;

    private WebClient webClient;
    private XmlManager xml;
    private XmlManager language;
    private XmlManager artists;
    private ExecutorService executor;
    private DownloadTaskJPanel taskManager;
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
        artists = new XmlManager();

        try {
            File artistsXml = new File(xml.getContentById("DAoutput") + System.getProperty("file.separator") + "artists-log.xml");
            if (!artistsXml.exists()) {
                artists.createFile(artistsXml.getAbsolutePath());
            } else {
                artists.loadFile(artistsXml);
            }

            String artist = link.substring(7, link.indexOf("."));
            artist = artist.substring(0, 1).toUpperCase() + artist.substring(1);
            finalPath = xml.getContentById("DAoutput") + System.getProperty("file.separator") + artist;
            File file = new File(finalPath);
            if (!file.exists()) {
                file.mkdirs();
            } else {
                //System.out.println("folder exists");
            }
        } catch (MalformedURLException ex) {
            System.out.println(ex.toString());
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    private boolean getInformationAboutGallery() throws IOException {
        try {
            taskManager.infoDisplay.setText(language.getContentById("getImages"));
            HtmlPage conn = webClient.getPage(link + "0");

            Document getNumberImages = Jsoup.parse(conn.asXml());
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
                    
                    conn = webClient.getPage(link + numOfImages);

                    if (conn.asText().contains("This section has no deviations yet!")) {
                        break;
                    }

                    numOfImages += 24;
                }
                //by now "numOfImages" is holding the last avaliable page, but we need specify how many
                //images are on the last page

                numOfImages -= 24;

                conn = webClient.getPage(link + numOfImages);
                getNumberImages = Jsoup.parse(conn.asXml());
                Elements getNumber = getNumberImages.select("div[class~=tt-a tt-fh]");

                numOfImages += getNumber.size();
                numOfPages = numOfImages / 24 + 1;
            }
        } catch (java.net.UnknownHostException ex) {
            JOptionPane.showMessageDialog(null, language.getContentById("internetDroppedOut"), language.getContentById("genericErrorTitle"), JOptionPane.OK_OPTION);
            return false;
        }

        return true;
    }

    private boolean checkArtistExistance() throws IOException {
        String artist = link.substring(7, link.indexOf("."));
        artist = artist.substring(0, 1).toUpperCase() + artist.substring(1);

        if (Integer.parseInt(xml.getContentById("existed")) == 1) {
            if (finalPath.contains(artist)) {
                File[] getImages = new File(finalPath).listFiles();
                for (File image : getImages) {
                    if (image.isFile()) {
                        image.delete();
                    }
                }
            }

            if (artists.checkIfTagExists("name", artist)) {
                originalNumOfImages = numOfImages;
                tagOcc = artists.getTagIndex("name", artist);
                artists.setContentByName("imageCount", tagOcc, "" + numOfImages);
                artists.saveXml();
            } else {
                createArtistTag();
            }

            return true;
        }

        if (artists.checkIfTagExists("name", artist)) {
            if (Integer.parseInt(xml.getContentById("existed")) == 0) {
                String[] texts = language.getContentById("skipEnabled").replace("&string", artist).split("&br");
                JOptionPane.showMessageDialog(null, texts[0] + "\n" + texts[1], language.getContentById("genericInfoTitle"), JOptionPane.INFORMATION_MESSAGE);
                return false;
            }

            if (!finalPath.contains(artist)) {
                originalNumOfImages = numOfImages;
                tagOcc = artists.getTagIndex("name", artist);
                artists.setContentByName("imageCount", tagOcc, "" + numOfImages);
                artists.saveXml();
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
                        taskManager.setNewExtractor(new UpdateFurAffinity(link, taskManager));
                    }
                }.start();
                convertedToUpdate = true;
                return false;
            } else {
                originalNumOfImages = numOfImages;
                tagOcc = artists.getTagIndex("name", artist);
                artists.setContentByName("imageCount", tagOcc, "" + numOfImages);
                artists.saveXml();
                return true;
            }
        } else {
            createArtistTag();
            return true;
        }
    }

    private void createArtistTag() throws IOException {
        String artist = link.substring(7, link.indexOf("."));
        artist = artist.substring(0, 1).toUpperCase() + artist.substring(1);
        originalNumOfImages = numOfImages;
        artists.addSubordinatedTag("artist", "root", 0);
        tagOcc = artists.getAllContentsByName("artist").size() - 1;
        artists.addSubordinatedTag("name", "artist", tagOcc);
        artists.setContentByName("name", tagOcc, artist);
        artists.addSubordinatedTag("avatarUrl", "artist", tagOcc);
        artists.setContentByName("avatarUrl", tagOcc, avatarUrl);
        artists.addSubordinatedTag("firstDownloaded", "artist", tagOcc);
        artists.setContentByName("firstDownloaded", tagOcc, UsefulMethods.getSimpleDateFormat());
        artists.addSubordinatedTag("lastUpdated", "artist", tagOcc);
        artists.setContentByName("lastUpdated", tagOcc, UsefulMethods.getSimpleDateFormat());
        artists.addSubordinatedTag("imageCount", "artist", tagOcc);
        artists.setContentByName("imageCount", tagOcc, "" + numOfImages);
        artists.saveXml();
    }

    private void download() throws Exception {
        String artist = link.substring(7, link.indexOf("."));
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
        String artist = link.substring(7, link.indexOf("."));
        final String artistFinal = artist;

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

                        int cut = numOfImages - ((numOfPages - 1) * 24);
                        int c = 0;
                        int count = 0;
                        String before = "";
                        executor = Executors.newFixedThreadPool(Integer.parseInt(xml.getContentById("simult")));

                        while (c < numOfImages) {
                            if (isTerminated) {
                                break;
                            }

                            HtmlPage conn = webClient.getPage(link + c);
                            Document docLinks = Jsoup.parse(conn.asXml());
                            Elements getPages = docLinks.select("a[href~=" + artistFinal + ".deviantart.com/art/]");

                            for (int i = 0; i < getPages.size(); i++) {
                                while (isPaused) {
                                    sleep(2);
                                }
                                if (count == numOfImages) {
                                    break;
                                }

                                String temp = getPages.get(i).toString();
                                temp = temp.substring(temp.indexOf("href"));
                                temp = temp.substring(6, temp.indexOf("\"", 6));

                                if (!before.equals(temp) && !temp.contains("#comments")) {
                                    before = temp;
                                    HtmlPage page = webClient.getPage(temp);

                                    Document docImages = Jsoup.parse(page.asXml());
                                    Elements getLinks = docImages.select("img[data-embed-format~=thumb]");
                                    temp = getLinks.get(1).toString();

                                    temp = temp.substring(temp.indexOf("src") + 5);
                                    temp = temp.substring(0, temp.indexOf("\""));
                                    count++;

                                    executor.submit(new ImageExtractor(temp, count));

                                }
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
    }

    @Override
    public void run() {
        try {
            download();
            artists.saveXml();
        } catch (Exception ex) {
            Logger.getLogger(DeviantArt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private class ImageExtractor implements Runnable {

        String finalLink;
        int downloadNumber;

        public ImageExtractor(String finalLink, int downloadNumber) {
            this.finalLink = finalLink;
            this.downloadNumber = downloadNumber;
        }

        private boolean download() {
            String imageName = null;
            if (!Boolean.parseBoolean(xml.getContentById("DAadvancedNaming"))) {
                imageName = finalLink.substring(finalLink.lastIndexOf("/"), finalLink.length());
            } else if (Integer.parseInt(xml.getContentById("DAnamingOption")) == 0) {
                imageName = downloadNumber + finalLink.substring(finalLink.lastIndexOf("."));
            } else if (Integer.parseInt(xml.getContentById("DAnamingOption")) == 1) {
                imageName = getDate() + finalLink.substring(finalLink.lastIndexOf("."));
            }

            try {
                URL imageURL = new URL(finalLink);
                InputStream inputImg = imageURL.openStream();
                OutputStream imageFile = new FileOutputStream(finalPath + System.getProperty("file.separator") + imageName);
                BufferedOutputStream writeImg = new BufferedOutputStream(imageFile);

                NumberFormat nf = NumberFormat.getNumberInstance();
                nf.setMaximumFractionDigits(1);
                nf.setGroupingUsed(true);

                int bytes;
                while ((bytes = inputImg.read()) != -1) {
                    while (isPaused) {
                        sleep(2);
                    }

                    if (isTerminated) {
                        break;
                    } else {
                        writeImg.write(bytes);
                    }
                }

                writeImg.close();
                imageFile.close();
                inputImg.close();

                numOfImages--;
                if (!isTerminated) {
                    taskManager.infoDisplay.setText(language.getContentById("downloading").replace("&num", "" + numOfImages));
                    taskManager.progressBar.setValue(taskManager.progressBar.getValue() + 1);

                    String show = nf.format(taskManager.progressBar.getPercentComplete() * 100) + "%";
                    taskManager.progressBar.setString(show);
                    artists.setContentByName("imageCount", tagOcc, "" + (originalNumOfImages - numOfImages));

                    if (show.equals("100%")) {
                        taskManager.stopButton.setVisible(false);
                        taskManager.infoDisplay.setText(language.getContentById("downloadFinished"));

                        for (java.awt.event.MouseListener listener : taskManager.playButton.getMouseListeners()) {
                            taskManager.playButton.removeMouseListener(listener);
                        }

                        artists.saveXml();
                        taskManager.playButton.addMouseListener(taskManager.playButtonDownloadFinishedBehavior());
                    }
                } else {
                    File files = new File(finalPath + System.getProperty("file.separator") + imageName);
                    files.delete();

                    files = new File(finalPath);
                    artists.setContentByName("imageCount", tagOcc, "" + files.list().length);
                    artists.saveXml();
                }
            } catch (java.net.ConnectException ex) {
                executor.execute(new ImageExtractor(finalLink, downloadNumber));
            } catch (MalformedURLException ex) {
                Logger.getLogger(DeviantArt.class.getName()).log(Level.SEVERE, null, ex);
            } catch (java.net.SocketException ex) {
                executor.execute(new ImageExtractor(finalLink, downloadNumber));
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(DeviantArt.class.getName()).log(Level.SEVERE, null, ex);
            }

            return false;
        }

        private String getDate() {
            Calendar date = Calendar.getInstance();
            String gmt;
            String hours;
            String minutes;
            String seconds;
            String millis;

            if (((date.get(Calendar.ZONE_OFFSET) / (1000 * 60 * 60)) % 24) < 10) {
                gmt = "GMT -0" + ((date.get(Calendar.ZONE_OFFSET) * -1 / (1000 * 60 * 60)) % 24) + "h";
            } else {
                gmt = "GMT -" + ((date.get(Calendar.ZONE_OFFSET) * -1 / (1000 * 60 * 60)) % 24) + "h";
            }

            if (date.get(Calendar.HOUR_OF_DAY) < 10) {
                hours = "0" + date.get(Calendar.HOUR_OF_DAY);
            } else {
                hours = "" + date.get(Calendar.HOUR_OF_DAY);
            }

            if (date.get(Calendar.MINUTE) < 10) {
                minutes = "0" + date.get(Calendar.MINUTE);
            } else {
                minutes = "" + date.get(Calendar.MINUTE);
            }

            if (date.get(Calendar.SECOND) < 10) {
                seconds = "0" + date.get(Calendar.SECOND);
            } else {
                seconds = "" + date.get(Calendar.SECOND);
            }

            millis = "" + date.get(Calendar.MILLISECOND);

            return hours + "h, " + minutes + "min, " + seconds + "sec & " + millis + "mil (" + gmt + ")" + ", at "
                    + date.getDisplayName(2, 2, Locale.US) + " " + date.get(5) + ", " + date.get(1);
        }

        @Override
        public void run() {
            download();
        }
    }
}

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
import com.panels.main.StylizedMainJFrame;
import com.util.crypto.PasswordManager;
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
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

@SuppressWarnings("")
public class FurAffinity extends BasicCore {

    private boolean isPaused = false;
    private boolean isTerminated = false;
    private boolean isDownloading = false;
    private boolean convertedToUpdate = false;
    private int tagOcc;
    private int numOfImages;
    private int originalNumOfImages;
    private int numOfPages = 0;
    private String finalPath;
    private String link;

    private String avatarUrl;
    private String pageVisits;
    private String commentsReceived;
    private String commentsGiven;
    private String journals;
    private String favourites;

    private WebClient webClient;
    private ExecutorService executor;
    private XmlManager xml;
    private XmlManager language;
    private XmlManager artists;
    private PasswordManager pass;
    private DownloadTaskJPanel taskManager;

    public FurAffinity(String url, DownloadTaskJPanel taskManager) {
        link = url;
        this.taskManager = taskManager;

        xml = UsefulMethods.loadManager(UsefulMethods.OPTIONS);
        language = UsefulMethods.loadManager(UsefulMethods.LANGUAGE);
        artists = new XmlManager();
        pass = new PasswordManager();

        String artist = url.substring(35, url.lastIndexOf("/"));
        artist = artist.substring(0, 1).toUpperCase() + artist.substring(1);

        try {
            File artistsXml = new File(xml.getContentById("FAoutput") + System.getProperty("file.separator") + "artists-log.xml");
            if (!artistsXml.exists()) {
                artists.createFile(artistsXml.getAbsolutePath());
            } else {
                artists.loadFile(artistsXml);
            }

            if (Boolean.parseBoolean(xml.getContentById("sub"))) {
                finalPath = xml.getContentById("FAoutput") + System.getProperty("file.separator") + artist;
                File file = new File(finalPath);
                if (!file.exists()) {
                    file.mkdirs();
                } else {
                    //System.out.println("folder exists");
                }
            } else {
                finalPath = xml.getContentById("FAoutput");
                File file = new File(finalPath);
                if (!file.exists()) {
                    file.mkdirs();
                }
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
            HtmlPage conn = webClient.getPage(link);

            Document getNumberImages = Jsoup.parse(conn.asXml());
            Elements testURL = getNumberImages.select("body");

            if (testURL.toString().contains("could not be found.")) {
                JOptionPane.showMessageDialog(null, language.getContentById("errorUrlNonExistent"), language.getContentById("genericErrorTitle"), 0);
                return false;
            } else {
                int c = 1;
                while (1 > 0) {
                    HtmlPage page = webClient.getPage(link + c + "/");

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
        } catch (java.net.UnknownHostException ex) {
            JOptionPane.showMessageDialog(null, language.getContentById("internetDroppedOut"), language.getContentById("genericErrorTitle"), JOptionPane.OK_OPTION);
            return false;
        }

        return true;
    }

    private boolean checkArtistExistance() throws IOException {
        String artist = link.substring(35, link.lastIndexOf("/"));
        artist = artist.substring(0, 1).toUpperCase() + artist.substring(1);

        try {
            HtmlPage page = webClient.getPage("http://www.furaffinity.net/user/" + artist.toLowerCase());
            Document parsed = Jsoup.parse(page.getBody().asXml());

            avatarUrl = parsed.select("img.avatar").get(0).attr("src");
            Elements infoTable = parsed.select("table").get(3).select("td");
            String temp = infoTable.get(12).text();
            temp = temp.substring(temp.indexOf(":") + 2);
            pageVisits = temp.substring(0, temp.indexOf(" "));
            temp = temp.substring(temp.indexOf(":") + 2);
            temp = temp.substring(temp.indexOf(":") + 2);
            commentsReceived = temp.substring(0, temp.indexOf(" "));
            temp = temp.substring(temp.indexOf(":") + 2);
            commentsGiven = temp.substring(0, temp.indexOf(" "));
            temp = temp.substring(temp.indexOf(":") + 2);
            journals = temp.substring(0, temp.indexOf(" "));
            temp = temp.substring(temp.indexOf(":") + 2);
            favourites = temp.replaceAll("[^0-9]", "");

        } catch (java.lang.IndexOutOfBoundsException ex) {
            avatarUrl = "null";
            pageVisits = "null";
            commentsReceived = "null";
            commentsGiven = "null";
            journals = "null";
            favourites = "null";
        } catch (FailingHttpStatusCodeException | java.lang.NullPointerException ex) {
            JOptionPane.showMessageDialog(null, language.getContentById("internetDroppedOut"), language.getContentById("genericErrorTitle"), JOptionPane.OK_OPTION);
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
                new Thread("Changed from Download to Update task") {
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
        String artist = link.substring(35, link.lastIndexOf("/"));
        artist = artist.substring(0, 1).toUpperCase() + artist.substring(1);
        originalNumOfImages = numOfImages;
        artists.addSubordinatedTag("artist", "root", 0);
        tagOcc = artists.getAllContentsByName("artist").size() - 1;
        artists.addSubordinatedTag("name", "artist", tagOcc);
        artists.setContentByName("name", tagOcc, artist);
        artists.addSubordinatedTag("avatarUrl", "artist", tagOcc);
        artists.setContentByName("avatarUrl", tagOcc, "http://" + avatarUrl.substring(2, avatarUrl.length()));
        artists.addSubordinatedTag("firstDownloaded", "artist", tagOcc);
        artists.setContentByName("firstDownloaded", tagOcc, UsefulMethods.getSimpleDateFormat());
        artists.addSubordinatedTag("lastUpdated", "artist", tagOcc);
        artists.setContentByName("lastUpdated", tagOcc, UsefulMethods.getSimpleDateFormat());
        artists.addSubordinatedTag("pageVisits", "artist", tagOcc);
        artists.setContentByName("pageVisits", tagOcc, pageVisits);
        artists.addSubordinatedTag("commentsReceived", "artist", tagOcc);
        artists.setContentByName("commentsReceived", tagOcc, commentsReceived);
        artists.addSubordinatedTag("commentsGiven", "artist", tagOcc);
        artists.setContentByName("commentsGiven", tagOcc, commentsGiven);
        artists.addSubordinatedTag("journals", "artist", tagOcc);
        artists.setContentByName("journals", tagOcc, journals);
        artists.addSubordinatedTag("favourites", "artist", tagOcc);
        artists.setContentByName("favourites", tagOcc, favourites);
        artists.addSubordinatedTag("imageCount", "artist", tagOcc);
        artists.setContentByName("imageCount", tagOcc, "" + numOfImages);
        artists.saveXml();
    }

    private void download() throws IOException, InterruptedException, ExecutionException, Exception {
        String artist = link.substring(35, link.lastIndexOf("/"));
        artist = artist.substring(0, 1).toUpperCase() + artist.substring(1);
        taskManager.author.setText(artist + " | FurAfinity");

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

            if (StylizedMainJFrame.AUTO_START) {
                downloadAction();
                taskManager.setExecutingLayout();
            }

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

                        int cut = numOfImages - ((numOfPages - 1) * 72);
                        int count = 0;
                        executor = Executors.newFixedThreadPool(Integer.parseInt(xml.getContentById("simult")));
                        executor.awaitTermination(0L, TimeUnit.SECONDS);

                        for (int c = 1; c <= numOfPages; c++) {
                            if (isTerminated) {
                                break;
                            }

                            HtmlPage conn = webClient.getPage(link + c + "/");
                            Document docLinks = Jsoup.parse(conn.asXml());
                            Elements getPages = docLinks.select("a[href~=/view/]");

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
                                int end = temp.indexOf("/", 15);
                                temp = "http://www.furaffinity.net" + temp.substring(9, end);
                                conn = webClient.getPage(temp);
                                Document docImages = Jsoup.parse(conn.asXml());
                                Elements getLinks = docImages.select("a[href~=//d.facdn.net/art/]");
                                String tempImagesArray = getLinks.toString();
                                end = tempImagesArray.indexOf("\"", 11);

                                if (end - 11 > 0) {
                                    temp = "http://" + tempImagesArray.substring(11, end);
                                    count++;

                                    if (isTerminated) {
                                        break;
                                    } else {
                                        executor.execute(new ImageExtractor(temp, count));
                                    }
                                } else {
                                    numOfImages--;
                                    taskManager.progressBar.setValue(taskManager.progressBar.getValue() + 1);

                                    NumberFormat nf = NumberFormat.getNumberInstance();
                                    nf.setMaximumFractionDigits(1);
                                    nf.setGroupingUsed(true);

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
                                }
                            }
                        }

                    } catch (FailingHttpStatusCodeException | java.net.UnknownHostException | java.net.ConnectException ex) {
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
            Logger.getLogger(FurAffinity.class.getName()).log(Level.SEVERE, null, ex);
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
            if (!Boolean.parseBoolean(xml.getContentById("FAadvancedNaming"))) {
                imageName = finalLink.substring(finalLink.lastIndexOf("/"), finalLink.length());
            } else if (Integer.parseInt(xml.getContentById("FAnamingOption")) == 0) {
                imageName = downloadNumber + finalLink.substring(finalLink.lastIndexOf("."));
            } else if (Integer.parseInt(xml.getContentById("FAnamingOption")) == 1) {
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
                executor.submit(new ImageExtractor(finalLink, downloadNumber));
            } catch (java.io.FileNotFoundException ex) {
                numOfImages--;
                taskManager.progressBar.setValue(taskManager.progressBar.getValue() + 1);

                NumberFormat nf = NumberFormat.getNumberInstance();
                nf.setMaximumFractionDigits(1);
                nf.setGroupingUsed(true);

                String show = nf.format(taskManager.progressBar.getPercentComplete() * 100) + "%";
                taskManager.progressBar.setString(show);
                artists.setContentByName("imageCount", tagOcc, "" + (originalNumOfImages - numOfImages));

                if (show.equals("100%")) {
                    taskManager.stopButton.setVisible(false);
                    taskManager.infoDisplay.setText(language.getContentById("downloadFinished"));

                    for (java.awt.event.MouseListener listener : taskManager.playButton.getMouseListeners()) {
                        taskManager.playButton.removeMouseListener(listener);
                    }

                    try {
                        artists.saveXml();
                    } catch (IOException ex1) {
                        Logger.getLogger(FurAffinity.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    taskManager.playButton.addMouseListener(taskManager.playButtonDownloadFinishedBehavior());
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(FurAffinity.class.getName()).log(Level.SEVERE, null, ex);
            } catch (java.net.SocketException ex) {
                executor.submit(new ImageExtractor(finalLink, downloadNumber));
            } catch (IOException | InterruptedException ex) {
                executor.submit(new ImageExtractor(finalLink, downloadNumber));
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

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

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.panels.main.DownloadTaskJPanel;
import com.util.UsefulMethods;
import com.util.crypto.PasswordManager;
import com.util.xml.XmlManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.Thread.sleep;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;
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
    private int tagOcc;
    private int numOfImages;
    private int originalNumOfImages;
    private int numOfPages = 0;
    private String finalPath;
    private String link;
    private WebClient webClient;
    private ExecutorService executor;
    private XmlManager xml;
    private XmlManager language;
    private XmlManager artists;
    private PasswordManager pass;
    private DownloadTaskJPanel taskManager;

    public Tumblr(String url, DownloadTaskJPanel taskManager) {
        link = url;
        this.taskManager = taskManager;

        if (!link.endsWith("archive/") || !link.endsWith("archive")) {
            link = link.substring(0, link.indexOf("/", 9));
            link += "/archive/";
        }

        webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setAppletEnabled(false);

        webClient = UsefulMethods.shutUpHtmlUnit(webClient);
        webClient.getCurrentWindow().setInnerHeight(Integer.MAX_VALUE);

        xml = UsefulMethods.loadManager(UsefulMethods.OPTIONS);
        language = UsefulMethods.loadManager(UsefulMethods.LANGUAGE);
        artists = new XmlManager();
        pass = new PasswordManager();

        try {
            File artistsXml = new File(xml.getContentById("TUoutput") + System.getProperty("file.separator") + "artists-log.xml");
            if (!artistsXml.exists()) {
                artists.createFile(artistsXml.getAbsolutePath());
            } else {
                artists.loadFile(artistsXml);
            }

            String artist = link.substring(7, link.indexOf("."));
            artist = artist.substring(0, 1).toUpperCase() + artist.substring(1);
            if (Boolean.parseBoolean(xml.getContentById("sub"))) {
                finalPath = xml.getContentById("TUoutput") + System.getProperty("file.separator") + artist;
                File file = new File(finalPath);
                if (!file.exists()) {
                    file.mkdirs();
                } else {
                    //System.out.println("folder exists");
                }
            } else {
                finalPath = xml.getContentById("TUoutput");
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

    private boolean submittingForm() {
        try {
            taskManager.infoDisplay.setText(language.getContentById("loginIn"));
            HtmlPage page1 = webClient.getPage("https://www.tumblr.com/login");
            HtmlForm form = page1.getHtmlElementById("signup_form");

            HtmlTextInput usernameCheck = form.getInputByName("determine_email");
            HtmlTextInput usernameField = form.getInputByName("user[email]");
            HtmlPasswordInput passwordField = form.getInputByName("user[password]");
            HtmlButton next = (HtmlButton) page1.getElementsByIdAndOrName("signup_forms_submit").get(0);
            HtmlButton button = form.getFirstByXPath("button");

            usernameCheck.setValueAttribute("nunomcbeenga@gmail.com");
            usernameField.setValueAttribute("nunomcbeenga@gmail.com");
            next.click();
            passwordField.setValueAttribute("swordfish");

            HtmlPage page2 = button.click();
            if (page2.getBaseURL().toString().equals("https://www.tumblr.com/login")) {
                JOptionPane.showMessageDialog(null, language.getContentById("loginFailed").replace("&string", "Tumblr"),
                        language.getContentById("genericErrorTitle"), JOptionPane.OK_OPTION);
                return false;
            }

        } catch (java.net.UnknownHostException | org.apache.http.conn.HttpHostConnectException ex) {
            JOptionPane.showMessageDialog(null, language.getContentById("internetDroppedOut"), language.getContentById("genericErrorTitle"), JOptionPane.OK_OPTION);
        } catch (IOException ex) {
            Logger.getLogger(Tumblr.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FailingHttpStatusCodeException ex) {
            System.err.println("Deu ruim na task nº" + taskManager.getTaskNumber());
        }

        return true;
    }

    private boolean getInformationAboutGallery() throws IOException {
        taskManager.infoDisplay.setText(language.getContentById("getImages"));
        HtmlPage conn = webClient.getPage(link);

        Document getNumberImages = Jsoup.parse(conn.asXml());
        Elements testURL = getNumberImages.select("body");

        if (testURL.toString().contains("There's nothing here.")) {
            JOptionPane.showMessageDialog(null, language.getContentById("errorUrlNonExistent"), language.getContentById("genericErrorTitle"), 0);
            return false;
        } else {
            while (true) {
                getNumberImages = Jsoup.parse(conn.asXml());
                Elements images = getNumberImages.getElementsByClass("hover"); // 50
                numOfImages += images.size();
                //System.out.println(nextPage.toString());
                HtmlAnchor next = (HtmlAnchor) conn.getElementById("next_page_link");
                if (next != null) {
                    conn = next.click();
                } else {
                    break;
                }
            }

            numOfPages = numOfImages / 50;
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
                        //taskManager.setNewExtractor(new UpdateFurAffinity(link, taskManager));
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
        artists.addSubordinatedTag("imageCount", "artist", tagOcc);
        artists.setContentByName("imageCount", tagOcc, "" + numOfImages);
        artists.saveXml();
    }

    private void download() throws IOException {
        String artist = link.substring(7, link.indexOf("."));
        artist = artist.substring(0, 1).toUpperCase() + artist.substring(1);
        taskManager.author.setText(artist + " | Tumblr");

        if (submittingForm() && getInformationAboutGallery() && checkArtistExistance()) {
            taskManager.infoDisplay.setText(language.getContentById("wentOK"));
            taskManager.playButton.setVisible(true);
            taskManager.progressBar.setIndeterminate(false);
            taskManager.playButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
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
                                    HtmlPage conn = webClient.getPage(link);
                                    executor = Executors.newFixedThreadPool(Integer.parseInt(xml.getContentById("simult")));

                                    for (int c = 0; c < numOfPages; c++) {

                                        if (isTerminated) {
                                            break;
                                        }

                                        Document getImages = Jsoup.parse(conn.asXml());
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
                                            Elements image = Jsoup.parse(post.asXml()).getElementsByClass("bustImage");
                                            if (image.size() > 0) {
                                                temp = image.get(0).toString();

                                                if (temp.contains("src")) {
                                                    temp = temp.substring(temp.indexOf("src") + 5);
                                                    temp = temp.substring(0, temp.indexOf("\""));
                                                    c++;

                                                    executor.execute(new ImageExtractor(temp, count));
                                                }
                                            } else {
                                                numOfImages--;
                                                taskManager.infoDisplay.setText(language.getContentById("downloading").replace("&num", "" + numOfImages));
                                                taskManager.progressBar.setValue(taskManager.progressBar.getValue() + 1);
                                            }
                                        }

                                        HtmlAnchor next = (HtmlAnchor) conn.getElementById("next_page_link");
                                        conn = next.click();
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
            if (!Boolean.parseBoolean(xml.getContentById("TUadvancedNaming"))) {
                imageName = finalLink.substring(finalLink.lastIndexOf("/"), finalLink.length());
            } else if (Integer.parseInt(xml.getContentById("TUnamingOption")) == 0) {
                imageName = downloadNumber + finalLink.substring(finalLink.lastIndexOf("."));
            } else if (Integer.parseInt(xml.getContentById("TUnamingOption")) == 1) {
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
            } catch (MalformedURLException ex) {
                Logger.getLogger(Tumblr.class.getName()).log(Level.SEVERE, null, ex);
            } catch (java.net.SocketException ex) {
                executor.submit(new ImageExtractor(finalLink, downloadNumber));
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(Tumblr.class.getName()).log(Level.SEVERE, null, ex);
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

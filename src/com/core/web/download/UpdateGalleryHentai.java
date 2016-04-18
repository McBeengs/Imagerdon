/* **********   UpdateGalleryHentai.java   **********
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
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.panels.main.DownloadTaskJPanel;
import com.util.UsefulMethods;
import com.util.xml.XmlManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import static java.lang.Thread.sleep;
import java.util.concurrent.TimeUnit;

public class UpdateGalleryHentai extends BasicCore {

    private int numOfImages;
    private int numOfPages = 0;
    private int originalNumOfImages;
    private int tagOcc;
    private String finalPath;
    private String link;
    private String galleryName;
    private String display;
    private boolean isPaused = false;
    private boolean isTerminated = false;
    private boolean isDownloading = false;
    private final WebClient webClient;
    private final XmlManager xml;
    private final XmlManager language;
    private final XmlManager artists;
    private ExecutorService executor;
    private final DownloadTaskJPanel taskManager;

    public UpdateGalleryHentai(String url, DownloadTaskJPanel taskManager) {
        link = url;
        this.taskManager = taskManager;

        this.taskManager.playButton.setVisible(false);
        this.taskManager.progressBar.setIndeterminate(true);

        webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setAppletEnabled(false);

        xml = UsefulMethods.loadManager(UsefulMethods.OPTIONS);
        language = UsefulMethods.loadManager(UsefulMethods.LANGUAGE);
        artists = new XmlManager();
    }

    private boolean getInformationAboutGallery() throws IOException {
        try {
            taskManager.infoDisplay.setText(language.getContentById("getImages"));
            HtmlPage conn = webClient.getPage(link);
            Document check = Jsoup.parse(conn.asXml());
            String linkTemp = link;

            if (check.toString().contains("This gallery has been flagged as")) {
                linkTemp += "?nw=session";
            }

            if (check.toString().contains("Your IP address has been temporarily banned")) {
                String left = check.toString().substring(246);
                left = left.substring(0, left.indexOf("</body>") - 2);
                System.out.println(left);
                //JOptionPane.showMessageDialog(null, "Oh shit... Seems that you were temporally banned from the site due\n"
                //        + "to download overload. If you want to proceed with the task, you must wait");
            }

            conn = webClient.getPage(linkTemp);
            Document getNumberImages = Jsoup.parse(conn.asXml());
            Elements testURL = getNumberImages.select("body");

            Elements gallery = getNumberImages.select("#gn");
            setAditionalInfo(gallery.get(0).toString().substring(13));

            if (testURL.toString().contains("Key missing") == true) {
                JOptionPane.showMessageDialog(null, language.getContentById("errorUrlNonExistent"), language.getContentById("genericErrorTitle"), 0);
                return false;
            } else {
                Elements getNumber = getNumberImages.select(".gdt2");

                int last = getNumber.get(5).toString().indexOf(" ", 18);
                String temp = getNumber.get(5).toString().substring(18, last);
                numOfImages = Integer.parseInt(temp);
                float result = numOfImages / 40;

                for (int h = 1; h < 999; h++) {
                    if (result <= h) {
                        numOfPages = h;
                        break;
                    }
                }
            }
        } catch (java.net.UnknownHostException ex) {
            JOptionPane.showMessageDialog(null, language.getContentById("internetDroppedOut"), language.getContentById("genericErrorTitle"), JOptionPane.OK_OPTION);
            return false;
        }

        return true;
    }

    private void setAditionalInfo(String galleryName) {
        this.galleryName = galleryName;
        this.galleryName = galleryName.substring(0, galleryName.length() - 6);

        display = this.galleryName;
        if (display.length() > 20) {
            display = galleryName.substring(0, 20) + "...";
        }

        taskManager.author.setText(display + " | Gallery Hentai");

        try {
            File artistsXml = new File(xml.getContentById("GHoutput") + System.getProperty("file.separator") + "artists-log.xml");
            if (!artistsXml.exists()) {
                artists.createFile(artistsXml.getAbsolutePath());
            } else {
                artists.loadFile(artistsXml);
            }

            display = this.galleryName;
            String[] illegal = {"/", "\\", "*", "<", ">", "?", ":", "\"", "|"};
            String[] legal = {"[bar]", "[bar]", "[star]", "[less]", "[more]", "[question]", "[colon]", "[quotation]", "[bar]"};

            for (int i = 0; i < illegal.length; i++) {
                if (display.contains(illegal[i])) {
                    display = display.replace(illegal[i], legal[i]);
                }
            }

            finalPath = xml.getContentById("GHoutput") + System.getProperty("file.separator") + display;
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

    private void download() throws IOException {
        taskManager.author.setText(language.getContentById("GHGalleryName"));
        
        if (getInformationAboutGallery()) {
            taskManager.infoDisplay.setText(language.getContentById("wentOK"));

            java.awt.event.MouseListener[] teste = this.taskManager.playButton.getMouseListeners();
            for (MouseListener teste1 : teste) {
                taskManager.playButton.removeMouseListener(teste1);
            }

            taskManager.playButton.addMouseListener(taskManager.playButtonNormalBehavior());
            taskManager.playButton.setVisible(true);
            taskManager.progressBar.setIndeterminate(false);

            taskManager.playButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    if (!isDownloading) {
                        isDownloading = true;
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    int count = 0;
                                    tagOcc = artists.getTagIndex("name", display);
                                    int onDisk = Integer.parseInt(artists.getContentByName("imageCount", tagOcc + 2));

                                    if (onDisk >= numOfImages) {
                                        JOptionPane.showMessageDialog(null, language.getContentById("artistUp2Date").replaceAll("&string", display));
                                        taskManager.progressBar.setValue(taskManager.progressBar.getMaximum());
                                        taskManager.progressBar.setStringPainted(true);
                                        taskManager.progressBar.setString("100%");
                                        taskManager.infoDisplay.setText(language.getContentById("downloadFinished"));
                                        taskManager.stopButton.setVisible(false);
                                        taskManager.playButton.setVisible(true);
                                        taskManager.playButton.removeMouseListener(taskManager.playButton.getMouseListeners()[0]);
                                        taskManager.playButton.addMouseListener(taskManager.playButtonDownloadFinishedBehavior());
                                    } else {
                                        int cut = numOfImages - ((numOfPages - 1) * 40);
                                        originalNumOfImages = numOfImages;
                                        numOfImages -= onDisk;
                                        finalPath = xml.getContentById("GHoutput") + System.getProperty("file.separator") + display
                                                + System.getProperty("file.separator");

                                        taskManager.progressBar.setIndeterminate(false);
                                        taskManager.progressBar.setMinimum(0);
                                        taskManager.progressBar.setMaximum(numOfImages);
                                        taskManager.progressBar.setStringPainted(true);
                                        taskManager.progressBar.setString("0%");
                                        taskManager.infoDisplay.setText(language.getContentById("downloading").replace("&num", "" + numOfImages));

                                        executor = Executors.newFixedThreadPool(Integer.parseInt(xml.getContentById("simult")));
                                        executor.awaitTermination(0L, TimeUnit.SECONDS);

                                        for (int c = 0; c < numOfPages; c++) {
                                            if (isTerminated) {
                                                break;
                                            }

                                            HtmlPage conn = webClient.getPage(link);
                                            Document check = Jsoup.parse(conn.asXml());

                                            if (check.toString().contains("This gallery has been flagged as")) {
                                                link += "?nw=always";
                                                webClient.getPage(link);
                                            }

                                            conn = webClient.getPage(link + "?p=" + c);
                                            Document docLinks = Jsoup.parse(conn.asXml());
                                            Elements getLinks = docLinks.select("div .gdtm a");

                                            for (int i = 0; i < 40; i++) {
                                                while (isPaused) {
                                                    sleep(2);
                                                }

                                                if (c == numOfPages) {
                                                    if (i == cut) {
                                                        break;
                                                    }
                                                }

                                                if (count <= (originalNumOfImages - onDisk)) {
                                                    String temp = getLinks.get(i).toString();
                                                    temp = temp.substring(9);
                                                    temp = temp.substring(0, temp.indexOf("\""));
                                                    Document docImages = Jsoup.connect(temp).get();
                                                    Elements getImages = docImages.select("#img");

                                                    temp = getImages.get(0).toString();
                                                    temp = temp.substring(19);
                                                    temp = temp.substring(0, temp.indexOf("\""));

                                                    if (isTerminated) {
                                                        break;
                                                    } else {
                                                        executor.execute(new ImageExtractor(temp, count));
                                                    }
                                                }
                                                count++;
                                            }
                                        }
                                    }
                                } catch (IOException ex) {
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(UpdateGalleryHentai.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }.start();
                    }
                }
            });
        } else {
            taskManager.progressBar.setIndeterminate(false);
            taskManager.infoDisplay.setText(language.getContentById("taskError"));
            taskManager.playButton.setVisible(true);
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

        try {
            artists.saveXml();
        } catch (IOException ex) {
            Logger.getLogger(UpdateGalleryHentai.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            download();
            artists.saveXml();
        } catch (Exception ex) {
            Logger.getLogger(UpdateGalleryHentai.class.getName()).log(Level.SEVERE, null, ex);
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
            if (!Boolean.parseBoolean(xml.getContentById("GHadvancedNaming"))) {
                imageName = finalLink.substring(finalLink.lastIndexOf("/"), finalLink.length());
            } else if (Integer.parseInt(xml.getContentById("GHnamingOption")) == 0) {
                imageName = downloadNumber + finalLink.substring(finalLink.lastIndexOf("."));
            } else if (Integer.parseInt(xml.getContentById("GHnamingOption")) == 1) {
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
                    artists.setContentByName("imageCount", tagOcc + 1, "" + (originalNumOfImages - numOfImages));

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
                    artists.setContentByName("imageCount", tagOcc + 2, "" + files.list().length);
                    artists.saveXml();
                }
            } catch (java.net.ConnectException ex) {
                executor.submit(new ImageExtractor(finalLink, downloadNumber));
            } catch (MalformedURLException ex) {
                Logger.getLogger(UpdateGalleryHentai.class.getName()).log(Level.SEVERE, null, ex);
            } catch (java.net.SocketException ex) {
                executor.submit(new ImageExtractor(finalLink, downloadNumber));
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(UpdateGalleryHentai.class.getName()).log(Level.SEVERE, null, ex);
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

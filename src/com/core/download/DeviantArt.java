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
package com.core.download;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.panels.main.DownloadTaskJPanel;
import com.util.crypto.PasswordManager;
import com.util.xml.XmlManager;
import java.applet.AudioClip;
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
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class DeviantArt extends BasicCore {

    private int buttonPressedResult;
    private int numOfImages;
    private int numOfPages = 0;
    private int originalNumOfImages;
    private int tagOcc;
    private boolean isPaused = false;
    private boolean isTerminated = false;
    private boolean isDownloading = false;
    private String finalPath;
    private String link;
    private HtmlPage uncensoredLink;
    private WebClient webClient;
    private XmlManager xml;
    private XmlManager language;
    private XmlManager artists;
    private PasswordManager pass;
    private ExecutorService executor;
    private AudioClip sucess;
    private AudioClip fail;
    private DownloadTaskJPanel taskManager;
    int helper = 0;

    public DeviantArt(String url, DownloadTaskJPanel taskManager) {
        link = url;
        this.taskManager = taskManager;

        link = link.substring(0, link.lastIndexOf("/")) + "%2F&offset=";

        webClient = new WebClient(BrowserVersion.BEST_SUPPORTED);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setAppletEnabled(false);

        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);

        xml = new XmlManager();
        language = new XmlManager();
        artists = new XmlManager();
        pass = new PasswordManager();

        xml.loadFile("config\\options.xml");
        String temp = xml.getContentByName("language", 0);
        temp = temp.substring(0, temp.indexOf(","));
        language.loadFile("language\\" + temp.toLowerCase() + ".xml");

        try {
            File artistsXml = new File(xml.getContentById("DAoutput") + "\\artists-log.xml");
            if (!artistsXml.exists()) {
                artists.createFile(artistsXml.getAbsolutePath());
            } else {
                artists.loadFile(artistsXml);
            }

            String artist = link.substring(7, link.indexOf("."));
            artist = artist.substring(0, 1).toUpperCase() + artist.substring(1);
            finalPath = xml.getContentById("DAoutput") + "\\" + artist;
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
                numOfImages = 0;
                while (true) {
                    conn = webClient.getPage(link + numOfImages);

                    if (conn.asXml().contains("This section has no deviations yet!")) {
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
        if (artists.checkIfTagExists("name", artist)) {
            File getImages = new File(xml.getContentById("DAoutput") + "\\" + artist);
            int older = getImages.listFiles().length;

            if (older == numOfImages) {
                JOptionPane.showMessageDialog(null, "The artist \"" + artist + "\" is already up to date. No need of an download");
                return false;
            }

            String[] texts = language.getContentById("artistExists").replace("&string", artist)
                    .replace("&num", "" + (numOfImages - older)).split("&br");
            int result = JOptionPane.showConfirmDialog(null, texts[0] + "\n" + texts[1], language.getContentById("genericInfoTitle"),
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (result == JOptionPane.YES_OPTION) {
                taskManager.setNewTaskType(DownloadTaskJPanel.UPDATE_TASK);
                new UpdateFurAffinity(link, taskManager).start();
                return false;
            } else {
                originalNumOfImages = numOfImages;
                tagOcc = artists.getTagIndex("name", artist);
                artists.setContentByName("imageCount", tagOcc, "" + numOfImages);
                artists.saveXml();
                return true;
            }
        } else {
            originalNumOfImages = numOfImages;
            artists.addSubordinatedTag("artist", "root", 0);
            tagOcc = artists.getAllContentsByName("artist").size() - 1;
            artists.addSubordinatedTag("name", "artist", tagOcc);
            artists.setContentByName("name", tagOcc, artist);
            artists.addSubordinatedTag("imageCount", "artist", tagOcc);
            artists.setContentByName("imageCount", tagOcc, "" + numOfImages);
            artists.saveXml();

            return true;
        }
    }

    private void download() throws Exception {
        String artist = link.substring(7, link.indexOf("."));
        final String artistFinal = artist;
        artist = artist.substring(0, 1).toUpperCase() + artist.substring(1);
        taskManager.author.setText(artist + " | DeviantArt");

        if (getInformationAboutGallery() && checkArtistExistance()) {
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

                                    int cut = numOfImages - ((numOfPages - 1) * 24);
                                    int c = 0;
                                    String before = "";
                                    boolean hasFormBeenSend = false;
                                    executor = Executors.newFixedThreadPool(Integer.parseInt(xml.getContentById("simult")));

                                    while (c <= numOfImages) {
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
                                            if (c + cut == numOfImages) {
                                                break;
                                            }

                                            String temp = getPages.get(i).toString();
                                            temp = temp.substring(temp.indexOf("href"));
                                            temp = temp.substring(6, temp.indexOf("\"", 6));

                                            if (!before.equals(temp) && !temp.contains("#comments")) {
                                                before = temp;
                                                HtmlPage page = webClient.getPage(temp);

                                                if (page.asText().contains("This content is intended for mature audiences.")
                                                        && !hasFormBeenSend) {
                                                    hasFormBeenSend = true;
                                                    webClient.getOptions().setJavaScriptEnabled(true);
                                                    page = webClient.getPage(page.getBaseURL());
                                                    HtmlInput month = (HtmlInput) page.getHtmlElementById("month");
                                                    HtmlInput day = (HtmlInput) page.getHtmlElementById("day");
                                                    HtmlInput year = (HtmlInput) page.getHtmlElementById("year");
                                                    HtmlCheckBoxInput accept = page.getHtmlElementById("agree_tos");
                                                    HtmlInput send = (HtmlInput) page.getFirstByXPath("//input[@class='submitbutton smbutton smbutton-green smbutton-size-default disabledbutton']");

                                                    int monthR = 0, dayR = 0, yearR = 0;
                                                    Random random = new Random();

                                                    while (monthR == 0) {
                                                        monthR = random.nextInt(12);
                                                    }
                                                    while (dayR == 0) {
                                                        dayR = random.nextInt(27);
                                                    }
                                                    while (yearR < 1980) {
                                                        yearR = random.nextInt(1996);
                                                    }

                                                    month.setValueAttribute("" + monthR);
                                                    day.setValueAttribute("" + dayR);
                                                    year.setValueAttribute("" + yearR);
                                                    accept.setChecked(true);

                                                    HtmlPage page2 = send.click();
                                                    page = page2;
                                                }

                                                Document docImages = Jsoup.parse(page.asXml());
                                                Elements getLinks = docImages.select("img[data-embed-type~=deviation]");
                                                temp = getLinks.toString();
                                                if (temp.contains("src")) {
                                                    temp = temp.substring(temp.indexOf("src") + 5);
                                                    temp = temp.substring(0, temp.indexOf("\""));

                                                    if (isTerminated) {
                                                        break;
                                                    } else {
                                                        executor.execute(new ImageExtractor(temp));
                                                    }
                                                }
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
        executor.shutdown();
    }

    @Override
    public void run() {
        try {
            download();
        } catch (Exception ex) {
            Logger.getLogger(DeviantArt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<String> failed = new ArrayList<>();

    private class ImageExtractor implements Runnable {

        String finalLink;

        public ImageExtractor(String finalLink) {
            this.finalLink = finalLink;
        }

        private boolean download() {
            String imageName;
            if (!Boolean.parseBoolean(xml.getContentById("FAadvancedNaming"))) {
                imageName = finalLink.substring(finalLink.lastIndexOf("/"), finalLink.length());
            } else {
                imageName = finalLink.substring(finalLink.lastIndexOf("/"), finalLink.length());
            }

            try {
                URL imageURL = new URL(finalLink);
                InputStream inputImg = imageURL.openStream();
                OutputStream imageFile = new FileOutputStream(finalPath + "\\" + imageName);
                BufferedOutputStream writeImg = new BufferedOutputStream(imageFile);

                NumberFormat nf = NumberFormat.getNumberInstance();
                nf.setMaximumFractionDigits(1);
                nf.setGroupingUsed(true);

                int bytes;
                while ((bytes = inputImg.read()) != -1) {
                    while (isPaused) {
                        sleep(2);
                    }
                    writeImg.write(bytes);
                }

                writeImg.close();
                imageFile.close();
                inputImg.close();

                numOfImages--;
                if (!isTerminated) {
                    taskManager.infoDisplay.setText(language.getContentById("downloading").replace("&num", "" + numOfImages));
                }
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

            } catch (java.net.ConnectException ex) {
                failed.add(link);
                System.out.println("An error has happen while download the gallery. The task returned " + failed.size()
                        + " failed downloads.");
            } catch (MalformedURLException ex) {
                Logger.getLogger(DeviantArt.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(DeviantArt.class.getName()).log(Level.SEVERE, null, ex);
            }

            return false;
        }

        @Override
        public void run() {
            download();
        }
    }
}

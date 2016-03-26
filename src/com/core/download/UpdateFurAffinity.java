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
package com.core.download;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.panels.main.DownloadTaskJPanel;
import com.util.crypto.PasswordManager;
import com.util.xml.XmlManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.Thread.sleep;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class UpdateFurAffinity extends BasicCore {

    private boolean isPaused = false;
    private boolean isTerminated = false;
    private boolean isDownloading = false;
    private int tagOcc;
    private int numOfImages;
    private int originalNumOfImages;
    private int numOfPages = 0;
    private String finalPath;
    private final String link;
    private HtmlPage uncensoredLink;
    private final WebClient webClient;
    private ExecutorService executor;
    private final XmlManager xml;
    private final XmlManager language;
    private final XmlManager artists;
    private final PasswordManager pass;
    private final DownloadTaskJPanel taskManager;

    public UpdateFurAffinity(String url, DownloadTaskJPanel taskManager) {
        link = url;
        this.taskManager = taskManager;

        this.taskManager.playButton.setVisible(false);
        this.taskManager.progressBar.setIndeterminate(true);

        webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setAppletEnabled(false);

        xml = new XmlManager();
        language = new XmlManager();
        artists = new XmlManager();
        pass = new PasswordManager();

        xml.loadFile("config\\options.xml");
        String temp = xml.getContentByName("language", 0);
        temp = temp.substring(0, temp.indexOf(","));
        language.loadFile("language\\" + temp.toLowerCase() + ".xml");

        artists.loadFile(xml.getContentById("FAoutput") + "\\artists-log.xml");
    }

    private boolean submittingForm() {
        try {
            taskManager.infoDisplay.setText(language.getContentById("loginIn"));
            HtmlPage page1 = webClient.getPage("https://www.furaffinity.net/login/");
            HtmlForm form = page1.getFirstByXPath("//form [@method='post']");

            HtmlTextInput usernameField = form.getInputByName("name");
            HtmlPasswordInput passwordField = form.getInputByName("pass");
            HtmlSubmitInput button = form.getInputByName("login");

            String user = pass.decrypt(pass.stringToByte(xml.getContentById("FAuser")), "12345678".getBytes(), "12345678".getBytes());
            String passw = pass.decrypt(pass.stringToByte(xml.getContentById("FApass")), "12345678".getBytes(), "12345678".getBytes());

            usernameField.setValueAttribute(user.trim());
            passwordField.setValueAttribute(passw.trim());

            HtmlPage page2 = button.click();

            if (page2.getUrl().toString().equals("https://www.furaffinity.net/login/?msg=1")) {
                JOptionPane.showMessageDialog(null, language.getContentById("loginFailedFA"), language.getContentById("genericErrorTitle"), JOptionPane.OK_OPTION);
                return false;
            }

            String temp = page2.getUrl() + link.substring(27);

            uncensoredLink = webClient.getPage(temp);
            return true;

        } catch (java.net.UnknownHostException ex) {
            JOptionPane.showMessageDialog(null, language.getContentById("internetDroppedOut"), language.getContentById("genericErrorTitle"), JOptionPane.OK_OPTION);
        } catch (IOException ex) {
            Logger.getLogger(FurAffinity.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    private boolean getInformationAboutGallery() throws IOException {
        try {
            taskManager.infoDisplay.setText(language.getContentById("getImages"));
            HtmlPage conn = webClient.getPage(uncensoredLink.getUrl());

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

    private void download() throws IOException {
        String artist = link.substring(35, link.lastIndexOf("/"));
        artist = artist.substring(0, 1).toUpperCase() + artist.substring(1);
        taskManager.author.setText(artist + " | FurAfinity");
        final String thread = artist;

        if (submittingForm() && getInformationAboutGallery()) {
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
                                    tagOcc = artists.getTagIndex("name", thread);
                                    int onDisk = Integer.parseInt(artists.getContentByName("imageCount", tagOcc + 1));

                                    if (onDisk == numOfImages) {
                                        JOptionPane.showMessageDialog(null, "The artist \"" + thread + "\" is already up to date. No need of an download");
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
                                        finalPath = xml.getContentById("FAoutput") + "\\" + thread;

                                        taskManager.progressBar.setIndeterminate(false);
                                        taskManager.progressBar.setMinimum(0);
                                        taskManager.progressBar.setMaximum(numOfImages);
                                        taskManager.progressBar.setStringPainted(true);
                                        taskManager.progressBar.setString("0%");
                                        taskManager.infoDisplay.setText(language.getContentById("downloading").replace("&num", "" + numOfImages));

                                        executor = Executors.newFixedThreadPool(Integer.parseInt(xml.getContentById("simult")));

                                        for (int c = 1; c <= numOfPages; c++) {
                                            if (isTerminated) {
                                                break;
                                            }

                                            HtmlPage conn = webClient.getPage(uncensoredLink.getUrl().toString() + c + "/");
                                            Document docLinks = Jsoup.parse(conn.asXml());
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

                                                if (count <= onDisk) {
                                                    String temp = getPages.get(i).toString();
                                                    int end = temp.indexOf("/", 15);
                                                    temp = "http://www.furaffinity.net" + temp.substring(9, end);
                                                    conn = webClient.getPage(temp);
                                                    Document docImages = Jsoup.parse(conn.asXml());
                                                    Elements getLinks = docImages.select("a[href~=//d.facdn.net/art/]");
                                                    String tempImagesArray = getLinks.toString();
                                                    end = tempImagesArray.indexOf("\"", 10);
                                                    temp = "http://" + tempImagesArray.substring(11, end);

                                                    if (isTerminated) {
                                                        break;
                                                    } else {
                                                        executor.execute(new ImageExtractor(temp));
                                                    }
                                                }
                                                count++;
                                            }
                                        }
                                    }
                                } catch (IOException ex) {

                                } catch (InterruptedException ex) {
                                    Logger.getLogger(UpdateFurAffinity.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }.start();
                    }
                }
            });
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
        executor.shutdownNow();
        try {
            artists.saveXml();
        } catch (IOException ex) {
            Logger.getLogger(FurAffinity.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(FurAffinity.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(FurAffinity.class.getName()).log(Level.SEVERE, null, ex);
            }

            return false;
        }

        @Override
        public void run() {
            download();
        }
    }
}

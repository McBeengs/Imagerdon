/* **********   ImageExtractor.java   **********
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

import com.panels.main.DownloadTaskJPanel;
import com.util.UsefulMethods;
import com.util.xml.XmlManager;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.Thread.sleep;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;

public class ImageExtractor implements Runnable {

    private String artistName;
    private String link;
    private String imageName;
    private int server;
    private int downloadNum;
    private int numOfImages;
    private DownloadTaskJPanel taskManager;
    private boolean isPaused = false;
    private boolean isTerminated = false;
    private String savePath;

    public ImageExtractor(int server, String artistName, String link, int numOfImages, int downloadNumber,
            DownloadTaskJPanel taskManager, String savePath) {
        this.server = server;
        this.artistName = artistName;
        this.link = link;
        this.downloadNum = downloadNumber;
        this.numOfImages = numOfImages;
        this.taskManager = taskManager;
        this.savePath = savePath;

        String s;
        XmlManager xml = UsefulMethods.loadManager(UsefulMethods.OPTIONS);

        switch (server) {
            case 0:
                s = "DA";
                break;
            case 1:
                s = "TU";
                break;
            case 2:
                s = "FA";
                break;
            case 3:
                s = "E621";
                break;
            default:
                throw new IndexOutOfBoundsException();
        }

        if (!Boolean.parseBoolean(xml.getContentById(s + "advancedNaming"))) {
            imageName = link.substring(link.lastIndexOf("/"), link.length());
        } else if (Integer.parseInt(xml.getContentById(s + "namingOption")) == 0) {
            imageName = downloadNumber + link.substring(link.lastIndexOf("."));
        } else if (Integer.parseInt(xml.getContentById(s + "namingOption")) == 1) {
            imageName = UsefulMethods.getComplexDateFormat() + link.substring(link.lastIndexOf("."));
        }
    }

    @Override
    public void run() {
        try {
            XmlManager language = UsefulMethods.loadManager(UsefulMethods.LANGUAGE);
            URL imageURL = new URL(link);
            HttpURLConnection http = (HttpURLConnection) imageURL.openConnection();
            http.addRequestProperty("User-Agent", "Mozilla/4.76");
            InputStream inputImg = http.getInputStream();
            OutputStream imageFile = new FileOutputStream(savePath + File.separator + imageName);
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

            if (!isTerminated) {
                taskManager.progressBar.setValue(taskManager.progressBar.getValue() + 1);
                taskManager.infoDisplay.setText(language.getContentById("downloading").replace("&num", ""
                        + (taskManager.progressBar.getMaximum() - taskManager.progressBar.getValue())));
                String show = nf.format(taskManager.progressBar.getPercentComplete() * 100) + "%";
                taskManager.progressBar.setString(show);

                Connection conn = UsefulMethods.getDBInstance();
                conn.createStatement().execute("UPDATE artist SET image_count = " + numOfImages + " WHERE name = '" + artistName + "' AND server = " + server);

                if (show.equals("100%")) {
                    taskManager.stopButton.setVisible(false);
                    taskManager.infoDisplay.setText(language.getContentById("downloadFinished"));

                    for (java.awt.event.MouseListener listener : taskManager.playButton.getMouseListeners()) {
                        taskManager.playButton.removeMouseListener(listener);
                    }

                    taskManager.playButton.addMouseListener(taskManager.playButtonDownloadFinishedBehavior());
                }
            } else {
                File files = new File(savePath + File.separator + imageName);
                files.delete();

                files = new File(savePath);
                Connection conn = UsefulMethods.getDBInstance();
                conn.createStatement().execute("UPDATE artist SET image_count = " + files.list().length + " WHERE name = '" + artistName + "' AND server = " + server);
            }
        } catch (java.net.ConnectException ex) {
            new ImageExtractor(server, artistName, link, numOfImages, downloadNum, taskManager, savePath);
        } catch (MalformedURLException ex) {
            new ImageExtractor(server, artistName, link, numOfImages, downloadNum, taskManager, savePath);
        } catch (java.net.SocketException ex) {
            new ImageExtractor(server, artistName, link, numOfImages, downloadNum, taskManager, savePath);
        } catch (IOException | InterruptedException ex) {
            new ImageExtractor(server, artistName, link, numOfImages, downloadNum, taskManager, savePath);
        } catch (SQLException ex) {

        }
    }

    public void pause() {
        isPaused = true;
    }

    public void play() {
        isPaused = false;
    }

}

/* **********   CustomBatchDownload.java   **********
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
import com.panels.tools.BatchDownloads;
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

public class CustomBatchDownload extends BasicCore {
    
    private final XmlManager language;
    private boolean isPaused = false;
    private boolean isTerminated = false;
    private boolean isDownloading = false;
    private ExecutorService executor;
    private final String[] links;
    private String finalPath;
    private int numOfImages;
    private int count = 0;
    private final DownloadTaskJPanel taskManager;
    
    public CustomBatchDownload(String[] urls, DownloadTaskJPanel taskManager) {
        links = urls;
        this.taskManager = taskManager;
        language = UsefulMethods.loadManager(UsefulMethods.LANGUAGE);
    }
    
    private void download() {
        taskManager.infoDisplay.setText(language.getContentById("wentOK"));
        taskManager.playButton.setVisible(true);
        taskManager.progressBar.setIndeterminate(false);
        executor = Executors.newFixedThreadPool(5);
        
        taskManager.playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                NumberFormat nf = NumberFormat.getNumberInstance();
                nf.setMaximumFractionDigits(1);
                nf.setGroupingUsed(true);
                
                for (final String link : links) {
                    count++;
                    Thread save = new Thread() {
                        @Override
                        public void run() {
                            try {
                                taskManager.progressBar.setIndeterminate(false);
                                taskManager.progressBar.setMinimum(0);
                                taskManager.progressBar.setMaximum(links.length);
                                taskManager.progressBar.setStringPainted(true);
                                taskManager.progressBar.setString("0%");
                                taskManager.infoDisplay.setText(language.getContentById("downloading").replace("&num", "" + links.length));
                                isDownloading = true;
                                numOfImages = links.length;
                                
                                String imageName = link.substring(link.lastIndexOf("/") + 1);
                                URL imageURL = new URL(link);
                                InputStream inputImg = imageURL.openStream();
                                OutputStream imageFile = new FileOutputStream(finalPath + File.separator + imageName);
                                BufferedOutputStream writeImg = new BufferedOutputStream(imageFile);
                                
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
                            } catch (MalformedURLException ex) {
                                JOptionPane.showMessageDialog(null, language.getContentById("batchLineError").replace("&num", "" + count),
                                        language.getContentById("genericErrorTitle"), JOptionPane.ERROR_MESSAGE);
                            } catch (IOException | InterruptedException ex) {
                                Logger.getLogger(BatchDownloads.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    };
                    
                    executor.execute(save);
                    
                    if (!isTerminated) {
                        taskManager.infoDisplay.setText(language.getContentById("downloading").replace("&num", "" + numOfImages));
                        taskManager.progressBar.setValue(taskManager.progressBar.getValue() + 1);
                        
                        String show = nf.format(taskManager.progressBar.getPercentComplete() * 100) + "%";
                        taskManager.progressBar.setString(show);
                        
                        if (show.equals("100%")) {
                            taskManager.stopButton.setVisible(false);
                            taskManager.infoDisplay.setText(language.getContentById("downloadFinished"));
                            
                            for (java.awt.event.MouseListener listener : taskManager.playButton.getMouseListeners()) {
                                taskManager.playButton.removeMouseListener(listener);
                            }
                            
                            taskManager.playButton.addMouseListener(taskManager.playButtonDownloadFinishedBehavior());
                        }
                    }
                }
            }
        });
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
            Logger.getLogger(BatchDownloads.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

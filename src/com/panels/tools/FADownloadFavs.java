package com.panels.tools;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.panels.main.DownloadTaskJPanel;
import com.panels.main.StylizedMainJFrame;
import com.panels.main.StylizedMainJFrame.AddTask;
import com.util.crypto.PasswordManager;
import com.util.UsefulMethods;
import com.util.xml.XmlManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class FADownloadFavs extends javax.swing.JFrame {

    private final XmlManager xml;
    private final XmlManager language;
    private final PasswordManager pass;
    private final WebClient webClient;
    private final DefaultTableModel model;
    private HtmlPage uncensoredLink;
    private final ArrayList artists;
    private final ArrayList<String> artistsLinks;
    private String user;
    private int numOfPages = 1;

    public FADownloadFavs() {
        xml = UsefulMethods.loadManager(UsefulMethods.OPTIONS);
        language = UsefulMethods.loadManager(UsefulMethods.LANGUAGE);
        pass = new PasswordManager();

        artists = new ArrayList<>();
        artistsLinks = new ArrayList<>();

        webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setAppletEnabled(false);

        initComponents();
        model = (DefaultTableModel) table.getModel();

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                button.setEnabled(false);
                button.removeMouseListener(this);
                displayer.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/spinner.gif")));
                displayer.setForeground(java.awt.Color.BLACK);
                displayer.setText(language.getContentById("loginIn"));

                new Thread("Submitting Form") {
                    @Override
                    public void run() {
                        if (submittingForm()) {
                            try {
                                displayer.setText("Done checking... " + getRepeted() + " removed from favs");

                                int res = JOptionPane.showConfirmDialog(null, "All ready. Click \"Yes\" for send "
                                        + artists.size() + " new tasks for the main screen.", "Ready", JOptionPane.YES_NO_OPTION,
                                        JOptionPane.INFORMATION_MESSAGE);

                                if (res == JOptionPane.YES_OPTION) {
                                    sendTasks();
                                }

                            } catch (IOException ex) {
                                Logger.getLogger(FADownloadFavs.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }.start();
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        displayer = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        displayer.setText(language.getContentById("checkIntegridy"));

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Artist Name", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setEnabled(false);
        table.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(table);

        button.setText(language.getContentById("checkButton"));
        button.setFocusable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(displayer, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(button, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(displayer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private boolean submittingForm() {
        try {
            HtmlPage page1 = webClient.getPage("https://www.furaffinity.net/login/");
            HtmlForm form = page1.getFirstByXPath("//form [@method='post']");

            HtmlTextInput usernameField = form.getInputByName("name");
            HtmlPasswordInput passwordField = form.getInputByName("pass");
            HtmlSubmitInput submitButton = form.getInputByName("login");

            user = pass.decrypt(pass.stringToByte(xml.getContentById("FAuser")), "12345678".getBytes(), "12345678".getBytes());
            String passw = pass.decrypt(pass.stringToByte(xml.getContentById("FApass")), "12345678".getBytes(), "12345678".getBytes());

            usernameField.setValueAttribute(user.trim());
            passwordField.setValueAttribute(passw.trim());

            HtmlPage page2 = submitButton.click();

            if (page2.getUrl().toString().equals("https://www.furaffinity.net/login/?msg=1")) {
                JOptionPane.showMessageDialog(null, language.getContentById("loginFailed").replace("&string", "FurAffinity"),
                        "Error", JOptionPane.OK_OPTION);
                return false;
            }

            String temp = page2.getUrl() + "favorites/" + user.toLowerCase().trim() + "/";

            uncensoredLink = webClient.getPage(temp);
            return true;

        } catch (FailingHttpStatusCodeException ex) {
            JOptionPane.showMessageDialog(null, "Connection with the Internet has dropped", "Error", JOptionPane.OK_OPTION);
        } catch (IOException ex) {
            Logger.getLogger(FADownloadFavs.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    private int getRepeted() throws IOException {
        int d = 0;
        displayer.setText(language.getContentById("getRepeted"));

        while (true) {
            HtmlPage page = webClient.getPage(uncensoredLink.getUrl().toString() + numOfPages + "/");

            if (page.asXml().contains("There are no submissions to list") == true) {
                break;
            }

            Document doc = Jsoup.parse(page.asXml());
            Elements get = doc.select("a[href~=/user/]");

            for (int j = 0; j < get.size(); j++) {
                String ex = get.get(j).toString();
                String temp = ex.substring(ex.indexOf(">") + 2, ex.indexOf("<", 4)).trim();

                if (!temp.equals("~" + user.trim()) && !temp.equals("User Page") && !temp.equals("")) {
                    if (!artists.contains(temp)) {
                        artists.add(temp);
                        artistsLinks.add("http://www.furaffinity.net/gallery/" + ex.substring(ex.indexOf("/", 11) + 1, ex.lastIndexOf("\"")));
                    } else {
                        addRow(temp, d + 1);
                    }

                    d++;
                }
            }

            numOfPages++;
        }

        displayer.setIcon(null);

        return model.getRowCount();
    }

    private void addRow(String artist, int index) {
        model.addRow(new Object[]{artist, "Duplicated on slot " + index});
        removeRepeated(index);
    }

    private void removeRepeated(final int index) {
        new Thread("Removing slot nº" + index) {
            @Override
            public void run() {
                CloneWebClient clone = new CloneWebClient(webClient);

                int decoy = 0;
                boolean isFinished = false;
                WebClient drop = clone.getClone();

                try {
                    for (int i = 1; i <= numOfPages; i++) {
                        if (isFinished) {
                            break;
                        }

                        HtmlPage page = drop.getPage("http://www.furaffinity.net/favorites/" + user.trim() + "/" + i);
                        Document doc = Jsoup.parse(page.asXml());
                        Elements get = doc.select("a[href~=/view/]");

                        for (int j = 0; j < get.size(); j++) {
                            decoy++;
                            if (decoy == index) {
                                String temp = get.get(j).toString();
                                temp = temp.substring(temp.indexOf("/"), temp.indexOf("/", 16)) + "/";

                                HtmlPage erase = drop.getPage("http://www.furaffinity.net" + temp);
                                doc = Jsoup.parse(erase.asXml());
                                get = doc.select("a[href~=/fav/]");

                                temp = get.get(0).toString();
                                temp = "http://www.furaffinity.net" + temp.substring(temp.indexOf("\"") + 1, temp.indexOf("\"", 10));

                                drop.getPage(temp);

                                isFinished = true;
                                break;
                            }
                        }
                    }
                } catch (IOException | FailingHttpStatusCodeException ex) {
                    Logger.getLogger(FADownloadFavs.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
    }

    private void sendTasks() throws IOException {
        new Thread() {
            @Override
            public void run() {
                StylizedMainJFrame.ADD_TASK.setAutoStart(true);
                
                for (int i = 0; i < artistsLinks.size(); i++) {
                    StylizedMainJFrame.ADD_TASK.addTask(artistsLinks.get(0), DownloadTaskJPanel.FUR_AFFINITY, DownloadTaskJPanel.DOWNLOAD_TASK);
                    artistsLinks.remove(0);
                    try {
                        sleep(20000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FADownloadFavs.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                StylizedMainJFrame.ADD_TASK.setAutoStart(false);
            }
        }.start();

        JButton leave = new JButton("Just leave");
        JButton remove = new JButton("Remove favs");

        leave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                exit();
            }
        });

        remove.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                removeAllFavs();
            }
        });

        Object[] teste = {leave, remove};
        JOptionPane.showOptionDialog(this, "The tasks will be added once every 20 seconds. You can either close this window\n"
                + " now or choose to remove all favs from the server before leaving", "Info", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, teste, JOptionPane.CLOSED_OPTION);
    }

    private void removeAllFavs() {
        int res = JOptionPane.showConfirmDialog(null, "This cannot be undone, are you sure?", "Warning", JOptionPane.YES_NO_OPTION);

        if (res == JOptionPane.YES_OPTION) {
            JLabel removing = new JLabel("Removing");
            removing.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/spinner.gif")));
            JOptionPane.showMessageDialog(null, removing);
            new Thread("Removing all favorites") {
                @Override
                public void run() {
                    CloneWebClient clone = new CloneWebClient(webClient);
                    WebClient drop = clone.getClone();

                    try {
                        for (int i = 1; i <= numOfPages; i++) {
                            HtmlPage page = drop.getPage("http://www.furaffinity.net/favorites/" + user.trim() + "/" + i);
                            Document doc = Jsoup.parse(page.asXml());
                            Elements get = doc.select("a[href~=/view/]");

                            for (int j = 0; j < get.size(); j++) {
                                String temp = get.get(j).toString();
                                temp = temp.substring(temp.indexOf("/"), temp.indexOf("/", 16)) + "/";

                                HtmlPage erase = drop.getPage("http://www.furaffinity.net" + temp);
                                doc = Jsoup.parse(erase.asXml());
                                get = doc.select("a[href~=/fav/]");

                                temp = get.get(0).toString();
                                temp = "http://www.furaffinity.net" + temp.substring(temp.indexOf("\"") + 1, temp.indexOf("\"", 10));

                                drop.getPage(temp);
                            }
                        }
                    } catch (IOException | FailingHttpStatusCodeException ex) {
                        Logger.getLogger(FADownloadFavs.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }.start();

            exit();
        }
    }

    private javax.swing.JFrame getPanel() {
        return this;
    }

    private void exit() {
        this.dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button;
    private javax.swing.JLabel displayer;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables

    private class CloneWebClient {

        private final WebClient web;

        public CloneWebClient(WebClient webClient) {
            web = webClient;
        }

        public WebClient getClone() {
            return web;
        }
    }

}

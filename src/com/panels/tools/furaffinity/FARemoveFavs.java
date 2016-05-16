/* **********   FARemoveFavs.java   **********
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
package com.panels.tools.furaffinity;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.util.UsefulMethods;
import com.util.crypto.PasswordManager;
import com.util.xml.XmlManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class FARemoveFavs {

    public FARemoveFavs() {
        int res = JOptionPane.showConfirmDialog(null, "This cannot be undone, are you sure?", "Warning", JOptionPane.YES_NO_OPTION);

        if (res == JOptionPane.YES_OPTION) {
            JLabel removing = new JLabel("Removing");
            removing.setIcon(new ImageIcon(getClass().getResource("/com/style/icons/spinner.gif")));
            JOptionPane.showMessageDialog(null, removing);
            new Thread("Removing all FA favorites") {
                @Override
                public void run() {
                    try {
                        PasswordManager pass = new PasswordManager();
                        XmlManager xml = UsefulMethods.loadManager(UsefulMethods.OPTIONS);
                        String user = pass.decrypt(pass.stringToByte(xml.getContentById("FAuser")), "12345678".getBytes(), "12345678".getBytes());
                        
                        while (!UsefulMethods.isWebClientReady()) {
                            sleep(2);
                        }
                        WebClient get = UsefulMethods.getWebClientInstance();

                        HtmlPage page;
                        int i = 1;

                        while (true) {
                            page = get.getPage("http://www.furaffinity.net/favorites/" + user.trim() + "/" + i);

                            if (page.getBody().toString().contains("There are no submissions to list")) {
                                break;
                            }

                            Document doc = Jsoup.parse(page.asXml());
                            Elements select = doc.select("a[href~=/view/]");

                            for (int j = 0; j < select.size(); j++) {
                                String temp = select.get(j).toString();
                                temp = temp.substring(temp.indexOf("/"), temp.indexOf("/", 16)) + "/";

                                HtmlPage erase = get.getPage("http://www.furaffinity.net" + temp);
                                doc = Jsoup.parse(erase.asXml());
                                select = doc.select("a[href~=/fav/]");

                                temp = select.get(0).toString();
                                temp = "http://www.furaffinity.net" + temp.substring(temp.indexOf("\"") + 1, temp.indexOf("\"", 10));

                                get.getPage(temp);
                            }
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(FARemoveFavs.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }.start();
        }
    }
}

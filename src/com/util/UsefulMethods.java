/* **********   GetOptions.java   **********
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
package com.util;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.IncorrectnessListener;
import com.gargoylesoftware.htmlunit.InteractivePage;
import com.gargoylesoftware.htmlunit.ScriptException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HTMLParserListener;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;
import com.util.crypto.PasswordManager;
import com.util.xml.XmlManager;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import net.java.balloontip.BalloonTip;
import net.java.balloontip.styles.MinimalBalloonStyle;
import net.java.balloontip.utils.FadingUtils;
import net.java.balloontip.utils.TimingUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.LogFactory;
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.CSSParseException;
import org.w3c.css.sac.ErrorHandler;

public class UsefulMethods implements Serializable {

    public static final int OPTIONS = 0;
    public static final int LANGUAGE = 1;
    private static WebClient webClient;
    private static Connection conn;
    private static XmlManager xml;
    private static XmlManager language;
    private static boolean isWebViewReady = false;

    public static String getOptions() {
        //get OS
        String os = System.getProperty("os.name").toLowerCase();
        String path;

        String separator = System.getProperty("file.separator");

        if (os.contains("win")) {
            path = System.getProperty("user.home") + separator + "Documents" + separator + "Repository" + separator;
        } else if (os.contains("uni") || os.contains("nux") || os.contains("aix")) {
            path = System.getProperty("user.home") + separator + "Repository" + separator;
        } else if (os.contains("mac")) {
            path = System.getProperty("user.home") + "";
        } else {
            JOptionPane.showMessageDialog(null, "Your OS doesn't support Java's JRE 7 or above.");
            return null;
        }

        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<root>\n"
                + "  <optionsPane>\n"
                + "    <gui>\n"
                + "      <language selected=\"0\">English</language>\n"
                + "      <style selected=\"0\">Metal</style>\n"
                + "    </gui>\n"
                + "    <downloads>\n"
                + "      <scroll id=\"simult\">5</scroll>\n"
                + "      <directory id=\"mainOutput\">" + path + "</directory>\n"
                + "      <boolean id=\"sub\">true</boolean>\n"
                + "      <dropdown id=\"existed\">2</dropdown>\n"
                + "      <boolean id=\"update\">true</boolean>\n"
                + "    </downloads>\n"
                + "    <deviantArt>\n"
                + "      <string id=\"DAuser\">110, 126, 122, 83, 72, -100, -91, -14, -65, -68, -60, -72, 81, 71, 64, -60</string>\n"
                + "      <string id=\"DApass\">110, 126, 122, 83, 72, -100, -91, -14, -65, -68, -60, -72, 81, 71, 64, -60</string>\n"
                + "      <directory id=\"DAoutput\">" + path + "DeviantArt" + "</directory>\n"
                + "      <boolean id=\"DAadvancedNaming\">false</boolean>\n"
                + "      <number id=\"DAnamingOption\">0</number>\n"
                + "    </deviantArt>\n"
                + "    <tumblr>\n"
                + "      <string id=\"TUuser\">110, 126, 122, 83, 72, -100, -91, -14, -65, -68, -60, -72, 81, 71, 64, -60</string>\n"
                + "      <string id=\"TUpass\">110, 126, 122, 83, 72, -100, -91, -14, -65, -68, -60, -72, 81, 71, 64, -60</string>\n"
                + "      <directory id=\"TUoutput\">" + path + "Tumblr" + "</directory>\n"
                + "      <boolean id=\"TUadvancedNaming\">false</boolean>\n"
                + "      <number id=\"TUnamingOption\">0</number>\n"
                + "    </tumblr>\n"
                + "    <furAffinity>\n"
                + "      <string id=\"FAuser\">110, 126, 122, 83, 72, -100, -91, -14, -65, -68, -60, -72, 81, 71, 64, -60</string>\n"
                + "      <string id=\"FApass\">110, 126, 122, 83, 72, -100, -91, -14, -65, -68, -60, -72, 81, 71, 64, -60</string>\n"
                + "      <directory id=\"FAoutput\">" + path + "FurAffinity" + "</directory>\n"
                + "      <boolean id=\"FAadvancedNaming\">false</boolean>\n"
                + "      <number id=\"FAnamingOption\">0</number>\n"
                + "    </furAffinity>\n"
                + "    <e621>\n"
                + "      <directory id=\"E621output\">" + path + "e621" + "</directory>\n"
                + "      <boolean id=\"E621advancedNaming\">false</boolean>\n"
                + "      <number id=\"E621namingOption\">0</number>\n"
                + "    </e621>\n"
                + "  </optionsPane>\n"
                + "</root>\n"
                + "";
    }

    @SuppressWarnings("null")
    public static WebClient getWebClientInstance() throws IOException, Exception {
        File get = new File(getClassPath(UsefulMethods.class) + File.separator + "login_cache.obj");

        if (get.exists()) {
            FileInputStream fileIn = new FileInputStream(get);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            CookieManager manager = (CookieManager) in.readObject();
            webClient = new WebClient(BrowserVersion.CHROME);
            webClient.setCookieManager(manager);
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setAppletEnabled(false);

            isWebViewReady = true;
            return new CloneWebClient(shutUpHtmlUnit(webClient)).getClone();
        } else {
            CookieManager manager = new CookieManager();
            WebClient save = new WebClient(BrowserVersion.CHROME);
            save.setCookieManager(manager);
            save.getOptions().setCssEnabled(false);
            save.getOptions().setJavaScriptEnabled(false);
            save.getOptions().setAppletEnabled(false);

            HtmlPage preLogin, posLogin;
            HtmlForm form;
            HtmlTextInput usernameField;
            HtmlPasswordInput passwordField;
            HtmlSubmitInput button;
            PasswordManager pass = new PasswordManager();
            xml = loadManager(OPTIONS);
            String user;
            String passw;

            // DeviantArt login
            preLogin = save.getPage("https://www.deviantart.com/");
            if (preLogin == null) {
                throw new Exception("DeviantArt");
            }
            form = preLogin.getHtmlElementById("form-login");

            usernameField = form.getInputByName("username");
            passwordField = form.getInputByName("password");
            button = form.getInputByName("action");

            user = pass.decrypt(pass.stringToByte(xml.getContentById("DAuser")), "12345678".getBytes(), "12345678".getBytes());
            passw = pass.decrypt(pass.stringToByte(xml.getContentById("DApass")), "12345678".getBytes(), "12345678".getBytes());

            usernameField.setValueAttribute(user.trim());
            passwordField.setValueAttribute(passw.trim());

            posLogin = button.click();

            if (posLogin.asXml().contains("The password you entered was incorrect")) {
                save = null;
                throw new Exception("DeviantArt");
            }
            // End of DeviantArt login

            // Tumblr login
            preLogin = save.getPage("https://www.tumblr.com/login");
            form = preLogin.getHtmlElementById("signup_form");

            HtmlTextInput usernameCheck = form.getInputByName("determine_email");
            usernameField = form.getInputByName("user[email]");
            passwordField = form.getInputByName("user[password]");
            HtmlButton next = (HtmlButton) preLogin.getElementsByIdAndOrName("signup_forms_submit").get(0);
            HtmlButton buttonInput = form.getFirstByXPath("button");

            user = pass.decrypt(pass.stringToByte(xml.getContentById("TUuser")), "12345678".getBytes(), "12345678".getBytes());
            passw = pass.decrypt(pass.stringToByte(xml.getContentById("TUpass")), "12345678".getBytes(), "12345678".getBytes());

            usernameCheck.setValueAttribute(user);
            usernameField.setValueAttribute(user);
            next.click();
            passwordField.setValueAttribute(passw);

            posLogin = buttonInput.click();

            if (posLogin.getBaseURL().toString().equals("https://www.tumblr.com/login")) {
                save = null;
                throw new Exception("Tumblr");
            }
            // End of Tumblr login

            // FurAffinity login
            preLogin = save.getPage("https://www.furaffinity.net/login/");
            HtmlAnchor oldCaptcha = (HtmlAnchor) preLogin.getElementById("captcha-switch");
            preLogin = oldCaptcha.click();
            if (preLogin.asXml().contains("If you believe you are seeing this screen in error please press CTRL-F5 to refresh")) {
                save = null;
                throw new Exception("FurAffinity");
            }
            form = preLogin.getFirstByXPath("//form [@method='post']");

            usernameField = form.getInputByName("name");
            passwordField = form.getInputByName("pass");
            button = form.getInputByName("login");

            user = pass.decrypt(pass.stringToByte(xml.getContentById("FAuser")), "12345678".getBytes(), "12345678".getBytes());
            passw = pass.decrypt(pass.stringToByte(xml.getContentById("FApass")), "12345678".getBytes(), "12345678".getBytes());

            usernameField.setValueAttribute(user.trim());
            passwordField.setValueAttribute(passw.trim());

            //show captcha pane
            JPanel panel = new JPanel();
            panel.add(new JLabel("Type the characters you see in the picture aside:"));
            HtmlImage getCaptcha = preLogin.getHtmlElementById("captcha_img");
            BufferedImage bi = getCaptcha.getImageReader().read(0);
            JLabel captcha = new JLabel(new ImageIcon(bi));
            captcha.setBorder(new LineBorder(Color.gray));
            panel.add(captcha);
            String value = JOptionPane.showInputDialog(null, panel, "Captcha", JOptionPane.QUESTION_MESSAGE);

            if (value.isEmpty()) {
                save = null;
                throw new Exception("FurAffinity");
            }

            HtmlTextInput captchaInput = form.getInputByName("captcha");
            captchaInput.setValueAttribute(value);

            try {
                posLogin = button.click();
            } catch (com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException ex) {
                save = null;
                throw new Exception("FurAffinity");
            }

            if (posLogin.getUrl().toString().equals("https://www.furaffinity.net/login/?msg=1")) {
                save = null;
                throw new Exception("FurAffinity");
            } else if (posLogin.getUrl().toString().equals("https://www.furaffinity.net/login/?msg=2")
                    || posLogin.getUrl().toString().equals("https://www.furaffinity.net/login/?msg=3")) {
                save = null;
                throw new Exception("FurAffinity");
            }
            // End of FurAffinity login

            try {
                FileOutputStream fileOut = new FileOutputStream(get);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(manager);
            } catch (IOException ex) {
                Logger.getLogger(UsefulMethods.class.getName()).log(Level.SEVERE, null, ex);
            }

            isWebViewReady = true;
            webClient = save;
            return new CloneWebClient(shutUpHtmlUnit(webClient)).getClone();
        }
    }

    public static boolean isWebClientReady() {
        return isWebViewReady;
    }

    public static void clearWebClientInstance() {
        webClient = null;
        isWebViewReady = false;

        File get = new File(getClassPath(UsefulMethods.class) + File.separator + "login_cache.obj");
        if (get.exists()) {
            get.delete();
        }
    }

    public static Connection getDBInstance() {
        if (conn != null) {
            return conn;
        } else {
            try {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection("jdbc:sqlite:" + getClassPath(UsefulMethods.class) + "artists_sqlite.db");
                Statement s = conn.createStatement();

                s.execute("CREATE TABLE IF NOT EXISTS `artist` ( `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `server` INT NOT NULL, `name` VARCHAR(100) NOT NULL , `avatar_url` VARCHAR(150) NOT NULL , `first_downloaded` DATE NOT NULL , `last_updated` DATE NOT NULL , `image_count` INT NOT NULL)");
                s.execute("CREATE TABLE IF NOT EXISTS `tag` ( `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `tag` VARCHAR(100) NOT NULL );");
                s.execute("CREATE TABLE IF NOT EXISTS `inner_tag` ( `artist_id` INT NOT NULL , `tag_id` INT NOT NULL );");
                s.execute("CREATE TABLE IF NOT EXISTS `info` ( `artist_id` INT NOT NULL, `description` TEXT(255), `fav` BOOLEAN NOT NULL );");
                s.close();

                return conn;
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(UsefulMethods.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }
    }

    public static String getClassPath(Class<?> cls) {
        try {
            String path = cls.getProtectionDomain().getCodeSource().getLocation().getPath();
            String decodedPath = java.net.URLDecoder.decode(path, "UTF-8");
            if (decodedPath.contains("/")) {
                decodedPath = decodedPath.substring(0, decodedPath.lastIndexOf("/")) + "/";
            } else {
                decodedPath = decodedPath.substring(0, decodedPath.lastIndexOf("\\")) + "\\";
            }

            return decodedPath;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(UsefulMethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static XmlManager loadManager(int manager) {
        if (manager == OPTIONS && xml != null) {
            return xml;
        } else if (manager == LANGUAGE && language != null) {
            return language;
        }

        UsefulMethods get = new UsefulMethods();
        xml = new XmlManager();
        boolean checkOS = false;

        File getConfig = new File(UsefulMethods.getClassPath(get.getClass()) + File.separator + "config");
        if (!getConfig.exists()) {
            getConfig.mkdir();
        }

        File getOptions = new File(UsefulMethods.getClassPath(get.getClass()) + "config" + File.separator + "options.xml");
        if (!getOptions.exists()) {
            String content = getOptions();

            if (content != null) {
                try {
                    getOptions.createNewFile();
                    FileUtils.writeStringToFile(getOptions, content);
                } catch (IOException ex) {
                    Logger.getLogger(get.getClass().getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                checkOS = true;
            }
        }

        xml.loadFile(UsefulMethods.getClassPath(get.getClass()) + "config" + File.separator + "options.xml");
        switch (manager) {
            case OPTIONS:
                return xml;
            case LANGUAGE:
                if (!checkOS) {
                    language = new XmlManager();
                    String temp = xml.getContentByName("language", 0);
                    language.loadFile(UsefulMethods.getClassPath(get.getClass()) + File.separator + "language"
                            + File.separator + temp.toLowerCase() + ".xml");

                    return language;
                }
        }

        return null;
    }

    public static void makeHyperlinkOptionPane(String[] message, String link, int linkIndex, int messageType, String messageTitle) {
        JOptionPane pane = new JOptionPane(null, messageType);

        StringBuilder style = new StringBuilder("font-family:" + pane.getFont().getFamily() + ";");
        style.append("font-weight:").append(pane.getFont().isBold() ? "bold" : "normal").append(";");
        style.append("font-size:").append(pane.getFont().getSize()).append("pt;");
        style.append("background-color: rgb(").append(pane.getBackground().getRed()).append(", ")
                .append(pane.getBackground().getGreen()).append(", ").append(pane.getBackground().getBlue()).append(");");

        JEditorPane ep = new JEditorPane();
        ep.setEditorKit(JEditorPane.createEditorKitForContentType("text/html"));
        ep.setEditable(false);
        ep.setBorder(null);

        String show = "";
        for (int i = 0; i < message.length; i++) {
            if (i != linkIndex) {
                show += message[i];
            } else {
                show += " <a href=\"" + link + "\">" + message[i] + "</a>";
            }
        }

        ep.setText("<html><body style=\"" + style + "\">" + show + "</body></html>");

        ep.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
                    try {
                        Desktop.getDesktop().browse(e.getURL().toURI());
                    } catch (IOException | URISyntaxException ex) {

                    }
                }
            }
        });

        JOptionPane.showMessageDialog(null, ep, messageTitle, messageType);
    }

    public static String getSimpleDateFormat() {
        Calendar date = Calendar.getInstance();

        return date.getDisplayName(2, 2, Locale.US) + " " + date.get(Calendar.DATE) + ", " + date.get(Calendar.YEAR);
    }

    public static String getComplexDateFormat() {
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

    public static void makeBalloon(final JComponent component, final String text, final Color color) {
        new Thread("Showing ballon \"" + text + "\"") {
            @Override
            public void run() {
                BalloonTip balloonTip = new BalloonTip(component, new JLabel(text), new MinimalBalloonStyle(color, 10),
                        BalloonTip.Orientation.LEFT_BELOW, BalloonTip.AttachLocation.SOUTH, 25, 10, false);

                FadingUtils.fadeInBalloon(balloonTip, null, 200, 24);
                try {
                    java.lang.Thread.sleep(4000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(UsefulMethods.class.getName()).log(Level.SEVERE, null, ex);
                }
                FadingUtils.fadeOutBalloon(balloonTip, null, 200, 24);
                TimingUtils.showTimedBalloon(balloonTip, 200);
            }
        }.start();
    }

    public static WebClient shutUpHtmlUnit(WebClient webClient) {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);

        webClient.setIncorrectnessListener(new IncorrectnessListener() {
            @Override
            public void notify(String arg0, Object arg1) {
            }
        });
        webClient.setCssErrorHandler(new ErrorHandler() {
            @Override
            public void warning(CSSParseException csspe) throws CSSException {
            }

            @Override
            public void error(CSSParseException csspe) throws CSSException {
            }

            @Override
            public void fatalError(CSSParseException csspe) throws CSSException {
            }
        });
        webClient.setJavaScriptErrorListener(new JavaScriptErrorListener() {
            @Override
            public void scriptException(InteractivePage ip, ScriptException se) {
            }

            @Override
            public void timeoutError(InteractivePage ip, long l, long l1) {
            }

            @Override
            public void malformedScriptURL(InteractivePage ip, String string, MalformedURLException murle) {
            }

            @Override
            public void loadScriptError(InteractivePage ip, URL url, Exception excptn) {
            }
        });
        webClient.setHTMLParserListener(new HTMLParserListener() {
            @Override
            public void error(String string, URL url, String string1, int i, int i1, String string2) {
            }

            @Override
            public void warning(String string, URL url, String string1, int i, int i1, String string2) {
            }
        });

        return webClient;
    }

    private static class CloneWebClient {

        private final WebClient client;

        public CloneWebClient(WebClient client) {
            this.client = client;
        }

        public WebClient getClone() {
            return client;
        }
    }
}

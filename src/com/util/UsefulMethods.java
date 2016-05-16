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
import com.gargoylesoftware.htmlunit.IncorrectnessListener;
import com.gargoylesoftware.htmlunit.InteractivePage;
import com.gargoylesoftware.htmlunit.ScriptException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HTMLParserListener;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;
import com.util.crypto.PasswordManager;
import com.util.xml.XmlManager;
import java.awt.Color;
import java.awt.Desktop;
import java.io.UnsupportedEncodingException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

public class UsefulMethods {

    public static final int OPTIONS = 0;
    public static final int LANGUAGE = 1;
    private static WebClient webClient;
    private static boolean isWebViewReady = false;

    public static String getOptions() {
        //get OS
        String os = System.getProperty("os.name").toLowerCase();
        String path;

        //get system path separator
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
                + "      <language>English, 0</language>\n"
                + "      <style>Metal, 0</style>\n"
                + "    </gui>\n"
                + "    <downloads>\n"
                + "      <scroll id=\"simult\">5</scroll>\n"
                + "      <directory id=\"mainOutput\">" + path + "</directory>\n"
                + "      <boolean id=\"sub\">true</boolean>\n"
                + "      <dropdown id=\"existed\">2</dropdown>\n"
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
                + "    <galleryHentai>\n"
                + "      <directory id=\"GHoutput\">" + path + "Gallery Hentai" + "</directory>\n"
                + "      <boolean id=\"GHadvancedNaming\">false</boolean>\n"
                + "      <number id=\"GHnamingOption\">0</number>\n"
                + "    </galleryHentai>\n"
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

    public static WebClient getWebClientInstance() throws IOException, Exception {
        if (webClient == null) {
            webClient = new WebClient(BrowserVersion.CHROME);
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setAppletEnabled(false);
            webClient = shutUpHtmlUnit(webClient);

            HtmlPage preLogin, posLogin;
            HtmlForm form;
            HtmlTextInput usernameField;
            HtmlPasswordInput passwordField;
            HtmlSubmitInput button;
            PasswordManager pass = new PasswordManager();
            XmlManager xml = loadManager(OPTIONS);
            String user;
            String passw;

            // DeviantArt login
            preLogin = webClient.getPage("https://www.deviantart.com/");
            if (preLogin == null) {
                webClient = null;
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
                webClient = null;
                throw new Exception("DeviantArt");
            }
            // End of DeviantArt login

            // Tumblr login
            preLogin = webClient.getPage("https://www.tumblr.com/login");
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
                webClient = null;
                throw new Exception("Tumblr");
            }
            // End of Tumblr login

            // FurAffinity login
            preLogin = webClient.getPage("https://www.furaffinity.net/login/");
            form = preLogin.getFirstByXPath("//form [@method='post']");

            usernameField = form.getInputByName("name");
            passwordField = form.getInputByName("pass");
            button = form.getInputByName("login");

            user = pass.decrypt(pass.stringToByte(xml.getContentById("FAuser")), "12345678".getBytes(), "12345678".getBytes());
            passw = pass.decrypt(pass.stringToByte(xml.getContentById("FApass")), "12345678".getBytes(), "12345678".getBytes());

            usernameField.setValueAttribute(user.trim());
            passwordField.setValueAttribute(passw.trim());

            try {
                posLogin = button.click();
            } catch (com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException ex) {
                webClient = null;
                throw new Exception("FurAffinity");
            }
            
            if (posLogin.getUrl().toString().equals("https://www.furaffinity.net/login/?msg=1")) {
                webClient = null;
                throw new Exception("FurAffinity");
            }
            // End of FurAffinity login

            isWebViewReady = true;
            return webClient;
        } else {
            return webClient;
        }
    }

    public static boolean isWebClientReady() {
        return isWebViewReady;
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
        UsefulMethods get = new UsefulMethods();
        XmlManager xml = new XmlManager();
        String separator = System.getProperty("file.separator");
        boolean checkOS = false;

        File getConfig = new File(UsefulMethods.getClassPath(get.getClass()) + separator + "config");
        if (!getConfig.exists()) {
            getConfig.mkdir();
        }

        File getOptions = new File(UsefulMethods.getClassPath(get.getClass()) + "config" + separator + "options.xml");
        if (!getOptions.exists()) {
            String content = UsefulMethods.getOptions();

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

        xml.loadFile(UsefulMethods.getClassPath(get.getClass()) + "config" + separator + "options.xml");
        switch (manager) {
            case OPTIONS:
                return xml;
            case LANGUAGE:
                if (!checkOS) {
                    XmlManager language = new XmlManager();
                    String temp = xml.getContentByName("language", 0);
                    temp = temp.substring(0, temp.indexOf(","));
                    language.loadFile(UsefulMethods.getClassPath(get.getClass()) + separator + "language" + separator + temp.toLowerCase() + ".xml");

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

    private static WebClient shutUpHtmlUnit(WebClient webClient) {
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
}

/* **********   NovoClass.java   **********
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
package com.core.web.explorer.panes;

import com.panels.main.StylizedMainJFrame;
import com.panels.main.StylizedMainJFrame.ClosableTabbedPane;
import com.sun.javafx.application.PlatformImpl;
import com.sun.javafx.scene.control.skin.ContextMenuContent;
import com.sun.javafx.scene.control.skin.ContextMenuContent.MenuItemContainer;
import com.core.web.cookies.CookiesPersistance;
import com.util.UsefulMethods;
import com.util.xml.XmlManager;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.Priority;
import javafx.scene.transform.Scale;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebHistory.Entry;
import javafx.scene.web.WebView;
import javafx.stage.PopupWindow;
import javafx.stage.Window;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

public class WebViewPage extends JPanel {

    private boolean isCookiesEnabled = true;
    private String selectedHref;
    private String selectedFile;
    private CookiesPersistance cookieManager;
    private JTabbedPane tabbedPane;
    private XmlManager language;
    private final ImageIcon icon;
    private final int tabIndex;
    private Button back;
    private Button forward;
    private ProgressBar loadProgress;
    private TextField currentUrl;
    private Button go;
    private Button refresh;
    private ZoomingPane zoomingPane;
    private JFXPanel jFXPanel;
    private WebView browser;

    public WebViewPage(ClosableTabbedPane tabbedPane, int tabIndex, ImageIcon icon) {
        language = UsefulMethods.loadManager(UsefulMethods.LANGUAGE);
        initComponents();
        this.tabbedPane = tabbedPane;
        this.tabIndex = tabIndex;
        this.icon = icon;

    }

    public WebViewPage(ClosableTabbedPane tabbedPane, int tabIndex, ImageIcon icon, String url) {
        language = UsefulMethods.loadManager(UsefulMethods.LANGUAGE);
        initComponents();
        this.tabbedPane = tabbedPane;
        this.tabIndex = tabIndex;
        this.icon = icon;
        createScene(url);
    }

    private void initComponents() {
        Platform.setImplicitExit(false);
        jFXPanel = new JFXPanel();
        setLayout(new GridLayout());
        add(jFXPanel);
    }

    public void setCookies(boolean bln) {
        isCookiesEnabled = bln;
    }

    public boolean isCookiesEnabled() {
        return isCookiesEnabled;
    }

    public void setTabbedPane(ClosableTabbedPane tabbedPane) {
        this.tabbedPane = tabbedPane;
    }

    public void load(String url) {
        createScene(url);
    }

    private void createScene(final String url) {
        PlatformImpl.runLater(new Runnable() {
            @Override
            public void run() {
                browser = new WebView();

                if (isCookiesEnabled) {
                    File get = new File(UsefulMethods.getClassPath(getClass()) + "cookies.obj");
                    if (get.exists()) {
                        FileInputStream fileOut;
                        ObjectInputStream out;
                        try {
                            fileOut = new FileInputStream(get);
                            out = new ObjectInputStream(fileOut);
                            cookieManager = (CookiesPersistance) out.readObject();
                            out.close();
                            fileOut.close();

                            cookieManager = new CookiesPersistance(cookieManager.getCookies());
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(WebViewPage.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            int result = JOptionPane.showConfirmDialog(null, language.getContentById("cookiesErr1"),
                                    language.getContentById("error"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);

                            if (result == JOptionPane.OK_OPTION || result == JOptionPane.CANCEL_OPTION) {
                                JOptionPane.showMessageDialog(null, language.getContentById("cookiesErr2"),
                                        language.getContentById("error"), JOptionPane.ERROR_MESSAGE);

                                cookieManager = new CookiesPersistance();
                                cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
                            }
                        } catch (ClassNotFoundException ex) {
                        }
                    } else {
                        cookieManager = new CookiesPersistance();
                        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
                    }

                    CookieManager.setDefault(cookieManager);
                } else {
                    CookieManager.setDefault(new CookieManager());
                }

                zoomingPane = new ZoomingPane(browser);
                currentUrl = new TextField(url);
                BorderPane bp = new BorderPane();
                bp.setCenter(zoomingPane);
                VBox layout = new VBox();
                layout.getChildren().setAll(createProgressReport(), browser);
                jFXPanel.setScene(new Scene(layout));

                browser.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
                    @Override
                    public void changed(ObservableValue<? extends Worker.State> ov, Worker.State t, Worker.State t1) {
                        loadProgress.progressProperty().bind(browser.getEngine().getLoadWorker().progressProperty());
                        if (browser.getEngine().getHistory().getCurrentIndex() == browser.getEngine().getHistory().getEntries().size() - 1) {
                            forward.setDisable(true);
                        }

                        if (t1.equals(Worker.State.SUCCEEDED)) {
                            loadProgress.progressProperty().unbind();
                            loadProgress.progressProperty().set(100);

                            String title;
                            try {
                                title = browser.getEngine().getDocument().getElementsByTagName("title").item(0).getTextContent();
                            } catch (java.lang.NullPointerException ex) {
                                title = language.getContentById("uninformedName");
                            }

                            if (title.length() > 18) {
                                title = title.substring(0, 18);
                            }

                            tabbedPane.setTitleAt(tabIndex, title + "     ");

                            //set listener to catch eventual right clicks
                            EventListener hrefListener = new EventListener() {
                                @Override
                                public void handleEvent(Event evt) {
                                    String domEventType = evt.getType();
                                    if (domEventType.equals("mouseover")) {
                                        String res = ((Element) evt.getTarget()).getAttribute("href");
                                        if (res != null) {
                                            selectedHref = res;
                                        }
                                    }
                                }
                            };

                            EventListener notHrefListener = new EventListener() {
                                @Override
                                public void handleEvent(Event evt) {
                                    String domEventType = evt.getType();
                                    if (domEventType.equals("mouseover")) {
                                        String res = ((Element) evt.getTarget()).getAttribute("src");
                                        if (res != null) {
                                            selectedFile = res;
                                        }
                                    }
                                }
                            };

                            Document doc = browser.getEngine().getDocument();
                            NodeList anchorList = doc.getElementsByTagName("a");
                            for (int i = 0; i < anchorList.getLength(); i++) {
                                ((EventTarget) anchorList.item(i)).addEventListener("mouseover", hrefListener, false);
                            }

                            NodeList imgList = doc.getElementsByTagName("img");
                            for (int i = 0; i < imgList.getLength(); i++) {
                                ((EventTarget) imgList.item(i)).addEventListener("mouseover", notHrefListener, false);
                            }

                            try {
                                saveCookies();
                            } catch (IOException ex) {
                                Logger.getLogger(WebViewPage.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else if (t1.equals(Worker.State.FAILED)) {
                            tabbedPane.setTitleAt(tabIndex, "Error     ");
                            tabbedPane.setIconAt(tabIndex, new ImageIcon(getClass().getResource("/com/style/icons/stopPage.png")));
                            tabbedPane.setComponentAt(tabIndex, new ErrorPane(browser.getEngine().getLoadWorker().getException().toString(), tabbedPane, tabIndex, icon, url));
                        }
                    }
                });

                browser.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                    @Override
                    public void handle(ContextMenuEvent e) {
                        createPopupWindow();
                    }
                });

                browser.getEngine().getHistory().getEntries().addListener(new ListChangeListener<Entry>() {
                    @Override
                    public void onChanged(ListChangeListener.Change<? extends Entry> change) {
                        currentUrl.setText(change.getList().get(change.getList().size() - 1).getUrl());

                        final WebHistory history = browser.getEngine().getHistory();
                        int current = history.getCurrentIndex();

                        if (current < 0) {
                            back.setDisable(true);
                        } else {
                            back.setDisable(false);
                        }
                    }
                });

                back.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        goBack();
                    }
                });

                forward.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        goForward();
                    }
                });

                currentUrl.setOnKeyPressed(new EventHandler<javafx.scene.input.KeyEvent>() {
                    @Override
                    public void handle(javafx.scene.input.KeyEvent t) {
                        if (t.getCode() == javafx.scene.input.KeyCode.ENTER) {
                            String url = currentUrl.getText();

                            if (url.startsWith("www.")) {
                                url = "http://" + url;
                            } else {
                                url = "http://www." + url;
                            }

                            browser.getEngine().load(url);
                        }
                    }
                });

                go.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        browser.getEngine().load(currentUrl.getText());
                    }
                });

                refresh.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        browser.getEngine().reload();
                    }
                });

                browser.getEngine().load(url);
            }
        });
    }

    private void goBack() {
        final WebHistory history = browser.getEngine().getHistory();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (history.getEntries().size() - 1 > 0) {
                    history.go(-1);
                    forward.setDisable(false);
                    currentUrl.setText(history.getEntries().get(browser.getEngine().getHistory().getCurrentIndex()).getUrl());
                    if (history.getCurrentIndex() == 0) {
                        back.setDisable(true);
                    }
                }
            }
        });
    }

    private void goForward() {
        final WebHistory history = browser.getEngine().getHistory();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (history.getCurrentIndex() != history.getEntries().size() - 1) {
                    history.go(1);
                    back.setDisable(false);
                    currentUrl.setText(history.getEntries().get(browser.getEngine().getHistory().getCurrentIndex()).getUrl());
                    if (history.getCurrentIndex() == history.getEntries().size() - 1) {
                        forward.setDisable(true);
                    }
                } else {
                    forward.setDisable(true);
                }
            }
        });
    }

    @SuppressWarnings("deprecation")
    private PopupWindow createPopupWindow() {
        final Iterator<Window> windows = Window.impl_getWindows();

        while (windows.hasNext()) {
            Window window = windows.next();

            if (window instanceof ContextMenu) {
                if (window.getScene() != null && window.getScene().getRoot() != null) {
                    Parent root = window.getScene().getRoot();

                    // access to context menu content
                    if (root.getChildrenUnmodifiable().size() > 0) {
                        Node popup = root.getChildrenUnmodifiable().get(0);
                        if (popup.lookup(".context-menu") != null) {
                            Node bridge = popup.lookup(".context-menu");
                            ContextMenuContent cmc = (ContextMenuContent) ((Parent) bridge).getChildrenUnmodifiable().get(0);

                            VBox itemsContainer = cmc.getItemsContainer();
                            String[] typos = new String[]{"Reload page", "Stop loading", "Go Back", "Go Forward", "Open Link", "Open Link in New Window",
                                "Copy Link to Clipboard", "Open Image in New Window", "Copy Image to Clipboard"};
                            List<MenuItemContainer> remove = new ArrayList<>();

                            for (Node n : itemsContainer.getChildren()) {
                                MenuItemContainer item = (MenuItemContainer) n;
                                for (String typo : typos) {
                                    if (item.getItem().getText().equals(typo)) {
                                        remove.add(item);
                                    }
                                }
                            }

                            for (final MenuItemContainer bye : remove) {
                                String text = bye.getItem().getText();
                                itemsContainer.getChildren().remove(bye);

                                if (text.equals(typos[0])) {
                                    MenuItem reloadPage = new MenuItem(language.getContentById("reloadPage"));
                                    reloadPage.setOnAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent e) {
                                            browser.getEngine().reload();
                                        }
                                    });

                                    cmc.getItemsContainer().getChildren().add(cmc.new MenuItemContainer(reloadPage));

                                    text = currentUrl.getText();
                                    String[] outcomes = new String[]{".deviantart.com/gallery/", ".tumblr.com", "http://g.e-hentai.org/g/",
                                        "http://www.furaffinity.net/gallery/", "https://e621.net/post/index/"};

                                    for (int i = 0; i < outcomes.length; i++) {
                                        if (text.contains(outcomes[i])) {
                                            if (i == 0) {
                                                if (!text.endsWith("?catpath=/")) {
                                                    text += "?catpath=/";
                                                }
                                            } else if (i == 3) {
                                                String title = browser.getEngine().getDocument().getElementsByTagName("title").item(0).getTextContent();
                                                if (title.endsWith("| Tumblr")) {
                                                    break;
                                                }
                                            }

                                            String show = null;
                                            switch (i) {
                                                case 0:
                                                    String artist = text.substring(7, text.indexOf("."));
                                                    artist = artist.substring(0, 1).toUpperCase() + artist.substring(1);
                                                    show = language.getContentById("saveGallery").replace("&string", artist);
                                                    break;
                                                case 1:
                                                    show = language.getContentById("saveGallery").replace("&string", "Tumblr");
                                                    break;
                                                case 2:
                                                    String title = browser.getEngine().getDocument().getElementsByTagName("title").item(0).getTextContent();
                                                    if (title.length() > 10) {
                                                        title = title.substring(0, 10);
                                                    }
                                                    show = language.getContentById("saveGallery").replace("&string", title);
                                                    break;
                                                case 3:
                                                    String artist2 = text.substring(35, text.lastIndexOf("/"));
                                                    artist2 = artist2.substring(0, 1).toUpperCase() + artist2.substring(1);
                                                    show = language.getContentById("saveGallery").replace("&string", artist2);
                                                    break;
                                                case 4:
                                                    String artist3 = text.substring(text.lastIndexOf("/") + 1);
                                                    artist3 = artist3.substring(0, 1).toUpperCase() + artist3.substring(1);
                                                    show = language.getContentById("saveGallery").replace("&string", artist3);
                                                    break;
                                            }

                                            final String send = text;
                                            final int server = i;
                                            MenuItem openLink = new MenuItem(show);
                                            openLink.setOnAction(new EventHandler<ActionEvent>() {
                                                @Override
                                                public void handle(ActionEvent t) {
                                                    StylizedMainJFrame.AddTask add = StylizedMainJFrame.ADD_TASK;
                                                    add.addTask(send, server, -2);
                                                }
                                            });

                                            cmc.getItemsContainer().getChildren().add(cmc.new MenuItemContainer(openLink));
                                            break;
                                        }
                                    }

                                } else if (text.equals(typos[2])) {
                                    MenuItem goBack = new MenuItem(language.getContentById("goBack"));
                                    goBack.setOnAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent e) {
                                            goBack();
                                        }
                                    });

                                    cmc.getItemsContainer().getChildren().add(cmc.new MenuItemContainer(goBack));
                                } else if (text.equals(typos[3])) {
                                    MenuItem goForward = new MenuItem(language.getContentById("goForward"));
                                    goForward.setOnAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent e) {
                                            goForward();
                                        }
                                    });

                                    cmc.getItemsContainer().getChildren().add(cmc.new MenuItemContainer(goForward));
                                } else if (text.equals(typos[4])) {
                                    MenuItem openLink = new MenuItem(language.getContentById("openLink"));
                                    openLink.setOnAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent t) {
                                            browser.getEngine().load(setNewPageUrl());
                                        }
                                    });

                                    cmc.getItemsContainer().getChildren().add(cmc.new MenuItemContainer(openLink));
                                } else if (text.equals(typos[5])) {
                                    MenuItem openLink = new MenuItem(language.getContentById("openLinkNT"));
                                    openLink.setOnAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent t) {
                                            tabbedPane.addTab("New Tab     ", icon, new WebViewPage((ClosableTabbedPane) tabbedPane,
                                                    tabbedPane.getTabCount() - 2, icon, setNewPageUrl()));
                                        }
                                    });

                                    cmc.getItemsContainer().getChildren().add(cmc.new MenuItemContainer(openLink));
                                } else if (text.equals(typos[6])) {
                                    MenuItem openLink = new MenuItem(language.getContentById("copyLinkCB"));
                                    openLink.setOnAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent t) {
                                            StringSelection selection = new StringSelection(setNewPageUrl());
                                            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                                            clipboard.setContents(selection, selection);
                                        }
                                    });

                                    cmc.getItemsContainer().getChildren().add(cmc.new MenuItemContainer(openLink));
                                } else if (text.equals(typos[7])) {
                                    MenuItem openLink = new MenuItem(language.getContentById("openLink"));
                                    openLink.setOnAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent t) {
                                            tabbedPane.addTab(language.getContentById("openImage") + "     ", icon, new WebViewPage((ClosableTabbedPane) tabbedPane,
                                                    tabbedPane.getTabCount() - 2, icon, selectedFile));
                                        }
                                    });

                                    cmc.getItemsContainer().getChildren().add(cmc.new MenuItemContainer(openLink));
                                }
                            }

                            return (PopupWindow) window;
                        }
                    }
                }
                return null;
            }
        }

        return null;
    }

    private String setNewPageUrl() {
        String url = currentUrl.getText();

        if (selectedHref.indexOf("/", 1) > 0) {
            String temp = selectedHref.substring(0, selectedHref.indexOf("/", 1) + 1);
            if (url.contains(temp)) {
                url = url.substring(0, url.indexOf(temp)) + selectedHref;
            }
        } else {
            url = url.substring(0, url.length() - 1) + selectedHref;
        }

        return url;
    }

    private HBox createProgressReport() {
        loadProgress = new ProgressBar();
        back = new Button();
        forward = new Button();
        go = new Button();
        refresh = new Button();

        back.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/com/style/icons/back.png"))));
        back.setStyle("-fx-background-radius: 90 0 0 90;");
        back.setDisable(true);

        forward.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/com/style/icons/forward.png"))));
        forward.setStyle("-fx-background-radius: 0 90 90 0;");
        forward.setDisable(true);

        loadProgress.setMinHeight(41);
        loadProgress.setMinWidth(330);
        loadProgress.setMaxWidth(Double.MAX_VALUE);

        currentUrl.setBorder(Border.EMPTY);
        currentUrl.setStyle("-fx-background-color: transparent;"
                + "-fx-text-fill: white;");
        currentUrl.setFont(new javafx.scene.text.Font(16));

        go.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/com/style/icons/navigate.png"))));
        go.setStyle("-fx-background-radius: 0 90 90 0;");

        refresh.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/com/style/icons/refresh.png"))));
        refresh.setStyle("-fx-background-radius: 90 90 90 90;");
        refresh.setAlignment(Pos.CENTER_RIGHT);

        HBox progressReport = new HBox();
        progressReport.setPrefWidth(Double.MAX_VALUE);

        HBox backFor = new HBox();
        backFor.setPadding(new Insets(5, 5, 0, 0));
        backFor.getChildren().addAll(back, forward);

        HBox goRef = new HBox();
        goRef.setPadding(new Insets(5, 5, 5, 5));
        goRef.getChildren().addAll(go, refresh);

        StackPane pb = new ProgressIndicatorBar().setProgressBar();
        progressReport.getChildren().setAll(backFor, pb, go, goRef);
        HBox.setHgrow(pb, Priority.ALWAYS);
        progressReport.setPadding(new Insets(10));
        progressReport.setAlignment(Pos.CENTER_LEFT);

        return progressReport;
    }

    public synchronized void saveCookies() throws FileNotFoundException, IOException {
        FileOutputStream fileOut = new FileOutputStream("cookies.obj");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(cookieManager);
        out.close();
        fileOut.close();

    }

    // http://stackoverflow.com/questions/22796742/fit-javafx-webview-browser-content-to-window
    private class ZoomingPane extends Pane {

        Node content;
        private final DoubleProperty zoomFactor = new SimpleDoubleProperty(1);
        private double zoomFactory = 1.0;

        private ZoomingPane(Node content) {
            this.content = content;
            getChildren().add(content);
            final Scale scale = new Scale(1, 1);
            content.getTransforms().add(scale);

            zoomFactor.addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                    scale.setX(t1.doubleValue());
                    scale.setY(zoomFactory);
                    requestLayout();
                }
            });
        }

        @Override
        protected void layoutChildren() {
            Pos pos = Pos.TOP_LEFT;
            double width = getWidth();
            double height = getHeight();
            double top = getInsets().getTop();
            double right = getInsets().getRight();
            double left = getInsets().getLeft();
            double bottom = getInsets().getBottom();
            double contentWidth = (width - left - right) / zoomFactor.get();
            double contentHeight = (height - top - bottom) / zoomFactory;
            layoutInArea(content, left, top,
                    contentWidth, contentHeight,
                    0, null,
                    pos.getHpos(),
                    pos.getVpos());
        }

        public final Double getZoomFactor() {
            return zoomFactor.get();
        }

        public final void setZoomFactor(Double zoomFactor) {
            this.zoomFactor.set(zoomFactor);
        }

        public final void setZoomFactors(Double zoomFactorx, Double Zoomfactory) {
            this.zoomFactory = Zoomfactory;
            this.zoomFactor.set(zoomFactorx);
        }

        public final DoubleProperty zoomFactorProperty() {
            return zoomFactor;
        }
    }

    private class ProgressIndicatorBar extends StackPane {

        public StackPane setProgressBar() {
            getChildren().setAll(loadProgress, currentUrl);
            return this;
        }
    }
}

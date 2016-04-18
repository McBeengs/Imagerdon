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

import com.panels.main.StylizedMainJFrame.ClosableTabbedPane;
import com.sun.javafx.application.PlatformImpl;
import com.sun.javafx.scene.control.skin.ContextMenuContent;
import com.sun.javafx.scene.control.skin.ContextMenuContent.MenuItemContainer;
import com.util.serialize.CookiesPersistance;
import java.awt.GridLayout;
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
import javafx.collections.ObservableList;
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
import javafx.event.EventType;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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

public class WebViewPage extends JPanel {

    private boolean isCookiesEnabled = true;
    private boolean isBacking = false;
    private CookiesPersistance cookieManager;
    private JTabbedPane tabbedPane;
    private ImageIcon icon;
    private int tabIndex;
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
        initComponents();
        this.tabbedPane = tabbedPane;
        this.tabIndex = tabIndex;
        this.icon = icon;
    }

    public WebViewPage(ClosableTabbedPane tabbedPane, int tabIndex, ImageIcon icon, String url) {
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
                //browser.setContextMenuEnabled(false);

                if (isCookiesEnabled) {
                    File get = new File("cookies.obj");
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
                            int result = JOptionPane.showConfirmDialog(null, "Fatal error while loading options in storage. File \"cookies.obj\" is corrupted",
                                    "Error", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);

                            if (result == JOptionPane.OK_OPTION || result == JOptionPane.CANCEL_OPTION) {
                                JOptionPane.showMessageDialog(null, "Unfortunately, the cookies will be deleted.",
                                        "Error", JOptionPane.ERROR_MESSAGE);

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
                        ObservableList<Entry> entries = browser.getEngine().getHistory().getEntries();
                        if (browser.getEngine().getHistory().getCurrentIndex()
                                == browser.getEngine().getHistory().getEntries().size() - 1) {
                            forward.setDisable(true);
                        }

                        if (entries.size() > 0) {
                            currentUrl.setText(entries.get(entries.size() - 1).getUrl());
                        }

                        loadProgress.progressProperty().bind(browser.getEngine().getLoadWorker().progressProperty());
                        if (t1.equals(Worker.State.SUCCEEDED)) {
                            loadProgress.progressProperty().unbind();
                            loadProgress.progressProperty().set(100);

                            String title;
                            try {
                                title = browser.getEngine().getDocument().getElementsByTagName("title").item(0).getTextContent();
                            } catch (java.lang.NullPointerException ex) {
                                title = "[Uninformed Name]";
                            }

                            if (title.length() > 18) {
                                title = title.substring(0, 18);
                            }

                            tabbedPane.setTitleAt(tabIndex, title + "     ");
                            try {
                                saveCookies();
                            } catch (IOException ex) {
                                Logger.getLogger(WebViewPage.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else if (t1.equals(Worker.State.FAILED)) {
                            tabbedPane.removeTabAt(tabIndex);
                            tabbedPane.addTab("Error     ", new ImageIcon(getClass().getResource("/com/style/icons/stopPage.png")),
                                    new ErrorPane(browser.getEngine().getLoadWorker().getException().toString(), tabbedPane, tabIndex, icon, url));
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
                        final WebHistory history = browser.getEngine().getHistory();

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if (history.getEntries().size() - 1 > 0) {
                                    isBacking = true;
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
                });

                forward.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
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
        }
        );
    }

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
                            for (Node n : itemsContainer.getChildren()) {
                                final MenuItemContainer item = (MenuItemContainer) n;
                                //customize function
                            }
                            // remove some item:
                            // itemsContainer.getChildren().remove(0);

                            // creating new item:
                            MenuItem menuItem = new MenuItem("Save page");
                            menuItem.setOnAction(new EventHandler<ActionEvent>() {

                                @Override
                                public void handle(ActionEvent e) {
                                    System.out.println("Save Page");
                                }
                            });
                            // add new item:
                            cmc.getItemsContainer().getChildren().add(cmc.new MenuItemContainer(menuItem));

                            return (PopupWindow) window;
                        }
                    }
                }
                return null;
            }
        }
        return null;
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

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

import com.sun.javafx.application.PlatformImpl;
import com.beengs.store.PersistentCookiesStore;
import java.awt.BorderLayout;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.paint.Color;
import java.awt.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.scene.web.WebHistory.Entry;
import javafx.scene.web.WebView;
import javax.swing.JPanel;

public class WebViewPage extends JPanel implements Serializable {

    private boolean isCookiesEnabled = true;
    private Button back;
    private Button forward;
    private ProgressBar loadProgress;
    private Text currentUrl;
    private Button refresh;
    private List<Entry> backFields;
    private List<Entry> forwardFields;
    private ZoomingPane zoomingPane;
    private final PersistentCookiesStore store;
    private JFXPanel jFXPanel;
    private WebView browser;

    public WebViewPage() {
        initComponents();
        store = new PersistentCookiesStore("cookies");
    }

    public WebViewPage(String url) {
        initComponents();
        store = new PersistentCookiesStore("cookies");
        createScene(url);
    }

    public WebViewPage(PersistentCookiesStore store) {
        initComponents();
        this.store = store;
    }

    public WebViewPage(String url, PersistentCookiesStore store) {
        initComponents();
        this.store = store;
        createScene(url);
    }

    private void initComponents() {
        jFXPanel = new JFXPanel();
        setLayout(new BorderLayout());
        add(jFXPanel, BorderLayout.CENTER);
    }

    public void setCookies(boolean bln) {
        isCookiesEnabled = bln;
    }

    public boolean isCookiesEnabled() {
        return isCookiesEnabled;
    }

    public void load(String url) {
        createScene(url);
    }

    private void createScene(String url) {
        CookieManager manager = new CookieManager(store, CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(manager);

        PlatformImpl.runLater(() -> {
            browser = new WebView();
            zoomingPane = new ZoomingPane(browser);
            currentUrl = new Text(url);
            BorderPane bp = new BorderPane();
            bp.setCenter(zoomingPane);
            VBox layout = new VBox();
            layout.getChildren().setAll(createProgressReport(), browser);
            jFXPanel.setScene(new Scene(layout));

            ObservableList<Entry> entries = browser.getEngine().getHistory().getEntries();

            browser.getEngine().getLoadWorker().stateProperty().addListener((ObservableValue<? extends Worker.State> ov, Worker.State t, Worker.State t1) -> {
                if (entries.size() > 0) {
                    setUrlText(entries.get(entries.size() - 1).getUrl());
                }
                loadProgress.progressProperty().bind(browser.getEngine().getLoadWorker().progressProperty());

                if (t1.equals(Worker.State.SUCCEEDED)) {
                    loadProgress.progressProperty().unbind();
                    loadProgress.progressProperty().set(100);
                }
            });

            backFields = new ArrayList<>();
            forwardFields = new ArrayList<>();

            browser.getEngine().getHistory().getEntries().addListener((ListChangeListener.Change<? extends Entry> change) -> {
                boolean checkIfExists = false;
                for (Entry entry : backFields) {
                    if (entries.get(entries.size() - 1).getUrl().equals(entry.getUrl())) {
                        checkIfExists = true;
                    }
                }

                if (!checkIfExists) {
                    backFields.add(entries.get(entries.size() - 1));
                    forwardFields.clear();
                    forward.setDisable(true);
                }

                if (backFields.size() > 1) {
                    back.setDisable(false);
                }
            });

            back.setOnAction((ActionEvent t) -> {
                forward.setDisable(false);
                if (backFields.size() > 0) {
                    browser.getEngine().load(backFields.get(backFields.size() - 2).getUrl());

                    boolean checkIfExists = false;
                    for (Entry entry : forwardFields) {
                        if (forwardFields.get(forwardFields.size() - 1).getUrl().equals(entry.getUrl())) {
                            checkIfExists = true;
                        }
                    }

                    if (!checkIfExists) {
                        forwardFields.add(backFields.get(backFields.size() - 1));
                    }

                    backFields.remove(backFields.size() - 1);

                    if (backFields.get(backFields.size() - 1).getUrl().equals(backFields.get(0).getUrl())) {
                        back.setDisable(true);
                    }
                }
            });

            forward.setOnAction((ActionEvent t) -> {
                if (forwardFields.size() > 0) {
                    browser.getEngine().load(forwardFields.get(forwardFields.size() - 1).getUrl());
                    forwardFields.remove(forwardFields.size() - 1);
                } else {
                    forward.setDisable(true);
                }
            });
            
            refresh.setOnAction((ActionEvent t) -> {
                browser.getEngine().reload();
            });

            browser.getEngine().load(url);
        });
    }

    private void setUrlText(String text) {
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
        Font font = new Font(currentUrl.getFont().getName(), Font.PLAIN, (int) currentUrl.getFont().getSize());
        
        int textWidth = (int) font.getStringBounds(text, frc).getWidth();
        if (textWidth > loadProgress.getWidth()) {
            text = "..." + text.substring(textWidth / 13);
        }
        
        currentUrl.setText(text);
    }

    private HBox createProgressReport() {
        loadProgress = new ProgressBar();
        back = new Button();
        forward = new Button();
        refresh = new Button();

        back.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/com/style/icons/back.png"))));
        back.setStyle("-fx-background-radius: 90 0 0 90;");
        back.setDisable(true);

        forward.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/com/style/icons/forward.png"))));
        forward.setStyle("-fx-background-radius: 0 90 90 0;");
        forward.setDisable(true);

        loadProgress.setMinHeight(40);
        loadProgress.setMinWidth(400);

        currentUrl.setFill(Color.WHITE);
        currentUrl.setFont(new javafx.scene.text.Font(16));
        
        refresh.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/com/style/icons/refresh.png"))));
        refresh.setStyle("-fx-background-radius: 90 90 90 90;");

        HBox progressReport = new HBox();
        progressReport.getChildren().setAll(back, forward, new ProgressIndicatorBar().setProgressBar(), refresh);
        progressReport.setPadding(new Insets(10));
        progressReport.setAlignment(Pos.CENTER_LEFT);

        return progressReport;
    }

    // http://stackoverflow.com/questions/22796742/fit-javafx-webview-browser-content-to-window
    // obs.: Praise lord Mike
    private class ZoomingPane extends Pane implements Serializable {

        Node content;
        private final DoubleProperty zoomFactor = new SimpleDoubleProperty(1);
        private double zoomFactory = 1.0;

        private ZoomingPane(Node content) {
            this.content = content;
            getChildren().add(content);
            final Scale scale = new Scale(1, 1);
            content.getTransforms().add(scale);

            zoomFactor.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                scale.setX(newValue.doubleValue());
                scale.setY(zoomFactory);
                requestLayout();
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

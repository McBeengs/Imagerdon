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

import com.beengs.cookies.PersistCookie;
import com.sun.javafx.application.PlatformImpl;
import com.beengs.store.PersistentCookiesStore;
import java.awt.BorderLayout;
import java.io.IOException;
import java.io.Serializable;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.scene.web.WebView;
import javax.swing.JPanel;

public class WebViewPage extends JPanel implements Serializable {

    private boolean isCookiesEnabled = true;
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
//        try {
//            HttpCookie cookie = new PersistCookie().loadCookie("");
//        } catch (IOException | ClassNotFoundException ex) {
//            Logger.getLogger(WebViewPage.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        CookieManager manager = new CookieManager(store, CookiePolicy.ACCEPT_ORIGINAL_SERVER);
        CookieHandler.setDefault(manager);

        PlatformImpl.runLater(() -> {
            browser = new WebView();

            browser.getEngine().getLoadWorker().stateProperty().addListener((ObservableValue<? extends Worker.State> ov, Worker.State t, Worker.State t1) -> {
                //new SerializeManager().saveObject("cookiesBrowser.txt", persistent);
            });

            zoomingPane = new ZoomingPane(browser);
            BorderPane bp = new BorderPane();
            bp.setCenter(zoomingPane);
            jFXPanel.setScene(new Scene(bp));
            browser.getEngine().load(url);
        });
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
}

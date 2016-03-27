package testArea;

import com.sun.javafx.application.PlatformImpl;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javax.swing.JFrame;

public class SimpleJFrame {

    public static void main(String[] args) {
        JFXPanel panel = new JFXPanel();
        ProgressBar pb = new ProgressBar();
        JFrame frame = new JFrame();

        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel.setScene(new Scene(pb));
        frame.add(panel);
        frame.setVisible(true);

        PlatformImpl.runLater(() -> {
            for (int i = 0; i < 80; i++) {
                pb.setProgress(i);
            }
        });
    }
}

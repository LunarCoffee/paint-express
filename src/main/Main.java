package main;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    static int winWidth = 750;
    static int winHeight = 700;
    static Canvas canvas = new Canvas();

    private Main() {
        setLayout(new BorderLayout());
        initUI();
    }

    private void initUI() {
        // Set the title and try to find the window icon.
        setTitle("Paint Express");
        try {
            setIconImage(new ImageIcon(getClass().getResource("/icons/window_icon.png")).getImage());
        } catch (NullPointerException npe) {
            JOptionPane.showMessageDialog(null, "Icons not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Set window size, center it, and exit fully when the window is closed.
        setSize(winWidth, winHeight);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Add custom menu bar, toolbar, and canvas.
        add(new PaintMenu(), BorderLayout.PAGE_START);
        add(new PaintToolbar());
        add(canvas, BorderLayout.PAGE_END);
        pack();
    }

    public static void main(String[] args) {
        // Enable antialiasing for text, and run the app.
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}

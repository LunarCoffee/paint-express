package main;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private PaintToolbar toolBar = new PaintToolbar();

    static int winWidth = 750;
    static int winHeight = 700;
    static Canvas canvas = new Canvas();

    private Main() {
        setLayout(new BorderLayout());
        initUI();
    }

    private void initUI() {
        setTitle("Paint Express");
        setIconImage(new ImageIcon("resources/icons/window_icon.png").getImage());
        setSize(winWidth, winHeight);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Add custom menu bar.
        PaintMenu menu = new PaintMenu();
        add(menu, BorderLayout.PAGE_START);

        add(toolBar);
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

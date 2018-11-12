package main;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    static Canvas canvas = new Canvas();
    static PaintToolbar toolBar = new PaintToolbar();

    private Main() {
        setLayout(new BorderLayout());
        initUI();
    }

    private void initUI() {
        setTitle("Paint Express");
        setIconImage(new ImageIcon("resources/icons/window_icon.png").getImage());
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Add custom menu bar.
        PaintMenu menu = new PaintMenu();
        add(menu, BorderLayout.PAGE_START);

        // Add custom toolbar with color and tool selection.
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

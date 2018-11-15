package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

class PaintToolbar extends JToolBar {
    PaintToolbar() {
        setBackground(Color.WHITE);
        initToolBar(this);
    }

    private void initToolBar(JToolBar toolBar) {
        // Temporary JPanel to fix the changing of button dimensions.
        JPanel temp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addColorButtons(temp);

        // Slider controlling the brush's winWidth.
        temp.add(new JLabel("   Width:"));
        JSlider brushWidth = new JSlider(JSlider.HORIZONTAL, 1, 20, 1);
        brushWidth.addChangeListener(e -> Main.canvas.g2.setStroke(new BasicStroke(brushWidth.getValue())));
        temp.add(brushWidth);

        // Add padding with label and add tool buttons
        temp.add(new JLabel(" "));
        addGeometryButtons(temp);

        // Lock the toolbar and add it to the panel.
        setFloatable(false);
        toolBar.add(temp, BorderLayout.WEST);
    }

    private void addColorButtons(JPanel panel) {
        panel.add(makeColorButton(Color.RED));
        panel.add(makeColorButton(Color.ORANGE));
        panel.add(makeColorButton(Color.YELLOW));
        panel.add(makeColorButton(Color.GREEN));
        panel.add(makeColorButton(Color.BLUE));
        panel.add(makeColorButton(new Color(145, 0, 225)));
        panel.add(makeColorButton(new Color(105, 50, 0)));
        panel.add(makeColorButton(Color.PINK));
        panel.add(makeColorButton(Color.WHITE));
        panel.add(makeColorButton(Color.LIGHT_GRAY));
        panel.add(makeColorButton(Color.GRAY));
        panel.add(makeColorButton(Color.BLACK));
    }

    private JButton makeColorButton(Color color) {
        JButton colorButton = new JButton();
        colorButton.addActionListener(e -> Main.canvas.g2.setColor(color));
        colorButton.setBackground(color);
        colorButton.setPreferredSize(new Dimension(22, 22));
        return colorButton;
    }

    private void addGeometryButtons(JPanel panel) {
        try {
            panel.add(makeGeometryButton("pencil"));
            panel.add(makeGeometryButton("eraser"));
            panel.add(makeGeometryButton("line"));
            panel.add(makeGeometryButton("rect"));
            panel.add(makeGeometryButton("oval"));
        } catch (IOException ioe) {
            // Cannot find icons for tool buttons.
            JOptionPane.showMessageDialog(null, "Icons not found!", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private JButton makeGeometryButton(String name) throws IOException {
        // Scale icon size to fit inside the button.
        ImageIcon imageIcon = new ImageIcon(ImageIO.read(getClass().getResource("/icons/" + name + ".png")));
        Image image = imageIcon.getImage();
        image = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);

        // Make button with light grey background.
        JButton geometryButton = new JButton(imageIcon);
        geometryButton.addActionListener(e -> Main.canvas.shape = name);
        geometryButton.setBackground(new Color(225, 225, 225));
        geometryButton.setPreferredSize(new Dimension(22, 22));
        return geometryButton;
    }
}

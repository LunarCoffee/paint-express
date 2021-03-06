package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

class Canvas extends JPanel {
    private int oldX;
    private int oldY;
    private int curX;
    private int curY;
    BufferedImage image;
    Graphics2D g2;
    String shape = "pencil";

    Canvas() {
        setBackground(Color.WHITE);
        addMousePressedListener();
        addMouseDraggedListener();
        addMouseReleasedListener();

        image = new BufferedImage(Main.winWidth, Main.winHeight - 60, BufferedImage.TYPE_INT_RGB);
        g2 = (Graphics2D) image.getGraphics();
        fillBackground(Color.WHITE);
        g2.setColor(Color.BLACK);
    }

    private void addMousePressedListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Record first mouse position.
                oldX = e.getX();
                oldY = e.getY();

                // Draw a single dot where the click occurred.
                if (shape.equals("pencil"))
                    drawPencil(oldX, oldY);
                repaint();
            }
        });
    }

    private void addMouseDraggedListener() {
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                switch (shape) {
                case "pencil":
                    drawPencil(e.getX(), e.getY());
                    break;
                case "eraser":
                    drawEraser(e.getX(), e.getY());
                    break;
                case "line":
                    drawLine(e.getX(), e.getY());
                    break;
                case "rect":
                    drawRect(e.getX(), e.getY());
                    break;
                case "oval":
                    drawOval(e.getX(), e.getY());
                }
                repaint();
            }
        });
    }

    private void addMouseReleasedListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                switch (shape) {
                case "line":
                    // Remove the non-AA rubber band line and draw a new AA-enabled one.
                    g2.drawLine(oldX, oldY, curX, curY);
                    g2.setPaintMode();
                    g2.drawLine(oldX, oldY, curX, curY);
                    break;
                case "rect":
                    // Remove the non-AA rubber band rectangle and draw a new AA-enabled one.
                    g2.drawPolygon(new int[]{oldX, curX, curX, oldX}, new int[]{oldY, oldY, curY, curY}, 4);
                    g2.setPaintMode();
                    g2.drawPolygon(new int[]{oldX, curX, curX, oldX}, new int[]{oldY, oldY, curY, curY}, 4);
                    break;
                case "oval":
                    // Width and height of the previous rubber band circle.
                    int width = Math.max(oldX, curX) - Math.min(oldX, curX);
                    int height = Math.max(oldY, curY) - Math.min(oldY, curY);
                    // Remove the non-AA rubber band circle and draw a new AA-enabled one.
                    g2.drawOval(Math.min(oldX, curX), Math.min(oldY, curY), width, height);
                    g2.setPaintMode();
                    g2.drawOval(Math.min(oldX, curX), Math.min(oldY, curY), width, height);
                }
                // Reset coordinates.
                curX = -1;
                curY = -1;
                repaint();
            }
        });
    }

    private void drawPencil(int x, int y) {
        // Draw a very short line segment.
        g2.drawLine(oldX, oldY, x, y);
        oldX = x;
        oldY = y;
    }

    private void drawEraser(int x, int y) {
        // Store previous color and set the color to white.
        Color temp = g2.getColor();
        g2.setColor(Color.WHITE);

        // Simulate erasing an area.
        g2.drawLine(oldX, oldY, x, y);
        oldX = x;
        oldY = y;

        // Restore the original color.
        g2.setColor(temp);
    }

    private void drawLine(int x, int y) {
        // Remove previous rubber band line.
        if (curX > 0 && curY > 0)
            g2.drawLine(oldX, oldY, curX, curY);

        // Show new rubber band line.
        g2.setXORMode(getBackground());
        g2.drawLine(oldX, oldY, x, y);
        curX = x;
        curY = y;
    }

    private void drawRect(int x, int y) {
        // Remove previous rubber band rectangle.
        if (curX > 0 && curY > 0)
            g2.drawPolygon(new int[]{oldX, curX, curX, oldX}, new int[]{oldY, oldY, curY, curY}, 4);

        // Show new rubber band rectangle.
        g2.setXORMode(getBackground());
        g2.drawPolygon(new int[]{oldX, x, x, oldX}, new int[]{oldY, oldY, y, y}, 4);
        curX = x;
        curY = y;
    }

    private void drawOval(int x, int y) {
        // Width and height of the previous rubber band circle.
        int width = Math.max(oldX, curX) - Math.min(oldX, curX);
        int height = Math.max(oldY, curY) - Math.min(oldY, curY);
        // Remove previous rubber band circle.
        if (curX > 0 && curY > 0)
            g2.drawOval(Math.min(oldX, curX), Math.min(oldY, curY), width, height);

        // Width and height of the new rubber band circle.
        width = Math.max(oldX, x) - Math.min(oldX, x);
        height = Math.max(oldY, y) - Math.min(oldY, y);
        // Show new rubber band circle.
        g2.setXORMode(getBackground());
        g2.drawOval(Math.min(oldX, x), Math.min(oldY, y), width, height);
        curX = x;
        curY = y;
    }

    void fillBackground(Color color) {
        Color oldColor = g2.getColor();
        g2.setColor(color);
        g2.fillRect(0, 0, Main.winWidth, Main.winHeight);
        g2.setColor(oldColor);
        repaint();
    }

    void invertColors() {
        g2.setXORMode(Color.BLACK);
        fillBackground(Color.WHITE);
        g2.setPaintMode();
    }

    @Override
    public Dimension getPreferredSize() {
        // Try to make the window at least 600 pixels tall.
        return new Dimension(0, Main.winHeight - 60);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
        // Enable antialiasing.
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
}

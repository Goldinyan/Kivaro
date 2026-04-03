package main.java.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ColorPickerPanel extends JPanel implements MouseListener, MouseMotionListener {

    private final Color[] colors = {
        Color.WHITE,
        Color.BLACK,
        Color.RED,
        Color.BLUE,
        Color.GREEN,
        Color.YELLOW,
        Color.PINK,
        Color.GRAY,
        Color.CYAN
    };

    private final String[] labels = {
        "White (1)",
        "Black (2)",
        "Red (3)",
        "Blue (4)",
        "Green (5)",
        "Yellow (6)",
        "Pink (7)",
        "Gray (8)",
        "Cyan (9)"
    };

    private Color currentColor = Color.WHITE;
    private int hoveredColorIndex = -1;

    private static final int CIRCLE_RADIUS = 25;
    private static final int PADDING_TOP = 50;
    private static final int PADDING_SIDES = 20;
    private static final int SPACING_BETWEEN = 30;
    private static final int LABEL_OFFSET = 45;
    private static final int PANEL_WIDTH = 200;

    private final CanvasWrapper canvasWrapper;

    public ColorPickerPanel(CanvasWrapper canvasWrapper) {
        this.canvasWrapper = canvasWrapper;
        setPreferredSize(new Dimension(PANEL_WIDTH, 600));
        setBackground(Color.BLACK);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g; // to 2D
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // keine ahnung aber das brauch es

        int centerX = getWidth() / 2 - PADDING_SIDES;

        for (int i = 0; i < colors.length; i++) {
            int circleY = PADDING_TOP + (i * (CIRCLE_RADIUS * 2 + SPACING_BETWEEN));

            // circle bg
            g2d.setColor(colors[i]);
            g2d.fillOval(centerX - CIRCLE_RADIUS, circleY - CIRCLE_RADIUS, CIRCLE_RADIUS * 2, CIRCLE_RADIUS * 2);

            // border
            if (colors[i].equals(currentColor)) {
                g2d.setColor(Color.YELLOW);
                g2d.setStroke(new BasicStroke(4));
            } else if (i == hoveredColorIndex) {
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.setStroke(new BasicStroke(2));
            } else {
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.setStroke(new BasicStroke(1));
            }
            g2d.drawOval(centerX - CIRCLE_RADIUS, circleY - CIRCLE_RADIUS, CIRCLE_RADIUS * 2, CIRCLE_RADIUS * 2);


            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            g2d.drawString(labels[i], centerX + LABEL_OFFSET, circleY + 5);
        }

    }

    private int getColorIndexAtPosition(int x, int y) {
        int centerX = getWidth() / 2 - PADDING_SIDES;

        for (int i = 0; i < colors.length; i++) {
            int circleY = PADDING_TOP + (i * (CIRCLE_RADIUS * 2 + SPACING_BETWEEN));
            int dx = x - centerX;
            int dy = y - circleY;
            int distanceSquared = dx * dx + dy * dy;

            if (distanceSquared <= CIRCLE_RADIUS * CIRCLE_RADIUS) {
                return i;
            }
        }
        return -1;
    }

    public void setCurrentColor(Color color) {
        this.currentColor = color;
        repaint();
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int colorIndex = getColorIndexAtPosition(e.getX(), e.getY());
        if (colorIndex >= 0) {
            currentColor = colors[colorIndex];
            //screenPanel.setCurrColor(currentColor);
            repaint();
        }
    }


    @Override
    public void mouseMoved(MouseEvent e) {
        int newHoveredIndex = getColorIndexAtPosition(e.getX(), e.getY());

        if (newHoveredIndex != hoveredColorIndex) {
            hoveredColorIndex = newHoveredIndex;
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {
        hoveredColorIndex = -1;
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {}
}

import main.java.ui.CanvasWrapper;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class UserManual extends JPanel implements MouseListener, MouseMotionListener {

    private String[][] keybinds = {{"X Align", "Align the ..."}};
    private CanvasWrapper canvasWrapper;
    private int hoveredColorIndex = -1;

    private static final int PANEL_WIDTH = 490;
    private static final int PANEL_HEIGHT = 880;



    public UserManual(CanvasWrapper canvasWrapper){
        this.canvasWrapper = canvasWrapper;
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Color.BLACK);
        addMouseListener(this);
        addMouseMotionListener(this);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);

        /* Graphics2D g2d = (Graphics2D) g; // to 2D
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // keine ahnung aber das brauch es

        int centerX = getWidth() / 2 - PADDING_SIDES;
        int startY = PADDING_TOP;

        for (int i = 0; i < colors.length; i++) {
            int circleY = startY + (i * (CIRCLE_RADIUS * 2 + SPACING_BETWEEN));

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
        } */
    }

    private int getColorIndexAtPosition(int x, int y) {
       /* int centerX = getWidth() / 2 - PADDING_SIDES;
        int startY = PADDING_TOP;

        for (int i = 0; i < colors.length; i++) {
            int circleY = startY + (i * (CIRCLE_RADIUS * 2 + SPACING_BETWEEN));
            int dx = x - centerX;
            int dy = y - circleY;
            int distanceSquared = dx * dx + dy * dy;

            if (distanceSquared <= CIRCLE_RADIUS * CIRCLE_RADIUS) {
                return i;
            }
        } */
        return -1;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        int colorIndex = getColorIndexAtPosition(e.getX(), e.getY());

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {}
}

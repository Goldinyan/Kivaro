import java.awt.*;
import java.awt.event.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

public class ManualButton extends JPanel implements MouseListener, MouseMotionListener {

    private boolean hovered = false;
    private final Runnable toggleManual;
    private final Runnable toggleManualAnimation;

    private final int BUTTON_WIDTH = 50;
    private final int BUTTON_HEIGHT = 36;
    private final int RADIUS = 16;
    private boolean isHovered = false;
    private int xPos = 0;




    Timer hoverAnimationOn = new Timer(16, e -> {
        if (hovered && xPos < 20) {
            xPos += 1;
            repaint();
        } else {
            ((Timer)e.getSource()).stop();
        }
    });

    Timer hoverAnimationOff = new Timer(16, e -> {
        if (!hovered && xPos >= 0) {
            xPos -= 1;
            repaint();
        } else {
            ((Timer)e.getSource()).stop();
        }
    });





    private RoundRectangle2D shape;

    public ManualButton(Runnable toggleManual, Runnable toggleManualAnimation) {
        this.toggleManual = toggleManual;
        this.toggleManualAnimation = toggleManualAnimation;
        setOpaque(false);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int x = getWidth() - BUTTON_WIDTH;
        int y = 0;

        shape = new RoundRectangle2D.Float(x, y, BUTTON_WIDTH, BUTTON_HEIGHT, RADIUS, RADIUS);

        g2.setClip(shape);

        g2.setColor(hovered ? new Color(70,70,70) : new Color(50,50,50));
        g2.fill(shape);

        g2.setColor(new Color(255,255,255,120));
        g2.setStroke(new BasicStroke(1.5f));
        g2.draw(shape);

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        int cx = x + BUTTON_WIDTH / 2 - 10 + xPos;   // center X
        int cy = y + BUTTON_HEIGHT / 2;  // center Y
        int size = 10;                   // halbe Pfeilgröße

        GeneralPath arrow = new GeneralPath();
        arrow.moveTo(cx - (float) size / 2, cy - size);   // oben links
        arrow.lineTo(cx + (float) size / 2, cy);          // mitte rechts
        arrow.lineTo(cx - (float) size / 2, cy + size); // unten links

        g2.draw(arrow);

        g2.dispose();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (shape != null && shape.contains(e.getPoint())) {
            toggleManual.run();
            toggleManualAnimation.run();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        boolean nowHovered = shape != null && shape.contains(e.getPoint());
        if (nowHovered != hovered) {
            hovered = nowHovered;

            if(hovered){
                hoverAnimationOn.start();
            } else {
                hoverAnimationOff.start();
            }

            repaint();
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) { hovered = false; repaint(); hoverAnimationOff.start();}
    @Override public void mouseDragged(MouseEvent e) {}
}

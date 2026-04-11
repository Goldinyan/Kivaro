package main.java.ui.Panels;

import main.java.context.EditorContext;
import main.java.ui.Buttons.MacButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class TopLeftButtonsPanel extends JPanel implements MouseListener, MouseMotionListener
{
    private final EditorContext ctx;


    public TopLeftButtonsPanel(EditorContext ctx) {
        this.ctx = ctx;

        setLayout(null);

        initButtons();
    }

    private void initButtons() {
        MacButton close = new MacButton(MacButton.Type.CLOSE);
        MacButton minimize = new MacButton(MacButton.Type.MINIMIZE);
        MacButton zoom = new MacButton(MacButton.Type.ZOOM);

        close.setBounds(10, 10, 14, 14);
        minimize.setBounds(30, 10, 14, 14);
        zoom.setBounds(50, 10, 14, 14);

        close.addActionListener(e -> System.exit(0));
        minimize.addActionListener(e ->
                ctx.window.setState(JFrame.ICONIFIED)
        );

        zoom.addActionListener(e -> {
            int state = ctx.window.getExtendedState();

            if ((state & JFrame.MAXIMIZED_BOTH) != 0) {
                ctx.window.setExtendedState(JFrame.NORMAL);
                ctx.window.setMinimumSize(new Dimension(800, 600));
            } else {
                ctx.window.setMinimumSize(null); // wichtig!
                ctx.window.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });


        add(close);
        add(minimize);
        add(zoom);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        setBackground(ctx.ctxManager.colorCtx.getTheme().background2);

        draw_border_right(g);
        draw_header(g);
    }

    private void draw_border_right(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;

        g2.setStroke(new BasicStroke(1.5F)); // 2px dick

        int w = getWidth();
        int h = getHeight();

        g2.setColor(ctx.ctxManager.colorCtx.getTheme().border);
        g2.drawLine(0, h, w, h);

        g2.setColor(Color.BLACK); // oder Theme-Farbe

        // Rechtslinie (x = w - 1)
        g2.drawLine(w - 1, 0, w - 1, h);


    }

    private void draw_header(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;

        String text = "Tools";

        g2.setFont(g2.getFont().deriveFont(12f)); // z.B. 12px
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent() - fm.getDescent();

        int drawX = 15 + (textWidth) / 2; // IHH magic numbers aber muss so ist halt ui leck eier
        int drawY = 47 + (textHeight) / 2;

        g2.setColor(ctx.ctxManager.colorCtx.getTheme().accent);

        g2.drawString(text, drawX, drawY);
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
    }

    @Override
    public void mousePressed(MouseEvent e)
    {

    }

    @Override
    public void mouseReleased(MouseEvent e)
    {

    }

    @Override
    public void mouseEntered(MouseEvent e)
    {

    }

    @Override
    public void mouseExited(MouseEvent e)
    {

    }

    @Override
    public void mouseDragged(MouseEvent e)
    {

    }

    @Override
    public void mouseMoved(MouseEvent e)
    {

    }
}

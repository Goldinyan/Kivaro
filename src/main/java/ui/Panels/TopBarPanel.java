package main.java.ui.Panels;

import main.java.context.EditorContext;
import main.java.tools.PencilTool;
import main.java.tools.Tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class TopBarPanel extends JPanel implements MouseListener, MouseMotionListener
{
    private int WIDHT, HEIGHT;
    private EditorContext ctx;
    private final int LEFT_PANEL_PADDING = 94;
    private Point clickOffset = null;


    public TopBarPanel(int WIDTH, int HEIGHT, EditorContext ctx)
    {
        this.WIDHT = WIDTH;
        this.HEIGHT = HEIGHT;
        this.ctx = ctx;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(ctx.ctxManager.colorCtx.getTheme().background2);


        setLayout(new BorderLayout());

        // Linkes Panel
        TopLeftButtonsPanel left = new TopLeftButtonsPanel(ctx);
        left.setPreferredSize(new Dimension(LEFT_PANEL_PADDING, HEIGHT));
        add(left, BorderLayout.WEST);

        // Restliche TopBar (dieses Panel selbst)


        addMouseListener(this);
        addMouseMotionListener(this);

    }


    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        // damit wird alles und das was ich als custom code habe gemacht ohne nur mein code

        setBackground(ctx.ctxManager.colorCtx.getTheme().grid1);

        draw_border_bottom(g);
        draw_header(g);
    }

    private void draw_header(Graphics g){
        Graphics2D g2 = (Graphics2D) g;

        String text = ctx.ctxManager.canvasCtx.name;


        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 12f));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent() - fm.getDescent();

        int drawX = LEFT_PANEL_PADDING + (textWidth) / 2;
        int drawY = 15 + (textHeight) / 2;

        g2.setColor(ctx.ctxManager.colorCtx.getTheme().text);
        g2.drawString(text, drawX, drawY);

        if(!ctx.ctxManager.canvasCtx.saved){
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 10f)); // z.B. 12px
            g2.setColor(ctx.ctxManager.colorCtx.getTheme().text2);
            g2.drawString("Edited", drawX, drawY + 12);
        }
    }

    private void draw_border_bottom(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;

        g2.setStroke(new BasicStroke(3F)); // 2px dick

        int w = getWidth();
        int h = getHeight();

        g2.setColor(Color.BLACK);

        g2.drawLine(0, h, w, h);
    }


    @Override
    public void mouseClicked(MouseEvent e)
    {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Nur wenn wir nicht im Resize-Border sind (oben 6px), dann fenster bewegen
        if (e.getY() >= 6) {
            clickOffset = e.getPoint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (clickOffset == null) return;

        if (e.getY() <= 6) return;

        Point screen = e.getLocationOnScreen();
        ctx.window.setLocation(
                screen.x - clickOffset.x,
                screen.y - clickOffset.y
        );
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        clickOffset = null;
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
    public void mouseMoved(MouseEvent e)
    {

    }
}

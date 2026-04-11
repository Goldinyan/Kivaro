package main.java.ui;

import main.java.context.EditorContext;
import main.java.tools.PaintTool;
import main.java.tools.PencilTool;
import main.java.tools.Tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class LeftPanel extends JPanel implements MouseListener, MouseMotionListener
{
    private int WIDHT, HEIGHT;
    private EditorContext ctx;
    private ArrayList<Tool> tools = new ArrayList<Tool>();
    private int pictureSize = 20;


    public LeftPanel(int WIDTH, int HEIGHT, EditorContext ctx)
    {
        this.WIDHT = WIDTH;
        this.HEIGHT = HEIGHT;
        this.ctx = ctx;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(ctx.ctxManager.colorCtx.getTheme().background1);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    private void initTools()
    {
        tools.add(new PencilTool());
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        for (int i = 0; i < tools.size(); i++)
        {
            System.out.println(i);
        }

        /*
        public class ImagePanel extends JPanel {

    private final BufferedImage img;

    public ImagePanel() {
        try {
            img = ImageIO.read(new File("assets/sprite.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // WICHTIG: Pixelart scharf rendern
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                            RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                            RenderingHints.VALUE_RENDER_SPEED);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_OFF);

        // Beispiel: PNG 4x vergrößert zeichnen
        int scale = 4;
        g2.drawImage(img, 0, 0, img.getWidth() * scale, img.getHeight() * scale, null);
    }
}
         */
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

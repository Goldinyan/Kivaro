package main.java.ui.Panels;

import main.java.context.EditorContext;
import main.java.tools.PencilTool;
import main.java.tools.Tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LeftPanel extends JPanel implements MouseListener, MouseMotionListener
{
    private int WIDHT, HEIGHT;
    private final EditorContext ctx;
    private ArrayList<Tool> tools = new ArrayList<Tool>();
    private final int picturePadding = 15;


    public LeftPanel(int WIDTH, int HEIGHT, EditorContext ctx)
    {
        this.WIDHT = WIDTH;
        this.HEIGHT = HEIGHT;
        this.ctx = ctx;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(ctx.ctxManager.colorCtx.getTheme().background2);
        addMouseListener(this);
        addMouseMotionListener(this);

        initTools();
    }


    private void initTools()
    {
        tools.add(new PencilTool());
        tools.add(new PencilTool());
        tools.add(new PencilTool());
        tools.add(new PencilTool());
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        // damit wird alles und das was ich als custom code habe gemacht ohne nur mein code

        setBackground(ctx.ctxManager.colorCtx.getTheme().background2);



        draw_tools(g);
        draw_border_right(g);

        // draw_header(g, 0, 0);

    }

    private void draw_border_right(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK); // oder Theme-Farbe

        g2.setStroke(new BasicStroke(1.5F)); // 2px dick

        int w = getWidth();
        int h = getHeight();

        // Rechtslinie (x = w - 1)
        g2.drawLine(w - 1, -1, w - 1, h);
    }



    private void draw_tools(Graphics g){
        for (int i = 0; i < tools.size(); i++)
        {
            BufferedImage img = tools.get(i).getImage();
            Graphics2D g2 = (Graphics2D) g;

            // für scharfes rendering
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_SPEED);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_OFF);

            int scale = 2;

            int col = i % 2;      // 0 -<links; 1 -> rechts
            int row = i / 2;      // jede 2 Icons ne neue zeile


            int pic_w = img.getWidth() * scale;
            int pic_h = img.getWidth() * scale;

            int x = col * (pic_w + picturePadding);
            int y = row * (pic_h + picturePadding);

            int pic_x = x + (picturePadding / 2);
            int pic_y = y + (picturePadding / 2);

            int w = (img.getWidth() + (picturePadding / 2)) * scale;
            int h = (img.getHeight() + (picturePadding / 2)) * scale;

            g2.drawImage(img, pic_x, pic_y, pic_w, pic_h, null);

            g2.setStroke(new BasicStroke(0.3F)); // 3px dick
            g2.setColor(ctx.ctxManager.colorCtx.getTheme().border);
            g2.drawRect(x, y, w, h);


        }
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

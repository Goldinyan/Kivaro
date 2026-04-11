package main.java.ui.Buttons;

import javax.swing.*;
import java.awt.*;

public class MacButton extends JButton
{
    public enum Type
    {CLOSE, MINIMIZE, ZOOM}

    private final Type type;

    public MacButton(Type type)
    {
        this.type = type;
        setPreferredSize(new Dimension(14, 14));
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(false);

        setRolloverEnabled(true);

    }

    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        switch (type)
        {
            case CLOSE -> g2.setColor(new Color(0xFF5F57));
            case MINIMIZE -> g2.setColor(new Color(0xFEBC2E));
            case ZOOM -> g2.setColor(new Color(0x28C840));
        }
        g2.fillOval(0, 0, 14, 14);

        // nur bei hover
        if (getModel().isRollover())
        {
            drawSymbol(g2);
        }
    }

    private void drawSymbol(Graphics2D g2)
    {
        g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        switch (type)
        {
            case CLOSE ->
            {
                g2.setColor(new Color(0x4A0000));
                g2.drawLine(4, 4, 10, 10);
                g2.drawLine(10, 4, 4, 10);
            }
            case MINIMIZE ->
            {
                g2.setColor(new Color(0x5A3E00));
                g2.drawLine(4, 7, 10, 7);
            }
            case ZOOM ->
            {
                g2.setColor(new Color(0x005A1C));
                g2.drawLine(4, 7, 10, 7);
                g2.drawLine(7, 4, 7, 10);
            }
        }
    }
}


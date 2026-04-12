package main.java.ui;

import javax.swing.*;
import java.awt.*;

public class BlurredOverlay extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(new Color(0, 50, 0, 20));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
    }
}



/*
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class BlurredOverlay extends JPanel
{

    private BufferedImage blurred;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (blurred == null) {
            blurred = createBlurredBackground();
        }

        g.drawImage(blurred, 0, 0, null);

        // optionaler Tint
        g.setColor(new Color(0, 0, 0, 80));
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private BufferedImage createBlurredBackground() {
        try {
            // Screenshot des gesamten Bildschirms
            Robot robot = new Robot();
            Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenshot = robot.createScreenCapture(screen);

            // Bereich ausschneiden, wo das Overlay sitzt
            Point p = getLocationOnScreen();
            BufferedImage region = screenshot.getSubimage(
                    p.x, p.y, getWidth(), getHeight()
            );

            // Blur anwenden
            return gaussianBlur(region, 12);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private BufferedImage gaussianBlur(BufferedImage img, int radius) {
        float[] matrix = createGaussianKernel(radius);
        BufferedImageOp op = new ConvolveOp(new Kernel(radius, radius, matrix),
                ConvolveOp.EDGE_NO_OP, null);
        return op.filter(img, null);
    }

    private float[] createGaussianKernel(int radius) {
        float[] data = new float[radius * radius];
        float sigma = radius / 3f;
        float sum = 0;
        int index = 0;

        for (int y = -radius/2; y <= radius/2; y++) {
            for (int x = -radius/2; x <= radius/2; x++) {
                float value = (float) Math.exp(-(x*x + y*y) / (2 * sigma * sigma));
                data[index++] = value;
                sum += value;
            }
        }

        // normalisieren
        for (int i = 0; i < data.length; i++) {
            data[i] /= sum;
        }

        return data;
    }
}
*/
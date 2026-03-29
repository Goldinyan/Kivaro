package main.java.graphics;

import java.awt.image.BufferedImage;

public class Layer {
    private BufferedImage image;
    private boolean visible = true;
    private float opacity = 1.0f;
    private String name;

    public Layer(int width, int height, String name) {
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.name = name;
    }

    public BufferedImage getImage() {
        return image;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean v) {
        visible = v;
    }

    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float o) {
        opacity = o;
    }

    public String getName() {
        return name;
    }
}

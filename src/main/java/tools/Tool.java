package main.java.tools;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface Tool {
    BufferedImage getImage();

    void onMouseDown(MouseEventContext mEv);
    void onMouseDrag(MouseEventContext mEv);
    void onMouseUp(MouseEventContext mEv);
    void renderPreview(Graphics2D g);

}

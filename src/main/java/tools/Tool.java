package main.java.tools;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface Tool {
    BufferedImage getImage();

    void onMouseDown(MouseEvent mEv);
    void onMouseDrag(MouseEvent mEv);
    void onMouseUp(MouseEvent mEv);
    void renderPreview(Graphics2D g);

}

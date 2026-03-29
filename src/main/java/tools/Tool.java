package main.java.tools;

import java.awt.*;

public interface Tool {
    void onMouseDown(MouseEvent mEv);
    void onMouseDrag(MouseEvent mEv);
    void onMouseUp(MouseEvent mEv);
    void renderPreview(Graphics2D g);

}

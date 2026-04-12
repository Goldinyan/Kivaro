package main.java.tools;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ToolMachine implements Tool {
    private Tool current;

    public void set(Tool next) {
        current = next;
    }

    @Override
    public BufferedImage getImage()
    {
        return null;
    }

    @Override
    public void onMouseDown(MouseEventContext mEv) {
        current.onMouseDown(mEv);
    }

    @Override
    public void onMouseDrag(MouseEventContext mEv) {
        current.onMouseDrag(mEv);
    }

    @Override
    public void onMouseUp(MouseEventContext mEv) {
    }

    public Tool getTool(){
        return current;
    }

    @Override
    public void renderPreview(Graphics2D g) {

    }
}

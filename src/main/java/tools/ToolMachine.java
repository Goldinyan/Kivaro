package main.java.tools;

import main.java.core.LayerManager;

import java.awt.*;

public class ToolMachine implements Tool {
    private Tool current;

    public void set(Tool next) {
        current = next;
    }

    @Override
    public void onMouseDown(MouseEvent mEv) {
        current.onMouseDown(mEv);
    }

    @Override
    public void onMouseDrag(MouseEvent mEv) {
        current.onMouseDrag(mEv);
    }

    @Override
    public void onMouseUp(MouseEvent mEv) {
    }

    public Tool getTool(){
        return current;
    }

    @Override
    public void renderPreview(Graphics2D g) {

    }
}

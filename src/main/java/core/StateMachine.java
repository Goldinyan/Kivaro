package main.java.core;

import main.java.state.EditorState;

import java.awt.*;

public class StateMachine {
    private EditorState current;

    public void set(EditorState next) {
        if (current != null) current.onExit();
        current = next;
        current.onEnter();
    }

    public void update() {
        current.update();
    }

    public void render(Graphics2D g) {
        current.render(g);
    }

    public void mouseDown(int x, int y) {
        current.onMouseDown(x, y);
    }

    public void mouseUp(int x, int y){
        current.onMouseUp(x, y);
    }

    public void mouseDrag(int x, int y) {
        current.onMouseDrag(x, y);
    }
}

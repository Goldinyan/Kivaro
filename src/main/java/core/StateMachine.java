package main.java.core;

import main.java.state.EditorState;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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

    public void mouseDown(int x, int y, MouseEvent e) {
        current.onMouseDown(x, y, e);
    }

    public void mouseUp(int x, int y, MouseEvent e){
        current.onMouseUp(x, y, e);
    }

    public void mouseDrag(int x, int y, MouseEvent e) {
        current.onMouseDrag(x, y, e);
    }
}

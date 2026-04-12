package main.java.state;


import java.awt.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public interface EditorState {
        void onEnter();
        void onExit();
        void update();
        void render(Graphics2D g);
        void onMouseDown(int x, int y, MouseEvent e);
        void onMouseDrag(int x, int y, MouseEvent e);
        void onMouseUp(int x, int y, MouseEvent e);
}

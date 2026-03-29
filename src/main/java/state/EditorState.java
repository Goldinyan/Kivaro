package main.java.state;

import java.awt.*;

public interface EditorState {
        void onEnter();
        void onExit();
        void update();
        void render(Graphics2D g);
        void onMouseDown(int x, int y);
        void onMouseDrag(int x, int y);
        void onMouseUp(int x, int y);
}

package main.java.tools;

import main.java.core.LayerManager;

public class MouseEvent {
    public final int x;
    public final int y;
    public LayerManager lm = null;

    public MouseEvent(int x, int y, LayerManager lm) {
        this.x = x;
        this.y = y;
        this.lm = lm;
    }

}

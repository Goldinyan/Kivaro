package main.java.state;

import main.java.context.EditorContext;
import main.java.tools.MouseEvent;

import java.awt.*;

public class DrawingState implements EditorState {
    private final EditorContext ctx;
    private MouseEvent mEv;

    public DrawingState(EditorContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void onEnter() {

    }

    @Override
    public void onExit() {

    }

    @Override
    public void update() {

    }

    @Override
    public void onMouseDown(int x, int y) {
        mEv = new MouseEvent(x, y , ctx.layers);
        ctx.tools.getActiveMachine().onMouseDown(mEv);
    }

    @Override
    public void onMouseDrag(int x, int y) {
        mEv = new MouseEvent(x, y , ctx.layers);
        ctx.tools.getActiveMachine().onMouseDrag(mEv);
    }

    @Override
    public void onMouseUp(int x, int y) {
        mEv = new MouseEvent(x, y , ctx.layers);
        ctx.tools.getActiveMachine().onMouseUp(mEv);
    }

    @Override
    public void render(Graphics2D g) {
        ctx.canvas.render(g);
        ctx.tools.getActiveMachine().renderPreview(g);
    }
}


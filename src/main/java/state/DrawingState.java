package main.java.state;

import main.java.context.EditorContext;
import main.java.tools.MouseEventContext;
import javax.swing.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


import java.awt.*;

public class DrawingState implements EditorState {
    private final EditorContext ctx;
    private MouseEventContext mEv;

    private int startX;
    private int startY;

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
    public void onMouseDown(int x, int y, MouseEvent e) {
        startX = x;
        startY = y;

        mEv = new MouseEventContext(x, y , ctx.layers);
        ctx.tools.getActiveMachine().onMouseDown(mEv);
    }

    @Override
    public void onMouseDrag(int x, int y, MouseEvent e) {
        if ((e.getModifiersEx() & MouseEvent.BUTTON3_DOWN_MASK) != 0) {
            int x_diff = startX - x;
            int y_diff = startY - y;

            ctx.ctxManager.canvasCtx.worldY -= (int) (y_diff * ctx.ctxManager.canvasCtx.zoom);
            ctx.ctxManager.canvasCtx.worldX -= (int) (x_diff * ctx.ctxManager.canvasCtx.zoom);

            System.out.println(x_diff + "   " + y_diff);
        }

        System.out.println("drag left");


        mEv = new MouseEventContext(x, y , ctx.layers);
        ctx.tools.getActiveMachine().onMouseDrag(mEv);
    }

    @Override
    public void onMouseUp(int x, int y, MouseEvent e) {
        mEv = new MouseEventContext(x, y , ctx.layers);
        ctx.tools.getActiveMachine().onMouseUp(mEv);
    }

    @Override
    public void render(Graphics2D g) {
        ctx.canvas.render(g);
        ctx.tools.getActiveMachine().renderPreview(g);
    }
}


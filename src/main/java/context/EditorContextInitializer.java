package main.java.context;

import main.java.context.colors.ColorContext;
import main.java.core.*;
import main.java.core.Canvas;
import main.java.graphics.Layer;
import main.java.state.DrawingState;
import main.java.tools.PencilTool;
import main.java.tools.ToolMachine;

import java.awt.*;

public class EditorContextInitializer {

    public EditorContext create() {
        EditorContext ctx = new EditorContext();

        initScreen(ctx);
        initTools(ctx);
        initStates(ctx);
        initManagers(ctx);
        initColors(ctx);



        return ctx;
    }

    private void initScreen(EditorContext ctx) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        ctx.ctxManager = new ContextManager();
        ctx.ctxManager.canvasCtx = new CanvasContext();
        ctx.ctxManager.canvasCtx.MAXWIDTH = screen.width;
        ctx.ctxManager.canvasCtx.MAXHEIGHT = screen.height;
        ctx.ctxManager.canvasCtx.WIDTH = (int) (screen.width * 0.7);
        ctx.ctxManager.canvasCtx.HEIGHT = (int) (screen.height * 0.7);
        ctx.ctxManager.canvasCtx.zoom = 1f;
        ctx.ctxManager.canvasCtx.worldX = (int) ((ctx.ctxManager.canvasCtx.MAXWIDTH - ctx.ctxManager.canvasCtx.WIDTH) / 2);
        ctx.ctxManager.canvasCtx.worldY = (int) ((ctx.ctxManager.canvasCtx.MAXHEIGHT - ctx.ctxManager.canvasCtx.HEIGHT) / 2);

    }


    private void initTools(EditorContext ctx) {
        ctx.tools = new ToolManager();
        ctx.tools.setToolMachine(new ToolMachine());
        ctx.tools.getActiveMachine().set(new PencilTool());
    }

    private void initManagers(EditorContext ctx) {
        ctx.canvas = new Canvas(ctx);
        ctx.layers = new LayerManager();
        ctx.layers.addLayer(new Layer(ctx.ctxManager.canvasCtx.WIDTH, ctx.ctxManager.canvasCtx.HEIGHT, "Layer 1" ));
        ctx.plugins = new PluginManager();
    }

    private void initStates(EditorContext ctx) {
        ctx.states = new StateMachine();
        ctx.states.set(new DrawingState(ctx));
    }

    private void initColors(EditorContext ctx){
        ctx.ctxManager.colorCtx = new ColorContext();
        ctx.ctxManager.canvasCtx.GRID_CELL_SIZE = 32;
    }
}


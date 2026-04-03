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

    public static void initScreen(EditorContext ctx) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        ctx.ctxManager = new ContextManager();
        ctx.ctxManager.canvasCtx = new CanvasContext();

        ctx.ctxManager.canvasCtx.MAXWIDTH = 1;
        ctx.ctxManager.canvasCtx.MAXHEIGHT = 1;

        ctx.ctxManager.canvasCtx.zoom = 1f;

    }

    public static void initTools(EditorContext ctx) {
        ctx.tools = new ToolManager();
        ctx.tools.setToolMachine(new ToolMachine());
        ctx.tools.getActiveMachine().set(new PencilTool());
    }

    public static void initStates(EditorContext ctx) {
        ctx.states = new StateMachine();
        ctx.states.set(new DrawingState(ctx));
    }

    public static void initColors(EditorContext ctx){
        ctx.ctxManager.colorCtx = new ColorContext();
        ctx.ctxManager.canvasCtx.GRID_CELL_SIZE = 32;
    }

    // bc we need the width, but we set it later in EditorWindow
    public static void initManagers(EditorContext ctx) {
        ctx.canvas = new Canvas(ctx);
        ctx.layers = new LayerManager();
        ctx.plugins = new PluginManager();
    }
}


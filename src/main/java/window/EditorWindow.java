package main.java.window;

import main.java.context.EditorContext;
import main.java.context.EditorContextInitializer;
import main.java.graphics.Layer;
import main.java.ui.BlurredOverlay;
import main.java.ui.CanvasCreateDialog;
import main.java.ui.CanvasWrapper;
import main.java.ui.UIManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class EditorWindow extends JFrame {

    private final UIManager uiManager;
    private final EditorContext ctx;

    public EditorWindow() {
        super("Kivaro");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);

        ctx = initContext();
        
        JPanel mainContent = new JPanel(new BorderLayout());
        JPanel overlay = createOverlay();
        setGlassPane(overlay);
        
        uiManager = new UIManager(mainContent, overlay);
        
        buildLayout();
        
        add(mainContent);
        uiManager.showPanel("editor");
        uiManager.printDebugInfo();
        
        showCreateDialog();
        setVisible(true);

        new Timer(16, e -> repaint()).start();
    }

    private EditorContext initContext() {
        EditorContext context = new EditorContext();
        EditorContextInitializer.initScreen(context);
        EditorContextInitializer.initTools(context);
        EditorContextInitializer.initStates(context);
        EditorContextInitializer.initColors(context);
        EditorContextInitializer.initManagers(context);
        return context;
    }

    private void buildLayout() {
        uiManager.registerLayout("editor", new JPanel(), new BorderLayout());
        
        JPanel leftSidebar = createLeftSidebar();
        JPanel canvasWrapper = createCanvasWrapper();
        
        uiManager.addToLayout("editor", "sidebar", leftSidebar, 250, 0, BorderLayout.WEST);
        uiManager.addToLayout("editor", "canvas", canvasWrapper, 0, 0, BorderLayout.CENTER);
    }

    private JPanel createLeftSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(Color.GRAY);
        return sidebar;
    }

    private JPanel createCanvasWrapper() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.DARK_GRAY);
        
        CanvasWrapper canvasWrapper = new CanvasWrapper(ctx);
        wrapper.add(canvasWrapper, BorderLayout.CENTER);
        
        wrapper.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ctx.ctxManager.canvasCtx.MAXWIDTH = wrapper.getWidth();
                ctx.ctxManager.canvasCtx.MAXHEIGHT = wrapper.getHeight();
                ctx.ctxManager.canvasCtx.WIDTH = (int) (wrapper.getWidth() * 0.7);
                ctx.ctxManager.canvasCtx.HEIGHT = (int) (wrapper.getHeight() * 0.7);
                ctx.ctxManager.canvasCtx.worldX = 
                    (ctx.ctxManager.canvasCtx.MAXWIDTH - ctx.ctxManager.canvasCtx.WIDTH) / 2;
                ctx.ctxManager.canvasCtx.worldY = 
                    (ctx.ctxManager.canvasCtx.MAXHEIGHT - ctx.ctxManager.canvasCtx.HEIGHT) / 2;
            }
        });
        
        ctx.layers.addLayer(new Layer(30, 30, "Layer 1"));
        
        return wrapper;
    }

    private JPanel createOverlay() {
        BlurredOverlay overlay = new BlurredOverlay();
        overlay.setOpaque(false);
        overlay.setLayout(new BorderLayout());
        overlay.setVisible(false);
        return overlay;
    }

    private void showCreateDialog() {
        CanvasCreateDialog dialog = new CanvasCreateDialog(ctx, uiManager);
        uiManager.showOverlay(dialog.build());
    }

    public UIManager getUIManager() {
        return uiManager;
    }
}

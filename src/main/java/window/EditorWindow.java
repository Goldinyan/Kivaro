package main.java.window;

import main.java.context.EditorContext;
import main.java.context.EditorContextInitializer;
import main.java.graphics.Layer;
import main.java.ui.*;
import main.java.ui.UIManager;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Objects;

public class EditorWindow extends JFrame
{

    private final UIManager uiManager;
    private final EditorContext ctx;

    public EditorWindow()
    {
        super("Kivaro");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);
        setMinimumSize(new Dimension(800, 500));  // min 800px breite, 500px höhe

        // System.out.println("RESOURCE = " + getClass().getResource("/assets/KivaroIcon.png"));

        setIconImage(
                new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/KivaroIcon.png"))).getImage()
        );
        Taskbar.getTaskbar().setIconImage(
                new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/KivaroIcon.png"))).getImage()
        );





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

    private EditorContext initContext()
    {
        EditorContext context = new EditorContext();
        EditorContextInitializer.initScreen(context);
        EditorContextInitializer.initTools(context);
        EditorContextInitializer.initStates(context);
        EditorContextInitializer.initColors(context);
        EditorContextInitializer.initManagers(context);
        return context;
    }

    private void buildLayout()
    {
        uiManager.registerLayout("editor", new JPanel(), new BorderLayout());

        int WIDTH = ctx.ctxManager.appContext.WIDTH;
        int HEIGHT = ctx.ctxManager.appContext.HEIGHT;

        int ls_width = 100;
        int ls_height = HEIGHT;
        JPanel leftSidebar = new LeftPanel(ls_width, ls_height, ctx);


        int tb_height = 30;
        int tb_width = (int) (WIDTH - ls_width);
        JPanel topBar = createTopBar();


        int rs_width = 200;
        int rs_height = HEIGHT - tb_height;
        JPanel rightSidebar = createRightSidebar();


        int bb_height = 30;
        int bb_width = WIDTH - ls_width - rs_width;
        JPanel bottomBar = createBottomBar();

        int cw_width = WIDTH - ls_width - rs_width;
        int cw_height = HEIGHT - tb_height - bb_height;
        JPanel canvasWrapper = createCanvasWrapper();

        uiManager.addToLayout("editor", "topBar", topBar, tb_width, tb_height, BorderLayout.NORTH);
        uiManager.addToLayout("editor", "rightSidebar", rightSidebar, rs_width, rs_height, BorderLayout.EAST);
        uiManager.addToLayout("editor", "leftSidebar", leftSidebar, ls_width, ls_height, BorderLayout.WEST);
        uiManager.addToLayout("editor", "bottomBar", bottomBar, bb_width, bb_height, BorderLayout.SOUTH);
        uiManager.addToLayout("editor", "canvas", canvasWrapper, cw_width, cw_height, BorderLayout.CENTER);
    }


    private @NotNull JPanel createTopBar()
    {
        JPanel topBar = new JPanel();
        topBar.setBackground(Color.BLUE);
        return topBar;
    }

    private @NotNull JPanel createBottomBar()
    {
        JPanel bottomBar = new JPanel();
        bottomBar.setBackground(Color.RED);
        return bottomBar;
    }

    private @NotNull JPanel createRightSidebar()
    {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(Color.YELLOW);
        return sidebar;
    }

    private @NotNull JPanel createCanvasWrapper()
    {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.YELLOW);

        CanvasWrapper canvasWrapper = new CanvasWrapper(ctx);
        wrapper.add(canvasWrapper, BorderLayout.CENTER);

        wrapper.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
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

    private @NotNull JPanel createOverlay()
    {
        BlurredOverlay overlay = new BlurredOverlay();
        overlay.setOpaque(false);
        overlay.setLayout(new BorderLayout());
        overlay.setVisible(false);
        return overlay;
    }

    private void showCreateDialog()
    {
        CanvasCreateDialog dialog = new CanvasCreateDialog(ctx, uiManager);
        uiManager.showOverlay(dialog.build());
    }

    public UIManager getUIManager()
    {
        return uiManager;
    }
}

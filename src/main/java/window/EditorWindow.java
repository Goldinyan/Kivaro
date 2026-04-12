package main.java.window;

import main.java.context.EditorContext;
import main.java.context.EditorContextInitializer;
import main.java.graphics.Layer;
import main.java.ui.*;
import main.java.ui.Panels.CanvasWrapper;
import main.java.ui.Panels.LeftPanel;
import main.java.ui.Panels.TopBarPanel;
import main.java.ui.UIManager;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Objects;

public class EditorWindow extends JFrame
{

    private EditorContext ctx;
    private UIManager uiManager;

    public EditorWindow()
    {
        super("Kivaro");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1200, 800));
        setResizable(true);
        setLocationRelativeTo(null);
        setUndecorated(true);
        /*setShape(new java.awt.geom.RoundRectangle2D.Double(
                0, 0,
                getWidth(),
                getHeight(),
                20, 20 // arcWidth, arcHeight
        ));*/
        // sonst geht maximize nicht

        setMinimumSize(new Dimension(800, 500));  // min 800px breite, 500px höhe

        // System.out.println("RESOURCE = " + getClass().getResource("/assets/KivaroIcon.png"));

        setIconImage(
                new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/KivaroIcon.png"))).getImage()
        );
        Taskbar.getTaskbar().setIconImage(
                new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/KivaroIcon.png"))).getImage()
        );


        ctx = Helper.initContext(this);

        System.out.println(ctx.ctxManager.colorCtx.getTheme().background1);

        JPanel mainContent = getMainContentPanel();


        JPanel overlay = createOverlay();
        setGlassPane(overlay);

        uiManager = new UIManager(mainContent, overlay);

       buildLayout();

        add(mainContent);
        uiManager.showPanel("editor");

        //showCreateDialog();
        openNewWindow();
        setVisible(true);

        new WindowResizeHandler(this, overlay);


        Timer GLOBAL_RENDER_TIMER = new Timer(16, e ->
        {
            repaint();
        });

        GLOBAL_RENDER_TIMER.start();
    }

    @Contract(" -> new") // intellij immer neues object
    private @NotNull JPanel getMainContentPanel()
    {
        return new JPanel(new BorderLayout())
        {
/*
            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();

                float t = 10f;
                g2.setColor(ctx.ctxManager.colorCtx.getTheme().border);
                g2.setStroke(new BasicStroke(t));

                int w = getWidth();
                int h = getHeight();

                g2.drawRect(
                        (int)(t / 2),
                        (int)(t / 2),
                        (int)(w - t),
                        (int)(h - t)
                );

                g2.dispose();
            }  */
        };

    }



    private void buildLayout()
    {
        uiManager.registerLayout("editor", new JPanel(), new BorderLayout());

        int WIDTH = ctx.ctxManager.appContext.WIDTH;
        int HEIGHT = ctx.ctxManager.appContext.HEIGHT;

        int ls_width = 94;
        int ls_height = HEIGHT;
        JPanel leftSidebar = new LeftPanel(ls_width, ls_height, ctx);


        int tb_height = 60;
        int tb_width = (int) (WIDTH - ls_width);
        JPanel topBar = new TopBarPanel(tb_width, tb_height, ctx);


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


    private @NotNull JPanel createBottomBar()
    {
        JPanel bottomBar = new JPanel();
        bottomBar.setBackground(ctx.ctxManager.colorCtx.getTheme().background4);
        return bottomBar;
    }

    private @NotNull JPanel createRightSidebar()
    {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(ctx.ctxManager.colorCtx.getTheme().background3);
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

    public void openNewWindow() {
        JFrame win = new JFrame("Canvas Settings");
        win.add(new CanvasCreateDialog(ctx, this::buildLayout).build());
        win.pack();
        win.setLocationRelativeTo(null);
        win.setVisible(true);
    }


    private @NotNull JPanel createOverlay()
    {
        BlurredOverlay overlay = new BlurredOverlay();
        overlay.setOpaque(false);
        overlay.setLayout(new BorderLayout());
        overlay.setVisible(true);  // Immer sichtbar, damit Events durchkommen
        return overlay;
    }

//    private void showCreateDialog()
//    {
//        CanvasCreateDialog dialog = new CanvasCreateDialog(ctx, uiManager);
//        uiManager.showOverlay(dialog.build());
//    }

    public UIManager getUIManager()
    {
        return uiManager;
    }
}

package main.java.window;

import main.java.context.EditorContext;
import main.java.context.EditorContextInitializer;
import main.java.ui.ScreenPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class EditorWindow extends JFrame {

    public EditorWindow() {
        super("Kivaro");

        // Context
        EditorContext ctx = new EditorContextInitializer().create();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);

        // LEFT PANEL
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(/*(int) (ctx.ctxManager.canvasCtx.MAXWIDTH * 0.2)*/ 250, 0));
        leftPanel.setBackground(Color.GRAY);

        // CANVAS WRAPPER
        JPanel canvasWrapper = getJPanel(ctx); // absolute layout
        canvasWrapper.setBackground(Color.DARK_GRAY);

        // MAIN PANEL
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(canvasWrapper, BorderLayout.CENTER);

        add(mainPanel);

        setVisible(true);
    }

    private static JPanel getJPanel(EditorContext ctx) {
            JPanel canvasWrapper = new JPanel(new BorderLayout());
            canvasWrapper.setBackground(Color.DARK_GRAY);

            ScreenPanel screenPanel = new ScreenPanel(ctx);
            canvasWrapper.add(screenPanel, BorderLayout.CENTER);

            return canvasWrapper;


        /*JPanel canvasWrapper = new JPanel(null); // absolute layout
        canvasWrapper.setBackground(Color.DARK_GRAY);

        ScreenPanel screenPanel = new ScreenPanel(ctx);
        int canvasW = (int) (ctx.ctxManager.canvasCtx.MAXWIDTH);
        int canvasH = (int) (ctx.ctxManager.canvasCtx.MAXHEIGHT);

        screenPanel.setBounds(0, 0, canvasW, canvasH);
        canvasWrapper.add(screenPanel);

        canvasWrapper.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int x = (canvasWrapper.getWidth() - canvasW) / 2;
                int y = (canvasWrapper.getHeight() - canvasH) / 2;
                screenPanel.setBounds(x, y, canvasW, canvasH);
            }
        });
        return canvasWrapper;
         */
    }
}

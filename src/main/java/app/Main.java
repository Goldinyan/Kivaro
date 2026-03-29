package main.java.app;

import main.java.window.EditorWindow;

import javax.swing.*;
import java.awt.*;


public class Main {
    public static void main(String[] args) {
        new EditorWindow();
    }
}


/*
public class Main {

    private static ManualButton manualButton;
    private static UserManual userManual;
    private static JFrame frame;

    private static int XOffset = 0;
    public static boolean showManual = false;

    private static boolean between(int x0, int x1, int v){
        return v >= x0 && v <= x1;
    }


    public static float animationProgress = 0f;

    public static Timer toggleManualAnimation = new Timer(8, e -> {
        if (showManual && animationProgress < 1f) {

            animationProgress += 0.005f; // geschwindigkeit
            if (animationProgress > 1f) animationProgress = 1f;

            float t = animationProgress;

            //  3t*2 – 2t*3 -> f'(x) = 6t - 6t*2
            // f'(0) -> 0; f'(1) -> 0;
            float smooth = t * t * (3 - 2 * t);

            XOffset = (int)(smooth * 500);

            userManual.setVisible(true);


        } else if (!showManual && animationProgress > 0f) {

            animationProgress -= 0.005f;
            if (animationProgress < 0f) animationProgress = 0f;

            float t = animationProgress;
            float smooth = t * t * (3 - 2 * t);

            XOffset = (int)(smooth * 500);

            userManual.setVisible(true);
        } else {
            if (!showManual) {
                userManual.setVisible(false); // erst jetzt verstecken
            }
            ((Timer)e.getSource()).stop();
        }

        updateManualButtonPosition(manualButton, userManual, frame);
    });




    private static void updateManualButtonPosition(ManualButton manualButton, UserManual userManual, JFrame frame) {
        JLayeredPane lp = frame.getLayeredPane();
        int x0 = (manualButton.getWidth()) / 2 - 10 + XOffset;
        int y0 = (manualButton.getHeight()) / 2 - 10;
        manualButton.setLocation(x0, y0);

        int x = (userManual.getWidth()) / 2 - 735 + XOffset;
        int y = 10;
        userManual.setLocation(x, y);
    }


    public static void main() {
        Sprite sprite = new Sprite();


        int FULLHEIGHT = 900;
        int MAXWIDTH = 1400;

        Screen screen = new Screen(MAXWIDTH, FULLHEIGHT, sprite);
        main.java.ui.ScreenPanel screenPanel = new main.java.ui.ScreenPanel(screen, sprite, MAXWIDTH - 150, FULLHEIGHT);
        ColorPickerPanel colorPickerPanel = new ColorPickerPanel(screenPanel);
        userManual = new UserManual(screenPanel);
        manualButton = new ManualButton(() -> showManual = !showManual, () -> toggleManualAnimation.start());

        frame = new JFrame("Pixel Editor - SAS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLayeredPane layeredPane = frame.getLayeredPane();

        // Borderlayout macht bessere alignment
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(screenPanel, BorderLayout.CENTER);
        mainPanel.add(colorPickerPanel, BorderLayout.EAST);

        frame.add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        // oberste ebene
        layeredPane.add(userManual, JLayeredPane.POPUP_LAYER);
        layeredPane.add(manualButton, JLayeredPane.POPUP_LAYER);
        userManual.setVisible(false);


        manualButton.setSize(50, 36);
        manualButton.setOpaque(true);
        manualButton.setBackground(new Color(0, 0, 0, 200));
        SwingUtilities.invokeLater(() -> {
            int x = (manualButton.getWidth()) / 2 - 10;
            int y = (manualButton.getHeight()) / 2 - 10;
            manualButton.setLocation(x, y);
        });


        userManual.setSize(490, 880);
        userManual.setOpaque(true);

        frame.setVisible(true);

        new Timer(8, e -> {
            frame.repaint();
        }).start();

    }


}
*/


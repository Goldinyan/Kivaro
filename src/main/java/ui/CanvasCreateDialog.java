package main.java.ui;

import javax.swing.*;
import java.awt.*;
import main.java.context.EditorContext;
import main.java.ui.Panels.TopBarPanel;
import main.java.ui.Panels.TopLeftButtonsPanel;

public class CanvasCreateDialog extends JFrame {

    private final EditorContext ctx;
    private final Runnable onFinish;

    public CanvasCreateDialog(EditorContext ctx, Runnable func) {
        this.ctx = ctx;
        this.onFinish = func;

        setLayout(new BorderLayout());
    }

    public JPanel build() {
        JPanel wrapper = new JPanel(new GridBagLayout()); // zentriert
        wrapper.setOpaque(false); // Overlay-Hintergrund bleibt sichtbar

        TopBarPanel topBarPanel = new TopBarPanel(getWidth(), 50, ctx);
        add(topBarPanel, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(400, 250));
        panel.setBackground(ctx.ctxManager.colorCtx.getTheme().background2);
        panel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField widthField = new JTextField("800");
        JTextField heightField = new JTextField("600");
        JButton createBtn = new JButton("Create");

        gbc.gridy = 0; panel.add(new JLabel("Canvas Width:"), gbc);
        gbc.gridy = 1; panel.add(widthField, gbc);
        gbc.gridy = 2; panel.add(new JLabel("Canvas Height:"), gbc);
        gbc.gridy = 3; panel.add(heightField, gbc);
        gbc.gridy = 4; panel.add(createBtn, gbc);

        wrapper.add(panel); // zentriert, ohne zu stretchen


        createBtn.addActionListener(e -> {
            try {
                int w = Integer.parseInt(widthField.getText());
                int h = Integer.parseInt(heightField.getText());

                ctx.ctxManager.canvasCtx.WIDTH = w;
                ctx.ctxManager.canvasCtx.HEIGHT = h;

                onFinish.run();


            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel,
                        "Bitte gültige Zahlen eingeben!",
                        "Fehler",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        return wrapper;
    }
}

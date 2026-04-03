package main.java.ui;

import javax.swing.*;
import java.awt.*;
import main.java.context.EditorContext;

public class CanvasCreateDialog {

    private final EditorContext ctx;
    private final UIManager uiManager;

    public CanvasCreateDialog(EditorContext ctx, UIManager uiManager) {
        this.ctx = ctx;
        this.uiManager = uiManager;
    }

    public JPanel build() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(400, 250));
        panel.setBackground(new Color(240, 240, 240));
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

        // === ACTION ===
        createBtn.addActionListener(e -> {
            try {
                int w = Integer.parseInt(widthField.getText());
                int h = Integer.parseInt(heightField.getText());

                ctx.ctxManager.canvasCtx.WIDTH = w;
                ctx.ctxManager.canvasCtx.HEIGHT = h;

                uiManager.hideOverlay();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel,
                        "Bitte gültige Zahlen eingeben!",
                        "Fehler",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }
}

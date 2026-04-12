package main.java.window;

import java.awt.*;
import java.util.Objects;
import javax.swing.*;
import main.java.context.EditorContext;
import main.java.ui.CanvasCreateDialog;

public class StartWindow extends JFrame {
  public StartWindow() {
    super("Kivaro");

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(new Dimension(1200, 800));
    setResizable(true);
    setLocationRelativeTo(null);
    setUndecorated(true);

    setMinimumSize(new Dimension(400, 250)); // min 800px breite, 500px höhe

    // System.out.println("RESOURCE = " + getClass().getResource("/assets/KivaroIcon.png"));

    setIconImage(
        new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/KivaroIcon.png")))
            .getImage());
    Taskbar.getTaskbar()
        .setIconImage(
            new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/KivaroIcon.png")))
                .getImage());

    EditorContext ctx = Helper.initContext(this);
    add(new CanvasCreateDialog(ctx, () -> switchToEditor(ctx)).build());
    pack();

    setLocationRelativeTo(null);

    setVisible(true);
  }

  private void switchToEditor(EditorContext ctx) {
      dispose();
      new EditorWindow(ctx);
  }
}

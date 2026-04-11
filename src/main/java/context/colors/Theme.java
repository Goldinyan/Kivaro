package main.java.context.colors;

import java.awt.*;

public class Theme {
    public Color background1;
    public Color background2;
    public Color text;
    public Color text2;
    public Color accent;
    public Color border;
    public Color grid1;
    public Color grid2;

    public Theme(Color bg1, Color bg2, Color text, Color text2, Color accent, Color border, Color grid1, Color grid2) {
        this.background1 = bg1;
        this.background2 = bg2;
        this.text = text;
        this.text2 = text2;
        this.accent = accent;
        this.border = border;
        this.grid1 = grid1;
        this.grid2 = grid2;
    }
}


package main.java.context;


import java.awt.*;

public class CanvasContext {
    public int MAXWIDTH;
    public int MAXHEIGHT;
    public int WIDTH;
    public int HEIGHT;
    public int GRID_CELL_SIZE;

    public float zoom;
    public int worldX;
    public int worldY;

    private boolean darkMode;
    private boolean showPreview;
    private Color backgroundColor = Color.DARK_GRAY;
    private int clickOffset = 8;

}

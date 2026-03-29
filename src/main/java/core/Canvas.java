package main.java.core;

import main.java.context.EditorContext;
import main.java.context.colors.Theme;
import main.java.graphics.Layer;

import java.awt.*;

public class Canvas {


    EditorContext ctx;


    public Canvas(EditorContext ctx){
        this.ctx = ctx;
    }

    public void render(Graphics2D g){

        draw_grid(g);
        g.setBackground(ctx.ctxManager.colorCtx.getTheme().background1);
        // 1. Hintergrund
        // 2. Alle Layer
        // 3. Tool-Preview
        // 4. Auswahl
        // 5. Grid

        for (Layer layer : ctx.layers.getAll()) {
            if (layer.isVisible()) {
                g.drawImage(layer.getImage(), 0, 0, null);
            }
        }
    }


    public void draw_grid(Graphics g) {
        int WIDTH = ctx.layers.getActive().getImage().getWidth();
        int HEIGHT = ctx.layers.getActive().getImage().getHeight();
        int GRID_CELL_SIZE = ctx.ctxManager.canvasCtx.GRID_CELL_SIZE;

        float zoom = ctx.ctxManager.canvasCtx.zoom;
        int worldX = ctx.ctxManager.canvasCtx.worldX;
        int worldY = ctx.ctxManager.canvasCtx.worldY;


        Theme theme = ctx.ctxManager.colorCtx.getTheme();

        int i = 0;

        for(int x = worldX; x < WIDTH + worldX; x += GRID_CELL_SIZE){
            i = (x / GRID_CELL_SIZE) % 2;

            for(int y = worldY; y < HEIGHT + worldY; y += GRID_CELL_SIZE){
                g.setColor(i % 2 == 0 ? theme.grid1 : theme.grid2);
                g.fillRect(x, y, GRID_CELL_SIZE, GRID_CELL_SIZE);
                i++;
            }
        }
    }
}

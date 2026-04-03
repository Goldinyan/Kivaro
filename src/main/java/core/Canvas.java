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
        int cell = ctx.ctxManager.canvasCtx.GRID_CELL_SIZE;
        float zoom = ctx.ctxManager.canvasCtx.zoom;

        int worldX = ctx.ctxManager.canvasCtx.worldX;
        int worldY = ctx.ctxManager.canvasCtx.worldY;

        int width = ctx.layers.getActive().getImage().getWidth();
        int height = ctx.layers.getActive().getImage().getHeight();

        if(width == 0 || height == 0){
            return;
        }

        Theme theme = ctx.ctxManager.colorCtx.getTheme();

        int screenCell = Math.max(1, (int)(cell * zoom));

        int startCellX = Math.floorDiv(worldX, cell);
        int startCellY = Math.floorDiv(worldY, cell);

        // System.out.println(startCellX + " " + startCellY);

        for (int cx = startCellX; cx * cell < width; cx++) {
            for (int cy = startCellY; cy * cell < height; cy++) {

                boolean even = ((cx + cy) & 1) == 0;
                g.setColor(even ? theme.grid1 : theme.grid2);

                int screenX = (int)((cx * cell - worldX) * zoom);
                int screenY = (int)((cy * cell - worldY) * zoom);

                g.fillRect(screenX, screenY, screenCell, screenCell);
            }
        }
    }

}

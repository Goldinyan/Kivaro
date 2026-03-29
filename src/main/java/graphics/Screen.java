package main.java.graphics;

import java.awt.*;

import main.java.graphics.Cell;

public class Screen {
    private Sprite sprite;
    public int WIDTH;
    public int PIXEL_SIZE;
    public int HEIGHT;


    private Color grid1 = Color.GRAY;
    private Color grid2 = Color.LIGHT_GRAY;

    public int GRID_CELL_SIZE = 64;

    public Screen(int width, int height, Sprite sprite) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.sprite = sprite;
        this.PIXEL_SIZE = 8;
    }

    public void draw_sprite(Graphics g) {
        if (sprite == null) return;

        Cell[] cells = sprite.get_all_cells();
        
        if (cells == null) return;

        for (Cell cell : cells) {
            if(cell.x == -1) continue;
            g.setColor(cell.color);
            g.fillRect(cell.x, cell.y, PIXEL_SIZE, PIXEL_SIZE);
        }
    }



    public void draw_preview(Graphics g, int mouse_x, int mouse_y, int brush_size, Color curr_color) {
        Graphics2D g2d = (Graphics2D) g;
        
        int w = brush_size * 2 - 1;
        
        // dash border
        Stroke dashed = new BasicStroke(
                1, // stärke des striches
                BasicStroke.CAP_BUTT, // flacge enden
                BasicStroke.JOIN_BEVEL, // abgeschärgte ecken
                10,
                new float[]{5}, // Länge der Striche
                0);

        g2d.setStroke(dashed); // für alle weiteren linien gilt das
        
        // Transparent preview color
        Color previewColor = new Color(
            curr_color.getRed(), 
            curr_color.getGreen(), 
            curr_color.getBlue(), 
            80  // A for transparent
        );
        g2d.setColor(previewColor);
        g2d.fillRect(mouse_x, mouse_y, w, w);
        
        g2d.setColor(Color.WHITE);
        g2d.drawRect(mouse_x, mouse_y, w, w);
    }
}

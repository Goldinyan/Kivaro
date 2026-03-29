package main.java.ui;

import main.java.context.EditorContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ScreenPanel extends JPanel {

    private final EditorContext ctx;

    public ScreenPanel(EditorContext ctx) {
        this.ctx = ctx;

        setFocusable(true);
        // setPreferredSize(new Dimension(ctx.ctxManager.canvasCtx.MAXWIDTH, ctx.ctxManager.canvasCtx.FULLHEIGHT));
        requestFocusInWindow();




        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                ctx.states.mouseDown(e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                ctx.states.mouseUp(e.getX(), e.getY());
            }
        };
        addMouseListener(mouseListener);


        MouseMotionListener mouseMotionListener = new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                ctx.states.mouseDrag(e.getX(), e.getY());
            }
        };
        addMouseMotionListener(mouseMotionListener);

        // Main Loop
        new Timer(16, e -> repaint()).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        // setBackground(ctx.ctxManager.colorCtx.getTheme().background1);
        super.paintComponent(g);
        ctx.states.render((Graphics2D) g);
    }
}


/*
public class ScreenPanel extends JPanel implements KeyListener, MouseListener, MouseMotionListener {

    private static class PaintTool {
        public static Color current_color;
        private int brush_size;
    }

    private static class Map {
        private static int MAXWIDTH;
        private static int FULLHEIGHT;

        private static int FIELD_WIDTH = 256;
        private static int FIELD_HEIGHT = 256;

        private static Sprite sprite;

        private static Screen screen;
    }

    private static class UI {
        private static boolean dark_mode;
        private static boolean show_preview;
        private static Color BG_COLOR = Color.DARK_GRAY;
        private static int CLICK_OFFSET = 8;
    }

    private static class InputState {
        boolean shift;
        boolean control;

        private static Vector_t mouse;
    }



    private static class Alignment {
        boolean x_Alignment;
        int x_value = -1;
        boolean y_Alignment;
        int y_value = -1;
    }


    private final PaintTool paint_tool = new PaintTool();
    private final Map Map = new Map();
    private final UI UI = new UI();
    private final InputState InputState = new InputState();
    private final Alignment Alignment = new Alignment();


    public ScreenPanel(Screen screen, Sprite sprite, int WIDTH, int height) {
        Map.screen = screen;
        Map.MAXWIDTH = WIDTH;
        Map.FULLHEIGHT = height;
        Map.sprite = sprite;

        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        setPreferredSize(new Dimension(MAXWIDTH, FULLHEIGHT));
    }

    @Override
    protected void paintComponent(Graphics g) {
        setBackground(UI.dark_mode ? UI.BG_COLOR : Color.WHITE);
        super.paintComponent(g);
        Map.screen.draw_grid(g);
        Map.screen.draw_sprite(g);

        InputState.mouse.x = Alignment.x_Alignment ? Alignment.x_value : InputState.mouse.x;
        InputState.mouse.y = Alignment.y_Alignment ? Alignment.y_value : InputState.mouse.y;

        // preview
        if (UI.show_preview) {
            Map.screen.draw_preview(g, InputState.mouse.x, InputState.mouse.y, paint_tool.brush_size, paint_tool.paint_tool.current_color);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        InputState.mouse.x = Alignment.x_Alignment ? Alignment.x_value : e.getX() - UI.CLICK_OFFSET;
        InputState.mouse.y =  Alignment.y_Alignment ? Alignment.y_value : e.getY() - UI.CLICK_OFFSET;



        if(e.getButton() == MouseEvent.BUTTON1){
            Map.sprite.add_cells(paint_tool.current_color, mouse_x, mouse_y, brush_size);
        } else if(e.getButton() == MouseEvent.BUTTON3){
            Map.sprite.remove_cell(mouse_x, mouse_y);
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e){
        int code = e.getKeyCode();
        switch (code) {

            case KeyEvent.VK_ENTER:
                Map.sprite.get_sas_code();
                break;
            
            case KeyEvent.VK_Y:
                Alignment.y_Alignment = !Alignment.y_Alignment;
                Alignment.x_Alignment = false;
                Alignment.y_value = mouse_y;
                break;
            
            case KeyEvent.VK_X:
                Alignment.x_Alignment = !Alignment.x_Alignment;
                Alignment.y_Alignment = false;
                Alignment.x_value = mouse_x;
                break;
                
            case KeyEvent.VK_2:
                if (InputState.shift) {
                    paint_tool.current_color = Color.BLACK;
                    System.out.println("Farbe auf Schwarz gestellt!");
                } else if (this.brush_size < 15) {
                    this.brush_size++;
                    System.out.println("Brushsize: " + this.brush_size);
                }
                break;

            case KeyEvent.VK_1:
                if (InputState.shift) {
                    paint_tool.current_color = Color.WHITE;
                    System.out.println("Farbe auf Weiß gestellt!");
                } else if (this.brush_size > 3) {
                    this.brush_size--;
                    System.out.println("Brushsize: " + this.brush_size);
                }
                break;

            case KeyEvent.VK_0:
                UI.dark_mode = !UI.dark_mode;
                break;
            case KeyEvent.VK_R:
                Map.sprite.clear();
                break;
            case KeyEvent.VK_SHIFT:
                InputState.shift = true;
                break;

            case KeyEvent.VK_CONTROL:
                InputState.control = true;
                break;


        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_SHIFT) InputState.shift = false;
        if(code == KeyEvent.VK_CONTROL) InputState.control = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        UI.show_preview = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        UI.show_preview = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouse_x = e.getX() - CLICK_OFFSET;
        mouse_y = e.getY() - CLICK_OFFSET;

        if((e.getModifiersEx() & InputStateEvent.BUTTON1_DOWN_MASK) == InputStateEvent.BUTTON1_DOWN_MASK && InputState.control){
            Map.sprite.add_cells(paint_tool.current_color, mouse_x, mouse_y, brush_size);
        } else if((e.getModifiersEx() & InputStateEvent.BUTTON3_DOWN_MASK) == InputStateEvent.BUTTON3_DOWN_MASK){
            Map.sprite.remove_cell(mouse_x, mouse_y);
        }

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouse_x = e.getX() - CLICK_OFFSET;
        mouse_y = e.getY() - CLICK_OFFSET;
        repaint();
    }


    public void setCurrColor(Color color) {
        this.paint_tool.current_color = color;
    }

    public Color getCurrColor() {
        return this.paint_tool.current_color;
    }

    public int getBrushSize() {
        return this.brush_size;
    }

    public boolean isDarkMode() {
        return UI.dark_mode;
    }
}
*/
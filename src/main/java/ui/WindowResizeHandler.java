package main.java.ui;

// MADE BY KI - Die Idee zumindest


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WindowResizeHandler {

    private final JFrame window;
    private static final int BORDER = 6;

    private Point dragStartScreen;
    private Rectangle startBounds;
    private int resizeDir = 0;

    private static final int NORTH = 1;
    private static final int SOUTH = 2;
    private static final int WEST  = 4;
    private static final int EAST  = 8;

    public WindowResizeHandler(JFrame window, Component glassPane) {
        this.window = window;


        // Globaler AWTEventListener bekommt ALLE Events egal ob we wer sie konsumiert
        AWTEventListener listener = event -> {
            if (!(event instanceof MouseEvent)) return;
            MouseEvent e = (MouseEvent) event;
            
            if (e.getID() == MouseEvent.MOUSE_MOVED) {
                handleMouseMoved(e);
            } else if (e.getID() == MouseEvent.MOUSE_PRESSED) {
                handleMousePressed(e);
            } else if (e.getID() == MouseEvent.MOUSE_DRAGGED) {
                handleMouseDragged(e);
            } else if (e.getID() == MouseEvent.MOUSE_RELEASED) {
                handleMouseReleased(e);
            }
        };
        
        Toolkit.getDefaultToolkit().addAWTEventListener(listener, AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);
    }

    private int getResizeDirection(MouseEvent e) {
        Point screenPoint = e.getLocationOnScreen();
        Point frameLocation = window.getLocationOnScreen();
        
        int x = screenPoint.x - frameLocation.x;
        int y = screenPoint.y - frameLocation.y;
        int w = window.getWidth();
        int h = window.getHeight();

        int dir = 0;

        if (y < BORDER) dir |= NORTH;
        else if (y > h - BORDER) dir |= SOUTH;

        if (x < BORDER) dir |= WEST;
        else if (x > w - BORDER) dir |= EAST;

        return dir;
    }

    private Cursor getCursorForDir(int dir) {
        return switch (dir) {
            case NORTH -> Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR);
            case SOUTH -> Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR);
            case WEST  -> Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR);
            case EAST  -> Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);
            case NORTH | WEST -> Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR);
            case NORTH | EAST -> Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR);
            case SOUTH | WEST -> Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR);
            case SOUTH | EAST -> Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR);
            default -> Cursor.getDefaultCursor();
        };
    }

    private void handleMouseMoved(MouseEvent e) {
        int dir = getResizeDirection(e);
        window.setCursor(getCursorForDir(dir));
    }

    private void handleMousePressed(MouseEvent e) {
        resizeDir = getResizeDirection(e);
        dragStartScreen = e.getLocationOnScreen();
        startBounds = window.getBounds();
    }

    private void handleMouseDragged(MouseEvent e) {
        if (resizeDir == 0) return;

        Point drag = e.getLocationOnScreen();
        int dx = drag.x - dragStartScreen.x;
        int dy = drag.y - dragStartScreen.y;

        Rectangle newBounds = new Rectangle(startBounds);
        Dimension minSize = window.getMinimumSize();

        if ((resizeDir & EAST) != 0) newBounds.width += dx;
        if ((resizeDir & SOUTH) != 0) newBounds.height += dy;
        if ((resizeDir & WEST) != 0) {
            newBounds.x += dx;
            newBounds.width -= dx;
        }
        if ((resizeDir & NORTH) != 0) {
            newBounds.y += dy;
            newBounds.height -= dy;
        }

        // min size bleibt
        if (newBounds.width < minSize.width) {
            if ((resizeDir & WEST) != 0) {
                newBounds.x -= (minSize.width - newBounds.width);
            }
            newBounds.width = minSize.width;
        }

        if (newBounds.height < minSize.height) {
            if ((resizeDir & NORTH) != 0) {
                newBounds.y -= (minSize.height - newBounds.height);
            }
            newBounds.height = minSize.height;
        }

        window.setBounds(newBounds);
    }

    private void handleMouseReleased(MouseEvent e) {
        resizeDir = 0;
    }
}

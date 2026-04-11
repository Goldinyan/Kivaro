# WindowResizeHandler Documentation

## Overview

The `WindowResizeHandler` enables custom window resizing for undecorated frames. It intercepts mouse events globally and allows resizing from any window border (top, bottom, left, right, and corners).

## Why AWTEventListener?

Standard approach of registering listeners on individual components fails because:
1. Inner components (TopBarPanel, CanvasWrapper, etc.) have their own MouseListeners that consume events
2. Once a component consumes an event, it doesn't propagate to parent listeners
3. This prevents the resize handler from receiving drag events

The solution is to use `AWTEventListener`, which operates at the toolkit level and receives ALL mouse events before any component-level listeners, bypassing the event consumption hierarchy.

## Core Concepts

### Bitwise Operations (| and |=)

The handler uses bitwise OR to combine resize directions:

```java
int dir = 0;
dir |= NORTH;  // dir = 1
dir |= EAST;   // dir = 1 | 8 = 9 (binary: 0001 | 1000 = 1001)
```

This allows checking multiple directions simultaneously:
```java
if ((dir & NORTH) != 0)  // Check if NORTH bit is set
if ((dir & EAST) != 0)   // Check if EAST bit is set
```

Constants:
- `NORTH = 1` (binary: 0001)
- `SOUTH = 2` (binary: 0010)
- `WEST = 4`  (binary: 0100)
- `EAST = 8`  (binary: 1000)

### Coordinate System

Mouse events have coordinates relative to their source component. The handler converts these to window-relative coordinates:

```java
Point screenPoint = e.getLocationOnScreen();      // Global screen coords
Point frameLocation = window.getLocationOnScreen(); // Window position on screen

int x = screenPoint.x - frameLocation.x;  // Convert to window-relative
int y = screenPoint.y - frameLocation.y;
```

The `BORDER = 6` pixel margin defines the resize area around window edges.

## Event Flow

1. **MOUSE_MOVED**: Calculate resize direction at current position, update cursor
2. **MOUSE_PRESSED**: Store starting position and window bounds, determine initial resize direction
3. **MOUSE_DRAGGED**: Calculate delta from start position, apply transformations based on resize direction
4. **MOUSE_RELEASED**: Reset resize direction

## Resize Calculation

When dragging, the handler:
1. Calculates pixel movement: `dx = currentX - startX`, `dy = currentY - startY`
2. Applies transformations based on direction bits:
   - EAST: Increase width
   - WEST: Move left and decrease width
   - SOUTH: Increase height
   - NORTH: Move up and decrease height

Example (resizing from bottom-right):
```java
newBounds.width += dx;   // Drag right = wider
newBounds.height += dy;  // Drag down = taller
```

Example (resizing from top-left):
```java
newBounds.x += dx;       // Drag left = shift window left
newBounds.width -= dx;   // Same drag = narrower
newBounds.y += dy;       // Drag up = shift window up
newBounds.height -= dy;  // Same drag = shorter
```

## Integration Notes

- Initialized in `EditorWindow` constructor after UI setup
- Requires `setUndecorated(true)` on the JFrame
- GlassPane visibility state doesn't affect functionality
- TopBarPanel must check `e.getY() >= 6` to avoid conflicts with NORTH resize area

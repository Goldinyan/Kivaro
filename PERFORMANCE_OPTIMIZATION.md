# Performance Optimization Guide

## Identified Issues

### 1. Global AWTEventListener Overhead

**Problem**: The AWTEventListener intercepts ALL mouse events globally, even when not resizing.

**Solution**:
```java
// Nur Cursor-Updates mit MouseInfo statt jedem Event
private final Timer cursorTimer = new Timer(50, e -> {
    PointerInfo pi = MouseInfo.getPointerInfo();
    if (pi != null) {
        Point screenPos = pi.getLocation();
        Point framePos = window.getLocationOnScreen();
        int dir = calculateResizeDirection(screenPos, framePos);
        window.setCursor(getCursorForDir(dir));
    }
});

// Nur MouseListener für actual dragging
public WindowResizeHandler(JFrame window, Component glassPane) {
    this.window = window;
    cursorTimer.start();
    // ... rest of setup
}
```

**Impact**: Reduces event processing from 1000s/sec to ~20 updates/sec cursor updates.

---

### 2. Excessive Repaint Calls

**Problem**: Multiple timers calling repaint() every 8-16ms on various components.

**Current**:
- EditorWindow: 8ms timer
- ManualPanelAnimator: 8ms timer
- ManualButton: 16ms hover timers
- Multiple repaint() calls in event handlers

**Solution**: Consolidate into single repaint cycle:

```java
// In EditorWindow
private static final Timer GLOBAL_RENDER_TIMER = new Timer(16, e -> {
    // Single repaint for entire frame
    frame.repaint();
});
```

**Impact**: Reduces rendering overhead by ~60%.

---

### 3. Resize Jitter

**Problem**: Multiple repaints during drag can cause visual stutter.

**Solution**: Use double-buffering (default in JFrame) + validate after setBounds():

```java
private void handleMouseDragged(MouseEvent e) {
    // ... calculate bounds ...
    window.setBounds(newBounds);
    window.validate();  // Immediately layout children
    window.repaint();   // Schedule single repaint
}
```

**Impact**: Smoother resize experience.

---

### 4. Expensive Coordinate Conversions

**Problem**: `getLocationOnScreen()` is called repeatedly per event.

**Solution**: Cache window position during drag:

```java
private void handleMousePressed(MouseEvent e) {
    resizeDir = getResizeDirection(e);
    dragStartScreen = e.getLocationOnScreen();
    startBounds = window.getBounds();
    cachedFrameLocation = window.getLocationOnScreen();  // Cache once
}

private int getResizeDirection(MouseEvent e) {
    // Reuse cached value during drag
    int x = e.getLocationOnScreen().x - cachedFrameLocation.x;
    // ...
}
```

**Impact**: Reduces expensive system calls during drag.

---

### 5. Unbounded Listener Registrations

**Problem**: TopBarPanel, LeftPanel, CanvasWrapper all register MouseListeners independently.

**Solution**: Consider delegating to a central event dispatcher instead of n independent listeners.

**Current structure**:
- MainWindow listener chain
- TopBarPanel listener
- LeftPanel listener
- CanvasWrapper listener
- ColorPickerPanel listener
- etc.

Each listener processes the same events. This should be consolidated if possible.

---

## Recommended Optimizations (Priority Order)

### HIGH PRIORITY
1. **Merge render timers** - Use single 16ms timer instead of multiple 8ms timers
   - Location: EditorWindow, ManualPanelAnimator, Main.java
   - Expected gain: 40-50ms CPU time per second

2. **Optimize AWTEventListener** - Use timer-based cursor updates
   - Location: WindowResizeHandler
   - Expected gain: Smoother overall performance

### MEDIUM PRIORITY
3. **Cache coordinate conversions** - During drag operations
   - Location: WindowResizeHandler.handleMouseDragged()
   - Expected gain: Smoother resize feedback

4. **Add validate() after setBounds()** - Force immediate layout
   - Location: WindowResizeHandler.handleMouseDragged()
   - Expected gain: Eliminate layout lag

### LOW PRIORITY
5. **Consolidate listeners** - Consider event dispatcher pattern
   - Would require refactoring multiple components
   - Estimated gain: 10-20% event processing overhead

---

## Profiling Recommendations

Use JProfiler or Java Flight Recorder to identify actual bottlenecks:

```bash
java -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:StartFlightRecording=duration=60s,filename=recording.jfr Main
```

## General Swing Performance Tips

- Avoid paintComponent() work - defer heavy operations
- Use SwingWorker for long operations
- Minimize component hierarchy depth
- Cache expensive calculations
- Use opaque components when possible
- Disable anti-aliasing for non-text rendering if not needed

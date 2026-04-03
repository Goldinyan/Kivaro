package main.java.ui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * VOLLSTÄNDIGER UI-Manager mit Layout-Verwaltung!
 * 
 * Der UIManager weiß:
 * ✓ Welche Panels existieren
 * ✓ Wie groß jedes Panel ist
 * ✓ Welches LAYOUT jedes Panel hat (BorderLayout, GridLayout, etc.)
 * ✓ WO jedes Panel positioniert ist (WEST, CENTER, NORTH, etc.)
 * ✓ Welcher Panel gerade angezeigt wird
 * ✓ Und kann SELBST Layouts rendern
 */
public class UIManager {
    
    // ===== CORE =====
    private final JPanel mainContainer;
    private final JPanel overlay;
    
    // ===== PANEL-VERWALTUNG =====
    private final Map<String, JPanel> panels = new HashMap<>();
    private final Map<String, Dimension> panelSizes = new HashMap<>();
    private final Map<String, LayoutManager> panelLayouts = new HashMap<>();
    
    // ===== LAYOUT-CONSTRAINTS (für BorderLayout) =====
    private final Map<String, String> panelConstraints = new HashMap<>();
    
    // ===== PANEL-HIERARCHIE =====
    // Speichert, welche Panels in welchem Panel sind
    private final Map<String, java.util.List<String>> panelHierarchy = new HashMap<>();
    
    private String currentPanel;
    private JPanel currentOverlay;
    
    public UIManager(JPanel mainContainer, JPanel overlay) {
        this.mainContainer = mainContainer;
        this.overlay = overlay;
    }
    
    /**
     * Registriert ein einfaches Panel (für Top-Level oder Overlay-Panels).
     */
    public void registerPanel(String id, JPanel panel, int width, int height) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Panel ID darf nicht null oder leer sein!");
        }
        if (panel == null) {
            throw new IllegalArgumentException("Panel darf nicht null sein!");
        }
        
        panels.put(id, panel);
        panelSizes.put(id, new Dimension(width, height));
        panelLayouts.put(id, panel.getLayout());  // Layout speichern
        
        if (width > 0 && height > 0) {
            panel.setPreferredSize(new Dimension(width, height));
        }
        
        System.out.println("Panel registriert: " + id + " (" + width + "x" + height + ")");
    }
    
    /**
     * Registriert ein flexibles Panel.
     */
    public void registerPanel(String id, JPanel panel) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Panel ID darf nicht null oder leer sein!");
        }
        if (panel == null) {
            throw new IllegalArgumentException("Panel darf nicht null sein!");
        }
        
        panels.put(id, panel);
        panelSizes.put(id, new Dimension(0, 0));
        panelLayouts.put(id, panel.getLayout());
        
        System.out.println("Panel registriert: " + id + " (flexibel)");
    }
    
    /**
     * Registriert ein komplettes Layout mit mehreren Panels!
     * 
     * Beispiel:
     * uiManager.registerLayout("editor", editorLayout, new BorderLayout());
     * uiManager.addToLayout("editor", "colorpicker", colorPickerPanel, 200, 600, BorderLayout.WEST);
     * uiManager.addToLayout("editor", "canvas", canvasPanel, 0, 0, BorderLayout.CENTER);
     */
    public void registerLayout(String id, JPanel container, LayoutManager layout) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Layout ID darf nicht null oder leer sein!");
        }
        if (container == null) {
            throw new IllegalArgumentException("Container darf nicht null sein!");
        }
        
        container.setLayout(layout);
        panels.put(id, container);
        panelLayouts.put(id, layout);
        panelSizes.put(id, new Dimension(0, 0));  // Layouts sind flexibel
        panelHierarchy.put(id, new java.util.ArrayList<>());
        
        System.out.println("Layout registriert: " + id + " (Layout: " + layout.getClass().getSimpleName() + ")");
    }
    
    /**
     * Fügt ein Panel zu einem Layout hinzu!
     * 
     * Beispiel:
     * uiManager.addToLayout("editor", "colorpicker", colorPickerPanel, 200, 600, BorderLayout.WEST);
     */
    public void addToLayout(String layoutId, String panelId, JPanel panel, 
                           int width, int height, Object constraint) {
        if (!panels.containsKey(layoutId)) {
            System.err.println("Layout nicht gefunden: " + layoutId);
            return;
        }
        
        // Panel speichern
        panels.put(panelId, panel);
        panelSizes.put(panelId, new Dimension(width, height));
        panelConstraints.put(panelId, constraint.toString());  // Constraint speichern (z.B. "WEST", "CENTER")
        
        if (width > 0 && height > 0) {
            panel.setPreferredSize(new Dimension(width, height));
        }
        
        // Zum Layout hinzufügen
        JPanel layoutContainer = panels.get(layoutId);
        layoutContainer.add(panel, constraint);
        
        // Hierarchie speichern
        panelHierarchy.get(layoutId).add(panelId);
        
        System.out.println("Panel hinzugefügt: " + panelId + " -> " + layoutId +
                         " (Constraint: " + constraint + ", Größe: " + width + "x" + height + ")");
    }
    
    /**
     * Zeigt ein Panel/Layout an.
     */
    public void showPanel(String panelId) {
        if (!panels.containsKey(panelId)) {
            System.err.println("Panel nicht gefunden: " + panelId);
            printDebugInfo();
            return;
        }
        
        mainContainer.removeAll();
        mainContainer.add(panels.get(panelId), BorderLayout.CENTER);
        currentPanel = panelId;
        updateLayout();
        
        System.out.println("Panel angezeigt: " + panelId);
    }

    /**
     * Zeigt einen Dialog/Overlay an.
     */
    public void showOverlay(JPanel dialog) {
        if (dialog == null) {
            System.err.println("Dialog darf nicht null sein!");
            return;
        }
        
        overlay.removeAll();
        overlay.add(dialog, BorderLayout.CENTER);
        currentOverlay = dialog;
        overlay.setVisible(true);
        updateLayout();
        
        System.out.println("Overlay angezeigt");
    }
    
    /**
     * Versteckt den aktuellen Overlay.
     */
    public void hideOverlay() {
        overlay.setVisible(false);
        overlay.removeAll();
        currentOverlay = null;
        updateLayout();
        
        System.out.println("Overlay versteckt");
    }
    
    /**
     * Gibt die Größe eines Panels zurück.
     */
    public Dimension getPanelSize(String panelId) {
        if (!panelSizes.containsKey(panelId)) {
            System.err.println("Panel nicht gefunden: " + panelId);
            return null;
        }
        return panelSizes.get(panelId);
    }
    
    /**
     * Gibt die Breite eines Panels zurück.
     */
    public int getPanelWidth(String panelId) {
        Dimension size = getPanelSize(panelId);
        return size != null ? size.width : -1;
    }
    
    /**
     * Gibt die Höhe eines Panels zurück.
     */
    public int getPanelHeight(String panelId) {
        Dimension size = getPanelSize(panelId);
        return size != null ? size.height : -1;
    }
    
    /**
     * Gibt das Layout eines Panels zurück.
     */
    public LayoutManager getPanelLayout(String panelId) {
        if (!panelLayouts.containsKey(panelId)) {
            System.err.println("Panel nicht gefunden: " + panelId);
            return null;
        }
        return panelLayouts.get(panelId);
    }
    
    /**
     * Gibt das Constraint/die Position eines Panels zurück (z.B. "WEST", "CENTER").
     */
    public String getPanelConstraint(String panelId) {
        return panelConstraints.getOrDefault(panelId, "?");
    }
    
    /**
     * Gibt alle Panels in einem Layout zurück.
     */
    public java.util.List<String> getPanelsInLayout(String layoutId) {
        return panelHierarchy.getOrDefault(layoutId, new java.util.ArrayList<>());
    }
    
    /**
     * Prüft, ob ein Panel existiert.
     */
    public boolean panelExists(String panelId) {
        return panels.containsKey(panelId);
    }
    
    /**
     * Gibt alle Panel-IDs zurück.
     */
    public java.util.Set<String> getRegisteredPanels() {
        return panels.keySet();
    }
    
    /**
     * Prüft ob ein Overlay sichtbar ist.
     */
    public boolean isOverlayVisible() {
        return overlay.isVisible();
    }
    
    /**
     * Gibt das aktuell angezeigte Panel zurück.
     */
    public String getCurrentPanel() {
        return currentPanel;
    }
    
    /**
     * Löscht ein Panel.
     */
    public void unregisterPanel(String panelId) {
        if (panels.remove(panelId) != null) {
            panelSizes.remove(panelId);
            panelLayouts.remove(panelId);
            panelConstraints.remove(panelId);
            System.out.println("Panel deregistriert: " + panelId);
        }
    }
    
    /**
     * Aktualisiert Layout und zeichnet neu.
     */
    private void updateLayout() {
        mainContainer.revalidate();
        mainContainer.repaint();
        overlay.revalidate();
        overlay.repaint();
    }
    
    /**
     * VOLLSTÄNDIGE Debug-Information mit ALLEM!
     */
    public void printDebugInfo() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║         UIManager - VOLLSTÄNDIGE DEBUG INFO                 ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        
        // Aktuelle Panel
        System.out.println("║ Aktuelles Panel: " + 
            String.format("%-40s", (currentPanel != null ? currentPanel : "Keine")) + "║");
        System.out.println("║ Overlay sichtbar: " + 
            String.format("%-39s", overlay.isVisible() ? "JA" : "NEIN") + "║");
        
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.println("║ ALLE PANELS & LAYOUTS:                                      ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        
        if (panels.isEmpty()) {
            System.out.println("║   (Keine)                                                   ║");
        } else {
            for (String id : panels.keySet()) {
                Dimension size = panelSizes.get(id);
                LayoutManager layout = panelLayouts.get(id);
                String sizeStr = size.width == 0 && size.height == 0 ? "flexibel" : 
                                size.width + "x" + size.height;
                String layoutStr = layout != null ? layout.getClass().getSimpleName() : "?";
                String constraint = panelConstraints.getOrDefault(id, "-");
                
                System.out.println("║ • ID: " + String.format("%-20s", id) + "║");
                System.out.println("║   Größe: " + String.format("%-48s", sizeStr) + "║");
                System.out.println("║   Layout: " + String.format("%-47s", layoutStr) + "║");
                System.out.println("║   Position: " + String.format("%-45s", constraint) + "║");
                
                // Wenn Layout Subpanels hat
                if (panelHierarchy.containsKey(id)) {
                    java.util.List<String> children = panelHierarchy.get(id);
                    if (!children.isEmpty()) {
                        System.out.println("║   Enthält Panels:");
                        for (String child : children) {
                            String childConstraint = panelConstraints.getOrDefault(child, "-");
                            System.out.println("║     - " + String.format("%-20s", child) + 
                                             " @ " + childConstraint + 
                                             String.format("%-24s", "") + "║");
                        }
                    }
                }
                System.out.println("║────────────────────────────────────────────────────────────║");
            }
        }
        
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
    }
    
    /**
     * Gibt Info als String zurück (für externe Nutzung).
     */
    public String getDebugInfoAsString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== UIManager - VOLLSTÄNDIGE INFO ===\n");
        sb.append("Aktuelles Panel: ").append(currentPanel != null ? currentPanel : "Keine").append("\n");
        sb.append("Overlay sichtbar: ").append(overlay.isVisible() ? "JA" : "NEIN").append("\n\n");
        sb.append("ALLE PANELS & LAYOUTS:\n");
        sb.append("─".repeat(50)).append("\n");
        
        for (String id : panels.keySet()) {
            Dimension size = panelSizes.get(id);
            LayoutManager layout = panelLayouts.get(id);
            String sizeStr = size.width == 0 && size.height == 0 ? "flexibel" : 
                            size.width + "x" + size.height;
            String layoutStr = layout != null ? layout.getClass().getSimpleName() : "?";
            String constraint = panelConstraints.getOrDefault(id, "-");
            
            sb.append("ID: ").append(id).append("\n");
            sb.append("  Größe: ").append(sizeStr).append("\n");
            sb.append("  Layout: ").append(layoutStr).append("\n");
            sb.append("  Position: ").append(constraint).append("\n");
            
            if (panelHierarchy.containsKey(id)) {
                java.util.List<String> children = panelHierarchy.get(id);
                if (!children.isEmpty()) {
                    sb.append("  Enthält Panels:\n");
                    for (String child : children) {
                        String childConstraint = panelConstraints.getOrDefault(child, "-");
                        sb.append("    - ").append(child).append(" @ ").append(childConstraint).append("\n");
                    }
                }
            }
            sb.append("─".repeat(50)).append("\n");
        }
        
        return sb.toString();
    }
}

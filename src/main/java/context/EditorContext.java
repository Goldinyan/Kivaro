package main.java.context;


import main.java.core.*;

import javax.swing.*;

public class EditorContext {
    public Canvas canvas;
    public LayerManager layers;
    public ToolManager tools;
    public StateMachine states;
    public PluginManager plugins;
    public ContextManager ctxManager;
    public JFrame window;
}


/*
    Canvas - wo gezeichnet wird // Darstellung und Rendering
    LayerManager - Ebenenverwaltung
    ToolManager - aktives main.java.tools.Tool, main.java.tools.Tool‑Liste
    StateMachine - welcher Editor‑Modus gerade aktiv ist
    PluginManager - Plugins laden, registrieren, ausführen
 */

/*
Input → ScreenPanel
ScreenPanel → StateMachine
StateMachine → aktiver State
State → Tools / Canvas / Layers
Rendering → StateMachine → ScreenPanel
Das ist eine saubere Engine‑Architektur.
 */
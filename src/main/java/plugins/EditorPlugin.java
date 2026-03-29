package main.java.plugins;

import main.java.context.EditorContext;

public interface EditorPlugin {
    String getName();
    void onLoad(EditorContext ctx);
}

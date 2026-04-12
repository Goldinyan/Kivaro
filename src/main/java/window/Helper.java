package main.java.window;

import main.java.context.EditorContext;
import main.java.context.EditorContextInitializer;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class Helper
{

    public static @NotNull EditorContext initContext(JFrame frame)
    {
        EditorContext context = new EditorContext();
        EditorContextInitializer.initScreen(context);
        EditorContextInitializer.initTools(context);
        EditorContextInitializer.initStates(context);
        EditorContextInitializer.initColors(context);
        EditorContextInitializer.initManagers(context);

        context.window = frame; // DAS IST DAS JFRAME ICH HABE GAR KEIN BOCK MEHR
        return context;
    }
}

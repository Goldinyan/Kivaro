package main.java.core;

import main.java.tools.Tool;
import main.java.tools.ToolMachine;


public class ToolManager {

    private ToolMachine toolMachine;


    public ToolMachine getActiveMachine() {
        return toolMachine;
    }

    public void setToolMachine(ToolMachine next){
        toolMachine = next;
    }
}

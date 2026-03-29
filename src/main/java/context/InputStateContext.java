package main.java.context;


import main.java.core.Vector_t;

public class InputStateContext
{
    public boolean shift;
    public boolean control;
    public Vector_t mouse = new Vector_t(0, 0);
}

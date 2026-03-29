package main.java.context.colors;

import java.awt.*;

public class ColorContext {
    private boolean darkMode = true;
    private Theme theme = Themes.DARK;

    public void setDarkMode(boolean dark) {
        this.darkMode = dark;
        this.theme = dark ? Themes.DARK : Themes.LIGHT;
    }

    public Theme getTheme() {
        return theme;
    }
}


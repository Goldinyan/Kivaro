package main.java.context.colors;

import java.awt.*;

public class ColorContext {
    private Theme theme = Themes.DARK;

    public void setDarkMode(boolean dark) {
        this.theme = dark ? Themes.DARK : Themes.LIGHT;
    }

    public Theme getTheme() {
        return theme;
    }
}


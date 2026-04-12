package main.java.context.colors;

import java.awt.*;

public class Themes {

    public static final Theme LIGHT = new Theme(
            new Color(245, 245, 245), // sehr helles Grau (fast weiß) – typischer Hintergrund
            new Color(230, 230, 230), // helles Grau – Panels, Sidebar
            new Color(10, 10 ,10),
            new Color(20, 20, 20),
            new Color(20, 20, 20),
            new Color(40, 40, 40),
            new Color(0, 120, 215),   // Windows-Blue – Akzentfarbe / Auswahl
            new Color(200, 200, 200), // mittleres hellgrau – Border / Linien
            new Color(105, 105, 105), // dunkleres Grau – sekundärer Text / Icons
            new Color(190, 190, 190)  // helles Mittelgrau – Hover / leichte Highlights
    );


    public static final Theme DARK = new Theme(
            new Color(42, 41, 41),    // sehr dunkles Grau – Haupt-Hintergrund
            new Color(37, 39, 39),  // dunkles Grau – Panels, Sidebar
            new Color(30, 30, 30),
            new Color(45, 45, 45),
            new Color(230, 230, 230),   // sehr helles Grau – Text / Icons
            new Color(180, 180, 180),
            new Color(107, 106, 106),
            new Color(66, 67, 67),    // mittleres Dunkelgrau – Border / Linien
            new Color(72, 72, 72), // neutrales Grau – sekundärer Text / Icons 57 57 56
            new Color(255, 255, 255)  // helles Grau – Hover / leichte Highlights
    );

}


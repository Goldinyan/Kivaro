package main.java.graphics;

import java.util.*;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.awt.Color;

public class Sprite
{
    private HashMap<String, Cell> cells;
    private int ERASER_SIZE = 10;

    private String[] setup_code_snippets = {
            "import sas.*;", 
            "import java.util.*;", 
            "import java.awt.Color;",
            "public class Character", 
            "{",
            "private List<Rectangle> rects = new ArrayList<Rectangle>();",
            "private Sprite sprite;",
            "public Character(){",
            "init_rects();",
            "sprite = new Sprite();",
            "for(int i = 0; i < rects.size(); i++){",
            "sprite.add(rects.get(i));",
            "}",
            "}",
            "public Sprite get_sprite(){",
            "return this.sprite;",
            "}",
            "void init_rects(){"
        };

    private List<String> code = new ArrayList<String>();

    public Sprite()
    {
        setup_code();

        this.cells = new HashMap<String, Cell>();
    }

    private void add_cell(Cell c){
        String prefix = c.x + "," + c.y;

        if(!cells.containsKey(prefix)){
            cells.put(prefix, c);
        }
    }

    public void clear(){
        this.cells = new HashMap<String, Cell>();
    }

    public void add_cells(Color color, int mouse_x, int mouse_y, int brush_size){
        int w = brush_size * 2 - 1; // 1 -> 1; 2 -> 3; 3 -> 5; 4 -> 7;
        add_cell(new Cell(color, mouse_x, mouse_y));
    }

    public void remove_cell(int x, int y) {
        for (int dx = -ERASER_SIZE; dx <= ERASER_SIZE; dx++) {
            for (int dy = -ERASER_SIZE; dy <= ERASER_SIZE; dy++) {

                int cx = x + dx;
                int cy = y + dy;

                for (String key : new ArrayList<>(cells.keySet())) {
                    if (key.startsWith(cx + "," + cy + ",")) {
                        cells.remove(key);
                    }
                }
            }
        } 
    }

    private void add_rect_code() {
        for(Cell c: cells.values()){
            String first = "rects.add(new Rectangle";
            String middle_1 = "(" + c.x + ", " + c.y + ", 1, 1, ";
            String middle_2 = "new Color(" + c.color.getRed() + ", " + c.color.getGreen() + ", "+ c.color.getBlue() + "))";
            String end = ");\n";

            String code_line = first + middle_1 + middle_2 + end; 

            this.code.add(code_line);
        }
    }

    public Cell[] get_all_cells(){

        Cell[] cells_array = new Cell[cells.size()];

        int i = 0;

        for(Cell c: cells.values()){
            cells_array[i] = c;
            i++;
        }

        return cells_array;

    }

    public void get_sas_code(){
        add_rect_code();
        finish_code();
        try {

            File file = new File("../Character.java");

            if (file.createNewFile()) {
                System.out.println("Datei <Character.java> wurder erforlgreich erstellt.");
                System.out.println("Datei erstellt: " + file.getName());
            } else {
                System.out.println("Datei existiert bereits und muss verschoben oder gelöscht werden.");
                return;            
            }


            FileWriter writer = new FileWriter("../Character.java");
            for(int i = 0; i < this.code.size(); i++){
                writer.write(code.get(i));
            }

            writer.close();

            System.out.println("Erfolgreich in die Datei geschrieben.");

        } catch (IOException e) {
            System.out.println("Ein Fehler ist aufgetreten.");
            e.printStackTrace();
        }

    }

    private void setup_code(){
        for(int i = 0; i < setup_code_snippets.length; i++){
            code.add(setup_code_snippets[i]);
        }
    }

    public void remove_last_rect(){
        this.code.removeLast();
    }

    private void finish_code(){
        StringBuilder code_line = new StringBuilder();
        code_line.append(System.getProperty("line.separator"));
        code_line.append("}");
        code_line.append(System.getProperty("line.separator"));
        code_line.append("}");

        this.code.add(code_line.toString());
    }
}
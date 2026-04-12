package main.java.core;

import main.java.graphics.Layer;

import java.util.ArrayList;
import java.util.List;

public class LayerManager {
    private final List<Layer> layers = new ArrayList<>();
    private int activeIndex = 0;

    public void addLayer(Layer layer) {
        layers.add(layer);
        System.out.println("layer added");
    }

    public void removeLayerAt(int i){ layers.remove(i); }

    public Layer getActive() {
        if(layers.isEmpty()) return null;
        return layers.get(activeIndex);
    }

    public List<Layer> getAll() {
        return layers;
    }

    public void setActive(int index) {
        activeIndex = index;
    }
}

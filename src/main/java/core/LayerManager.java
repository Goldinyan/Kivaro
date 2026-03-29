package main.java.core;

import main.java.graphics.Layer;

import java.util.ArrayList;
import java.util.List;

public class LayerManager {
    private final List<Layer> layers = new ArrayList<>();
    private int activeIndex = 0;

    public void addLayer(Layer layer) {
        layers.add(layer);
    }

    public void removeLayerAt(int i){ layers.remove(i); }

    public Layer getActive() {
        return layers.get(activeIndex);
    }

    public List<Layer> getAll() {
        return layers;
    }

    public void setActive(int index) {
        activeIndex = index;
    }
}

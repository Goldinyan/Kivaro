package main.java.tools;

import main.java.core.LayerManager;
import main.java.graphics.Layer;

import java.awt.*;

public class PencilTool implements Tool{

    private Color currColor;

    @Override
    public void onMouseDown(MouseEvent mEv) {

    }

    @Override
    public void onMouseDrag(MouseEvent mEv) {
        Layer layer = mEv.lm.getActive();
        layer.getImage().setRGB(mEv.x, mEv.y, currColor.getRGB());
    }


    @Override
    public void onMouseUp(MouseEvent mEv) {

    }

    @Override
    public void renderPreview(Graphics2D g) {

    }


}

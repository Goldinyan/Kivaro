package main.java.tools;

import main.java.core.LayerManager;
import main.java.graphics.Layer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class PencilTool implements Tool
{
    private Color currColor = Color.DARK_GRAY;

    @Override
    public BufferedImage getImage()
    {
        // Mann muss es als BufferedImage mit imageioread sonst casten geht das nicht
        try {
            return ImageIO.read(Objects.requireNonNull(
                    getClass().getResource("/assets/PencilToolIcon.png")
            ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }    }

    @Override
    public void onMouseDown(MouseEvent mEv)
    {

    }

    @Override
    public void onMouseDrag(MouseEvent mEv)
    {
        Layer layer = mEv.lm.getActive();
        layer.getImage().setRGB(mEv.x, mEv.y, currColor.getRGB());
    }


    @Override
    public void onMouseUp(MouseEvent mEv)
    {

    }

    @Override
    public void renderPreview(Graphics2D g)
    {

    }


}

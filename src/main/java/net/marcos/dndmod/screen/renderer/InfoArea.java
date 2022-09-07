package net.marcos.dndmod.screen.renderer;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Rect2i;


//Original by BluSunrize
public abstract class InfoArea extends DrawableHelper {                                                                 //Class to Create the Area that the Info will be retained
    protected final Rect2i area;

    protected InfoArea(Rect2i area){
        this.area = area;
    }
    public abstract void draw(MatrixStack Stack);                                                                       //Draws the Bar at the MatrixStack
}

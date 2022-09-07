package net.marcos.dndmod.screen.renderer;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Rect2i;
import net.minecraft.text.Text;
import team.reborn.energy.api.EnergyStorage;

import java.util.List;

/*
 *  BluSunrize
 *  Copyright (c) 2021
 *
 *  This code is licensed under "Blu's License of Common Sense" (FORGE VERSION)
 *  Details can be found in the license file in the root folder of this project
 */
public class EnergyInfoArea extends InfoArea {                                                                          //Class to draw an Energy Bar
    private final EnergyStorage energy;                                                                                 //Variable energy of type EnergyStorage

    public EnergyInfoArea(int xMin, int yMin)  {                                                                        //Constructor
        this(xMin, yMin, null,8,64);
    }                    //Constructor

    public EnergyInfoArea(int xMin, int yMin, EnergyStorage energy)  {                                                  //Constructor
        this(xMin, yMin, energy,8,64);
    }    //Constructor

    public EnergyInfoArea(int xMin, int yMin, EnergyStorage energy, int width, int height)  {                           //Constructor
        super(new Rect2i(xMin, yMin, width, height));
        this.energy = energy;
    }

    public List<Text> getTooltips() {                                                                                   //Method to write out the Tooltip over the Energy Bar
        return List.of(Text.literal(
                energy.getAmount()+"/"                                                                           //The amount of energy over the capacity of Energy
                +energy.getCapacity()+" E"));
    }

    @Override
    public void draw(MatrixStack transform) {                                                                           //Method to draw the Energy Bar
        final int height = area.getHeight();                                                                            //Sets the height of the Total Energy Bar
        int stored = (int)(height*(energy.getAmount()/(float)energy.getCapacity()));                                    //Sets the Height of the Stored Energy
        fillGradient(                                                                                                   //Draws a Fill Gradient
                transform,                                                                                              //Moves the bar at the transform area
                area.getX(), area.getY()+(height-stored),
                area.getX() + area.getWidth(), area.getY() +area.getHeight(),
                0xffb51500, 0xff600b00
        );
    }
}
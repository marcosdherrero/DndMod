package net.marcos.dndmod.painting;

import net.marcos.dndmod.DnDMod;

import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModPaintings {

    public static final PaintingVariant SUNSET =                                                                        //Creates a new PaintingVariant called Sunset
            registerPaintings("sunset", new PaintingVariant(32,16));                                //registerPaintings(String name, PaintingVariant paintingVariant)
    public static final PaintingVariant PLANT =                                                                         //Creates a new PaintingVariant called Sunset
            registerPaintings("plant", new PaintingVariant(16,16));                                 //registerPaintings(String name, PaintingVariant paintingVariant)
    public static final PaintingVariant WANDERER =                                                                      //Creates a new PaintingVariant called Sunset
            registerPaintings("wanderer", new PaintingVariant(16,32));                              //registerPaintings(String name, PaintingVariant paintingVariant)

    private static PaintingVariant registerPaintings(String name, PaintingVariant paintingVariant){                     //Method to register Paintings parameters(String name, PaintingVariant paintingVariant)
        return Registry.register(                                                                                       //Returns a registry of (Registry<V> registry, Identifier id, T entry)
                Registry.PAINTING_VARIANT,                                                                              //Calls which registry to add the Painting to
                new Identifier(DnDMod.MOD_ID, name),                                                                    //Calls for the Identifier of the Painting
                paintingVariant);                                                                                       //Adds the PaintingVariant
    }

    public static void registerPaintings(){                                                                              //Called in our Mod Initialize Method
        DnDMod.LOGGER.debug("Registering Paintings for " + DnDMod.MOD_ID);
    }
}

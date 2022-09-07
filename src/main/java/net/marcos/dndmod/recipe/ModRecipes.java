package net.marcos.dndmod.recipe;

import net.marcos.dndmod.DnDMod;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModRecipes {
    public static void registerRecipes(){                                                                               //Method to register the Recipe Methods

        Registry.register(Registry.RECIPE_SERIALIZER,                                                                   //Registers the Recipe Serializer
                new Identifier(DnDMod.MOD_ID, CustomTableBlockRecipe.Serializer.ID),
                CustomTableBlockRecipe.Serializer.INSTANCE);

        Registry.register(Registry.RECIPE_TYPE,                                                                         //registers the Recipe Type
                new Identifier(DnDMod.MOD_ID, CustomTableBlockRecipe.Type.ID),
                CustomTableBlockRecipe.Type.INSTANCE);

    }
}

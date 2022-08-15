package net.marcos.dndmod.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;

import net.marcos.dndmod.world.feature.ModPlacedFeatures;

import net.minecraft.world.gen.GenerationStep;

public class ModOreGeneration {                                                                                         //Class to Generate the Ores for the Overworld, Nether, and the End
    public static void generateOres(){                                                                                  //Method to Generate Ores generateOres()

                                                                                                                        //Calls BiomeModifications
        BiomeModifications.addFeature(                                                                                  //addFeature to BiomeModifications
                BiomeSelectors.foundInOverworld(),                                                                      //BiomeSelectors Where the new Biome Feature will go
                GenerationStep.Feature.UNDERGROUND_ORES,                                                                //GenerationStep.Feature Selects which type of feature is altered
                ModPlacedFeatures.CUSTOM_ORE_PLACED.getKey().get());                                                    //Gets the Key for the list of Ores that can be replaced and what they can be replaced by

                                                                                                                        //Calls BiomeModifications
        BiomeModifications.addFeature(                                                                                  //addFeature to BiomeModifications
                BiomeSelectors.foundInTheNether(),                                                                      //BiomeSelectors Where the new Biome Feature will go
                GenerationStep.Feature.UNDERGROUND_ORES,                                                                //GenerationStep.Feature Selects which type of feature is altered
                ModPlacedFeatures.CUSTOM_NETHER_ORE_PLACED.getKey().get());                                             //Gets the Key for the list of Ores that can be replaced and what they can be replaced by

                                                                                                                        //Calls BiomeModifications
        BiomeModifications.addFeature(                                                                                  //addFeature to BiomeModifications
                BiomeSelectors.foundInTheEnd(),                                                                         //BiomeSelectors Where the new Biome Feature will go
                GenerationStep.Feature.UNDERGROUND_ORES,                                                                //GenerationStep.Feature Selects which type of feature is altered
                ModPlacedFeatures.CUSTOM_ENDSTONE_ORE_PLACED.getKey().get());                                           //Gets the Key for the list of Ores that can be replaced and what they can be replaced by
    }
}

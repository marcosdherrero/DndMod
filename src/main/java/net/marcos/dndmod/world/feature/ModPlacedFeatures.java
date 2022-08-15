package net.marcos.dndmod.world.feature;

import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class ModPlacedFeatures {                                                                                        //Name of the Class

        public static final RegistryEntry<PlacedFeature> CUSTOM_ORE_PLACED =                                            //Creates a new RegistryEntry of Placed Features called CUSTOM_ORE_PLACED
                PlacedFeatures.register(                                                                                //Calls PlacedFeatures.register to register a new configurable feature
                        "custom_ore_placed",                                                                            //ID or name for the configurable feature
                        ModConfiguredFeatures.CUSTOM_ORE,                                                               //Calls ModConfiguredFeatures for our Custom Ore List to see what is replaceable and with what
                        modifiersWithCount(                                                                             //Placement Modifiers
                                9,                                                                                      //Count Modifier,
                                HeightRangePlacementModifier.trapezoid(                                                 //heightModifier . Shape of placement
                                    YOffset.fixed(-80),                                                           //Minimum Range
                                    YOffset.fixed(80))));                                                         //Maximum Range

        public static final RegistryEntry<PlacedFeature> CUSTOM_NETHER_ORE_PLACED =                                     //Creates a new RegistryEntry of Placed Features called CUSTOM_ORE_PLACED
                PlacedFeatures.register(                                                                                //Calls PlacedFeatures.register to register a new configurable feature
                        "custom_nether_ore_placed",                                                                     //ID or name for the configurable feature
                        ModConfiguredFeatures.CUSTOM_NETHER_ORE,                                                        //Calls ModConfiguredFeatures for our Custom Ore List to see what is replaceable and with what
                        modifiersWithCount(                                                                             //Placement Modifiers
                                10,                                                                                     //Count Modifier,
                                HeightRangePlacementModifier.uniform(                                                   //heightModifier . Shape of placement
                                        YOffset.fixed(-80),                                                       //Minimum Range
                                        YOffset.fixed(80))));                                                     //Maximum Range

        public static final RegistryEntry<PlacedFeature> CUSTOM_ENDSTONE_ORE_PLACED =                                   //Creates a new RegistryEntry of Placed Features called CUSTOM_ORE_PLACED
                PlacedFeatures.register(                                                                                //Calls PlacedFeatures.register to register a new configurable feature
                        "custom_endstone_ore_placed",                                                                   //ID or name for the configurable feature
                        ModConfiguredFeatures.CUSTOM_ENDSTONE_ORE,                                                      //Calls ModConfiguredFeatures for our Custom Ore List to see what is replaceable and with what
                        modifiersWithCount(                                                                             //Placement Modifiers
                                10,                                                                                     //Count Modifier,
                                HeightRangePlacementModifier.uniform(                                                   //heightModifier . Shape of placement
                                        YOffset.fixed(-80),                                                       //Minimum Range
                                        YOffset.fixed(80))));                                                     //Maximum Range


        private static List<PlacementModifier> modifiers(                                                               //Placement Modifier List called modifiers
                PlacementModifier countModifier,                                                                        //Parameter type PlacementModifier countModifier
                PlacementModifier heightModifier){                                                                      //Parameter type PlacementModifier heightModifier
                    return List.of(                                                                                     //Returning a List containing:
                            countModifier,                                                                              //countModifier
                            SquarePlacementModifier.of(),                                                               //SquarePlacementModifier
                            heightModifier,                                                                             //heightModifier
                            BiomePlacementModifier.of());                                                               //BiomePlacementModifier
        }

        private static List<PlacementModifier> modifiersWithCount(                                                      //Placement Modifier List called modifiersWithCount
                int count,                                                                                              //Parameter type int count
                PlacementModifier heightModifier) {                                                                     //Parameter type PlacementModifier heightModifier
                    return modifiers(                                                                                   //Returning modifiers(countModifier, heightModifier)
                            CountPlacementModifier.of(count),                                                           //at CountPlacementModifier of(ConstantIntProvider.create(count))
                            heightModifier);                                                                            //heightModifier
        }
        private static List<PlacementModifier> modifiersWithRarity(                                                     //Placement Modifier List called modifiersWithCount
                int chance,                                                                                             //Parameter type int chance
                PlacementModifier heightModifier) {                                                                     //Parameter type PlacementModifier heightModifier
                    return modifiers(                                                                                   //Returning modifiers(countModifier, heightModifier)
                            RarityFilterPlacementModifier.of(chance),                                                   //at RarityFilterPlacementModifier of(RarityFilterPlacementModifier(chance))
                            heightModifier);                                                                            //heightModifier
        }

}

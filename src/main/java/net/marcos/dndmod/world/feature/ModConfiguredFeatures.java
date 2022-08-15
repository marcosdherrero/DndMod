package net.marcos.dndmod.world.feature;

import net.marcos.dndmod.DnDMod;
import net.marcos.dndmod.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.*;

import java.util.List;

public class ModConfiguredFeatures {

    public static final List<OreFeatureConfig.Target> CUSTOM_OVERWORLD_ORES =                                           //Creates a new List for features in the OVERWORLD
        List.of(
            OreFeatureConfig.createTarget(                                                                              //Creates a new Configuration for the replacement createTarget(RuleTest test, BlockState state)
                OreConfiguredFeatures.STONE_ORE_REPLACEABLES,                                                           //OreConfiguredFeatures call which Feature to replace
                ModBlocks.CUSTOM_ORE_BLOCK.getDefaultState()),                                                          //Calls Which block you want to add to replace the original block
            OreFeatureConfig.createTarget(                                                                              //Creates the Second Target to replace //createTarget(RuleTest test, BlockState state)
                OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES,                                                       //Targets Can be extended as long as needed
                ModBlocks.CUSTOM_DEEPSLATE_ORE_BLOCK.getDefaultState()));                                               //Ends the list for the OVERWORLD features to replace


    public static final List<OreFeatureConfig.Target> CUSTOM_NETHER_ORES =                                              //Creates the List for replaceable features in the Nether World
        List.of(
            OreFeatureConfig.createTarget(                                                                              //createTarget(RuleTest test, BlockState state)
                OreConfiguredFeatures.BASE_STONE_NETHER,                                                                //Calls which Feature to replace
                ModBlocks.CUSTOM_NETHERRACK_ORE_BLOCK.getDefaultState()));                                              //Calls which Block to add to Feature

    public static final List<OreFeatureConfig.Target> CUSTOM_ENDSTONE_ORES =                                            //Creates the List for replaceable features in the End World
        List.of(
            OreFeatureConfig.createTarget(                                                                              //createTarget(RuleTest test, BlockState state)
                new BlockMatchRuleTest(Blocks.END_STONE),                                                               //new Test if the block matches the block selected it can replace it
                    ModBlocks.CUSTOM_ENDSTONE_ORE_BLOCK.getDefaultState()));                                            //By our custom ore block

    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> CUSTOM_ORE =                              //Registers our CUSTOM_ORE as a Feature
            ConfiguredFeatures.register(
                    "custom_ore_block",                                                                             //Feature ID
                    Feature.ORE,                                                                                        //Feature Type
                    new OreFeatureConfig(                                                                               //new OreFeatureConfig
                            CUSTOM_OVERWORLD_ORES,                                                                      //CUSTOM OVERWORLD ORES List
                            9));                                                                                        //Feature Size

    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> CUSTOM_NETHER_ORE =                       //Registers our CUSTOM_NETHER_ORE as a Feature
            ConfiguredFeatures.register(                                                                                //register(String id, Feature<DefaultFeatureConfig> feature)
                    "custom_netherrack_ore_block",                                                                      //Feature ID
                    Feature.ORE,                                                                                        //Feature Type
                    new OreFeatureConfig(                                                                               //new OreFeatureConfig
                            CUSTOM_NETHER_ORES,                                                                         //CUSTOM NETHER ORES List
                            25));                                                                                       //Feature Size

    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> CUSTOM_ENDSTONE_ORE =                     //Registers our CUSTOM_ENDSTONE_ORE as a Feature
            ConfiguredFeatures.register(                                                                                //register(String id, Feature<DefaultFeatureConfig> feature)
                    "custom_endstone_ore_block",                                                                        //Feature ID
                    Feature.ORE,                                                                                        //Feature Type
                    new OreFeatureConfig(                                                                               //new OreFeatureConfig
                            CUSTOM_ENDSTONE_ORES,                                                                       //CUSTOM ENDSTONE ORES List
                            20));                                                                                       //Feature Size


    public static void registerConfigureFeatures(){                                                                     //Called in our Mod Initialize Method
        DnDMod.LOGGER.debug("Registering the Mod Configuration Features for " + DnDMod.MOD_ID);
    }
}

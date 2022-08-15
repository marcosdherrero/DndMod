package net.marcos.dndmod.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import net.marcos.dndmod.DnDMod;
import net.marcos.dndmod.block.custom.CustomCropBlock;
import net.marcos.dndmod.block.custom.CustomLampBlock;
import net.marcos.dndmod.block.custom.JumpBlock;
import net.marcos.dndmod.item.ModItemGroup;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;

public class ModBlocks {

    public static final Block CUSTOM_BLOCK = registerBlock(                                                             //Register CUSTOM_BLOCK
        "custom_block",                                                                                                 //name
        new Block(FabricBlockSettings.of                                                                                //new Block made of FabricBlockSettings
                (Material.METAL)                                                                                        //Set Material Type
                .strength(4f)                                                                                           //Set Block Strength
                .requiresTool()),                                                                                       //Tells the game if your block requires a tool to break
        ModItemGroup.DND_MOD_ITEMS);                                                                                    //Block Item Group

    public static final Block CUSTOM_ORE_BLOCK = registerBlock(                                                         //Register CUSTOM_ORE_BLOCK
            "custom_ore_block",                                                                                         //name
            new OreBlock(FabricBlockSettings.of                                                                         //new OreBlock made of FabricBlockSettings of
                    (Material.STONE)                                                                                    //Set Material Type
                    .strength(4f)                                                                                       //Set Block Strength
                    .requiresTool(),                                                                                    //Tells the game if your block requires a tool to break
            UniformIntProvider.create(3,7)),                                                                            //Range of xp gained from breaking ore
            ModItemGroup.DND_MOD_ITEMS);                                                                                //Block Item Group

    public static final Block CUSTOM_DEEPSLATE_ORE_BLOCK = registerBlock(                                               //Register CUSTOM_DEEPSLATE_ORE_BLOCK
            "custom_deepslate_ore_block",
            new OreBlock(FabricBlockSettings.of
                    (Material.STONE)
                    .strength(4f)
                    .requiresTool(),
            UniformIntProvider.create(3,7)),
            ModItemGroup.DND_MOD_ITEMS);

    public static final Block CUSTOM_ENDSTONE_ORE_BLOCK = registerBlock(                                                //Register CUSTOM_ENDSTONE_ORE_BLOCK
            "custom_endstone_ore_block",
            new OreBlock(FabricBlockSettings.of
                    (Material.STONE)
                    .strength(4f)
                    .requiresTool(),
            UniformIntProvider.create(3,7)),
            ModItemGroup.DND_MOD_ITEMS);

    public static final Block CUSTOM_NETHERRACK_ORE_BLOCK = registerBlock(                                              //Register CUSTOM_NETHERRACK_ORE_BLOCK
            "custom_netherrack_ore_block",
            new OreBlock(FabricBlockSettings.of
                    (Material.STONE)
                    .strength(4f)
                    .requiresTool(),
            UniformIntProvider.create(3,7)),
            ModItemGroup.DND_MOD_ITEMS);

    public static final Block CUSTOM_JUMP_BLOCK = registerBlock(                                                        //Register CUSTOM_JUMP_BLOCK
            "custom_jump_block",                                                                                        //name
            new JumpBlock(FabricBlockSettings.of                                                                        //new JumpBlock made of FabricBlockSettings of
                    (Material.METAL)                                                                                    //Set Material Type
                    .strength(4f)                                                                                       //Set Block Strength
                    .requiresTool()),                                                                                   //Tells the game if your block requires a tool to break
            ModItemGroup.DND_MOD_ITEMS);                                                                                //Block Item Group

    public static final Block CUSTOM_LAMP_BLOCK = registerBlock(                                                        //Registers CUSTOM_LAMP_BLOCK
            "custom_lamp_block",                                                                                        //name
            new CustomLampBlock(FabricBlockSettings.of                                                                  //new CustomLampBlock made of FabricBlockSettings of
                    (Material.REDSTONE_LAMP)                                                                            //Set Material Type
                    .strength(4f)                                                                                       //Set Block Strength
                    .requiresTool()                                                                                     //Tells the game if your block requires a tool to break
                    .luminance(state -> state.get(CustomLampBlock.LIT)?15:0)),                                          //Gets the toggle able state of LIT to set the luminance of the block 15 or 0
            ModItemGroup.DND_MOD_ITEMS);                                                                                //Block Item Group

    public static final Block CUSTOM_CROP_BLOCK = registerBlockWithoutItem(                                             //Registers CUSTOM_CROP_BLOCK
            "custom_crop_block",                                                                                        //name
            new CustomCropBlock(FabricBlockSettings.copy(Blocks.WHEAT)));                                               //new CustomCropBlock made of FabricBlockSettings of Blocks.WHEAT


    private static Block registerBlock(String name, Block block, ItemGroup tab){                                        //Method to Register A typical block that has an item form
        registerBlockItem(name, block, tab);                                                                            //Register Block's corresponding inventory item by calling registerBlockItem(String name, Block block, ItemGroup tab)
        return Registry.register(                                                                                       //Returns a registry of register(registry, RegistryKey.of(registry.registryKey, id), entry)
                Registry.BLOCK,                                                                                         //BLOCK registry
                new Identifier(DnDMod.MOD_ID, name),                                                                    //new Identifier
                block);                                                                                                 //Block
    }

    private static Block registerBlockWithoutItem(String name, Block block){                                            //Method to Register a block without registering the item of itself like crops
        return Registry.register(                                                                                       //Returns a registry of register(registry, RegistryKey.of(registry.registryKey, id), entry)
                Registry.BLOCK,                                                                                         //BLOCK registry
                new Identifier(DnDMod.MOD_ID, name),                                                                    //new Identifier
                block);                                                                                                 //Block
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup tab){                                     //Method to Register a Blocks' Item Form
        return Registry.register(                                                                                       //Returns a registry of register(registry, RegistryKey.of(registry.registryKey, id), entry)
                Registry.ITEM,                                                                                          //ITEM Registry
                new Identifier(DnDMod.MOD_ID, name),                                                                    //new Identifier
                new BlockItem(                                                                                          //new BlockItem
                        block,                                                                                          //block
                        new FabricItemSettings().group(tab)));                                                          //new Fabric Item Settings of item group tab
    }

    public static void registerModBlocks(){                                                                             //Called in our Mod Initialize Method
        DnDMod.LOGGER.debug("Registering Blocks for " +DnDMod.MOD_ID);
    }
}

package net.marcos.dndmod.fluid;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import net.marcos.dndmod.DnDMod;
import net.marcos.dndmod.item.ModItemGroup;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModFluids {

    public static FlowableFluid STILL_CUSTOM_FLUID;
    public static FlowableFluid FLOWING_CUSTOM_FLUID;
    public static Block CUSTOM_FLUID_BLOCK;
    public static Item CUSTOM_FLUID_BUCKET;

    public static void register(){
        STILL_CUSTOM_FLUID = Registry.register(
                Registry.FLUID,
                new Identifier((DnDMod.MOD_ID), "custom_fluid"),
                new CustomFluid.Still());
        FLOWING_CUSTOM_FLUID = Registry.register(
                Registry.FLUID,
                new Identifier((DnDMod.MOD_ID), "flowing_custom_fluid"),
                new CustomFluid.Flowing());

        CUSTOM_FLUID_BLOCK = Registry.register(
                Registry.BLOCK,
                new Identifier(DnDMod.MOD_ID, "custom_fluid_block"),
                new FluidBlock(ModFluids.STILL_CUSTOM_FLUID, FabricBlockSettings.copyOf(Blocks.WATER)){});

        CUSTOM_FLUID_BUCKET = Registry.register(
                Registry.ITEM,
                new Identifier(DnDMod.MOD_ID,"custom_fluid_bucket"),
                new BucketItem(ModFluids.STILL_CUSTOM_FLUID, new FabricItemSettings().group(ModItemGroup.DND_MOD_ITEMS)
                        .recipeRemainder(Items.BUCKET).maxCount(1)));
    }
}

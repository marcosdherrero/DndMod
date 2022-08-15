package net.marcos.dndmod.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;

import net.marcos.dndmod.DnDMod;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    public static final ItemGroup DND_MOD_ITEMS = FabricItemGroupBuilder.build(                                         //Creates a new ItemGroup called DND_MOD_ITEMS ItemGroup build(Identifier identifier, Supplier<ItemStack> stackSupplier)
            new Identifier(DnDMod.MOD_ID, "dnd_mod_items"),                                                       //Identifier by MOD_ID and pathway
            () ->new ItemStack(ModItems.DICE_ITEM));                                                                    //ItemStack item image
}

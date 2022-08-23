package net.marcos.dndmod.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.marcos.dndmod.DnDMod;
import net.marcos.dndmod.block.ModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockEntities {
    public static BlockEntityType<CustomTableBlockEntity> CUSTOM_TABLE_BLOCK_ENTITY;

    public static void registerBlockEntities() {
        CUSTOM_TABLE_BLOCK_ENTITY = Registry.register(
                Registry.BLOCK_ENTITY_TYPE,
                new Identifier(DnDMod.MOD_ID, "custom_table_block_entity"),
                FabricBlockEntityTypeBuilder.create(CustomTableBlockEntity::new, ModBlocks.CUSTOM_TABLE_BLOCK).build());
    }
}

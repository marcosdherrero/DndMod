package net.marcos.dndmod.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.marcos.dndmod.DnDMod;
import net.marcos.dndmod.block.ModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import team.reborn.energy.api.EnergyStorage;

public class ModBlockEntities {                                                                                         //Class to instantiate our Custom BlockEntities

    public static BlockEntityType<CustomTableBlockEntity> CUSTOM_TABLE_BLOCK_ENTITY;                                    //Creates a BlockEntityType<CustomTableBlockEntity> called CUSTOM_TABLE_BLOCK_ENTITY

    public static void registerBlockEntities() {                                                                        //Method to registerBlockEntities
        CUSTOM_TABLE_BLOCK_ENTITY = Registry.register(                                                                  //Assigning the CUSTOM_TABLE_BLOCK_ENTITY
                Registry.BLOCK_ENTITY_TYPE,                                                                             //Getting the Registry of Block Entity types
                new Identifier(DnDMod.MOD_ID, "custom_table_block_entity"),                                       //Creates an Identifier for the blockEntity
                FabricBlockEntityTypeBuilder.create(                                                                    //Uses the FabricBlockEntityTypeBuilder to create a
                        CustomTableBlockEntity::new,                                                                    //new: CustomTableBlockEntity
                        ModBlocks.CUSTOM_TABLE_BLOCK).build());                                                         //from: ModBlocks.CUSTOM_TABLE_BLOCK

        EnergyStorage.SIDED.registerForBlockEntity((blockEntity, direction) ->                                          //Registers the Custom Energy
                blockEntity.energyStorage, CUSTOM_TABLE_BLOCK_ENTITY);

        FluidStorage.SIDED.registerForBlockEntity((blockEntity, direction) ->                                           //Registers the Custom Fluid
                blockEntity.fluidStorage, CUSTOM_TABLE_BLOCK_ENTITY);
    }
}

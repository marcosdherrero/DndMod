package net.marcos.dndmod.util;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;

import net.marcos.dndmod.item.ModItems;

import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.entity.EntityEquipmentPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.util.Identifier;

public class ModLootTableModifiers {

    //Alterable: blocks, chests, entities, gameplay
    //Referencing: data.minecraft.loot_tables

    //Set up a new psf Identifier for a Minecraft Loot Table to alter
    public static final Identifier GRASS_BLOCK_ID = new Identifier("minecraft","blocks/grass");
    public static final Identifier IGLOO_STRUCTURE_CHEST_ID =
            new Identifier("minecraft","chests/igloo_chest");
    public static final Identifier CREEPER_ID = new Identifier("minecraft","entities/creeper");

    //Method to Modify the Loot Tables
    public static void modifyLootTables(){                                                                              //Method to Modify Loot Tables modifyLootTables()
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if(GRASS_BLOCK_ID.equals(id)){                                                                              //Checks if ID Loot Table is called
                LootPool.Builder poolBuilder = LootPool.builder()                                                       //Creates a new LootPool.Builder
                        .rolls(ConstantLootNumberProvider.create(1))                                                    //Number of rolls when Item is called
                        .conditionally(RandomChanceLootCondition.builder(0.35f))                                 //Sets what % chance an item will successfully hit with conditionally
                        .with(ItemEntry.builder(ModItems.CUSTOM_SEEDS_ITEM))                                            //Sets The type of Item that is dropped from this loot pool
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f,2.0f))                 //Sets how many items you can get from low,high will get when your drop hits
                                .build());                                                                              //Builds this LootPool.Builder poolBuilder
                tableBuilder.pool(poolBuilder.build());                                                                 //Applies poolBuilder to tableBuilder
            }
            if(IGLOO_STRUCTURE_CHEST_ID.equals(id)){                                                                    //Checks if ID Loot table is called
                LootPool.Builder poolBuilder = LootPool.builder()                                                       //Creates a new LootPool.Builder
                        .rolls(ConstantLootNumberProvider.create(1))                                                    //Number of rolls when Item is called
                        .conditionally(RandomChanceLootCondition.builder(1f))                                    //Sets what % chance an item will successfully hit with conditionally
                        .with(ItemEntry.builder(ModItems.DICE_ITEM))                                                    //Sets The type of Item that is dropped from this loot pool
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f,1.0f))                //Sets how many items you can get from low,high will get when your drop hits
                                .build());                                                                              //Builds this LootPool.Builder poolBuilder
                tableBuilder.pool(poolBuilder.build());                                                                 //Applies poolBuilder to tableBuilder
            }
            if(CREEPER_ID.equals(id)){                                                                                  //Checks if ID Loot table is called
                LootPool.Builder poolBuilder = LootPool.builder()                                                       //Creates a new LootPool.Builder called poolBuilder
                        .rolls(ConstantLootNumberProvider.create(1))                                                    //Number of rolls when Item is called
                        .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.KILLER,           //Creates a new Condition to Check who and with what killed the Entity to make sure it was the player
                            new EntityPredicate.Builder().equipment(                                                    //New Entity Predicate builder to access Entity Data on what killed them
                                EntityEquipmentPredicate.Builder.create()                                               //Access Entity Equipment Predicate Builder to check what is in the player's hand
                                    .mainhand(ItemPredicate.Builder.create()                                            //Checks for item to be in the main hand
                                    .items(Items.DIAMOND_SWORD)                                                         //Item to check that the entity is killed by
                                    .build()).build()).build()))                                                        //builds the three Entity Checks
                        .conditionally(RandomChanceLootCondition.builder(1f))                                    //Sets what % chance an item will successfully hit with conditionally
                        .with(ItemEntry.builder(ModItems.CUSTOM_ITEM))                                                  //Sets The type of Item that is dropped from this loot pool
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f,2.0f))                //Sets how many items you can get from low,high will get when your drop hits
                                .build());                                                                              //Builds this LootPool.Builder poolBuilder
                tableBuilder.pool(poolBuilder.build());                                                                 //Applies poolBuilder to tableBuilder
            }


        });
    }


}

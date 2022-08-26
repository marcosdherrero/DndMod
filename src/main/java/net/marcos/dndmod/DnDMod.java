package net.marcos.dndmod;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.marcos.dndmod.block.ModBlocks;
import net.marcos.dndmod.block.entity.ModBlockEntities;
import net.marcos.dndmod.event.PlayerTickHandler;
import net.marcos.dndmod.fluid.ModFluids;
import net.marcos.dndmod.item.ModItems;
import net.marcos.dndmod.networking.ModMessages;
import net.marcos.dndmod.painting.ModPaintings;
import net.marcos.dndmod.recipe.ModRecipes;
import net.marcos.dndmod.screen.ModScreenHandlers;
import net.marcos.dndmod.util.ModLootTableModifiers;
import net.marcos.dndmod.util.ThirstLevelData;
import net.marcos.dndmod.villager.ModTrades;
import net.marcos.dndmod.villager.ModVillagers;
import net.marcos.dndmod.world.feature.ModConfiguredFeatures;
import net.marcos.dndmod.world.gen.ModOreGeneration;

import net.minecraft.entity.player.PlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DnDMod implements ModInitializer {																			//Main class DnDMod implements ModInitializer

	public static final String MOD_ID = "dndmod";																		//MOD_ID
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);												//Creates Logger

	@Override																											//Overrides the onInitialize method onInitialize()
	public void onInitialize() {
		ModConfiguredFeatures.registerConfigureFeatures();																//Register the ModConfigureFeatures
		ModItems.registerModItems();																					//Register the ModItems
		ModBlocks.registerModBlocks();																					//Register the ModBlocks
		ModVillagers.registerVillagers();																				//Register the ModVillagers
		ModTrades.registerTrades();																						//Register the ModTrades
		ModPaintings.registerPaintings();																				//Register the ModPaintings
		ModOreGeneration.generateOres();																				//Generate the ModOreGeneration
		ModLootTableModifiers.modifyLootTables();																		//Modify the ModLookTablesModifiers

		ModMessages.registerC2SPackets();																				//Register ModMessages the Client communicating to the Server
		ServerTickEvents.START_SERVER_TICK.register(new PlayerTickHandler());											//Register a new PlayerTickHandler in the Server Tick Events
		ModFluids.register();																							//Register the custom fluids
		ModBlockEntities.registerBlockEntities();																		//Register the custom Block Entities
		ModScreenHandlers.registerAllScreenHandlers();	 																//Register all the custom Screen Handlers
		ModRecipes.registerRecipes();																					//Register all Recipe Registries

		LOGGER.info("Hello Fabric world!");
	}
}

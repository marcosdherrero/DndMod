package net.marcos.dndmod;

import net.fabricmc.api.ModInitializer;

import net.marcos.dndmod.block.ModBlocks;
import net.marcos.dndmod.item.ModItems;
import net.marcos.dndmod.painting.ModPaintings;
import net.marcos.dndmod.util.ModLootTableModifiers;
import net.marcos.dndmod.villager.ModTrades;
import net.marcos.dndmod.villager.ModVillagers;
import net.marcos.dndmod.world.feature.ModConfiguredFeatures;
import net.marcos.dndmod.world.gen.ModOreGeneration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DnDMod implements ModInitializer {																			//Main class DnDMod implements ModInitializer

	public static final String MOD_ID = "dndmod";																		//MOD_ID
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);												//Creates Logger

	@Override																											//Overrides the onInitialize method onInitialize()
	public void onInitialize() {
		ModConfiguredFeatures.registerConfigureFeatures();																//registers our ModConfigureFeatures
		ModItems.registerModItems();																					//register ModItems
		ModBlocks.registerModBlocks();																					//register ModBlocks
		ModVillagers.registerVillagers();																				//register ModVillagers
		ModTrades.registerTrades();																						//register ModTrades
		ModPaintings.registerPaintings();																				//register ModPaintings
		ModOreGeneration.generateOres();																				//generate ModOreGeneration
		ModLootTableModifiers.modifyLootTables();																		//modify ModLookTablesModifiers
		LOGGER.info("Hello Fabric world!");
	}
}
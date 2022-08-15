package net.marcos.dndmod.villager;

import com.google.common.collect.ImmutableSet;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.object.builder.v1.villager.VillagerProfessionBuilder;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;

import net.marcos.dndmod.DnDMod;
import net.marcos.dndmod.block.ModBlocks;
import net.marcos.dndmod.item.ModItems;

import net.minecraft.block.Block;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;

public class ModVillagers {

    public static final PointOfInterestType CUSTOM_POI_ONE =                                                            //Adds Our Custom Point of Interest One
            registerPOI("custom_poi_one",                                                                        //registerPOI parameters (String name, Block block)
                    ModBlocks.CUSTOM_LAMP_BLOCK);                                                                       // Sets the POI to the CUSTOM_LAMP_BLOCK

    public static final VillagerProfession CUSTOM_VILLAGER_PROFESSION =                                                 //Adds Our Custom Villager Profession CUSTOM_VILLAGER_PROFESSION
            registerProfession(                                                                                         //register the profession parameters(String name,RegistryKey<PointOfInterestType> type)
                    "custom_villager_profession",                                                                       //name/ID of the profession
                    RegistryKey.of(                                                                                     //RegistryKey.of(RegistryKey<? extends Registry<T>> registry, Identifier value)
                            Registry.POINT_OF_INTEREST_TYPE_KEY,
                            new Identifier(DnDMod.MOD_ID, "custom_poi_one")));

    public static VillagerProfession registerProfession(String name, RegistryKey<PointOfInterestType> type) {           //Method to Register the Custom Villager Profession
        return Registry.register(                                                                                       //return Registry.register(registry, RegistryKey.of(registry.registryKey, id), entry);
                Registry.VILLAGER_PROFESSION,                                                                           //registry of KEY
                new Identifier(DnDMod.MOD_ID, name),                                                                    //RegistryKey.of(registry.registryKey, id)
                VillagerProfessionBuilder.create()                                                                      //entry of VillagerProfessionBuilder
                        .id(new Identifier(DnDMod.MOD_ID, name))                                                        //Set ID
                        .workstation(type)                                                                              //Set Workstation
                        .workSound(SoundEvents.ENTITY_VILLAGER_WORK_CLERIC)                                             //Set WorkSounds
                        .harvestableItems(ModItems.CUSTOM_SEEDS_ITEM, ModItems.CUSTOM_VEGETABLE_ITEM)                   //Set Harvestable Items
                        .build());
    }

    public static PointOfInterestType registerPOI(String name, Block block) {                                           //Method to Register a Block as a Point of Interest
        return PointOfInterestHelper.register(                                                                          //Returns a registrations of Point of Interest Type Helper Parameters (Identifier id, int ticketCount, int searchDistance, Iterable<BlockState> blocks)
                new Identifier(DnDMod.MOD_ID, name),                                                                    //Identifier ID
                1,                                                                                                      //Ticket Count
                1,                                                                                                      //Search Distance
                ImmutableSet.copyOf(                                                                                    //Create a Set from a copy of
                        block.getStateManager().getStates()));                                                          //All BlockState of these blocks
    }


    public static void registerVillagers() {                                                                            //Connects the ModVillager Class to the Main Mod Class
        DnDMod.LOGGER.debug("Registering Villagers for " + DnDMod.MOD_ID);
    }
}



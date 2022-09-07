package net.marcos.dndmod.villager;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.object.builder.v1.villager.VillagerProfessionBuilder;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.marcos.dndmod.DnDMod;
import net.marcos.dndmod.block.ModBlocks;
import net.marcos.dndmod.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.village.*;
import net.minecraft.world.World;
import net.minecraft.world.poi.PointOfInterestType;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModVillagers extends VillagerEntity {

    public static final PointOfInterestType CUSTOM_POI_ONE =                                                            //Adds Our Custom Point of Interest One
            registerPOI("custom_poi_one",                                                                        //registerPOI parameters (String name, Block block)
                    ModBlocks.CUSTOM_LAMP_BLOCK);                                                                       //Sets the POI to the CUSTOM_LAMP_BLOCK

    public static final VillagerProfession CUSTOM_DICE_PROFESSION =                                                     //Adds Our Custom Villager Profession CUSTOM_VILLAGER_PROFESSION
            registerProfession(                                                                                         //register the profession parameters(String name,RegistryKey<PointOfInterestType> type)
                    "custom_villager_profession",                                                                       //name/ID of the profession
                    RegistryKey.of(                                                                                     //RegistryKey.of(RegistryKey<? extends Registry<T>> registry, Identifier value)
                            Registry.POINT_OF_INTEREST_TYPE_KEY,
                            new Identifier(DnDMod.MOD_ID, "custom_poi_one")));

    public ModVillagers(EntityType<? extends VillagerEntity> type, World world){                                        //Constructor
        super(EntityType.VILLAGER, MinecraftClient.getInstance().world);
    }
    
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
    public static void registerTrades(){
        TradeOfferHelper.registerVillagerOffers(
                CUSTOM_DICE_PROFESSION, 1, factories -> {
                    factories.addAll(List.of(DICE_MAN_FACTORY));
                } );

    }

    public static TradeOffers.Factory[] DICE_MAN_FACTORY = new TradeOffers.Factory[]{
       new BuyItemForItemFactory(ModItems.D_ONE_HUNDRED, 1, ModItems.CUSTOM_ITEM, 16,1,30, 0.5f),
       new BuyItemForItemFactory(ModItems.D_TWENTY, 1, ModItems.CUSTOM_ITEM, 16,1,30, 0.5f),
       new BuyItemForItemFactory(ModItems.D_TWELVE, 1, ModItems.CUSTOM_ITEM, 16,1,30, 0.5f),
       new BuyItemForItemFactory(ModItems.D_TEN, 1, ModItems.CUSTOM_ITEM, 16,1,30, 0.5f),
       new BuyItemForItemFactory(ModItems.D_EIGHT, 1, ModItems.CUSTOM_ITEM, 16,1,30, 0.5f),
       new BuyItemForItemFactory(ModItems.D_SIX, 1, ModItems.CUSTOM_ITEM, 16,1,30, 0.5f),
       new BuyItemForItemFactory(ModItems.D_FOUR, 1, ModItems.CUSTOM_ITEM, 16,1,30, 0.5f),
       new BuyItemForItemFactory(ModItems.D_TWO, 1, ModItems.CUSTOM_ITEM, 16,1,30, 0.5f),
    };

    static class BuyItemForItemFactory implements TradeOffers.Factory{
        private final Item buy;
        private final int buyAmount;
        private final Item sell;
        private final int sellAmount;
        private final int maxUses;
        private final int experience;
        private final float multiplier;

        BuyItemForItemFactory(Item buy, int buyAmount, Item sell, int sellAmount, int maxUses, int experience, float multiplier) {
            this.buy = buy;
            this.buyAmount = buyAmount;
            this.sell = sell;
            this.sellAmount = sellAmount;
            this.maxUses = maxUses;
            this.experience = experience;
            this.multiplier = multiplier;
        }
        @Nullable
        @Override
        public TradeOffer create(Entity entity, Random random) {
            ItemStack buyItem = new ItemStack(this.buy, this.buyAmount);
            ItemStack sellItem = new ItemStack(this.buy, this.sellAmount);
            return new TradeOffer(buyItem, sellItem, this.maxUses, this.experience, this.multiplier);
        }
    }
    @Override
    protected void fillRecipes() {
        VillagerData villagerData = this.getVillagerData();
        Int2ObjectMap<TradeOffers.Factory[]> int2ObjectMap = TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(villagerData.getProfession());
        if (int2ObjectMap == null || int2ObjectMap.isEmpty()) {
            return;
        }
        TradeOffers.Factory[] factories = int2ObjectMap.get(villagerData.getLevel());
        if (factories == null) {
            return;
        }
        TradeOfferList tradeOfferList = this.getOffers();
        this.fillRecipesFromPool(tradeOfferList, factories, 8);
    }
    @Override
    protected void fillRecipesFromPool(TradeOfferList recipeList, TradeOffers.Factory[] pool, int count) {
        super.fillRecipesFromPool(recipeList, pool, 8);
    }

    public static void registerVillagers() {                                                                            //Connects the ModVillager Class to the Main Mod Class
        DnDMod.LOGGER.debug("Registering Villagers for " + DnDMod.MOD_ID);
    }
}



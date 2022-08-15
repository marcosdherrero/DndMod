package net.marcos.dndmod.villager;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;

import net.marcos.dndmod.DnDMod;
import net.marcos.dndmod.item.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraft.village.TradeOffer;

public class ModTrades {

    public static void registerTrades(){                                                                                //Method to Register the Trades
        TradeOfferHelper.registerVillagerOffers(                                                                        //registerVillagerOffers(VillagerProfession profession, int level, Consumer<List<TradeOffers.Factory>> factories)
                ModVillagers.CUSTOM_VILLAGER_PROFESSION,                                                                //Calls the ModVillagers Class and adds our static CUSTOM_VILLAGER_PROFESSION
                1,                                                                                                      //Level that the Villager will need to be to obtain this trade
                factories -> {                                                                                          //Creates a Factory List
                    factories.add(((entity, random) -> new TradeOffer(                                                  //Adds a new factory to the list of the TradeOffer - public TradeOffer(ItemStack buyItem, ItemStack sellItem, int maxUses, int merchantExperience, float priceMultiplier)
                            new ItemStack(ModItems.CUSTOM_ITEM, 64),                                             //Sets Trader buy item from new ItemStack(Items, count)
                            new ItemStack(ModItems.DICE_ITEM,1),                                                 //Sets Trader sell item from new ItemStack(Items, count)
                            1,10,0.02f                                                                //Max Items, Merchant Experience, Price Multiplier
                    )));
                });
        DnDMod.LOGGER.debug("Registering Villager Trades for " + DnDMod.MOD_ID);
    }

}

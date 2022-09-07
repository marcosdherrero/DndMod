package net.marcos.dndmod.villager.custom;

import net.marcos.dndmod.item.ModItems;
import net.marcos.dndmod.villager.ModVillagers;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerData;
import net.minecraft.village.VillagerType;
import org.jetbrains.annotations.Nullable;

public class CustomDiceVillager extends VillagerData {

    public static final TradeOffers.Factory[] CustomDiceVillagerTradeOffers = new TradeOffers.Factory[]{
             new BuyItemAForItemB(ModItems.D_ONE_HUNDRED,1,ModItems.CUSTOM_ITEM,1),
             new BuyItemAForItemB(ModItems.D_TWENTY,1,ModItems.CUSTOM_ITEM,1),
             new BuyItemAForItemB(ModItems.D_TWELVE,1,ModItems.CUSTOM_ITEM,1),
             new BuyItemAForItemB(ModItems.D_TEN,1,ModItems.CUSTOM_ITEM,1),
             new BuyItemAForItemB(ModItems.D_EIGHT,1,ModItems.CUSTOM_ITEM,1),
             new BuyItemAForItemB(ModItems.D_SIX,1,ModItems.CUSTOM_ITEM,1),
             new BuyItemAForItemB(ModItems.D_FOUR,1,ModItems.CUSTOM_ITEM,1),
             new BuyItemAForItemB(ModItems.D_TWO,1,ModItems.CUSTOM_ITEM,1)
            };

    public CustomDiceVillager() {
        super(VillagerType.PLAINS, ModVillagers.CUSTOM_DICE_PROFESSION, 1);
    }

    static class BuyItemAForItemB implements TradeOffers.Factory{
        private Item purchaseItem;
        private Item barterItem;
        private int purchaseAmount;
        private int barterAmount;
        private ItemStack purchaseStack;
        private ItemStack barterStack;

        public BuyItemAForItemB(Item purchaseItem, int purchaseAmount, Item barterItem, int barterAmount){
            this.purchaseItem = purchaseItem;
            this.barterItem = barterItem;
            this.purchaseAmount = purchaseAmount;
            this.barterAmount = barterAmount;
            this.purchaseStack = new ItemStack(this.purchaseItem);
            this.barterStack = new ItemStack(this.barterItem);
        }

        @Nullable
        @Override
        public TradeOffer create(Entity entity, Random random) {
            ItemStack buyItemStack = new ItemStack(this.purchaseItem,this.barterAmount);
            ItemStack barterItemStack = new ItemStack(this.barterItem,this.barterAmount);
            return new TradeOffer(buyItemStack,barterItemStack,1,25,0.0f);
        }
    }
}

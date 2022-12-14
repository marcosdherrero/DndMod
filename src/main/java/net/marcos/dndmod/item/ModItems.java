package net.marcos.dndmod.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.marcos.dndmod.DnDMod;
import net.marcos.dndmod.block.ModBlocks;
import net.marcos.dndmod.item.custom.DiceItem;
import net.minecraft.block.ComposterBlock;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    //public static List DICE_LIST = Registry.ITEM.stream().filter(DiceItem::isADiceItem).collect(Collectors.toList());

    public static final Item D_TWENTY = registerDiceItem("d_twenty",                                                    //Register Dice Item registerItem(String name, Item item)
            new DiceItem(new FabricItemSettings().group(ModItemGroup.DND_MOD_ITEMS).maxCount(1),  20));     //new DiceItem with FabricItemSettings adding the group and maxCount, and the number of sides for the die

    public static final Item D_TWELVE = registerDiceItem("d_twelve",                                                    //Register Dice Item registerItem(String name, Item item)
            new DiceItem(new FabricItemSettings().group(ModItemGroup.DND_MOD_ITEMS).maxCount(1),  12));     //new DiceItem with FabricItemSettings adding the group and maxCount, and the number of sides for the die

    public static final Item D_TEN = registerDiceItem("d_ten",                                                    //Register Dice Item registerItem(String name, Item item)
            new DiceItem(new FabricItemSettings().group(ModItemGroup.DND_MOD_ITEMS).maxCount(1),  10));     //new DiceItem with FabricItemSettings adding the group and maxCount, and the number of sides for the die

    public static final Item D_EIGHT = registerDiceItem("d_eight",                                                    //Register Dice Item registerItem(String name, Item item)
            new DiceItem(new FabricItemSettings().group(ModItemGroup.DND_MOD_ITEMS).maxCount(1),  8));     //new DiceItem with FabricItemSettings adding the group and maxCount, and the number of sides for the die

    public static final Item D_SIX = registerDiceItem("d_six",                                                    //Register Dice Item registerItem(String name, Item item)
            new DiceItem(new FabricItemSettings().group(ModItemGroup.DND_MOD_ITEMS).maxCount(1),  6));     //new DiceItem with FabricItemSettings adding the group and maxCount, and the number of sides for the die

    public static final Item D_FOUR = registerDiceItem("d_four",                                                    //Register Dice Item registerItem(String name, Item item)
            new DiceItem(new FabricItemSettings().group(ModItemGroup.DND_MOD_ITEMS).maxCount(1),  4));     //new DiceItem with FabricItemSettings adding the group and maxCount, and the number of sides for the die

    public static final Item D_TWO = registerDiceItem("d_two",                                                    //Register Dice Item registerItem(String name, Item item)
            new DiceItem(new FabricItemSettings().group(ModItemGroup.DND_MOD_ITEMS).maxCount(1),  2));     //new DiceItem with FabricItemSettings adding the group and maxCount, and the number of sides for the die

    public static final Item D_ONE_HUNDRED = registerDiceItem("d_one_hundred",                                       //Register Dice Item registerItem(String name, Item item)
            new DiceItem(new FabricItemSettings().group(ModItemGroup.DND_MOD_ITEMS).maxCount(1),  100));    //new DiceItem with FabricItemSettings adding the group and maxCount, and the number of sides for the die

    public static final Item CUSTOM_RAW_ITEM = registerItem("custom_raw_item",                                   //Register Custom Raw Ore Item registerItem(String name, Item item)
            new Item(new FabricItemSettings().group(ModItemGroup.CUSTOM_ITEMS)));                                      //new Item with FabricItemSettings adding the group

    public static final Item CUSTOM_ITEM = registerItem("custom_item",                                           //Register Custom Item registerItem(String name, Item item)
            new Item(new FabricItemSettings().group(ModItemGroup.CUSTOM_ITEMS)));                                      //new Item with FabricItemSettings adding the group

    public static final Item CUSTOM_SEEDS_ITEM = registerCompostItem(                                                   //Register Custom Seeds Item registerCompostItem(String name, Item item, float levelIncreaseChance)
            "custom_seeds_item",                                                                                        //name
            new AliasedBlockItem(ModBlocks.CUSTOM_CROP_BLOCK,                                                           //new AliasedBlockItem
            new FabricItemSettings().group(ModItemGroup.CUSTOM_ITEMS)),                                                //new FabricItemSettings ItemGroup
            3);                                                                                                         //Level Increase Chance when Composted

   public static final Item CUSTOM_GREAT_SWORD = registerItem("custom_great_sword",
           new SwordItem(ToolMaterials.DIAMOND, 10, 10f,
                   new FabricItemSettings().group(ModItemGroup.CUSTOM_ITEMS).maxCount(1)));

    public static final Item CUSTOM_VEGETABLE_ITEM = registerCompostItem("custom_vegetable_item",                //Register Custom Vegetable Item registerCompostItem(String name, Item item, float levelIncreaseChance)
            new Item(new FabricItemSettings()                                                                           //new FabricItemSettings of
                    .group(ModItemGroup.CUSTOM_ITEMS)                                                                  //ItemGroup
                    .food(new FoodComponent.Builder()                                                                   //Makes the item into a FoodComponent
                            .hunger(4)                                                                                  //Hunger Modifier
                            .saturationModifier(4f)                                                                     //Saturation Modifier
                            .build())),                                                                                 //build the food item
                     7);                                                                                                //Level Increase Chance when Composted


    private static Item registerItem(String name, Item item){                                                           //Method to Register an Item registerItem(String name, Item item)
        return Registry.register(                                                                                       //Returns a registry of (registry, RegistryKey.of(registry.registryKey, id), entry)
                Registry.ITEM,                                                                                          //Registry
                new Identifier(DnDMod.MOD_ID, name),                                                                    //new Identifier for Registry
                item);                                                                                                  //item to be registered
    }

    private static Item registerCompostItem(String name, Item item, float levelIncreaseChance) {                        //method to Register a compostable Item registerCompostItem(String name, Item item, float levelIncreaseChance)
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put((ItemConvertible)item.asItem(), levelIncreaseChance);          //Converts the item into a Compostable item
        return registerItem(name, item);
    }

    private static Item registerDiceItem(String name, Item item) {                        //method to Register a compostable Item registerCompostItem(String name, Item item, float levelIncreaseChance)
        return registerItem(name, item);
    }


    public static void registerModItems(){                                                                               //Called in our Mod Initialize Method
        DnDMod.LOGGER.debug("Registering Items for " + DnDMod.MOD_ID);
    }
}

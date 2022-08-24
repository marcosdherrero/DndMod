package net.marcos.dndmod.block.entity;

import net.marcos.dndmod.item.ModItems;
import net.marcos.dndmod.screen.CustomTableBlockScreenHandler;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

public class CustomTableBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {

    private final DefaultedList<ItemStack> inventory =                                                                  //Creates an Inventory for the Block Entity
            DefaultedList.ofSize(3, ItemStack.EMPTY);                                                              //int number of Slots,  the default Item in the slots

    protected final PropertyDelegate propertyDelegate;                                                                  //Creates a Delegate Synchronizes the client and the server
    private int progress = 0;                                                                                           //Creates a progress variable for each CustomTableBlockEntity which will track the crafting time
    private int maxProgress = 72;                                                                                       //Creates a maxProgress variable for each CustomTableBlockEntity

    public CustomTableBlockEntity(BlockPos pos, BlockState state) {                                                     //Constructor for the CustomBlockEntity
        super(ModBlockEntities.CUSTOM_TABLE_BLOCK_ENTITY, pos, state);                                                  //Sends a Super for the BlockEntity of (ModBlockEntities.CUSTOM_TABLE_BLOCK_ENTITY, pos, state)
        this.propertyDelegate = new PropertyDelegate() {                                                                //Creates a new property delegate for this CustomTableBlockEntity
            public int get(int index) {                                                                                 //Method to get the stored values of the CustomTableBlockEntity for the PropertyDelegate
                switch (index) {
                    case 0:
                        return CustomTableBlockEntity.this.progress;
                    case 1:
                        return CustomTableBlockEntity.this.maxProgress;
                    default:
                        return 0;
                }
            }

            public void set(int index, int value) {                                                                     //Method to set the stored values of the CustomTableBlockEntity for the PropertyDelegate
                switch (index) {
                    case 0:
                        CustomTableBlockEntity.this.progress = value;
                        break;
                    case 1:
                        CustomTableBlockEntity.this.maxProgress = value;
                        break;
                }
            }

            public int size() {                                                                                         //Method to get the size of the PropertyDelegate
                return 2;
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() {                                                                        //Method to get the inventory for the CustomTableBlockEntity
        return this.inventory;
    }

    @Override
    public Text getDisplayName() {                                                                                      //Method to get the name of the CustomTableBlockEntity
        return Text.literal("Custom Table Block");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {                             //Method to create a new ScreenHandler
        return new CustomTableBlockScreenHandler(syncId,inv,this, this.propertyDelegate);                      //returns a new CustomTableBlockScreenHandler
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {                                                                          //Method to have the BlockEntity write NBT values
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("custom_table_block.progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {                                                                              //Method to have the BlockEntity read NBT values
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
        progress = nbt.getInt("custom_table_block.progress");
    }

    public static  void tick(World world, BlockPos blockPos, BlockState state, CustomTableBlockEntity entity) {         //Method for what happens each tick for the CustomTableBlockEntity
        if(world.isClient()) {return;}                                                                                  //Checks if the world is on the client and just progress null

        if(hasRecipe(entity)){                                                                                          //Checks if the CustomTableBlockEntity has the proper recipe in its slot
            entity.progress++;                                                                                          //Progresses the crafting
            markDirty(world, blockPos, state);                                                                          //Marks the Entity as dirty, so it will make sure the world reads the new values
            if(entity.progress >= entity.maxProgress){                                                                  //Checks if the CustomTableBlockEntity's crafting is done
                craftItem(entity);                                                                                      //Method to Craft the new Item and trash the old item
            }
        }else {
            entity.resetProgress();                                                                                     //Resets the progress if the recipe item is not correct or is empty
            markDirty(world, blockPos, state);                                                                          //Marks the Entity as dirty, so it will make sure the world reads the new values
        }
    }

    private void resetProgress() {                                                                                      //Method to reset the progress of the CustomTableBlockEntity's crafting time
        this.progress =  0;
    }

    private static void craftItem(CustomTableBlockEntity entity) {                                                      //Method to Craft the item of the CustomTableBlockEntity
        SimpleInventory inventory = new SimpleInventory(entity.size());                                                 //Builds the inventory for the blockEntity
        for (int i = 0; i< entity.size(); i++){
            inventory.setStack(i, entity.getStack(i));
        }                                                                                                               //

        if(hasRecipe(entity)){                                                                                          //Checks if the CustomTableBlockEntity has the proper recipe in its slot
            entity.removeStack(1,1);                                                                        //Removes one item from the crafting slot
            entity.setStack(2, new ItemStack(ModItems.CUSTOM_ITEM, entity.getStack(2).getCount()+1));  //Adds one item in the crafted slot

            entity.resetProgress();                                                                                     //Resets the progress of the Crafting time
        }
    }

    private static boolean hasRecipe(CustomTableBlockEntity entity) {                                                   //Method to check that the Crafting slot contains the correct item
        SimpleInventory inventory = new SimpleInventory(entity.size());                                                 //Builds the inventory for the blockEntity
        for (int i = 0; i< entity.size(); i++){
            inventory.setStack(i, entity.getStack(i));
        }                                                                                                               //

        boolean hasCustomRawItemInFirstSlot = entity.getStack(1).getItem() == ModItems.CUSTOM_RAW_ITEM;            //Creates a new boolean variable that checks that the item is CustomRawItem

        return hasCustomRawItemInFirstSlot &&                                                                           //Returns True if crafting item matches +
               canInsertAmountIntoOutputSlot(inventory) &&                                                              //Method to check if the Crafted Slot has space available +
               canInsertItemIntoOutputSlot(inventory, ModItems.CUSTOM_ITEM);                                            //Method to check if the Crafted Slot item is the correct item
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleInventory inventory, Item output) {                        //Method to check if the Crafted Slot item is the correct item
        return inventory.getStack(2).getItem() == output || inventory.getStack (2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleInventory inventory) {                                   //Method to check if the Crafted Slot has space available
        return inventory.getStack(2).getMaxCount() > inventory.getStack(2).getCount();
    }
}

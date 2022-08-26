package net.marcos.dndmod.block.entity;

import net.marcos.dndmod.recipe.CustomTableBlockRecipe;
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

import java.util.Optional;

public class CustomTableBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {

    private final DefaultedList<ItemStack> inventory =                                                                  //Creates an Inventory for the Block Entity
            DefaultedList.ofSize(3, ItemStack.EMPTY);                                                              //int number of Slots,  the default Item in the slots

    protected final PropertyDelegate propertyDelegate;                                                                  //Creates a Delegate Synchronizes the client and the server
    private int progress = 0;                                                                                           //Creates a progress variable for each CustomTableBlockEntity which will track the crafting time
    private int maxProgress = 72;                                                                                       //Creates a maxProgress variable for each CustomTableBlockEntity
    private static int craftAmount = 2;                                                                                 //Creates a Static Variable of craftAmount for the CustomTableBlockEntity
    private static int removeAmount = 2;                                                                                //Creates a Static Variable of craftAmount for the CustomTableBlockEntity
    private static int slot0 = 0;                                                                                       //Creates a Static Variable of slot0 for the CustomTableBlockEntity
    private static int recipeSlot = 1;                                                                                  //Creates a Static Variable of recipeSlot for the CustomTableBlockEntity inventory slot where the recipe items will go
    private static int craftedSlot = 2;                                                                                 //Creates a Static Variable of craftedSlot for the CustomTableBlockEntity inventory slot where the crafted items will go

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
        return this.inventory;                                                                                          //Returns a list of this CustomTableBlockEntity's inventory
    }

    @Override
    public Text getDisplayName() {                                                                                      //Method to get the name of the CustomTableBlockEntity
        return Text.literal("Custom Table Block");                                                               //Returns a sting of "Custom Table Block"
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {                             //Method to create a new ScreenHandler
        return new CustomTableBlockScreenHandler(syncId,inv,this, this.propertyDelegate);                      //returns a new CustomTableBlockScreenHandler
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {                                                                          //Method to have the BlockEntity write NBT values
        super.writeNbt(nbt);                                                                                            //Sends write information to the NBT data on the server
        Inventories.writeNbt(nbt, inventory);                                                                           //Writes the inventory data on the NBT of the CustomTableBlockEntity
        nbt.putInt("custom_table_block.progress", progress);                                                            //Writes the progress of the crafting to the nbt
    }

    @Override
    public void readNbt(NbtCompound nbt) {                                                                              //Method to have the BlockEntity read NBT values
        Inventories.readNbt(nbt, inventory);                                                                            //Read the inventory of the CustomTableBlockEntity
        super.readNbt(nbt);                                                                                             //Sends the read information to the NBT
        progress = nbt.getInt("custom_table_block.progress");                                                      //Progresses the crafting of the item
    }

    public static void tick(World world, BlockPos blockPos, BlockState state, CustomTableBlockEntity entity) {          //Method for what happens each tick for the CustomTableBlockEntity
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
        this.progress =  0;                                                                                             //Sets the progress of this CustomTableBlockEntity to 0
    }

    private static void craftItem(CustomTableBlockEntity entity) {                                                      //Method to Craft the item of the CustomTableBlockEntity
        SimpleInventory inventory = new SimpleInventory(entity.size());                                                 //Builds the inventory for the blockEntity
        for (int i = 0; i< entity.size(); i++){
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<CustomTableBlockRecipe> recipe =                                                                       //Creates a new recipe variable that gets the list of recipe items from the list
                entity.getWorld().getRecipeManager()
                        .getFirstMatch(CustomTableBlockRecipe.Type.INSTANCE, inventory, entity.getWorld());

        if(hasRecipe(entity)){                                                                                          //Checks if the CustomTableBlockEntity has the proper recipe in its slot
            entity.removeStack(recipeSlot, removeAmount);                                                               //Removes the removeItem amount from the recipeSlot when crafted

            entity.setStack(craftedSlot, new ItemStack(recipe.get().getOutput().getItem(),                              //Adds craftAmount of items in the crafted slot
                    entity.getStack(craftedSlot).getCount() + craftAmount));

            entity.resetProgress();                                                                                     //Resets the progress of the Crafting time
        }
    }

    private static boolean hasRecipe(CustomTableBlockEntity entity) {                                                   //Method to check that the Crafting slot contains the correct item
        SimpleInventory inventory = new SimpleInventory(entity.size());                                                 //Builds the inventory for the blockEntity
        for (int i = 0; i< entity.size(); i++){
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<CustomTableBlockRecipe> match =                                                                        //Creates an Optional list of CustomTableBlockRecipe called match
                entity.getWorld().getRecipeManager()                                                                    //gets the world
                .getFirstMatch(CustomTableBlockRecipe.Type.INSTANCE, inventory, entity.getWorld());                     //Finds the first match from the json list in if it matches the inventory slot in the CustomTableBlockEntity

        return match.isPresent() &&                                                                                     //Returns True if crafting item matches +
               canInsertAmountIntoOutputSlot(inventory) &&                                                              //Method to check if the Crafted Slot has space available +
               canInsertItemIntoOutputSlot(inventory, match.get().getOutput().getItem());                               //Method to check if the Crafted Slot item is the correct item
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleInventory inventory, Item output) {                        //Method to check if the Crafted Slot item is the correct item
        return inventory.getStack(craftedSlot).getItem() == output || inventory.getStack (craftedSlot).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleInventory inventory) {                                   //Method to check if the Crafted Slot has space available
        return                                                                                                          //Return True if
                inventory.getStack(craftedSlot).getMaxCount() >                                                         //If the MaxStack of the item in the crafted slot is greater than
                inventory.getStack(craftedSlot).getCount()+craftAmount;                                                 //Stack in the crafted slot + the craftAmount
    }
}

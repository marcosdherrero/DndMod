package net.marcos.dndmod.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class CustomTableBlockScreenHandler  extends ScreenHandler {                                                     //Class to control the Screen and for how the game understands that information

    private final Inventory inventory;                                                                                  //Creates and Inventory variable for this CustomTableBlockScreenHandler
    private final PropertyDelegate propertyDelegate;                                                                    //Creates a PropertyDelegate variable for this CustomTableBlockScreenHandler

    public CustomTableBlockScreenHandler(int syncId, PlayerInventory playerInventory){                                  //Constructor for the CustomTableBlockScreenHandler
        this(syncId,playerInventory,new SimpleInventory(3), new ArrayPropertyDelegate(2));
    }

    public CustomTableBlockScreenHandler(int syncId, PlayerInventory playerInventory,                                   //Constructor for the CustomTableBlockScreenHandler
                                         Inventory inventory, PropertyDelegate delegate) {
        super(ModScreenHandlers.CUSTOM_TABLE_BLOCK_SCREEN_HANDLER, syncId);                                             //Sends a Super for the CustomTableBlockScreenHandler of (ModScreenHandlers.CUSTOM_TABLE_BLOCK_SCREEN_HANDLER, syncId)
        checkSize(inventory, 3);                                                                            //Method to check the size of the inventory of the CustomTableBlockScreenHandler
        this.inventory = inventory;                                                                                     //Assigns the inventory of this CustomTableBlockScreenHandler to the inventory of the CustomTableBlockEntity
        inventory.onOpen(playerInventory.player);                                                                       //calls the onOpen method to add the player's inventory to the ScreenHandler
        this.propertyDelegate = delegate;                                                                               //Assigns the propertyDelegate of this CustomTableBlockScreenHandler to the delegate of the CustomTableBlockEntity

        this.addSlot(new Slot(inventory,0,12,15));                                                         //Assigns the 0 Slot and location
        this.addSlot(new Slot(inventory,1,86,15));                                                         //Assigns the 1 Slot and location
        this.addSlot(new Slot(inventory,2,86,60));                                                         //Assigns the 2 Slot and location


        addPlayerInventory(playerInventory);                                                                            //Method to add the Player's Inventory to the ScreenHandler type CustomTableBlockScreenHandler
        addPlayerHotBar(playerInventory);                                                                               //Method to add the Player's Hot Bar to the ScreenHandler type CustomTableBlockScreenHandler

        addProperties(delegate);                                                                                        //Method to add the delegate's property's to the ScreenHandler type CustomTableBlockScreenHandler
    }

    public boolean isCrafting(){                                                                                        //Boolean Method that checks if the Crafting is true
        return propertyDelegate.get(0) > 0;
    }

    public int getScaledProgress(){                                                                                     //Int Method that scales the progress of the Arrow Bar when crafting
        int progress = this.propertyDelegate.get(0);                                                                    //sets the progress Variable to the current progress of the CustomTableBlockEntity
        int maxProgress = this.propertyDelegate.get(1);                                                                 //sets the maxProgress Variable to the maxProgress of the CustomTableBlockEntity
        int progressArrowSize = 26;                                                                                     //Sets the progressArrowSize to the width in pixels of the arrow

        return                                                                                                          //Returns Ternary Operator (boolean expression)?(True Result):(False Result)
                maxProgress != 0 && progress != 0 ?                                                                     //if max progress does not equal 0 and progress does not equal 0
                        progress * progressArrowSize / maxProgress:                                                     //return: progress*progressArrowSize/maxProgress
                        0;                                                                                              //else return: 0
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {                                                   //Method to allow for shift Clicking an item
        ItemStack newStack = ItemStack.EMPTY;                                                                           //Creates an ItemStack variable called newStack which is EMPTY
        Slot slot = this.slots.get(invSlot);                                                                            //Creates a Slot variable called slot which gets the inv slot at the position of invSlot
        if(slot != null && slot.hasStack()){                                                                            //Checks if slot is not null and if it has any item
            ItemStack originalStack = slot.getStack();                                                                  //Creates an ItemStack variable called originalStack and gets the item in that slot
            newStack = originalStack.copy();                                                                            //newStack now copies the originalStack
            if(invSlot<this.inventory.size()){                                                                          //Checks if the invSlot is within the inventory size of the ScreenHandler
                if(!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)){           //Checks if you can insert this item in that slot
                    return ItemStack.EMPTY;                                                                             //return: EMPTY for the item because it will move
                }
            }else if(!this.insertItem(originalStack, 0, this.inventory.size(), false)){              //Checks if you can insert this item in your inventory
                return ItemStack.EMPTY;                                                                                 //return: EMPTY for the item since it will move to another spot in your inventory
            }

            if(originalStack.isEmpty()){                                                                                //Checks if the  original stack is empty
                slot.setStack(ItemStack.EMPTY);                                                                         //Set the Slot's stack to EMPTY
            }else {                                                                                                     //If the Original stack is not empty
                slot.markDirty();                                                                                       //Set the slot to Dirty so the update will happen onto the Entity's data
            }
        }
        return newStack;                                                                                                //return: newStack which is a copy of the original Stack
    }

    @Override
    public boolean canUse(PlayerEntity player) {                                                                        //Boolean Method to allow the player to use the ScreenHandler or not
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory){                                                   //Helper Method to add player's inventory
        for(int i = 0; i < 3; ++i){                                                                                     //Rows
            for(int j = 0; j < 9; ++j){                                                                                 //Columns
                this.addSlot(new Slot(playerInventory, j+i*9+9, 8+j*18, 86+i*18));                         //adding each slot at their locations index(9-35)
            }
        }
    }

    private void addPlayerHotBar(PlayerInventory playerInventory){                                                      //Helper Method to add player's hotbar
        for(int i = 0; i < 9; ++i){                                                                                     //Columns
            this.addSlot(new Slot(playerInventory, i, 8+i*18, 144));                                             //Adding each slot at their locations index(0-8)
        }
    }
}

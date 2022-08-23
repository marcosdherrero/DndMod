package net.marcos.dndmod.screen;

import net.marcos.dndmod.block.entity.CustomTableBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;

public class CustomTableBlockScreenHandler  extends ScreenHandler {

    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;

    public CustomTableBlockScreenHandler(int syncId, PlayerInventory playerInventory){
        this(syncId,playerInventory,new SimpleInventory(3), new ArrayPropertyDelegate(2));
    }

    public CustomTableBlockScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate delegate) {
        super(ModScreenHandlers.CUSTOM_TABLE_BLOCK_SCREEN_HANDLER, syncId);
        checkSize(inventory, 3);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);
        this.propertyDelegate = delegate;

        this.addSlot(new Slot(inventory,0,12,15));
        this.addSlot(new Slot(inventory,1,86,15));
        this.addSlot(new Slot(inventory,2,86,60));


        addPlayerInventory(playerInventory);
        addPlayerHotBar(playerInventory);

        addProperties(delegate);
    }

    public boolean isCrafting(){
        return propertyDelegate.get(0) > 0;
    }

    public int getScaledProgress(){
        int progress = this.propertyDelegate.get(0);
        int maxProgress = this.propertyDelegate.get(1);
        int progressArrowSize = 26; //Width in Pixels of the arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress:0;
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if(slot != null && slot.hasStack()){
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if(invSlot<this.inventory.size()){
                if(!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)){
                    return ItemStack.EMPTY;
                }
            }else if(!this.insertItem(originalStack, 0, this.inventory.size(), false)){
                return ItemStack.EMPTY;
            }

            if(originalStack.isEmpty()){
                slot.setStack(ItemStack.EMPTY);
            }else {
                slot.markDirty();
            }
        }
        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory){                                                   //Helper Method to add player's inventory
        for(int i = 0; i < 3; ++i){                                                                                     //Rows
            for(int j = 0; j < 9; ++j){                                                                                 //Columns
                this.addSlot(new Slot(playerInventory, j+i*9+9, 8+j*18, 86+i*18));
            }
        }
    }

    private void addPlayerHotBar(PlayerInventory playerInventory){                                                      //Helper Method to add player's hotbar
        for(int i = 0; i < 9; ++i){                                                                                     //Columns
            this.addSlot(new Slot(playerInventory, i, 8+i*18, 144));
        }
    }
}

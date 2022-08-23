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
            DefaultedList.ofSize(3, ItemStack.EMPTY);                                                              //int number of Slots,  the default Item in its slots

    protected final PropertyDelegate propertyDelegate;                                                                  //Creates a Delegate Synchronizes the client and the server
    private int progress = 0;
    private int maxProgress = 72;

    public CustomTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CUSTOM_TABLE_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            public int get(int index) {
                switch (index) {
                    case 0:
                        return CustomTableBlockEntity.this.progress;
                    case 1:
                        return CustomTableBlockEntity.this.maxProgress;
                    default:
                        return 0;
                }
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0:
                        CustomTableBlockEntity.this.progress = value;
                        break;
                    case 1:
                        CustomTableBlockEntity.this.maxProgress = value;
                        break;
                }
            }

            public int size() {
                return 2;
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Custom Table Block");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new CustomTableBlockScreenHandler(syncId,inv,this, this.propertyDelegate);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("custom_table_block.progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
        progress = nbt.getInt("custom_table_block.progress");
    }

    public static  void tick(World world, BlockPos blockPos, BlockState state, CustomTableBlockEntity entity) {
        if(world.isClient()) {return;}

        if(hasRecipe(entity)){
            entity.progress++;
            markDirty(world, blockPos, state);
            if(entity.progress >= entity.maxProgress){
                craftItem(entity);
            }
        }else {
            entity.resetProgress();
            markDirty(world, blockPos, state);
        }
    }

    private void resetProgress() {
        this.progress =  0;
    }

    private static void craftItem(CustomTableBlockEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());                                                 //Builds the inventory for the blockEntity
        for (int i = 0; i< entity.size(); i++){
            inventory.setStack(i, entity.getStack(i));
        }

        if(hasRecipe(entity)){
            entity.removeStack(1,1);
            entity.setStack(2, new ItemStack(ModItems.CUSTOM_ITEM, entity.getStack(2).getCount()+1));

            entity.resetProgress();
        }
    }

    private static boolean hasRecipe(CustomTableBlockEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());                                                 //Builds the inventory for the blockEntity
        for (int i = 0; i< entity.size(); i++){
            inventory.setStack(i, entity.getStack(i));
        }

        boolean hasCustomRawItemInFirstSlot = entity.getStack(1).getItem() == ModItems.CUSTOM_RAW_ITEM;

        return hasCustomRawItemInFirstSlot &&
               canInsertAmountIntoOutputSlot(inventory) &&
               canInsertItemIntoOutputSlot(inventory, ModItems.CUSTOM_ITEM);
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleInventory inventory, Item output) {
        return inventory.getStack(2).getItem() == output || inventory.getStack (2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleInventory inventory) {
        return inventory.getStack(2).getMaxCount() > inventory.getStack(2).getCount();
    }
}

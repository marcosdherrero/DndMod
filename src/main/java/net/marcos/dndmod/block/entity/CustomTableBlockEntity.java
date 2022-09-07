package net.marcos.dndmod.block.entity;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;

import net.marcos.dndmod.block.custom.CustomTableBlock;
import net.marcos.dndmod.fluid.ModFluids;
import net.marcos.dndmod.item.ModItems;
import net.marcos.dndmod.networking.ModMessages;
import net.marcos.dndmod.recipe.CustomTableBlockRecipe;
import net.marcos.dndmod.screen.CustomTableBlockScreenHandler;

import net.marcos.dndmod.util.FluidStack;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.base.SimpleEnergyStorage;

import java.util.Optional;

public class CustomTableBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory { //Class for the CustomTableBlockEntity

    private final DefaultedList<ItemStack> inventory =                                                                  //Creates an Inventory for the Block Entity
            DefaultedList.ofSize(3, ItemStack.EMPTY);                                                              //int number of Slots,  the default Item in the slots

    public ItemStack getRenderStack() {                                                                                 //Method to Render a stack from the inventory
        if(this.getStack(2).isEmpty()){                                                                            //Checks that the second slot is empty
            return this.getStack(1);                                                                               //returns the ItemStack in that slot
        }else{
            return this.getStack(2);                                                                               //returns the ItemStack in that slot
        }
    }

    public void setInventory(DefaultedList<ItemStack> list) {                                                           //Sets the inventory of the Entity
        for(int i = 0; i<list.size(); i++){                                                                             //Iterates through the size of the list
            this.inventory.set(i,inventory.get(i));                                                                     //sets the inventory items to the inventory of the blockEntity
        }
    }

    @Override
    public void markDirty() {                                                                                           //Method to mark Dirty the BlockEntity which means it will be updated
        if(!world.isClient()){                                                                                          //Checks that the server is not on the client
            PacketByteBuf data = PacketByteBufs.create();                                                               //Creates a new PacketByteBuf called Buf
            data.writeInt(inventory.size());                                                                            //Writes the inventory size
            for(int i = 0; i < inventory.size(); i++){                                                                  //Iterates through the inventory size
                data.writeItemStack(inventory.get(i));                                                                  //Writes the Items in teh Inventory
            }
            data.writeBlockPos(getPos());                                                                               //Writes the block position

            for(ServerPlayerEntity player: PlayerLookup.tracking((ServerWorld) world, getPos())){                       //Iterates Through all the players
                ServerPlayNetworking.send(player, ModMessages.CUSTOM_TABLE_BLOCK_ITEM_SYNC, data);                      //Sends the buf from the Server to the Client
            }
        }
        super.markDirty();                                                                                              //Marks the blockEntity as dirty
    }

    public final SimpleEnergyStorage energyStorage =                                                                    //Creates a Static variable of SimpleEnergyStorage type from the TechReborn Mod API
            new SimpleEnergyStorage(30000, 32, 32){                                        //Sets the SimpleEnergyStorage Settings, Capacity, MaxInsert, MaxExtract
                @Override
                protected void onFinalCommit() {                                                                        //Called after an outer transaction succeeded,
                    markDirty();                                                                                        //To perform irreversible actions such as markDirty() or neighbor updates.
                    if(!world.isClient()){                                                                              //Checks if the world is not on the client
                        sendEnergyPacket();                                                                             //Sends the Packet from the server to client
                    }
                }
            };
    private void sendEnergyPacket() {                                                                                   //Method to send the EnergyPacket from the server to client
        PacketByteBuf data = PacketByteBufs.create();                                                                   //Creates a buf to be used to send data
        data.writeLong(energyStorage.amount);                                                                           //Writes the data to the buf
        data.writeBlockPos(getPos());                                                                                   //Writes new data to the buf

        for(ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, getPos())){                          //Iterates through the list of players on the server
            ServerPlayNetworking.send(player, ModMessages.CUSTOM_TABLE_BLOCK_ENERGY_SYNC, data);                        //Syncs the player client and server data
        }
    }

    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<FluidVariant>() {           //Creates a new SingleVariantStorage variable
        @Override
        protected FluidVariant getBlankVariant() {                                                                      //Method to get a Blank Fluid Variant
            return FluidVariant.blank();
        }
        @Override
        protected long getCapacity(FluidVariant variant) {                                                              //Method to get the capacity of the fluid container
            return FluidStack.convertDropletsToMb(FluidConstants.BUCKET)*20;                                            //20,000 mB
        }
        @Override
        protected void onFinalCommit() {                                                                                //Called after an outer transaction succeeded, To perform irreversible actions such as markDirty() or neighbor updates.
            markDirty();                                                                                                //Marks the world ready to commit changes
            if(!world.isClient()){                                                                                      //Checks if the world is not on the client
                sendFluidPacket();                                                                                      //Method to send the fluid data from server to Client
            }
          }
        };

        private void sendFluidPacket() {                                                                                //Method to send the fluid data from server to Client
            PacketByteBuf data = PacketByteBufs.create();                                                               //Creates a new buf for fluid data
            fluidStorage.variant.toPacket(data);                                                                        //Writes the data to the packet of the fluid in the container
            data.writeLong(fluidStorage.amount);                                                                        //Writes new data to the buf
            data.writeBlockPos(getPos());                                                                               //Writes new data to the buf
            for(ServerPlayerEntity player: PlayerLookup.tracking((ServerWorld) world, getPos())){                       //Iterates through the list of players on the server
                ServerPlayNetworking.send(player, ModMessages.CUSTOM_TABLE_BLOCK_FLUID_SYNC, data);                     //Syncs the player client and server data
            }
        }


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
                switch (index) {                                                                                        //Checks which output is asked
                    case 0:
                        return CustomTableBlockEntity.this.progress;                                                    //Progress of the individual container
                    case 1:
                        return CustomTableBlockEntity.this.maxProgress;                                                 //Max Progress of the individual container
                    default:
                        return 0;
                }
            }
            public void set(int index, int value) {                                                                     //Method to set the stored values of the CustomTableBlockEntity for the PropertyDelegate
                switch (index) {                                                                                        //Checks which output is asked
                    case 0:
                        CustomTableBlockEntity.this.progress = value;                                                   //Sets the progress value
                        break;
                    case 1:
                        CustomTableBlockEntity.this.maxProgress = value;                                                //Sets the max progress value
                        break;
                }
            }
            public int size() {                                                                                         //Method to get the size of the PropertyDelegate
                return 2;                                                                                               //This must match how many cases there are
            }
        };
    }
    public void setEnergyLevel(long energyLevel){                                                                       //Method to set the energy level to the client from the server
        this.energyStorage.amount =  energyLevel;                                                                       //Sets the individual energy level amount
    }
    public void setFluidLevel(FluidVariant fluidVariant, long fluidLevel){                                              //Method to set the fluidVariant & fluid level to the client from the server
        this.fluidStorage.variant = fluidVariant;                                                                       //Sets the fluid variant
        this.fluidStorage.amount = fluidLevel;                                                                          //Sets the fluid level
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
        sendEnergyPacket();
        sendFluidPacket();
        return new CustomTableBlockScreenHandler(syncId,inv,this, this.propertyDelegate);                        //returns a new CustomTableBlockScreenHandler

    }
    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {                                  //Method to send the data when the screen is opened from server to client
        buf.writeBlockPos(this.pos);                                                                                    //Gets the data from the server at this blockPos
    }
    @Override
    protected void writeNbt(NbtCompound nbt) {                                                                          //Method to have the BlockEntity write NBT values
        super.writeNbt(nbt);                                                                                            //Sends write information to the NBT data on the server
        Inventories.writeNbt(nbt, inventory);                                                                           //Writes the inventory data on the NBT of the CustomTableBlockEntity
        nbt.putInt("custom_table_block.progress", progress);                                                            //Writes the progress of the crafting to the nbt
        nbt.putLong("custom_table_block.energy", energyStorage.amount);                                                 //Writes the Energy Storage Amount to the nbt
        nbt.put("custom_table_block.variant", fluidStorage.variant.toNbt());                                       //Writes the Fluid Variant type to the nbt
        nbt.putLong("custom_table_block.fluid", fluidStorage.amount);                                                   //Writes the Fluid Amount to the nbt
    }

    @Override
    public void readNbt(NbtCompound nbt) {                                                                              //Method to have the BlockEntity read NBT values
        Inventories.readNbt(nbt, inventory);                                                                            //Read the inventory of the CustomTableBlockEntity
        super.readNbt(nbt);                                                                                             //Sends the read information to the NBT
        progress = nbt.getInt("custom_table_block.progress");                                                      //Progresses the crafting of the item
        energyStorage.amount = nbt.getLong("custom_table_block.energy");                                           //Reads the Energy Amount from the nbt
        fluidStorage.variant = FluidVariant.fromNbt((NbtCompound) nbt.get("custom_table_block.variant"));               //Reads the Fluid Variant Type from the nbt
        fluidStorage.amount = nbt.getLong("custom_table_block.fluid");                                             //Reads the Fluid Amount from the nbt
    }

    public static void tick(World world, BlockPos blockPos, BlockState state, CustomTableBlockEntity entity) {          //Method for what happens each tick for the CustomTableBlockEntity
        if(world.isClient()) {return;}                                                                                  //Checks if the world is on the client and just progress null
        
        if(thisEnergyItem(entity)){
            try(Transaction transaction = Transaction.openOuter()){
                entity.energyStorage.insert(16,transaction);
                transaction.commit();
            }
        }
        if(hasRecipe(entity) && hasEnoughEnergy(entity) && hasEnoughFluid(entity)){                                     //Checks if the CustomTableBlockEntity has the proper recipe in its slot
            entity.progress++;                                                                                          //Progresses the crafting
            extractEnergy(entity);                                                                                      //Method to remove energy from the container
            markDirty(world, blockPos, state);                                                                          //Marks the Entity as dirty, so it will make sure the world reads the new values
            if(entity.progress >= entity.maxProgress){                                                                  //Checks if the CustomTableBlockEntity's crafting is done
                craftItem(entity);                                                                                      //Method to Craft the new Item and trash the old item
                extractFluid(entity);                                                                                   //Method to extract fluid from the container
            }
        }else {
            entity.resetProgress();                                                                                     //Resets the progress if the recipe item is not correct or is empty
            markDirty(world, blockPos, state);                                                                          //Marks the Entity as dirty, so it will make sure the world reads the new values
        }

        if(hasFluidSourceInSlot(entity)){                                                                               //Checks that the container has the proper fluid source
            transferFluidToFluidStorage(entity);                                                                        //Method to transfer the fluid from the source to the container
        }
    }
    private static void extractFluid(CustomTableBlockEntity entity) {                                                   //Method to remove the fluid from the container
        try(Transaction transaction = Transaction.openOuter()){                                                         //Creates a Transaction variable to check if the container has crafted
            entity.fluidStorage.extract(FluidVariant.of(ModFluids.STILL_CUSTOM_FLUID),                                  //Extracts the fluid
                    500, transaction);
            transaction.commit();
        }
    }
    private static void extractEnergy(CustomTableBlockEntity entity) {                                                  //Method to remove the energy in the container
        try(Transaction transaction = Transaction.openOuter()){                                                         //Creates a Transaction variable to check if the container has crafted
            entity.energyStorage.extract(10,transaction);                                                   //Extracts the energy
            transaction.commit();
        }
    }
    private static boolean hasFluidSourceInSlot(CustomTableBlockEntity entity) {                                        //Boolean Method to check if the Fluid Source is correct
        return entity.getStack(0).getItem() == ModFluids.CUSTOM_FLUID_BUCKET;
    }
    private static void transferFluidToFluidStorage(CustomTableBlockEntity entity) {                                    //Method to Move the Source Fluid to the container
        try(Transaction transaction = Transaction.openOuter()){
            entity.fluidStorage.insert(FluidVariant.of(ModFluids.STILL_CUSTOM_FLUID),
                    FluidStack.convertDropletsToMb(FluidConstants.BUCKET), transaction);
            transaction.commit();
            entity.setStack(0, new ItemStack(Items.BUCKET));
        }
    }
    private static boolean hasEnoughFluid(CustomTableBlockEntity entity) {                                              //Boolean Method to check if the container has enough fluid to craft
      return entity.fluidStorage.amount >= 500;
    }

    private static boolean hasEnoughEnergy(CustomTableBlockEntity entity) {                                             //Boolean Method to check if the container has enough energy to craft
        return entity.energyStorage.amount >=  32;
    }
    private static boolean thisEnergyItem(CustomTableBlockEntity entity) {                                              //Boolean Method to check which item will give the container energy and from which slot
        return entity.getStack(0).getItem() == ModItems.CUSTOM_GREAT_SWORD;
    }
    private void resetProgress() {                                                                                      //Method to reset the progress of the CustomTableBlockEntity's crafting time
        this.progress =  0;                                                                                             //Sets the progress of this CustomTableBlockEntity to 0
    }
    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        Direction localDir = this.getWorld().getBlockState(this.pos).get(CustomTableBlock.FACING);                      //Gets the local Direction of the block. Reorienting the block's direction relative to the world's direction
        if(side == Direction.UP || side == Direction.DOWN){
            return false;
        }
        return switch (localDir){
            default ->
                    side.getOpposite() == Direction.NORTH && slot == 1 && stack.getItem() == ModItems.CUSTOM_RAW_ITEM||        //Insert Custom Raw Item only into slot 1 from the top
                    side.getOpposite() == Direction.EAST && slot == 1 && stack.getItem() == ModItems.CUSTOM_RAW_ITEM||         //Insert Custom Raw Item only into slot 1 from the Left
                    side.getOpposite() == Direction.WEST && slot == 0 && stack.getItem() == ModFluids.CUSTOM_FLUID_BUCKET;     //Insert Custom Raw Item only into slot 1 from the Right
            case EAST ->
                    side.rotateYClockwise() == Direction.NORTH && slot == 1 && stack.getItem() == ModItems.CUSTOM_RAW_ITEM||
                    side.rotateYClockwise() == Direction.EAST && slot == 1 && stack.getItem() == ModItems.CUSTOM_RAW_ITEM||
                    side.rotateYClockwise() == Direction.WEST && slot == 0 && stack.getItem() == ModFluids.CUSTOM_FLUID_BUCKET;
            case SOUTH -> side == Direction.NORTH && slot == 1||
                    side == Direction.EAST && slot == 1||
                    side == Direction.WEST && slot == 0;
            case WEST -> side.rotateYCounterclockwise() == Direction.NORTH && slot == 1 && stack.getItem() == ModItems.CUSTOM_RAW_ITEM||
                    side.rotateYCounterclockwise() == Direction.EAST && slot == 1 && stack.getItem() == ModItems.CUSTOM_RAW_ITEM||
                    side.rotateYCounterclockwise() == Direction.WEST && slot == 0 && stack.getItem() == ModFluids.CUSTOM_FLUID_BUCKET;
        };
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        Direction localDir = this.getWorld().getBlockState(this.pos).get(CustomTableBlock.FACING);                      //Gets the local Direction of the block. Reorienting the block's direction relative to the world's direction
        if(side == Direction.UP){
            return false;
        }

        if(side == Direction.DOWN){
            return slot == 2;
        }

        return switch(localDir){
            default ->
                    side.getOpposite() == Direction.SOUTH && slot == 2 ||                                               //Extract Down from slot 2
                    side.getOpposite() == Direction.EAST && slot == 2;                                                  //Extract Right from slot 2 needs other mods with items to extract from alternate sides than the bottom
            case EAST ->
                    side.rotateYClockwise() == Direction.SOUTH && slot == 2 ||
                    side.rotateYClockwise() == Direction.EAST && slot == 2;
            case SOUTH ->
                    side == Direction.SOUTH && slot == 2 ||
                    side == Direction.EAST && slot == 2;
            case WEST  ->
                    side.rotateYCounterclockwise() == Direction.SOUTH && slot == 2 ||
                            side.rotateYCounterclockwise() == Direction.EAST && slot == 2;
        };
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

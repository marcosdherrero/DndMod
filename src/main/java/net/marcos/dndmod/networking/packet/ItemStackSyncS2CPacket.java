package net.marcos.dndmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.marcos.dndmod.block.entity.CustomTableBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class ItemStackSyncS2CPacket {                                                                                   //Custom ItemStackSync Packet Class to Sync from Server to Client
    public static void receive(MinecraftClient client,
                               ClientPlayNetworkHandler handler,
                               PacketByteBuf buf,
                               PacketSender responseSender) {
        int size = buf.readInt();                                                                                       //Creates and sets an int variable called size that is equal to the reading from the buf
        DefaultedList<ItemStack> list = DefaultedList.ofSize(size, ItemStack.EMPTY);                                    //Creates a defaulted list of the that is empty of the size from the buf
        for(int i = 0; i<size; i++){                                                                                    //Iterates through the list
            list.set(i, buf.readItemStack());                                                                           //Sets the list to the Item
        }
        BlockPos position = buf.readBlockPos();                                                                         //Creates and sets a BlockPos variable called position to the reading from the buf

        if(client.world.getBlockEntity(position) instanceof CustomTableBlockEntity blockEntity){                        //checks that this blockEntity is a correct instance of blockEntity
            blockEntity.setInventory(list);                                                                             //Sets the inventory from the list
        }
    }
}

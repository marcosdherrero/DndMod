package net.marcos.dndmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.marcos.dndmod.block.entity.CustomTableBlockEntity;
import net.marcos.dndmod.screen.CustomTableBlockScreenHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

public class CustomTableEnergySyncS2CPacket {                                                                           //Packet Class that Syncs the Energy from Server to Client
    public static void receive(MinecraftClient client,
                               ClientPlayNetworkHandler handler,
                               PacketByteBuf buf,
                               PacketSender responseSender){

        long energy = buf.readLong();                                                                                   //Creates a long variable for the amount of
        BlockPos position = buf.readBlockPos();
        if(client.world.getBlockEntity(position) instanceof CustomTableBlockEntity blockEntity){                        //Checks if the block at that position is an instance of the CustomTableBlockEntity
            blockEntity.setEnergyLevel(energy);                                                                         //Sends the Client the syncing info on blockEntity Energy

            if(client.player.currentScreenHandler instanceof CustomTableBlockScreenHandler screenHandler &&             //Checks if the player has the Custom Table Screen open
                                                            screenHandler.blockEntity.getPos().equals(position)){
                blockEntity.setEnergyLevel(energy);                                                                     //Sends the Client the syncing info on blockEntity Energy
            }
        }
    }
}

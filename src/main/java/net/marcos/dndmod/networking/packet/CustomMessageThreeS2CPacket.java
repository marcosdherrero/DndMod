package net.marcos.dndmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.marcos.dndmod.util.IEntityDataSaver;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class CustomMessageThreeS2CPacket {                                                                              //Packet Class Thirst Level From Server to Client

    public static void receive(MinecraftClient client,                                                                  //Receiver Method for the Thirst Level Server to Client
                               ClientPlayNetworkHandler handler,
                               PacketByteBuf buf,
                               PacketSender responseSender){

        ((IEntityDataSaver) client.player).getPersistentData().putInt("thirst_level", buf.readInt());
    }
}

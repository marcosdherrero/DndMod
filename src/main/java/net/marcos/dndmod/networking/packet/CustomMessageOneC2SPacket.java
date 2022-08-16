package net.marcos.dndmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class CustomMessageOneC2SPacket {
    public static void  receive(                                                                                        //Creates receive method for the Packet
            MinecraftServer server,
            ServerPlayerEntity player,
            ServerPlayNetworkHandler handler,
            PacketByteBuf buf,
            PacketSender responseSender)
    {                                                                                                                   //Everything Here Happens ONLY on the Server

        EntityType.COW.spawn(                                                                                           //Spawn a Cow Entity
                ((ServerWorld) player.world),
                null,
                null,
                player,
                player.getBlockPos(),
                SpawnReason.TRIGGERED,
                true,
                false);

    }
}

package net.marcos.dndmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.marcos.dndmod.block.entity.CustomTableBlockEntity;
import net.marcos.dndmod.screen.CustomTableBlockScreenHandler;
import net.marcos.dndmod.util.FluidStack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

public class CustomTableFluidSyncS2CPacket {                                                                            //Packet Class ContainerFluid Sync from the Server to the Client

    public static void receive(MinecraftClient client,
                               ClientPlayNetworkHandler handler,
                               PacketByteBuf buf,
                               PacketSender responseSender) {
        FluidVariant variant = FluidVariant.fromPacket(buf);
        long fluidLevel = buf.readLong();
        BlockPos position = buf.readBlockPos();

        if (client.world.getBlockEntity(position) instanceof CustomTableBlockEntity blockEntity) {                      //Checks that the blockEntity is of the correct type
            blockEntity.setFluidLevel(variant, fluidLevel);                                                             //Sends the Fluid level
            if (client.player.currentScreenHandler instanceof CustomTableBlockScreenHandler screenHandler &&            //Checks: that the screen Handler is the correct instance and
                    screenHandler.blockEntity.getPos().equals(position)) {                                              //that the blockEntity's position is correct
                blockEntity.setFluidLevel(variant, fluidLevel);
                screenHandler.setFluid(new FluidStack(variant, fluidLevel));
            }
        }
    }
}

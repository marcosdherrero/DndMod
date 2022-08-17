package net.marcos.dndmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.marcos.dndmod.util.IEntityDataSaver;
import net.marcos.dndmod.util.ThirstLevelData;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class CustomMessageTwoC2SPacket {
    public static void  receive(                                                                                        //Creates receive method for the Packet
            MinecraftServer server,
            ServerPlayerEntity player,
            ServerPlayNetworkHandler handler,
            PacketByteBuf buf,
            PacketSender responseSender)
    {                                                                                                                   //Everything Here Happens ONLY on the Server
       ServerWorld world = player.getWorld();                                                                           //Creates a ServerWorld variable called world which is our player.getWorld();
       //int playerThirstLevel = ((IEntityDataSaver) player).getPersistentData().getInt("thirst_level");                //Creates an Int Variable player Thirst Level
       if(isWaterAroundThem(player, world, 2)){
        world.playSound(                                                                                                //playSound(@Nullable PlayerEntity player, BlockPos pos, SoundEvent sound, SoundCategory category, float volume, float pitch)
                   null,                                                                                                //Player Entity
                   player.getBlockPos(),                                                                                //Position that the sound will come from
                   SoundEvents.ENTITY_GENERIC_DRINK,                                                                    //Sound Event
                   SoundCategory.PLAYERS,                                                                               //Sound Category
                   .5F,                                                                                                 //Volume
                   world.random.nextFloat()*0.1F+0.9F);                                                                 //Pitch

           ThirstLevelData.increaseThirstLevel(((IEntityDataSaver) player), 10);                               //Refill Player Thirst by 10

           player.sendMessage(Text.literal("Drinking Water! Thirst Level is: " +                                 //Tell Player They are Successfully drinking and what their current thirst level is
                           ((IEntityDataSaver) player).getPersistentData().getInt("thirst_level"))
                   .fillStyle(Style.EMPTY.withColor(Formatting.AQUA)),true);                                    //Formats the info to Aqua

       }else{

           world.playSound(                                                                                             //playSound(@Nullable PlayerEntity player, BlockPos pos, SoundEvent sound, SoundCategory category, float volume, float pitch)
                   null,                                                                                                //Player Entity
                   player.getBlockPos(),                                                                                //Position that the sound will come from
                   SoundEvents.ENTITY_DONKEY_ANGRY,                                                                     //Sound Event
                   SoundCategory.PLAYERS,                                                                               //Sound Category
                   .5F,                                                                                                 //Volume
                   world.random.nextFloat()*0.1F+0.9F);                                                                 //Pitch

           ThirstLevelData.removeThirstLevel(((IEntityDataSaver) player), 1);                                //Refill Player Thirst by 10

           player.sendMessage(Text.literal("No Water Around! Thirst Level is: "+                                //Sends Player Message that they failed to drink water and what their current thirst level is
                           ((IEntityDataSaver) player).getPersistentData().getInt("thirst_level"))
                   .fillStyle(Style.EMPTY.withColor(Formatting.RED)),true);                                     //Formats the info to red

       }
    }

    private static boolean isWaterAroundThem(ServerPlayerEntity player, ServerWorld world, int distance) {              //Method to check if water is around the player
        return BlockPos.stream                                                                                          //Gets a stream of block's positions
                        (player.getBoundingBox().expand(distance))                                                      //Gets all blocks within a certain distance
                .map(world::getBlockState)                                                                              //Maps The world's block states
                .filter(state -> state.isOf(Blocks.WATER))                                                              //Filter to check for the block state we request
                .toArray().length                                                                                       //Adds them to the array for that block stream
                                  > 0;                                                                                  //Checks if there is at least 1 of our requested blocks returned as a Boolean
    }

}

package net.marcos.dndmod.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.marcos.dndmod.util.IEntityDataSaver;
import net.marcos.dndmod.util.ThirstLevelData;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Random;

public class PlayerTickHandler implements ServerTickEvents.StartTick{
    @Override                                                                                                           //Overrides the onStartTick Method in ServerTickEvents.StartTick
    public void onStartTick(MinecraftServer server) {                                                                   //Checks the server for players
        for(ServerPlayerEntity player : server.getPlayerManager().getPlayerList()){                                     //Goes through the list of players on the server
            if(new Random().nextFloat()<= 0.005f){                                                                      //Gets a random float and if it is less than a certain number the player will lose thirst
                removeThirstOnTick(player);                                                                             //Method to remove the thirst of a player
            }
        }
    }

    private static void removeThirstOnTick(ServerPlayerEntity player) {                                                 //Method to remove thirst when called
        IEntityDataSaver dataPlayer = ((IEntityDataSaver) player);                                                      //Makes a dataPlayer to save the Player Thirst Info
        ThirstLevelData.removeThirstLevel(dataPlayer, getRandomNumberOneTThree());                                      //Remove Thirst Level of dataPlayer of a random number 1-3

        if(ThirstLevelData.getThirstLevel(dataPlayer)%5 == 0){                                                          //Display your thirst every five thirst lost
            player.sendMessage(Text.literal("Getting Thirstier: Thirst Level is: "+
                            ThirstLevelData.getThirstLevel(dataPlayer))
                    .fillStyle(Style.EMPTY.withColor(Formatting.RED)),true);
        }
    }

    public static int getRandomNumberOneTThree(){                                                                       //Method to get a random number 1 through 3
        return (net.minecraft.util.math.random.Random.createLocal().nextInt(3))+1;
    }

}

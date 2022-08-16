package net.marcos.dndmod.util;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import net.marcos.dndmod.networking.ModMessages;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class ThirstLevelData {

    public static int increaseThirstLevel(IEntityDataSaver player, int drinkUp){                                        //Method to Increase a player's thirst level by drinkUp amount
        NbtCompound nbt = player.getPersistentData();                                                                   //Makes a new NbtCompound nbt variable set to the player.getPersistentData()
        int thirstLevel = nbt.getInt("thirst_level");                                                              //Sets thirstLevel to the player's current thirst level
        syncThirst(thirstLevel, (ServerPlayerEntity) player);                                                           //Syncs the player's thirst level with the server

        if(thirstLevel + drinkUp >= 100) {                                                                              //if Thirst level is maxed out stay at max level
            thirstLevel = 100;
        }else{                                                                                                          //else add drinkUp to your thirstLevel
            thirstLevel += drinkUp;
        }
        nbt.putInt("thirst_level", thirstLevel);                                                                        //Updates the player's thirst level after drinking
        syncThirst(thirstLevel, (ServerPlayerEntity) player);                                                           //Syncs the player's thirst level with the server
        return thirstLevel;                                                                                             //returns thirstLevel as an Integer
    }

    public static int removeThirstLevel(IEntityDataSaver player, int drinkDown){                                        //Method to Decrease a player's thirst level by drinkDown amount
        NbtCompound nbt = player.getPersistentData();                                                                   //Makes a new NbtCompound nbt variable set to the player.getPersistentData()
        int thirstLevel = nbt.getInt("thirst_level");                                                              //Sets thirstLevel to the player's current thirst level
        syncThirst(thirstLevel, (ServerPlayerEntity) player);                                                           //Syncs the player's thirst level with the server
        if(thirstLevel - drinkDown <= 0) {                                                                              //if Thirst level is at minimum stay at minimum
            thirstLevel = 0;
        }else{                                                                                                          //else subtract drinkDown from thirstLevel
            thirstLevel -= drinkDown;
        }

        nbt.putInt("thirst_level", thirstLevel);                                                                        //Updates the player's thirst level after removing thirst
        syncThirst(thirstLevel, (ServerPlayerEntity) player);                                                           //Syncs the player's thirst level with the server
        return thirstLevel;                                                                                             //returns thirstLevel as an Integer
    }

    public static int getThirstLevel(IEntityDataSaver player){                                                          //Method to get thirstLevel as an integer
        NbtCompound nbt = player.getPersistentData();                                                                   //Makes a new NbtCompound nbt variable set to the player.getPersistentData()
        int thirstLevel = nbt.getInt("thirst_level");                                                              //Sets thirstLevel to the player's current thirst level
        syncThirst(thirstLevel, (ServerPlayerEntity) player);                                                           //Syncs the player's thirst level with the server
        return thirstLevel;                                                                                             //returns thirstLevel as an Integer
    }

    public static void syncThirst(int thirstLevel, ServerPlayerEntity player){                                          //Method to Sync thirstLevel between Client and Server
        PacketByteBuf buffer = PacketByteBufs.create();                                                                 //Creates a PacketBytBuffer to communicate between the two
        buffer.writeInt(thirstLevel);                                                                                   //buffer having thirstLevel written to it
        ServerPlayNetworking.send(player, ModMessages.CUSTOM_MESSAGE_THREE_ID, buffer);                                 //Server sending the player, packet and buffer
    }


}

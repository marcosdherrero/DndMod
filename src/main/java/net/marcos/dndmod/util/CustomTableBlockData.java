package net.marcos.dndmod.util;

import com.mojang.datafixers.types.Type;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.marcos.dndmod.networking.ModMessages;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.random.Random;

public class CustomTableBlockData {

    public static int dTwenty;
    public static int dTwelve;
    public static int dTen;
    public static int dEight;
    public static int dSix;
    public static int dFour;
    public static int dTwo;
    public static int dOneHundred;
    public static String whichDie;
    public static int [] tableDice;


    public static void rollDie(IEntityDataSaver block, String key){
        NbtCompound nbt = block.getPersistentData();
        whichDie = key;
        dTwenty = nbt.getInt("d_twenty");
        dTwelve = nbt.getInt("d_twelve");
        dTen = nbt.getInt("d_ten");
        dEight = nbt.getInt("d_eight");
        dSix = nbt.getInt("d_six");
        dFour = nbt.getInt("d_four");
        dTwo = nbt.getInt("d_two");
        dOneHundred = nbt.getInt("d_one_hundred");
        tableDice = nbt.getIntArray("table_dice");

        switch(whichDie){
            case "d_twenty":
                dTwenty = getRandomNumber(20);
                nbt.putInt("d_twenty", dTwenty);
                tableDice[1] = dTwenty;
                break;
            case "d_twelve":
                dTwelve = getRandomNumber(12);
                nbt.putInt("d_twelve", dTwelve);
                tableDice[2] = dTwelve;
                break;
            case "d_ten":
                dTen = getRandomNumber(10);
                nbt.putInt("d_ten", dTen);
                tableDice[3] = dTen;
                break;
            case "d_eight":
                dEight = getRandomNumber(8);
                nbt.putInt("d_eight", dEight);
                tableDice[4] = dEight;
                break;
            case "d_six":
                dSix = getRandomNumber(6);
                nbt.putInt("d_six", dSix);
                tableDice[5] = dSix;
                break;
            case "d_four":
                dFour = getRandomNumber(4);
                nbt.putInt("d_four", dFour);
                tableDice[6] = dFour;
                break;
            case "d_two":
                dTwo = getRandomNumber(2);
                nbt.putInt("d_two", dTwo);
                tableDice[7] = dTwo;
                break;
            case "d_one_hundred":
                dOneHundred = getRandomNumber(100);
                nbt.putInt("d_one_hundred", dOneHundred);
                tableDice[8] = dOneHundred;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + whichDie);
        }
    }

    public static int getTableDieNumber(IEntityDataSaver block, String key){
        NbtCompound nbt = block.getPersistentData();
        return nbt.getInt(key);
    }

    public static int [] getTableDiceNumbers(IEntityDataSaver block){
        NbtCompound nbt = block.getPersistentData();
        tableDice = nbt.getIntArray("table_dice");
        return tableDice;
    }

    public static void syncTableDiceNumbers(ServerPlayerEntity player, int[] tableDice){
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeIntArray(tableDice);

    }

    public static int getRandomNumber(int upperLimit){
        return Random.createLocal().nextInt(upperLimit)+1;
    }

}

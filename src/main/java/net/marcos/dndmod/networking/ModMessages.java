package net.marcos.dndmod.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.marcos.dndmod.DnDMod;
import net.marcos.dndmod.networking.packet.CustomMessageOneC2SPacket;
import net.marcos.dndmod.networking.packet.CustomMessageThreeS2CPacket;
import net.marcos.dndmod.networking.packet.CustomMessageTwoC2SPacket;
import net.minecraft.util.Identifier;

public class ModMessages {

    public static final Identifier CUSTOM_MESSAGE_ONE_ID =                                                              //Each Message needs its own Identifier
            new Identifier(DnDMod.MOD_ID,"message_one");

    public static final Identifier CUSTOM_MESSAGE_TWO_ID =
            new Identifier(DnDMod.MOD_ID,"message_two");

    public static final Identifier CUSTOM_MESSAGE_THREE_ID =
            new Identifier(DnDMod.MOD_ID,"message_three");

    public static  void registerC2SPackets(){                                                                           //Method to send Messages from Client to Server referenced in the Mod onInitialize Method
        ServerPlayNetworking.registerGlobalReceiver(                                                                    //Calls a boolean registerGlobalReceiver(Identifier channelName, PlayChannelHandler channelHandler) which returns ServerNetworkingImpl.PLAY.registerGlobalReceiver(channelName, channelHandler)
                CUSTOM_MESSAGE_ONE_ID,                                                                                  //Id  name
                CustomMessageOneC2SPacket::receive);                                                                    //Packet with channelHandler

        ServerPlayNetworking.registerGlobalReceiver(                                                                    //Calls a boolean registerGlobalReceiver(Identifier channelName, PlayChannelHandler channelHandler) which returns ServerNetworkingImpl.PLAY.registerGlobalReceiver(channelName, channelHandler)
                CUSTOM_MESSAGE_TWO_ID,                                                                                  //Id  name
                CustomMessageTwoC2SPacket::receive);                                                                    //Packet with channelHandler
    }

    public static  void registerS2CPackets(){                                                                           //Method to send Messages from Server to Client
        ClientPlayNetworking.registerGlobalReceiver(                                                                    //Calls a boolean registerGlobalReceiver(Identifier channelName, PlayChannelHandler channelHandler) which returns ServerNetworkingImpl.PLAY.registerGlobalReceiver(channelName, channelHandler)
                CUSTOM_MESSAGE_THREE_ID,                                                                                //ID name
                CustomMessageThreeS2CPacket::receive);                                                                  //Packet With channelHandler


    }
}

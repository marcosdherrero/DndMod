package net.marcos.dndmod.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;

import net.marcos.dndmod.networking.ModMessages;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_CUSTOM = "key.category.dndmod.custom";                                      //Creates a String for the name of the Category for your Custom Key Binds
    public static final String CUSTOM_KEY_ONE = "key.dndmod.custom_key_one";                                            //Creates a String for the name of the Custom Key Bind
    public static final String CUSTOM_KEY_TWO = "key.dndmod.custom_key_two";                                            //Creates a String for the name of the Custom Key Bind
    public static final String CUSTOM_KEY_THREE = "key.dndmod.custom_key_three";                                        //Creates a String for the name of the Custom Key Bind

    public static KeyBinding keyOne;                                                                                    //creates a new KeyBinding
    public static KeyBinding keyTwo;                                                                                    //creates a new KeyBinding
    public static KeyBinding keyThree;                                                                                  //creates a new KeyBinding

    public static void registerKeyInputs(){                                                                             //Method to register what happens when a Custom Key Bind is pressed
        ClientTickEvents.END_CLIENT_TICK.register(client -> {                                                           //Registers when a game tick is read
            if(keyOne.wasPressed()){                                                                                    //Everything in this if statement will happen when our Custom Key Bind One is pressed
                ClientPlayNetworking.send(
                        ModMessages.CUSTOM_MESSAGE_ONE_ID,
                        PacketByteBufs.create());
            }
            if(keyTwo.wasPressed()){                                                                                    //Everything in this if statement will happen when our Custom Key Bind One is pressed
                ClientPlayNetworking.send(
                        ModMessages.CUSTOM_MESSAGE_TWO_ID,
                        PacketByteBufs.create());
            }
            if(keyThree.wasPressed()){                                                                                  //Everything in this if statement will happen when our Custom Key Bind One is pressed

            }
        });
    }

    public static void register(){                                                                                      //Called in our Mod Client Initialize Method
        keyOne = KeyBindingHelper.registerKeyBinding(
                new KeyBinding(                                                                                         //KeyBinding(String translationKey, int code, String category)
                    CUSTOM_KEY_ONE,                                                                                     //name of the Custom Key
                    InputUtil.Type.KEYSYM,                                                                              //Key code
                    GLFW.GLFW_KEY_O,                                                                                    //Key selected at default
                    KEY_CATEGORY_CUSTOM                                                                                 //category that the key bind is listed under in the menu
        ));
        keyTwo = KeyBindingHelper.registerKeyBinding(
                new KeyBinding(                                                                                         //KeyBinding(String translationKey, int code, String category)
                    CUSTOM_KEY_TWO,                                                                                     //name of the Custom Key
                    InputUtil.Type.KEYSYM,                                                                              //Key code
                    GLFW.GLFW_KEY_I,                                                                                    //Key selected at default
                    KEY_CATEGORY_CUSTOM                                                                                 //category that the key bind is listed under in the menu
        ));
        keyThree = KeyBindingHelper.registerKeyBinding(
                new KeyBinding(                                                                                         //KeyBinding(String translationKey, int code, String category)
                    CUSTOM_KEY_THREE,                                                                                   //name of the Custom Key
                    InputUtil.Type.KEYSYM,                                                                              //Key code
                    GLFW.GLFW_KEY_U,                                                                                    //Key selected at default
                    KEY_CATEGORY_CUSTOM                                                                                 //category that the key bind is listed under in the menu
        ));

        registerKeyInputs();                                                                                            //registers our Key Bind inputs
    }
}

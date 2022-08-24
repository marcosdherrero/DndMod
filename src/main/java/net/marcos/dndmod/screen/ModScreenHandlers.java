package net.marcos.dndmod.screen;

import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {                                                                                        //Class to instantiate our Custom ScreenHandlers

    public static ScreenHandlerType<CustomTableBlockScreenHandler> CUSTOM_TABLE_BLOCK_SCREEN_HANDLER;                   //Creates a ScreenHandlerType<CustomTableBlockScreenHandler> called CUSTOM_TABLE_BLOCK_SCREEN_HANDLER

    public static void registerAllScreenHandlers(){                                                                     //Method where the ScreenHandlers are Registered
        CUSTOM_TABLE_BLOCK_SCREEN_HANDLER = new ScreenHandlerType<>(CustomTableBlockScreenHandler::new);                //Creates a new ScreenHandlerType of type CustomTableBlockScreenHandler

    }
}

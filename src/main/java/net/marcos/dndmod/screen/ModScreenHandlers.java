package net.marcos.dndmod.screen;

import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {
    public static ScreenHandlerType<CustomTableBlockScreenHandler> CUSTOM_TABLE_BLOCK_SCREEN_HANDLER;

    public static void registerAllScreenHandlers(){
        CUSTOM_TABLE_BLOCK_SCREEN_HANDLER = new ScreenHandlerType<>(CustomTableBlockScreenHandler::new);

    }
}

package net.marcos.dndmod.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.marcos.dndmod.DnDMod;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModScreenHandlers {                                                                                        //Class to instantiate our Custom ScreenHandlers

    public static ScreenHandlerType<CustomTableBlockScreenHandler> CUSTOM_TABLE_BLOCK_SCREEN_HANDLER =                  //Creates a ScreenHandlerType<CustomTableBlockScreenHandler> called CUSTOM_TABLE_BLOCK_SCREEN_HANDLER
            new ExtendedScreenHandlerType<>(CustomTableBlockScreenHandler::new);
    public static void registerAllScreenHandlers(){                                                                     //Method where the ScreenHandlers are Registered
        Registry.register(Registry.SCREEN_HANDLER,
                new Identifier(DnDMod.MOD_ID,"custom_table_energy"),
                CUSTOM_TABLE_BLOCK_SCREEN_HANDLER);
    }
}

package net.marcos.dndmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;

import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.marcos.dndmod.block.ModBlocks;

import net.marcos.dndmod.client.ThirstHudOverlay;
import net.marcos.dndmod.client.ThirstHudOverlayUpdate;
import net.marcos.dndmod.event.KeyInputHandler;
import net.marcos.dndmod.fluid.ModFluids;
import net.marcos.dndmod.networking.ModMessages;

import net.marcos.dndmod.screen.CustomTableBlockScreen;
import net.marcos.dndmod.screen.ModScreenHandlers;
import net.marcos.dndmod.util.IEntityDataSaver;
import net.marcos.dndmod.util.ThirstLevelData;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class DnDModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {                                                                                  //Overrides the On Initialized Client
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CUSTOM_CROP_BLOCK, RenderLayer.getCutout());                    //Creates a render Layer to cut out the Custom Crop Block's png

        KeyInputHandler.register();                                                                                     //register KeyInputHandler
        ModMessages.registerS2CPackets();																				//register ModMessages the Server communicating to the Client

        HudRenderCallback.EVENT.register(new ThirstHudOverlayUpdate());                                                 //register a new ThirstHudOverlay in HudRenderCallback

        FluidRenderHandlerRegistry.INSTANCE.register(
                ModFluids.STILL_CUSTOM_FLUID,
                ModFluids.FLOWING_CUSTOM_FLUID,
                new SimpleFluidRenderHandler(                                                                           //Selects the fluid's render image maps
                        new Identifier("minecraft:block/water_still"),
                        new Identifier("minecraft:block/water_flow"),
                        0xA1E038D0                                                                                      //Alpha Value: 0xA1 R:E0 G:38 B:D0
                ));

        BlockRenderLayerMap.INSTANCE.putFluids(                                                                         //Set the Fluids to transparent
                RenderLayer.getTranslucent(),
                ModFluids.STILL_CUSTOM_FLUID,
                ModFluids.FLOWING_CUSTOM_FLUID);

        HandledScreens.register(ModScreenHandlers.CUSTOM_TABLE_BLOCK_SCREEN_HANDLER, CustomTableBlockScreen::new);
    }
}

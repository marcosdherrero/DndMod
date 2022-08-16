package net.marcos.dndmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.marcos.dndmod.block.ModBlocks;

import net.marcos.dndmod.client.ThirstHudOverlay;
import net.marcos.dndmod.event.KeyInputHandler;
import net.marcos.dndmod.networking.ModMessages;

import net.minecraft.client.render.RenderLayer;

public class DnDModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {                                                                                  //Overrides the On Initialized Client
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CUSTOM_CROP_BLOCK, RenderLayer.getCutout());                    //Creates a render Layer to cut out the Custom Crop Block's png

        KeyInputHandler.register();                                                                                     //register KeyInputHandler
        ModMessages.registerS2CPackets();																				//register ModMessages the Server communicating to the Client

        HudRenderCallback.EVENT.register(new ThirstHudOverlay());                                                       //register a new ThirstHudOverlay in HudRenderCallback
    }
}

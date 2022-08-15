package net.marcos.dndmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;

import net.marcos.dndmod.block.ModBlocks;

import net.minecraft.client.render.RenderLayer;

public class DnDModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {                                                                                  //Overrides the On Initialized Client
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CUSTOM_CROP_BLOCK, RenderLayer.getCutout());                    //Creates a render Layer to cut out the Custom Crop Block's png
    }
}

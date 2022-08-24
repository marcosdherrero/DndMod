package net.marcos.dndmod.block.custom;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.marcos.dndmod.block.ModBlocks;
import net.marcos.dndmod.item.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;

public class CustomCropBlock extends CropBlock {                                                                        //implements ClientModInitializer {

    public static final IntProperty AGE =                                                                               //creates IntProperty called AGE
            IntProperty.of("age", 0,6);                                                              //name,min,max

    public CustomCropBlock(Settings settings) {                                                                         //Custom Crop Block Constructor
        super(settings);
    }
    @Override                                                                                                           //Override the getSeedItem method
    protected ItemConvertible getSeedsItem() {
        return ModItems.CUSTOM_SEEDS_ITEM;
    }
    @Override                                                                                                           //Override the getMaxAge method
    public int getMaxAge() {
        return 6;
    }
    @Override                                                                                                           //Override IntProperty getAgeProperty
    public IntProperty getAgeProperty() {
        return AGE;
    }
    @Override                                                                                                           //Override the appendProperties Method
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

}



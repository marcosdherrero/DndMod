package net.marcos.dndmod.fluid;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public abstract class CustomFluid extends FlowableFluid {

    @Override
    protected boolean isInfinite() {                                                                                    //Method to check if Custom Fluid will create a new Source Block
        return false;                                                                                                   //when placed with 1 block between one another if true
    }

    @Override
    protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {                             //Checks what the fluid will do when colliding with a block at a certain block state
        final BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos):null;
        Block.dropStacks(state, world, pos, blockEntity);
    }

    @Override
    protected int getFlowSpeed(WorldView world) {                                                                       //gets our Custom Fluid flow speed
        return 4;                                                                                                       //Minecraft's normal water is set to 4
    }

    @Override
    protected int getLevelDecreasePerBlock(WorldView world) {                                                           //Method to get the level the Custom Fluid will decrease per block
        return 1;                                                                                                       //Minecraft's water is set to 1
    }

    @Override
    public boolean matchesType(Fluid fluid) {
        return fluid == getStill() || fluid == getFlowing();
    }

    @Override
    public int getLevel(FluidState state) {                                                                             //Method to get the level of the Custom Fluid
        return 0;
    }

    @Override
    public int getTickRate(WorldView world) {                                                                           //Method to get the Tick Rate of the World that the Custom Fluid will act with
        return 5;                                                                                                       //Minecraft's water is set to 5
    }

    @Override
    protected float getBlastResistance() {                                                                              //Method to get the Blast Resistance of the Custom Fluid
        return 100f;                                                                                                  //Minecraft's water is set to 100.0f
    }

    @Override
    protected boolean canBeReplacedWith(FluidState state, BlockView world,                                              //Method to Check if Custom Fluid can be replaced by other blocks
                                        BlockPos pos, Fluid fluid, Direction direction) {
        return false;                                                                                                   //False will allow us to place any block in this pos
    }

    @Override
    public Fluid getStill() {
        return ModFluids.STILL_CUSTOM_FLUID;
    }

    @Override
    public Fluid getFlowing() {
        return ModFluids.FLOWING_CUSTOM_FLUID;
    }

    @Override
    public Item getBucketItem() {
        return ModFluids.CUSTOM_FLUID_BUCKET;
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return ModFluids.CUSTOM_FLUID_BLOCK.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(state));
    }

    @Override
    public boolean isStill(FluidState state) {
        return false;
    }

    public static class Flowing extends CustomFluid{
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getLevel(FluidState state) {
            return state.get(LEVEL);
        }

        @Override
        public boolean isStill(FluidState state) {
            return false;
        }
    }

    public static class Still extends CustomFluid{
        @Override
        public int getLevel(FluidState state) {
            return 8;
        }

        @Override
        public boolean isStill(FluidState state) {
            return true;
        }
    }

}

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

public abstract class CustomFluid extends FlowableFluid {                                                               //Custom Fluid Class

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
    public boolean matchesType(Fluid fluid) {                                                                           //Boolean Method to Check if the fluid types match
        return fluid == getStill() || fluid == getFlowing();                                                            //Returns if the fluid is both moving or still when checking
    }

    @Override
    public int getLevel(FluidState state) {                                                                             //Method to get the level of the Custom Fluid
        return 0;                                                                                                       //
    }

    @Override
    public int getTickRate(WorldView world) {                                                                           //Method to get the Tick Rate of the World that the Custom Fluid will act with
        return 5;                                                                                                       //Minecraft's water is set to 5
    }

    @Override
    protected float getBlastResistance() {                                                                              //Method to get the Blast Resistance of the Custom Fluid
        return 100f;                                                                                                    //Minecraft's water is set to 100.0f
    }

    @Override
    protected boolean canBeReplacedWith(FluidState state, BlockView world,                                              //Method to Check if Custom Fluid can be replaced by other blocks
                                        BlockPos pos, Fluid fluid, Direction direction) {
        return false;                                                                                                   //False will allow us to place any block in this pos
    }

    @Override
    public Fluid getStill() {                                                                                           //Method to get the fluid that is still
        return ModFluids.STILL_CUSTOM_FLUID;                                                                            //Returns our Custom Fluid
    }

    @Override
    public Fluid getFlowing() {                                                                                         //Method to get a fluid that is flowing
        return ModFluids.FLOWING_CUSTOM_FLUID;                                                                          //Returns our Custom Fluid
    }

    @Override
    public Item getBucketItem() {                                                                                       //Method to get the bucket item for this fluidBlock
        return ModFluids.CUSTOM_FLUID_BUCKET;                                                                           //Returns our Custom Fluid Bucket
    }

    @Override
    protected BlockState toBlockState(FluidState state) {                                                               //Method to input the defaultedBlockState of the fluid
        return ModFluids.CUSTOM_FLUID_BLOCK.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(state));     //This sets the default height range of 0-15
    }

    @Override
    public boolean isStill(FluidState state) {                                                                          //Boolean Method to check if the fluid is sill or not
        return false;                                                                                                   //This fluid is flowing
    }

    public static class Flowing extends CustomFluid{                                                                    //Flowing Class
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {                              //Method to append the properties of the Fluid state
            super.appendProperties(builder);                                                                            //Sends out for the builder
            builder.add(LEVEL);                                                                                         //adds the LEVEL to the fluidState
        }

        @Override
        public int getLevel(FluidState state) {                                                                         //int Method to get the level of the fluid
            return state.get(LEVEL);                                                                                    //Returns the LEVEL of the fluid at that pos
        }

        @Override
        public boolean isStill(FluidState state) {                                                                      //Boolean Method to check if the Fluid is still
            return false;                                                                                               //This fluid is flowing
        }
    }

    public static class Still extends CustomFluid{                                                                      //Still Class
        @Override
        public int getLevel(FluidState state) {                                                                         //Method to get the level of the fluid
            return 8;                                                                                                   //This fluid will flow out 8 blocks
        }

        @Override
        public boolean isStill(FluidState state) {                                                                      //Method to check if the fluid is still
            return true;                                                                                                //This fluid is not flowing
        }
    }

}

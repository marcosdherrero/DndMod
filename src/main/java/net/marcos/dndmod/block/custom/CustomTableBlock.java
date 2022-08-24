package net.marcos.dndmod.block.custom;

import net.marcos.dndmod.block.entity.CustomTableBlockEntity;
import net.marcos.dndmod.block.entity.ModBlockEntities;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

public class CustomTableBlock extends BlockWithEntity implements BlockEntityProvider {
    /*
    Creating Our Custom Block and its placement in the world
     */

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    private static VoxelShape SHAPE =
            Block.createCuboidShape(0, 0, 0   ,16,9,16);

    public CustomTableBlock(Settings settings) {                                                                        //Creates te CustomTableBlock with specific settings as a parameter
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {          //Method to outline the shape of the CustomTableBlock
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {                                                     //Method to get the front of the CustomTableBlock
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {                                                //Method to rotate the CustomTableBlock to the correct facing position
        return state.with(FACING, rotation.rotate((state.get(FACING))));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {                                                    //Method to mirror the CustomTableBlock to the correct facing position
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {                                  //Method to appendProperties
        builder.add(FACING);                                                                                            //adds FACING as a property
    }

    /*Block Entity*/

    @Override
    public BlockRenderType getRenderType(BlockState state) {                                                            //Method to get the Render type for the CustomTableBlock
        return BlockRenderType.MODEL;                                                                                   //Ensures that the game will render the entity as a block
    }

    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved){       //Method to convert the block into a BlockEntity and break open
        if(state.getBlock()!=newState.getBlock()){                                                                      //its contents when broken
            BlockEntity blockEntity = world.getBlockEntity(pos);                                                        //creates a BlockEntity Variable blockEntity referencing the BlockEntity at that position
            if(blockEntity instanceof CustomTableBlockEntity){                                                          //Checks if the blockEntity is one of our searched for type
                ItemScatterer.spawn(world,pos, (CustomTableBlockEntity)blockEntity);                                    //Scatters the contents when broken
                world.updateComparators(pos,this);                                                               //Updates the comparators at that position for this CustomTableBlock block
            }
            super.onStateReplaced(state,world,pos,newState,moved);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos,                                              //Checks if the player has used on the blockEntity
                              PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient){                                                                                            //Checks if the player is on the client
            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
            if(screenHandlerFactory != null){                                                                           //If the ScreenHandler is not open
                player.openHandledScreen(screenHandlerFactory);                                                         //Open the Screen Handler
                }
        }
        return ActionResult.SUCCESS;                                                                                    //Declare Successful Action to the server
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state){                                               //Method to make our Block into a custom Block Entity
        return new CustomTableBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T>                                                                 //Method to getTicker and check the type for the CustomTableBlock
        getTicker(World world, BlockState state, BlockEntityType<T> type) {
            return checkType(type, ModBlockEntities.CUSTOM_TABLE_BLOCK_ENTITY, CustomTableBlockEntity::tick);
    }
}


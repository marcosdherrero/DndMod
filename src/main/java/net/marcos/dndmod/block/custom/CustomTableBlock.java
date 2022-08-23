package net.marcos.dndmod.block.custom;

import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents;
import net.marcos.dndmod.block.entity.CustomTableBlockEntity;
import net.marcos.dndmod.block.ModBlocks;
import net.marcos.dndmod.block.entity.ModBlockEntities;
import net.marcos.dndmod.item.ModItems;
//import net.marcos.dndmod.util.CustomTableBlockData;
import net.marcos.dndmod.util.IEntityDataSaver;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
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

    public CustomTableBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate((state.get(FACING))));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    /*Block Entity*/

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;                                                                                   //Ensures that the game will render the entity as a block
    }

    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved){
        if(state.getBlock()!=newState.getBlock()){
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if(blockEntity instanceof CustomTableBlockEntity){
                ItemScatterer.spawn(world,pos, (CustomTableBlockEntity)blockEntity);
                world.updateComparators(pos,this);
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
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.CUSTOM_TABLE_BLOCK_ENTITY, CustomTableBlockEntity::tick);
    }
}


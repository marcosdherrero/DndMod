package net.marcos.dndmod.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CustomLampBlock extends Block {

    public static final BooleanProperty LIT = BooleanProperty.of("lit");                                         //Creates the BooleanProperty of LIT

    public CustomLampBlock(Settings settings) {                                                                         //Creates the Super Settings for the CustomLampBlock
        super(settings);
    }

    @Override                                                                                                           //Overrides the onUse command
    public ActionResult onUse(BlockState state, World world, BlockPos pos,                                              //Creates the right click toggle functionality
                              PlayerEntity player, Hand hand, BlockHitResult hit) {
                if(!world.isClient() && hand == Hand.MAIN_HAND){                                                        //if the world is not on client and the main hand is the only one clicking
                        world.setBlockState(pos, state.cycle(LIT));                                                     //Cycles the Block State of LIT between true and false
            player.getItemCooldownManager().set(player.getMainHandStack().getItem(), 1);                                //Set an Item Cool-down Manager
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    //Creates the Tooltip for the Custom Lamp Block
    @Override                                                                                                           //Overrides the appendTooltip command
    public void appendTooltip(ItemStack stack, @Nullable BlockView world,
                              List<Text> tooltip, TooltipContext options) {
        if(Screen.hasShiftDown()){                                                                                      //If the player has shift down
            tooltip.add(Text.literal("Right Click To Cycle On and Off These Lamps!")                              //Show tooltip
                    .formatted(Formatting.AQUA));
        }else{                                                                                                          //else
            tooltip.add(Text.literal("Press Shift for more Info!")                                                //Show how to show tooltip
                    .formatted(Formatting.YELLOW));
        }
        super.appendTooltip(stack, world, tooltip, options);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {                                  //Overrides the appendProperties to add LIT to the Properties
        builder.add(LIT);                                                                                               //adds LIT to the appendProperties builder
        super.appendProperties(builder);                                                                                //builds the appendProperties
    }
}

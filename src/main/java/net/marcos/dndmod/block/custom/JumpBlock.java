package net.marcos.dndmod.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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

public class JumpBlock extends Block {                                                                                  //Custom Block Class JumpBlock extends the Block Method

    @Override                                                                                                           //Overrides the onUse Command
    public ActionResult onUse(BlockState state, World world, BlockPos pos,                                              //Action Result of onUse
                              PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient && hand == Hand.MAIN_HAND){                                                                  //Checks if player is right-clicking on the Client and with just the main hand
            player.sendMessage(Text.literal("Step on this for Jump Boost!"));                                    //Sends a message to the player to step on this block for jump boost
            player.getItemCooldownManager().set(player.getMainHandStack().getItem(), 5);                                //Sets a right-click cool-down, so you can't place blocks on the jump block
        }
        return super.onUse(state, world, pos, player, hand, hit);                                                       //return the super.onUse(state, world, pos, player, hand, hit)
    }

    @Override                                                                                                           //Overrides the appendToolTip command
    public void appendTooltip(ItemStack stack, @Nullable BlockView world,
                              List<Text> tooltip, TooltipContext options) {
        if(Screen.hasShiftDown()){                                                                                      //If the player has shift down
            tooltip.add(Text.literal("Step on this block for Jump Boost!").formatted(Formatting.AQUA));          //Show Tooltip
        }else{                                                                                                          //else
            tooltip.add(Text.literal("Press Shift for more Info!").formatted(Formatting.YELLOW));                //Show press shift for tooltip
        }
        super.appendTooltip(stack, world, tooltip, options);
    }

    @Override                                                                                                           //Override the onSteppedOn command
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if(entity instanceof LivingEntity livingEntity){                                                                //if the entity that stepped on the block is living
            livingEntity.addStatusEffect(                                                                               //add status effect
                    new StatusEffectInstance(                                                                           //new Status Effect Instance
                            StatusEffects.JUMP_BOOST,                                                                   //Status Effect name
                            200,                                                                                        //duration t/20 = s
                            10));                                                                                       //amplifier
        }
        super.onSteppedOn(world, pos, state, entity);
    }

    public JumpBlock(Settings settings) {                                                                               //Constructor Method
        super(settings);
    }
}

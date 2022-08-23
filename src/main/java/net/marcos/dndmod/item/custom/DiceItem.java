package net.marcos.dndmod.item.custom;

import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DiceItem extends Item {                                                                                    //Custom Item Class DiceItem extends the Item Class

    private int numberRolled;
    private int sides;
    private static LivingEntity player;
    public DiceItem(Settings settings, int numberOfSides) {                                                             //DiceItem Constructor
        super(settings);
        this.sides = numberOfSides;
        this.numberRolled = 0;
    }

    @Override                                                                                                           //Override the use command for when a player right clicks use(World world, PlayerEntity user, Hand hand)
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        this.player = user;
        if(!world.isClient && hand == Hand.MAIN_HAND){                                                                                            //if the world is not on the client
            if(user.isSneaking()) {
                this.numberRolled = getRandomNumber(this.sides);                                                                 //Assign a random number to the item's number rolled variable
                user.sendMessage(Text.literal("You rolled a: " + this.numberRolled),true);                        //Sends a message to the player to see what they rolled
                user.getItemCooldownManager().set(this, 10);                                                                //Sets a .5 sec cool-down
            }else{
                user.sendMessage(Text.literal("Your last roll was a: " +this.numberRolled +
                        " using a "+this.sides +" sided die"), true);
                user.getItemCooldownManager().set(this, 10);
            }
        }else {
            return super.use(world, user, hand);
        }
        return null;
    }

    @Override                                                                                                           //Overrides the appendTooltip command
    public void appendTooltip(ItemStack stack, @Nullable World world,
                              List<Text> tooltip, TooltipContext context) {
        NbtCompound nbt = new NbtCompound();
        if(Screen.hasShiftDown()){                                                                                      //if the player has shift down
            tooltip.add(Text.literal("Your most recent roll was: " + nbt.getInt("roll")).formatted(Formatting.AQUA));                  //show Tooltipe
        }else{                                                                                                          //else
            tooltip.add(Text.literal( "Press Shift for more Info!").formatted(Formatting.YELLOW));                //show how to show Tooltip
        }
    }

    private int getRandomNumber(int upperLimit){                                                                        //Method to get a random number
        return (Random.createLocal().nextInt(upperLimit))+1;                                                            //Minecraft's Random number generator bound to 20 (0-19) +1 for (1-20)
    }

}

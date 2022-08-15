package net.marcos.dndmod.item.custom;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DiceItem extends Item {                                                                                    //Custom Item Class DiceItem extends the Item Class

    public DiceItem(Settings settings) {                                                                                //DiceItem Constructor
        super(settings);
    }

    @Override                                                                                                           //Override the use command for when a player right clicks use(World world, PlayerEntity user, Hand hand)
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient){                                                                                            //if the world is not on the client
            outputRandomNumber(user);                                                                                   //Get a random number
            user.getItemCooldownManager().set(this, 20);
        }
        return super.use(world, user, hand);
    }

    @Override                                                                                                           //Overrides the appendTooltip command
    public void appendTooltip(ItemStack stack, @Nullable World world,
                              List<Text> tooltip, TooltipContext context) {
        if(Screen.hasShiftDown()){                                                                                      //if the player has shift down
            tooltip.add(Text.literal("Right Click to Roll a D20!").formatted(Formatting.AQUA));                  //show Tooltip
        }else{                                                                                                          //else
            tooltip.add(Text.literal("Press Shift for more Info!").formatted(Formatting.YELLOW));                //show how to show Tooltip
        }
    }

    private void outputRandomNumber(PlayerEntity player){                                                               //Method to output a random number
        player.sendMessage(Text.literal("You Rolled a "+ getRandomNumber() + "!"));                              //sends a Literal Message of a random number
    }

    private int getRandomNumber(){                                                                                      //Method to get a random number
        return (Random.createLocal().nextInt(20))+1;                                                            //Minecraft's Random number generator bound to 20 (0-19) +1 for (1-20)
    }
}

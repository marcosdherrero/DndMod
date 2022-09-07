package net.marcos.dndmod.item.custom;

import net.marcos.dndmod.item.ModItemGroup;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DiceItem extends Item {                                                                                    //Custom Item Class DiceItem extends the Item Class

    private int numberRolled;                                                                                           //Creates an int variable numberRolled
    public int sides;                                                                                                   //Creates an int variable call sides


    public DiceItem(Settings settings, int numberOfSides) {                                                             //DiceItem Constructor
        super(settings);                                                                                                //Sends for and Item to get made
        this.sides = numberOfSides;                                                                                     //Sets our custom variables for this type of item
        this.numberRolled = 0;

    }

    @Override                                                                                                           //Override the use command for when a player right clicks use(World world, PlayerEntity user, Hand hand)
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        if(!world.isClient && hand == Hand.MAIN_HAND){                                                                  //Checks that world on the server
            if(user.isSneaking()) {                                                                                     //Checks that the player is sneaking
                this.numberRolled = getRandomNumber(this.sides);                                                        //Assign a random number to the item's number rolled variable
                user.sendMessage(Text.literal("You rolled a: " + this.numberRolled),true);               //Sends a message to the player to see what they rolled
                user.getItemCooldownManager().set(this, 10);                                                            //Sets a .5 sec cool-down
            }else{
                user.sendMessage(Text.literal("Your last roll was a: " +this.numberRolled +                      //Sends a message to teh player of their last roll with that type of di
                        " using a "+this.sides +" sided die"), true);
                user.getItemCooldownManager().set(this, 10);                                                            //Sets a .5 second cool-down
            }
        }else {
            return super.use(world, user, hand);                                                                        //Sends out to use
        }
        return null;
    }

    @Override                                                                                                           //Overrides the appendTooltip command
    public void appendTooltip(ItemStack stack, @Nullable World world,                                                   //Method for the tooltip for the dice
                              List<Text> tooltip, TooltipContext context) {
        NbtCompound nbt = new NbtCompound();                                                                            //Creates a new nbt Variable
        if(Screen.hasShiftDown()){                                                                                      //checks that the player has shift down
            tooltip.add(Text.literal("Your most recent roll was: " + nbt.getInt("roll"))                    //show Tooltips
                    .formatted(Formatting.AQUA));
        }else{                                                                                                          //else
            tooltip.add(Text.literal( "Press Shift for more Info!").formatted(Formatting.YELLOW));                //show how to show Tooltip
        }
    }

    private int getRandomNumber(int upperLimit){                                                                        //Method to get a random number
        return (Random.createLocal().nextInt(upperLimit))+1;                                                            //Minecraft's Random number generator bound to 20 (0-19) +1 for (1-20)
    }

    public static boolean isADiceItem(Item item) {                                                                      //Boolean Method to check if an Item is an Item
        return item.getGroup() == ModItemGroup.DND_MOD_ITEMS;

    }
}

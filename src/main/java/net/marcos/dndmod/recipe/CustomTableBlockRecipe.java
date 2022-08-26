package net.marcos.dndmod.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class CustomTableBlockRecipe implements Recipe<SimpleInventory> {

   private final Identifier id;
   private final ItemStack output;
   private final DefaultedList<Ingredient> recipeItems;

    public CustomTableBlockRecipe(Identifier id, ItemStack output, DefaultedList<Ingredient> recipeItem) {              //Constructor for the CustomTableBlockRecipe
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItem;
    }


    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        if(world.isClient()) {
            return false;
        }


        return recipeItems.get(0).test(inventory.getStack(1));                                                     //Tests the slot of the recipe ingredient slot
    }

    @Override
    public ItemStack craft(SimpleInventory inventory) {
        return output;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput() {
        return output.copy();
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<CustomTableBlockRecipe>{                                             //Class Type which implements the RecipeType of CustomTableBlockRecipe
        private Type() { }                                                                                              //Creates an empty private Type List
        public static final Type INSTANCE = new Type();                                                                 //Creates a new INSTANCE of Type
        public static final String ID = "custom_table_block";                                                           //Creates a String ID for the CustomTableBlockRecipe Type
    }

    public static class Serializer implements RecipeSerializer<CustomTableBlockRecipe>{                                 //Class Serializer implements RecipeSerializer of CustomTableBlockRecipe
        public static final Serializer INSTANCE = new Serializer();                                                     //Creates a new INSTANCE of Serializer
        public static final String ID = "custom_table_block";                                                           //Creates a String ID for the CustomTableBlockRecipe Serializer

        @Override
        public CustomTableBlockRecipe read(Identifier id, JsonObject json) {                                            //Reads the json File

            ItemStack output =                                                                                          //Creates a new ItemStack called output for the crafted items
                   ShapedRecipe.outputFromJson(                                                                         //Receives our output from the Json file
                           JsonHelper.getObject(json, "output"));                                              //Sets this element name to be "output"

            JsonArray ingredients =                                                                                     //Creates a new JsonArray called ingredients for the recipe/ingredient items
                    JsonHelper.getArray(json, "ingredients");                                                  //Sets the ingredients to the array from the json file

            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(1, Ingredient.EMPTY);

            for(int i = 0; i< inputs.size(); i++){
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }
            return new CustomTableBlockRecipe(id, output, inputs);
        }

        @Override
        public CustomTableBlockRecipe read(Identifier id, PacketByteBuf buf) {                                          //Reads the networking information
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);


            for(int i = 0; i< inputs.size(); i++){
                inputs.set(i, Ingredient.fromPacket(buf));
            }

            ItemStack output = buf.readItemStack();
            return new CustomTableBlockRecipe(id, output, inputs);
        }

        @Override
        public void write(PacketByteBuf buf, CustomTableBlockRecipe recipe) {                                           //Writes the networking information
            buf.writeInt(recipe.getIngredients().size());
            for(Ingredient ing : recipe.getIngredients()){
                ing.write(buf);
            }
            buf.writeItemStack(recipe.getOutput());
        }
    }

}

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

   private final Identifier id;                                                                                         //Creates an ID for the Custom Recipe
   private final ItemStack output;                                                                                      //Creates the Output from the Custom Recipe
   private final DefaultedList<Ingredient> recipeItems;                                                                 //Creates the List of Ingredients for the recipe
    public CustomTableBlockRecipe(Identifier id, ItemStack output, DefaultedList<Ingredient> recipeItem) {              //Constructor for the CustomTableBlockRecipe
        this.id = id;                                                                                                   //Sets the Custom Recipe ID
        this.output = output;                                                                                           //Sets the Custom Recipe Output || Crafted Items
        this.recipeItems = recipeItem;                                                                                  //Sets the Custom Recipe recipeItems || Ingredient Items
    }


    @Override
    public boolean matches(SimpleInventory inventory, World world) {                                                    //Boolean Method to check the if the item in the ingredient slots are correct
        if(world.isClient()) {                                                                                          //Checks if the game is on the client
            return false;                                                                                               //return false
        }
        return recipeItems.get(0).test(inventory.getStack(1));                                                     //Tests the slot of the recipe ingredient slot
    }

    @Override
    public ItemStack craft(SimpleInventory inventory) {                                                                 //Method to Craft the Recipe Item
        return output;                                                                                                  //Return of the Crafted Item
    }

    @Override
    public boolean fits(int width, int height) {                                                                        //Returns the ingredients accepted as inputs for this recipe.
        return true;                                                                                                    //Used by the recipe book when displaying a ghost form of this recipe on the crafting grid as well as for previewing the possible inputs in the book itself.
    }

    @Override
    public ItemStack getOutput() {                                                                                      //Method to get the output of the Recipe
        return output.copy();                                                                                           //Returns a new ItemStack Item that will be placed in the crafted inventory slot
    }

    @Override
    public Identifier getId() {                                                                                         //Method to get the ID of the Recipe
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {                                                                        //The recipe serializer controls the serialization and deserialization of recipe content.
        return Serializer.INSTANCE;
    }

    @Override
        public RecipeType<?> getType() {                                                                                //The recipe type allows matching recipes more efficiently by only checking recipes under a given type.
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

            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(1, Ingredient.EMPTY);                          //Determines how many items will be taken to craft the final item

            for(int i = 0; i< inputs.size(); i++){
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));                                                 //Grabs the ingredient items from the JSON files to match
            }
            return new CustomTableBlockRecipe(id, output, inputs);                                                      //Sets the inputs
        }

        @Override
        public CustomTableBlockRecipe read(Identifier id, PacketByteBuf buf) {                                          //Reads the networking information
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);                   //Size of List


            for(int i = 0; i< inputs.size(); i++){                                                                      //Iterates through the list of ingredients
                inputs.set(i, Ingredient.fromPacket(buf));                                                              //Grabs the next ingredient
            }

            ItemStack output = buf.readItemStack();                                                                     //Creates a new buf reader of the output
            return new CustomTableBlockRecipe(id, output, inputs);                                                      //Returns a new CustomTableBlockRecipe(id, output, input);
        }

        @Override
        public void write(PacketByteBuf buf, CustomTableBlockRecipe recipe) {                                           //Writes the networking information
            buf.writeInt(recipe.getIngredients().size());                                                               //Writes the size of the recipe list
            for(Ingredient ing : recipe.getIngredients()){
                ing.write(buf);                                                                                         //Writes each item from the list
            }
            buf.writeItemStack(recipe.getOutput());                                                                     //Writes the new item to the buffer
        }
    }

}

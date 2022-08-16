package net.marcos.dndmod.client;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

import net.marcos.dndmod.DnDMod;
import net.marcos.dndmod.util.IEntityDataSaver;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;

import net.minecraft.util.Identifier;

public class ThirstHudOverlay implements HudRenderCallback {

   public static final Identifier FILLED_THIRST_BOTTLE =                                                                //Creates a new Identifier for the FILLED_THIRST_BOTTLE texture
           new Identifier(DnDMod.MOD_ID, "textures/thirst/filled_bottle.png");
   public static final Identifier EMPTY_THIRST_BOTTLE =                                                                 //Creates a new Identifier for the EMPTY_THIRST_BOTTLE texture
           new Identifier(DnDMod.MOD_ID, "textures/thirst/empty_bottle.png");


    @Override                                                                                                           //Overrides the onHudRender Method
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        int x = 0;                                                                                                      //x position
        int y = 0;                                                                                                      //y position
        int playerThirstLevel = ((IEntityDataSaver) MinecraftClient.getInstance().player)                               //Creates an Int Variable player Thirst Level
                .getPersistentData().getInt("thirst_level");
        MinecraftClient client = MinecraftClient.getInstance();                                                         //Gets the Instance of the MinecraftClient that is running for the player
        if(client != null){                                                                                             //Ensures that the Client Screen is not null
            int width = client.getWindow().getScaledWidth();                                                            //Sets width to Window Scaled Width
            int height = client.getWindow().getScaledHeight();                                                          //Sets height to Window Scaled Height

            x = width/2;                                                                                                //sets x to the middle of the screen
            y = height;                                                                                                 //sets y to the height of the screen
        }

        RenderSystem.setShader(GameRenderer::getPositionTexShader);                                                     //Sets the PositionTexShader to
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);

       for(int i = 1; i<=10; i++){                                                                                      //Prints out 10 Water Bottles
           if(playerThirstLevel>(i*10)){ //WaterBottle is full Draw it at i                                            //Selects if bottle is filled if thirst is greater than or equal to its position
               RenderSystem.setShaderTexture(0,FILLED_THIRST_BOTTLE);                                            //Selects FILLED_THIRST_BOTTLE texture
               DrawableHelper.drawTexture(matrixStack,x-94+(i*7),y-54,                                            //Draws Texture
                       0,0,12,12,12,12);
           }else if(playerThirstLevel<=(i*10)){//WaterBottle is empty Draw it at i                                       //Selects if bottle is empty if thirst is less than it's position
               RenderSystem.setShaderTexture(0,EMPTY_THIRST_BOTTLE);
               DrawableHelper.drawTexture(matrixStack,x-94+(i*7),y-54,
                       0,0,12,12,12,12);
           }
       }
    }
}

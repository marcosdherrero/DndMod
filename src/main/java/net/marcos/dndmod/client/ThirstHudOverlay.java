package net.marcos.dndmod.client;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

import net.marcos.dndmod.DnDMod;
import net.marcos.dndmod.util.IEntityDataSaver;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
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
        int u = 0;
        int v = 0;

        MinecraftClient client = MinecraftClient.getInstance();                                                         //Gets the Instance of the MinecraftClient that is running for the player
        if(client != null) {                                                                                             //Ensures that the Client Screen is not null
            int width = client.getWindow().getScaledWidth();                                                            //Sets width to Window Scaled Width
            int height = client.getWindow().getScaledHeight();                                                          //Sets height to Window Scaled Height

            x = width / 2;                                                                                                //sets x to the middle of the screen
            y = height;                                                                                                 //sets y to the height of the screen


            RenderSystem.setShader(GameRenderer::getPositionTexShader);                                                     //Sets the PositionTexShader to
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

            int playerThirstLevel = ((IEntityDataSaver) MinecraftClient.getInstance().player)                               //Creates an Int Variable player Thirst Level
                    .getPersistentData().getInt("thirst_level");
            double playerThirstLevelDivTen = playerThirstLevel / 10;
            int playerThirstLevelTensPlace = (int) playerThirstLevelDivTen;
            double playerThirstLevelRemTen = playerThirstLevel % 10;
            int playerThirstLevelOnesPlace = (int) playerThirstLevelRemTen;

            for (int i = 0; i < 10; i++) {                                                                              //Prints out 10 Water Bottles
                x = width/2 - 94 +(i*9);
                y = height - 30;
                int textureSquareWidth = 12;
                int textureSquareHeight = 12;
                int regionWidth = 12;
                int regionHeight = 12;
                int textureWidth = 12;
                int textureHeight = 12;
                int mapRange = mapRange(9,0,textureHeight,0, playerThirstLevelRemTen);
                int reversedMap = mapRange(textureHeight,0,0,textureHeight*2,mapRange);

                if (playerThirstLevelTensPlace > (i)) { //WaterBottle is full Draw it at i                              //Selects if bottle is filled if thirst is greater than or equal to its position
                    RenderSystem.setShaderTexture(0, FILLED_THIRST_BOTTLE);                                      //Selects FILLED_THIRST_BOTTLE texture
                    DrawableHelper.drawTexture(
                            matrixStack,
                            x , y , u, v,
                            textureSquareWidth,
                            textureSquareHeight,
                            textureWidth,
                            textureHeight);
                }else if(playerThirstLevelTensPlace == i) {

                    DrawableHelper.enableScissor(x,y,x+textureWidth,y+reversedMap);
                    RenderSystem.setShaderTexture(0, EMPTY_THIRST_BOTTLE);                                       //Selects EMPTY_THIRST_BOTTLE texture
                    DrawableHelper.drawTexture(matrixStack, x , y ,u, v, textureSquareWidth, textureSquareHeight, textureWidth, textureHeight);
                    DrawableHelper.enableScissor(x,y+reversedMap,x+textureWidth,y+textureHeight);
                    RenderSystem.setShaderTexture(0, FILLED_THIRST_BOTTLE);                                      //Selects FILLED_THIRST_BOTTLE texture
                    DrawableHelper.drawTexture(matrixStack, x , y ,u, v, textureSquareWidth, textureSquareHeight, textureWidth, textureHeight);
                    RenderSystem.disableScissor();

                }else {
                    RenderSystem.setShaderTexture(0, EMPTY_THIRST_BOTTLE);                                       //Selects EMPTY_THIRST_BOTTLE texture
                    DrawableHelper.drawTexture(matrixStack, x , y,u, v, textureSquareWidth, textureSquareHeight, textureWidth, textureHeight);
                }
            }
        }
    }

    //mapRange(0,9,1,textureHeight, playerThirstLevelRemTen)
    public static int mapRange(double a1, double a2, double b1, double b2, double s){
        return (int) (b1 + ((s - a1)*(b2 - b1))/(a2 - a1));
    }
}

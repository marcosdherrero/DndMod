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

public class ThirstHudOverlayUpdate implements HudRenderCallback {

    public final Identifier FILLED_BOTTLE =
            new Identifier(DnDMod.MOD_ID, "textures/thirst/filled_bottle.png");
    public final Identifier EMPTY_BOTTLE =
            new Identifier(DnDMod.MOD_ID, "textures/thirst/empty_bottle.png");

    public int x = 0;                                                                                                   //Variable for the Starting x Drawn Position
    public int y = 0;                                                                                                   //Variable for the Starting y Drawn Position
    public int u = 0;                                                                                                   //Variable for the Starting u Drawn Position (Where the drawing will start)
    public int v = 0;                                                                                                   //Variable for the Starting v Drawn Position (Where the drawing will start)
    public int maxBottleHeight = 12;                                                                                    //Variable for the Max Bottle Height
    public int maxBottleWidth = 12;                                                                                     //Variable for the Max Bottle Width
    public int bottleHeight;                                                                                            //Variable for the current Bottle Height
    public int thirstLevel;                                                                                             //Variable for the current Thirst Level
    public int TensPlace;                                                                                               //Variable for the current Tens Place
    public int onesPlace;                                                                                               //Variable for the current Ones Place


    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {

        int width = MinecraftClient.getInstance().getWindow().getScaledWidth();                                         //Variable set to the width of the Client Screen
        int height = MinecraftClient.getInstance().getWindow().getScaledHeight();                                       //Variable set to the height of the Client Screen

        thirstLevel = ((IEntityDataSaver) MinecraftClient.getInstance()                                                 //Set's the thirstLevel variable to the player's thirst level
                .player).getPersistentData().getInt("thirst_level");
        TensPlace = thirstLevel/10;
        bottleHeight = onesPlace = thirstLevel%10;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);                                                     //Sets the PositionTexShader to Draw Texture Shaders
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);                                    //Sets Shader Color to 1.0F
        MinecraftClient client = MinecraftClient.getInstance();                                                         //Creates a MinecraftClient.getInstance() variable called client

        if (client != null) {                                                                                           //Checks if the client is live
            for (int i = 0; i < 10; i++) {                                                                              //Loops through the ten bottles for the thirst Levels
                x = width / 2 - 94 + (i * 9);                                                                           //Sets the x dimension for the thirst bottle
                y = height - 40;                                                                                        //Sets the y position for the thirst bottle to br drawn at
                v = 0;                                                                                                  //Sets the v to be drawn
                RenderSystem.setShaderTexture(0, EMPTY_BOTTLE);                                                 //Sets the texture to the EMPTY_BOTTLE texture
                DrawableHelper.drawTexture(matrixStack, x, y, u, v,                                                     //Draws the Empty Bottle Texture
                        maxBottleWidth, maxBottleHeight, maxBottleWidth, maxBottleHeight);
                if (i < TensPlace) {                                                                                    //Checks that the bottle texture is filled
                    RenderSystem.setShaderTexture(0, FILLED_BOTTLE);                                            //Sets the texture to the FILLED_BOTTLE texture
                    DrawableHelper.drawTexture(matrixStack, x, y, u, v,                                                 //Draws the Full Bottle Texture Over the Drawn Empty Bottle
                            maxBottleWidth, maxBottleHeight, maxBottleWidth, maxBottleHeight);
                } else if(i == TensPlace){                                                                              //Checks if the Bottle is partially full
                    v = maxBottleHeight-onesPlace;                                                                      //Sets the v
                    y += v;                                                                                             //Sets the y Position
                    bottleHeight = onesPlace;                                                                           //Sets the BottleHeight
                    RenderSystem.setShaderTexture(0, FILLED_BOTTLE);
                    DrawableHelper.drawTexture(
                            matrixStack,                                                                                //Picks the Matrix Stack from the selected texture
                            x,                                                                                          //X position that the texture will be drawn in
                            y,                                                                                          //Y position that the texture will be drawn in
                            u,                                                                                          //X position that the texture will begin to be cut from the original texture
                            v,                                                                                          //Y position that the texture will begin to be cut from the original texture
                            maxBottleWidth,                                                                             //Texture Width To be drawn from the U value
                            bottleHeight,                                                                               //Texture Height To be drawn from V value
                            maxBottleWidth,                                                                             //Texture Width In total
                            maxBottleHeight);                                                                           //Texture Height In Total
                }
            }
        }
    }
    public int mapRangeResultOfsInRangeOfaTob(double a1, double a2, double b1, double b2, double s){
                return (int) (b1 + ((s - a1)*(b2 - b1))/(a2 - a1));
    }
}

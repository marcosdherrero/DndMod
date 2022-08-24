package net.marcos.dndmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.marcos.dndmod.DnDMod;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CustomTableBlockScreen extends HandledScreen<CustomTableBlockScreenHandler> {                              //Class to create a CustomTableBlockScreen (What we see visually)

    private static final Identifier GUI_TEXTURE =                                                                       //Creates a new Identifier called GUI_TEXTURE
            new Identifier(DnDMod.MOD_ID, "textures/gui/custom_table_block_gui.png");                             //This is given a path to an image for the GUI of the CustomTableBlockScreen

    public CustomTableBlockScreen(CustomTableBlockScreenHandler handler, PlayerInventory inventory, Text title) {       //Constructor for the CustomTableBlockScreen
        super(handler, inventory, title);
    }

    @Override
    protected void init() {                                                                                             //Checks if the texture is within the screen dimensions
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title))/2;                                                    //Sets the titleX variable
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {                          //Method to tell the game how to render the GUI
        RenderSystem.setShader(GameRenderer::getPositionTexShader);                                                     //Sets the
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);                                       //Sets a color Shader all set to 1 for a normal look
        RenderSystem.setShaderTexture(0,GUI_TEXTURE);                                                           //Sets which texture is being used
        int x = (width-backgroundWidth)/2;
        int y = (height-backgroundHeight)/2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);                                    //Draws the GUI_TEXTURE

        renderProgressArrow(matrices, x, y);                                                                            //RenderProgressArrow
    }

    private void renderProgressArrow(MatrixStack matrices, int x, int y) {                                              //Method to draw the progress arrow
        if(handler.isCrafting()){                                                                                       //Check if the ScreenHandler is crafting
            drawTexture(                                                                                                //Draw the Progress Arrow
                    matrices,                                                                                           //The matrices of the texture
                    x+105,                                                                                              //Drawn X Position
                    y+33,                                                                                               //Drawn Y Position
                    176,                                                                                                //U Position the x position that the texture will start to draw from
                    0,                                                                                                  //V Position the y position that the texture will start to draw from
                    8,                                                                                                  //Width that you are drawing from that starting U
                    handler.getScaledProgress());                                                                       //Height that you are drawing from that starting U
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {                                     //Method to render the classes objects
        renderBackground(matrices);                                                                                     //render the background
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);                                                                 //renders the mouse to be drawn over the tooltip
    }
}

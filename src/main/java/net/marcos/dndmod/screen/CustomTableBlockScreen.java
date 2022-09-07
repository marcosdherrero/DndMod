package net.marcos.dndmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.marcos.dndmod.DnDMod;
import net.marcos.dndmod.screen.renderer.EnergyInfoArea;
import net.marcos.dndmod.screen.renderer.FluidStackRenderer;
import net.marcos.dndmod.util.FluidStack;
import net.marcos.dndmod.util.MouseUtil;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class CustomTableBlockScreen extends HandledScreen<CustomTableBlockScreenHandler> {                              //Class to create a CustomTableBlockScreen (What we see visually)

    private static final Identifier GUI_TEXTURE =                                                                       //Creates a new Identifier called GUI_TEXTURE
            new Identifier(DnDMod.MOD_ID, "textures/gui/custom_table_block_gui.png");                             //This is given a path to an image for the GUI of the CustomTableBlockScreen
    private EnergyInfoArea energyInfoArea;                                                                              //Creates an EnergyInfoArea variable
    private FluidStackRenderer fluidStackRenderer;
    public CustomTableBlockScreen(CustomTableBlockScreenHandler handler, PlayerInventory inventory, Text title) {       //Constructor for the CustomTableBlockScreen
        super(handler, inventory, title);
    }

    @Override
    protected void init() {                                                                                             //Checks if the texture is within the screen dimensions
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title))/2;                                                    //Sets the titleX variable
        assignEnergyInfoData();
        assignFluidStackRenderer();
    }

    private void assignFluidStackRenderer() {                                                                           //Method to assign the EnergyInfoArea
        fluidStackRenderer = new FluidStackRenderer(                                                                    //Creates a new Fluid Stack Rendered
                FluidStack.convertDropletsToMb(FluidConstants.BUCKET)*20,                                               //At the Capacity
                true, 15, 61);                                                                            //Shown Capacity, width, height
    }

    private void assignEnergyInfoData() {                                                                               //Method to assign the EnergyInfoArea
        energyInfoArea = new EnergyInfoArea(((width-backgroundWidth)/2)+156,                                      //Assigns the width pos
                ((height-backgroundHeight)/2)+13, handler.blockEntity.energyStorage);                                   //Assigns the Height Pos
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {                                       //Method to Draw the Foreground or Amount Filled in Bar
        int x = (width-backgroundWidth)/2;
        int y = (height-backgroundHeight)/2;

        renderEnergyAreaToolTips(matrices,mouseX,mouseY,x,y);                                                           //Calls the renderEnergyAreaToolTips method
        renderFluidTooltip(matrices,mouseX,mouseY,x,y, handler.fluidStack, 55, 15, fluidStackRenderer);
    }

    private void renderFluidTooltip(MatrixStack matrices, int pMouseX, int pMouseY, int x, int y,                       //Method to render the tooltip when hovering over the fluid container
                                    FluidStack fluidStack, int offsetX, int offsetY, FluidStackRenderer renderer) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, offsetX, offsetY,renderer)){                                        //checks that the Mouse is over the liquid container area
            renderTooltip(matrices, fluidStackRenderer.getTooltip(fluidStack, TooltipContext.Default.NORMAL),           //Calls renderTooltip Method from the screen class
                    Optional.empty(), pMouseX-x,pMouseY-y);
        }
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
        energyInfoArea.draw(matrices);

        fluidStackRenderer.drawFluid(matrices, handler.fluidStack,                                                      //sets the fluid stack handler to the fluid's area
                                        x+55, y +15, 16, 61,
                FluidStack.convertDropletsToMb(FluidConstants.BUCKET)*20);
    }

    private void renderEnergyAreaToolTips(MatrixStack matrices, int pMouseX, int pMouseY, int x, int y){                //Method to render the Tool Tips for the Energy Bar
        if(isMouseAboveArea(pMouseX,pMouseY, x,y,156,13,8,64)){                            //Checks if the mouse is over the ScreenHandler Energy Bar
            renderTooltip(matrices,energyInfoArea.getTooltips(),                                                        //calls the renderToolTip Method
                    Optional.empty(),pMouseX-x,pMouseY-y);
        }
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y,                                            //method to check if the mouse is over the Energy Bar
                                     int offsetX, int offsetY, FluidStackRenderer renderer){
        return MouseUtil.isMouseOver(pMouseX,pMouseY,x+offsetX,y+offsetY ,
                                    renderer.getWidth(), renderer.getHeight());
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y,                                            //method to check if the mouse is over the Energy Bar
                                     int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x+offsetX, y+offsetY, width, height);
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

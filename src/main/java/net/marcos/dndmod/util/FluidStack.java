package net.marcos.dndmod.util;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;

public class FluidStack {                                                                                               //Custom Util Class for FluidStack Transferring

    public FluidVariant fluidVariant;                                                                                   //Creates a FluidVariant Variable called fluidVariant
    public long amount;                                                                                                 //Creates a long Variable called amount

    public FluidStack(FluidVariant variant, long amount){                                                               //FluidStack constructor
        this.fluidVariant = variant;                                                                                    //Sets the fluidVariant
        this.amount = amount;                                                                                           //Sets the amount
    }


    //Following are Getters/Setters/Converters
    public FluidVariant getFluidVariant(){
        return fluidVariant;
    }
    public void setFluidVariant(FluidVariant fluidVariant){
        this.fluidVariant = fluidVariant;
    }
    public long getAmount(){
        return amount;
    }
    public void setAmount(long amount){
        this.amount = amount;
    }
    public static long convertDropletsToMb(long droplets){
        return (droplets/81);
    }
    public static long convertMbToDroplets(long mb){
        return mb*81;
    }
}

package com.MINEGAS123.SF2SF;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(SF2SF.MOD_ID)
public class SF2SF {
    public static final String MOD_ID = "sf2sf";
    
    public SF2SF() {
        MinecraftForge.EVENT_BUS.register(this);
        NetworkHandler.register();
    }
}

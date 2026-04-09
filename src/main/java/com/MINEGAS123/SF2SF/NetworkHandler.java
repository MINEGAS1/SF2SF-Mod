package com.MINEGAS123.SF2SF;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
        new ResourceLocation(SF2SF.MOD_ID, "main"),
        () -> "1",
        s -> true,
        s -> true
    );
    
    public static void register() {
        INSTANCE.registerMessage(0, KickPacket.class,
            KickPacket::encode,
            KickPacket::decode,
            KickPacket::handle
        );
    }
}

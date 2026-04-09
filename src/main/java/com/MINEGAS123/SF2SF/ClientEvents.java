package com.MINEGAS123.SF2SF;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = SF2SF.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {
    private static int cooldown = 0;
    
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;
        
        if (cooldown > 0) cooldown--;
        
        if (KeyBindings.KICK_KEY.consumeClick() && cooldown == 0) {
            NetworkHandler.INSTANCE.sendToServer(new KickPacket());
            cooldown = 10;
        }
    }
}

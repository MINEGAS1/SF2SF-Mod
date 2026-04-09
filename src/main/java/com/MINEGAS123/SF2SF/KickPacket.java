package com.MINEGAS123.SF2SF;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import java.util.List;
import java.util.function.Supplier;

public class KickPacket {
    public KickPacket() {}
    
    public void encode(FriendlyByteBuf buffer) {}
    
    public static KickPacket decode(FriendlyByteBuf buffer) {
        return new KickPacket();
    }
    
    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                Vec3 eyePos = player.getEyePosition();
                Vec3 lookVec = player.getLookAngle();
                double range = 3.5;
                Vec3 reachPoint = eyePos.add(lookVec.scale(range));
                AABB searchBox = player.getBoundingBox().inflate(range);
                List<net.minecraft.world.entity.Entity> entities = player.serverLevel().getEntities(player, searchBox);
                
                for (net.minecraft.world.entity.Entity target : entities) {
                    if (target.isAlive() && target instanceof LivingEntity) {
                        if (target.getBoundingBox().clip(eyePos, reachPoint).isPresent()) {
                            target.hurt(player.damageSources().playerAttack(player), 5.0f);
                            Vec3 knockback = target.position().subtract(player.position()).normalize();
                            target.setDeltaMovement(knockback.x * 0.6, 0.4, knockback.z * 0.6);
                            break;
                        }
                    }
                }
            }
        });
        context.setPacketHandled(true);
    }
}

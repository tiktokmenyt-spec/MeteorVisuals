package dev.meteorvisuals.mixin;

import dev.meteorvisuals.client.VisualConfig;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public abstract class ParticleManagerMixin {
    @Inject(method = "addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V", at = @At("HEAD"), cancellable = true)
    private void meteorvisuals$filter(ParticleEffect effect, double x, double y, double z, double vx, double vy, double vz, CallbackInfo ci) {
        if (!VisualConfig.enabled || (!VisualConfig.explosions && (effect.getType() == ParticleTypes.EXPLOSION || effect.getType() == ParticleTypes.EXPLOSION_EMITTER)) || (!VisualConfig.weather && (effect.getType() == ParticleTypes.RAIN || effect.getType() == ParticleTypes.SNOWFLAKE))) ci.cancel();
    }
}

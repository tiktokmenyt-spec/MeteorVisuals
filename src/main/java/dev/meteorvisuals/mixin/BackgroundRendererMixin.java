package dev.meteorvisuals.mixin;

import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Fog;
import net.minecraft.client.render.FogType;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BackgroundRenderer.class)
public abstract class BackgroundRendererMixin {
    @Inject(method = "applyFog", at = @At("RETURN"), cancellable = true)
    private static void meteorvisuals$nightMist(Camera camera, FogType type, float viewDistance, boolean thick, float tickDelta, CallbackInfoReturnable<Fog> cir) {
        if (camera.getFocusedEntity() != null && camera.getFocusedEntity().getWorld() instanceof ClientWorld world) {
            long time = world.getTimeOfDay() % 24000L;
            if (time > 13000L && time < 23000L) {
                Fog fog = cir.getReturnValue();
                cir.setReturnValue(new Fog(fog.start(), fog.end(), fog.shape(), 0.18f, 0.02f, 0.28f, fog.alpha()));
            }
        }
    }
}

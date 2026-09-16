package dev.meteorvisuals.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.particle.DustParticleEffect;

public final class ArrowTrailSystem {
    private ArrowTrailSystem() {}

    public static void initialize() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.world == null || client.player == null) return;
            for (ArrowEntity arrow : client.world.getEntitiesByClass(ArrowEntity.class,
                    client.player.getBoundingBox().expand(96), a -> true)) {
                float t = (arrow.age % 40) / 39.0f;
                int r = (int) (138 + (255 - 138) * t);
                int g = (int) (43 + (215 - 43) * t);
                int b = (int) (226 - 226 * t);
                int rgb = (r << 16) | (g << 8) | b;
                float red = ((rgb >> 16) & 255) / 255.0f;
                float green = ((rgb >> 8) & 255) / 255.0f;
                float blue = (rgb & 255) / 255.0f;
                var p = arrow.getPos();
                client.world.addParticle(new DustParticleEffect(red, green, blue, 1.0f), p.x, p.y, p.z, 0, 0.01, 0);
            }
        });
    }
}

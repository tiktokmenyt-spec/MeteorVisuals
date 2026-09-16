package dev.meteorvisuals.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudElementRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

/** A local presentation mode for recording screenshots or OBS footage. */
public final class DemoMode {
    private static final Identifier HUD_ID = Identifier.of("meteorvisuals", "demo_mode");
    private static boolean active;
    private static int ticks;

    private DemoMode() {}

    public static void initialize() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> tick(client));
        HudElementRegistry.addLast(HUD_ID, (context, tickCounter) -> renderHud(context));
    }

    public static boolean isActive() {
        return active;
    }

    public static void toggle() {
        active = !active;
        ticks = 0;
    }

    private static void tick(MinecraftClient client) {
        if (!active || !VisualConfig.enabled || !VisualConfig.demoEffects || client.world == null || client.player == null) {
            return;
        }

        ticks++;
        if (ticks % 2 != 0) return;

        double time = ticks * 0.12D;
        double radius = 1.15D + MathHelper.sin(ticks * 0.04F) * 0.12D;
        for (int i = 0; i < 12; i++) {
            double angle = time + Math.PI * 2.0D * i / 12.0D;
            double x = client.player.getX() + Math.cos(angle) * radius;
            double y = client.player.getY() + 0.25D + (i % 3) * 0.42D;
            double z = client.player.getZ() + Math.sin(angle) * radius;
            float mix = i / 11.0F;
            float red = 0.54F + mix * 0.46F;
            float green = 0.17F + mix * 0.67F;
            float blue = 0.89F - mix * 0.89F;
            client.world.addParticle(new DustParticleEffect(red, green, blue, 1.0F), x, y, z, 0.0D, 0.015D, 0.0D);
        }
    }

    private static void renderHud(DrawContext context) {
        if (!active || !VisualConfig.enabled || !VisualConfig.demoEffects) return;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        int center = client.getWindow().getScaledWidth() / 2;
        context.drawCenteredTextWithShadow(
                client.textRenderer,
                Text.literal("METEOR VISUALS • DEMO MODE"),
                center,
                12,
                0xFFFFD700
        );
        context.drawCenteredTextWithShadow(
                client.textRenderer,
                Text.literal("Local presentation effects only"),
                center,
                24,
                0xFF8A2BE2
        );
    }
}

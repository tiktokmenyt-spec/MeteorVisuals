package dev.meteorvisuals.client;

import dev.meteorvisuals.audio.AudioPulseEngine;
import net.fabricmc.fabric.api.client.rendering.v1.HudElementRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public final class VisualHud {
    private static final Identifier ID = Identifier.of("meteorvisuals", "cooldown");
    private VisualHud() {}
    public static void initialize() { HudElementRegistry.addLast(ID, (context, tick) -> render(context, tick.getTickDelta(true))); }

    private static void render(DrawContext context, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        float pearl = client.player.getItemCooldownManager().getCooldownProgress(Items.ENDER_PEARL, delta);
        float apple = client.player.getItemCooldownManager().getCooldownProgress(Items.GOLDEN_APPLE, delta);
        float progress = Math.max(pearl, apple);
        if (progress <= 0) return;
        int x = client.getWindow().getScaledWidth() - 54;
        int y = client.getWindow().getScaledHeight() - 70;
        context.drawCenteredTextWithShadow(client.textRenderer, Math.round(progress * 100) + "%", x, y + 28, 0xFFFFD700);
        int radius = 18;
        for (int i = 0; i < 32 * progress; i++) {
            double a = -Math.PI / 2 + i * Math.PI * 2 / 32;
            int px = x + (int)(Math.cos(a) * radius);
            int py = y + (int)(Math.sin(a) * radius);
            int color = i < 16 ? 0xFF8A2BE2 : 0xFFFFD700;
            context.fill(px - 2, py - 2, px + 2, py + 2, color);
        }
        if (AudioPulseEngine.getPulseScale() > 1.05f) context.fill(x - 21, y - 21, x + 21, y - 20, 0xFFFFD700);
    }
}

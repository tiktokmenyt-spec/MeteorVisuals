package dev.meteorvisuals.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudElementRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Locale;

public final class VisualOverlay {
    private static final Identifier ID = Identifier.of("meteorvisuals", "status_overlay");

    private VisualOverlay() {
    }

    public static void initialize() {
        HudElementRegistry.addLast(ID, (context, tickCounter) -> render(context));
    }

    private static void render(DrawContext context) {
        if (!VisualConfig.enabled || !VisualConfig.overlay) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) return;

        int x = 8;
        int y = 8;
        int purple = 0xFF8A2BE2;
        int gold = 0xFFFFD700;

        context.drawTextWithShadow(client.textRenderer, Text.literal("METEOR VISUALS"), x, y, gold);
        y += 12;
        context.drawTextWithShadow(client.textRenderer,
                Text.literal(String.format(Locale.ROOT, "XYZ %.1f %.1f %.1f", client.player.getX(), client.player.getY(), client.player.getZ())),
                x, y, purple);
        y += 12;
        context.drawTextWithShadow(client.textRenderer,
                Text.literal("FPS " + client.getCurrentFps()), x, y, gold);
    }
}

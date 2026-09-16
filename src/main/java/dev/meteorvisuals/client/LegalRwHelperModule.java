package dev.meteorvisuals.client;

import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudElementRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import java.util.ArrayDeque;

public final class LegalRwHelperModule {
    private static final ArrayDeque<Notice> NOTICES = new ArrayDeque<>();
    private static final Identifier ID = Identifier.of("meteorvisuals", "rw_notifications");
    private LegalRwHelperModule() {}

    public static void initialize() {
        ClientReceiveMessageEvents.GAME.register((message, overlay) -> {
            String text = message.getString();
            String value = text.toLowerCase(java.util.Locale.ROOT);
            if (value.contains("dynamic") || value.contains("toxic waste") || value.contains("токсич") || value.contains("ивент")) {
                synchronized (NOTICES) { NOTICES.addLast(new Notice(text, System.currentTimeMillis() + 6000)); }
            }
        });
        HudElementRegistry.addLast(ID, (context, tickCounter) -> render(context));
    }

    private static void render(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        long now = System.currentTimeMillis();
        synchronized (NOTICES) {
            NOTICES.removeIf(n -> n.expires < now);
            int y = 14;
            for (Notice n : NOTICES) {
                context.drawTextWithShadow(client.textRenderer, Text.literal(n.text), 12, y, 0xFFFFD700);
                y += 12;
            }
        }
    }

    private record Notice(String text, long expires) {}
}

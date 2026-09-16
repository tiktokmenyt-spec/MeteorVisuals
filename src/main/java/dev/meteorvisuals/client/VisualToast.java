package dev.meteorvisuals.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public final class VisualToast {
    private VisualToast() {
    }

    public static void show(String message) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null && VisualConfig.notifications) {
            client.player.sendMessage(Text.literal("§5Meteor Visuals §6» §f" + message), true);
        }
    }
}

package dev.meteorvisuals.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public final class MeteorKeybinds {
    public static final KeyBinding OPEN_MENU = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.meteorvisuals.open_menu",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            "category.meteorvisuals"
    ));

    private MeteorKeybinds() {
    }

    public static void initialize() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (OPEN_MENU.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new VisualSettingsScreen(Text.translatable("screen.meteorvisuals.title")));
                }
            }
        });
    }
}

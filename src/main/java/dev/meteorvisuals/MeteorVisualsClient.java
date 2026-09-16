package dev.meteorvisuals;

import dev.meteorvisuals.audio.AudioPulseEngine;
import dev.meteorvisuals.client.ArrowTrailSystem;
import dev.meteorvisuals.client.LegalRwHelperModule;
import dev.meteorvisuals.client.VisualHud;
import net.fabricmc.api.ClientModInitializer;

public final class MeteorVisualsClient implements ClientModInitializer {
    public static final int PURPLE = 0x8A2BE2;
    public static final int GOLD = 0xFFD700;

    @Override public void onInitializeClient() {
        ArrowTrailSystem.initialize();
        LegalRwHelperModule.initialize();
        VisualHud.initialize();
        AudioPulseEngine.start();
    }
}

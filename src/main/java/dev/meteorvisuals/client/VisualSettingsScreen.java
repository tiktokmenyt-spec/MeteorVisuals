package dev.meteorvisuals.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.text.Text;

public final class VisualSettingsScreen extends Screen {
    private static final int PURPLE = 0xFF8A2BE2;
    private static final int GOLD = 0xFFFFD700;

    public VisualSettingsScreen(Text title) {
        super(title);
    }

    @Override
    protected void init() {
        int center = this.width / 2;
        int y = this.height / 2 - 84;

        addDrawableChild(CyclingButtonWidget.onOffBuilder()
                .initially(VisualConfig.enabled)
                .build(center - 100, y, 200, 20, Text.translatable("option.meteorvisuals.enabled"),
                        (button, value) -> VisualConfig.enabled = value));

        addDrawableChild(CyclingButtonWidget.onOffBuilder()
                .initially(VisualConfig.arrowTrails)
                .build(center - 100, y + 26, 200, 20, Text.translatable("option.meteorvisuals.arrow_trails"),
                        (button, value) -> VisualConfig.arrowTrails = value));

        addDrawableChild(CyclingButtonWidget.onOffBuilder()
                .initially(VisualConfig.notifications)
                .build(center - 100, y + 52, 200, 20, Text.translatable("option.meteorvisuals.notifications"),
                        (button, value) -> VisualConfig.notifications = value));

        addDrawableChild(CyclingButtonWidget.onOffBuilder()
                .initially(VisualConfig.overlay)
                .build(center - 100, y + 78, 200, 20, Text.translatable("option.meteorvisuals.overlay"),
                        (button, value) -> VisualConfig.overlay = value));

        addDrawableChild(ButtonWidget.builder(Text.translatable("gui.done"), button -> close())
                .dimensions(center - 100, y + 112, 200, 20)
                .build());
    }

    private void close() {
        if (client != null) client.setScreen(null);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(textRenderer, title, width / 2, 24, GOLD);
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("Meteor Visuals"), width / 2, 42, PURPLE);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}

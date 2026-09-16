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
        int y = this.height / 2 - 100;

        addDrawableChild(CyclingButtonWidget.onOffBuilder().initially(VisualConfig.enabled)
                .build(center - 100, y, 200, 20, Text.translatable("option.meteorvisuals.enabled"),
                        (button, value) -> VisualConfig.enabled = value));
        addDrawableChild(CyclingButtonWidget.onOffBuilder().initially(VisualConfig.arrowTrails)
                .build(center - 100, y + 25, 200, 20, Text.translatable("option.meteorvisuals.arrow_trails"),
                        (button, value) -> VisualConfig.arrowTrails = value));
        addDrawableChild(CyclingButtonWidget.onOffBuilder().initially(VisualConfig.notifications)
                .build(center - 100, y + 50, 200, 20, Text.translatable("option.meteorvisuals.notifications"),
                        (button, value) -> VisualConfig.notifications = value));
        addDrawableChild(CyclingButtonWidget.onOffBuilder().initially(VisualConfig.overlay)
                .build(center - 100, y + 75, 200, 20, Text.translatable("option.meteorvisuals.overlay"),
                        (button, value) -> VisualConfig.overlay = value));
        addDrawableChild(CyclingButtonWidget.onOffBuilder().initially(VisualConfig.demoEffects)
                .build(center - 100, y + 100, 200, 20, Text.translatable("option.meteorvisuals.demo_effects"),
                        (button, value) -> VisualConfig.demoEffects = value));
        addDrawableChild(ButtonWidget.builder(
                        Text.translatable(DemoMode.isActive() ? "button.meteorvisuals.stop_demo" : "button.meteorvisuals.start_demo"),
                        button -> {
                            DemoMode.toggle();
                            button.setMessage(Text.translatable(DemoMode.isActive() ? "button.meteorvisuals.stop_demo" : "button.meteorvisuals.start_demo"));
                        })
                .dimensions(center - 100, y + 127, 200, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.translatable("gui.done"), button -> close())
                .dimensions(center - 100, y + 153, 200, 20).build());
    }

    private void close() {
        if (client != null) client.setScreen(null);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(textRenderer, title, width / 2, 18, GOLD);
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("Meteor Visuals"), width / 2, 34, PURPLE);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}

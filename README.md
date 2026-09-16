# Meteor Visuals

Client-only Fabric 1.21.4 visuals for Java 21. The mod does not send packets, automate gameplay, alter combat, or change server state.

Features included:

- Purple-to-gold client-side arrow trails.
- ReallyWorld-style public announcement notifications.
- Ender Pearl and Golden Apple cooldown HUD.
- Configurable particle filtering mixin.
- Purple nighttime fog replacement.
- Audio RMS pulse engine with a bounded 1.0-1.25 scale.

The player cosmetics layer is intentionally left as a separate renderer feature because its constructor and model signatures vary between Yarn mapping builds. Add it only after confirming the exact `PlayerEntityRenderer` constructor in the selected mappings.

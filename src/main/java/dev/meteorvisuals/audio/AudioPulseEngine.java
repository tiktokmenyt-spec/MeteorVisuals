package dev.meteorvisuals.audio;

import javax.sound.sampled.*;
import java.util.concurrent.atomic.AtomicReference;

public final class AudioPulseEngine {
    private static final AtomicReference<Float> SCALE = new AtomicReference<>(1.0f);
    private static volatile boolean running;
    private AudioPulseEngine() {}
    public static float getPulseScale() { return SCALE.get(); }
    public static synchronized void start() {
        if (running) return;
        running = true;
        Thread.ofPlatform().name("meteorvisuals-audio-pulse").start(AudioPulseEngine::capture);
    }
    private static void capture() {
        AudioFormat format = new AudioFormat(44100, 16, 1, true, false);
        try (TargetDataLine line = AudioSystem.getTargetDataLine(format)) {
            line.open(format, 4096); line.start(); byte[] buffer = new byte[2646];
            while (running) {
                int n = line.read(buffer, 0, buffer.length); double sum = 0;
                for (int i = 0; i + 1 < n; i += 2) { short s = (short)((buffer[i + 1] << 8) | (buffer[i] & 255)); sum += s * (double)s; }
                float rms = n == 0 ? 0 : (float)(Math.sqrt(sum / Math.max(1, n / 2)) / 32768.0);
                float target = 1.0f + Math.min(0.25f, rms * 1.8f);
                SCALE.updateAndGet(old -> old + (target - old) * 0.2f);
                Thread.sleep(30);
            }
        } catch (LineUnavailableException | InterruptedException ignored) { SCALE.set(1.0f); }
    }
}

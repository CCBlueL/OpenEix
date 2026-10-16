/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

import com.heypixel.heypixelmod.obsoverlay.EixClientCore;
import com.heypixel.heypixelmod.obsoverlay.utils.BackgroundExecutor;
import java.io.BufferedInputStream;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundResourcePaths {
    public static void a(String string, float f2) {
        if (!EixClientCore.a().e) {
            return;
        }
        BackgroundExecutor.a(() -> {
            try {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(SoundResourcePaths.class.getResourceAsStream("/assets/heypixel/VcX6svVqmeT8/sounds/" + string));
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                FloatControl floatControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
                float f3 = floatControl.getMaximum() - floatControl.getMinimum();
                float f4 = f3 * f2 + floatControl.getMinimum();
                floatControl.setValue(f4);
                clip.start();
            }
            catch (IOException | LineUnavailableException | UnsupportedAudioFileException exception) {
                exception.printStackTrace();
            }
        });
    }
}


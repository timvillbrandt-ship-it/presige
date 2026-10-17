package dev.prestige.module.impl;

import dev.prestige.module.Module;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class FullbrightModule extends Module {
    public FullbrightModule() {
        super("Fullbright", "Alles hell, auch in Höhlen (clientseitig).", Category.RENDER);
    }

    @Override
    public void onTick() {
        var p = mc.player;
        if (p == null) return;
        // Immer wieder erneuern, damit der Effekt nie ausläuft
        p.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 400, 0, false, false, false));
    }

    @Override
    protected void onDisable() {
        if (mc.player != null) mc.player.removeEffect(MobEffects.NIGHT_VISION);
    }
}

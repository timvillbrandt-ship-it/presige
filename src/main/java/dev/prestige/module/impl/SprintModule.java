package dev.prestige.module.impl;

import dev.prestige.module.Module;

public class SprintModule extends Module {
    public SprintModule() {
        super("Sprint", "Sprintet automatisch beim Vorwärtslaufen.", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        var p = mc.player;
        if (p == null) return;
        boolean forward = mc.options.keyUp.isDown();
        if (forward && !p.isShiftKeyDown() && !p.horizontalCollision && p.getFoodData().getFoodLevel() > 6) {
            p.setSprinting(true);
        }
    }
}

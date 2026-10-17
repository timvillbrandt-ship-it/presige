package dev.prestige;

import com.mojang.blaze3d.platform.InputConstants;
import dev.prestige.gui.ClickGui;
import dev.prestige.module.Module;
import dev.prestige.module.ModuleManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class PrestigeClient implements ClientModInitializer {
    public static final String MOD_ID = "prestige";
    private static ModuleManager modules;
    private static KeyMapping openGui;

    public static ModuleManager modules() { return modules; }

    @Override
    public void onInitializeClient() {
        modules = new ModuleManager();

        KeyMapping.Category category = KeyMapping.Category.register(
                Identifier.fromNamespaceAndPath(MOD_ID, "main"));
        openGui = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.prestige.open_gui", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, category));

        ClientTickEvents.END_CLIENT_TICK.register(mc -> {
            while (openGui.consumeClick()) {
                mc.setScreen(new ClickGui());
            }
            if (mc.player != null && mc.level != null) modules.tick();
        });

        // HUD: Wasserzeichen + Liste der aktiven Module
        HudElementRegistry.addLast(Identifier.fromNamespaceAndPath(MOD_ID, "hud"), (g, delta) -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.options.hideGui) return;
            g.text(mc.font, "Prestige", 4, 4, 0xFF7C5CFF, true);
            int y = 4;
            int right = mc.getWindow().getGuiScaledWidth();
            for (Module m : modules.all()) {
                if (!m.isEnabled()) continue;
                int w = mc.font.width(m.getName());
                g.text(mc.font, m.getName(), right - w - 4, y, 0xFFFFFFFF, true);
                y += 10;
            }
        });
    }
}

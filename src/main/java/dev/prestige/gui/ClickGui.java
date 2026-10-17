package dev.prestige.gui;

import dev.prestige.PrestigeClient;
import dev.prestige.module.Module;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.List;

/** Einfache ClickGUI: eine Spalte pro Kategorie, Linksklick toggelt ein Modul. */
public class ClickGui extends Screen {
    private static final int COL_W = 110;
    private static final int ROW_H = 16;
    private static final int GAP = 12;

    public ClickGui() {
        super(Component.literal("Prestige"));
    }

    @Override
    public boolean isPauseScreen() { return false; }

    @Override
    public void extractRenderState(GuiGraphicsExtractor g, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(g, mouseX, mouseY, partialTick);

        int x = 20;
        for (Module.Category cat : Module.Category.values()) {
            List<Module> mods = PrestigeClient.modules().byCategory(cat);
            if (mods.isEmpty()) continue;

            int y = 20;
            g.fill(x, y, x + COL_W, y + ROW_H, 0xFF1E1E2E);
            g.text(this.font, cat.name(), x + 4, y + 4, 0xFFFFFFFF, true);
            y += ROW_H;

            for (Module m : mods) {
                boolean hover = inside(mouseX, mouseY, x, y, COL_W, ROW_H);
                int bg = m.isEnabled() ? 0xFF7C5CFF : (hover ? 0xFF3A3A4A : 0xFF2A2A3A);
                g.fill(x, y, x + COL_W, y + ROW_H, bg);
                g.text(this.font, m.getName(), x + 4, y + 4, 0xFFFFFFFF, true);
                if (hover) {
                    g.text(this.font, m.getDescription(), 20, this.height - 20, 0xFFCCCCCC, true);
                }
                y += ROW_H;
            }
            x += COL_W + GAP;
        }
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        double mx = event.x(), my = event.y();
        int button = event.button();

        int x = 20;
        for (Module.Category cat : Module.Category.values()) {
            List<Module> mods = PrestigeClient.modules().byCategory(cat);
            if (mods.isEmpty()) continue;

            int y = 20 + ROW_H;
            for (Module m : mods) {
                if (button == 0 && inside(mx, my, x, y, COL_W, ROW_H)) {
                    m.toggle();
                    return true;
                }
                y += ROW_H;
            }
            x += COL_W + GAP;
        }
        return super.mouseClicked(event, doubleClick);
    }

    private static boolean inside(double mx, double my, int x, int y, int w, int h) {
        return mx >= x && mx < x + w && my >= y && my < y + h;
    }
}

package dev.prestige.module;

import net.minecraft.client.Minecraft;

public abstract class Module {
    protected static final Minecraft mc = Minecraft.getInstance();

    private final String name;
    private final String description;
    private final Category category;
    private boolean enabled;

    public enum Category { MOVEMENT, RENDER, PLAYER, MISC }

    protected Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public Category getCategory() { return category; }
    public boolean isEnabled() { return enabled; }

    public void toggle() { setEnabled(!enabled); }

    public void setEnabled(boolean value) {
        if (this.enabled == value) return;
        this.enabled = value;
        try {
            if (value) onEnable(); else onDisable();
        } catch (Throwable t) {
            // Ein kaputtes Modul darf nie das Spiel crashen
            this.enabled = false;
            t.printStackTrace();
        }
    }

    protected void onEnable() {}
    protected void onDisable() {}
    /** Wird jeden Client-Tick aufgerufen, solange das Modul aktiv ist (player != null). */
    public void onTick() {}
}

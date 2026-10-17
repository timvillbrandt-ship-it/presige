package dev.prestige.module;

import dev.prestige.module.impl.FullbrightModule;
import dev.prestige.module.impl.SprintModule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ModuleManager {
    private final List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        // Neue Module hier registrieren
        register(new SprintModule());
        register(new FullbrightModule());
    }

    private void register(Module m) { modules.add(m); }

    public List<Module> all() { return Collections.unmodifiableList(modules); }

    public List<Module> byCategory(Module.Category c) {
        return modules.stream().filter(m -> m.getCategory() == c).toList();
    }

    public void tick() {
        for (Module m : modules) {
            if (!m.isEnabled()) continue;
            try {
                m.onTick();
            } catch (Throwable t) {
                m.setEnabled(false);
                t.printStackTrace();
            }
        }
    }
}

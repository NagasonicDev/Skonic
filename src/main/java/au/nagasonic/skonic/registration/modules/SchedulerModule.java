package au.nagasonic.skonic.registration.modules;

import au.nagasonic.skonic.registration.HierarchicalAddonModule;
import ch.njol.skript.SkriptAddon;
import org.jetbrains.annotations.NotNull;

public class SchedulerModule extends HierarchicalAddonModule {

    @NotNull
    @Override
    public String name() {
        return "Scheduler";
    }

    @Override
    public void loadSelf(@NotNull SkriptAddon addon) {
    }
}

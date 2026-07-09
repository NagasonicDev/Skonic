package au.nagasonic.skonic.modules;

import au.nagasonic.skonic.classes.other.Types;
import org.jetbrains.annotations.NotNull;
import org.skriptlang.skript.addon.AddonModule;
import org.skriptlang.skript.addon.HierarchicalAddonModule;
import org.skriptlang.skript.addon.SkriptAddon;

public class OtherTypesModule extends HierarchicalAddonModule {
    public OtherTypesModule(AddonModule parent) {
        super(parent);
    }

    @Override
    public String name() {
        return "Other Types";
    }

    @Override
    public void loadSelf(@NotNull SkriptAddon addon) {
        register(addon,
                Types::register
        );
    }
}

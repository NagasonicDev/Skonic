package au.nagasonic.skonic.modules.items.other;

import au.nagasonic.skonic.modules.items.other.elements.ExprEnchBookWith;
import au.nagasonic.skonic.modules.items.other.elements.ExprSmelted;
import org.jetbrains.annotations.NotNull;
import org.skriptlang.skript.addon.AddonModule;
import org.skriptlang.skript.addon.HierarchicalAddonModule;
import org.skriptlang.skript.addon.SkriptAddon;

public class OtherItemsModule extends HierarchicalAddonModule {
    public OtherItemsModule(AddonModule parent) {
        super(parent);
    }

    @Override
    public String name() {
        return "Other Items";
    }

    @Override
    public void loadSelf(@NotNull SkriptAddon addon) {
        register(addon,
                ExprEnchBookWith::register,
                ExprSmelted::register
        );
    }
}

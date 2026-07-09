package au.nagasonic.skonic.registration.modules;

import au.nagasonic.skonic.elements.items.other.ExprEnchBookWith;
import au.nagasonic.skonic.elements.items.other.ExprSmelted;
import au.nagasonic.skonic.registration.HierarchicalAddonModule;
import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.jetbrains.annotations.NotNull;
import org.skriptlang.skript.registration.SyntaxRegistry;

public class OtherItemsModule extends HierarchicalAddonModule {

    @NotNull
    @Override
    public String name() {
        return "Other Items";
    }

    @Override
    public void loadSelf(@NotNull SkriptAddon addon) {
        SyntaxRegistry registry = Skript.instance().syntaxRegistry();

        ExprEnchBookWith.register(registry);
        ExprSmelted.register(registry);
    }
}

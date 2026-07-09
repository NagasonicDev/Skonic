package au.nagasonic.skonic.registration.modules;

import au.nagasonic.skonic.classes.other.Types;
import au.nagasonic.skonic.registration.HierarchicalAddonModule;
import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.jetbrains.annotations.NotNull;
import org.skriptlang.skript.registration.SyntaxRegistry;

public class OtherTypesModule extends HierarchicalAddonModule {

    @NotNull
    @Override
    public String name() {
        return "Other Types";
    }

    @Override
    public void loadSelf(@NotNull SkriptAddon addon) {
        SyntaxRegistry registry = Skript.instance().syntaxRegistry();

        Types.register(registry);
    }
}

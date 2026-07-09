package au.nagasonic.skonic.registration.modules;

import au.nagasonic.skonic.classes.citizens.forcefield.ForcefieldType;
import au.nagasonic.skonic.elements.forcefield.*;
import au.nagasonic.skonic.registration.HierarchicalAddonModule;
import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.util.Version;
import org.jetbrains.annotations.NotNull;
import org.skriptlang.skript.registration.SyntaxRegistry;

public class ForcefieldModule extends HierarchicalAddonModule {

    @NotNull
    @Override
    public String name() {
        return "Forcefield";
    }

    @Override
    public boolean canLoad() {
        return Skript.getVersion().isLargerThan(new Version(2, 9, 5));
    }

    @Override
    public void loadSelf(@NotNull SkriptAddon addon) {
        SyntaxRegistry registry = Skript.instance().syntaxRegistry();

        ForcefieldType.register(registry);
        EffCitizenForcefield.register(registry);
        ExprForcefield.register(registry);
        ExprForcefieldHeight.register(registry);
        ExprForcefieldStrength.register(registry);
        ExprForcefieldVertStrength.register(registry);
        ExprForcefieldWidth.register(registry);
    }
}

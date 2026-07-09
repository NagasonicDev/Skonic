package au.nagasonic.skonic.modules.citizens.forcefield;

import au.nagasonic.skonic.classes.citizens.forcefield.ForcefieldType;
import au.nagasonic.skonic.modules.citizens.forcefield.elements.*;
import ch.njol.skript.Skript;
import ch.njol.skript.util.Version;
import org.jetbrains.annotations.NotNull;
import org.skriptlang.skript.addon.AddonModule;
import org.skriptlang.skript.addon.HierarchicalAddonModule;
import org.skriptlang.skript.addon.SkriptAddon;

public class ForcefieldModule extends HierarchicalAddonModule {
    public ForcefieldModule(AddonModule parent) {
        super(parent);
    }

    @Override
    public String name() {
        return "Forcefield";
    }
    @Override
    protected boolean canLoadSelf(SkriptAddon addon) {
        return Skript.getVersion().isLargerThan(new Version(2, 9, 5));
    }

    @Override
    public void loadSelf(@NotNull SkriptAddon addon) {
        register(addon,
                ForcefieldType::register,
                EffCitizenForcefield::register,
                ExprForcefield::register,
                ExprForcefieldHeight::register,
                ExprForcefieldStrength::register,
                ExprForcefieldVertStrength::register,
                ExprForcefieldWidth::register
        );
    }
}

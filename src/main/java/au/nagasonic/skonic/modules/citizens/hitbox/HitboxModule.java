package au.nagasonic.skonic.modules.citizens.hitbox;

import au.nagasonic.skonic.classes.citizens.hitbox.HitboxType;
import au.nagasonic.skonic.modules.citizens.hitbox.elements.*;
import ch.njol.skript.Skript;
import ch.njol.skript.util.Version;
import org.jetbrains.annotations.NotNull;
import org.skriptlang.skript.addon.AddonModule;
import org.skriptlang.skript.addon.HierarchicalAddonModule;
import org.skriptlang.skript.addon.SkriptAddon;

public class HitboxModule extends HierarchicalAddonModule {
    public HitboxModule(AddonModule parent) {
        super(parent);
    }

    @Override
    public String name() {
        return "Hitbox";
    }

    @Override
    protected boolean canLoadSelf(SkriptAddon addon) {
        return Skript.getVersion().isLargerThan(new Version(2, 9, 5));
    }

    @Override
    public void loadSelf(@NotNull SkriptAddon addon) {
        register(addon,
                HitboxType::register,
                ExprCitizenHitbox::register,
                ExprHitbox::register,
                ExprHitboxHeight::register,
                ExprHitboxScale::register,
                ExprHitboxWidth::register
        );
    }
}

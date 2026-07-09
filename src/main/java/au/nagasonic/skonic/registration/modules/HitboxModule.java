package au.nagasonic.skonic.registration.modules;

import au.nagasonic.skonic.classes.citizens.hitbox.HitboxType;
import au.nagasonic.skonic.elements.hitbox.*;
import au.nagasonic.skonic.registration.HierarchicalAddonModule;
import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.util.Version;
import org.jetbrains.annotations.NotNull;
import org.skriptlang.skript.registration.SyntaxRegistry;

public class HitboxModule extends HierarchicalAddonModule {

    @NotNull
    @Override
    public String name() {
        return "Hitbox";
    }

    @Override
    public boolean canLoad() {
        return Skript.getVersion().isLargerThan(new Version(2, 9, 5));
    }

    @Override
    public void loadSelf(@NotNull SkriptAddon addon) {
        SyntaxRegistry registry = Skript.instance().syntaxRegistry();

        HitboxType.register(registry);
        ExprCitizenHitbox.register(registry);
        ExprHitbox.register(registry);
        ExprHitboxHeight.register(registry);
        ExprHitboxScale.register(registry);
        ExprHitboxWidth.register(registry);
    }
}

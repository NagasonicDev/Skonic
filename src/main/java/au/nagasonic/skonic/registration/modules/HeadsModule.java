package au.nagasonic.skonic.registration.modules;

import au.nagasonic.skonic.elements.items.heads.*;
import au.nagasonic.skonic.registration.HierarchicalAddonModule;
import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.jetbrains.annotations.NotNull;
import org.skriptlang.skript.registration.SyntaxRegistry;

public class HeadsModule extends HierarchicalAddonModule {

    @NotNull
    @Override
    public String name() {
        return "Heads";
    }

    @Override
    public void loadSelf(@NotNull SkriptAddon addon) {
        SyntaxRegistry registry = Skript.instance().syntaxRegistry();

        ExprHeadFromName.register(registry);
        ExprHeadFromPlayer.register(registry);
        ExprHeadFromSkin.register(registry);
        ExprHeadFromURL.register(registry);
        ExprHeadFromUUID.register(registry);
        ExprHeadFromValue.register(registry);
        ExprOwnerOfHead.register(registry);
        ExprURLOfHead.register(registry);
        ExprValueOfHead.register(registry);
    }
}

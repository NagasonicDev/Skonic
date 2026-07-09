package au.nagasonic.skonic.registration.modules;

import au.nagasonic.skonic.elements.skins.*;
import au.nagasonic.skonic.registration.HierarchicalAddonModule;
import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.jetbrains.annotations.NotNull;
import org.skriptlang.skript.registration.SyntaxRegistry;

public class SkinsModule extends HierarchicalAddonModule {

    @NotNull
    @Override
    public String name() {
        return "Skins";
    }

    @Override
    public void loadSelf(@NotNull SkriptAddon addon) {
        SyntaxRegistry registry = Skript.instance().syntaxRegistry();

        EffChangeSkin.register(registry);
        EffDownloadPlayerSkin.register(registry);
        ExprPlayerSkin.register(registry);
        ExprSkinFromFile.register(registry);
        ExprSkinFromURL.register(registry);
        ExprSkinFromUUID.register(registry);
        ExprSkinSignature.register(registry);
        ExprSkinValue.register(registry);
        ExprSkinWith.register(registry);
        ExprURLInValue.register(registry);
    }
}

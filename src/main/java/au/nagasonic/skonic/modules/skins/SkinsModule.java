package au.nagasonic.skonic.modules.skins;

import au.nagasonic.skonic.modules.skins.elements.*;
import org.jetbrains.annotations.NotNull;
import org.skriptlang.skript.addon.AddonModule;
import org.skriptlang.skript.addon.HierarchicalAddonModule;
import org.skriptlang.skript.addon.SkriptAddon;

public class SkinsModule extends HierarchicalAddonModule {
    public SkinsModule(AddonModule parent) {
        super(parent);
    }

    @Override
    public String name() {
        return "Skins";
    }

    @Override
    public void loadSelf(@NotNull SkriptAddon addon) {
        register(addon,
                EffChangeSkin::register,
                EffDownloadPlayerSkin::register,
                ExprPlayerSkin::register,
                ExprSkinFromFile::register,
                ExprSkinFromURL::register,
                ExprSkinFromUUID::register,
                ExprSkinSignature::register,
                ExprSkinValue::register,
                ExprSkinWith::register,
                ExprURLInValue::register
        );
    }
}

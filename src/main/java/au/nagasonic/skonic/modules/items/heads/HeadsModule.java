package au.nagasonic.skonic.modules.items.heads;

import au.nagasonic.skonic.modules.items.heads.elements.*;
import org.jetbrains.annotations.NotNull;
import org.skriptlang.skript.addon.AddonModule;
import org.skriptlang.skript.addon.HierarchicalAddonModule;
import org.skriptlang.skript.addon.SkriptAddon;

public class HeadsModule extends HierarchicalAddonModule {
    public HeadsModule(AddonModule parent) {
        super(parent);
    }


    @Override
    public String name() {
        return "Heads";
    }

    @Override
    public void loadSelf(@NotNull SkriptAddon addon) {
        register(addon,
                ExprHeadFromName::register,
                ExprHeadFromPlayer::register,
                ExprHeadFromSkin::register,
                ExprHeadFromURL::register,
                ExprHeadFromUUID::register,
                ExprHeadFromValue::register,
                ExprOwnerOfHead::register,
                ExprURLOfHead::register,
                ExprValueOfHead::register
        );
    }
}

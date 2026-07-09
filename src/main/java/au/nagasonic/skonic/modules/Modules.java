package au.nagasonic.skonic.modules;

import au.nagasonic.skonic.modules.citizens.CitizensModule;
import au.nagasonic.skonic.modules.items.heads.HeadsModule;
import au.nagasonic.skonic.modules.items.other.OtherItemsModule;
import au.nagasonic.skonic.modules.skins.SkinsModule;
import org.skriptlang.skript.addon.AddonModule;
import org.skriptlang.skript.addon.HierarchicalAddonModule;
import org.skriptlang.skript.addon.SkriptAddon;

import java.util.List;

public class Modules extends HierarchicalAddonModule {
    @Override
    public Iterable<AddonModule> children() {
        return List.of(
                new CitizensModule(this),
                new HeadsModule(this),
                new OtherItemsModule(this),
                new OtherTypesModule(this),
                new SkinsModule(this)
        );
    }

    @Override
    protected void loadSelf(SkriptAddon skriptAddon) {
        // redundant asf
    }

    @Override
    public String name() {
        return "Skonic";
    }
}

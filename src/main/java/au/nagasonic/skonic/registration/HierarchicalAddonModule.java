package au.nagasonic.skonic.registration;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.jetbrains.annotations.NotNull;
import org.skriptlang.skript.registration.SyntaxRegistry;

import java.util.function.Consumer;

public abstract class HierarchicalAddonModule {

    @NotNull
    public abstract String name();

    public abstract void loadSelf(@NotNull SkriptAddon addon);

    public boolean canLoad() {
        return true;
    }

    @SafeVarargs
    protected final void registerElements(@NotNull Consumer<SyntaxRegistry>... elements) {
        SyntaxRegistry registry = Skript.instance().syntaxRegistry();
        for (Consumer<SyntaxRegistry> element : elements) {
            element.accept(registry);
        }
    }
}

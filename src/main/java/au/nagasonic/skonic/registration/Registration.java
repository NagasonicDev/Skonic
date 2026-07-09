package au.nagasonic.skonic.registration;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.SyntaxRegistry;

import java.util.function.Consumer;

public class Registration {

    private final SkriptAddon addon;
    private final SyntaxRegistry syntaxRegistry;

    public Registration(@NotNull SkriptAddon addon) {
        this.addon = addon;
        this.syntaxRegistry = Skript.instance().syntaxRegistry();
    }

    @NotNull
    public SkriptAddon getAddon() {
        return addon;
    }

    @NotNull
    public SyntaxRegistry getSyntaxRegistry() {
        return syntaxRegistry;
    }

    @SafeVarargs
    public final Registration register(@NotNull HierarchicalAddonModule... modules) {
        for (HierarchicalAddonModule module : modules) {
            module.loadSelf(addon);
        }
        return this;
    }

    @SafeVarargs
    public final Registration registerElements(@NotNull Consumer<SyntaxRegistry>... elements) {
        SyntaxRegistry registry = syntaxRegistry;
        for (Consumer<SyntaxRegistry> element : elements) {
            element.accept(registry);
        }
        return this;
    }

    public <E extends Event, T> void registerEventValue(@NotNull Class<E> eventClass,
                                                         @NotNull Class<T> valueClass,
                                                         @NotNull Getter<T, E> getter,
                                                         int time) {
        EventValues.registerEventValue(eventClass, valueClass, getter, time);
    }

    public void finalizeRegistration() {
    }
}

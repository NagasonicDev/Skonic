package au.nagasonic.skonic.elements.citizens.events;

import au.nagasonic.skonic.elements.citizens.Forcefield;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CitizenForcefieldCreateEvent extends Event {

    private final Forcefield forcefield;

    public CitizenForcefieldCreateEvent(Forcefield forcefield){
        this.forcefield = forcefield;
    }

    public Forcefield getForcefield(){
        return forcefield;
    }
    @Override
    public @NotNull HandlerList getHandlers() {
        throw new UnsupportedOperationException();
    }
}

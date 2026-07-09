package au.nagasonic.skonic.modules.citizens.forcefield.elements;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CitizenForcefieldCreateEvent extends Event {

    private final NPCForcefield forcefield;

    public CitizenForcefieldCreateEvent(NPCForcefield forcefield){
        this.forcefield = forcefield;
    }

    public NPCForcefield getForcefield(){
        return forcefield;
    }
    @Override
    public @NotNull HandlerList getHandlers() {
        throw new UnsupportedOperationException();
    }
}

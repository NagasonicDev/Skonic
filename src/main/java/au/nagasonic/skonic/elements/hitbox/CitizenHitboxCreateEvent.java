package au.nagasonic.skonic.elements.hitbox;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CitizenHitboxCreateEvent extends Event {
    private final NPCHitbox hitbox;

    public CitizenHitboxCreateEvent(NPCHitbox hitbox){
        this.hitbox = hitbox;
    }

    public NPCHitbox getHitbox(){
        return hitbox;
    }
    @Override
    public @NotNull HandlerList getHandlers() {
        throw new UnsupportedOperationException();
    }
}

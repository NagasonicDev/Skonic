package au.nagasonic.skonic.elements.citizens.effects;

import au.nagasonic.skonic.Skonic;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.AsyncEffect;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.MobType;
import net.citizensnpcs.trait.TargetableTrait;
import net.citizensnpcs.util.NMS;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

@Name("Make Citizen Targetable")
@Description({"Make a citizen targetable to other mobs.", "Or you can stop this process."})
@Examples({"make npc with id 2 targetable", "make npc with id 3 not targetable"})
@Since("1.2, 1.2.2-b1 (temporary)")
@RequiredPlugins("Citizens")
public class EffCitizenTargetable extends AsyncEffect {
    static {
        Skript.registerEffect(EffCitizenTargetable.class,
                "make (citizen|npc) %npc% [[able to] be] target(ed|able) [t:temporarily]",
                "make (citizen|npc) %npc% not [[able to] be] target(ed|able) [t:temporarily]");
    }
    private int pattern;
    private Expression<NPC> npcExpr;
    private boolean t;
    @Override
    protected void execute(Event event) {
        if (npcExpr != null){
            NPC npc = npcExpr.getSingle(event);
            if (npc != null){
                if (pattern == 0){
                    npc.getOrAddTrait(TargetableTrait.class).setTargetable(true);
                }else {
                    npc.getOrAddTrait(TargetableTrait.class).setTargetable(false);
                }
                if (pattern == 0 && npc.getOrAddTrait(MobType.class).getType() == EntityType.PLAYER
                        && npc.shouldRemoveFromPlayerList()) {
                    if (t == true) {
                        npc.data().set(NPC.Metadata.REMOVE_FROM_PLAYERLIST, false);
                    } else {
                        npc.data().setPersistent(NPC.Metadata.REMOVE_FROM_PLAYERLIST, false);
                    }
                    if (npc.isSpawned()) {
                        NMS.addOrRemoveFromPlayerList(npc.getEntity(), false);
                    }
                }
            }else Skonic.log(Level.SEVERE, "NPC cannot be null");
        }else Skonic.log(Level.SEVERE, "NPC Expression cannot be null");
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        if (pattern == 0){
            return "make " + npcExpr.toString(event, b) + " targetable";
        }else return "make " + npcExpr.toString(event, b) + " not targetable";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        pattern = i;
        npcExpr = (Expression<NPC>) exprs[0];
        t = parseResult.hasTag("t");
        return true;
    }
}

package au.nagasonic.skonic.modules.citizens.elements.effects;

import ch.njol.skript.Skript;
import org.skriptlang.skript.registration.SyntaxRegistry;
import org.skriptlang.skript.registration.SyntaxInfo;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Spawned;
import net.citizensnpcs.trait.CurrentLocation;
import org.bukkit.Location;

import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.Nullable;

@Name("Clone Citizen")
@Description("Clones a citizen and spawns a copy at the given location.")
@Since("1.2.5")
@RequiredPlugins("Citizens")
public class EffCloneCitizen extends Effect {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EFFECT,
            SyntaxInfo.builder(EffCloneCitizen.class)
                .addPatterns(
                    "(copy|clone) (npc|citizen) %npc% (to|at) %location% [n:[with] new name %-string%]"
                )
                .build()
        );
    }

    private Expression<NPC> npcExpr;
    private Expression<Location> locExpr;
    private boolean n;
    private Expression<String> stringExpr;

    @Override
    protected void execute(Event event) {
        final NPC npc = npcExpr.getSingle(event);
        final Location loc = locExpr.getSingle(event);
        String name = stringExpr.getSingle(event);
        if (npc == null) {
            Skript.error("No citizen found to clone.", ErrorQuality.SEMANTIC_ERROR);
            return;
        }
        if (loc == null){
            Skript.error("Location cannot be null.", ErrorQuality.SEMANTIC_ERROR);
            return;
        }
        if (name == null){
            name = npc.getRawName();
        }
        NPC copy = npc.clone();
        if (!copy.getRawName().equals(name)) {
            copy.setName(name);
        }
        if (copy.getOrAddTrait(Spawned.class).shouldSpawn()) {
            loc.getChunk().load();
            copy.teleport(loc, PlayerTeleportEvent.TeleportCause.PLUGIN);
            copy.getOrAddTrait(CurrentLocation.class).setLocation(loc);
        }
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return n ? "clone npc " + npcExpr.toString(event, b) + " to " + locExpr.toString(event, b) + " with new name " + stringExpr.toString(event, b)
                : "clone npc " + npcExpr.toString(event, b) + " to " + locExpr.toString(event, b);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        npcExpr = (Expression<NPC>) exprs[0];
        locExpr = (Expression<Location>) exprs[1];
        n = parseResult.hasTag("n");
        if (n){
            stringExpr = (Expression<String>) exprs[2];
        }
        return true;
    }
}

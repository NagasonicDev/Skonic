package au.nagasonic.skonic.modules.citizens.elements.effects;

import org.skriptlang.skript.registration.SyntaxRegistry;
import org.skriptlang.skript.registration.SyntaxInfo;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Citizen Rotate")
@Description("Rotates a Citizens NPC to face a location or entity.")
@RequiredPlugins("Citizens")
@Since("1.3")
@Examples("make npc with id 3 face player")
public class EffCitizenRotate extends Effect {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EFFECT,
            SyntaxInfo.builder(EffCitizenRotate.class)
                .addPatterns(
                    "make (citizen|npc) %npc% face %location%",
                    "make (citizen|npc) %npc% face %entity%"
                )
                .build()
        );
    }
    private Expression<NPC> npcExpr;
    private Expression<?> targetExpr;
    private int pattern;

    @Override
    protected void execute(Event event) {
        NPC npc = npcExpr.getSingle(event);
        if (npc == null || !npc.isSpawned() || npc.getEntity() == null) return;
        if (pattern == 0) {
            Location loc = (Location) targetExpr.getSingle(event);
            if (loc != null) {
                npc.faceLocation(loc);
            }
        } else {
            Entity entity = (Entity) targetExpr.getSingle(event);
            if (entity != null) {
                npc.faceLocation(entity.getLocation());
            }
        }
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "make " + npcExpr.toString(event, debug) + " face " + targetExpr.toString(event, debug);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        npcExpr = (Expression<NPC>) exprs[0];
        targetExpr = exprs[1];
        pattern = matchedPattern;
        return true;
    }
}

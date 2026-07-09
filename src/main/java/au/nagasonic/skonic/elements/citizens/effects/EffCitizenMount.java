package au.nagasonic.skonic.elements.citizens.effects;

import ch.njol.skript.Skript;
import org.skriptlang.skript.registration.SyntaxRegistry;
import org.skriptlang.skript.registration.SyntaxInfo;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.MountTrait;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Citizen Mount")
@Description("Makes a Citizens NPC mount or dismount an entity.")
@RequiredPlugins("Citizens")
@Since("1.3")
@Examples("make npc with id 3 mount player")
public class EffCitizenMount extends Effect {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EFFECT,
            SyntaxInfo.builder(EffCitizenMount.class)
                .addPatterns(
                    "make (citizen|npc) %npc% mount %entity%",
                    "make (citizen|npc) %npc% dismount"
                )
                .build()
        );
    }
    private Expression<NPC> npcExpr;
    private Expression<Entity> entityExpr;
    private int pattern;

    @Override
    protected void execute(Event event) {
        NPC npc = npcExpr.getSingle(event);
        if (npc == null) return;
        MountTrait mount = npc.getOrAddTrait(MountTrait.class);
        if (pattern == 0) {
            Entity entity = entityExpr.getSingle(event);
            if (entity != null) {
                mount.setMountedOn(entity.getUniqueId());
            }
        } else {
            mount.unmount();
        }
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        if (pattern == 0) {
            return "make " + npcExpr.toString(event, debug) + " mount " + entityExpr.toString(event, debug);
        }
        return "make " + npcExpr.toString(event, debug) + " dismount";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        npcExpr = (Expression<NPC>) exprs[0];
        pattern = matchedPattern;
        if (pattern == 0) entityExpr = (Expression<Entity>) exprs[1];
        return true;
    }
}

package au.nagasonic.skonic.modules.citizens.elements.conditions;

import org.skriptlang.skript.registration.SyntaxRegistry;
import org.skriptlang.skript.registration.SyntaxInfo;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Citizen Picks Up Items")
@Description("Whether a Citizens NPC picks up items.")
@Since("1.3")
@RequiredPlugins("Citizens")
public class CondCitizenPicksUpItems extends Condition {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.CONDITION,
            SyntaxInfo.builder(CondCitizenPicksUpItems.class)
                .addPatterns(
                    "(citizen|npc)[s] %npcs% (pick|picks) up items",
                    "(citizen|npc)[s] %npcs% (do[es]( not|n't)|do[es]n't) pick up items"
                )
                .build()
        );
    }
    private Expression<NPC> npcExpr;
    private int pattern;
    @Override
    public boolean check(Event event) {
        NPC[] npcs = npcExpr.getArray(event);
        if (npcs == null) return false;
        for (NPC npc : npcs) {
            if (npc == null) return false;
            boolean val = npc.data().get(NPC.Metadata.PICKUP_ITEMS, false);
            if (pattern == 0) {
                if (!val) return false;
            } else {
                if (val) return false;
            }
        }
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return npcExpr.toString(event, debug) + (pattern == 0 ? " picks up items" : " does not pick up items");
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        npcExpr = (Expression<NPC>) exprs[0];
        pattern = matchedPattern;
        return true;
    }
}

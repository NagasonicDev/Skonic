package au.nagasonic.skonic.modules.citizens.elements.expressions;

import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;
import org.skriptlang.skript.registration.SyntaxRegistry;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Citizen UUID")
@Description("The UUID of a Citizens NPC or its entity.")
@RequiredPlugins("Citizens")
@Since("1.3")
@Examples("set {_uuid} to uuid of npc with id 3")
public class ExprCitizenUUID extends SimpleExpression<Object> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EXPRESSION,
            DefaultSyntaxInfos.Expression.builder(ExprCitizenUUID.class, Object.class)
                .addPatterns(
                    "(citizen|npc) uuid of %npc%",
                    "%npc%'[s] (citizen|npc) uuid",
                    "(citizen|npc) (entity|minecraft) uuid of %npc%",
                    "%npc%'[s] (entity|minecraft) uuid"
                )
                .build()
        );
    }
    private Expression<NPC> npcExpr;
    private int pattern;

    @Override
    protected Object @Nullable [] get(Event event) {
        NPC npc = npcExpr.getSingle(event);
        if (npc == null) return null;
        if (pattern == 0 || pattern == 1) {
            return new Object[]{npc.getUniqueId()};
    } else {
            if (npc.isSpawned() && npc.getEntity() != null) {
                return new Object[]{npc.getEntity().getUniqueId()};
            }
            return null;
        }
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<?> getReturnType() {
        return Object.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "uuid of " + npcExpr.toString(event, debug);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        npcExpr = (Expression<NPC>) exprs[0];
        pattern = matchedPattern;
        return true;
    }
}

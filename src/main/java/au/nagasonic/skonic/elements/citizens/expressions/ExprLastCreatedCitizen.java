package au.nagasonic.skonic.elements.citizens.expressions;

import au.nagasonic.skonic.elements.citizens.effects.EffSpawnCitizen;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Last Created Citizen")
@Description("The last created citizen." +
        "Only applicable if the citizen is created via this addon. Will not save when server restarts.")
@RequiredPlugins("Citizens")
@Since("1.0.5")
@Examples({"spawn a zombie citizen named \"Undead\" at spawn", "set {_e} to last created npc"})
@DocumentationId("12506")
public class ExprLastCreatedCitizen extends SimpleExpression<NPC> {
    static {
        Skript.registerExpression(ExprLastCreatedCitizen.class,
                NPC.class,
                ExpressionType.SIMPLE,
                "last (spawned|created) (citizen|npc)");
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected @Nullable NPC[] get(Event event) {
        if (EffSpawnCitizen.lastSpawnedNPC != null){
            return new NPC[]{EffSpawnCitizen.lastSpawnedNPC};
        }else return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public Class<? extends NPC> getReturnType() {
        return NPC.class;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "last spawned citizen";
    }
}

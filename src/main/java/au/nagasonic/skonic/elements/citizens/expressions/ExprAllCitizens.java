package au.nagasonic.skonic.elements.citizens.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Name("All Citizens")
@Description("Expression of all citizens.")
@RequiredPlugins("Citizens")
@Since("1.0.7")
@Examples({"broadcast all citizens", "loop all citizens:", "\tbroadcast id of loop-value"})
@DocumentationId("12493")
public class ExprAllCitizens extends SimpleExpression<NPC> {
    static {
        Skript.registerExpression(ExprAllCitizens.class, NPC.class, ExpressionType.SIMPLE,
                "all (citizens|npcs)");
    }
    @Override
    protected @Nullable NPC[] get(Event event) {
        NPCRegistry registry = CitizensAPI.getNPCRegistry();
        List<NPC> npcs = new ArrayList<>();
        for (NPC npc : registry.sorted()){
            npcs.add(npc);
        }
        return npcs.toArray(new NPC[0]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends NPC> getReturnType() {
        return NPC.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return "all citizens";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }
}

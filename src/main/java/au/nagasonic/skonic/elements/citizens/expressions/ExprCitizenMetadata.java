package au.nagasonic.skonic.elements.citizens.expressions;

import au.nagasonic.skonic.Skonic;
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

@Name("Citizen Metadata")
@Description("The value of a Citizen's metadata.")
@RequiredPlugins("Citizens")
@Since("1.2.7")
public class ExprCitizenMetadata extends SimpleExpression<Object> {
    static {
        Skript.registerExpression(ExprCitizenMetadata.class, Object.class, ExpressionType.COMBINED,
                "%npcmetadata% (citizen|npc) ([meta]data) of %npc%",
                "(citizen|npc) ([meta]data) %npcmetadata% of %npc%");
    }
    private Expression<NPC.Metadata> metadataExpr;
    private Expression<NPC> npcExpr;

    @Override
    protected Object @Nullable [] get(Event event) {
        NPC npc = npcExpr.getSingle(event);
        if (npc != null){
            NPC.Metadata key = metadataExpr.getSingle(event);
            if (key != null){
                Object data = npc.data().get(key);
                if (data == null){
                    Skonic.logger().warn("null");
                }
                return new Object[]{data};
            }else{
                Skonic.logger().warn("Metadata does not exist.");
            }
        }else{
            Skonic.logger().warn("NPC is null.");
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return "Metadata " + metadataExpr.toString(event, b) + " of " + npcExpr.toString(event, b);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.metadataExpr = (Expression<NPC.Metadata>) exprs[0];
        this.npcExpr = (Expression<NPC>) exprs[1];
        return true;
    }
}

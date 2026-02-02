package au.nagasonic.skonic.elements.citizens.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Name("Citizen Nametag Visibility")
@Description("Whether a Citizens NPC's nameplate is visible.")
@Since("1.2.5")
@RequiredPlugins("Citizens")
@Examples("set npc nameplate visibility of all npcs to false")
public class ExprCitizenNameTagVisibility extends SimplePropertyExpression<NPC, Boolean> {
    static {
        register(ExprCitizenNameTagVisibility.class, Boolean.class, "(citizen|npc) name[ ][tag|plate] visibility", "npcs");
    }
    @Override
    public @Nullable Boolean convert(NPC npc) {
        return npc.data().get(NPC.Metadata.NAMEPLATE_VISIBLE);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public @Nullable Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) return CollectionUtils.array(Boolean.class);
        return null;
    }

    @SuppressWarnings({"NullableProblems", "ConstantValue"})
    @Override
    public void change(Event event, @Nullable Object[] delta, Changer.ChangeMode mode) {
        if (delta != null && delta[0] instanceof Boolean) {
            Boolean visible = (Boolean) delta[0];
            for (NPC npc : getExpr().getArray(event)) {
                npc.data().setPersistent(NPC.Metadata.NAMEPLATE_VISIBLE, visible);
                npc.scheduleUpdate(NPC.NPCUpdate.PACKET);
            }
        }
    }

    @Override
    @NotNull
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

    @Override
    @NotNull
    protected String getPropertyName() {
        return "citizen nametag visibility";
    }
}

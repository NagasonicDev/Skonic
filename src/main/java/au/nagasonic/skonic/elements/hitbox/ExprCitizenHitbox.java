package au.nagasonic.skonic.elements.hitbox;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Name("Citizen Hitbox")
@Description("The hitbox of a Citizens NPC")
@Examples("set {_box} to hitbox of npc with id 3")
@Since("1.2.2-b1")
@RequiredPlugins("Citizens")
public class ExprCitizenHitbox extends SimplePropertyExpression<NPC, NPCHitbox> {
    static {
        register(ExprCitizenHitbox.class, NPCHitbox.class, "(citizen|npc) hitbox", "npc");
    }
    @Override
    public @Nullable NPCHitbox convert(NPC npc) {
        return NPCHitbox.fromNPC(npc);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public @Nullable Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) return CollectionUtils.array(NPCHitbox.class);
        return null;
    }

    @SuppressWarnings({"NullableProblems", "ConstantValue"})
    @Override
    public void change(Event event, @Nullable Object[] delta, Changer.ChangeMode mode) {
        if (delta != null && delta[0] instanceof NPCHitbox) {
            NPCHitbox box = (NPCHitbox) delta[0];
            NPC npc = getExpr().getSingle(event);
            if (npc != null){
                box.setHitbox(npc);
            }
        }
    }

    @Override
    @NotNull
    public Class<? extends NPCHitbox> getReturnType() {
        return NPCHitbox.class;
    }

    @Override
    @NotNull
    protected String getPropertyName() {
        return "citizen hitbox";
    }
}

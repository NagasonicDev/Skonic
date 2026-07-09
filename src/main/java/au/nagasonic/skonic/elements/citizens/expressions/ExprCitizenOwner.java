package au.nagasonic.skonic.elements.citizens.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Owner;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

import org.skriptlang.skript.registration.SyntaxRegistry;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;

@Name("Citizen Owner (UUID)")
@Description("The 'owner' of a Citizens NPC, if null, then it is owned by the server, else a player.")
@Since("1.2.8")
@RequiredPlugins("Citizens")
public class ExprCitizenOwner extends SimplePropertyExpression<NPC, UUID> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EXPRESSION,
            DefaultSyntaxInfos.Expression.builder(ExprCitizenOwner.class, UUID.class)
                .addPatterns(
                    "(citizen|npc) owner [uuid] of %npcs%",
                    "%npcs%'[s] (citizen|npc) owner [uuid]"
                )
                .build()
        );
    }
    @Override
    public @Nullable UUID convert(NPC npc) {
        Owner trait = npc.getOrAddTrait(Owner.class);
        return trait.getOwnerId();
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public @Nullable Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) return CollectionUtils.array(UUID.class);
        return null;
    }

    @SuppressWarnings({"NullableProblems", "ConstantValue"})
    @Override
    public void change(Event event, @Nullable Object[] delta, Changer.ChangeMode mode) {
        UUID uuid = null;
        if (delta != null && delta[0] instanceof UUID d) {
            uuid = d;
        }
        for (NPC npc : getExpr().getArray(event)) {
            Owner trait = npc.getOrAddTrait(Owner.class);
            trait.setOwner(uuid);
        }
    }

    @Override
    @NotNull
    public Class<? extends UUID> getReturnType() {
        return UUID.class;
    }

    @Override
    @NotNull
    protected String getPropertyName() {
        return "citizen owner uuid";
    }
}

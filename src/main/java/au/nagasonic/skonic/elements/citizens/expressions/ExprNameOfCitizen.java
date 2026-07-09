package au.nagasonic.skonic.elements.citizens.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import org.skriptlang.skript.registration.SyntaxRegistry;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;
@Name("Citizen Name")
@Description("Get the name of a citizen")
@RequiredPlugins("Citizens")
@Since("1.0.5")
@Examples("set citizen name of last created npc to \"Nagasonic\"")
public class ExprNameOfCitizen extends SimplePropertyExpression<NPC, String> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EXPRESSION,
            DefaultSyntaxInfos.Expression.builder(ExprNameOfCitizen.class, String.class)
                .addPatterns(
                    "(citizen|npc) name of %npcs%",
                    "%npcs%'[s] (citizen|npc) name"
                )
                .build()
        );
    }
    @Override
    public @Nullable String convert(NPC npc) {
        return npc.getName();
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public @Nullable Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) return CollectionUtils.array(String.class);
        return null;
    }

    @SuppressWarnings({"NullableProblems", "ConstantValue"})
    @Override
    public void change(Event event, @Nullable Object[] delta, Changer.ChangeMode mode) {
        if (delta != null && delta[0] instanceof String) {
            String name = (String) delta[0];
            for (NPC npc : getExpr().getArray(event)) {
                npc.setName(name);
                Location loc = npc.getEntity().getLocation();
                npc.despawn();
                npc.spawn(loc);
            }
        }
    }

    @Override
    @NotNull
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    @NotNull
    protected String getPropertyName() {
        return "citizen name";
    }
}

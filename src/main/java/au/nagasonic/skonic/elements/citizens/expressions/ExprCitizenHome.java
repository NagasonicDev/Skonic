package au.nagasonic.skonic.elements.citizens.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.HomeTrait;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.SyntaxRegistry;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;

@Name("Citizen Home Location")
@Description("The home location of a Citizens NPC.")
@RequiredPlugins("Citizens")
@Since("1.3")
@Examples("set home of {_npc} to location(0, 0, 0, world \"world\")")
public class ExprCitizenHome extends SimplePropertyExpression<NPC, Location> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EXPRESSION,
            DefaultSyntaxInfos.Expression.builder(ExprCitizenHome.class, Location.class)
                .addPatterns(
                    "(citizen|npc) home [location] of %npcs%",
                    "%npcs%'[s] (citizen|npc) home [location]"
                )
                .build()
        );
    }
    @Override
    public @Nullable Location convert(NPC npc) {
        HomeTrait home = npc.getOrAddTrait(HomeTrait.class);
        if (home.getHomeLocation() != null) return home.getHomeLocation().clone();
        return null;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public @Nullable Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) return CollectionUtils.array(Location.class);
        return null;
    }

    @SuppressWarnings({"NullableProblems", "ConstantValue"})
    @Override
    public void change(Event event, @Nullable Object[] delta, Changer.ChangeMode mode) {
        if (delta != null && delta[0] instanceof Location) {
            Location loc = (Location) delta[0];
            for (NPC npc : getExpr().getArray(event)) {
                npc.getOrAddTrait(HomeTrait.class).setHomeLocation(loc);
            }
        }
    }

    @Override
    @NotNull
    public Class<? extends Location> getReturnType() {
        return Location.class;
    }

    @Override
    @NotNull
    protected String getPropertyName() {
        return "citizen home location";
    }
}

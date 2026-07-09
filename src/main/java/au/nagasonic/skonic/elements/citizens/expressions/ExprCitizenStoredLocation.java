package au.nagasonic.skonic.elements.citizens.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.CurrentLocation;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.skriptlang.skript.registration.SyntaxRegistry;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;
@Name("Citizen Stored Location")
@Description("The stored (current) location of a Citizens NPC.")
@RequiredPlugins("Citizens")
@Since("1.3")
@Examples("set {_loc} to stored location of {_npc}")
public class ExprCitizenStoredLocation extends SimplePropertyExpression<NPC, Location> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EXPRESSION,
            DefaultSyntaxInfos.Expression.builder(ExprCitizenStoredLocation.class, Location.class)
                .addPatterns(
                    "(citizen|npc) stored location of %npcs%",
                    "%npcs%'[s] (citizen|npc) stored location"
                )
                .build()
        );
    }
    @Override
    public @Nullable Location convert(NPC npc) {
        CurrentLocation loc = npc.getOrAddTrait(CurrentLocation.class);
        if (loc.getLocation() != null) return loc.getLocation().clone();
        return null;
    }

    @Override
    @NotNull
    public Class<? extends Location> getReturnType() {
        return Location.class;
    }

    @Override
    @NotNull
    protected String getPropertyName() {
        return "citizen stored location";
    }
}

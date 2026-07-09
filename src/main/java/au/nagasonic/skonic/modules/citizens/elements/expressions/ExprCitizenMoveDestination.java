package au.nagasonic.skonic.modules.citizens.elements.expressions;

import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.SyntaxRegistry;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;

@Name("Citizen Move Destination")
@Description("The navigation target location of a Citizens NPC.")
@RequiredPlugins("Citizens")
@Since("1.3")
@Examples("set {_dest} to move destination of {_npc}")
public class ExprCitizenMoveDestination extends SimplePropertyExpression<NPC, Location> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EXPRESSION,
            DefaultSyntaxInfos.Expression.builder(ExprCitizenMoveDestination.class, Location.class)
                .addPatterns(
                    "(citizen|npc) move destination of %npcs%",
                    "%npcs%'[s] (citizen|npc) move destination"
                )
                .build()
        );
    }
    @Override
    public @Nullable Location convert(NPC npc) {
        if (npc.getNavigator().isNavigating()) {
            return npc.getNavigator().getTargetAsLocation();
        }
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
        return "citizen move destination";
    }
}

package au.nagasonic.skonic.modules.citizens.elements.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.SyntaxRegistry;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;

@Name("Citizen Skin Name")
@Description("The skin name of a Citizens NPC.")
@Since("1.2.2-b2")
@RequiredPlugins("Citizens")
@Examples("set citizen skin name of {_npc} to \"Nagasonic\"")
public class ExprCitizenSkinName extends SimplePropertyExpression<NPC, String> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EXPRESSION,
            DefaultSyntaxInfos.Expression.builder(ExprCitizenSkinName.class, String.class)
                .addPatterns(
                    "(citizen|npc) skin name of %npcs%",
                    "%npcs%'[s] (citizen|npc) skin name"
                )
                .build()
        );
    }
    @Override
    public @Nullable String convert(NPC npc) {
        SkinTrait trait = npc.getOrAddTrait(SkinTrait.class);
        return trait.getSkinName();
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
                SkinTrait trait = npc.getOrAddTrait(SkinTrait.class);
                trait.setSkinName(name);
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
        return "citizen skin name";
    }
}

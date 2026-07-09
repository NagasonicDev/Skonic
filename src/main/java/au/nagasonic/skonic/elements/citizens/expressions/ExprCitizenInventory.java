package au.nagasonic.skonic.elements.citizens.expressions;

import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.SyntaxRegistry;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;

@Name("Citizen Inventory")
@Description("The inventory of a Citizens NPC.")
@RequiredPlugins("Citizens")
@Since("1.3")
@Examples("set slot 0 of npc inventory of {_npc} to diamond sword")
public class ExprCitizenInventory extends SimplePropertyExpression<NPC, org.bukkit.inventory.Inventory> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EXPRESSION,
            DefaultSyntaxInfos.Expression.builder(ExprCitizenInventory.class, org.bukkit.inventory.Inventory.class)
                .addPatterns(
                    "(citizen|npc) inventory of %npcs%",
                    "%npcs%'[s] (citizen|npc) inventory"
                )
                .build()
        );
    }
    @Override
    public @Nullable org.bukkit.inventory.Inventory convert(NPC npc) {
        Inventory inv = npc.getOrAddTrait(Inventory.class);
        return inv.getInventoryView();
    }

    @Override
    @NotNull
    public Class<? extends org.bukkit.inventory.Inventory> getReturnType() {
        return org.bukkit.inventory.Inventory.class;
    }

    @Override
    @NotNull
    protected String getPropertyName() {
        return "citizen inventory";
    }
}

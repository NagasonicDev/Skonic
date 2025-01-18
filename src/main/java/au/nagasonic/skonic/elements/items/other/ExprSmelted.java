package au.nagasonic.skonic.elements.items.other;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

@Name("Smelted Form")
@Description({"Returns the smelted form of an itemtype.", "If it cannot be smelted, it will return the same itemtype."})
public class ExprSmelted extends SimplePropertyExpression<ItemType, ItemType> {
    static {
        Skript.registerExpression(ExprSmelted.class, ItemType.class, ExpressionType.PROPERTY,
                "smelt[ed] [(result|form) of] %itemtypes%",
                "%itemtypes%'[s] smelted [(result|form)]",
                "%itemtypes% smelted");
    }

    @SuppressWarnings("deprecation")
    private ItemStack smelted(ItemStack item) {
        Material type = item.getType();
        for (@NotNull Iterator<Recipe> it = Bukkit.recipeIterator(); it.hasNext(); ) {
            if (it.next() instanceof FurnaceRecipe recipe){
                Material ingredient = recipe.getInput().getType();
                if (type == ingredient){
                    ItemStack result = item.clone();
                    result.setType(recipe.getResult().getType());
                    return result;
                }
            }
        }
        return item;
    }

    @Override
    public @NotNull Class<? extends ItemType> getReturnType() {
        return ItemType.class;
    }

    @Override
    protected @NotNull String getPropertyName() {
        return "smelted form";
    }

    @Nullable
    @Override
    public ItemType convert(ItemType itemType) {
        return new ItemType(smelted(itemType.getRandom()));
    }
}

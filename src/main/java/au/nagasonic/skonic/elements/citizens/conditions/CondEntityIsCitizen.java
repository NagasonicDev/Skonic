package au.nagasonic.skonic.elements.citizens.conditions;

import au.nagasonic.skonic.Skonic;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

@Name("Entity Is a Citizen")
@Description("Whether an entity is a Citizens NPC.")
@Since("1.2.1-b2")
@RequiredPlugins("Citizens")
public class CondEntityIsCitizen extends Condition {
    static {
        Skript.registerCondition(CondEntityIsCitizen.class,
                "%livingentities% (is|are) [a[n]] (citizen|npc)[s]",
                "%livingentities% (is(n't| not)|are(n't|not)) [a[n]] (citizen|npc)[s]");
    }
    private Expression<LivingEntity> entitiesExpr;
    private int pattern;
    @Override
    public boolean check(Event event) {
        if (entitiesExpr == null){
            Skonic.log(Level.SEVERE, "Entity/ies are null");
            return false;
        }
        LivingEntity[] entities = entitiesExpr.getArray(event);
        if (entitiesExpr == null){
            Skonic.log(Level.SEVERE, "Entity/ies are null");
            return false;
        }
        if (pattern == 0){
            for (LivingEntity entity : entities) {
                if (entity.hasMetadata("NPC") == false) return false;
            }
        }else{
            for (LivingEntity entity : entities) {
                if (entity.hasMetadata("NPC") == true) return false;
            }
        }
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return null;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.entitiesExpr = (Expression<LivingEntity>) exprs[0];
        this.pattern = matchedPattern;
        return true;
    }
}

package au.nagasonic.skonic.elements.citizens.expressions;

import au.nagasonic.skonic.elements.citizens.Forcefield;
import au.nagasonic.skonic.elements.citizens.events.CitizenForcefieldCreateEvent;
import ch.njol.skript.Skript;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.expressions.base.SectionExpression;
import ch.njol.skript.lang.*;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;


import java.util.List;

public class ExprForcefield extends SectionExpression<Forcefield> {
    static {
        Skript.registerExpression(ExprForcefield.class, Forcefield.class, ExpressionType.COMBINED,
                "[a] forcefield [w:with width %number%] [h:with height %number%] [s:with strength %number%] [v:with vertical strength %number%]");
        EventValues.registerEventValue(CitizenForcefieldCreateEvent.class, Forcefield.class, CitizenForcefieldCreateEvent::getForcefield);
    }
    private Trigger trigger;
    private Expression<Number> widthExpr;
    private Expression<Number> heightExpr;
    private Expression<Number> strengthExpr;
    private Expression<Number> vertStrengthExpr;

    @Override
    public boolean init(Expression<?>[] exprs, int pattern, Kleenean delayed, SkriptParser.ParseResult result, @Nullable SectionNode node, @Nullable List<TriggerItem> triggerItems) {
        if (node != null){
            trigger = loadCode(node, "create forcefield", null, CitizenForcefieldCreateEvent.class);
        }
        widthExpr = (Expression<Number>) exprs[0];
        heightExpr = (Expression<Number>) exprs[1];
        strengthExpr = (Expression<Number>) exprs[2];
        vertStrengthExpr = (Expression<Number>) exprs[3];
        return true;
    }

    @Override
    protected Forcefield @Nullable [] get(Event event) {
        Forcefield forcefield = new Forcefield(
                this.widthExpr.getSingle(event).doubleValue(),
                this.heightExpr.getSingle(event).doubleValue(),
                this.strengthExpr.getSingle(event).doubleValue(),
                this.vertStrengthExpr.getSingle(event).doubleValue()
        );
        if (trigger != null){
            CitizenForcefieldCreateEvent createEvent = new CitizenForcefieldCreateEvent(forcefield);
            Variables.withLocalVariables(event, createEvent, () ->
                    TriggerItem.walk(trigger, createEvent)
            );
        }
        return new Forcefield[]{forcefield};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Forcefield> getReturnType() {
        return Forcefield.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "forcefield with width: " + widthExpr.toString(event, debug) + " with height: " + heightExpr.toString(event, debug) + " with strength: " + strengthExpr.toString(event, debug) + " with vertical strength: " + vertStrengthExpr.toString(event, debug);
    }
}

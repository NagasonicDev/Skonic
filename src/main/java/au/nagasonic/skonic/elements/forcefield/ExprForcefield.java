package au.nagasonic.skonic.elements.forcefield;

import ch.njol.skript.Skript;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SectionExpression;
import ch.njol.skript.lang.*;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;


import java.util.List;

@Name("Forcefield - Create")
@Description("Expression to create a Citizens Forcefield. Default values are 1.")
@Since("1.2.1-b1")
@Examples({"set {_force} to forcefield:", "\tset forcefield width to 3", "\tset forcefield height to 4", "\tset forcefield strength to 1", "\tset forcefield vertical strength to 2", "", "set {_field} to a forcefield with width 2"})
@RequiredPlugins("Citizens")
public class ExprForcefield extends SectionExpression<NPCForcefield> {
    static {
        Skript.registerExpression(ExprForcefield.class, NPCForcefield.class, ExpressionType.COMBINED,
                "[a] forcefield [with width %number%[,| and]] [[with] height %number%[,| and]] [[with] strength %number%[,| and]] [[with] vertical strength %number%]");
        EventValues.registerEventValue(CitizenForcefieldCreateEvent.class, NPCForcefield.class, CitizenForcefieldCreateEvent::getForcefield);
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
    protected NPCForcefield @Nullable [] get(Event event) {
        NPCForcefield forcefield = new NPCForcefield(
                this.widthExpr.getSingle(event) == null ? 1 : this.widthExpr.getSingle(event).doubleValue(),
                this.heightExpr.getSingle(event) == null ? 1 : this.heightExpr.getSingle(event).doubleValue(),
                this.strengthExpr.getSingle(event) == null ? 1 : this.strengthExpr.getSingle(event).doubleValue(),
                this.vertStrengthExpr.getSingle(event) == null ? 1 : this.vertStrengthExpr.getSingle(event).doubleValue()
        );
        if (trigger != null){
            CitizenForcefieldCreateEvent createEvent = new CitizenForcefieldCreateEvent(forcefield);
            Variables.withLocalVariables(event, createEvent, () ->
                    TriggerItem.walk(trigger, createEvent)
            );
        }
        return new NPCForcefield[]{forcefield};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends NPCForcefield> getReturnType() {
        return NPCForcefield.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "forcefield with width: " + widthExpr.toString(event, debug) + " with height: " + heightExpr.toString(event, debug) + " with strength: " + strengthExpr.toString(event, debug) + " with vertical strength: " + vertStrengthExpr.toString(event, debug);
    }
}

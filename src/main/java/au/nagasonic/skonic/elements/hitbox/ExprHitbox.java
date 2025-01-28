package au.nagasonic.skonic.elements.hitbox;

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

@Name("Hitbox With")
@Description({"A Citizen Hitbox with the given components.", "Scale is a multiplier for the hitbox. For example, if the width was 1 and the height was 2, then if the scale was 2, the hitbox's effective dimenstions would be 2 width and 4 height.", "Default values are:", "- Scale: 1", "- Width: 1", "- Height: 2"})
@Since("1.2.2-b1")
@Examples({"set {_hitbox} to a hitbox with scale 1, width 2 and height 4", "", "set {_box} to a hitbox:", "\tset hitbox scale to 1", "\tset hitbox width to 1", "\tset hitbox height to 2"})
@RequiredPlugins("Citizens")
public class ExprHitbox extends SectionExpression<NPCHitbox> {
    static {
        Skript.registerExpression(ExprHitbox.class, NPCHitbox.class, ExpressionType.COMBINED,
                "[a] hitbox [with scale %number%[,| and]] [[with] width %number%[,| and]] [[with] height %number%]");
        EventValues.registerEventValue(CitizenHitboxCreateEvent.class, NPCHitbox.class, CitizenHitboxCreateEvent::getHitbox);
    }
    private Trigger trigger;
    private Expression<Number> scaleExpr;
    private Expression<Number> widthExpr;
    private Expression<Number> heightExpr;


    @Override
    public boolean init(Expression<?>[] exprs, int pattern, Kleenean delayed, SkriptParser.ParseResult result, @Nullable SectionNode node, @Nullable List<TriggerItem> triggerItems) {
        if (node != null){
            trigger = loadCode(node, "create hitbox", null, CitizenHitboxCreateEvent.class);
        }
        scaleExpr = (Expression<Number>) exprs[0];
        widthExpr = (Expression<Number>) exprs[1];
        heightExpr = (Expression<Number>) exprs[2];

        return true;
    }

    @Override
    protected NPCHitbox @Nullable [] get(Event event) {
        NPCHitbox hitbox = new NPCHitbox(
                this.scaleExpr.getSingle(event) == null ? 1 : this.scaleExpr.getSingle(event).floatValue(),
                this.widthExpr.getSingle(event) == null ? 1 : this.widthExpr.getSingle(event).floatValue(),
                this.heightExpr.getSingle(event) == null ? 2 : this.heightExpr.getSingle(event).floatValue()
        );
        if (trigger != null){
            CitizenHitboxCreateEvent createEvent = new CitizenHitboxCreateEvent(hitbox);
            Variables.withLocalVariables(event, createEvent, () ->
                    TriggerItem.walk(trigger, createEvent)
            );
        }
        return new NPCHitbox[]{hitbox};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends NPCHitbox> getReturnType() {
        return NPCHitbox.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "hitbox with scale: " + scaleExpr.toString(event, debug) + " with width: " + widthExpr.toString(event, debug) + " with height: " + heightExpr.toString(event, debug);
    }
}

package au.nagasonic.skonic.classes.citizens.hitbox;

import au.nagasonic.skonic.elements.hitbox.NPCHitbox;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;

public class HitboxType {
    static {
        Classes.registerClass(new ClassInfo<>(NPCHitbox.class, "npchitbox")
                .user("npc ?hitboxes?")
                .name("Citizen Hitbox")
                .description("Represesnts a Citizens Hitbox")
                .examples("set {_force} to hitbox with scale 4 with height 2 with width 1")
                .requiredPlugins("Citizens")
                .since("1.2.2")
                .defaultExpression(new EventValueExpression<>(NPCHitbox.class))
                .parser(new Parser<NPCHitbox>() {
                    @SuppressWarnings("NullableProblems")
                    @Override
                    public boolean canParse(ParseContext context) {
                        return false;
                    }
                    @Override
                    public String toString(NPCHitbox o, int flags) {
                        return o.toString();
                    }

                    @Override
                    public String toVariableNameString(NPCHitbox o) {
                        return "npchitbox:" + toString(o, 0);
                    }
                }));
    }
}

package au.nagasonic.skonic.classes.citizens.forcefield;

import au.nagasonic.skonic.elements.forcefield.NPCForcefield;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;

public class ForcefieldType {
    static {
        Classes.registerClass(new ClassInfo<>(NPCForcefield.class, "npcforcefield")
                .user("npc ?forcefields?")
                .name("Citizen Forcefield")
                .description("Represesnts a Citizens Forcefield")
                .examples("set {_force} to forcefield with 5 width with 3 height")
                .requiredPlugins("Citizens")
                .since("1.2.1-b1")
                .defaultExpression(new EventValueExpression<>(NPCForcefield.class))
                .parser(new Parser<NPCForcefield>() {
                    @SuppressWarnings("NullableProblems")
                    @Override
                    public boolean canParse(ParseContext context) {
                        return false;
                    }
                    @Override
                    public String toString(NPCForcefield o, int flags) {
                        return o.toString();
                    }

                    @Override
                    public String toVariableNameString(NPCForcefield o) {
                        return "npcforcefield:" + toString(o, 0);
                    }
                }));
    }
}

package au.nagasonic.skonic.classes.other;

import au.nagasonic.skonic.elements.skins.Skin;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;

@SuppressWarnings({"unused", "deprecation"})
public class Types {
    static {
        Classes.registerClass(new ClassInfo<>(Skin.class, "skin")
                .user("skin?")
                .name("Skin")
                .description("Represents a Skin.")
                .examples("player's skin")
                .since("1.0.2")
                .parser(new Parser<>() {
                    @SuppressWarnings("NullableProblems")
                    @Override
                    public boolean canParse(ParseContext context) {
                        return false;
                    }
                    @Override
                    public String toString(Skin skin, int flags) {
                        return "skin with value " + skin.value + " and signature " + skin.signature;
                    }

                    @Override
                    public String toVariableNameString(Skin skin) {
                        return "skin: " + skin.toString();
                    }
                }));

    }
}

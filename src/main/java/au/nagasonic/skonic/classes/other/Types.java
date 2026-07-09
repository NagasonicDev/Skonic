package au.nagasonic.skonic.classes.other;

import au.nagasonic.skonic.Skonic;
import au.nagasonic.skonic.modules.skins.elements.Skin;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.util.EnumUtils;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.lang.properties.Property;
import org.skriptlang.skript.lang.properties.handlers.TypedValueHandler;
import org.skriptlang.skript.registration.SyntaxRegistry;

@SuppressWarnings({"unused", "deprecation"})
public class Types {
    public static void register(SyntaxRegistry syntaxRegistry) {
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
                        return "skin with value " + skin.getTexture() + " and signature " + skin.getSignature();
                    }

                    @Override
                    public String toVariableNameString(Skin skin) {
                        return "skin: " + skin.toString();
                    }
                })
                .property(Property.TYPED_VALUE,
                        "The skin value of the skin.",
                        Skonic.getAddonLoader().getAddon(),
                        new TypedValueHandler<Skin, String>() {
                            @Override
                            public @Nullable String convert(Skin propertyHolder) {
                                if (propertyHolder != null)
                                    return propertyHolder.getTexture();
                                return null;
                            }

                            @Override
                            public @NotNull Class<String> returnType() {
                                return String.class;
                            }
                        }));
        EnumUtils<ChatColor> CHAT_COLOR_ENUM = new EnumUtils<>(ChatColor.class, "chatcolors");
        Classes.registerClass(new ClassInfo<>(ChatColor.class, "chatcolor")
                .user("chat ?colou?rs?")
                .name("Chat Color")
                .description("Represents a Chat Color, different from Skript's Color class.", "Although not a Citizens type, the only current use is in the Citizen Glow effect.")
                .usage(CHAT_COLOR_ENUM.getAllNames())
                .examples("dark gray")
                .since("1.2.2-b1")
                .parser(new Parser<ChatColor>() {
                    @SuppressWarnings("NullableProblems")
                    @Override
                    public @Nullable ChatColor parse(String s, ParseContext context) {
                        return CHAT_COLOR_ENUM.parse(s);
                    }
                    @Override
                    public String toString(ChatColor o, int flags) {
                        return CHAT_COLOR_ENUM.toString(o, flags);
                    }

                    @Override
                    public String toVariableNameString(ChatColor o) {
                        return "chat color: " + toString(o, 0);
                    }
                }));
    }
}

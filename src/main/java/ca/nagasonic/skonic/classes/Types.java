package ca.nagasonic.skonic.classes;

import ca.nagasonic.skonic.elements.skins.Skin;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.util.coll.CollectionUtils;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.lang.converter.Converters;

@SuppressWarnings({"unused", "deprecation"})
public class Types {
    static {
        Classes.registerClass(new ClassInfo<>(NPC.class, "npc")
                .user("npcs?")
                .name("Citizens NPC")
                .description("Represents a Citizens NPC.")
                .examples("last spawned npc", "delete last spawned npc")
                .since("1.0.0")
                .documentationId("npc")
                .parser(new Parser<NPC>() {
                    @SuppressWarnings("NullableProblems")
                    @Override
                    public boolean canParse(ParseContext context) {
                        return false;
                    }

                    @Override
                    @NotNull
                    public String toString(NPC npc, int flags) {
                        return "citizen with id " + npc.getId();
                    }

                    @Override
                    @NotNull
                    public String toVariableNameString(NPC npc) {
                        return "citizen:" + npc.getId();
                    }
                })
                .changer(new Changer<NPC>() {
                    @SuppressWarnings("NullableProblems")
                    @Override
                    public @Nullable Class<?>[] acceptChange(ChangeMode mode) {
                        if (mode == ChangeMode.DELETE) return CollectionUtils.array();
                        return null;
                    }

                    @SuppressWarnings("NullableProblems")
                    @Override
                    public void change(NPC[] what, @Nullable Object[] delta, Changer.ChangeMode mode) {
                        if (mode == ChangeMode.DELETE) {
                            for (NPC npc : what) {
                                npc.destroy();
                            }
                        }
                    }
                }));
        Classes.registerClass(new ClassInfo<>(Skin.class, "skin")
                .user("skin?")
                .name("Skin")
                .description("Represents a Skin.")
                .examples("player's skin")
                .since("1.0.2")
                .documentationId("skin")
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
                        return "skin:" + skin.toString();
                    }
                }));


        Converters.registerConverter(NPC.class, Entity.class, NPC::getEntity);
    }
}

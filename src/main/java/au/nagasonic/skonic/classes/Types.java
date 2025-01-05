package au.nagasonic.skonic.classes;

import au.nagasonic.skonic.elements.forcefield.Forcefield;
import au.nagasonic.skonic.elements.skins.Skin;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.EnumSerializer;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.util.EnumUtils;
import ch.njol.util.coll.CollectionUtils;
import net.citizensnpcs.api.event.DespawnReason;
import net.citizensnpcs.api.event.SpawnReason;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.EntityPoseTrait.EntityPose;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.lang.converter.Converters;

@SuppressWarnings({"unused", "deprecation"})
public class Types {
    static {
        if (Bukkit.getPluginManager().isPluginEnabled("Citizens")){
            Classes.registerClass(new ClassInfo<>(NPC.class, "npc")
                    .user("npcs?")
                    .name("Citizens NPC")
                    .description("Represents a Citizens NPC.")
                    .examples("last spawned npc", "delete last spawned npc")
                    .since("1.0.0")
                    .requiredPlugins("Citizens")
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
            EnumUtils<SpawnReason> SPAWN_REASON_ENUM = new EnumUtils<>(SpawnReason.class, "npcspawnreasons");
            Classes.registerClass(new ClassInfo<>(SpawnReason.class, "npcspawnreason")
                    .user("npc ?spawn ?reasons?")
                    .name("Citizens Spawn Reason")
                    .description("Represents the reasons a Citizen will spawn.")
                    .usage(SPAWN_REASON_ENUM.getAllNames().replace("spawnreasons.", ""))
                    .examples("if event-npcspawnreason = chunk load:")
                    .since("1.1")
                    .requiredPlugins("Citizens")
                    .parser(new Parser<SpawnReason>() {

                        @SuppressWarnings("NullableProblems")
                        @Override
                        public @Nullable SpawnReason parse(String string, ParseContext context) {
                            return SPAWN_REASON_ENUM.parse(string);
                        }

                        @Override
                        @NotNull
                        public String toString(SpawnReason spawnReason, int flags) {
                            return SPAWN_REASON_ENUM.toString(spawnReason, flags);
                        }

                        @Override
                        @NotNull
                        public String toVariableNameString(SpawnReason spawnReason) {
                            return "npcspawnreason:" + toString(spawnReason, 0);
                        }
                    })
                    .serializer(new EnumSerializer<>(SpawnReason.class)));

            EnumUtils<DespawnReason> DESPAWN_REASON_ENUM = new EnumUtils<>(DespawnReason.class, "npcdespawnreasons");
            Classes.registerClass(new ClassInfo<>(DespawnReason.class, "npcdespawnreason")
                    .user("npc ?despawn ?reasons?")
                    .name("Citizens Despawn Reason")
                    .description("Represents the reasons a Citizen will despawn.")
                    .usage(DESPAWN_REASON_ENUM.getAllNames().replace("despawnreasons.", ""))
                    .examples("if event-npcdespawnreason = chunk unload:")
                    .requiredPlugins("Citizens")
                    .since("1.1")
                    .parser(new Parser<DespawnReason>() {

                        @SuppressWarnings("NullableProblems")
                        @Override
                        public @Nullable DespawnReason parse(String s, ParseContext context) {
                            return DESPAWN_REASON_ENUM.parse(s);
                        }

                        @Override
                        @NotNull
                        public String toString(DespawnReason despawnReason, int flags) {
                            return DESPAWN_REASON_ENUM.toString(despawnReason, flags);
                        }

                        @Override
                        @NotNull
                        public String toVariableNameString(DespawnReason despawnReason) {
                            return "npcdespawnreason:" + toString(despawnReason, 0);
                        }
                    })
                    .serializer(new EnumSerializer<>(DespawnReason.class)));
            EnumUtils<EntityPose> ENTITY_POSE_ENUM = new EnumUtils<>(EntityPose.class, "npcentityposes");
            Classes.registerClass(new ClassInfo<>(EntityPose.class, "npcentitypose")
                    .user("npc ?entity ?poses?")
                    .name("Citizens Entity Pose")
                    .description("Represents the 'pose' of a Citizens NPC.", "Essentially different animations, depending on the entity type of the NPC.")
                    .usage(ENTITY_POSE_ENUM.getAllNames())
                    .examples("if entity pose of {_npc} is crouch:")
                    .requiredPlugins("Citizens")
                    .since("1.2.1")
                    .parser(new Parser<EntityPose>() {

                        @SuppressWarnings("NullableProblems")
                        @Override
                        public @Nullable EntityPose parse(String s, ParseContext context) {
                            return ENTITY_POSE_ENUM.parse(s);
                        }
                        @Override
                        @NotNull
                        public String toString(EntityPose o, int flags) {
                            return ENTITY_POSE_ENUM.toString(o, flags);
                        }

                        @Override
                        @NotNull
                        public String toVariableNameString(EntityPose o) {
                            return "npcentitypose:" + toString(o, 0);
                        }
                    })
                    .serializer(new EnumSerializer<>(EntityPose.class)));
            Classes.registerClass(new ClassInfo<>(Forcefield.class, "npcforcefield")
                    .user("npcforcefield?")
                    .name("Citizen Forcefield")
                    .description("Represesnts a Citizens Forcefield")
                    .examples("set {_force} to forcefield with 5 width with 3 height")
                    .requiredPlugins("Citizens")
                    .since("1.2.1")
                    .defaultExpression(new EventValueExpression<>(Forcefield.class))
                    .parser(new Parser<Forcefield>() {
                        @SuppressWarnings("NullableProblems")
                        @Override
                        public boolean canParse(ParseContext context) {
                            return false;
                        }
                        @Override
                        public String toString(Forcefield o, int flags) {
                            return o.toString();
                        }

                        @Override
                        public String toVariableNameString(Forcefield o) {
                            return "npcforcefield:" + toString(o, 0);
                        }
                    }));

            // CONVERTERS
            // Enables any Skript effect/expression that works for entities
            Converters.registerConverter(NPC.class, Entity.class, NPC::getEntity);
        }
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

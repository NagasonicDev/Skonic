package au.nagasonic.skonic.elements.citizens.events;

import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import net.citizensnpcs.api.event.*;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.SyntaxRegistry;
import org.skriptlang.skript.bukkit.registration.BukkitSyntaxInfos;

@SuppressWarnings("unused")
public class CitizenEvents extends SimpleEvent {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
                BukkitSyntaxInfos.Event.KEY,
                BukkitSyntaxInfos.Event.builder(CitizenEvents.class, "Citizen Click")
                        .addPatterns("(citizen|npc) click")
                        .addEvent(NPCClickEvent.class)
                        .addDescription("Called when a Citizens NPC is clicked by a player.")
                        .addSince("1.1")
                        .addRequiredPlugin("Citizens")
                        .addExamples("on citizen click:", "\tsend \"You clicked %event-npc%\" to event-player")
                        .build()
        );
        syntaxRegistry.register(
                BukkitSyntaxInfos.Event.KEY,
                BukkitSyntaxInfos.Event.builder(CitizenEvents.class, "Citizen Left Click")
                        .addPatterns("(citizen|npc) left click")
                        .addEvent(NPCLeftClickEvent.class)
                        .addDescription("Called when a Citizens NPC is left clicked by a player.", "Only is called if NPC is not vulnerable.")
                        .addSince("1.1")
                        .addRequiredPlugin("Citizens")
                        .addExamples("on citizen left click:", "\tsend \"You left clicked %event-npc%\" to event-player")
                        .build()
        );

        syntaxRegistry.register(
                BukkitSyntaxInfos.Event.KEY,
                BukkitSyntaxInfos.Event.builder(CitizenEvents.class, "Citizen Right Click")
                        .addPatterns("(citizen|npc) right click")
                        .addEvent(NPCRightClickEvent.class)
                        .addDescription("Called when a Citizens NPC is right clicked by a player")
                        .addSince("1.1")
                        .addRequiredPlugin("Citizens")
                        .addExamples("on citizen right click:", "\tsend \"You right clicked %event-npc%\" to event-player")
                        .build()
        );
        syntaxRegistry.register(
                BukkitSyntaxInfos.Event.KEY,
                BukkitSyntaxInfos.Event.builder(CitizenEvents.class, "Citizen Create")
                        .addPatterns("(citizen|npc) create")
                        .addEvent(NPCCreateEvent.class)
                        .addDescription("Called when a Citizens NPC is created by the server or an external service (Such as this plugin).")
                        .addSince("1.1")
                        .addRequiredPlugin("Citizens")
                        .addExamples("on citizen create:", "\tid of event-npc is 3", "\tbroadcast \"%event-npc% was created!\"")
                        .build()
        );

        syntaxRegistry.register(
                BukkitSyntaxInfos.Event.KEY,
                BukkitSyntaxInfos.Event.builder(CitizenEvents.class, "Citizen Create by Player")
                        .addPatterns("(citizen|npc) create by [a] player")
                        .addEvent(PlayerCreateNPCEvent.class)
                        .addDescription("Called when a Citizens NPC is created by a player.")
                        .addSince("1.1")
                        .addRequiredPlugin("Citizens")
                        .build()
        );

        syntaxRegistry.register(
                BukkitSyntaxInfos.Event.KEY,
                BukkitSyntaxInfos.Event.builder(CitizenEvents.class, "Citizen Create by CommandSender")
                        .addPatterns("(citizen|npc) create by [a] command [sender]")
                        .addEvent(CommandSenderCreateNPCEvent.class)
                        .addDescription("Called when a Citizens NPC is created via a command.")
                        .addSince("1.1")
                        .addRequiredPlugin("Citizens")
                        .build()
        );

        syntaxRegistry.register(
                BukkitSyntaxInfos.Event.KEY,
                BukkitSyntaxInfos.Event.builder(CitizenEvents.class, "Citizen Spawn")
                        .addPatterns("(citizen|npc) spawn")
                        .addEvent(NPCSpawnEvent.class)
                        .addDescription("Called when a Citizens NPC is spawned.")
                        .addSince("1.1")
                        .addRequiredPlugin("Citizens")
                        .addExamples("on citizen spawn:", "\tcancel event", "\tbroadcast \"You can't spawn a citizen at %event-location%\"")
                        .build()
        );

        syntaxRegistry.register(
                BukkitSyntaxInfos.Event.KEY,
                BukkitSyntaxInfos.Event.builder(CitizenEvents.class, "Citizen Despawn")
                        .addPatterns("(citizen|npc) despawn")
                        .addEvent(NPCDespawnEvent.class)
                        .addDescription("Called when a Citizens NPC despawns.")
                        .addSince("1.1")
                        .addRequiredPlugin("Citizens")
                        .addExamples("on citizen despawn:", "\tevent-npc is npc with id 3", "\tcancel event")
                        .build()
        );

        syntaxRegistry.register(
                BukkitSyntaxInfos.Event.KEY,
                BukkitSyntaxInfos.Event.builder(CitizenEvents.class, "Citizen Remove")
                        .addPatterns("(citizen|npc) (delete|remove)")
                        .addEvent(NPCRemoveEvent.class)
                        .addDescription("Called when a Citizens NPC is removed.")
                        .addSince("1.1")
                        .addRequiredPlugin("Citizens")
                        .addExamples("on citizen remove:", "\tbroadcast \"%event-npc% was removed\"")
                        .build()
        );

        syntaxRegistry.register(
                BukkitSyntaxInfos.Event.KEY,
                BukkitSyntaxInfos.Event.builder(CitizenEvents.class, "Citizen Death")
                        .addPatterns("(citizen|npc) death")
                        .addEvent(NPCDeathEvent.class)
                        .addDescription("Called when a Citizens NPC dies.")
                        .addSince("1.1")
                        .addRequiredPlugin("Citizens")
                        .addExamples("on citizen death:", "\tbroadcast \"%event-npc% died due to %event-damagecause%\"")
                        .build()
        );

        syntaxRegistry.register(
                BukkitSyntaxInfos.Event.KEY,
                BukkitSyntaxInfos.Event.builder(CitizenEvents.class, "Citizen Damage")
                        .addPatterns("(citizen|npc) damage")
                        .addEvent(NPCDamageEvent.class)
                        .addDescription("Called when a Citizens NPC is damaged", "Does not include damage by entity or damage by block, use their respective events for that.")
                        .addSince("1.1")
                        .addRequiredPlugin("Citizens")
                        .addExamples("on citizen damage:", "\tbroadcast \"%event-npc% took %event-number% damage due to %event-damagecause%\"")
                        .build()
        );

        syntaxRegistry.register(
                BukkitSyntaxInfos.Event.KEY,
                BukkitSyntaxInfos.Event.builder(CitizenEvents.class, "Citizen Damage by Entity")
                        .addPatterns("(citizen|npc) damage by [an] entity")
                        .addEvent(NPCDamageByEntityEvent.class)
                        .addDescription("Called when a Citizens NPC is damaged by an entity.")
                        .addSince("1.1")
                        .addRequiredPlugin("Citizens")
                        .addExamples("on citizen damage by an entity:", "\tbroadcast \"%event-npc% took %event-number% damage by %event-entity%\"")
                        .build()
        );

        syntaxRegistry.register(
                BukkitSyntaxInfos.Event.KEY,
                BukkitSyntaxInfos.Event.builder(CitizenEvents.class, "Citizen Damage by Block")
                        .addPatterns("(citizen|npc) damage by [a] block")
                        .addEvent(NPCDamageByBlockEvent.class)
                        .addDescription("Called when a Citizens NPC is damaged by a block.", "Example: Lava, Fire")
                        .addSince("1.1")
                        .addRequiredPlugin("Citizens")
                        .addExamples("on citizen damage by block:", "\tbroadcast \"%event-npc% took %event-number% damage by %event-block\"")
                        .build()
        );

        syntaxRegistry.register(
                BukkitSyntaxInfos.Event.KEY,
                BukkitSyntaxInfos.Event.builder(CitizenEvents.class, "Citizen Combust")
                        .addPatterns("(citizen|npc) combust")
                        .addEvent(NPCCombustEvent.class)
                        .addDescription("Called when a Citizens NPC combusts.")
                        .addSince("1.3")
                        .addRequiredPlugin("Citizens")
                        .addExamples("on npc combust:", "\tbroadcast \"%event-npc% caught fire!\"")
                        .build()
        );

        syntaxRegistry.register(
                BukkitSyntaxInfos.Event.KEY,
                BukkitSyntaxInfos.Event.builder(CitizenEvents.class, "Citizen Knockback")
                        .addPatterns("(citizen|npc) knockback")
                        .addEvent(NPCKnockbackEvent.class)
                        .addDescription("Called when a Citizens NPC is knocked back.")
                        .addSince("1.3")
                        .addRequiredPlugin("Citizens")
                        .addExamples("on npc knockback:", "\tcancel event")
                        .build()
        );

        syntaxRegistry.register(
                BukkitSyntaxInfos.Event.KEY,
                BukkitSyntaxInfos.Event.builder(CitizenEvents.class, "Citizen Push")
                        .addPatterns("(citizen|npc) push[ed]")
                        .addEvent(NPCPushEvent.class)
                        .addDescription("Called when a Citizens NPC is pushed.")
                        .addSince("1.3")
                        .addRequiredPlugin("Citizens")
                        .addExamples("on npc push:", "\tbroadcast \"%event-npc% was pushed\"")
                        .build()
        );

        syntaxRegistry.register(
                BukkitSyntaxInfos.Event.KEY,
                BukkitSyntaxInfos.Event.builder(CitizenEvents.class, "Citizen Seen by Player")
                        .addPatterns("(citizen|npc) seen by [a] player")
                        .addEvent(NPCSeenByPlayerEvent.class)
                        .addDescription("Called when a Citizens NPC is seen by a player.")
                        .addSince("1.3")
                        .addRequiredPlugin("Citizens")
                        .addExamples("on npc seen by player:", "\tbroadcast \"%event-player% saw %event-npc%\"")
                        .build()
        );

        syntaxRegistry.register(
                BukkitSyntaxInfos.Event.KEY,
                BukkitSyntaxInfos.Event.builder(CitizenEvents.class, "Citizen Open Door")
                        .addPatterns("(citizen|npc) open[ed] [a] door")
                        .addEvent(NPCOpenDoorEvent.class)
                        .addDescription("Called when a Citizens NPC opens a door.")
                        .addSince("1.3")
                        .addRequiredPlugin("Citizens")
                        .addExamples("on npc open door:", "\tbroadcast \"%event-npc% opened a door\"")
                        .build()
        );

        syntaxRegistry.register(
                BukkitSyntaxInfos.Event.KEY,
                BukkitSyntaxInfos.Event.builder(CitizenEvents.class, "Citizen Open Gate")
                        .addPatterns("(citizen|npc) open[ed] [a] gate")
                        .addEvent(NPCOpenGateEvent.class)
                        .addDescription("Called when a Citizens NPC opens a gate.")
                        .addSince("1.3")
                        .addRequiredPlugin("Citizens")
                        .addExamples("on npc open gate:", "\tbroadcast \"%event-npc% opened a gate\"")
                        .build()
        );

        syntaxRegistry.register(
                BukkitSyntaxInfos.Event.KEY,
                BukkitSyntaxInfos.Event.builder(CitizenEvents.class, "Citizen Rename")
                        .addPatterns("(citizen|npc) rename[d]")
                        .addEvent(NPCRenameEvent.class)
                        .addDescription("Called when a Citizens NPC is renamed.")
                        .addSince("1.3")
                        .addRequiredPlugin("Citizens")
                        .addExamples("on npc rename:", "\tset {_old} to event-string", "\tbroadcast \"%event-npc% was renamed from %{_old}%\"")
                        .build()
        );

        syntaxRegistry.register(
                BukkitSyntaxInfos.Event.KEY,
                BukkitSyntaxInfos.Event.builder(CitizenEvents.class, "Citizen Vehicle Damage")
                        .addPatterns("(citizen|npc) vehicle damage")
                        .addEvent(NPCVehicleDamageEvent.class)
                        .addDescription("Called when a Citizens NPC's vehicle is damaged.")
                        .addSince("1.3")
                        .addRequiredPlugin("Citizens")
                        .addExamples("on npc vehicle damage:", "\tbroadcast \"%event-npc%'s vehicle was damaged\"")
                        .build()
        );

        syntaxRegistry.register(
                BukkitSyntaxInfos.Event.KEY,
                BukkitSyntaxInfos.Event.builder(CitizenEvents.class, "Citizen Clone")
                        .addPatterns("(citizen|npc) clone[d]")
                        .addEvent(NPCCloneEvent.class)
                        .addDescription("Called when a Citizens NPC is cloned.")
                        .addSince("1.3")
                        .addRequiredPlugin("Citizens")
                        .addExamples("on npc clone:", "\tbroadcast \"%event-npc% was cloned\"")
                        .build()
        );

        // Event values
        EventValues.registerEventValue(NPCEvent.class, NPC.class, new Getter<>() {
            @Override
            public @Nullable NPC get(NPCEvent event) {
                return event.getNPC();
            }
        }, 0);

        EventValues.registerEventValue(NPCClickEvent.class, Player.class, new Getter<>() {
            @Override
            public @Nullable Player get(NPCClickEvent event) {
                return event.getClicker();
            }
        }, 0);

        EventValues.registerEventValue(PlayerCreateNPCEvent.class, Player.class, new Getter<>() {
            @Override
            public @Nullable Player get(PlayerCreateNPCEvent event) {
                return event.getCreator();
            }
        }, 0);

        EventValues.registerEventValue(CommandSenderCreateNPCEvent.class, CommandSender.class, new Getter<>() {
            @Override
            public @Nullable CommandSender get(CommandSenderCreateNPCEvent event) {
                return event.getCreator();
            }
        }, 0);

        EventValues.registerEventValue(NPCSpawnEvent.class, Location.class, new Getter<>() {
            @Override
            public @Nullable Location get(NPCSpawnEvent event) {
                return event.getLocation();
            }
        }, 0);

        EventValues.registerEventValue(NPCSpawnEvent.class, SpawnReason.class, new Getter<>() {
            @Override
            public @Nullable SpawnReason get(NPCSpawnEvent event) {
                return event.getReason();
            }
        }, 0);

        EventValues.registerEventValue(NPCDespawnEvent.class, DespawnReason.class, new Getter<>() {
            @Override
            public @Nullable DespawnReason get(NPCDespawnEvent event) {
                return event.getReason();
            }
        }, 0);

        EventValues.registerEventValue(NPCDeathEvent.class, Player.class, new Getter<>() {
            @Override
            public @Nullable Player get(NPCDeathEvent event) {
                return event.getEvent().getEntity().getKiller();
            }
        }, 0);

        EventValues.registerEventValue(NPCDeathEvent.class, EntityDamageEvent.DamageCause.class, new Getter<>() {
            @Override
            public @Nullable EntityDamageEvent.DamageCause get(NPCDeathEvent event) {
                EntityDeathEvent devent = event.getEvent();
                return devent.getEntity().getLastDamageCause().getCause();
            }
        }, 0);

        EventValues.registerEventValue(NPCDamageEvent.class, EntityDamageEvent.DamageCause.class, new Getter<>() {
            @Override
            public @Nullable EntityDamageEvent.DamageCause get(NPCDamageEvent event) {
                return event.getCause();
            }
        }, 0);

        EventValues.registerEventValue(NPCDamageEvent.class, Double.class, new Getter<>() {
            @Override
            public @Nullable Double get(NPCDamageEvent event) {
                return event.getDamage();
            }
        }, 0);

        EventValues.registerEventValue(NPCDamageByEntityEvent.class, Entity.class, new Getter<>() {
            @Override
            public @Nullable Entity get(NPCDamageByEntityEvent event) {
                return event.getDamager();
            }
        }, 0);

        EventValues.registerEventValue(NPCDamageByBlockEvent.class, Block.class, new Getter<>() {
            @Override
            public @Nullable Block get(NPCDamageByBlockEvent event) {
                return event.getDamager();
            }
        }, 0);

        EventValues.registerEventValue(NPCCombustEvent.class, NPC.class, new Getter<>() {
            @Override
            public @Nullable NPC get(NPCCombustEvent event) {
                return event.getNPC();
            }
        }, 0);

        EventValues.registerEventValue(NPCKnockbackEvent.class, NPC.class, new Getter<>() {
            @Override
            public @Nullable NPC get(NPCKnockbackEvent event) {
                return event.getNPC();
            }
        }, 0);

        EventValues.registerEventValue(NPCPushEvent.class, NPC.class, new Getter<>() {
            @Override
            public @Nullable NPC get(NPCPushEvent event) {
                return event.getNPC();
            }
        }, 0);

        EventValues.registerEventValue(NPCSeenByPlayerEvent.class, NPC.class, new Getter<>() {
            @Override
            public @Nullable NPC get(NPCSeenByPlayerEvent event) {
                return event.getNPC();
            }
        }, 0);

        EventValues.registerEventValue(NPCSeenByPlayerEvent.class, Player.class, new Getter<>() {
            @Override
            public @Nullable Player get(NPCSeenByPlayerEvent event) {
                return event.getPlayer();
            }
        }, 0);

        EventValues.registerEventValue(NPCOpenDoorEvent.class, NPC.class, new Getter<>() {
            @Override
            public @Nullable NPC get(NPCOpenDoorEvent event) {
                return event.getNPC();
            }
        }, 0);

        EventValues.registerEventValue(NPCOpenGateEvent.class, NPC.class, new Getter<>() {
            @Override
            public @Nullable NPC get(NPCOpenGateEvent event) {
                return event.getNPC();
            }
        }, 0);

        EventValues.registerEventValue(NPCRenameEvent.class, NPC.class, new Getter<>() {
            @Override
            public @Nullable NPC get(NPCRenameEvent event) {
                return event.getNPC();
            }
        }, 0);

        EventValues.registerEventValue(NPCRenameEvent.class, String.class, new Getter<>() {
            @Override
            public @Nullable String get(NPCRenameEvent event) {
                return event.getOldName();
            }
        }, 0);

        EventValues.registerEventValue(NPCVehicleDamageEvent.class, NPC.class, new Getter<>() {
            @Override
            public @Nullable NPC get(NPCVehicleDamageEvent event) {
                return event.getNPC();
            }
        }, 0);

        EventValues.registerEventValue(NPCCloneEvent.class, NPC.class, new Getter<>() {
            @Override
            public @Nullable NPC get(NPCCloneEvent event) {
                return event.getClone();
            }
        }, 0);
    }
}

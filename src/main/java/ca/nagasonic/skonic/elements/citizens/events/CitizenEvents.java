package ca.nagasonic.skonic.elements.citizens.events;

import ch.njol.skript.Skript;
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

@SuppressWarnings("unused")
public class CitizenEvents extends SimpleEvent {
    static {
        Skript.registerEvent("Citizen Click", CitizenEvents.class, NPCClickEvent.class,
                        "(citizen|npc) click")
                .description("Called when a Citizens NPC is clicked by a player.")
                .since("1.1")
                .requiredPlugins("Citizens")
                .documentationID("12469");
        Skript.registerEvent("Citizen Left Click", CitizenEvents.class, NPCLeftClickEvent.class,
                        "(citizen|npc) left click")
                .description("Called when a Citizens NPC is left clicked by a player.", "Only is called if NPC is not vulnerable.")
                .since("1.1")
                .requiredPlugins("Citizens")
                .documentationID("12478");

        Skript.registerEvent("Citizen Right Click", CitizenEvents.class, NPCRightClickEvent.class,
                        "(citizen|npc) right click")
                .description("Called when a Citizens NPC is right clicked by a player")
                .since("1.1")
                .requiredPlugins("Citizens")
                .documentationID("12480");

        Skript.registerEvent("Citizen Create", CitizenEvents.class, NPCCreateEvent.class,
                        "(citizen|npc) create")
                .description("Called when a Citizens NPC is created by the server or an external service (Such as this plugin).")
                .since("1.1")
                .requiredPlugins("Citizens")
                .documentationID("12470");

        Skript.registerEvent("Citizen Create by Player", CitizenEvents.class, PlayerCreateNPCEvent.class,
                        "(citizen|npc) create by [a] player")
                .description("Called when a Citizens NPC is created by a player.")
                .since("1.1")
                .requiredPlugins("Citizens")
                .documentationID("12472");

        Skript.registerEvent("Citizen Create by CommandSender", CitizenEvents.class, CommandSenderCreateNPCEvent.class,
                        "(citizen|npc) create by [a] command [sender]")
                .description("Called when a Citizens NPC is created via a command.")
                .since("1.1")
                .requiredPlugins("Citizens")
                .documentationID("12471");

        Skript.registerEvent("Citizen Spawn", CitizenEvents.class, NPCSpawnEvent.class,
                        "(citizen|npc) spawn")
                .description("Called when a Citizens NPC is spawned.")
                .since("1.1")
                .requiredPlugins("Citizens")
                .documentationID("12481");

        Skript.registerEvent("Citizen Despawn", CitizenEvents.class, NPCDespawnEvent.class,
                        "(citizen|npc) despawn")
                .description("Called when a Citizens NPC despawns.")
                .since("1.1")
                .requiredPlugins("Citizens")
                .documentationID("12477");

        Skript.registerEvent("Citizen Remove", CitizenEvents.class, NPCRemoveEvent.class,
                        "(citizen|npc) (delete|remove)")
                .description("Called when a Citizens NPC is removed.")
                .since("1.1")
                .requiredPlugins("Citizens")
                .documentationID("12479");

        Skript.registerEvent("Citizen Death", CitizenEvents.class, NPCDeathEvent.class,
                        "(citizen|npc) death")
                .description("Called when a Citizens NPC dies.")
                .since("1.1")
                .requiredPlugins("Citizens")
                .documentationID("12476");

        Skript.registerEvent("Citizen Damage", CitizenEvents.class, NPCDamageEvent.class,
                        "(citizen|npc) damage")
                .description("Called when a Citizens NPC is damaged", "Does not include damage by entity or damage by block, use their respective events for that.")
                .since("1.1")
                .requiredPlugins("Citizens")
                .documentationID("12473");

        Skript.registerEvent("Citizen Damage by Entity", CitizenEvents.class, NPCDamageByEntityEvent.class,
                        "(citizen|npc) damage by [an] entity")
                .description("Called when a Citizens NPC is damaged by an entity.")
                .since("1.1")
                .requiredPlugins("Citizens")
                .documentationID("12475");

        Skript.registerEvent("Citizen Damage by Block", CitizenEvents.class, NPCDamageByBlockEvent.class,
                        "(citizen|npc) damage by [a] block")
                .description("Called when a Citizens NPC is damaged by a block.", "Example: Lava, Fire")
                .since("1.1")
                .requiredPlugins("Citizens")
                .documentationID("12474");


        //event-npc in all NPC events.
        EventValues.registerEventValue(NPCEvent.class, NPC.class, new Getter<>() {
            @Override
            public @Nullable NPC get(NPCEvent event) {
                return event.getNPC();
            }
        }, 0);

        //event-player in click events
        EventValues.registerEventValue(NPCClickEvent.class, Player.class, new Getter<>() {
            @Override
            public @Nullable Player get(NPCClickEvent event) {
                return event.getClicker();
            }
        }, 0);

        //event-player in Player create NPC events
        EventValues.registerEventValue(PlayerCreateNPCEvent.class, Player.class, new Getter<>() {
            @Override
            public @Nullable Player get(PlayerCreateNPCEvent event) {
                return event.getCreator();
            }
        }, 0);

        //event-commandsender in Command Sender create NPC events
        EventValues.registerEventValue(CommandSenderCreateNPCEvent.class, CommandSender.class, new Getter<>() {
            @Override
            public @Nullable CommandSender get(CommandSenderCreateNPCEvent event) {
                return event.getCreator();
            }
        }, 0);

        //event-location in npc spawn events
        EventValues.registerEventValue(NPCSpawnEvent.class, Location.class, new Getter<>() {
            @Override
            public @Nullable Location get(NPCSpawnEvent event) {
                return event.getLocation();
            }
        }, 0);

        //event-spawnreason in npc spawn events
        EventValues.registerEventValue(NPCSpawnEvent.class, SpawnReason.class, new Getter<>() {
            @Override
            public @Nullable SpawnReason get(NPCSpawnEvent event) {
                return event.getReason();
            }
        }, 0);

        //event-despawnreason in npc despawn events
        EventValues.registerEventValue(NPCDespawnEvent.class, DespawnReason.class, new Getter<>() {
            @Override
            public @Nullable DespawnReason get(NPCDespawnEvent event) {
                return event.getReason();
            }
        }, 0);

        //event-player in citizen death events
        EventValues.registerEventValue(NPCDeathEvent.class, Player.class, new Getter<>() {
            @Override
            public @Nullable Player get(NPCDeathEvent event) {
                return event.getEvent().getEntity().getKiller();
            }
        }, 0);

        //event-damagecause in citizen death events
        EventValues.registerEventValue(NPCDeathEvent.class, EntityDamageEvent.DamageCause.class, new Getter<>() {
            @Override
            public @Nullable EntityDamageEvent.DamageCause get(NPCDeathEvent event) {
                EntityDeathEvent devent = event.getEvent();
                return devent.getEntity().getLastDamageCause().getCause();
            }
        }, 0);

        //event-damagecause in citizen damage events
        EventValues.registerEventValue(NPCDamageEvent.class, EntityDamageEvent.DamageCause.class, new Getter<>() {
            @Override
            public @Nullable EntityDamageEvent.DamageCause get(NPCDamageEvent event) {
                return event.getCause();
            }
        }, 0);

        //event-number in citizen damage events
        EventValues.registerEventValue(NPCDamageEvent.class, Double.class, new Getter<>() {
            @Override
            public @Nullable Double get(NPCDamageEvent event) {
                return event.getDamage();
            }
        }, 0);

        //event-entity in citizen damage by entity events
        EventValues.registerEventValue(NPCDamageByEntityEvent.class, Entity.class, new Getter<>() {
            @Override
            public @Nullable Entity get(NPCDamageByEntityEvent event) {
                return event.getDamager();
            }
        }, 0);

        //event-block in citizen damage by block events
        EventValues.registerEventValue(NPCDamageByBlockEvent.class, Block.class, new Getter<>() {
            @Override
            public @Nullable Block get(NPCDamageByBlockEvent event) {
                return event.getDamager();
            }
        }, 0);
    }
}

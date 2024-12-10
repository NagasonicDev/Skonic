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

public class CitizenEvents {
    static {
        Skript.registerEvent("Citizen Click", SimpleEvent.class, NPCClickEvent.class,
                "(citizen|npc) click")
                .description("Called when a Citizens NPC is clicked by a player.")
                .since("1.1")
                .documentationID("citizen.event.click")
                .requiredPlugins("Citizens");
        Skript.registerEvent("Citizen Left Click", SimpleEvent.class, NPCLeftClickEvent.class,
                "(citizen|npc) left click")
                .description("Called when a Citizens NPC is left clicked by a player.", "Only is called if NPC is not vulnerable.")
                .since("1.1")
                .documentationID("citizen.event.leftclick")
                .requiredPlugins("Citizens");
        Skript.registerEvent("Citizen Right Click", SimpleEvent.class, NPCRightClickEvent.class,
                "(citizen|npc) right click")
                .description("Called when a Citizens NPC is right clicked by a player")
                .since("1.1")
                .documentationID("citizen.event.rightclick")
                .requiredPlugins("Citizens");
        Skript.registerEvent("Citizen Create", SimpleEvent.class, NPCCreateEvent.class,
                "(citizen|npc) create")
                .description("Called when a Citizens NPC is created by the server or an external service (Such as this plugin).")
                .since("1.1")
                .documentationID("citizen.event.create")
                .requiredPlugins("Citizens");
        Skript.registerEvent("Citizen Create by Player", SimpleEvent.class, PlayerCreateNPCEvent.class,
                "(citizen|npc) create by [a] player")
                .description("Called when a Citizens NPC is created by a player.")
                .since("1.1")
                .documentationID("citizen.event.createplayer")
                .requiredPlugins("Citizens");
        Skript.registerEvent("Citizen Create by CommandSender", SimpleEvent.class, CommandSenderCreateNPCEvent.class,
                "(citizen|npc) create by [a] command [sender]")
                .description("Called when a Citizens NPC is created via a command.")
                .since("1.1")
                .documentationID("citizen.event.createcommand")
                .requiredPlugins("Citizens");
        Skript.registerEvent("Citizen Spawn", SimpleEvent.class, NPCSpawnEvent.class,
                "(citizen|npc) spawn")
                .description("Called when a Citizens NPC is spawned.")
                .since("1.1")
                .documentationID("citizen.event.spawn")
                .requiredPlugins("Citizens");
        Skript.registerEvent("Citizen Despawn", SimpleEvent.class, NPCDespawnEvent.class,
                "(citizen|npc) despawn")
                .description("Called when a Citizens NPC despawns.")
                .since("1.1")
                .documentationID("citizen.event.despawn")
                .requiredPlugins("Citizens");
        Skript.registerEvent("Citizen Remove", SimpleEvent.class, NPCRemoveEvent.class,
                "(citizen|npc) (delete|remove)")
                .description("Called when a Citizens NPC is removed.")
                .since("1.1")
                .documentationID("citizen.event.spawn")
                .requiredPlugins("Citizens");
        Skript.registerEvent("Citizen Death", SimpleEvent.class, NPCDeathEvent.class,
                "(citizen|npc) death")
                .description("Called when a Citizens NPC dies.")
                .since("1.1")
                .documentationID("citizen.event.death")
                .requiredPlugins("Citizens");
        Skript.registerEvent("Citizen Damage", SimpleEvent.class, NPCDamageEvent.class,
                "(citizen|npc) damage")
                .description("Called when a Citizens NPC is damaged", "Does not include damage by entity or damage by block, use their respective events for that.")
                .since("1.1")
                .documentationID("citizen.event.damage")
                .requiredPlugins("Citizens");
        Skript.registerEvent("Citizen Damage by Entity", SimpleEvent.class, NPCDamageByEntityEvent.class,
                "(citizen|npc) damage by [an] entity")
                .description("Called when a Citizens NPC is damaged by an entity.")
                .since("1.1")
                .documentationID("citizen.event.damagebyentity")
                .requiredPlugins("Citizens");
        Skript.registerEvent("Citizen Damage by Block", SimpleEvent.class, NPCDamageByBlockEvent.class,
                "(citizen|npc) damage by [a] block")
                .description("Called when a Citizens NPC is damaged by a block.", "Example: Lava, Fire")
                .since("1.1")
                .documentationID("citizen.event.damagebyblock")
                .requiredPlugins("Citizens");


        //event-npc in all NPC events.
        EventValues.registerEventValue(NPCEvent.class, NPC.class, new Getter<NPC, NPCEvent>() {
            @Override
            public @Nullable NPC get(NPCEvent event) {
                return event.getNPC();
            }
        }, EventValues.TIME_NOW);

        //event-player in click events
        EventValues.registerEventValue(NPCClickEvent.class, Player.class, new Getter<Player, NPCClickEvent>() {
            @Override
            public @Nullable Player get(NPCClickEvent event) {
                return event.getClicker();
            }
        }, EventValues.TIME_NOW);

        //event-player in Player create NPC events
        EventValues.registerEventValue(PlayerCreateNPCEvent.class, Player.class, new Getter<Player, PlayerCreateNPCEvent>() {
            @Override
            public @Nullable Player get(PlayerCreateNPCEvent event) {
                return event.getCreator();
            }
        }, EventValues.TIME_NOW);

        //event-commandsender in Command Sender create NPC events
        EventValues.registerEventValue(CommandSenderCreateNPCEvent.class, CommandSender.class, new Getter<CommandSender, CommandSenderCreateNPCEvent>() {
            @Override
            public @Nullable CommandSender get(CommandSenderCreateNPCEvent event) {
                return event.getCreator();
            }
        }, EventValues.TIME_NOW);

        //event-location in npc spawn events
        EventValues.registerEventValue(NPCSpawnEvent.class, Location.class, new Getter<Location, NPCSpawnEvent>() {
            @Override
            public @Nullable Location get(NPCSpawnEvent event) {
                return event.getLocation();
            }
        }, EventValues.TIME_NOW);

        //event-spawnreason in npc spawn events
        EventValues.registerEventValue(NPCSpawnEvent.class, SpawnReason.class, new Getter<SpawnReason, NPCSpawnEvent>() {
            @Override
            public @Nullable SpawnReason get(NPCSpawnEvent event) {
                return event.getReason();
            }
        }, EventValues.TIME_NOW);

        //event-despawnreason in npc despawn events
        EventValues.registerEventValue(NPCDespawnEvent.class, DespawnReason.class, new Getter<DespawnReason, NPCDespawnEvent>() {
            @Override
            public @Nullable DespawnReason get(NPCDespawnEvent event) {
                return event.getReason();
            }
        }, EventValues.TIME_NOW);

        //event-player in citizen death events
        EventValues.registerEventValue(NPCDeathEvent.class, Player.class, new Getter<Player, NPCDeathEvent>() {
            @Override
            public @Nullable Player get(NPCDeathEvent event) {
                return event.getEvent().getEntity().getKiller();
            }
        }, EventValues.TIME_NOW);

        //event-damagecause in citizen death events
        EventValues.registerEventValue(NPCDeathEvent.class, EntityDamageEvent.DamageCause.class, new Getter<EntityDamageEvent.DamageCause, NPCDeathEvent>() {
            @Override
            public @Nullable EntityDamageEvent.DamageCause get(NPCDeathEvent event) {
                EntityDeathEvent devent = event.getEvent();
                return devent.getEntity().getLastDamageCause().getCause();
            }
        }, EventValues.TIME_NOW);

        //event-damagecause in citizen damage events
        EventValues.registerEventValue(NPCDamageEvent.class, EntityDamageEvent.DamageCause.class, new Getter<EntityDamageEvent.DamageCause, NPCDamageEvent>() {
            @Override
            public @Nullable EntityDamageEvent.DamageCause get(NPCDamageEvent event) {
                return event.getCause();
            }
        }, EventValues.TIME_NOW);

        //event-damage in citizen damage events
        EventValues.registerEventValue(NPCDamageEvent.class, Double.class, new Getter<Double, NPCDamageEvent>() {
            @Override
            public @Nullable Double get(NPCDamageEvent event) {
                return event.getDamage();
            }
        }, EventValues.TIME_NOW);

        //event-entity in citizen damage by entity events
        EventValues.registerEventValue(NPCDamageByEntityEvent.class, Entity.class, new Getter<Entity, NPCDamageByEntityEvent>() {
            @Override
            public @Nullable Entity get(NPCDamageByEntityEvent event) {
                return event.getDamager();
            }
        }, EventValues.TIME_NOW);

        //event-block in citizen damage by block events
        EventValues.registerEventValue(NPCDamageByBlockEvent.class, Block.class, new Getter<Block, NPCDamageByBlockEvent>() {
            @Override
            public @Nullable Block get(NPCDamageByBlockEvent event) {
                return event.getDamager();
            }
        }, EventValues.TIME_NOW);
    }
}

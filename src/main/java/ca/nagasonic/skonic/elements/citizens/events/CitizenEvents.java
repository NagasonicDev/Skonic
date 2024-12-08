package ca.nagasonic.skonic.elements.citizens.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import net.citizensnpcs.api.event.*;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
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
                .description("Called when a Citizens NPC is left clicked by a player.")
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
                .description("Called when a Citizens NPC is created.")
                .since("1.1")
                .documentationID("citizen.event.create")
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

        EventValues.registerEventValue(NPCEvent.class, NPC.class, new Getter<NPC, NPCEvent>() {
            @Override
            public @Nullable NPC get(NPCEvent event) {
                return event.getNPC();
            }
        }, EventValues.TIME_NOW);

        EventValues.registerEventValue(NPCClickEvent.class, Player.class, new Getter<Player, NPCClickEvent>() {
            @Override
            public @Nullable Player get(NPCClickEvent event) {
                return event.getClicker();
            }
        }, EventValues.TIME_NOW);

        EventValues.registerEventValue(NPCSpawnEvent.class, Location.class, new Getter<Location, NPCSpawnEvent>() {
            @Override
            public @Nullable Location get(NPCSpawnEvent event) {
                return event.getLocation();
            }
        }, EventValues.TIME_NOW);

        EventValues.registerEventValue(NPCSpawnEvent.class, SpawnReason.class, new Getter<SpawnReason, NPCSpawnEvent>() {
            @Override
            public @Nullable SpawnReason get(NPCSpawnEvent event) {
                return event.getReason();
            }
        }, EventValues.TIME_NOW);

        EventValues.registerEventValue(NPCDespawnEvent.class, DespawnReason.class, new Getter<DespawnReason, NPCDespawnEvent>() {
            @Override
            public @Nullable DespawnReason get(NPCDespawnEvent event) {
                return event.getReason();
            }
        }, EventValues.TIME_NOW);

        EventValues.registerEventValue(NPCDeathEvent.class, Player.class, new Getter<Player, NPCDeathEvent>() {
            @Override
            public @Nullable Player get(NPCDeathEvent event) {
                return event.getEvent().getEntity().getKiller();
            }
        }, EventValues.TIME_NOW);

        EventValues.registerEventValue(NPCDeathEvent.class, EntityDamageEvent.DamageCause.class, new Getter<EntityDamageEvent.DamageCause, NPCDeathEvent>() {
            @Override
            public @Nullable EntityDamageEvent.DamageCause get(NPCDeathEvent event) {
                return event.getEvent().getEntity().getLastDamageCause().getCause();
            }
        }, EventValues.TIME_NOW);
    }
}

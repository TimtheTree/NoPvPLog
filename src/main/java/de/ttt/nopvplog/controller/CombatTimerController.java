package de.ttt.nopvplog.controller;

import de.ttt.nopvplog.NoPvPLogTemplate;
import de.ttt.nopvplog.models.CombatTimer;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.UUID;

public class CombatTimerController {
    private HashMap<UUID, CombatTimer> combatTimerHashMap;

    public CombatTimerController() {
        this.combatTimerHashMap = new HashMap<>();
    }

    public void addEntry(PlayerJoinEvent event) {
        UUID playerId = event.getPlayer().getUniqueId();
        this.combatTimerHashMap.put(playerId, new CombatTimer(playerId));
    }

    public void addEntry(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            UUID playerId = event.getEntity().getUniqueId();
            this.combatTimerHashMap.put(playerId, new CombatTimer(playerId));
        }
    }

    public @Nullable CombatTimer getCombatTimer(UUID playerId) {
        return this.combatTimerHashMap.getOrDefault(playerId, null);
        //TODO potenzielle Gefahrenstelle
    }

    public void deleteEntry(UUID playerId) {
        this.combatTimerHashMap.remove(playerId);
    }

    public void updateEntry(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.PLAYER && event.getDamager().getType() == EntityType.PLAYER) {
            UUID playerId = event.getEntity().getUniqueId();
            if (getCombatTimer(playerId) != null) {
                getCombatTimer(playerId).update(event);
            } else addEntry(event);
        }
    }
}

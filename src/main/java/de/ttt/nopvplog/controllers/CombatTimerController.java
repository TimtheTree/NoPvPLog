package de.ttt.nopvplog.controllers;

import de.ttt.nopvplog.NoPvPLogTemplate;
import de.ttt.nopvplog.models.CombatTimer;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.UUID;

public class CombatTimerController {

    private final long timerDuration;

    private final long minimumDeactivationDistance;
    private final HashMap<UUID, CombatTimer> combatTimerHashMap;


    public CombatTimerController(NoPvPLogTemplate template) {
        this.combatTimerHashMap = new HashMap<>();
        addAllPlayers();

        this.timerDuration = template.getConfig().getLong("CombatTimerDuration");
        this.minimumDeactivationDistance = template.getConfig().getLong("MinimumDeactivationDistance");
    }

    public long getTimerDuration() {
        return timerDuration;
    }

    public long getMinimumDeactivationDistance() {
        return minimumDeactivationDistance;
    }

    public void addEntry(PlayerJoinEvent event) {
        addEntry(event.getPlayer().getUniqueId());
    }

    public void addEntry(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player player) {
            addEntry(player.getUniqueId());
        }
    }

    public void addEntry(UUID playerId) {
        this.combatTimerHashMap.put(playerId, new CombatTimer(playerId));
    }

    public void addAllPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            addEntry(player.getUniqueId());
        }
    }

    private @Nullable CombatTimer getCombatTimer(UUID playerId) {
        return this.combatTimerHashMap.getOrDefault(playerId, null);
        //TODO potenzielle Gefahrenstelle
    }

    public void deleteEntry(UUID playerId) {
        this.combatTimerHashMap.remove(playerId);
    }

    public void updateEntry(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.PLAYER && event.getDamager().getType() == EntityType.PLAYER) {
            UUID playerId = event.getEntity().getUniqueId();

            CombatTimer timer = getCombatTimer(playerId);

            if (timer == null) {
                addEntry(event);
                timer = getCombatTimer(playerId);
            }

            timer.update(event);
        }
    }

    public boolean detectCombat(UUID playerId) {

        CombatTimer combatTimer = getCombatTimer(playerId);

        if (combatTimer == null) addEntry(playerId);
        combatTimer = getCombatTimer(playerId);
        return !combatTimer.isOutOfCombat(this.timerDuration, this.minimumDeactivationDistance);
    }
}

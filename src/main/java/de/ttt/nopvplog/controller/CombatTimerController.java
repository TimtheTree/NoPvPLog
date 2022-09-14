package de.ttt.nopvplog.controller;

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

    private long timerDuration;

    private long minimumDeactivationDistance;
    private HashMap<UUID, CombatTimer> combatTimerHashMap;
    private NoPvPLogTemplate template;

    public CombatTimerController(NoPvPLogTemplate template) {
        this.combatTimerHashMap = new HashMap<>();
        this.template = template;
        addAllPlayers();


        this.timerDuration = template.getConfig().getLong("CombatTimerDuration");
        this.minimumDeactivationDistance = template.getConfig().getLong("MinimumDeactivationDistance");
    }

    public void addEntry(PlayerJoinEvent event) {
        addEntry(event.getPlayer());
    }

    public void addEntry(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player player) {
            addEntry(player);
        }
    }

    public void addEntry(Player player) {
        UUID playerId = player.getUniqueId();
        this.combatTimerHashMap.put(playerId, new CombatTimer(playerId));
    }

    public void addAllPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            addEntry(player);
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

    private boolean detectCombat(Player player) {

        CombatTimer combatTimer = getCombatTimer(player.getUniqueId());

        if (combatTimer == null) addEntry(player);
        combatTimer = getCombatTimer(player.getUniqueId());
        return !combatTimer.isOutOfCombat(this.timerDuration,this.minimumDeactivationDistance);
    }
}

package de.ttt.nopvplog.controllers;

import de.ttt.nopvplog.NoPvPLogTemplate;
import de.ttt.nopvplog.models.CombatTimerPvp;
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


    public boolean detectCombat(UUID playerId) {

        CombatTimerPvp combatTimer = (CombatTimerPvp) this.getTimer(playerId);

        if (combatTimer == null) addEntry(playerId);
        combatTimer = getCombatTimer(playerId);
        return !combatTimer.isOutOfCombat(this.timerDuration, this.minimumDeactivationDistance);
    }
}

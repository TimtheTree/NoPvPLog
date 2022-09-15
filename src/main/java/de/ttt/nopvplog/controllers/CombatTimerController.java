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

public class CombatTimerController extends TimerController {

    private final long minimumDeactivationDistance;

    public CombatTimerController(NoPvPLogTemplate template) {
        super(template);
        this.minimumDeactivationDistance = template.getConfig().getLong("MinimumDeactivationDistancePvP");
    }

    @Override
    public void addEntry(UUID playerId) {
        this.timerHashMap.put(playerId, new CombatTimerPvp(playerId));
    }

    public long getMinimumDeactivationDistance() {
        return minimumDeactivationDistance;
    }


    public boolean detectCombat(UUID playerId) {

        CombatTimerPvp combatTimer = (CombatTimerPvp) this.getTimer(playerId);

        if (combatTimer == null) addEntry(playerId);
        combatTimer = (CombatTimerPvp) this.getTimer(playerId);
        return !combatTimer.isOutOfCombat(this.timerDuration, this.minimumDeactivationDistance);
    }
}

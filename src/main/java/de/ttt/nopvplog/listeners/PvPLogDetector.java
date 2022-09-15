package de.ttt.nopvplog.listeners;

import de.ttt.nopvplog.controllers.CombatTimerController;
import de.ttt.nopvplog.controllers.DamageTimerController;
import de.ttt.nopvplog.controllers.DeathCrateController;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PvPLogDetector implements Listener {

    private DeathCrateController crateController;
    private CombatTimerController combatTimerController;
    private DamageTimerController damageTimerController;

    public PvPLogDetector(DeathCrateController crateController, CombatTimerController combatTimerController) {
        this.crateController = crateController;
        this.combatTimerController = combatTimerController;
    }

    public void onPlayerLeave(PlayerQuitEvent event) {

    }

    private boolean playerIsInCombat(UUID playerId){

        return this.combatTimerController.detectCombat(playerId) || this.damageTimerController.detectCombat(playerId);

    }

}

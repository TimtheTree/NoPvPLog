package de.ttt.nopvplog.listeners;

import de.ttt.nopvplog.controllers.CombatTimerController;
import de.ttt.nopvplog.controllers.DeathCrateController;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PvPLogDetector implements Listener {

    private DeathCrateController crateController;
    private CombatTimerController timerController;

    public PvPLogDetector(DeathCrateController crateController, CombatTimerController timerController) {
        this.crateController = crateController;
        this.timerController = timerController;
    }

    public void onPlayerLeave(PlayerQuitEvent event) {

    }

    private boolean playerIsInCombat(UUID playerId){

        return this.timerController.detectCombat(playerId);

    }

}

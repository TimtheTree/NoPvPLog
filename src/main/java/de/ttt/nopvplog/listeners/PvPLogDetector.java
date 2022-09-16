package de.ttt.nopvplog.listeners;

import de.ttt.nopvplog.controllers.CombatTimerController;
import de.ttt.nopvplog.controllers.DamageTimerController;
import de.ttt.nopvplog.controllers.DeathCrateController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PvPLogDetector implements Listener {

    private final DeathCrateController crateController;
    private final CombatTimerController combatTimerController;
    private final DamageTimerController damageTimerController;

    public PvPLogDetector(DeathCrateController crateController, CombatTimerController combatTimerController, DamageTimerController damageTimerController) {
        this.crateController = crateController;
        this.combatTimerController = combatTimerController;
        this.damageTimerController = damageTimerController;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {

        //TODO Kill player before they leave, apply playerIsInCombat logic from below, apply DeatCrateController logic before killing the player

        if(!playerIsInCombat(event.getPlayer().getUniqueId())) return;

        this.crateController.executePvPLogLogic(event.getPlayer());

        event.getPlayer().getInventory().clear();

        event.getPlayer().setHealth(0.0);

    }

    private boolean playerIsInCombat(UUID playerId) {

        return this.combatTimerController.detectCombat(playerId) || this.damageTimerController.detectCombat(playerId);

    }

}

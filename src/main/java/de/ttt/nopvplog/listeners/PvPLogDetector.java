package de.ttt.nopvplog.listeners;

import de.ttt.nopvplog.controllers.CombatTimerController;
import de.ttt.nopvplog.controllers.DamageTimerController;
import de.ttt.nopvplog.controllers.DeathCrateController;
import de.ttt.nopvplog.controllers.HologramController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PvPLogDetector implements Listener {

    private final DeathCrateController crateController;
    private final CombatTimerController combatTimerController;
    private final DamageTimerController damageTimerController;
    private final HologramController hologramController;

    public PvPLogDetector(DeathCrateController crateController, CombatTimerController combatTimerController, DamageTimerController damageTimerController, HologramController hologramController) {
        this.crateController = crateController;
        this.combatTimerController = combatTimerController;
        this.damageTimerController = damageTimerController;
        this.hologramController = hologramController;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {

        if(!playerIsInCombat(event.getPlayer().getUniqueId())) return;

        this.crateController.executePvPLogLogic(event.getPlayer());

        this.hologramController.createHologram(event.getPlayer());

        event.getPlayer().getInventory().clear();

        event.getPlayer().setHealth(0.0);

    }

    private boolean playerIsInCombat(UUID playerId) {

        this.combatTimerController.updateTimer(playerId);
        this.damageTimerController.updateTimer(playerId);

        return this.combatTimerController.detectCombat(playerId) || this.damageTimerController.detectCombat(playerId);

    }

}

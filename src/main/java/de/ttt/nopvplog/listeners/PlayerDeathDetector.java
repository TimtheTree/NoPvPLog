package de.ttt.nopvplog.listeners;

import de.ttt.nopvplog.NoPvPLogTemplate;
import de.ttt.nopvplog.models.CombatTimerPvp;
import de.ttt.nopvplog.models.DamageTimer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.UUID;

public record PlayerDeathDetector(NoPvPLogTemplate template) implements Listener {

    /**removes combat from dead player and the killer
     *
     * @param event PlayerDeathEvent
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDeath(PlayerDeathEvent event) {

        if (event.isCancelled()) return;

        UUID playerId = event.getPlayer().getUniqueId();

        CombatTimerPvp combatTimerPvp = (CombatTimerPvp) this.template().getCTController().getTimer(playerId);
        DamageTimer damageTimer = (DamageTimer) this.template().getDTController().getTimer(playerId);

        Bukkit.getScheduler().scheduleSyncDelayedTask(template, ()->{
            combatTimerPvp.leaveCombat();
            damageTimer.leaveCombat();
            combatTimerPvp.clearEnemyReferences();
            }, 1);

        for (UUID uuid : combatTimerPvp.getRelatedTimers((int) this.template.getCTController().getMinimumDeactivationDistance())) {

            CombatTimerPvp tmpTimer = (CombatTimerPvp) this.template().getCTController().getTimer(uuid);

            if(!tmpTimer.getRelatedTimers().isEmpty()){
                tmpTimer.clearEnemyReferences();
                tmpTimer.leaveCombat();
            }
        }
    }
}

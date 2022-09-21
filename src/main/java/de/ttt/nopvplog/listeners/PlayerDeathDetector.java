package de.ttt.nopvplog.listeners;

import de.ttt.nopvplog.NoPvPLogTemplate;
import de.ttt.nopvplog.models.CombatTimerPvp;
import de.ttt.nopvplog.models.DamageTimer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.UUID;

public record PlayerDeathDetector(NoPvPLogTemplate template) implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDeath(PlayerDeathEvent event) {

        if (event.isCancelled()) return;

        UUID playerId = event.getPlayer().getUniqueId();

        CombatTimerPvp combatTimerPvp = (CombatTimerPvp) this.template().getCTController().getTimer(playerId);
        DamageTimer damageTimer = (DamageTimer) this.template().getDTController().getTimer(playerId);

        for (UUID uuid : combatTimerPvp.getRelatedTimers()) {

            CombatTimerPvp tmpTimer = (CombatTimerPvp) this.template().getCTController().getTimer(uuid);

            tmpTimer.setEnemyReference(null);
            tmpTimer.leaveCombat();
        }

        combatTimerPvp.leaveCombat();
        damageTimer.leaveCombat();

    }
}

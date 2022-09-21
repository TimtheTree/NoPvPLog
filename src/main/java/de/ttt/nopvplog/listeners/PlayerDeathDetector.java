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

        //IDEE: nur enemy reference unsetten wenn der Timer danach keine timer mehr hat mit denen er in combat wäre

        if (event.isCancelled()) return;

        UUID playerId = event.getPlayer().getUniqueId();

        CombatTimerPvp combatTimerPvp = (CombatTimerPvp) this.template().getCTController().getTimer(playerId);
        DamageTimer damageTimer = (DamageTimer) this.template().getDTController().getTimer(playerId);

        combatTimerPvp.leaveCombat();
        combatTimerPvp.setEnemyReference(null);
        damageTimer.leaveCombat();

        for (UUID uuid : combatTimerPvp.getRelatedTimers((int) this.template.getCTController().getMinimumDeactivationDistance())) {

            CombatTimerPvp tmpTimer = (CombatTimerPvp) this.template().getCTController().getTimer(uuid);

            if(tmpTimer.getRelatedTimers().isEmpty()){
                tmpTimer.setEnemyReference(null);
                tmpTimer.leaveCombat();
            }
        }
    }
}

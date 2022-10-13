package de.ttt.nopvplog.listeners;

import de.ttt.nopvplog.NoPvPLogTemplate;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashMap;

public class CombatDetector implements Listener {

    private final NoPvPLogTemplate template;

    public CombatDetector(NoPvPLogTemplate template) {
        this.template = template;
    }

    /**
     * Handles updating of Combat timers, both PvP and general
     *
     * @param event the event to update the timers from
     */
    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {

        if (!(event.getEntity() instanceof Player)) return;

        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            //Cover all Damage causes except entity attacks, update general timer
            template.getDTController().updateEntry(event);

        } else if (event instanceof EntityDamageByEntityEvent damageByEntityEvent) {

            if (damageByEntityEvent.getDamager() instanceof Player) {
                //Cover damage by enemy players, update combat timer
                template.getCTController().updateEntry(damageByEntityEvent);
                //Now covered all except non player entity attacks
            } else {
                //Cover all non player entity attacks, update the general timer
                template.getDTController().updateEntry(event);
            }
        }
    }
}
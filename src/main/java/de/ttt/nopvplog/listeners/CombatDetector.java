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
     * @param event
     */
    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {

        if (!(event.getEntity() instanceof Player)) return;

        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            //Cover all Damage causes except entity attacks
            template.getDTController().updateEntry(event);

        } else if(event instanceof EntityDamageByEntityEvent damageByEntityEvent) {

            if (damageByEntityEvent.getDamager() instanceof Player) {
                //Cover damage by enemy players
                template.getCTController().updateEntry(damageByEntityEvent);
                //Now covered all except non player entity attacks
            } else if (damageByEntityEvent.getDamager() instanceof Projectile projectile) {

                if (projectile.getShooter() instanceof Player player) {
                    //Cover players shooting projectiles, all thats left is non player entity attacks
                    template.getCTController().updateEntry(
                            new EntityDamageByEntityEvent(player, event.getEntity(),
                                    event.getCause(), new HashMap<>(), new HashMap<>(), damageByEntityEvent.isCritical())
                    );
                }
            } else {
                //Cover all non player entity attacks
                template.getDTController().updateEntry(event);
            }
        }
    }
}
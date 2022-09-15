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

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {

        if (!(event.getEntity() instanceof Player)) return;

        if (event.getDamager() instanceof Player) {
            template.getCTController().updateEntry(event);
        } else if (event.getDamager() instanceof Projectile projectile) {

            if (projectile.getShooter() instanceof Player player) {
                template.getCTController().updateEntry(
                        new EntityDamageByEntityEvent(player, event.getEntity(),
                                event.getCause(), new HashMap<>(), new HashMap<>(), event.isCritical())
                );
            }
        } else {
            template.getDTController().updateEntry(event);
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {

        if (!(event.getEntity() instanceof Player)) return;

        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            template.getDTController().updateEntry(event);
        }
    }

}



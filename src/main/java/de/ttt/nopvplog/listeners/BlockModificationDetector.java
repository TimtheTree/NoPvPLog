package de.ttt.nopvplog.listeners;

import de.ttt.nopvplog.NoPvPLogTemplate;
import de.ttt.nopvplog.models.Timer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.UUID;

public class BlockModificationDetector implements Listener {

    private final NoPvPLogTemplate template;

    public BlockModificationDetector(NoPvPLogTemplate template) {
        this.template = template;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent event) {
        if (cancelModification(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (cancelModification(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }


    /**
     * @param playerId The player to check
     * @return whether the player can modify blocks (false) or it should be cancelled (true)
     */
    private boolean cancelModification(UUID playerId) {

        Timer<? extends EntityDamageEvent> relatedTimer = template.getLongerTimer(playerId);

        return !relatedTimer.isOutOfCombat();
    }
}

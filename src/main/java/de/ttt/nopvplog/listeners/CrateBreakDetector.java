package de.ttt.nopvplog.listeners;

import de.ttt.nopvplog.NoPvPLogTemplate;
import de.ttt.nopvplog.controllers.HologramController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.UUID;

public class CrateBreakDetector implements Listener {

    private final NoPvPLogTemplate template;

    public CrateBreakDetector(NoPvPLogTemplate template) {
        this.template = template;
    }

    /**removes a hologram as part of a crate is broken
     *
     * @param event Blockbreak event
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {

        if (event.isCancelled()) return;

        if (event.getBlock().hasMetadata(HologramController.getMetadataKey())) {
            template.getHologramController().removeHologram((UUID[]) event.getBlock().getMetadata(HologramController.getMetadataKey()).get(0).value(), event);
        }
    }
}

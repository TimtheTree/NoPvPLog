package de.ttt.nopvplog.listeners;

import de.ttt.nopvplog.NoPvPLogTemplate;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoinDetector implements Listener {

    NoPvPLogTemplate template;

    public PlayerJoinDetector(NoPvPLogTemplate template) {
        this.template = template;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        UUID playerId = event.getPlayer().getUniqueId();

        template.getCTController().getTimer(playerId).leaveCombat();
        template.getDTController().getTimer(playerId).leaveCombat();

    }
}

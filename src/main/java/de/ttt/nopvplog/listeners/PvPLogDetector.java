package de.ttt.nopvplog.listeners;

import de.ttt.nopvplog.NoPvPLogTemplate;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PvPLogDetector implements Listener {

    private NoPvPLogTemplate template;

    public PvPLogDetector(NoPvPLogTemplate template) {
        this.template = template;
    }

    public void onPlayerLeave(PlayerQuitEvent event) {

    }

}

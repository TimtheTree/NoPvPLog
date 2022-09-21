package de.ttt.nopvplog.models.actionbar;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public record CombatTimerMessage(String baseMessage, UUID ownerId) {
    private static final String TIMER_PLACEHOLDER = "%time%";

    /**
     * Sends the owner of this CombatTimerMessage
     *
     * @param time the time to be displayed to the player
     */
    public void display(long time) {

        Player player = Bukkit.getPlayer(this.ownerId);

        if (player == null) return;

        player.sendActionBar(Component.text(renderMessage(time)));

    }

    /**
     * Replaces the time placeholder for the actual time
     *
     * @param time the time to display
     * @return the fully rendered message
     */
    private String renderMessage(long time) {

        if(time < 0) time = 0;

        return this.baseMessage.replaceAll(TIMER_PLACEHOLDER, String.valueOf(time));
    }
}

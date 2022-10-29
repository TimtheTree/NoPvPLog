package de.ttt.nopvplog.models.actionbar;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public record CombatTimerMessage(String timerBaseMessage, String enemyBaseMessage,UUID ownerId) {
    private static final String TIMER_PLACEHOLDER = "%time%";

    private static final String DISTANCE_PLACEHOLDER = "%deactivationDistance%";

    /**
     * Sends the owner of this CombatTimerMessage
     *
     * @param time the time to be displayed to the player
     */
    public void display(long time, boolean inCombat, long deactivationDistance) {

        Player player = Bukkit.getPlayer(this.ownerId);

        if (player == null) return;

        player.sendActionBar(Component.text(renderMessage(time, inCombat, deactivationDistance)));

    }

    /**
     * Replaces the time placeholder for the actual time
     *
     * @param time the time to display
     * @return the fully rendered message
     */
    private String renderMessage(long time, boolean inCombat, long deactivationDistance) {

        String msg = "";

        if (time >= 0) msg = this.timerBaseMessage.replace(TIMER_PLACEHOLDER, String.valueOf(time));
        else if (inCombat)
            msg = this.enemyBaseMessage.replace(DISTANCE_PLACEHOLDER, String.valueOf(deactivationDistance));

        return  msg;
    }
}

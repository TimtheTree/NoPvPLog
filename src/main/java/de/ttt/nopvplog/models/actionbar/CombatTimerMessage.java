package de.ttt.nopvplog.models.actionbar;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CombatTimerMessage {
    private static final String TIMER_PLACEHOLDER = "%time%";

    private final String baseMessage;
    private final UUID ownerId;

    public CombatTimerMessage(String baseMessage, UUID ownerId) {
        this.baseMessage = baseMessage;
        this.ownerId = ownerId;
    }

    /**
     * Sends the owner of this CombatTimerMessage
     * @param time the time to be displayed to the player
     */
    public void display(long time){

        Player player = Bukkit.getPlayer(this.ownerId);

        if(player == null) return;

        player.sendActionBar(Component.text(renderMessage(time)));

    }

    private String renderMessage(long time){
        return this.baseMessage.replaceAll(TIMER_PLACEHOLDER, String.valueOf(time));
    }
}

package de.ttt.nopvplog.models.actionbar;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public abstract class AbstractActionBarMessage implements ActionBarMessage {

    protected static final String TIME_PLACEHOLDER = "%timer%";

    protected final String message;

    private int currentTimer;

    protected AbstractActionBarMessage(String message, int currentTimer) {
        this.message = message;
        this.currentTimer = currentTimer;
    }

    @Override
    public void update() {

    }

    @Override
    public void displayMessage(UUID playerId) {

        Player player = Bukkit.getPlayer(playerId);

        if (player == null) return;

        player.sendActionBar(Component.text().content(this.renderMessage()));

    }

    private String renderMessage() {

        return message.replaceAll(TIME_PLACEHOLDER, String.valueOf(this.currentTimer));

    }

}

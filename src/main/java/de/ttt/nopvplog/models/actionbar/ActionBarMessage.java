package de.ttt.nopvplog.models.actionbar;

import java.util.UUID;

public interface ActionBarMessage {
    void update();
    void displayMessage(UUID playerId);

}

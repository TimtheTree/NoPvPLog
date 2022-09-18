package de.ttt.nopvplog.models;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public class Hologram {

    //TODO add despawning to armorstands

    private Player owner;

    private final Location location = owner.getLocation().toCenterLocation();

    private final String name = owner.getName();

    public Hologram(Player owner) {
        this.owner = owner;
    }

    /**
     * Creates two lines of flying text above the passed players position
     *
     * @param mainTextColor   the color the upper text should be which contains the players name
     * @param secondTextColor the color the lower text should be
     * @param mainText        part of the upper text (which should be fitted to the passed players name standing at the beginning of the upper text)
     * @param secondText      the lower text
     */
    public void createHologram(ChatColor mainTextColor, ChatColor secondTextColor, String mainText, String secondText) {

        createMainText(mainTextColor, mainText);

        createSecondText(secondTextColor, secondText);

    }

    /**
     * Creates the upper line of flying text
     *
     * @param textColor color the text should be
     * @param text the text
     */
    private void createMainText(ChatColor textColor, String text) {
        ArmorStand mainTextAS = this.location.getWorld().spawn(this.location.add(0, 0.1, 0), ArmorStand.class);

        mainTextAS.setGravity(false);
        mainTextAS.setCanPickupItems(false);
        mainTextAS.setCustomName(textColor + this.name + text);
        mainTextAS.setCustomNameVisible(true);
        mainTextAS.setVisible(false);
    }

    /**
     * Creates the lower line of flying text
     *
     * @param textColor color the text should be
     * @param text the text
     */
    private void createSecondText(ChatColor textColor, String text) {
        ArmorStand secondTextAS = this.location.getWorld().spawn(this.location.subtract(0, 0.3, 0), ArmorStand.class);

        secondTextAS.setGravity(false);
        secondTextAS.setCanPickupItems(false);
        secondTextAS.setCustomName(textColor + text);
        secondTextAS.setCustomNameVisible(true);
        secondTextAS.setVisible(false);
    }
}

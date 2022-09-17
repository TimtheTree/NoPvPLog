package de.ttt.nopvplog.models;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public class Hologram {

    //TODO add despawning to armorstands
    //TODO maybe add heads to armorstands

    private Player owner;

    public Hologram(Player owner) {
        this.owner = owner;
    }

    /**
     * Creates two lines of flying text above the passed players position
     * @param mainTextColor the color the upper text should be which contains the players name
     * @param secondTextColor the color the lower text should be
     * @param mainText part of the upper text (which should be fitted to the passed players name standing at the beginning of the upper text)
     * @param secondText the lower text
     */
    public void createHologram(ChatColor mainTextColor, ChatColor secondTextColor, String mainText, String secondText) {
        Location location = owner.getLocation().toCenterLocation();

        ArmorStand mainTextAS = location.getWorld().spawn(location.add(0, 0.1, 0), ArmorStand.class);

        ArmorStand secondTextAS = location.getWorld().spawn(location.subtract(0, 0.3, 0), ArmorStand.class);

        String name = owner.getName();

        mainTextAS.setGravity(false);
        mainTextAS.setCanPickupItems(false);
        mainTextAS.setCustomName(mainTextColor + name + mainText);
        mainTextAS.setCustomNameVisible(true);
        mainTextAS.setVisible(false);

        secondTextAS.setGravity(false);
        secondTextAS.setCanPickupItems(false);
        secondTextAS.setCustomName(secondTextColor + secondText);
        secondTextAS.setCustomNameVisible(true);
        secondTextAS.setVisible(false);
    }
}

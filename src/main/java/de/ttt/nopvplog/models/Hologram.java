package de.ttt.nopvplog.models;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Hologram {

    //TODO add despawning to armorstands

    private Player owner;

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
        Location location = this.owner.getLocation().toCenterLocation();
        String name = this.owner.getName();

        createMainText(location, name, mainTextColor, mainText);

        createSecondText(location, secondTextColor, secondText);

    }

    public void removeHologram(UUID mASID, UUID sASID) {

        Bukkit.getServer().getWorld(mASID).getEntity(mASID).remove();

        Bukkit.getServer().getWorld(sASID).getEntity(sASID).remove();

    }

    /**
     * Creates the upper line of flying text
     *
     * @param textColor color the text should be
     * @param text      the text
     */
    private void createMainText(Location location, String name, ChatColor textColor, String text) {
        ArmorStand mainTextAS = location.getWorld().spawn(location.add(0, 0.1, 0), ArmorStand.class);

        mainTextAS.setGravity(false);
        mainTextAS.setCanPickupItems(false);
        mainTextAS.setCustomName(textColor + name + text);
        mainTextAS.setCustomNameVisible(true);
        mainTextAS.setVisible(false);
    }

    /**
     * Creates the lower line of flying text
     *
     * @param textColor color the text should be
     * @param text      the text
     */
    private void createSecondText(Location location, ChatColor textColor, String text) {
        ArmorStand secondTextAS = location.getWorld().spawn(location.subtract(0, 0.3, 0), ArmorStand.class);

        secondTextAS.setGravity(false);
        secondTextAS.setCanPickupItems(false);
        secondTextAS.setCustomName(textColor + text);
        secondTextAS.setCustomNameVisible(true);
        secondTextAS.setVisible(false);
    }
}

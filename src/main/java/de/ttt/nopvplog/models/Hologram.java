package de.ttt.nopvplog.models;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Hologram {

    //TODO add despawning to armorstands

    private final String name;
    private final Location location;
    private ArmorStand mainTextAS;
    private ArmorStand secondTextAS;

    public Hologram(PlayerQuitEvent event) {
        this.name = event.getPlayer().getName();
        this.location = event.getPlayer().getLocation();
    }

    public static void removeHologram(UUID mASID, UUID sASID, World world) {

        Entity mainArmorStand = world.getEntity(mASID);
        Entity secondArmorStand = world.getEntity(sASID);

        if (mainArmorStand != null) {
            mainArmorStand.remove();
        }

        if (secondArmorStand != null) {
            secondArmorStand.remove();
        }
    }

    public @NotNull UUID getMainTextASUUID() {
        return mainTextAS.getUniqueId();
    }

    public @NotNull UUID getSecondTextASUUID() {
        return secondTextAS.getUniqueId();
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

        createMainText(location, name, mainTextColor, mainText);

        createSecondText(location, secondTextColor, secondText);

    }

    /**
     * Creates the upper line of flying text
     *
     * @param textColor color the text should be
     * @param text      the text
     */
    private void createMainText(Location location, String name, ChatColor textColor, String text) {
        mainTextAS = location.getWorld().spawn(location.toCenterLocation().add(0, 0.1, 0), ArmorStand.class);

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
        secondTextAS = location.getWorld().spawn(location.toCenterLocation().subtract(0, 0.2, 0), ArmorStand.class);

        secondTextAS.setGravity(false);
        secondTextAS.setCanPickupItems(false);
        secondTextAS.setCustomName(textColor + text);
        secondTextAS.setCustomNameVisible(true);
        secondTextAS.setVisible(false);
    }
}

package de.ttt.nopvplog.models;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public class Hologram {

    private Player owner;

    public Hologram(Player owner) {
        this.owner = owner;
    }

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

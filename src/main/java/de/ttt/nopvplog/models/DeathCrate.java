package de.ttt.nopvplog.models;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class DeathCrate {

    private final Inventory mainInv;

    private final Inventory equipInv;

    private final UUID owner;

    public DeathCrate(Inventory mainInv, Inventory equipInv, UUID owner) {
        this.mainInv = mainInv;
        this.equipInv = equipInv;
        this.owner = owner;
    }

    /**
     * Fills both crates of a player who just combat logged
     * @param player the player to fill the crates from
     */
    public void fillCrates(Player player) {

    }

    /**
     * Fills the main (bottom) crate from the players inventory
     * @param player
     */
    private void fillMain(Player player) {

    }

    /**
     * Fills the equipment (top) crate from the players hotbar and Armor
     * @param player
     */
    private void fillEquip(Player player) {

    }
}

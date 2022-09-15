package de.ttt.nopvplog.models;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
        fillMain(player);
        fillEquip(player);
    }

    /**
     * Fills the main (bottom) crate from the players inventory
     * @param player
     */
    private void fillMain(Player player) {
        mainInv.setContents(player.getInventory().getStorageContents());
    }

    /**
     * Fills the equipment (top) crate from the players hotbar and Armor
     * @param player
     */
    private void fillEquip(Player player) {

        ItemStack[] armorContents = player.getInventory().getArmorContents();
        ItemStack[] hotbarContents = player.getInventory().getExtraContents();

        int length = armorContents.length + hotbarContents.length;
        ItemStack[] equipContents = new ItemStack[length];

        int i = 0;

        for(ItemStack item : armorContents) {
            equipContents[i] = item;
            i++;
        }

        for(ItemStack item : hotbarContents) {
            equipContents[i] = item;
            i++;
        }

        equipInv.setContents(equipContents);
    }
}

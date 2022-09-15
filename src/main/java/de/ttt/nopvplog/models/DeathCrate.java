package de.ttt.nopvplog.models;

import de.ttt.nopvplog.NoPvPLogTemplate;
import de.ttt.nopvplog.exceptions.DeathCrateCreationException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class DeathCrate {

    private Inventory mainInv;

    private Inventory equipInv;

    private final UUID owner;

    private final NoPvPLogTemplate template;

    private final Material containerType;

    public DeathCrate(Inventory mainInv, Inventory equipInv, UUID owner, NoPvPLogTemplate template) {

        this.mainInv = mainInv;
        this.equipInv = equipInv;
        this.owner = owner;
        this.template = template;

        String containerName = template.getConfig().getString("DeathCrateType");

        try {

            this.containerType = Material.valueOf(containerName.toUpperCase());

        } catch (IllegalArgumentException e) {
            throw new DeathCrateCreationException("Could not get a proper container type from config. Make sure it exists and is a container with 3x9 inventory space!");
        }

    }

    public void createCrates(Location location){

        Block blockBottom = location.getWorld().getBlockAt(location);
        Block blockTop = blockBottom.getRelative(BlockFace.UP);

        blockBottom.setType(containerType);
        blockTop.setType(containerType);

        if(!(blockBottom instanceof Container containerBottom)){
            throw new DeathCrateCreationException("Could not get a proper container type from config. Make sure it exists and is a container with 3x9 inventory space!");
        }

        if(!(blockTop instanceof Container containerTop)){
            throw new DeathCrateCreationException("Could not get a proper container type from config. Make sure it exists and is a container with 3x9 inventory space!");
        }

        this.mainInv = containerBottom.getInventory();
        this.equipInv = containerTop.getInventory();

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

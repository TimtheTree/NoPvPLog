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

    public void fillCrates(Player player) {

    }

    private void fillMain(Player player) {

    }

    private void fillEquip(Player player) {

    }
}

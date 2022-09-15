package de.ttt.nopvplog.controllers;

import de.ttt.nopvplog.NoPvPLogTemplate;
import de.ttt.nopvplog.exceptions.DeathCrateCreationException;
import org.bukkit.Material;

public class DeathCrateController {

    private final Material containerType;
    private final NoPvPLogTemplate template;

    public DeathCrateController(NoPvPLogTemplate template) {
        this.template = template;

        String containerName = template.getConfig().getString("DeathCrateType");

        try {

            this.containerType = Material.valueOf(containerName.toUpperCase());

        } catch (IllegalArgumentException e) {
            throw new DeathCrateCreationException("Could not get a proper container type from config. Make sure it exists and is a container with 3x9 inventory space!");
        }
    }

    public Material getContainerType() {
        return containerType;
    }

    public NoPvPLogTemplate getTemplate() {
        return template;
    }
}

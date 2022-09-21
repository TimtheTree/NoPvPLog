package de.ttt.nopvplog.commands;

import de.ttt.nopvplog.NoPvPLogTemplate;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CombatRemoveCommand implements TabExecutor {
    private NoPvPLogTemplate template;

    public CombatRemoveCommand(NoPvPLogTemplate template) {
        this.template = template;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player && sender.isOp() && args.length == 1) {
            UUID playerId = Bukkit.getPlayer(args[0]).getUniqueId();

            template.getCTController().deleteEntry(playerId);
            template.getDTController().deleteEntry(playerId);

            return true;
        }
        else return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return new ArrayList<>();
    }
}

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

    /**
     * removes combat from the given player as the command is executed
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return boolean that is used to check if the command worked
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player && sender.isOp()) {
            UUID playerId = null;
            switch (args.length) {
                case 1:
                    playerId = Bukkit.getPlayer(args[0]).getUniqueId();
                    break;
                case 0:
                    playerId = ((Player) sender).getUniqueId();
                    break;
                default:
                    return false;
            }

            template.getCTController().getTimer(playerId).leaveCombat();
            template.getDTController().getTimer(playerId).leaveCombat();

            return true;
        } else return false;
    }

    /**
     * is used to complete the command in the commandline
     *
     * @param sender  Source of the command.  For players tab-completing a
     *                command inside of a command block, this will be the player, not
     *                the command block.
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    The arguments passed to the command, including final
     *                partial argument to be completed
     * @return an ArrayList to complete the command in the commandline
     */
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) return null;
        else return new ArrayList<>();
    }
}

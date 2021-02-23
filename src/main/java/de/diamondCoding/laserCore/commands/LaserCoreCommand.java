package de.diamondCoding.laserCore.commands;

import de.diamondCoding.laserCore.commands.subcommands.SubCommand;
import de.diamondCoding.laserCore.utils.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.*;

public class LaserCoreCommand implements TabExecutor {

    private final Map<String, SubCommand> subCommands = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //check if the command executor has permission
        if (!sender.hasPermission("lasercore.admin")) {
            Message.NO_PERMISSION.sendMessage(sender);
            return false;
        }
        //check if the subcommand was given
        if (args.length < 1) {
            Message.COMMAND_LASER_CORE_SYNTAX.sendMessage(sender);
            return false;
        }
        if (!(sender instanceof Player)) {
            Message.NO_PLAYER.sendMessage(sender);
            return false;
        }
        //get the player
        Player player = (Player) sender;
        String key = args[0].toLowerCase();
        if (subCommands.containsKey(key)) {
            return subCommands.get(key).execute(player, args);
        }
        Message.COMMAND_LASER_CORE_SYNTAX.sendMessage(sender);
        return false;
    }

    public void registerSubCommand(String command, SubCommand subCommand) {
        subCommands.put(command.toLowerCase(), subCommand);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!sender.hasPermission("lasercore.admin")) {
            return new ArrayList<>();
        }
        if(args.length <= 1) {
            return new ArrayList<>(subCommands.keySet());
        } else {
            SubCommand subCommand = subCommands.get(args[0].toLowerCase());
            if(subCommand != null) {
                return subCommand.onTabComplete(sender, command, alias, args);
            }
        }
        return new ArrayList<>();
    }

}

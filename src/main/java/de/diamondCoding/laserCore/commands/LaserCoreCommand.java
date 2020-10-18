package de.diamondCoding.laserCore.commands;

import de.diamondCoding.laserCore.commands.subcommands.SubCommand;
import de.diamondCoding.laserCore.utils.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.*;

public class LaserCoreCommand implements TabExecutor {
    private Map<String, SubCommand> subCommandMap = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //check if the command executor has permisison
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
        if (subCommandMap.containsKey(key)) {
            return subCommandMap.get(key).execute(player, args);
        }
        Message.COMMAND_LASER_CORE_SYNTAX.sendMessage(sender);
        return false;
    }

    public void registerSubCommand(String command, SubCommand subCommand) {
        subCommandMap.put(command.toLowerCase(), subCommand);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        System.out.println(Arrays.toString(args) + " " + args.length);
        if (!sender.hasPermission("lasercore.admin")) {
            return new ArrayList<>();
        }
        return new ArrayList<>(subCommandMap.keySet());
    }
}

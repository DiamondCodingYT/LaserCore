package de.diamondCoding.laserCore.commands.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public interface SubCommand {

    boolean execute(Player player, String[] args);

    List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args);

}

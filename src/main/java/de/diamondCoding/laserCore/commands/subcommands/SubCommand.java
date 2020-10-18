package de.diamondCoding.laserCore.commands.subcommands;

import org.bukkit.entity.Player;

public interface SubCommand {
    boolean execute(Player player, String[] args);
}

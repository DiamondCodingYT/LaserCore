package de.diamondCoding.laserCore.commands.subcommands;

import de.diamondCoding.laserCore.LaserCore;
import de.diamondCoding.laserCore.utils.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GunCommand implements SubCommand {

    @Override
    public boolean execute(Player player, String[] args) {
        int slot = player.getInventory().firstEmpty();
        if (slot == -1) {
            Message.COMMAND_LASER_CORE_GUN_INV_FULL.sendMessage(player);
            return false;
        }
        player.getInventory().setItem(slot, LaserCore.getLaserCore().generateGunItem());
        Message.COMMAND_LASER_CORE_GUN_GIVEN.sendMessage(player);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>();
    }

}

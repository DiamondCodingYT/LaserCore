package de.diamondCoding.laserCore.commands.subcommands;

import de.diamondCoding.laserCore.LaserCore;
import de.diamondCoding.laserCore.utils.Message;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class IsGunCommand implements SubCommand {
    @Override
    public boolean execute(Player player, String[] args) {
        ItemStack inHand = player.getInventory().getItemInHand();
        if (inHand.getType() == Material.AIR) {
            Message.COMMAND_LASER_CORE_ITEM_NULL.sendMessage(player);
            return true;
        }
        (LaserCore.getLaserCore().isGun(inHand) ? Message.COMMAND_LASER_CORE_IS_GUN_TRUE : Message.COMMAND_LASER_CORE_IS_GUN_FALSE).sendMessage(player);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>();
    }

}

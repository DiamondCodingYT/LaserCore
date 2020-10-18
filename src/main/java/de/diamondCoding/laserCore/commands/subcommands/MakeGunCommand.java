package de.diamondCoding.laserCore.commands.subcommands;

import de.diamondCoding.laserCore.LaserCore;
import de.diamondCoding.laserCore.utils.Message;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MakeGunCommand implements SubCommand {
    @Override
    public boolean execute(Player player, String[] args) {
        ItemStack inHand = player.getInventory().getItemInHand();
        if (inHand.getType() != Material.AIR) {
            player.getInventory().setItemInHand(LaserCore.getLaserCore().makeGun(inHand));
            Message.COMMAND_LASER_CORE_MAKE_GUN_MADE_GUN.sendMessage(player);
        } else {
            Message.COMMAND_LASER_CORE_ITEM_NULL.sendMessage(player);
        }
        return true;
    }
}

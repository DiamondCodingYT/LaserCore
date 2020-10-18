package de.diamondCoding.laserCore.commands;

import de.diamondCoding.laserCore.LaserCore;
import de.diamondCoding.laserCore.utils.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LaserCoreCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //check if the command executor has permisison
        if(!sender.hasPermission("lasercore.admin")) {
            Message.NO_PERMISSION.sendMessage(sender);
            return false;
        }
        //check if the subcommand was given
        if(args.length < 1) {
            Message.COMMAND_LASER_CORE_SYNTAX.sendMessage(sender);
            return false;
        }
        //get the player
        Player player;
        if(sender instanceof Player) {
            player = (Player) sender;
        } else {
            Message.NO_PLAYER.sendMessage(sender);
            return false;
        }
        //get the item in hand
        ItemStack inHand = player.getInventory().getItemInHand();
        switch (args[0].toLowerCase()) {
            case "gun":
                    int slot = player.getInventory().firstEmpty();
                    if(slot==-1) {
                        Message.COMMAND_LASER_CORE_GUN_INV_FULL.sendMessage(sender);
                        return false;
                    }
                    player.getInventory().setItem(slot, LaserCore.getLaserCore().generateGunItem());
                    Message.COMMAND_LASER_CORE_GUN_GIVEN.sendMessage(sender);
                return true;
            case "makegun":
                    if(inHand!=null) {
                        player.getInventory().setItemInHand(LaserCore.getLaserCore().makeGun(inHand));
                        Message.COMMAND_LASER_CORE_MAKE_GUN_MADE_GUN.sendMessage(sender);
                    } else {
                        Message.COMMAND_LASER_CORE_ITEM_NULL.sendMessage(sender);
                    }
                return true;
            case "isgun":
                    if(inHand!=null) {
                        (LaserCore.getLaserCore().isGun(inHand) ? Message.COMMAND_LASER_CORE_IS_GUN_TRUE : Message.COMMAND_LASER_CORE_IS_GUN_FALSE).sendMessage(sender);
                    } else {
                        Message.COMMAND_LASER_CORE_ITEM_NULL.sendMessage(sender);
                    }
                return true;
            default:
                Message.COMMAND_LASER_CORE_SYNTAX.sendMessage(sender);
        }
        return false;
    }

}

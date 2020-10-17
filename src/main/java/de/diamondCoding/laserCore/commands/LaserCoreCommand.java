package de.diamondCoding.laserCore.commands;

import de.diamondCoding.laserCore.utils.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class LaserCoreCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("lasercore.admin")) {
            Message.NO_PERMISSION.sendMessage(sender);
            return false;
        }
        if(args.length < 1) {
            Message.COMMAND_LASER_CORE_SYNTAX.sendMessage(sender);
            return false;
        }
        switch (args[0].toLowerCase()) {
            case "gun":
                //TODO
                return true;
            default:
                Message.COMMAND_LASER_CORE_SYNTAX.sendMessage(sender);
        }
        return false;
    }

}

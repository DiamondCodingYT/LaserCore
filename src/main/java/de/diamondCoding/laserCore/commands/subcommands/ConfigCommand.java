package de.diamondCoding.laserCore.commands.subcommands;

import com.google.gson.JsonPrimitive;
import de.diamondCoding.laserCore.LaserCore;
import de.diamondCoding.laserCore.utils.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ConfigCommand implements SubCommand {

    @Override
    public boolean execute(Player player, String[] args) {
        if(args.length < 2) {
            Message.COMMAND_LASER_CORE_CONFIG_SYNTAX.sendMessage(player);
            return false;
        }
        switch (args[1].toLowerCase()) {
            case "reload":
                LaserCore.getLaserCore().getConfigurationHandler().loadConfig();
                Message.COMMAND_LASER_CORE_CONFIG_CONFIG_RELOADED.sendMessage(player);
                return true;
            case "set":
                if(args.length < 4) {
                    Message.COMMAND_LASER_CORE_CONFIG_SET_SYNTAX.sendMessage(player);
                    return false;
                }
                String option = args[2];
                String value = args[3];
                switch (option.toLowerCase()) {
                    case "guncooldown":
                        int gunCooldown;
                        try {
                            gunCooldown = Integer.parseInt(value);
                        } catch (NumberFormatException exception) {
                            Message.COMMAND_LASER_CORE_CONFIG_SET_NOT_INT.sendMessage(player, value);
                            return false;
                        }
                        if(gunCooldown < 0) {
                            Message.COMMAND_LASER_CORE_CONFIG_SET_NOT_POSITIV_INT.sendMessage(player, value);
                            return false;
                        }
                        LaserCore.getLaserCore().getConfigurationHandler().getConfigJson().add("gunCooldown", new JsonPrimitive(gunCooldown));
                        LaserCore.getLaserCore().getConfigurationHandler().writeConfigFile();
                        LaserCore.getLaserCore().getConfigurationHandler().loadValues();
                        Message.COMMAND_LASER_CORE_CONFIG_SET_SUCCESS.sendMessage(player, option, value+"ms");
                        break;
                    default:
                        Message.COMMAND_LASER_CORE_CONFIG_SET_UNKNOWN_OPTION.sendMessage(player, option);
                }
                return false;
            default:
                Message.COMMAND_LASER_CORE_CONFIG_SYNTAX.sendMessage(player);
                return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> result = new ArrayList<>();
        if(args.length <= 2) {
            result.add("reload");
            result.add("set");
        } else if(args.length == 3) {
            switch (args[1].toLowerCase()) {
                case "set":
                    result = new ArrayList<>(LaserCore.getLaserCore().getConfigurationHandler().getValues().keySet());
                case "config":
                default:
                    break;
            }
        }
        return result;
    }

}

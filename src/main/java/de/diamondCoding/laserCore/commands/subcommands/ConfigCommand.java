package de.diamondCoding.laserCore.commands.subcommands;

import de.diamondCoding.laserCore.LaserCore;
import de.diamondCoding.laserCore.configuration.ConfigValue;
import de.diamondCoding.laserCore.configuration.exceptions.ConfigValueCanNotBeParsedFromStringException;
import de.diamondCoding.laserCore.configuration.exceptions.UnknownConfigValueException;
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
                String optionName = args[2];
                String valueInput = args[3];
                ConfigValue currentConfigValue;
                try {
                    currentConfigValue = LaserCore.getLaserCore().getConfigurationHandler().getValue(optionName);
                } catch (UnknownConfigValueException exception) {
                    Message.COMMAND_LASER_CORE_CONFIG_SET_UNKNOWN_OPTION.sendMessage(player, optionName);
                    return false;
                }
                try {
                    ConfigValue newConfigValue = currentConfigValue.fromString(valueInput);
                    LaserCore.getLaserCore().getConfigurationHandler().getConfigJson().add(optionName, newConfigValue.buildJson());
                    LaserCore.getLaserCore().getConfigurationHandler().writeConfigFile();
                    LaserCore.getLaserCore().getConfigurationHandler().loadValues();
                    Message.COMMAND_LASER_CORE_CONFIG_SET_SUCCESS.sendMessage(player, optionName, newConfigValue.getDisplayableValueString());
                } catch (ConfigValueCanNotBeParsedFromStringException exception) {
                    Message.COMMAND_LASER_CORE_CONFIG_SET_COULD_NOT_BE_PARSED.sendMessage(player, valueInput, exception.getReason());
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
            if (args[1].equalsIgnoreCase("set")) {
                result = new ArrayList<>(LaserCore.getLaserCore().getConfigurationHandler().getValues().keySet());
            }
        }
        return result;
    }

}

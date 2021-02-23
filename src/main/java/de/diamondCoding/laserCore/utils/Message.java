package de.diamondCoding.laserCore.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public enum Message {

    //hardcoded placeholders
    PREFIX("&4&lLaser&c&lCore &8»&7"),
    SYNTAX("&8[&cUsage&8]&c"),
    ERROR("&8[&cError&8]&c"),

    //item names
    ITEM_GUN("&3Phaser"),

    //general messages
    NO_PERMISSION("{prefix} &cYou have no permissions!"),
    NO_PLAYER("{prefix} &cThis command is only available to players!"),

    //lasercore command
    COMMAND_LASER_CORE_SYNTAX("{prefix} {syntax} /lasercore <gun|makegun|isgun|config>"),
    COMMAND_LASER_CORE_ITEM_NULL("{prefix} {error} You are not holding an item your ahnd."),

    COMMAND_LASER_CORE_GUN_INV_FULL("{prefix} {error} Your inventory is full."),
    COMMAND_LASER_CORE_GUN_GIVEN("{prefix} &aA LaserCore Gun was added to your Inventory."),

    COMMAND_LASER_CORE_MAKE_GUN_MADE_GUN("{prefix} &aThe item in your hand has been converted to a LaserCore Gun."),

    COMMAND_LASER_CORE_IS_GUN_TRUE("{prefix} &aThe item in your hand is a LaserCore Gun."),
    COMMAND_LASER_CORE_IS_GUN_FALSE("{prefix} &cThe item in your hand is not a LaserCore Gun."),

    COMMAND_LASER_CORE_CONFIG_SYNTAX("{prefix} {syntax} /lasercore config <reload|set> [...]"),
    COMMAND_LASER_CORE_CONFIG_CONFIG_RELOADED("{prefix} &aThe Config was reload &2successfully&a."),
    COMMAND_LASER_CORE_CONFIG_SET_SYNTAX("{prefix} {syntax} /lasercore config set <gunCooldown> <value>"),
    COMMAND_LASER_CORE_CONFIG_SET_UNKNOWN_OPTION("{prefix} &cUnknown config option '{0}'"),
    COMMAND_LASER_CORE_CONFIG_SET_COULD_NOT_BE_PARSED("{prefix} &c'{0}' could not be parsed. Reason: {1}"),
    COMMAND_LASER_CORE_CONFIG_SET_SUCCESS("{prefix} &2{0}&a was set to &2{1}&a.");

    private final String MESSAGE;
    private final String REGEX;

    Message(final String MESSAGE) {
        this.MESSAGE = MESSAGE;
        REGEX = String.format("\\{%s}", this.name().toLowerCase());
    }

    public String getMessage(Object... messageValues) {
        String result = MESSAGE;
        for (int i = 0; i < messageValues.length; i++)
            result = result.replaceAll("\\{" + i + "}", messageValues[i].toString());

        for (Message value : values()) {
            result = result.replaceAll(value.REGEX, value.MESSAGE);
        }

        return ChatColor.translateAlternateColorCodes('&', result);
    }

    public void sendMessage(CommandSender sender, Object... messageValues) {
        sender.sendMessage(getMessage(messageValues));
    }

}

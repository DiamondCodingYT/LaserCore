package de.diamondCoding.laserCore.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public enum Message {

    //hardcoded placeholders
    PREFIX("&4&lLaser&c&lCore &8»&7"),
    SYNTAX("&8[&cVerwenung&8]&c"),
    ERROR("&8[&cFehler&8]&c"),

    //item names
    ITEM_GUN("&3Phaser"),

    //general messages
    NO_PERMISSION("{prefix} &cDu hast keine Rechte!"),
    NO_PLAYER("{prefix} &cDiese Funktion funktoniert leider nur bei Spielern!"),

    //lasercore command
    COMMAND_LASER_CORE_SYNTAX("{prefix} {syntax} /lasercore <gun|makegun|isgun>"),
    COMMAND_LASER_CORE_ITEM_NULL("{prefix} {error} Du hällst kein Item in der Hand."),

    COMMAND_LASER_CORE_GUN_INV_FULL("{prefix} {error} Dein Inventar ist voll."),
    COMMAND_LASER_CORE_GUN_GIVEN("{prefix} &aDir wurde eine LaserCore Gun gegeben."),

    COMMAND_LASER_CORE_MAKE_GUN_MADE_GUN("{prefix} &aDieses Item ist nun eine LaserCore Gun."),

    COMMAND_LASER_CORE_IS_GUN_TRUE("{prefix} &aDas gehaltene Item ist eine LaserCore Gun."),
    COMMAND_LASER_CORE_IS_GUN_FALSE("{prefix} &cDas gehaltene Item ist keine LaserCore Gun.");

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

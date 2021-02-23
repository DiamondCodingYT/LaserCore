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
    COMMAND_LASER_CORE_SYNTAX("{prefix} {syntax} /lasercore <gun|makegun|isgun|config>"),
    COMMAND_LASER_CORE_ITEM_NULL("{prefix} {error} Du hällst kein Item in der Hand."),

    COMMAND_LASER_CORE_GUN_INV_FULL("{prefix} {error} Dein Inventar ist voll."),
    COMMAND_LASER_CORE_GUN_GIVEN("{prefix} &aDir wurde eine LaserCore Gun gegeben."),

    COMMAND_LASER_CORE_MAKE_GUN_MADE_GUN("{prefix} &aDieses Item ist nun eine LaserCore Gun."),

    COMMAND_LASER_CORE_IS_GUN_TRUE("{prefix} &aDas gehaltene Item ist eine LaserCore Gun."),
    COMMAND_LASER_CORE_IS_GUN_FALSE("{prefix} &cDas gehaltene Item ist keine LaserCore Gun."),

    COMMAND_LASER_CORE_CONFIG_SYNTAX("{prefix} {syntax} /lasercore config <reload|set> [...]"),
    COMMAND_LASER_CORE_CONFIG_CONFIG_RELOADED("{prefix} &aDie Config wurde erfolgreich neu geladen."),
    COMMAND_LASER_CORE_CONFIG_SET_SYNTAX("{prefix} {syntax} /lasercore config set <gunCooldown> <value>"),
    COMMAND_LASER_CORE_CONFIG_SET_UNKNOWN_OPTION("{prefix} &cUnbekannt Config Option '{0}'"),
    COMMAND_LASER_CORE_CONFIG_SET_NOT_INT("{prefix} &c'{0}' konnte nicht als Ganzzahl erkannt werden."),
    COMMAND_LASER_CORE_CONFIG_SET_NOT_POSITIV_INT("{prefix} &c{0} ist keine positive Ganzzahl erkannt werden."),
    COMMAND_LASER_CORE_CONFIG_SET_SUCCESS("{prefix} &2{0}&a wurde zu &2{1}&a gesetzt.");

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

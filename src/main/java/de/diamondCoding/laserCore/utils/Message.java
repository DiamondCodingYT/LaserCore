package de.diamondCoding.laserCore.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public enum Message {

    PREFIX("LaserCore"),
    SYNTAX("Verwenung:"),

    ITEM_GUN("&3Phaser"),

    NO_PERMISSION("{prefix} &cDu hast keine Rechte!"),
    COMMAND_LASER_CORE_SYNTAX("{prefix} {syntax} /lasercore <gun|todo>");

    private final String MESSAGE;

    Message(final String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    public String getMessage(Object... messageValues) {
        String result = MESSAGE;
        for (int i = 0; i < messageValues.length; i++) result = result.replaceAll("\\{" + i + "}", messageValues[i].toString());
        result = result.replaceAll("\\{prefix}", PREFIX.MESSAGE);
        result = result.replaceAll("\\{syntax}", SYNTAX.MESSAGE);
        return ChatColor.translateAlternateColorCodes('&', result);
    }

    public void sendMessage(CommandSender sender, Object... messageValues) {
        sender.sendMessage(getMessage(messageValues));
    }

}

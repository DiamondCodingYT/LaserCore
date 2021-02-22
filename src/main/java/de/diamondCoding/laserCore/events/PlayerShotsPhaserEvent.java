package de.diamondCoding.laserCore.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerShotsPhaserEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    @Getter
    @Setter
    private boolean cancelled;

    @Getter
    private final Player shooter;
    @Getter
    @Setter
    private final Color laserColor;
    @Getter
    @Setter
    private final int cooldownMilliseconds;

    public PlayerShotsPhaserEvent(Player shooter, Color laserColor, int cooldownMilliseconds) {
        this.shooter = shooter;
        this.laserColor = laserColor;
        this.cooldownMilliseconds = cooldownMilliseconds;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}

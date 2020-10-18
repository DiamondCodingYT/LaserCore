package de.diamondCoding.laserCore.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LaserEntityHitEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Getter
    @Setter
    private boolean cancelled;

    @Getter
    private final Entity entity;
    @Getter
    private final Player shooter;

    public LaserEntityHitEvent(Entity entity, Player shooter) {
        this.entity = entity;
        this.shooter = shooter;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}

package de.diamondCoding.laserCore.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LaserBlockHitEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Getter
    @Setter
    private boolean cancelled;

    @Getter
    private final Block block;
    @Getter
    private final Player shooter;

    public LaserBlockHitEvent(Block block, Player shooter) {
        this.block = block;
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

package de.diamondCoding.laserCore.utils;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

public class RayTraceHit {

    private Block block;
    private Entity entity;

    public RayTraceHit(Block block) {
        this.block = block;
    }
    public RayTraceHit(Entity entity) {
        this.entity = entity;
    }

    public boolean isBlockHit() {
        return block!=null;
    }
    public boolean isEntityHit() {
        return entity!=null;
    }

    public Block getBlock() {
        return block;
    }
    public Entity getEntity() {
        return entity;
    }

}

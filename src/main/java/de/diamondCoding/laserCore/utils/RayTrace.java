package de.diamondCoding.laserCore.utils;

import de.diamondCoding.laserCore.events.LaserBlockHitEvent;
import de.diamondCoding.laserCore.events.LaserEntityHitEvent;
import lombok.Setter;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class RayTrace {

    World world;
    Vector origin, direction;
    double reach, accuracy;

    @Setter
    Entity rayCaster;

    /**
     * Makes a realy cool raycast
     *
     * @param world     The world or the raycast
     * @param origin    The Origin of the raytrace, so where the raytrace starts
     * @param direction The direction of the raytrace
     * @param reach     how long the raytrace should go (in blocks)
     * @param accuracy  how accurate the raytrace should be (in blocks), so in how small increments it goes
     */
    public RayTrace(World world, Vector origin, Vector direction, double reach, double accuracy) {
        this.world = world;
        this.origin = origin;
        this.direction = direction;
        this.reach = reach;
        this.accuracy = accuracy;
    }

    //get a point on the raytrace at X blocks away
    public Vector getPostion(double blocksAway) {
        return origin.clone().add(direction.clone().multiply(blocksAway));
    }

    /**
     * This Methode traveses the raycast till it hits something
     */
    public void traverseShoot(Player player, boolean withParticles) {
        for (double d = 0; d <= reach; d += accuracy) {
            Vector position = getPostion(d);
            if (withParticles)
                world.spigot().playEffect(position.toLocation(world), Effect.COLOURED_DUST, 0, 1, 255, 0, 0, 1, 0, 64); //maybe build player specific effects
            Location location = position.toLocation(world);

            //test for entity hit
            Entity[] entities = location.getChunk().getEntities();
            for (Entity entity : entities) {
                if (entity == null || (rayCaster != null && entity == rayCaster)) continue;
                BoundingBox boundingBox = new BoundingBox(entity);
                if (intersects(position, boundingBox.min, boundingBox.max)) {
                    //if a entity was hit we trigger that event
                    LaserEntityHitEvent laserEntityHitEvent = new LaserEntityHitEvent(entity, player);
                    Bukkit.getServer().getPluginManager().callEvent(laserEntityHitEvent);
                    if (!laserEntityHitEvent.isCancelled()) return; //if the event wasn't canceled we can stop the laser
                }
            }

            //test for block hit
            Block block = world.getBlockAt(location);
            if (block != null) {
                BoundingBox boundingBox = new BoundingBox(block);
                if (block.getType() != Material.AIR && intersects(position, boundingBox.min, boundingBox.max)) {
                    //if a block was hit we trigger that event
                    LaserBlockHitEvent laserBlockHitEvent = new LaserBlockHitEvent(block, player);
                    Bukkit.getServer().getPluginManager().callEvent(laserBlockHitEvent);
                    if (!laserBlockHitEvent.isCancelled()) return; //if the event wasn't canceled we can stop the laser
                }
            }

        }
    }

    /**
     * Tests if the position vector is in between the min and max vector
     *
     * @param position the postion vector
     * @param min      the minamal vector
     * @param max      the maximal vectoe
     * @return if it intersects
     */
    public static boolean intersects(Vector position, Vector min, Vector max) {
        return !(
                (position.getX() < min.getX() || position.getX() > max.getX())
                || (position.getY() < min.getY() || position.getY() > max.getY())
                || (position.getZ() < min.getZ() || position.getZ() > position.getZ())
        );
    }

}